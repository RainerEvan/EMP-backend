package com.emp.backend.mapper.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {
    public String username;
    public String password;
    public String role;
}
