package com.ressourcesrelationnelles.dto;

public class ValidationRegisterDto {
    private String adresseMail;

    private String code;

    public ValidationRegisterDto() {
    }

    public ValidationRegisterDto(String adresseMail, String code) {
        this.adresseMail = adresseMail;
        this.code = code;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
