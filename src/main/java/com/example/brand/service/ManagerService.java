package com.example.brand.service;

import com.example.brand.entity.Brand;
import com.example.brand.entity.Manager;
import com.example.brand.repository.ManagerRepository;
import com.example.brand.util.exception.DuplicateBrandManagerException;
import com.example.brand.util.exception.ManagerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;

    public void saveManager(Manager registerManager) throws RuntimeException{
        if(managerRepository.existsByManagerEmail(registerManager.getManagerEmail())){
            throw new RuntimeException(registerManager.getManagerEmail() + " : 등록된 이메일 입니다.");
        }
        managerRepository.save(registerManager);
    }

    public void registerBrand(Manager manager, Brand brand) throws ManagerException {
        if(manager.getBrand() != null){
            throw new DuplicateBrandManagerException(brand.getId());
        }
        manager.registerBrand(brand);
        updateManager(manager);
    }


    public Manager findManager(String email){
        return managerRepository.findByManagerEmail(email).orElseThrow(()-> new RuntimeException(email + " : 등록된 사용자의 이메일이 아닙니다."));
    }

    private void updateManager(Manager updateManager){
        managerRepository.save(updateManager);
    }
}
