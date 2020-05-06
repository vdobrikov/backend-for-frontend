package com.vdobrikov.service;

import com.vdobrikov.utils.RestClient;

import com.vdobrikov.model.Book;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class BookService {
    private final String url;
    private final RestClient restClient;

    public BookService(String url, RestClient restClient) {
        this.url = Objects.requireNonNull(url);
        this.restClient = Objects.requireNonNull(restClient);
    }

    public Collection<Book> list() {
        return restClient.listAll(url, Book[].class);
    }

    public Optional<Book> get(String id) {
        Objects.requireNonNull(id);
        return restClient.getOne(url + "/" + id, Book.class);
    }
}
