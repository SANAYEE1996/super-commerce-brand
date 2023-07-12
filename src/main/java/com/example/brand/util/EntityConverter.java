package com.example.brand.util;

import com.example.brand.dto.BrandRegisterDto;
import com.example.brand.entity.Brand;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EntityConverter {

    public Brand toBrandEntity(BrandRegisterDto dto, String logo){
        return new Brand(0L, dto.getBrandName(), dto.getBrandIntro(), dto.getAddress(), logo, LocalDateTime.now().format(TimeFormat.timeFormatter));
    }
}
