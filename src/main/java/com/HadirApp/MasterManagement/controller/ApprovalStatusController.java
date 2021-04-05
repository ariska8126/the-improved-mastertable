/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.ApprovalStatus;
import com.HadirApp.MasterManagement.entity.Users;
import com.HadirApp.MasterManagement.repository.ApprovalStatusRepository;
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
 * @author herli
 */
@RestController
@RequestMapping("/api/master/approvalstatus")
@Api(tags = "Approval Status Management")
public class ApprovalStatusController {

    @Autowired
    private ApprovalStatusRepository repository;

    public int maxApprovalStatus() {
        String maxApprovalStatusStr = repository.getMaxApprovalStatus();
        int maxApprovalStatus = Integer.parseInt(maxApprovalStatusStr);
        return maxApprovalStatus;
    }

    //read all where 
    @GetMapping("/getactiveapprovalstatus")
    @ApiOperation(value = "${ApprovalStatusController.getactiveapprovalstatus}")
    public String getActiveApprovalStatus(@RequestHeader("bearer") String header) {

        System.out.println("===== Request /getactiveapprovalstatus in Master Management =====");
        JSONArray jSONArray = new JSONArray();
        JSONObject j = new JSONObject();
        int tokenExist = repository.findIfExistToken(header);

        if (tokenExist == 1) {
            System.out.println("===== Token is Exist =====");
            // get current date and userId
            Optional<Users> userIdObj = repository.getUsersByToken(header);
            String userId = userIdObj.get().getUserId();
            // get user role
            String userRole = repository.getUsersRole(userId);

            System.out.println("===== User Details =====");
            System.out.println("User Id : " + userId);
            System.out.println("User Role : " + userRole);

            // Check role
            if (userRole.equals("admin")) {

            } else {
                System.out.println("===== Access Denied =====");
                System.out.println("User Role : " + userRole);

                j.put("status", "false");
                j.put("description", "Access denied");
                j.put("roleName", userRole);
                return j.toJSONString();
            }
        } else {
            System.out.println("===== Wrong/Expire Token =====");
            j.put("status", "false");
            j.put("description", "you don't have authorization to access");
            return j.toJSONString();
        }

        // ACTION
        List<ApprovalStatus> ap = repository.getActiveApprovalStatus();

        for (ApprovalStatus a : ap) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("approvalStatusId", a.getApprovalStatusId());
            jSONObject.put("approvalStatusName", a.getApprovalStatusName());
            jSONObject.put("approvalStatusActive", a.getApprovalStatusActive());
            jSONArray.add(jSONObject);
        }
        j.put("approvalStatusList", jSONArray);

        return j.toString();
        // END ACTION

    }

    //read by id
    @GetMapping("/getapprovalstatus/{id}")
    @ApiOperation(value = "${ApprovalStatusController.getapprovalstatusbyid}")
    public String getApprovalStatusByID(@RequestHeader("bearer") String header, @PathVariable int id) {

        System.out.println("===== Request /getactiveapprovalstatus/{id} in Master Management =====");
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject1 = new JSONObject();

        int tokenExist = repository.findIfExistToken(header);

        if (tokenExist == 1) {
            System.out.println("===== Token is Exist =====");
            // get current date and userId
            Optional<Users> userIdObj = repository.getUsersByToken(header);
            String userId = userIdObj.get().getUserId();
            // get user role
            String userRole = repository.getUsersRole(userId);

            System.out.println("===== User Details =====");
            System.out.println("User Id : " + userId);
            System.out.println("User Role : " + userRole);

            // Check role
            if (userRole.equals("admin")) {
                // ACTION
                Optional<ApprovalStatus> app = repository.findById(id);
                if (!app.isPresent()) {
                    System.out.println("approval status not found");
                }
                jSONObject.put("approvalStatusId", app.get().getApprovalStatusId());
                jSONObject.put("approvalStatusName", app.get().getApprovalStatusName());
                jSONObject.put("approvalStatusActive", app.get().getApprovalStatusActive());
                jSONArray.add(jSONObject);

                jSONObject1.put("approvalStatus", jSONArray);

                return jSONObject1.toString();
                // END ACTION
            } else {
                System.out.println("===== Access Denied =====");
                System.out.println("User Role : " + userRole);

                jSONObject.put("status", "false");
                jSONObject.put("description", "Access denied");
                jSONObject.put("roleName", userRole);
                return jSONObject.toJSONString();
            }
        } else {
            System.out.println("===== Wrong/Expire Token =====");
            jSONObject.put("status", "false");
            jSONObject.put("description", "you don't have authorization to access");
            return jSONObject.toJSONString();
        }

    }

    //update
    @PutMapping("/updateapprovalstatus/{id}")
    @ApiOperation(value = "${ApprovalStatusController.update}")
    public String updateApprovalStatus(@RequestHeader("bearer") String header, @RequestBody ApprovalStatus appstatus, @PathVariable int id) {

        System.out.println("===== Request /getactiveapprovalstatus/{id} in Master Management =====");
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject1 = new JSONObject();

        int tokenExist = repository.findIfExistToken(header);

        if (tokenExist == 1) {
            System.out.println("===== Token is Exist =====");
            // get current date and userId
            Optional<Users> userIdObj = repository.getUsersByToken(header);
            String userId = userIdObj.get().getUserId();
            // get user role
            String userRole = repository.getUsersRole(userId);

            System.out.println("===== User Details =====");
            System.out.println("User Id : " + userId);
            System.out.println("User Role : " + userRole);

            // Check role
            if (userRole.equals("admin")) {
                // ACTION
                Optional<ApprovalStatus> appOptional = repository.findById(id);

                if (!appOptional.isPresent()) {
                    jSONObject.put("status", "false");
                    jSONObject.put("description", "update unsuccessfully, ID Not Found");

                    return jSONObject.toString();
                }
                String name = appOptional.get().getApprovalStatusName();
                String active = appOptional.get().getApprovalStatusName();

                appstatus.setApprovalStatusId(id);
                repository.save(appstatus);

                String nameUpdate = appOptional.get().getApprovalStatusName();
                String activeUpdate = appOptional.get().getApprovalStatusName();

                if (name.equals(nameUpdate) && active.equals(activeUpdate)) {
                    jSONObject.put("status", "true");
                    jSONObject.put("description", "there is no data udated");

                    return jSONObject.toString();
                } else {
                    jSONObject.put("status", "true");
                    jSONObject.put("description", "update successfully");

                    return jSONObject.toString();
                }
                // END ACTION
            } else {
                System.out.println("===== Access Denied =====");
                System.out.println("User Role : " + userRole);

                jSONObject.put("status", "false");
                jSONObject.put("description", "Access denied");
                jSONObject.put("roleName", userRole);
                return jSONObject.toJSONString();
            }
        } else {
            System.out.println("===== Wrong/Expire Token =====");
            jSONObject.put("status", "false");
            jSONObject.put("description", "you don't have authorization to access");
            return jSONObject.toJSONString();
        }

    }

    //create
    @PostMapping("/insertapprovalstatus")
    @ApiOperation(value = "${ApprovalStatusController.insert}")
    public String insertApprovalStatus(@RequestHeader("bearer") String header, @RequestBody ApprovalStatus approvalStatus) {
        System.out.println("===== Request /getactiveapprovalstatus/{id} in Master Management =====");
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject1 = new JSONObject();

        int tokenExist = repository.findIfExistToken(header);

        if (tokenExist == 1) {
            System.out.println("===== Token is Exist =====");
            // get current date and userId
            Optional<Users> userIdObj = repository.getUsersByToken(header);
            String userId = userIdObj.get().getUserId();
            // get user role
            String userRole = repository.getUsersRole(userId);

            System.out.println("===== User Details =====");
            System.out.println("User Id : " + userId);
            System.out.println("User Role : " + userRole);

            // Check role
            if (userRole.equals("admin")) {
                // ACTION
                int beforeInsert = maxApprovalStatus();
                ApprovalStatus saveapprovalStatus = repository.save(approvalStatus);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                        .buildAndExpand(saveapprovalStatus.getApprovalStatusId()).toUri();
                int afterInsert = maxApprovalStatus();

                if (afterInsert > beforeInsert) {
                    jSONObject.put("status", "true");
                    jSONObject.put("description", "insert successfully");

                } else {
                    jSONObject.put("status", "false");
                    jSONObject.put("description", "insert unsuccessfully");
                }
                return jSONObject.toString();
                // END ACTION
            } else {
                System.out.println("===== Access Denied =====");
                System.out.println("User Role : " + userRole);

                jSONObject.put("status", "false");
                jSONObject.put("description", "Access denied");
                jSONObject.put("roleName", userRole);
                return jSONObject.toJSONString();
            }
        } else {
            System.out.println("===== Wrong/Expire Token =====");
            jSONObject.put("status", "false");
            jSONObject.put("description", "you don't have authorization to access");
            return jSONObject.toJSONString();
        }
    }
}
