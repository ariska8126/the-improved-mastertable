/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.repository;

import com.HadirApp.MasterManagement.entity.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author creative
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    @Query(value = "SELECT * FROM role where role.role_active = 'true'", nativeQuery = true)
    List <Role> getActiveRole();
    
    @Query(value = "SELECT count(*) from role", nativeQuery = true)
    public String getMaxRole();
}
