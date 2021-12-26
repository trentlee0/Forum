package com.example.forum.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDTO {
    private long commentId;
    private String text;
    private long postId;
    private long userId;
    private String username;
    private String avatar;
    private String publishDatetime;
}
