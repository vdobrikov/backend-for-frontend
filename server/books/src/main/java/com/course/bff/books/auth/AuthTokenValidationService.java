package com.course.bff.books.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static java.net.http.HttpRequest.BodyPublishers.ofString;
import static java.net.http.HttpResponse.BodyHandlers.discarding;

@Component
public class AuthTokenValidationService {
    private final String url;
    private final HttpClient httpClient;

    public AuthTokenValidationService(@Value("${url.auth}") String url, HttpClient httpClient) {
        this.url = url;
        this.httpClient = httpClient;
    }

    public boolean validateTokenSilently(String token) {
        try {
            return validateToken(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateToken(String token) throws IOException, InterruptedException {
        var req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-type", "text/plain")
                .POST(ofString(token))
                .build();
        var resp = httpClient.send(req, discarding());
        return resp.statusCode() == 200;
    }
}
