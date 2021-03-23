/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.repository;

import com.HadirApp.MasterManagement.entity.Division;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author herli
 */
public interface DivisionRepository extends JpaRepository<Division, Integer>{
    
    @Query(value = "SELECT * FROM division where division_active = 'true'", nativeQuery = true)
    List <Division> getActiveDivision();
    
    @Query(value = "SELECT MAX(division_id) FROM division", nativeQuery = true)
    public String getMaxDivision();
    
}
