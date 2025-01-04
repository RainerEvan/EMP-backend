package com.emp.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.emp.backend.mapper.request.UserRequest;
import com.emp.backend.model.User;

import lombok.AllArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getAllUser() {
        String sql = "SELECT username, role FROM public.user";
        
        return jdbcTemplate.query(
            sql,
            new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        );
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT username, password, role FROM public.user WHERE username = ?";
        
       try {
            return jdbcTemplate.queryForObject(
                sql,
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user = new User();
                        user.setUsername(rs.getString("username"));
                        user.setPassword(rs.getString("password"));
                        user.setRole(rs.getString("role"));
                        return user;
                    }
                },
                username
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int insertUser(UserRequest userRequest){
        String sql = "INSERT INTO public.user (username, password, role) VALUES (?, ?, ?)";

        return jdbcTemplate.update(sql, userRequest.getUsername(), userRequest.getPassword(), userRequest.getRole());
    }
}
