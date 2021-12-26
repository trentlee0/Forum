package com.example.forum.model.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class ImageDTO {
    private String alt;
    private String href;
    private String url;
}
