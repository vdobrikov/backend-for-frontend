package com.vdobrikov.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RestClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public RestClient(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public <T> List<T> listAll(String url, Class<T[]> clazz) {
        var req = createGetRequest(url);
        var resp = send(req);
        assertResponseCode(resp);
        T[] values = getValue(resp, clazz);
        return Arrays.asList(values);
    }

    public <T> Optional<T> getOne(String url, Class<T> clazz) {
        var req = createGetRequest(url);
        var resp = send(req);
        if (resp.statusCode() == 404) {
            return Optional.empty();
        }
        return Optional.of(getValue(resp, clazz));
    }

    private HttpRequest createGetRequest(String uri) {
        return createGetRequest(URI.create(uri));
    }

    private HttpRequest createGetRequest(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .header("Accept", "application/json")
                .GET()
                .build();
    }

    private HttpResponse<String> send(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }

    private <T> T getValue(HttpResponse<String> response, Class<T> clazz) {
        try {
            return objectMapper.readValue(response.body(), clazz);
        } catch (JsonProcessingException e) {
            throw new ClientException(e);
        }
    }

    private void assertResponseCode(HttpResponse<?> response) {
        if (response.statusCode() >= 400) {
            throw new ClientException("HTTP status code=" + response.statusCode());
        }
    }
}
