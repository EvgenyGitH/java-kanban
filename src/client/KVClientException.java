package client;

public class KVClientException extends RuntimeException {

    public KVClientException(String message) {
        super(message);
    }

    public String getDetailMessage() {
        return "Ошибка выполнения запроса: " + getMessage();
    }

}