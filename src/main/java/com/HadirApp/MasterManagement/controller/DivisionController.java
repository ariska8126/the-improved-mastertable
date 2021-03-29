/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.Division;
import com.HadirApp.MasterManagement.repository.DivisionRepository;
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
@RequestMapping("/api/master/division")
@Api(tags = "Division Management")
public class DivisionController {

    @Autowired
    private DivisionRepository repository;

    public int maxDivision() {
        String maxDivisionStr = repository.getMaxDivision();
        int maxDivision = Integer.parseInt(maxDivisionStr);
        return maxDivision;
    }

    //read
    @GetMapping("/getdivision")
    @ApiOperation(value = "${DivisionController.getdivision}")
    public String getAllDivision() {

        List<Division> division = repository.findAll();

        JSONArray jSONArray = new JSONArray();
        JSONObject j = new JSONObject();

        for (Division div : division) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("divisionId", div.getDivisionId());
            jSONObject.put("divisionName", div.getDivisionName());
            jSONObject.put("divisionActive", div.getDivisionActive());
            jSONArray.add(jSONObject);
        }
        j.put("division_list", jSONArray);

        return j.toString();
    }

    //read all where 
    @GetMapping("/getactivedivision")
    @ApiOperation(value = "${DivisionController.getactivedivision}")
    public String getActiveDivision() {
        List<Division> division = repository.getActiveDivision();

        JSONArray jSONArray = new JSONArray();
        JSONObject j = new JSONObject();

        for (Division div : division) {
            JSONObject jSONObject = new JSONObject();

            jSONObject.put("divisionId", div.getDivisionId());
            jSONObject.put("divisionName", div.getDivisionName());
            jSONObject.put("divisionActive", div.getDivisionActive());

            jSONArray.add(jSONObject);
        }
        j.put("divisionList", jSONArray);

        return j.toString();
    }

    //read by id
    @GetMapping("/getdivision/{id}")
    @ApiOperation(value = "${DivisionController.getdivisionbyid}")
    public String getDivisionByID(@PathVariable int id) {

        Optional<Division> division = repository.findById(id);
        if (!division.isPresent()) {
            System.out.println("division not found");
        }

        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject1 = new JSONObject();

        jSONObject.put("divisionId", division.get().getDivisionId());
        jSONObject.put("divisionName", division.get().getDivisionName());
        jSONObject.put("divisionActive", division.get().getDivisionActive());
        

        jSONArray.add(jSONObject);

        jSONObject1.put("division", jSONArray);

        return jSONObject1.toString();

    }

    //update
    @PutMapping("/update/{id}")
    @ApiOperation(value = "DivisionController.update")
    public String updateDivision(@RequestBody Division division, @PathVariable int id) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        Optional<Division> divOptional = repository.findById(id);

        if (!divOptional.isPresent()) {
            jsonObject.put("status", "false");
            jsonObject.put("description", "update unsuccessfully, division not found");

            return jsonObject.toString();
        }

        String name = divOptional.get().getDivisionName();
        String active = divOptional.get().getDivisionActive();

        division.setDivisionId(id);
        repository.save(division);

        String nameUpdate = divOptional.get().getDivisionName();
        String activeUpdate = divOptional.get().getDivisionActive();

        if (name.equals(nameUpdate) && active.equals(activeUpdate)) {
            jsonObject.put("status", "true");
            jsonObject.put("description", "there is no data udated");

            return jsonObject.toString();
        } else {
            jsonObject.put("status", "true");
            jsonObject.put("description", "update successfully");

            return jsonObject.toString();
        }
    }

    //create
    @PostMapping("/insert")
    @ApiOperation(value = "${DivisionController.insert}")
    public String insertDivision(@RequestBody Division division) {
        int beforeInsert = maxDivision();
        Division saveDivision = repository.save(division);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(saveDivision.getDivisionId()).toUri();
        int afterInsert = maxDivision();

        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject1 = new JSONObject();

        if (afterInsert > beforeInsert) {
            jSONObject.put("status", "true");
            jSONObject.put("description", "insert successfully");
            

        } else {
            jSONObject.put("status", "false");
            jSONObject.put("description", "insert unsuccessfully");
            
        }


        return jSONObject.toString();
    }

}
