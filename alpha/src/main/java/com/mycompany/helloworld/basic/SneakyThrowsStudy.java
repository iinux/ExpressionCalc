package com.mycompany.helloworld.basic;

import lombok.SneakyThrows;

import java.io.UnsupportedEncodingException;

public class SneakyThrowsStudy {
    @SneakyThrows({UnsupportedEncodingException.class})
    public String utf8ToString(byte[] bytes) {
        return new String(bytes, "UTF-8");
    }

    @SneakyThrows
    public void run() {
        throw new Throwable();
    }
}
