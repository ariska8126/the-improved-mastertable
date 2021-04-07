/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.repository;

import com.HadirApp.MasterManagement.entity.AttendanceStatus;
import com.HadirApp.MasterManagement.entity.Bootcamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author creative
 */
public interface AttendanceStatusRepository extends JpaRepository<AttendanceStatus, Integer> {

    @Query(value = "SELECT * FROM attendance_status where attendance_status.attendance_status_active = 'true'", nativeQuery = true)
    List<AttendanceStatus> getActiveAttendance();

    @Query(value = "SELECT count(*) from attendance_status", nativeQuery = true)
    public String getMaxAttendance();
}
