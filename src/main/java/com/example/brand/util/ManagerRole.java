package com.example.brand.util;

import lombok.Getter;

@Getter
public enum ManagerRole {

    NONE("NONE"),
    BRAND_MASTER("BRAND_MASTER"),
    BRAND_MANAGER("BRAND_MANAGER");

    private final String role;

    ManagerRole(String role) {
        this.role = role;
    }
}
