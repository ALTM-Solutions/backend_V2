package com.ressourcesrelationnelles.dto;

public class ResetPasswordDto {

    private String email;

    public ResetPasswordDto(String email) {
        this.email = email;
    }

    public ResetPasswordDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
