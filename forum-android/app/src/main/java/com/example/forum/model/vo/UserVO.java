package com.example.forum.model.vo;

import com.example.forum.model.pojo.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private User user;
    private boolean isLogin;
    private String token;
}
