package client;

import manager.exception.RequestFailedException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private String url;
    private String apiToken;

    private HttpClient client;

    public KVTaskClient(int port) {
        url = "http://localhost:" + port + "/";
        apiToken = register(url);
        client = HttpClient.newHttpClient();
    }

    private String register(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "register"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new RequestFailedException("Невозможно сделать запрос на регистрацию, код статуса" + response.statusCode());
            }
        } catch (IOException | InterruptedException exception) {
            throw new RequestFailedException("Невозможно сделать запрос", exception);
        }
    }

    public void save(String key, String value) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "save/" + key + ">?API_TOKEN=" + apiToken))
                    .POST(HttpRequest.BodyPublishers.ofString(value))
                    .header("Accept", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RequestFailedException("Невозможно сохранить запрос, код статуса" + response.statusCode());
            }
        } catch (IOException | InterruptedException exception) {
            throw new RequestFailedException("Возникла проблема, проверьте текст запроса", exception);
        }
    }

    public String load(String key) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url + "load/" + key + ">?API_TOKEN=" + apiToken))
                    .GET()
                    .header("Accept", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new RequestFailedException("Невозможно выполнить загрузку, код статуса" + response.statusCode());
            }
        } catch (IOException | InterruptedException exception) {
            throw new RequestFailedException("Возникла проблема, проверьте текст запроса", exception);
        }
    }
}
