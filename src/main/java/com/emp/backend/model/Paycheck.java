package com.emp.backend.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paycheck {
    public String id;
    public String employeeId;
    public BigDecimal baseSalary;
    public BigDecimal transportationAllowance;
    public BigDecimal foodAllowance;
    public BigDecimal overtime;
    public BigDecimal totalGrossIncome;
    public BigDecimal bpjsKesehatan;
    public BigDecimal bpjsKetenagakerjaan;
    public BigDecimal tax;
    public BigDecimal otherCut;
    public BigDecimal totalDeduction;
    public BigDecimal totalNetIncome;
    public String fileName;
    public String fileBase64;
    public String dateLastSent;
    public BigDecimal countSent;
    public String createdAt;
    public String createdBy;
    public String updatedAt;
    public String updatedBy;
}
