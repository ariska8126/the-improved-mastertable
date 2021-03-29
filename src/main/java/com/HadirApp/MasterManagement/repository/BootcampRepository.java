/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.repository;

import com.HadirApp.MasterManagement.entity.Bootcamp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author creative
 */
public interface BootcampRepository extends JpaRepository<Bootcamp, String>{
    @Query(value = "SELECT * FROM bootcamp where bootcamp.bootcamp_active = 'true'", nativeQuery = true)
    List <Bootcamp> getActiveBootcamp();
    
    @Query(value = "SELECT count(*) from bootcamp", nativeQuery = true)
    public String getMaxBootcamp();

    public Optional<Bootcamp> findById(String id);
    
    @Query(value = "SELECT b.* FROM bootcamp b JOIN bootcamp_detail bd ON b.bootcamp_id = bd.bootcamp_id WHERE user_id = ?1", nativeQuery = true)
    public List<Bootcamp> getBootcamp(@Param ("id") String id);
}
