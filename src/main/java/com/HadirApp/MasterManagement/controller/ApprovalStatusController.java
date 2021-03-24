/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.ApprovalStatus;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author herli
 */
@RestController
@RequestMapping("/api/master/approvalstatus")
@Api(tags = "ApprovalStatusManagement")
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
    public String getActiveApprovalStatus() {
        List<ApprovalStatus> ap = repository.getActiveApprovalStatus();

        JSONArray jSONArray = new JSONArray();
        JSONObject j = new JSONObject();

        for (ApprovalStatus a : ap) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("approval_status_id", a.getApprovalStatusId());
            jSONObject.put("approval_status_name", a.getApprovalStatusName());
            jSONArray.add(jSONObject);
        }
        j.put("approvalstatus_list", jSONArray);

        return j.toString();
    }

    //read by id
    @GetMapping("/getapprovalstatus/{id}")
    @ApiOperation(value = "${ApprovalStatusController.getapprovalstatusbyid}")
    public String getApprovalStatusByID(@PathVariable int id) {

        Optional<ApprovalStatus> app = repository.findById(id);
        if (!app.isPresent()) {
            System.out.println("role not found");
        }

        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject1 = new JSONObject();

        jSONObject.put("approval_status_id", app.get().getApprovalStatusId());
        jSONObject.put("approval_status_name", app.get().getApprovalStatusName());
        jSONArray.add(jSONObject);

        jSONObject1.put("approvalstatus", jSONArray);

        return jSONObject1.toString();

    }

    //update
    @PutMapping("update{id}")
    @ApiOperation(value = "${ApprovalStatusController.update}")
    public String updateApprovalStatus(@RequestBody ApprovalStatus appstatus, @PathVariable int id) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        
        Optional<ApprovalStatus> appOptional = repository.findById(id);

        if (!appOptional.isPresent()) {
            return "fail";
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

    //create
    @PostMapping("/insert")
    @ApiOperation(value = "${ApprovalStatusController.insert}")
    public String insertApprovalStatus(@RequestBody ApprovalStatus approvalStatus) {
        int beforeInsert = maxApprovalStatus();
        ApprovalStatus saveapprovalStatus = repository.save(approvalStatus);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(saveapprovalStatus.getApprovalStatusId()).toUri();
        int afterInsert = maxApprovalStatus();

        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject1 = new JSONObject();

        if (afterInsert > beforeInsert) {
            jSONObject.put("status", "true");
            jSONObject.put("description", "insert successfully");
            jSONArray.add(jSONObject);

        } else {
            jSONObject.put("status", "false");
            jSONObject.put("description", "insert unsuccessfully");
            jSONArray.add(jSONObject);
        }

        jSONObject1.put("status", jSONArray);

        return jSONObject1.toString();
    }

    //read
    @GetMapping("/getapprovalstatus")
    @ApiOperation(value = "${ApprovalStatusController.getapprovalstatus}")
    public String getAllApprovalStatus() {

        List<ApprovalStatus> app = repository.findAll();

        JSONArray jSONArray = new JSONArray();
        JSONObject j = new JSONObject();

        for (ApprovalStatus a : app) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", a.getApprovalStatusId());
            jSONObject.put("name", a.getApprovalStatusName());
            jSONArray.add(jSONObject);
        }
        j.put("approvalstatus_list", jSONArray);

        return j.toString();
    }

}
