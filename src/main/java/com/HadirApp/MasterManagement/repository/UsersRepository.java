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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author herli
 */
@Transactional
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    //get user active = true
    @Query(value = "SELECT * FROM users WHERE user_active = 'true'", nativeQuery = true)
    List<Users> getActiveUsers();
    
    //get user by role & status is active
    @Query(value = "SELECT * FROM users u JOIN role r ON u.role_id = r.role_id WHERE r.role_name LIKE ?1 AND u.user_active = 'true'", nativeQuery = true)
    List<Users> getUsersByRole(@Param ("id") String role);

    //get user id
    @Query(value = "SELECT user_id FROM users", nativeQuery = true)
    public ArrayList<String> getAllUsersID();

    //get user by id
    @Query(value = "SELECT * FROM users WHERE user_id = ?1", nativeQuery = true)
    public Optional<Users> getUsersByID(@Param("id") String id);
    
    //get user by id list
    @Query(value = "SELECT * FROM users WHERE user_id = ?1", nativeQuery = true)
    public Iterable<Users> getUsersListByID(@Param("id") String id);
    
    //get entity user by id
    @Query(value = "SELECT * FROM users WHERE user_id = ?1", nativeQuery = true)
    public Users getEntityUsersByID(@Param("id") String id);
    
    //IF EXIST ID
    @Query(value = "SELECT IF(EXISTS(SELECT * FROM users WHERE user_id = ?1),1,0)", nativeQuery = true)
    public int findIfExistID(@Param("id") String id);
    
    //IF EXIST EMAIL
    @Query(value = "SELECT IF(EXISTS(SELECT * FROM users WHERE user_email = ?1),1,0)", nativeQuery = true)
    public int findIfExistEmail(@Param("mail") String mail);
    
    // HARD DELETE USER
    @Modifying
    @Query(value = "DELETE FROM users where users.user_id = :userId", nativeQuery = true)
    int deleteUserById(@Param("userId") String id);
    
    //if exist token
    @Query(value="SELECT IF(EXISTS(SELECT * FROM `users` WHERE user_token = ?1),1,0)",nativeQuery = true)
    public int findIfExistToken(@Param ("token") String token);
    
    //select user by token
    @Query(value="SELECT * FROM `users` WHERE user_token = ?1",nativeQuery = true)
    public Users findUserByToken(@Param ("token") String token);
    
    //select bootcamp id trainer
    @Query(value="", nativeQuery = true)
    public String findBootcampidByUserId(@Param ("id") String id);
}
