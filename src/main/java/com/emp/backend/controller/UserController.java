package com.emp.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.backend.mapper.request.UserRequest;
import com.emp.backend.mapper.response.ResponseSchema;
import com.emp.backend.model.User;
import com.emp.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping
    public ResponseEntity<ResponseSchema<List<User>>> getAllUser() {
        ResponseSchema<List<User>> response = userService.getAllUser();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/register")
    public ResponseEntity<ResponseSchema<String>> insertUser(@RequestBody UserRequest userRequest) {
        ResponseSchema<String> response = userService.insertUser(userRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
