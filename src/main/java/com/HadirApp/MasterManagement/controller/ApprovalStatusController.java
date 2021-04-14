/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.ApprovalStatus;
import com.HadirApp.MasterManagement.entity.Users;
import com.HadirApp.MasterManagement.repository.ApprovalStatusRepository;
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
 * @author herli
 */
@RestController
@RequestMapping("/api/master/approvalstatus")
@Api(tags = "Approval Status Management")
public class ApprovalStatusController {

    @Autowired
    private ApprovalStatusRepository repository;

    @Autowired
    private UsersRepository userRepository;

    public int maxApprovalStatus() {
        String maxApprovalStatusStr = repository.getMaxApprovalStatus();
        int maxApprovalStatus = Integer.parseInt(maxApprovalStatusStr);
        return maxApprovalStatus;
    }

    //read all where 
    @GetMapping("/getactiveapprovalstatus")
    @ApiOperation(value = "${ApprovalStatusController.getactiveapprovalstatus}")
    public String getActiveApprovalStatus(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId != 0) {
                System.out.println("you're authorized to access this operation");

                List<ApprovalStatus> ap = repository.getActiveApprovalStatus();

                JSONArray jSONArray = new JSONArray();
                JSONObject j = new JSONObject();

                for (ApprovalStatus a : ap) {
                    JSONObject jSONObject1 = new JSONObject();
                    jSONObject1.put("approvalStatusId", a.getApprovalStatusId());
                    jSONObject1.put("approvalStatusName", a.getApprovalStatusName());
                    jSONObject1.put("approvalStatusActive", a.getApprovalStatusActive());
                    jSONArray.add(jSONObject1);
                }
                j.put("approvalStatusList", jSONArray);

                return j.toString();

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

    //read by id
    @GetMapping("/getapprovalstatus/{id}")
    @ApiOperation(value = "${ApprovalStatusController.getapprovalstatusbyid}")
    public String getApprovalStatusByID(@PathVariable int id,
            @RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId != 0) {
                System.out.println("you're authorized to access this operation");

                Optional<ApprovalStatus> app = repository.findById(id);
                if (!app.isPresent()) {
                    System.out.println("approval status not found");
                }

                JSONArray jSONArray = new JSONArray();

                JSONObject jSONObject1 = new JSONObject();

                jSONObject.put("approvalStatusId", app.get().getApprovalStatusId());
                jSONObject.put("approvalStatusName", app.get().getApprovalStatusName());
                jSONObject.put("approvalStatusActive", app.get().getApprovalStatusName());
                jSONArray.add(jSONObject);

                jSONObject1.put("approvalStatus", jSONArray);

                return jSONObject1.toString();

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

    //update
    @PutMapping("/updateapprovalstatus/{id}")
    @ApiOperation(value = "${ApprovalStatusController.update}")
    public String updateApprovalStatus(@RequestBody ApprovalStatus appstatus,
            @PathVariable int id, @RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId != 0) {
                System.out.println("you're authorized to access this operation");

                JSONObject jsonObject = new JSONObject();

                Optional<ApprovalStatus> appOptional = repository.findById(id);

                if (!appOptional.isPresent()) {
                    jsonObject.put("status", "false");
                    jsonObject.put("description", "update unsuccessfully, ID Not Found");

                    return jsonObject.toString();
                }
                String name = appOptional.get().getApprovalStatusName();
                String active = appOptional.get().getApprovalStatusName();

                appstatus.setApprovalStatusId(id);
                repository.save(appstatus);

                String nameUpdate = appOptional.get().getApprovalStatusName();
                String activeUpdate = appOptional.get().getApprovalStatusName();

                if (name.equals(nameUpdate) && active.equals(activeUpdate)) {
                    jsonObject.put("status", "true");
                    jsonObject.put("description", "there is no data udated");

                    return jsonObject.toString();
                } else {
                    jsonObject.put("status", "true");
                    jsonObject.put("description", "update successfully");

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

    //create
    @PostMapping("/insertapprovalstatus")
    @ApiOperation(value = "${ApprovalStatusController.insert}")
    public String insertApprovalStatus(@RequestBody ApprovalStatus approvalStatus,
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

    //read
    @GetMapping("/getapprovalstatus")
    @ApiOperation(value = "${ApprovalStatusController.getapprovalstatus}")
    public String getAllApprovalStatus(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId != 0) {
                System.out.println("you're authorized to access this operation");

                List<ApprovalStatus> app = repository.findAll();

                JSONArray jSONArray = new JSONArray();
                JSONObject j = new JSONObject();

                for (ApprovalStatus a : app) {
                    
                    jSONObject.put("approvalStatusId", a.getApprovalStatusId());
                    jSONObject.put("approvalStatusName", a.getApprovalStatusName());
                    jSONObject.put("approvalStatusActive", a.getApprovalStatusActive());
                    jSONArray.add(jSONObject);
                }
                j.put("approvalStatusList", jSONArray);

                return j.toString();

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
