/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.repository;

import com.HadirApp.MasterManagement.entity.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author herli
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {

    //get user active = true
    @Query(value = "SELECT * FROM users WHERE user_active = 'true'", nativeQuery = true)
    List<Users> getActiveUsers();

    //get user id
    @Query(value = "SELECT user_id FROM users", nativeQuery = true)
    public ArrayList<String> getAllUsersID();

    //get user by id
    @Query(value = "SELECT * FROM users WHERE user_id = ?1", nativeQuery = true)
    public Optional<Users> getUsersByID(@Param("id") String id);
    
    //get entity user by id
    @Query(value = "SELECT * FROM users WHERE user_id = ?1", nativeQuery = true)
    public Users getEntityUsersByID(@Param("id") String id);
    
    //IF EXIST ID
    @Query(value = "SELECT IF(EXISTS(SELECT * FROM users WHERE user_id = ?1),1,0)", nativeQuery = true)
    public int findIfExistID(@Param("id") String id);
    
    
}