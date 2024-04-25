package com.ressourcesrelationnelles.dto;

class LoginDetails{
    String username;
    int id;

    String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
}
public class ResponseLoginDto {
    private boolean success;
    private String message;

    private String token;
    private LoginDetails details;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginDetails getDetails() {
        return details;
    }

    public void setDetails(String username, Integer id, String role) {
        this.details = new LoginDetails();
        this.details.setUsername(username);
        this.details.setId(id);
        this.details.setRole(role);
    }

}