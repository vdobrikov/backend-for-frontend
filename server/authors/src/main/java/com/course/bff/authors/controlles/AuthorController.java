package com.course.bff.authors.controlles;

import com.course.bff.authors.models.Author;
import com.course.bff.authors.requests.CreateAuthorCommand;
import com.course.bff.authors.responses.AuthorResponse;
import com.course.bff.authors.services.AuthorService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.Response;
import org.asynchttpclient.util.HttpConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {
    private final static Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping()
    public Collection<AuthorResponse> getAuthors() {
        logger.info("Get authors");
        List<AuthorResponse> authorResponses = new ArrayList<>();
        this.authorService.getAuthors().forEach(author -> {
            AuthorResponse authorResponse = createAuthorResponse(author);
            authorResponses.add(authorResponse);
        });

        return authorResponses;
    }

    @GetMapping("/{id}")
    public AuthorResponse getById(@PathVariable UUID id) {
        logger.info(String.format("Find authors by %s", id));
        Optional<Author> authorSearch = this.authorService.findById(id);
        if (authorSearch.isEmpty()) {
            throw new RuntimeException("Author isn't found");
        }

        return createAuthorResponse(authorSearch.get());
    }

    @PostMapping()
    public AuthorResponse createAuthors(@RequestBody CreateAuthorCommand createAuthorCommand) {
        logger.info("Create authors");
        Author author = this.authorService.create(createAuthorCommand);
        AuthorResponse authorResponse = createAuthorResponse(author);
        this.sendPushNotification(authorResponse);
        return authorResponse;
    }


    private void sendPushNotification(AuthorResponse authorResponse) {
        DefaultAsyncHttpClientConfig.Builder clientBuilder = Dsl.config().setConnectTimeout(500);
        AsyncHttpClient client = Dsl.asyncHttpClient(clientBuilder);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Request socketRequest = new RequestBuilder(HttpConstants.Methods.POST)
                .setUrl("http://bff-web-sockets-service:8080/api/v1/websocket")
                .setBody(gson.toJson(authorResponse))
                .build();

        ListenableFuture<Response> socketFuture = client.executeRequest(socketRequest);
        try {
            socketFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Push Notification Error", e);
        }
    }

    private AuthorResponse createAuthorResponse(Author author) {
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setId(author.getId());
        authorResponse.setFirstName(author.getFirstName());
        authorResponse.setLastName(author.getLastName());
        authorResponse.setAddress(author.getAddress());
        authorResponse.setLanguage(author.getLanguage());
        return authorResponse;
    }
}
