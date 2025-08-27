package com.bookmyshow.dtos;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String role;
}

