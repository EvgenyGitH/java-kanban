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
                .setPrettyPrinting()
                //.serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())

                .create();

    }


    public void TaskHandler (HttpExchange exchange) {
        try {
         //   String path = exchange.getRequestURI().getPath();
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
    /*            case "DELETE":{
                //    handleDELETE(exchange);
                    break;
                }*/
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

            if (Pattern.matches("^/tasks$", path)) {
                String response = gson.toJson(taskManager.getPrioritizedTasks());
                sendText(exchange, response);
                return;
            }

            String[] pathSplit = path.split("/");
            String typeTask = pathSplit[2];
            switch (typeTask) {
                case "task": {
                    if (Pattern.matches("^/tasks/task/\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/task/", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getTaskById(id));
                            sendText(exchange, response);
                            System.out.println("Запрос успешно выполнен.");
                            break;
                        }
                    }

                }
                case "epic": {
                    if (Pattern.matches("^/tasks/epic/\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/epic/", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getTaskById(id));
                            sendText(exchange, response);
                            System.out.println("Запрос успешно выполнен.");
                            break;
                        }
                    }
                }
                case "subtask": {
                    if (Pattern.matches("^/tasks/subtask/\\d+$", path)) {
                        String pathId = path.replaceFirst("/tasks/subtask/", "");
                        int id = parsePathId(pathId);
                        if (id != -1) {
                            String response = gson.toJson(taskManager.getTaskById(id));
                            sendText(exchange, response);
                            System.out.println("Запрос успешно выполнен.");
                            break;
                        }
                    }
                }
                case "history": {
                    List<Task> history = taskManager.getArrayHistory();
                    if (history.isEmpty()) {
                        System.out.println("Истории просмотров пуста");
                        exchange.sendResponseHeaders(204, 0);
                    }

                    String response = gson.toJson(taskManager.getArrayHistory());
                    sendText(exchange, response);
                    System.out.println("Запрос успешно выполнен.");
                    break;
                }


                default: {
                    /*System.out.println("/tasks/ ждёт GET, POST или DELETE запрос а получил: "
                            + exchange.getRequestMethod());*/
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
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

            String path = exchange.getRequestURI().getPath(); //toString();
            String[] splitPath = path.split("/");

            switch (splitPath[2]) {
                case "task":
                    System.out.println("\n/tasks/task/");
                    Task task = gson.fromJson(body, Task.class);
                    if (task.getIdTask() > 0) {
                        int idTask = task.getIdTask();
                        taskManager.updateById(idTask, task);
                        sendText(exchange, "Задача task - обновлена");
                    } else {
                        taskManager.saveTask(task);

                        sendText(exchange, "task - добавлена");
                    }
                    break;
                case "epic":
                    System.out.println("\n/tasks/epic/");
                    Epic epic = gson.fromJson(body, Epic.class);
                    if (epic.getIdTask() > 0) {
                        int idTask = epic.getIdTask();
                        taskManager.updateById(idTask, epic);
                        sendText(exchange, "Задача epic - обновлена");
                    } else {
                        taskManager.saveEpic(epic);
                        sendText(exchange, "epic - добавлена");
                    }
                    break;
                case "subtask":
                    System.out.println("\n/tasks/subtask/");
                    Subtask subtask = gson.fromJson(body, Subtask.class);
                    if (subtask.getIdTask() > 0) {
                        int idTask = subtask.getIdTask();
                        taskManager.updateById(idTask, subtask);
                        sendText(exchange, "Задача subtask - обновлена");
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

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

    private void writeResponse(HttpExchange exchange,
                               String responseString,
                               int responseCode) throws IOException {
        if(responseString.isBlank()) {
            exchange.sendResponseHeaders(responseCode, 0);
        } else {
            byte[] bytes = responseString.getBytes(DEFAULT_CHARSET);
            exchange.sendResponseHeaders(responseCode, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
        exchange.close();
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

      //  httpTaskServer.stop();
    }





    static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
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

    static class DurationAdapter extends TypeAdapter<Duration> {
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
