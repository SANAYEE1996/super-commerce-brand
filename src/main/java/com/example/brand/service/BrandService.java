package com.example.brand.service;

import com.example.brand.entity.Brand;
import com.example.brand.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public Long registerBrand(Brand brand){
        return brandRepository.save(brand).getId();
    }

    public Brand findBrand(Long id){
        return brandRepository.findById(id).orElseThrow(()->new RuntimeException(id+" : 이 아이디로 등록된 브랜드가 아닙니다."));
    }

    public void deleteBrand(Long id){
        brandRepository.deleteById(id);
    }
}
