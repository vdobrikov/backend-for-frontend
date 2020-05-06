package com.vdobrikov;

import java.util.ResourceBundle;

public class Config {
    public static final String BUNDLE_NAME = "app";
    public static final String KEY_URL_AUTHOR = "url.author";
    public static final String KEY_URL_BOOK = "url.book";
    public static final String KEY_URL_WEBSOCKET = "url.websocket";

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

    public String get(String key) {
        return bundle.getString(key);
    }
}
