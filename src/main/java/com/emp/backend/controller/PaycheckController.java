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
import org.springframework.web.bind.annotation.RestController;

import com.emp.backend.mapper.response.ResponseSchema;
import com.emp.backend.model.Paycheck;
import com.emp.backend.service.PaycheckService;
import com.emp.backend.util.AppConstants;

@RestController
@RequestMapping("/paycheck")
public class PaycheckController {
    @Autowired
    private PaycheckService paycheckService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<ResponseSchema<List<Paycheck>>> getAllPaychecksByEmployeeId(@PathVariable String employeeId) {
        ResponseSchema<List<Paycheck>> response = paycheckService.getAllPaychecksByEmployeeId(employeeId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseSchema<Paycheck>> getPaycheckById(@PathVariable String id) {
        ResponseSchema<Paycheck> response = paycheckService.getPaycheckById(id);
        if (response.getOutput() != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<ResponseSchema<Paycheck>> getPaycheckReport(@PathVariable String id) {
        ResponseSchema<Paycheck> response = paycheckService.getPaycheckReport(id);
        if (response.getOutput() != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseSchema<Void>> addPaycheck(@RequestBody Paycheck paycheck) {
        ResponseSchema<Void> response = paycheckService.addPaycheck(paycheck);
        if (response.getMessage().equals(AppConstants.SUCCESS_MSG)) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseSchema<Void>> updatePaycheck(@PathVariable String id, @RequestBody Paycheck paycheck) {
        paycheck.setId(id);
        ResponseSchema<Void> response = paycheckService.updatePaycheck(paycheck);
        if (response.getMessage().equals(AppConstants.SUCCESS_MSG)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseSchema<Void>> deletePaycheck(@PathVariable String id) {
        ResponseSchema<Void> response = paycheckService.deletePaycheck(id);
        if (response.getMessage().equals(AppConstants.SUCCESS_MSG)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
