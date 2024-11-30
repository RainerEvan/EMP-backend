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
import com.emp.backend.model.Maintenance;
import com.emp.backend.model.MaintenanceGroup;
import com.emp.backend.service.MaintenanceService;
import com.emp.backend.util.AppConstants;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {
    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping("/group")
    public ResponseEntity<ResponseSchema<List<MaintenanceGroup>>> getAllMaintenanceGroup() {
        ResponseSchema<List<MaintenanceGroup>> response = maintenanceService.getAllMaintenanceGroup();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<ResponseSchema<MaintenanceGroup>> getMaintenanceGroupById(@PathVariable String groupId) {
        ResponseSchema<MaintenanceGroup> response = maintenanceService.getMaintenanceGroupByGroupId(groupId);
        if (response.getOutput() != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/group")
    public ResponseEntity<ResponseSchema<Void>> addMaintenanceGroup(@RequestBody MaintenanceGroup maintenanceGroup) {
        ResponseSchema<Void> response = maintenanceService.addMaintenanceGroup(maintenanceGroup);
        if (response.getMessage().equals(AppConstants.SUCCESS_MSG)) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/group/{groupId}")
    public ResponseEntity<ResponseSchema<Void>> updateMaintenanceGroup(@PathVariable String groupId, @RequestBody MaintenanceGroup maintenanceGroup) {
        maintenanceGroup.setGroupId(groupId);
        ResponseSchema<Void> response = maintenanceService.updateMaintenanceGroup(maintenanceGroup);
        if (response.getMessage().equals(AppConstants.SUCCESS_MSG)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<ResponseSchema<Void>> deleteMaintenanceGroup(@PathVariable String groupId) {
        ResponseSchema<Void> response = maintenanceService.deleteMaintenanceGroup(groupId);
        if (response.getMessage().equals(AppConstants.SUCCESS_MSG)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/param/{groupId}")
    public ResponseEntity<ResponseSchema<List<Maintenance>>> getAllMaintenancesByGroupId(@PathVariable String groupId) {
        ResponseSchema<List<Maintenance>> response = maintenanceService.getAllMaintenancesByGroupId(groupId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/param")
    public ResponseEntity<ResponseSchema<Void>> addMaintenance(@RequestBody Maintenance maintenance) {
        ResponseSchema<Void> response = maintenanceService.addMaintenance(maintenance);
        if (response.getMessage().equals(AppConstants.SUCCESS_MSG)) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/param")
    public ResponseEntity<ResponseSchema<Void>> updateMaintenance(@RequestBody Maintenance maintenance) {
        ResponseSchema<Void> response = maintenanceService.updateMaintenance(maintenance);
        if (response.getMessage().equals(AppConstants.SUCCESS_MSG)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/param/{paramCd}")
    public ResponseEntity<ResponseSchema<Void>> deleteMaintenance(@PathVariable String paramCd) {
        ResponseSchema<Void> response = maintenanceService.deleteMaintenance(paramCd);
        if (response.getMessage().equals(AppConstants.SUCCESS_MSG)) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
