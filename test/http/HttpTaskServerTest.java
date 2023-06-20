package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import server.KVServer;
import task.Epic;
import task.StatusTask;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    public static HttpTaskServer httpTaskServer;
    public static HttpClient client;
    public static final Gson gson = new GsonBuilder()
            //  .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();
    public static HttpRequest.Builder requestBuilder;
    public static URI uri;
    public static HttpRequest request;
    public static HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
    public static HttpResponse<String> response;

    public static KVServer kvServer;

    @BeforeAll
    public static void beforeAll() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
        requestBuilder = HttpRequest.newBuilder();
    }

    @AfterAll
    public static void AfterAll() {
        kvServer.stop();
    }

    @BeforeEach
    public void beforeEach() {
        client = HttpClient.newHttpClient();
        requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8090"));
    }

    @Test
    public void testGetAllTasks() throws IOException, InterruptedException {
        Task task = new Task("21.02.2023 14:00", "14", "Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        String taskJson = gson.toJson(task);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build(), handler);

        Epic epic = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        String epicJson = gson.toJson(epic);

        uri = URI.create("http://localhost:8090/tasks/epic/");
        response = client.send(requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .build(), handler);
        int id = Integer.parseInt(response.body());

        Subtask subtask = new Subtask("21.02.2023 14:15", "14", "Купить Ром/Колу", "Купить 1 литр", StatusTask.NEW, id);
        String subtaskJson = gson.toJson(subtask);

        uri = URI.create("http://localhost:8090/tasks/subtask/");
        response = client.send(requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .build(), handler);

        uri = URI.create("http://localhost:8090/tasks");
        request = requestBuilder
                .GET()
                .uri(uri)
                .build();
        response = client.send(request, handler);

        assertEquals(200, response.statusCode(), "Запрос не обработан");
        assertNotEquals(2, response.body(), "Задачи не сохраняются.");

    }


    @Test
    public void testGetAndPostTask() throws IOException, InterruptedException {
        Task task = new Task("02.02.2023 14:30", "14", "Заказать пиццу", "Позвонить в ресторан", StatusTask.NEW);
        String taskJson = gson.toJson(task);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build(), handler);

        assertEquals(200, response.statusCode(), "Запрос не обработан");
        assertFalse(response.body().isEmpty(), "Задача не сохранена, не возвращен id");
        int id = Integer.parseInt(response.body());

        uri = URI.create("http://localhost:8090/tasks/task/?id=" + id);
        response = client.send(requestBuilder
                .GET()
                .uri(uri)
                .build(), handler);

        task.setIdTask(id);
        taskJson = gson.toJson(task);

        assertEquals(200, response.statusCode(), "Запрос не обработан");
        assertEquals(taskJson, response.body(), "Задачи не совпадают");

    }

    @Test
    public void testGetAndPostTaskUpdate() throws IOException, InterruptedException {

        Task task = new Task("26.02.2023 17:00", "14", "Заказать пиццу", "Позвонить в ресторан", StatusTask.NEW);
        String taskJson = gson.toJson(task);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build(), handler);

        assertEquals(200, response.statusCode(), "Запрос не обработан");
        assertFalse(response.body().isEmpty(), "Задача не сохранена, не возвращен id");
        int id = Integer.parseInt(response.body());

        uri = URI.create("http://localhost:8090/tasks/task/?id=" + id);
        response = client.send(requestBuilder
                .GET()
                .uri(uri)
                .build(), handler);

        task.setIdTask(id);
        taskJson = gson.toJson(task);

       assertEquals(200, response.statusCode(), "Запрос не обработан");
        assertEquals(taskJson, response.body(), "Задачи не совпадают");

        //update
        Task task1 = new Task("26.02.2023 17:00", "14", "Заказать Торт", "Позвонить в кондитеру", StatusTask.NEW);
        String taskJson1 = gson.toJson(task1);

        uri = URI.create("http://localhost:8090/tasks/task/?id=" + id);
        response = client.send(requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson1))
                .build(), handler);

        assertEquals(200, response.statusCode(), "Запрос не обработан");
        assertFalse(response.body().isEmpty(), "Задача не сохранена, не возвращен id");

        uri = URI.create("http://localhost:8090/tasks/task/?id=" + id);
        response = client.send(requestBuilder
                .GET()
                .uri(uri)
                .build(), handler);

        task1.setIdTask(id);
        taskJson1 = gson.toJson(task1);

        assertEquals(200, response.statusCode(), "Запрос не обработан");
        assertEquals(taskJson1, response.body(), "Задача не обновлена");

    }

    @Test
    public void testGetAndDeleteTasks() throws IOException, InterruptedException {
        Task task = new Task("19.02.2023 09:15", "14", "Позвать гостей", "Обзвонить по списку", StatusTask.NEW);
        String taskJson = gson.toJson(task);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build(), handler);

        uri = URI.create("http://localhost:8090/tasks/delete");
        request = requestBuilder
                .DELETE()
                .uri(uri)
                .build();
        response = client.send(request, handler);

        uri = URI.create("http://localhost:8090/tasks");
        request = requestBuilder
                .GET()
                .uri(uri)
                .build();
        response = client.send(request, handler);

        assertEquals(200, response.statusCode(), "Запрос не обработан");
        assertEquals(2, response.body().length(), "Удаление задач не выполнено");

        task = new Task("19.02.2023 09:30", "14", "Заказать пиццу", "Позвонить в ресторан", StatusTask.NEW);
        taskJson = gson.toJson(task);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build(), handler);

        uri = URI.create("http://localhost:8090/tasks");
        request = requestBuilder
                .GET()
                .uri(uri)
                .build();
        response = client.send(request, handler);

        assertEquals(200, response.statusCode(), "Запрос не обработан");
        assertNotEquals(true, response.body().isEmpty(), "Возвращен пустой список задач");
    }

    @Test
    public void testGetHistory() throws IOException, InterruptedException {
        Task task = new Task("19.02.2023 19:00", "14", "Заказать пиццу", "Позвонить в ресторан", StatusTask.NEW);
        String taskJson = gson.toJson(task);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build(), handler);
        int id = Integer.parseInt(response.body());

        uri = URI.create("http://localhost:8090/tasks/task/?id=" + id);
        request = requestBuilder
                .GET()
                .uri(uri)
                .build();
        response = client.send(request, handler);

        uri = URI.create("http://localhost:8090/tasks/history");
        request = requestBuilder
                .GET()
                .uri(uri)
                .build();
        response = client.send(request, handler);

        assertEquals(200, response.statusCode(), "Запрос не обработан");
        assertNotEquals(2, response.body(), "История просмотров не сохраняется.");
    }


    @Test
    public void testDeleteAllTasks() throws IOException, InterruptedException {
        Task task = new Task("19.02.2023 08:00", "14", "Заказать пиццу", "Позвонить в ресторан", StatusTask.NEW);
        String taskJson = gson.toJson(task);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build(), handler);

        Epic epic = new Epic("Приготовить коктейль", "Купить ингредиенты в соответствии с рецептом", StatusTask.NEW);
        String epicJson = gson.toJson(epic);

        uri = URI.create("http://localhost:8090/tasks/epic/");
        response = client.send(requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .build(), handler);
        int id = Integer.parseInt(response.body());

        Subtask subtask = new Subtask("19.02.2023 08:45", "14", "Купить Ром/Колу", "Купить 1 литр", StatusTask.NEW, id);
        String subtaskJson = gson.toJson(subtask);

        uri = URI.create("http://localhost:8090/tasks/subtask/");
        response = client.send(requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .build(), handler);

        uri = URI.create("http://localhost:8090/tasks/delete");
        request = requestBuilder
                .DELETE()
                .uri(uri)
                .build();
        response = client.send(request, handler);

        uri = URI.create("http://localhost:8090/tasks");
        request = requestBuilder
                .GET()
                .uri(uri)
                .build();
        response = client.send(request, handler);

        assertEquals(200, response.statusCode(), "Запрос не обработан");
        assertEquals(2, response.body().length(), "Задачи не удалены.");
    }

    @Test
    public void testDeleteTaskByID() throws IOException, InterruptedException {
        Task task = new Task("27.02.2023 12:00", "14", "Заказать пиццу", "Позвонить в ресторан", StatusTask.NEW);
        String taskJson = gson.toJson(task);

        uri = URI.create("http://localhost:8090/tasks/task/");
        response = client.send(requestBuilder
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .build(), handler);

        assertEquals(200, response.statusCode(), "Запрос не обработан");
        assertFalse(response.body().isEmpty(), "Задача не сохранена, не возвращен id");
        int id = Integer.parseInt(response.body());

        uri = URI.create("http://localhost:8090/tasks/delete/?id=" + id);
        response = client.send(requestBuilder
                .uri(uri)
                .DELETE()
                .build(), handler);

        uri = URI.create("http://localhost:8090/tasks/task/?id=" + id);
        response = client.send(requestBuilder
                .uri(uri)
                .GET()
                .build(), handler);

        assertEquals(404, response.statusCode(), "Запрос не обработан");
        assertTrue(response.body().isEmpty(), "Задача не удалена.");
    }


    //----------------------------------------------------------------------------------------------------
    static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        private final DateTimeFormatter formatterWriter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
            if (localDateTime == null) {
                jsonWriter.value("null");
            } else {
                jsonWriter.value(localDateTime.format(formatterWriter));
            }
        }
        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), formatterWriter);
        }
    }

    static class DurationAdapter extends TypeAdapter<Duration> {
        @Override
        public void write(JsonWriter jsonWriter, Duration duration) throws IOException {
            if (duration == null) {
                jsonWriter.value("null");
            } else {
                jsonWriter.value(duration.toMinutes());
            }
        }
        @Override
        public Duration read(JsonReader jsonReader) throws IOException {
            if (jsonReader.nextString() == null) {
                return Duration.ofMinutes(0);
            } else {
                return Duration.ofMinutes(Long.parseLong(jsonReader.nextString()));
            }
        }
    }
}
