package com.emp.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.emp.backend.model.Employee;

@Repository
public class EmployeeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Employee> employeeRowMapper = new RowMapper<Employee>() {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getString("id"));
            employee.setFullName(rs.getString("full_name"));
            employee.setBirthPlace(rs.getString("birth_place"));
            employee.setBirthDate(rs.getString("birth_date"));
            employee.setPhoneNumber(rs.getString("phone_number"));
            employee.setEmail(rs.getString("email"));
            employee.setNik(rs.getString("nik"));
            employee.setEmployeeId(rs.getString("employee_id"));
            employee.setPosition(rs.getString("position"));
            employee.setDepartment(rs.getString("department"));
            employee.setDateStart(rs.getString("date_start"));
            employee.setDateEnd(rs.getString("date_end"));
            employee.setBaseSalary(rs.getBigDecimal("base_salary"));
            employee.setBankAccount(rs.getString("bank_account"));
            employee.setBankAccountNum(rs.getString("bank_account_num"));
            employee.setCreatedAt(rs.getString("created_at"));
            employee.setCreatedBy(rs.getString("created_by"));
            employee.setUpdatedAt(rs.getString("updated_at"));
            employee.setUpdatedBy(rs.getString("updated_by"));
            employee.setIsActive(rs.getBoolean("is_active"));
            return employee;
        }
    };

    public List<Employee> getAllEmployees() {
        String sql = "SELECT id, full_name, birth_place, birth_date, phone_number, email, nik, employee_id, " +
                     "position, department, date_start, date_end, base_salary, bank_account, bank_account_num, " +
                     "created_at, created_by, updated_at, updated_by, is_active " +
                     "FROM employee ORDER BY employee_id";
        return jdbcTemplate.query(sql, employeeRowMapper);
    }
    
    public Employee getEmployeeByEmployeeId(String employeeId) {
        String sql = "SELECT id, full_name, birth_place, birth_date, phone_number, email, nik, employee_id, " +
                     "position, department, date_start, date_end, base_salary, bank_account, bank_account_num, " +
                     "created_at, created_by, updated_at, updated_by, is_active " +
                     "FROM employee WHERE employee_id = ?";
        return jdbcTemplate.queryForObject(sql, employeeRowMapper, employeeId);
    }

    public String getLastEmployeeId() {
        String sql = "SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public void insertEmployee(Employee employee) {
        String sql = "INSERT INTO employee (full_name, birth_place, birth_date, phone_number, email, nik, employee_id, " +
                     "position, department, date_start, date_end, base_salary, bank_account, bank_account_num, created_at, created_by, updated_at, updated_by, is_active) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?, ?, ?)";
        jdbcTemplate.update(sql, employee.getFullName(), employee.getBirthPlace(), employee.getBirthDate(),
                            employee.getPhoneNumber(), employee.getEmail(), employee.getNik(), employee.getEmployeeId(),
                            employee.getPosition(), employee.getDepartment(), employee.getDateStart(), employee.getDateEnd(),
                            employee.getBaseSalary(), employee.getBankAccount(), employee.getBankAccountNum(),
                            employee.getCreatedBy(), employee.getUpdatedAt(), employee.getUpdatedBy(), employee.getIsActive());
    }
    
    public void updateEmployee(Employee employee) {
        String sql = "UPDATE employee SET full_name = ?, birth_place = ?, birth_date = ?, phone_number = ?, email = ?, nik = ?, " +
                     "position = ?, department = ?, date_start = ?, date_end = ?, base_salary = ?, " +
                     "bank_account = ?, bank_account_num = ?, updated_by = ?, is_active = ? WHERE employee_id = ?";
        jdbcTemplate.update(sql, employee.getFullName(), employee.getBirthPlace(), employee.getBirthDate(),
                            employee.getPhoneNumber(), employee.getEmail(), employee.getNik(),
                            employee.getPosition(), employee.getDepartment(), employee.getDateStart(), employee.getDateEnd(),
                            employee.getBaseSalary(), employee.getBankAccount(), employee.getBankAccountNum(), employee.getUpdatedBy(),
                            employee.getIsActive(), employee.getEmployeeId());
    }
    
    public void deleteEmployee(String id) {
        String sql = "DELETE FROM employee WHERE id = CAST(? AS UUID)";
        jdbcTemplate.update(sql, id);
    }
}
