package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Handler;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {

    private static final int PORT = 8090;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final HttpServer httpServer;
    private final TaskManager taskManager;
    private final Gson gson;


    public HttpTaskServer () throws IOException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT),0);
        httpServer.createContext("/tasks", this::TaskHandler);
        taskManager = Managers.getDefault();
        gson = new GsonBuilder()
             //   .setPrettyPrinting()
              //  .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())

                .create();

    }


    public void TaskHandler (HttpExchange exchange) {
        try {
            String requestMethod = exchange.getRequestMethod();

            switch (requestMethod){
                case "GET":{
                    handleGET(exchange);
                    break;
                }
                case "POST":{
                    handlePOST(exchange);
                    break;
                }
                case "DELETE":{
                    handleDELETE(exchange);
                    break;
                }
                default: {
                    System.out.println("/tasks/ ждёт GET, POST или DELETE запрос а получил: "
                            + exchange.getRequestMethod());
                    exchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception exception){
            exception.printStackTrace();
        } finally {
            exchange.close();
        }
    }


    public void handleGET (HttpExchange exchange) {
        try {
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();

            if (Pattern.matches("^/tasks$", path)) {
                String response = gson.toJson(taskManager.printAllTask());
                sendText(exchange, response);
                return;
            }

            String[] pathSplit = path.split("/");
            String typeTask = pathSplit[2];
            switch (typeTask) {
                case "task": {
                    if (Pattern.matches("^id=\\d+$", query)) {
                        String pathId = query.replaceFirst("id=", "");
                        int id = parsePathId(pathId);
                        if (id != -1 && taskManager.getTaskById(id)!=null) {
                            String response = gson.toJson(taskManager.getTaskById(id));
                            sendText(exchange, response);
                            System.out.println("Запрос успешно выполнен.");
                            break;
                        }else {
                            System.out.println("Задачи по указанному id не существует");
                            exchange.sendResponseHeaders(404, 0);
                            break;
                        }

                    }

                }
                case "epic": {
                    if (Pattern.matches("^id=\\d+$", query)) {
                        String pathId = query.replaceFirst("id=", "");
                        int id = parsePathId(pathId);
                        if (id != -1 && taskManager.getTaskById(id)!=null) {
                            String response = gson.toJson(taskManager.getTaskById(id));
                            sendText(exchange, response);
                            System.out.println("Запрос успешно выполнен.");
                            break;
                        }else {
                            System.out.println("Задачи по указанному id не существует");
                            exchange.sendResponseHeaders(404, 0);
                            break;
                        }
                    }
                }
                case "subtask": {
                    if (Pattern.matches("^id=\\d+$", query)) {
                        String pathId = query.replaceFirst("id=", "");
                        int id = parsePathId(pathId);
                        if (id != -1 && taskManager.getTaskById(id)!=null) {
                            String response = gson.toJson(taskManager.getTaskById(id));
                            sendText(exchange, response);
                            System.out.println("Запрос успешно выполнен.");
                            break;
                        }else {
                            System.out.println("Задачи по указанному id не существует");
                            exchange.sendResponseHeaders(404, 0);
                            break;
                        }
                    }
                }
                case "history": {
                    if (Pattern.matches("^/tasks/history$", path)) {
                        List<Task> history = taskManager.getArrayHistory();
                        if (history.isEmpty()) {
                            System.out.println("Истории просмотров пуста");
                            String response = "Истории просмотров пуста";
                            sendText(exchange, response);
                            break;
                        } else {
                            String response = gson.toJson(taskManager.getArrayHistory());
                            sendText(exchange, response);
                            System.out.println("Запрос успешно выполнен.");
                            break;
                        }
                    }
                }
                default: {
                    exchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception exception){
            exception.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    public void handlePOST (HttpExchange exchange) {
        try {
            InputStream inputStream = exchange.getRequestBody();
            String query = exchange.getRequestURI().getQuery();

            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

            String path = exchange.getRequestURI().getPath();
            String[] splitPath = path.split("/");

            switch (splitPath[2]) {
                case "task":

                    System.out.println("\n/tasks/task/");
                    Task task = gson.fromJson(body, Task.class);

                    if (query !=null){
                        if (Pattern.matches("^id=\\d+$", query)) {
                            String pathId = query.replaceFirst("id=", "");
                            int id = parsePathId(pathId);
                            if (id != -1 && id>0) {
                                taskManager.updateById(id, task);
                                sendText(exchange, String.valueOf(id)); //"Задача task - обновлена"
                            }
                    }

                    } else {
                        taskManager.saveTask(task);
                        sendText(exchange, String.valueOf(task.getIdTask()) ); //"task - добавлена"
                    }
                    break;
                case "epic":
                    System.out.println("\n/tasks/epic/");
                    Epic epic = gson.fromJson(body, Epic.class);
                        if (query !=null){
                            if (Pattern.matches("^id=\\d+$", query)) {
                                String pathId = query.replaceFirst("id=", "");
                                int id = parsePathId(pathId);
                                if (id != -1 && id>0) {
                                    taskManager.updateById(id, epic);
                                    sendText(exchange, "Задача epic - обновлена");
                                }
                            }
                    } else {
                        taskManager.saveEpic(epic);
                        sendText(exchange, "epic - добавлена");
                    }
                    break;
                case "subtask":
                    System.out.println("\n/tasks/subtask/");
                    Subtask subtask = gson.fromJson(body, Subtask.class);

                        if (query !=null){
                            if (Pattern.matches("^id=\\d+$", query)) {
                                String pathId = query.replaceFirst("id=", "");
                                int id = parsePathId(pathId);
                                if (id != -1 && id>0) {
                                    taskManager.updateById(id, subtask);
                                    sendText(exchange, "Задача subtask - обновлена");
                                }
                            }



                    } else {
                        taskManager.saveSubtask(subtask);
                        sendText(exchange, "subtask - добавлена");
                    }
                    break;
            }

        } catch (Exception exception){
            exception.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    public void handleDELETE (HttpExchange exchange) {
        try {
            String path = exchange.getRequestURI().getPath();
            String query = exchange.getRequestURI().getQuery();

            if (Pattern.matches("^/tasks/delete$", path)) {
                taskManager.removeAllTask();
                String response = "Все задачи удалены";
                sendText(exchange, response);
                return;
            }
            if (Pattern.matches("^id=\\d+$", query)) {
                String pathId = query.replaceFirst("id=", "");
                int id = parsePathId(pathId);
                if (id != -1) {
                    taskManager.removeTaskById(id);
                    String response = "Задача с номером ID: " + id + " удалена";
                    sendText(exchange, response);
                    System.out.println("Запрос успешно выполнен.");

                } else {
                    String response = "Задача с номером ID: " + id + " отсутствует";
                    sendText(exchange, response);
                }
            }
        } catch (Exception exception){
            exception.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    private int parsePathId (String pathId){
        try{
            return Integer.parseInt(pathId);
        }catch(NumberFormatException exception){
            return -1;
        }
    }

    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text ) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

    public void start() {
        System.out.println("\nЗапускаем HttpTaskServer на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        httpServer.start();
    }
    public void stop(){
        System.out.println("Cервер на порту " + PORT + "остановлен.");
        httpServer.stop(0);
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
        httpTaskServer.stop();
    }

    public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        private final DateTimeFormatter formatterWriter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
            if (localDateTime == null){
                jsonWriter.value("null");
            }else {
                jsonWriter.value(localDateTime.format(formatterWriter));
            }
        }

        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), formatterWriter);
        }
    }

    public class DurationAdapter extends TypeAdapter<Duration> {
        @Override
        public void write(JsonWriter jsonWriter, Duration duration) throws IOException {
            if (duration == null){
                jsonWriter.value("null");
            }else {
                jsonWriter.value(duration.toMinutes());
            }
        }

        @Override
        public Duration read(JsonReader jsonReader) throws IOException {
            return Duration.ofMinutes(Long.parseLong(jsonReader.nextString()));
        }
    }


}
