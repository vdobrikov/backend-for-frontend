package com.vdobrikov.service;

import java.util.Objects;

public class OutputService {

    public void out(Object object) {
        out(Objects.toString(object));
    }

    public void out(String msg) {
        System.out.println(msg);
    }
}
