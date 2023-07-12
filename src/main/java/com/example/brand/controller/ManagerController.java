package com.example.brand.controller;

import com.example.brand.dto.LoginDto;
import com.example.brand.jwt.TokenInfo;
import com.example.brand.service.ManagerLoginService;
import com.example.brand.util.ResponseBody;
import com.example.brand.util.ResponseDto;
import com.example.brand.util.ResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerLoginService managerLoginService;

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
}
