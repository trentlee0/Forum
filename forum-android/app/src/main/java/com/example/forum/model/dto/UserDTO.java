package com.example.forum.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class UserDTO {
    private String avatar;
    private Long uid;
    private String uname;
    private String ubirthday;
    private String ugender;
    private String ugrade;
}
