package com.emp.backend.mapper.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String message;
    private String jwtToken;
    private Date expirationDate;
    private String username;
    private String role;
}
