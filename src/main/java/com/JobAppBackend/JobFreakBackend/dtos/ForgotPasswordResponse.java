package com.JobAppBackend.JobFreakBackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordResponse {
    private String username;
    private String token;
    private String message;
}
