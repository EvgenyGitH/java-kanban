package server;

import client.KVClientException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final String apiToken;
    private final HttpClient taskClient;
    private final String uriServer;

    public KVTaskClient(String str) {
        uriServer = str;
        taskClient = HttpClient.newHttpClient();
        apiToken = register();
    }

    public String register() {
        try {
            URI uri = URI.create(uriServer + "/register");
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(uri)
                    .build();
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = taskClient.send(request, handler);
            if (response.statusCode() != 200) {
                throw new KVClientException("Регистрация не удалась. " +
                        "Проверьте, пожалуйста, запрос и повторите попытку.");
            }
            return response.body();
        } catch (InterruptedException | IOException ignored) {
            throw new KVClientException("Регистрация не удалась. " +
                    "Проверьте, пожалуйста, запрос и повторите попытку.");
        }
    }

    public void put(String key, String json) {
        try {
            URI uri = URI.create(uriServer + "/save/" + key + "?API_TOKEN=" + apiToken);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> response = taskClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new KVClientException("Сохранение не выполнено. " +
                        "Проверьте, пожалуйста, запрос и повторите попытку.");
            }
            System.out.println("Код ответа: " + response.statusCode());
        } catch (InterruptedException | IOException ignored) {
            throw new KVClientException("Проверьте, пожалуйста, запрос и повторите попытку.");
        }
    }

    public String load(String key) {
        try {
            URI uri = URI.create(uriServer + "/load/" + key + "?API_TOKEN=" + apiToken);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = taskClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new KVClientException("Загрузка не выполнена. " +
                        "Проверьте, пожалуйста, запрос и повторите попытку.");
            }
            System.out.println("Код ответа: " + response.statusCode());
            return response.body();
        } catch (InterruptedException | IOException ignored) {
            throw new KVClientException("Проверьте, пожалуйста, запрос и повторите попытку.");
        }
    }

}
