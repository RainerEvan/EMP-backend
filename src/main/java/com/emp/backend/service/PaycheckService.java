package com.emp.backend.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.backend.dao.PaycheckDao;
import com.emp.backend.mapper.response.ResponseSchema;
import com.emp.backend.model.Paycheck;
import com.emp.backend.util.AppConstants;

@Service
public class PaycheckService {
    private static final Logger log = LoggerFactory.getLogger(PaycheckService.class);

    @Autowired
    private PaycheckDao paycheckDao;

    public ResponseSchema<List<Paycheck>> getAllPaychecksByEmployeeId(String employeeId) {
        ResponseSchema<List<Paycheck>> response = new ResponseSchema<>();
        try {
            List<Paycheck> paychecks = paycheckDao.getAllPaychecksByEmployeeId(employeeId);
            response.setMessage(AppConstants.SUCCESS_MSG);
            response.setOutput(paychecks);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Error retrieving paychecks: {}", e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<Paycheck> getPaycheckById(String id) {
        ResponseSchema<Paycheck> response = new ResponseSchema<>();
        try {
            Paycheck paycheck = paycheckDao.getPaycheckById(id);
            if (paycheck != null) {
                response.setMessage(AppConstants.SUCCESS_MSG);
                response.setOutput(paycheck);
            } else {
                response.setMessage("Paycheck not found");
            }
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Error retrieving paycheck with ID {}: {}", id, e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<Paycheck> getPaycheckReport(String id) {
        ResponseSchema<Paycheck> response = new ResponseSchema<>();
        try {
            Paycheck paycheck = paycheckDao.getPaycheckById(id);
            if (paycheck != null) {
                response.setMessage(AppConstants.SUCCESS_MSG);
                response.setOutput(paycheck);
            } else {
                response.setMessage("Paycheck not found");
            }
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Error retrieving paycheck with ID {}: {}", id, e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<Void> addPaycheck(Paycheck paycheck) {
        ResponseSchema<Void> response = new ResponseSchema<>();
        try {
            String fileName = generateFileName(paycheck.getEmployeeId());
            paycheck.setFileName(fileName);
            paycheckDao.insertPaycheck(paycheck);
            response.setMessage(AppConstants.SUCCESS_MSG);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Failed to add paycheck: {}", e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<Void> updatePaycheck(Paycheck paycheck) {
        ResponseSchema<Void> response = new ResponseSchema<>();
        try {
            paycheckDao.updatePaycheck(paycheck);
            response.setMessage(AppConstants.SUCCESS_MSG);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Failed to update paycheck: {}", e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<Void> deletePaycheck(String id) {
        ResponseSchema<Void> response = new ResponseSchema<>();
        try {
            paycheckDao.deletePaycheck(id);
            response.setMessage(AppConstants.SUCCESS_MSG);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Failed to delete paycheck with ID {}: {}", id, e.getMessage(), e);
        }
        return response;
    }

    private String generateFileName(String employeeId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String dateStr = dateFormat.format(new Date());
        return "SLIPGAJI-" + employeeId + "-" + dateStr;
    }
}
