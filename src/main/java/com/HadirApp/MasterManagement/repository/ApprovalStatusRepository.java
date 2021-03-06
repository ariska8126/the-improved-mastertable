/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.repository;

import com.HadirApp.MasterManagement.entity.ApprovalStatus;
import com.HadirApp.MasterManagement.entity.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author herli
 */
public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatus, Integer>{
    
    @Query(value = "SELECT * FROM approval_status where approval_status_active = 'true'", nativeQuery = true)
    List <ApprovalStatus> getActiveApprovalStatus();
    
    @Query(value = "SELECT COUNT(*) FROM approval_status", nativeQuery = true)
    public String getMaxApprovalStatus();
    
    @Query(value = "SELECT IF(EXISTS(SELECT * FROM users WHERE users.user_token = ?1),1,0)", nativeQuery = true)
    public int findIfExistToken(@Param("token") String mail);
    
    //get user by id
    @Query(value = "SELECT * FROM users WHERE users.user_token = ?1", nativeQuery = true)
    public Optional<Users> getUsersByToken(@Param("id") String id);
    
    //get role name
    @Query(value = "select role_name from users join role on role.role_id = users.role_id WHERE users.user_id = ?1", nativeQuery = true)
    public String getUsersRole(@Param("id") String id);
    
}
