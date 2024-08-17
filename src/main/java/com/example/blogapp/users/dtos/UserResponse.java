package com.example.blogapp.users.dtos;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String image;
    private String bio;
    private String token;
}
