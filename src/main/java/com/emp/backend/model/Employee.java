package com.emp.backend.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    public String id;
    public String fullName;
    public String birthPlace;
    public String birthDate;
    public String phoneNumber;
    public String email;
    public String nik;
    public String employeeId;
    public String position;
    public String department;
    public String dateStart;
    public String dateEnd;
    public BigDecimal baseSalary;
    public String bankAccount;
    public String bankAccountNum;
    public String createdAt;
    public String createdBy;
    public String updatedAt;
    public String updatedBy;
    public Boolean isActive;
}
