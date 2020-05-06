package com.vdobrikov.service;


import com.vdobrikov.model.Author;
import com.vdobrikov.utils.RestClient;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class AuthorService {
    private final String url;
    private final RestClient restClient;

    public AuthorService(String url, RestClient restClient) {
        this.url = Objects.requireNonNull(url);
        this.restClient = Objects.requireNonNull(restClient);
    }

    public Collection<Author> list() {
        return restClient.listAll(url, Author[].class);
    }

    public Optional<Author> get(UUID id) {
        Objects.requireNonNull(id);
        return get(id.toString());
    }

    public Optional<Author> get(String id) {
        Objects.requireNonNull(id);
        return restClient.getOne(url + "/" + id, Author.class);
    }
}
