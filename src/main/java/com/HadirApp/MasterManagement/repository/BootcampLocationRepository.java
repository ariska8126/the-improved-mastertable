/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.repository;

import com.HadirApp.MasterManagement.entity.BootcampLocation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author creative
 */
public interface BootcampLocationRepository extends JpaRepository<BootcampLocation, Integer>{
    @Query(value = "SELECT * FROM bootcamp_location where bootcamp_location.bootcamp_location_active = 'true'", nativeQuery = true)
    List <BootcampLocation> getActiveBootcamp();
    
    @Query(value = "SELECT count(*) from bootcamp_location", nativeQuery = true)
    public String getMaxBootcamp();
}
