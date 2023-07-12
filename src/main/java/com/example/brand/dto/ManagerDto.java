package com.example.brand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ManagerDto {

    private String email;

    private String password;

    private String name;

    private String contactNumber;

    private String registerDate;

    public ManagerDto(String email, String password, String name, String contactNumber, String registerDate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.contactNumber = contactNumber;
        this.registerDate = registerDate;
    }
}
