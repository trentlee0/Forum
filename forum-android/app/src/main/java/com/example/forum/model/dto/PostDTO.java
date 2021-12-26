package com.example.forum.model.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostDTO {
    private long postId;
    private long userId;
    private String username;
    private String avatar;
    private long plateId;
    private String postName;
    private String createDatetime;
    private String content;
    private String updateDatetime;
    private long approvePostCount;
    private List<CommentDTO> comments;
}
