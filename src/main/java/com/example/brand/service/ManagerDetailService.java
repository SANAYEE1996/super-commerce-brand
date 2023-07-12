package com.example.brand.service;

import com.example.brand.entity.CustomerUserDetails;
import com.example.brand.entity.Manager;
import com.example.brand.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerDetailService implements UserDetailsService {

    private final ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("load User by User Name :: {} ", username);
        return managerRepository.findByManagerEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(()-> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    private UserDetails createUserDetails(Manager manager){
        return new CustomerUserDetails(manager);
    }
}
