package com.emp.backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.backend.dao.MaintenanceDao;
import com.emp.backend.mapper.response.ResponseSchema;
import com.emp.backend.model.Maintenance;
import com.emp.backend.model.MaintenanceGroup;
import com.emp.backend.util.AppConstants;

@Service
public class MaintenanceService {

    private static final Logger log = LoggerFactory.getLogger(MaintenanceService.class);

    @Autowired
    private MaintenanceDao maintenanceDao;

    public ResponseSchema<List<MaintenanceGroup>> getAllMaintenanceGroup() {
        ResponseSchema<List<MaintenanceGroup>> response = new ResponseSchema<>();
        try {
            List<MaintenanceGroup> maintenanceGroups = maintenanceDao.getAllMaintenanceGroups();
            response.setMessage(AppConstants.SUCCESS_MSG);
            response.setOutput(maintenanceGroups);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Error retrieving maintenance groups: {}", e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<MaintenanceGroup> getMaintenanceGroupByGroupId(String groupId) {
        ResponseSchema<MaintenanceGroup> response = new ResponseSchema<>();
        try {
            MaintenanceGroup maintenanceGroup = maintenanceDao.getMaintenanceGroupByGroupId(groupId);
            if (maintenanceGroup != null) {
                response.setMessage(AppConstants.SUCCESS_MSG);
                response.setOutput(maintenanceGroup);
            } else {
                response.setMessage("Maintenance group not found");
            }
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Error retrieving maintenance group with ID {}: {}", groupId, e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<Void> addMaintenanceGroup(MaintenanceGroup maintenanceGroup) {
        ResponseSchema<Void> response = new ResponseSchema<>();
        try {
            String newGroupId = generateGroupId();
            maintenanceGroup.setGroupId(newGroupId);
            maintenanceDao.insertMaintenanceGroup(maintenanceGroup);
            response.setMessage(AppConstants.SUCCESS_MSG);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Failed to add maintenance group: {}", e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<Void> updateMaintenanceGroup(MaintenanceGroup maintenanceGroup) {
        ResponseSchema<Void> response = new ResponseSchema<>();
        try {
            maintenanceDao.updateMaintenanceGroup(maintenanceGroup);
            response.setMessage(AppConstants.SUCCESS_MSG);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Failed to update maintenance group: {}", e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<Void> deleteMaintenanceGroup(String groupId) {
        ResponseSchema<Void> response = new ResponseSchema<>();
        try {
            maintenanceDao.deleteMaintenanceGroup(groupId);
            response.setMessage(AppConstants.SUCCESS_MSG);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Failed to delete maintenance group with ID {}: {}", groupId, e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<List<Maintenance>> getAllMaintenancesByGroupId(String groupId) {
        ResponseSchema<List<Maintenance>> response = new ResponseSchema<>();
        try {
            List<Maintenance> maintenances = maintenanceDao.getAllMaintenancesByGroupId(groupId);
            response.setMessage(AppConstants.SUCCESS_MSG);
            response.setOutput(maintenances);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Error retrieving maintenances for groupId {}: {}", groupId, e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<Void> addMaintenance(Maintenance maintenance) {
        ResponseSchema<Void> response = new ResponseSchema<>();
        try {
            String newParamCd = generateParamCd(maintenance.getGroupId());
            maintenance.setParamCd(newParamCd);
            maintenanceDao.insertMaintenance(maintenance);
            response.setMessage(AppConstants.SUCCESS_MSG);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Failed to add maintenance: {}", e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<Void> updateMaintenance(Maintenance maintenance) {
        ResponseSchema<Void> response = new ResponseSchema<>();
        try {
            maintenanceDao.updateMaintenance(maintenance);
            response.setMessage(AppConstants.SUCCESS_MSG);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Failed to update maintenance: {}", e.getMessage(), e);
        }
        return response;
    }

    public ResponseSchema<Void> deleteMaintenance(String paramCd) {
        ResponseSchema<Void> response = new ResponseSchema<>();
        try {
            maintenanceDao.deleteMaintenance(paramCd);
            response.setMessage(AppConstants.SUCCESS_MSG);
        } catch (Exception e) {
            response.setMessage(AppConstants.FAILED_MSG);
            log.error("Failed to delete maintenance with Param CD {}: {}", paramCd, e.getMessage(), e);
        }
        return response;
    }

    private String generateGroupId() {
        String lastGroupId = maintenanceDao.getLastGroupId();
        if (lastGroupId == null) {
            return String.format("G%03d", 1);
        }
    
        int lastNum = Integer.parseInt(lastGroupId.substring(1));
        int newNum = lastNum + 1;
    
        return String.format("G%03d", newNum);
    }

    private String generateParamCd(String groupId) {
        String lastParamCd = maintenanceDao.getLastParamCd(groupId);
        if (lastParamCd == null) {
            return String.format("%s-%04d", groupId, 1);
        }
    
        int lastNum = Integer.parseInt(lastParamCd.split("-")[1]);
        int newNum = lastNum + 1;
    
        return String.format("%s-%04d", groupId, newNum);
    }
}
