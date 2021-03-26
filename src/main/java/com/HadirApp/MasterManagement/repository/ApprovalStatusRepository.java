/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.repository;

import com.HadirApp.MasterManagement.entity.ApprovalStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author herli
 */
public interface ApprovalStatusRepository extends JpaRepository<ApprovalStatus, Integer>{
    
    @Query(value = "SELECT * FROM approval_status where approval_status_active = 'true'", nativeQuery = true)
    List <ApprovalStatus> getActiveApprovalStatus();
    
    @Query(value = "SELECT COUNT(*) FROM approval_status", nativeQuery = true)
    public String getMaxApprovalStatus();
    
}
