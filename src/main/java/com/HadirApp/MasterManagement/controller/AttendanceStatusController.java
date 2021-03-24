/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.AttendanceStatus;
import com.HadirApp.MasterManagement.repository.AttendanceStatusRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author creative
 */
@RestController
@RequestMapping("/api/master/attendance")
@Api(tags = "Attendance Status Managemet")
public class AttendanceStatusController {
    
    @Autowired
    AttendanceStatusRepository attendanceStatusRepository;
    
    public int maxId() {
        String maxIdStr = attendanceStatusRepository.getMaxAttendance();
        int maxId = Integer.parseInt(maxIdStr);

        return maxId;
    }
    
    // READ
    @GetMapping("/getattendancestatus")
    @ApiOperation(value = "${AttendanceStatusController.getattendancestatus}")
    public String getAllAttendance() {

        List<AttendanceStatus> attendanceStatus = attendanceStatusRepository.findAll();

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();

        for (AttendanceStatus as : attendanceStatus) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("attendance_status_id", as.getAttendanceStatusId());
            jsonObject.put("attendance_status_name", as.getAttendanceStatusName());
            jsonArray.add(jsonObject);
        }

        jsonObject2.put("attendance_status_list", jsonArray);

        return jsonObject2.toString();
    }
    
    // Read all where status = true
    @GetMapping("/getactiveattendancestatus")
    @ApiOperation(value = "${AttendanceStatusController.getactiveattendancestatus}")
    public String getActiveAttendance() {

        List<AttendanceStatus> attendanceStatus = attendanceStatusRepository.getActiveAttendance();

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();

        for (AttendanceStatus as : attendanceStatus) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("attendance_status_id", as.getAttendanceStatusId());
            jsonObject.put("attendance_status_name", as.getAttendanceStatusName());
            jsonArray.add(jsonObject);
        }

        jsonObject2.put("attendance_status_list", jsonArray);

        return jsonObject2.toString();
    }
    
    // Read by ID
    @GetMapping("/getattendancestatus/{id}")
    @ApiOperation(value = "${AttendanceStatusController.getattendancebyid}")
    public String getAttendanceStatusById(@PathVariable int id) {
        Optional<AttendanceStatus> attendanceStatusOptional = attendanceStatusRepository.findById(id);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();

        if (!attendanceStatusOptional.isPresent()) {
            jsonObject.put("description", "data not found");
            jsonObject.put("status", "false");
            jsonArray.add(jsonObject);

            jsonObject2.put("attendance_status", jsonArray);

            return jsonObject.toString();
        }

        jsonObject.put("attendance_status_id", attendanceStatusOptional.get().getAttendanceStatusId());
        jsonObject.put("attendance_status_name", attendanceStatusOptional.get().getAttendanceStatusName());
        jsonArray.add(jsonObject);

        jsonObject2.put("attendance_status", jsonArray);

        return jsonObject.toString();
    }
    
    // UPDATE AND SOFT DELETE
    @PutMapping("/update/{id}")
    @ApiOperation(value = "${AttendanceStatusController.update}")
    public String updateAttendanceStatus(@RequestBody AttendanceStatus attendanceStatus, @PathVariable int id) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        
        Optional<AttendanceStatus> attendanceStatusOptional = attendanceStatusRepository.findById(id);
        if (!attendanceStatusOptional.isPresent()) {
            return "fail";
        }
        
        String name = attendanceStatusOptional.get().getAttendanceStatusName();
        String active = attendanceStatusOptional.get().getAttendanceStatusActive();
        
        attendanceStatus.setAttendanceStatusId(id);
        attendanceStatusRepository.save(attendanceStatus);
        
        String nameUpdate = attendanceStatusOptional.get().getAttendanceStatusName();
        String activeUpdate = attendanceStatusOptional.get().getAttendanceStatusActive();
        
        if (name.equals(nameUpdate) && active.equals(activeUpdate)) {
            jsonObject.put("status", "true");
            jsonObject.put("description", "there is no data udated");
            jsonArray.add(jsonObject);
            jsonObject2.put("status", jsonArray);

            return jsonObject.toString();
        } else {
            jsonObject.put("status", "true");
            jsonObject.put("description", "update successfully");
            jsonArray.add(jsonObject);

            jsonObject2.put("status", jsonArray);

            return jsonObject.toString();
        }
    }
    
     // CREATE
    @PostMapping("/insert")
    @ApiOperation(value = "${AttendanceStatusController.insert}")
    public String insertAttendance(@RequestBody AttendanceStatus attendanceStatus) {
        int beforeInsert = maxId();
        AttendanceStatus savedAS = attendanceStatusRepository.save(attendanceStatus);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(savedAS.getAttendanceStatusId()).toUri();
        int afterInsert = maxId();

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();

        if (afterInsert > beforeInsert) {
            jsonObject.put("status", "true");
            jsonObject.put("description", "insert successfully");
            jsonArray.add(jsonObject);
        } else{
            jsonObject.put("status", "false");
            jsonObject.put("description", "insert unsuccessfully");
            jsonArray.add(jsonObject);
        }

        jsonObject2.put("status", jsonArray);

        return jsonObject.toString();
    }
}
