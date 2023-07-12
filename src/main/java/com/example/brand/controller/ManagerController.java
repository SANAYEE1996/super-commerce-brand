package com.example.brand.controller;

import com.example.brand.dto.LoginDto;
import com.example.brand.dto.ManagerDto;
import com.example.brand.entity.Manager;
import com.example.brand.jwt.TokenInfo;
import com.example.brand.service.ManagerLoginService;
import com.example.brand.service.ManagerService;
import com.example.brand.service.RoleService;
import com.example.brand.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerLoginService managerLoginService;

    private final ManagerService managerService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseDto login(@RequestBody LoginDto loginDto){
        try {
            TokenInfo token = managerLoginService.login(loginDto.getLoginEmail(), loginDto.getLoginPassword());
            return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("Login Success").body(new ResponseBody<>(token)).build();
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().code(ResponseStatus.EXCEPTION.getStatusCode()).message(e.getMessage()).build();
        }
    }

    @PostMapping("/register")
    public ResponseDto register(@RequestBody ManagerDto managerDto){
        try {
            Manager registerManager = Manager.builder()
                    .managerEmail(managerDto.getEmail())
                    .managerPassword(passwordEncoder.encode(managerDto.getPassword()))
                    .managerName(managerDto.getName())
                    .contactNumber(managerDto.getContactNumber())
                    .registerDate(LocalDateTime.now().format(TimeFormat.timeFormatter))
                    .build();
            managerService.saveManager(registerManager);
            Manager manager = managerService.findManager(managerDto.getEmail());
            roleService.addRole(manager.getId(), ManagerRole.NONE.getRole());
        }catch (RuntimeException e){
            log.error(e.getMessage());
            return ResponseDto.builder().code(ResponseStatus.EXCEPTION.getStatusCode()).message(e.getMessage()).build();
        }
        return ResponseDto.builder().code(ResponseStatus.OK.getStatusCode()).message("가입 성공").build();
    }
}
