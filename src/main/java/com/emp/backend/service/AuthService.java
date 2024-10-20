package com.emp.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.emp.backend.mapper.request.AuthRequest;
import com.emp.backend.mapper.response.AuthResponse;
import com.emp.backend.mapper.response.ResponseSchema;
import com.emp.backend.security.detail.CustomUserDetails;
import com.emp.backend.security.jwt.JwtTokenUtil;
import com.emp.backend.util.AppConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public ResponseSchema<AuthResponse> login(AuthRequest authRequest){
        ResponseSchema<AuthResponse> response = new ResponseSchema<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(userDetails.getUsername());
            
            String role = userDetails.getAuthorities().iterator().next().getAuthority();

            AuthResponse auth = new AuthResponse(token, userDetails.getUsername(), role);
            response.setMessage(AppConstants.SUCCESS_MSG);
            response.setOutput(auth);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.info(e.getMessage());
        }

        return response;
    }
}
