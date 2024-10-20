package com.emp.backend.mapper.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSchema<T> {
    public String message;
    public T output;
}
