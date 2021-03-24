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
@Api(tags = "DivisionManagement")
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
            jSONObject.put("id_division", div.getDivisionId());
            jSONObject.put("name_division", div.getDivisionName());
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
            jSONObject.put("id_division", div.getDivisionId());
            jSONObject.put("name_division", div.getDivisionName());
            jSONArray.add(jSONObject);
        }
        j.put("division_list", jSONArray);

        return j.toString();
    }

    //read by id
    @GetMapping("/getdivision/{id}")
    @ApiOperation(value = "${DivisionController.getdivisionbyid}")
    public String getDivisionByID(@PathVariable int id) {

        Optional<Division> division = repository.findById(id);
        if (!division.isPresent()) {
            System.out.println("role not found");
        }

        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject1 = new JSONObject();

        jSONObject.put("id_division", division.get().getDivisionId());
        jSONObject.put("name_division", division.get().getDivisionName());
        jSONArray.add(jSONObject);
        
        jSONObject1.put("division", jSONArray);

        return jSONObject1.toString();

    }
    
    //update
    @PutMapping("/update/{id}")
    @ApiOperation(value="DivisionController.update")
    public String updateDivision(@RequestBody Division division, @PathVariable int id){
        Optional<Division> divOptional = repository.findById(id);
        
        if(!divOptional.isPresent()){
            return "fail";
        }
        
        division.setDivisionId(id);
        repository.save(division);
        
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();

        jsonObject.put("status", "true");
        jsonObject.put("description", "update successfully");
        jsonArray.add(jsonObject);

        jsonObject2.put("status", jsonArray);

        //return ResponseEntity.noContent().build();
        return jsonObject2.toString();
    }
    
    //create
    @PostMapping("/insert")
    @ApiOperation(value="${DivisionController.insert}")
    public String insertDivision(@RequestBody Division division){
        int beforeInsert = maxDivision();
        Division saveDivision = repository.save(division);
        
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(saveDivision.getDivisionId()).toUri();
        int afterInsert = maxDivision();
        
        
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject1 = new JSONObject();
        
        if(afterInsert > beforeInsert){
            jSONObject.put("status", "true");
            jSONObject.put("description", "insert successfully");
            jSONArray.add(jSONObject);
            
        }else{
            jSONObject.put("status", "false");
            jSONObject.put("description", "insert unsuccessfully");
            jSONArray.add(jSONObject);
        }
        
        jSONObject1.put("status", jSONArray);
        
        return jSONObject1.toString();
    }
    

}
