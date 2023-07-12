package com.example.brand.service;

import com.example.brand.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public void addRole(Long managerId, String role){
        roleRepository.addRole(managerId, role);
    }

    public void updateRole(Long managerId, String role){
        roleRepository.updateRole(managerId, role);
    }
}
