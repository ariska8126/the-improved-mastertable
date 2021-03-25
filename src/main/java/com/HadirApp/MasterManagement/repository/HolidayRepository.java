/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.repository;

import com.HadirApp.MasterManagement.entity.CalendarHoliday;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author herli
 */
public interface HolidayRepository extends JpaRepository<CalendarHoliday, Integer>{
    
    @Query(value = "SELECT * FROM calendar_holiday WHERE calendar_holiday_active = 'true'", nativeQuery = true)
    List <CalendarHoliday> getActiveCalendarHoliday();
    
    @Query(value = "SELECT count(*) from calendar_holiday", nativeQuery = true)
    public String getCountHoliday();
    
}
