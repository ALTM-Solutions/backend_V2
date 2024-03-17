package com.ressourcesrelationnelles.model;

public enum UserType {

    ADMIN("ADMIN"), CITIZEN("CITIZEN");

    private final String type;

    UserType(String string){
        type = string;
    }

    @Override
    public String toString(){
        return type;
    }

}
