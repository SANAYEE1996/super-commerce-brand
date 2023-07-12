package com.example.brand.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BrandRegisterDto {

    private String managerEmail;

    private String brandName;

    private String brandIntro;

    private String address;

    public BrandRegisterDto(String managerEmail, String brandName, String brandIntro, String address) {
        this.managerEmail = managerEmail;
        this.brandName = brandName;
        this.brandIntro = brandIntro;
        this.address = address;
    }
}
