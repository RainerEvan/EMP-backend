package com.emp.backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.backend.dao.EmployeeDao;
import com.emp.backend.mapper.response.ResponseSchema;
import com.emp.backend.model.Employee;
import com.emp.backend.util.AppConstants;

@Service
public class EmployeeService {
    
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeDao employeeDao;

    public ResponseSchema<List<Employee>> getAllEmployees() {
        ResponseSchema<List<Employee>> response = new ResponseSchema<>();
        try {
            List<Employee> employees = employeeDao.getAllEmployees();
            response.setMessage(AppConstants.SUCCESS_MSG);
            response.setOutput(employees);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Error retrieving employees: {}", e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<Employee> getEmployeeByEmployeeId(String employeeId) {
        ResponseSchema<Employee> response = new ResponseSchema<>();
        try {
            Employee employee = employeeDao.getEmployeeByEmployeeId(employeeId);
            if (employee != null) {
                response.setMessage(AppConstants.SUCCESS_MSG);
                response.setOutput(employee);
            } else {
                response.setMessage("Employee not found");
            }
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Error retrieving employee with ID {}: {}", employeeId, e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<Void> addEmployee(Employee employee) {
        ResponseSchema<Void> response = new ResponseSchema<>();

        try {
            String newId = generateEmployeeId();
            employee.setEmployeeId(newId);

            employeeDao.insertEmployee(employee);
            response.setMessage(AppConstants.SUCCESS_MSG);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Failed to add employee: {}", e.getMessage(), e);
        }

        return response;
    }

    public ResponseSchema<Void> updateEmployee(Employee employee) {
        ResponseSchema<Void> response = new ResponseSchema<>();

        try {
            employeeDao.updateEmployee(employee);
            response.setMessage(AppConstants.SUCCESS_MSG);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Failed to update employee: {}", e.getMessage(), e);
        }

        return response;
    }

    public ResponseSchema<Void> deleteEmployee(String id) {
        ResponseSchema<Void> response = new ResponseSchema<>();

        try {
            employeeDao.deleteEmployee(id);
            response.setMessage(AppConstants.SUCCESS_MSG);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Failed to delete employee with ID {}: {}", id, e.getMessage(), e);
        }

        return response;
    }

    private String generateEmployeeId() {
        String lastId = employeeDao.getLastEmployeeId();
        if (lastId == null) {
            return "EMP00001";
        }

        int lastNum = Integer.parseInt(lastId.substring(3));
        int newNum = lastNum + 1;

        return String.format("EMP%05d", newNum);
    }

}
