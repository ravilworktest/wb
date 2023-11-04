package org.example.pageLoader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class Client {
    HttpClient client = HttpClient.newHttpClient();

    public HttpResponse<String> send(String url) {
        HttpResponse<String> response = null;
        int repeatCount = 1;

        for (int i = 0; i < repeatCount; i++) {
            response = sendRequest(url);
            if (response.statusCode() != 200) {
                System.out.println("Request: " + url);
                System.out.println("HTTP status: " + response.statusCode());
                System.out.println("Response data: " + response.body());
            } else {
                break;
            }
        }

        return response;
    }

    public HttpResponse<String> sendRequest(String url) {
        var request = HttpRequest.newBuilder(URI.create(url))
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept-Encoding", "deflate")
                .header("Accept-Language", "en-US,en;q=0.5")
                .header("Sec-Fetch-Dest", "document")
                .header("Sec-Fetch-Mode", "navigate")
                .header("Sec-Fetch-Site", "none")
                .header("Sec-Fetch-User", "?1")
                .header("Upgrade-Insecure-Requests", "1")
                .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/115.0")
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Accept", "*/*")
                .header("Origin", "https://www.wildberries.ru")
                .header("Referer", "https://www.wildberries.ru/")
                .timeout(Duration.of(10, SECONDS))
                .build();

        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
