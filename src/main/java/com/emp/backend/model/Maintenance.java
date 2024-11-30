package com.emp.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Maintenance {
    public String id;
    public String groupId;
    public String paramCd;
    public String name;
    public String description;
    public String extraCd1;
    public String extraCd2;
    public String extraCd3;
    public String createdAt;
    public String createdBy;
    public String updatedAt;
    public String updatedBy;
    public Boolean isActive;
}
