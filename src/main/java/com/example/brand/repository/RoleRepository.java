package com.example.brand.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public void addRole(Long managerId, String role){

        String sql = "INSERT INTO manager_roles (manager_id, roles) "+
                "VALUES (?,?)";

        jdbcTemplate.update(sql, managerId, role);
    }

    public void updateRole(Long managerId, String role){

        String sql = "UPDATE manager_roles SET roles = ? "+
                "WHERE manager_id = ? ";

        jdbcTemplate.update(sql, managerId, role);
    }
}
