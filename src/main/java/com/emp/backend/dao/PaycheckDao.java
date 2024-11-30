package com.emp.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.emp.backend.model.Paycheck;

@Repository
public class PaycheckDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Paycheck> paycheckRowMapper = new RowMapper<Paycheck>() {

        @Override
        public Paycheck mapRow(ResultSet rs, int rowNum) throws SQLException {
            Paycheck paycheck = new Paycheck();
            paycheck.setId(rs.getString("id"));
            paycheck.setEmployeeId(rs.getString("employee_id"));
            paycheck.setBaseSalary(rs.getBigDecimal("base_salary"));
            paycheck.setTransportationAllowance(rs.getBigDecimal("transportation_allowance"));
            paycheck.setFoodAllowance(rs.getBigDecimal("food_allowance"));
            paycheck.setOvertime(rs.getBigDecimal("overtime"));
            paycheck.setTotalGrossIncome(rs.getBigDecimal("total_gross_income"));
            paycheck.setBpjsKesehatan(rs.getBigDecimal("bpjs_kesehatan"));
            paycheck.setBpjsKetenagakerjaan(rs.getBigDecimal("bpjs_ketenagakerjaan"));
            paycheck.setTax(rs.getBigDecimal("tax"));
            paycheck.setOtherCut(rs.getBigDecimal("other_cut"));
            paycheck.setTotalDeduction(rs.getBigDecimal("total_deduction"));
            paycheck.setTotalNetIncome(rs.getBigDecimal("total_net_income"));
            paycheck.setFileName(rs.getString("file_name"));
            paycheck.setDateLastSent(rs.getString("date_last_sent"));
            paycheck.setCountSent(rs.getBigDecimal("count_sent"));
            paycheck.setCreatedAt(rs.getString("created_at"));
            paycheck.setCreatedBy(rs.getString("created_by"));
            paycheck.setUpdatedAt(rs.getString("updated_at"));
            paycheck.setUpdatedBy(rs.getString("updated_by"));
            return paycheck;
        }
    };

    public List<Paycheck> getAllPaychecksByEmployeeId(String employeeId) {
        String sql = "SELECT id, employee_id, base_salary, transportation_allowance, food_allowance, overtime, " +
                     "total_gross_income, bpjs_kesehatan, bpjs_ketenagakerjaan, tax, other_cut, total_deduction, " +
                     "total_net_income, file_name, date_last_sent, count_sent, created_at, created_by, updated_at, updated_by " +
                     "FROM paycheck WHERE employee_id = ?";
        return jdbcTemplate.query(sql, paycheckRowMapper, employeeId);
    }

    public Paycheck getPaycheckById(String id) {
        String sql = "SELECT id, employee_id, base_salary, transportation_allowance, food_allowance, overtime, " +
                     "total_gross_income, bpjs_kesehatan, bpjs_ketenagakerjaan, tax, other_cut, total_deduction, " +
                     "total_net_income, file_name, date_last_sent, count_sent, created_at, created_by, updated_at, updated_by " +
                     "FROM paycheck WHERE id = CAST(? AS UUID)";
        return jdbcTemplate.queryForObject(sql, paycheckRowMapper, id);
    }

    public void insertPaycheck(Paycheck paycheck) {
        String sql = "INSERT INTO paycheck (employee_id, base_salary, transportation_allowance, food_allowance, overtime, " +
                     "total_gross_income, bpjs_kesehatan, bpjs_ketenagakerjaan, tax, other_cut, total_deduction, " +
                     "total_net_income, file_name, date_last_sent, count_sent, created_at, created_by, updated_at, updated_by) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?, ?)";
        jdbcTemplate.update(sql, paycheck.getEmployeeId(), paycheck.getBaseSalary(),
                            paycheck.getTransportationAllowance(), paycheck.getFoodAllowance(), paycheck.getOvertime(),
                            paycheck.getTotalGrossIncome(), paycheck.getBpjsKesehatan(), paycheck.getBpjsKetenagakerjaan(),
                            paycheck.getTax(), paycheck.getOtherCut(), paycheck.getTotalDeduction(), paycheck.getTotalNetIncome(),
                            paycheck.getFileName(), paycheck.getDateLastSent(), paycheck.getCountSent(),
                            paycheck.getCreatedBy(), paycheck.getUpdatedAt(), paycheck.getUpdatedBy());
    }

    public void updatePaycheck(Paycheck paycheck) {
        String sql = "UPDATE paycheck SET employee_id = ?, base_salary = ?, transportation_allowance = ?, food_allowance = ?, " +
                     "overtime = ?, total_gross_income = ?, bpjs_kesehatan = ?, bpjs_ketenagakerjaan = ?, tax = ?, other_cut = ?, " +
                     "total_deduction = ?, total_net_income = ?, file_name = ?, date_last_sent = ?, " +
                     "count_sent = ?, updated_by = ? WHERE id = CAST(? AS UUID)";
        jdbcTemplate.update(sql, paycheck.getEmployeeId(), paycheck.getBaseSalary(), paycheck.getTransportationAllowance(),
                            paycheck.getFoodAllowance(), paycheck.getOvertime(), paycheck.getTotalGrossIncome(),
                            paycheck.getBpjsKesehatan(), paycheck.getBpjsKetenagakerjaan(), paycheck.getTax(),
                            paycheck.getOtherCut(), paycheck.getTotalDeduction(), paycheck.getTotalNetIncome(),
                            paycheck.getFileName(), paycheck.getDateLastSent(), paycheck.getCountSent(),
                            paycheck.getUpdatedBy(), paycheck.getId());
    }

    public void deletePaycheck(String id) {
        String sql = "DELETE FROM paycheck WHERE id = CAST(? AS UUID)";
        jdbcTemplate.update(sql, id);
    }
}
