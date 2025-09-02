package com.server.social_platform_server.dto.auth_dto;

public class RegisterRequest {
    private String username;
    private String email;
    private String password;

    public RegisterRequest(String u, String e, String p){
        this.username = u;
        this.email = e;
        this.password = p;
    }

    public RegisterRequest(){}

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
