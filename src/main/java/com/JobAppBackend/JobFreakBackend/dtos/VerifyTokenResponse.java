package com.JobAppBackend.JobFreakBackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTokenResponse {
    private String message;
    private boolean success;
    private String token;
}
