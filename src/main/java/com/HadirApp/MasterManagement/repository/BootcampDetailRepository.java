/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.repository;

import com.HadirApp.MasterManagement.entity.Bootcamp;
import com.HadirApp.MasterManagement.entity.BootcampDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author creative
 */
public interface BootcampDetailRepository extends JpaRepository<BootcampDetail, String>{
    
    @Query(value = "SELECT b.* FROM bootcamp b JOIN bootcamp_detail bd ON b.bootcamp_id = bd.bootcamp_id JOIN users u ON bd.user_id = u.user_id WHERE u.user_id = ?1", nativeQuery = true)
    public List<Bootcamp> getBootcamp(@Param ("id") String id);
}
