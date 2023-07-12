package com.example.brand.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "manager")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    private String managerEmail;

    @Column(name = "password")
    private String managerPassword;

    @Column(name = "name")
    private String managerName;

    @Column(name = "tel_number")
    private String contactNumber;

    @Column(name = "register_date")
    private String registerDate;

    @Column(name = "modify_date")
    private String modifyDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public void registerBrand(Brand brand){
        this.brand = brand;
    }
}
