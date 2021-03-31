/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.AttendanceStatus;
import com.HadirApp.MasterManagement.entity.Users;
import com.HadirApp.MasterManagement.repository.AttendanceStatusRepository;
import com.HadirApp.MasterManagement.repository.UsersRepository;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author creative
 */
@RestController
@RequestMapping("/api/master/attendancestatus")
@Api(tags = "Attendance Status Managemet")
public class AttendanceStatusController {

    @Autowired
    AttendanceStatusRepository attendanceStatusRepository;

    @Autowired
    private UsersRepository userRepository;

    public int maxId() {
        String maxIdStr = attendanceStatusRepository.getMaxAttendance();
        int maxId = Integer.parseInt(maxIdStr);

        return maxId;
    }

    // READ
    @GetMapping("/getattendancestatus")
    @ApiOperation(value = "${AttendanceStatusController.getattendancestatus}")
    public String getAllAttendance(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1) {
                System.out.println("you're authorized to access this operation");

                List<AttendanceStatus> attendanceStatus = attendanceStatusRepository.findAll();

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject2 = new JSONObject();

                for (AttendanceStatus as : attendanceStatus) {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("attendanceStatusId", as.getAttendanceStatusId());
                    jsonObject.put("attendanceStatusName", as.getAttendanceStatusName());
                    jsonObject.put("attendanceStatusActive", as.getAttendanceStatusActive());

                    jsonArray.add(jsonObject);
                }

                jsonObject2.put("attendanceStatusList", jsonArray);

                return jsonObject2.toString();

            } else {
                System.out.println("access denied");
                jSONObject.put("status", "false");
                jSONObject.put("description", "you don't have authorization to access");

                return jSONObject.toJSONString();
            }
        }
        jSONObject.put("status", "false");
        jSONObject.put("description", "your token didn't authorized to access");

        return jSONObject.toJSONString();
    }

    // Read all where status = true
    @GetMapping("/getactiveattendancestatus")
    @ApiOperation(value = "${AttendanceStatusController.getactiveattendancestatus}")
    public String getActiveAttendance(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1) {
                System.out.println("you're authorized to access this operation");

                List<AttendanceStatus> attendanceStatus = attendanceStatusRepository.getActiveAttendance();

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject2 = new JSONObject();

                for (AttendanceStatus as : attendanceStatus) {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("attendanceStatusId", as.getAttendanceStatusId());
                    jsonObject.put("attendanceStatusName", as.getAttendanceStatusName());
                    jsonObject.put("attendanceStatusActive", as.getAttendanceStatusActive());

                    jsonArray.add(jsonObject);
                }

                jsonObject2.put("attendanceStatusList", jsonArray);

                return jsonObject2.toString();

            } else {
                System.out.println("access denied");
                jSONObject.put("status", "false");
                jSONObject.put("description", "you don't have authorization to access");

                return jSONObject.toJSONString();
            }
        }
        jSONObject.put("status", "false");
        jSONObject.put("description", "your token didn't authorized to access");

        return jSONObject.toJSONString();
    }

    // Read by ID
    @GetMapping("/getattendancestatus/{id}")
    @ApiOperation(value = "${AttendanceStatusController.getattendancebyid}")
    public String getAttendanceStatusById(@PathVariable int id,
            @RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1) {
                System.out.println("you're authorized to access this operation");

                Optional<AttendanceStatus> attendanceStatusOptional = attendanceStatusRepository.findById(id);

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject2 = new JSONObject();

                if (!attendanceStatusOptional.isPresent()) {
                    jsonObject.put("description", "data not found");
                    jsonObject.put("status", "false");
                    jsonArray.add(jsonObject);

                    jsonObject2.put("attendanceStatus", jsonArray);

                    return jsonObject.toString();
                }

                jsonObject.put("attendanceStatusId", attendanceStatusOptional.get().getAttendanceStatusId());
                jsonObject.put("attendanceStatusName", attendanceStatusOptional.get().getAttendanceStatusName());
                jsonObject.put("attendanceStatusActuve", attendanceStatusOptional.get().getAttendanceStatusActive());

                jsonArray.add(jsonObject);

                jsonObject2.put("attendanceStatus", jsonArray);

                return jsonObject.toString();

            } else {
                System.out.println("access denied");
                jSONObject.put("status", "false");
                jSONObject.put("description", "you don't have authorization to access");

                return jSONObject.toJSONString();
            }
        }
        jSONObject.put("status", "false");
        jSONObject.put("description", "your token didn't authorized to access");

        return jSONObject.toJSONString();
    }

    // UPDATE AND SOFT DELETE
    @PutMapping("/updateattendancestatus/{id}")
    @ApiOperation(value = "${AttendanceStatusController.update}")
    public String updateAttendanceStatus(@RequestBody AttendanceStatus attendanceStatus,
            @PathVariable int id, @RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1) {
                System.out.println("you're authorized to access this operation");

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

            } else {
                System.out.println("access denied");
                jSONObject.put("status", "false");
                jSONObject.put("description", "you don't have authorization to access");

                return jSONObject.toJSONString();
            }
        }
        jSONObject.put("status", "false");
        jSONObject.put("description", "your token didn't authorized to access");

        return jSONObject.toJSONString();
    }

    // CREATE
    @PostMapping("/insertattendancestatus")
    @ApiOperation(value = "${AttendanceStatusController.insert}")
    public String insertAttendance(@RequestBody AttendanceStatus attendanceStatus,
            @RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1) {
                System.out.println("you're authorized to access this operation");

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
                } else {
                    jsonObject.put("status", "false");
                    jsonObject.put("description", "insert unsuccessfully");
                    jsonArray.add(jsonObject);
                }

                jsonObject2.put("status", jsonArray);

                return jsonObject.toString();

            } else {
                System.out.println("access denied");
                jSONObject.put("status", "false");
                jSONObject.put("description", "you don't have authorization to access");

                return jSONObject.toJSONString();
            }
        }
        jSONObject.put("status", "false");
        jSONObject.put("description", "your token didn't authorized to access");

        return jSONObject.toJSONString();
    }
}
