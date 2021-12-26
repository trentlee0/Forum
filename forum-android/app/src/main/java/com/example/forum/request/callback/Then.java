package com.example.forum.request.callback;

import java.io.IOException;

@FunctionalInterface
public interface Then extends ResponseResult {
    @Override
    default void catchResult(IOException e) {
    }

    @Override
    void thenResult(String resp);
}
