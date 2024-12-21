package com.emp.backend.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.emp.backend.dao.PaycheckDao;
import com.emp.backend.dao.EmployeeDao;
import com.emp.backend.mapper.response.ResponseSchema;
import com.emp.backend.model.Paycheck;
import com.emp.backend.model.Employee;
import com.emp.backend.util.AppConstants;
import com.emp.backend.util.ReportEngine;

@Service
public class PaycheckService {
    private static final Logger log = LoggerFactory.getLogger(PaycheckService.class);

    @Value("${spring.datasource.driver-class-name}")
    private String DATABASE_DRIVER;

    @Value("${spring.datasource.url}")
    private String DATABASE_URL;

    @Value("${spring.datasource.username}")
    private String DATABASE_USER;

    @Value("${spring.datasource.password}")
    private String DATABASE_PASSWORD;

    @Autowired
    private PaycheckDao paycheckDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ResourceLoader resourceLoader;

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

    public ResponseSchema<String> getPaycheckReport(String id) {
        ResponseSchema<String> response = new ResponseSchema<>();
        ReportEngine reportEngine = new ReportEngine();
        try {
            Paycheck paycheck = paycheckDao.getPaycheckById(id);
            if (paycheck != null) {
                String reportDesignPath = getReportDesignPath("paycheck.rptdesign");

                Map<String, Object> params = new HashMap<>();
                params.put("paycheckId", paycheck.getId());
                params.put("employeeId", paycheck.getEmployeeId());

                params.put("databaseDriverClass", DATABASE_DRIVER);
                params.put("databaseUrl", DATABASE_URL);
                params.put("databaseUser", DATABASE_USER);
                params.put("databasePassword", DATABASE_PASSWORD);

                byte[] generatedPdf = reportEngine.generatePdfReport(reportDesignPath, params);
                String base64 = Base64.getEncoder().encodeToString(generatedPdf);

                response.setMessage(AppConstants.SUCCESS_MSG);
                response.setOutput(base64);
            } else {
                response.setMessage("Paycheck not found");
            }
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Error generating paycheck with ID {}: {}", id, e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<String> sendPaycheckEmail(String id) {
        ResponseSchema<String> response = new ResponseSchema<>();
        try {
            Paycheck paycheck = paycheckDao.getPaycheckById(id);
            Employee employee = employeeDao.getEmployeeByEmployeeId(paycheck.getEmployeeId());
            if (paycheck != null && employee != null) {
                ResponseSchema<String> reportResponse = getPaycheckReport(id);
                String base64 = reportResponse.output;
                byte[] generatedPdf = Base64.getDecoder().decode(base64);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
                LocalDateTime dateTime = LocalDateTime.parse(paycheck.getCreatedAt(), formatter);
                String month = dateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
                String year = String.valueOf(dateTime.getYear());
                String monthYear = String.format("%s %s", month, year);

                String subject = String.format("Slip Gaji - %s - %s", monthYear, employee.getFullName());
                String body = emailService.generateEmailBody(employee.getFullName(), monthYear);

                emailService.sendEmailWithAttachment(employee.getEmail(), subject, body, generatedPdf, paycheck.getFileName());

                paycheck.setCountSent(paycheck.getCountSent().add(new BigDecimal(1)));
                paycheckDao.updatePaycheckEmailSent(paycheck);

                response.setMessage(AppConstants.SUCCESS_MSG);
                response.setOutput("Email terkirim");
            } else {
                response.setMessage("Paycheck not found");
            }
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Error generating paycheck with ID {}: {}", id, e.getMessage(), e);
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

    public String getReportDesignPath(String fileName) throws IOException {
        return resourceLoader.getResource("classpath:templates/" + fileName).getFile().getAbsolutePath();
    }
}
