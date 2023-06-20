package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import server.KVTaskClient;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();
    private final KVTaskClient taskClient;

    public HttpTaskManager(String urlKVServer) {
        super(null);
        taskClient = new KVTaskClient(urlKVServer);
    }

    @Override
    public void save() {
        String jsonTasks = gson.toJson(tasks);
        String jsonEpics = gson.toJson(epics);
        String jsonSubtasks = gson.toJson(subtasks);
        String jsonHistory = gson.toJson(inMemoryHistoryManager.getHistory());

        taskClient.put("tasks", jsonTasks);
        taskClient.put("epics", jsonEpics);
        taskClient.put("subtasks", jsonSubtasks);
        taskClient.put("history", jsonHistory);
    }

    public static HttpTaskManager loadFromServer(String urlKVServer) {
        HttpTaskManager taskManager = new HttpTaskManager(urlKVServer);
        KVTaskClient client = taskManager.getTaskClient();

        String jsonTasks = client.load("tasks");
        String jsonEpics = client.load("epics");
        String jsonSubtasks = client.load("subtasks");
        String jsonHistory = client.load("history");

        Type mapType = new TypeToken<HashMap<Integer, Task>>() {
        }.getType();
        HashMap<Integer, Task> tasksRecover = gson.fromJson(jsonTasks, mapType);
        for (Task task : tasksRecover.values()) {
            taskManager.tasks.put(task.getIdTask(), task);
        }

        mapType = new TypeToken<HashMap<Integer, Epic>>() {
        }.getType();
        HashMap<Integer, Epic> epicsRecover = gson.fromJson(jsonEpics, mapType);
        for (Epic epic : epicsRecover.values()) {
            taskManager.epics.put(epic.getIdTask(), epic);
        }

        mapType = new TypeToken<HashMap<Integer, Subtask>>() {
        }.getType();
        HashMap<Integer, Subtask> subtasksRecover = gson.fromJson(jsonSubtasks, mapType);
        for (Subtask subtask : subtasksRecover.values()) {
            taskManager.subtasks.put(subtask.getIdTask(), subtask);
        }

        Type listType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> historyRecover = gson.fromJson(jsonHistory, listType);
        HashMap<Integer, Task> allTasks = new HashMap<>(taskManager.tasks);
        allTasks.putAll(taskManager.epics);
        allTasks.putAll(taskManager.subtasks);
        historyRecover.forEach(task -> taskManager.inMemoryHistoryManager.add(allTasks.get(task.getIdTask())));
        return taskManager;
    }

    public KVTaskClient getTaskClient() {
        return taskClient;
    }


    static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        public DateTimeFormatter formatterWriter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
            jsonWriter.value(localDateTime.format(formatterWriter));
        }

        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), formatterWriter);
        }
    }

    static class DurationAdapter extends TypeAdapter<Duration> {
        @Override
        public void write(JsonWriter jsonWriter, Duration duration) throws IOException {
            jsonWriter.value(duration.toMinutes());
        }

        @Override
        public Duration read(JsonReader jsonReader) throws IOException {
            return Duration.ofMinutes(Long.parseLong(jsonReader.nextString()));
        }
    }
}