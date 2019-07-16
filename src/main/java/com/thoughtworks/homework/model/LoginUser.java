package com.thoughtworks.homework.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser {

    private String email;
    private String password;
    private Boolean rememberMe;

    public LoginUser(String email, String password, Boolean rememberMe) {
        this.email = email;
        this.password = password;
        this.rememberMe = rememberMe;
    }
}
