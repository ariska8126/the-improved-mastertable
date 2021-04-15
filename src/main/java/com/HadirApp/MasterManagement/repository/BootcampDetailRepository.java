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
import org.springframework.stereotype.Repository;

/**
 *
 * @author creative
 */
@Repository
public interface BootcampDetailRepository extends JpaRepository<BootcampDetail, String>{
    
    @Query(value = "SELECT b.* FROM bootcamp b JOIN bootcamp_detail bd ON b.bootcamp_id = bd.bootcamp_id JOIN users u ON bd.user_id = u.user_id WHERE u.user_id = ?1", nativeQuery = true)
    public List<Bootcamp> getBootcamp(@Param ("id") String id);
    
    @Query(value = "SELECT IF(EXISTS(SELECT * FROM bootcamp_detail WHERE user_id = :userId and bootcamp_id = :bootcampId),1,0)", nativeQuery = true)
    public int findExistBootcampDetail(@Param ("userId") String userId, @Param("bootcampId") String bootcampId);
    
    @Query(value = "SELECT bootcamp.*,bootcamp_detail.* from bootcamp join bootcamp_detail on bootcamp.bootcamp_id = bootcamp_detail.bootcamp_id where bootcamp_detail.user_id = ?1", nativeQuery = true)
    public Iterable<BootcampDetail> getBootcampDetailByUser(@Param("userId") String id);

}
