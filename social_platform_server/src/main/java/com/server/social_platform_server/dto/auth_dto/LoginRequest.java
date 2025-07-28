package com.server.social_platform_server.dto.auth_dto;

public class LoginRequest {
    String email;
    String password;

    public LoginRequest(String e, String p){
        this.email = e;
        this.password = p;
    }

    public LoginRequest(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
