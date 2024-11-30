package com.emp.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceGroup {
    public String id;
    public String groupId;
    public String name;
    public String description;
    public String createdAt;
    public String createdBy;
    public String updatedAt;
    public String updatedBy;
    public Boolean isActive;
}
