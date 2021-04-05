/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.repository;

import com.HadirApp.MasterManagement.entity.Attendance;
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
    
    // Get Bootcamp and Trainner
    @Query(value = "select attendance.* from attendance join users on attendance.user_id = users.user_id join bootcamp_detail on bootcamp_detail.user_id = users.user_id where bootcamp_detail.bootcamp_id in (:bootcampId)", nativeQuery = true)
    Iterable<Attendance> getAttendanceByBootcamp(@Param("bootcampId") String bootcampId);
    
    // Delete assign 
    @Modifying
    @Query(value = "DELETE from bootcamp_detail WHERE bootcamp_detail.bootcamp_detail_id = :userId", nativeQuery = true)
    int deleteTrainnerInBootcamp(@Param("userId") String id);
    
}
