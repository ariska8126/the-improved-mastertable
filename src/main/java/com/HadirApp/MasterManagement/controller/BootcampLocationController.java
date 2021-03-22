/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.BootcampLocation;
import com.HadirApp.MasterManagement.entity.Role;
import com.HadirApp.MasterManagement.repository.BootcampLocationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import javax.management.relation.RoleNotFoundException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author creative
 */
@RestController
@RequestMapping("/api/master/bootcamp")
@Api(tags = "Bootcamp Location Management")
public class BootcampLocationController {

    @Autowired
    BootcampLocationRepository bootcampLocationRepository;

    public int maxId() {
        String maxIdStr = bootcampLocationRepository.getMaxBootcamp();
        int maxId = Integer.parseInt(maxIdStr);

        return maxId;
    }

    // READ
    @GetMapping("/getbootcamplocation")
    @ApiOperation(value = "${BootcampLocationController.getbootcamplocation}")
    public String getAllBootcampLocation() {

        List<BootcampLocation> bootcampLocation = bootcampLocationRepository.findAll();

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();

        for (BootcampLocation bl : bootcampLocation) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bootcamp_location_id", bl.getBootcampLocationId());
            jsonObject.put("bootcamp_location_name", bl.getBootcampLocationName());
            jsonArray.add(jsonObject);
        }

        jsonObject2.put("bootcamp_location_list", jsonArray);

        return jsonObject2.toString();
    }

    // Read all where status = true
    @GetMapping("/getactivebootcamplocation")
    @ApiOperation(value = "${BootcampLocationController.getactivebootcamplocation}")
    public String getActiveBootcampLocation() {

        List<BootcampLocation> bootcampLocation = bootcampLocationRepository.getActiveBootcamp();

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();

        for (BootcampLocation bl : bootcampLocation) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bootcamp_location_id", bl.getBootcampLocationId());
            jsonObject.put("bootcamp_location_name", bl.getBootcampLocationName());
            jsonArray.add(jsonObject);
        }

        jsonObject2.put("bootcamp_location_list", jsonArray);

        return jsonObject2.toString();
    }

    // Read by ID
    @GetMapping("/getbootcamplocation/{id}")
    @ApiOperation(value = "${BootcampLocationController.getbootcamplocationbyid}")
    public String getBootcampLocationById(@PathVariable int id) {
        Optional<BootcampLocation> bootcampLocation = bootcampLocationRepository.findById(id);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();

        if (!bootcampLocation.isPresent()) {
            jsonObject.put("description", "data not found");
            jsonObject.put("status", "false");
            jsonArray.add(jsonObject);

            jsonObject2.put("bootcamp_location", jsonArray);

            return jsonObject.toString();
        }

        jsonObject.put("bootcamp_location_id", bootcampLocation.get().getBootcampLocationId());
        jsonObject.put("bootcamp_location_name", bootcampLocation.get().getBootcampLocationName());
        jsonArray.add(jsonObject);

        jsonObject2.put("bootcamp_location", jsonArray);

        return jsonObject.toString();
    }
}
