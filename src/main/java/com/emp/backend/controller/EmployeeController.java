package com.emp.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.emp.backend.mapper.response.ResponseSchema;
import com.emp.backend.model.Employee;
import com.emp.backend.service.EmployeeService;
import com.emp.backend.util.AppConstants;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<ResponseSchema<List<Employee>>> getAllEmployees() {
        ResponseSchema<List<Employee>> response = employeeService.getAllEmployees();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<ResponseSchema<Employee>> getEmployeeById(@PathVariable String employeeId) {
        ResponseSchema<Employee> response = employeeService.getEmployeeByEmployeeId(employeeId);
        if (response.getOutput() != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseSchema<Void>> addEmployee(@RequestBody Employee employee) {
        ResponseSchema<Void> response = employeeService.addEmployee(employee);
        if (response.getMessage().equals(AppConstants.SUCCESS_MSG)) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<ResponseSchema<Void>> updateEmployee(@PathVariable String employeeId, @RequestBody Employee employee) {
        employee.setEmployeeId(employeeId);
        ResponseSchema<Void> response = employeeService.updateEmployee(employee);
        if (response.getMessage().equals(AppConstants.SUCCESS_MSG)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/profile-image/{employeeId}")
    public ResponseEntity<ResponseSchema<Void>> editAccount(@RequestPart(name = "image", required = true) MultipartFile image, @PathVariable String employeeId){
        ResponseSchema<Void> response = employeeService.updateEmployeeProfileImage(image, employeeId);
        if (response.getMessage().equals(AppConstants.SUCCESS_MSG)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<ResponseSchema<Void>> deleteEmployee(@PathVariable String employeeId) {
        ResponseSchema<Void> response = employeeService.deleteEmployee(employeeId);
        if (response.getMessage().equals(AppConstants.SUCCESS_MSG)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    
}
