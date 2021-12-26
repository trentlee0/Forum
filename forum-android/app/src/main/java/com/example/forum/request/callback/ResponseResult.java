package com.example.forum.request.callback;

import java.io.IOException;

public interface ResponseResult {
    void thenResult(String resp);

    void catchResult(IOException e);
}
