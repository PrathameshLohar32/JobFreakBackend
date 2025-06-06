package com.JobAppBackend.JobFreakBackend.dtos;

import java.util.List;

public class LoginResponse {
    private String jwtToken;

    private String username;

    public LoginResponse(String username, String jwtToken) {
        this.username = username;
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
