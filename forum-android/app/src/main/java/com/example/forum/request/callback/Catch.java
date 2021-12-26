package com.example.forum.request.callback;

import java.io.IOException;

@FunctionalInterface
public interface Catch extends ResponseResult {
    @Override
    default void thenResult(String resp) {
    }

    @Override
    void catchResult(IOException e);
}
