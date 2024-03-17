package com.ressourcesrelationnelles.dto;

public class ResponseRegisterDto {
    private String username;

    private int id;

    private String token;

    private String message;

    private boolean success;

    public ResponseRegisterDto(String username, int id, String token, String message, boolean success) {
        this.username = username;
        this.id = id;
        this.token = token;
        this.message = message;
        this.success = success;
    }

    public ResponseRegisterDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
