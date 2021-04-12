/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.repository;

import com.HadirApp.MasterManagement.entity.Bootcamp;
import com.HadirApp.MasterManagement.entity.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author creative
 */
@Repository
public interface BootcampRepository extends JpaRepository<Bootcamp, String>{
    @Query(value = "SELECT * FROM bootcamp where bootcamp.bootcamp_active = 'true'", nativeQuery = true)
    List <Bootcamp> getActiveBootcamp();
    
    @Query(value = "SELECT count(*) from bootcamp", nativeQuery = true)
    public String getMaxBootcamp();

    public Optional<Bootcamp> findById(String id);
    
    @Query(value = "SELECT bootcamp.* from bootcamp join bootcamp_detail on bootcamp.bootcamp_id = bootcamp_detail.bootcamp_id where bootcamp_detail.user_id = ?1", nativeQuery = true)
    public Iterable<Bootcamp> getBootcampByUser(@Param("userId") String id); 
            
    @Query(value = "SELECT b.* FROM bootcamp b JOIN bootcamp_detail bd ON b.bootcamp_id = bd.bootcamp_id WHERE user_id = ?1", nativeQuery = true)
    public List<Bootcamp> getBootcamp(@Param ("id") String id);
    
    @Query(value = "SELECT IF(EXISTS(SELECT * FROM users WHERE users.user_token = ?1),1,0)", nativeQuery = true)
    public int findIfExistToken(@Param("token") String mail);
    
    //get user by id
    @Query(value = "SELECT * FROM users WHERE users.user_token = ?1", nativeQuery = true)
    public Optional<Users> getUsersByToken(@Param("id") String id);
    
    //get role name
    @Query(value = "select role_name from users join role on role.role_id = users.role_id WHERE users.user_id = ?1", nativeQuery = true)
    public String getUsersRole(@Param("id") String id);
    
    

}
