/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.Attendance;
import com.HadirApp.MasterManagement.entity.Role;
import com.HadirApp.MasterManagement.repository.JasperTestRepository;
import java.util.List;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author creative
 */
@RestController
public class JasperTestController {
    
    @Autowired JasperTestRepository jasperRepository;
    
    @GetMapping("/getattendance")
    public String getAttendance(){
        List<Attendance> attendance = jasperRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();
        
        for (Attendance attendances : attendance) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", attendances.getAttendanceId());
            jsonObject.put("date", attendances.getAttendanceDate().toString());
            jsonObject.put("time",attendances.getAttendanceTime().toString());
            jsonObject.put("remark",attendances.getAttendanceRemark());
            jsonObject.put("type",attendances.getAttendanceType());
            jsonObject.put("status",attendances.getAttendanceStatusId().getAttendanceStatusName());
            jsonObject.put("employee",attendances.getUserId().getUserFullname());
            jsonArray.add(jsonObject);
        }

        jsonObject2.put("attendance_list", jsonArray);

        return jsonObject2.toString();
    }
    
}
