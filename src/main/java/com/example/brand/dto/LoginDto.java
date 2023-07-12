package com.example.brand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginDto {

    private String loginEmail;

    private String loginPassword;

    public LoginDto(String loginEmail, String loginPassword) {
        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;
    }
}
