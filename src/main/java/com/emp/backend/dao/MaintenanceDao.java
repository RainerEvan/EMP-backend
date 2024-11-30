package com.emp.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.emp.backend.model.Maintenance;
import com.emp.backend.model.MaintenanceGroup;

@Repository
public class MaintenanceDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<MaintenanceGroup> maintenanceGroupRowMapper = new RowMapper<MaintenanceGroup>() {
        @Override
        public MaintenanceGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
            MaintenanceGroup maintenanceGroup = new MaintenanceGroup();
            maintenanceGroup.setId(rs.getString("id"));
            maintenanceGroup.setGroupId(rs.getString("group_id"));
            maintenanceGroup.setName(rs.getString("name"));
            maintenanceGroup.setDescription(rs.getString("description"));
            maintenanceGroup.setCreatedAt(rs.getString("created_at"));
            maintenanceGroup.setCreatedBy(rs.getString("created_by"));
            maintenanceGroup.setUpdatedAt(rs.getString("updated_at"));
            maintenanceGroup.setUpdatedBy(rs.getString("updated_by"));
            maintenanceGroup.setIsActive(rs.getBoolean("is_active"));
            return maintenanceGroup;
        }
    };

    public List<MaintenanceGroup> getAllMaintenanceGroups() {
        String sql = "SELECT id, group_id, name, description, created_at, created_by, updated_at, updated_by, is_active " +
                     "FROM maintenance_group ORDER BY group_id";
        return jdbcTemplate.query(sql, maintenanceGroupRowMapper);
    }

    public MaintenanceGroup getMaintenanceGroupByGroupId(String groupId) {
        String sql = "SELECT id, group_id, name, description, created_at, created_by, updated_at, updated_by, is_active " +
                     "FROM maintenance_group WHERE group_id = ?";
        return jdbcTemplate.queryForObject(sql, maintenanceGroupRowMapper, groupId);
    }

    public String getLastGroupId() {
        String sql = "SELECT group_id FROM maintenance_group ORDER BY group_id DESC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void insertMaintenanceGroup(MaintenanceGroup maintenanceGroup) {
        String sql = "INSERT INTO maintenance_group (group_id, name, description, created_at, created_by, updated_at, updated_by, is_active) " +
                     "VALUES (?, ?, ?, now(), ?, ?, ?, ?)";
        jdbcTemplate.update(sql, maintenanceGroup.getGroupId(), maintenanceGroup.getName(),
                            maintenanceGroup.getDescription(), maintenanceGroup.getCreatedBy(),
                            maintenanceGroup.getUpdatedAt(), maintenanceGroup.getUpdatedBy(), maintenanceGroup.getIsActive());
    }

    public void updateMaintenanceGroup(MaintenanceGroup maintenanceGroup) {
        String sql = "UPDATE maintenance_group SET name = ?, description = ?, " +
                     "updated_by = ?, is_active = ? WHERE group_id = ?";
        jdbcTemplate.update(sql, maintenanceGroup.getName(), maintenanceGroup.getDescription(),
                            maintenanceGroup.getUpdatedBy(), maintenanceGroup.getIsActive(), maintenanceGroup.getGroupId());
    }

    public void deleteMaintenanceGroup(String groupId) {
        String sql = "DELETE FROM maintenance_group WHERE group_id = ?";
        jdbcTemplate.update(sql, groupId);
    }

    private final RowMapper<Maintenance> maintenanceRowMapper = new RowMapper<Maintenance>() {
        @Override
        public Maintenance mapRow(ResultSet rs, int rowNum) throws SQLException {
            Maintenance maintenance = new Maintenance();
            maintenance.setId(rs.getString("id"));
            maintenance.setGroupId(rs.getString("group_id"));
            maintenance.setParamCd(rs.getString("param_cd"));
            maintenance.setName(rs.getString("name"));
            maintenance.setDescription(rs.getString("description"));
            maintenance.setExtraCd1(rs.getString("extra_cd1"));
            maintenance.setExtraCd2(rs.getString("extra_cd2"));
            maintenance.setExtraCd3(rs.getString("extra_cd3"));
            maintenance.setCreatedAt(rs.getString("created_at"));
            maintenance.setCreatedBy(rs.getString("created_by"));
            maintenance.setUpdatedAt(rs.getString("updated_at"));
            maintenance.setUpdatedBy(rs.getString("updated_by"));
            maintenance.setIsActive(rs.getBoolean("is_active"));
            return maintenance;
        }
    };

    public List<Maintenance> getAllMaintenancesByGroupId(String groupId) {
        String sql = "SELECT id, group_id, param_cd, name, description, extra_cd1, extra_cd2, extra_cd3, " +
                     "created_at, created_by, updated_at, updated_by, is_active " +
                     "FROM maintenance WHERE group_id = ? ORDER BY param_cd";
        return jdbcTemplate.query(sql, maintenanceRowMapper, groupId);
    }

    public String getLastParamCd(String groupId) {
        String sql = "SELECT param_cd FROM maintenance WHERE group_id = ? ORDER BY CAST(SPLIT_PART(param_cd, '-', 2) AS INTEGER) DESC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, groupId);
        } catch (Exception e) {
            return null;
        }
    }

    public void insertMaintenance(Maintenance maintenance) {
        String sql = "INSERT INTO maintenance (group_id, param_cd, name, description, extra_cd1, extra_cd2, extra_cd3, " +
                     "created_at, created_by, updated_at, updated_by, is_active) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?)";
        jdbcTemplate.update(sql, maintenance.getGroupId(), maintenance.getParamCd(), maintenance.getName(),
                            maintenance.getDescription(), maintenance.getExtraCd1(), maintenance.getExtraCd2(),
                            maintenance.getExtraCd3(), maintenance.getCreatedBy(),
                            maintenance.getUpdatedAt(), maintenance.getUpdatedBy(), maintenance.getIsActive());
    }

    public void updateMaintenance(Maintenance maintenance) {
        String sql = "UPDATE maintenance SET group_id = ?, param_cd = ?, name = ?, description = ?, extra_cd1 = ?, " +
                     "extra_cd2 = ?, extra_cd3 = ?, updated_by = ?, " +
                     "is_active = ? WHERE id = CAST(? AS UUID)";
        jdbcTemplate.update(sql, maintenance.getGroupId(), maintenance.getParamCd(), maintenance.getName(),
                            maintenance.getDescription(), maintenance.getExtraCd1(), maintenance.getExtraCd2(),
                            maintenance.getExtraCd3(), maintenance.getUpdatedBy(), maintenance.getIsActive(),
                            maintenance.getId());
    }

    public void deleteMaintenance(String paramCd) {
        String sql = "DELETE FROM maintenance WHERE param_cd = ?";
        jdbcTemplate.update(sql, paramCd);
    }
}
