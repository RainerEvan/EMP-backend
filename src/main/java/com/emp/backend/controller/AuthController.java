package com.emp.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.backend.mapper.request.AuthRequest;
import com.emp.backend.mapper.response.AuthResponse;
import com.emp.backend.mapper.response.ResponseSchema;
import com.emp.backend.service.AuthService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    @Autowired
    public AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseSchema<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        log.info(authRequest.toString());
        
        ResponseSchema<AuthResponse> response = authService.login(authRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
