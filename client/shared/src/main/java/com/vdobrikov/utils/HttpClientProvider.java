package com.vdobrikov.utils;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.http.HttpClient;
import java.time.Duration;

public class HttpClientProvider {
    public static HttpClient createHttpClient(String clientName, char[] authToken) {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(clientName, authToken);
                    }
                })
                .build();
    }
}
