package com.example.blogapp.common.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String message;
}
