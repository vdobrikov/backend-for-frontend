package com.vdobrikov;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdobrikov.model.Author;
import com.vdobrikov.model.Book;
import com.vdobrikov.service.AuthorService;
import com.vdobrikov.service.BookService;
import com.vdobrikov.service.OutputService;
import com.vdobrikov.utils.ClientException;
import com.vdobrikov.utils.HttpClientProvider;
import com.vdobrikov.utils.RestClient;
import com.vdobrikov.websocket.StompWebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Scanner;

public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) throws ClientException {
        LOG.info("Starting");

        var outputService = new OutputService();
        var config = new Config();

        var httpClient = HttpClientProvider.createHttpClient(config.getSystemName(), config.getAuthToken().toCharArray());
        var restClient = new RestClient(httpClient, new ObjectMapper());

        var authorService = new AuthorService(config.getAuthorUrl(), restClient);
        var bookService = new BookService(config.getBookUrl(), restClient);
        var stompSebSocketClient = new StompWebSocketClient(config.getWebsocketUrl(), outputService::out);

        for (Book book : bookService.list()) {
            Optional<Author> maybeAuthor = authorService.get(book.getAuthorId());
            outputService.out("Book: " + book +
                    "\n\tAuthor: " + maybeAuthor.map(Author::toString).orElse(book.getAuthorId().toString()));
        }

        stompSebSocketClient.connect();

        new Scanner(System.in).nextLine(); // Don't close immediately.
        LOG.info("Shutdown");
    }
}
