package com.vdobrikov;

import java.util.ResourceBundle;

public class Config {
    public static final String BUNDLE_NAME = "app";
    public static final String KEY_URL_AUTHOR = "url.author";
    public static final String KEY_URL_BOOK = "url.book";
    public static final String KEY_URL_WEBSOCKET = "url.websocket";
    public static final String KEY_AUTH_TOKEN = "auth.token";
    public static final String KEY_SYSTEM_NAME = "system.name";

    private final ResourceBundle bundle;

    public Config() {
        bundle = ResourceBundle.getBundle(BUNDLE_NAME);
    }

    public String getAuthorUrl() {
        return get(KEY_URL_AUTHOR);
    }

    public String getBookUrl() {
        return get(KEY_URL_BOOK);
    }

    public String getWebsocketUrl() {
        return get(KEY_URL_WEBSOCKET);
    }

    public String getAuthToken() {
        return get(KEY_AUTH_TOKEN);
    }

    public String getSystemName() {
        return get(KEY_SYSTEM_NAME);
    }

    public String get(String key) {
        return bundle.getString(key);
    }
}
