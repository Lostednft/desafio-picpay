package com.challenge.picpay.models;

public enum UserRole {

    USUARIO("usuario"),
    LOJISTA("lojista");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
