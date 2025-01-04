package com.emp.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emp.backend.dao.UserDao;
import com.emp.backend.mapper.request.UserRequest;
import com.emp.backend.mapper.response.ResponseSchema;
import com.emp.backend.model.User;
import com.emp.backend.util.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    
    @Autowired
    public UserDao userDao;

    @Autowired
    public PasswordEncoder passwordEncoder;

    public ResponseSchema<List<User>> getAllUser(){
        ResponseSchema<List<User>> response = new ResponseSchema<>();

        try {
            List<User> users = userDao.getAllUser();
            response.setMessage(AppConstants.SUCCESS_MSG);
            response.setOutput(users);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.info(e.getMessage());
        }

        return response;
    }
    
    public ResponseSchema<String> insertUser(UserRequest userRequest){
        ResponseSchema<String> response = new ResponseSchema<>();

        try {
            User existingUser = userDao.getUserByUsername(userRequest.username);
            if(existingUser == null){
                userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
                int result = userDao.insertUser(userRequest);
                response.setMessage(AppConstants.SUCCESS_MSG);
                response.setOutput("User has been added successfully" + result);
            } else{
                response.setMessage(AppConstants.FAILED_MSG);
                response.setOutput("User has already existed");
            }
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.info(e.getMessage());
        }

        return response;
    }


}
