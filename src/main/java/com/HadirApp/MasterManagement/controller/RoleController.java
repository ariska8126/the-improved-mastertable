/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.Role;
import com.HadirApp.MasterManagement.repository.RoleRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.management.relation.RoleNotFoundException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/master/role")
@Api(tags = "MasterManagemet")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    public int maxRole() {
        String maxRoleStr = roleRepository.getMaxRole();
        int maxRole = Integer.parseInt(maxRoleStr);

        return maxRole;
    }

    // READ
    @GetMapping("/getrole")
    @ApiOperation(value = "${RoleController.getrole}")
    public String getAllRole() {

        List<Role> role = roleRepository.findAll();

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();

        for (Role roles : role) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id_role", roles.getRoleId());
            jsonObject.put("name_role", roles.getRoleName());
            jsonArray.add(jsonObject);
        }

        jsonObject2.put("role_list", jsonArray);

        return jsonObject2.toString();
    }

    // Read all where status = true
    @GetMapping("/getactiverole")
    @ApiOperation(value = "${RoleController.getactiverole}")
    public String getActiveRole() {

        List<Role> role = roleRepository.getActiveRole();

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();

        for (Role roles : role) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id_role", roles.getRoleId());
            jsonObject.put("name_role", roles.getRoleName());
            jsonArray.add(jsonObject);
        }

        jsonObject2.put("role_list", jsonArray);

        return jsonObject2.toString();
    }

    // Read by ID
    @GetMapping("/getrole/{id}")
    @ApiOperation(value = "${RoleController.getrolebyid}")
    public String getRoleById(@PathVariable int id) throws RoleNotFoundException {
        Optional<Role> role = roleRepository.findById(id);
        if (!role.isPresent()) {
            throw new RoleNotFoundException("id-" + id);
        }

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();

        jsonObject.put("id_role", role.get().getRoleId());
        jsonObject.put("name_role", role.get().getRoleName());
        jsonArray.add(jsonObject);

        jsonObject2.put("role", jsonArray);

        return jsonObject.toString();
    }

    // UPDATE AND SOFT DELETE
    @PutMapping("/update/{id}")
    @ApiOperation(value = "${RoleController.update}")
    public String updateRole(@RequestBody Role role, @PathVariable int id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (!roleOptional.isPresent()) {
            return "fail";
        }

        role.setRoleId(id);
        roleRepository.save(role);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();

        jsonObject.put("status", "true");
        jsonObject.put("description", "update successfully");
        jsonArray.add(jsonObject);

        jsonObject2.put("status", jsonArray);

        //return ResponseEntity.noContent().build();
        return jsonObject.toString();
    }

    // CREATE
    @PostMapping("/insert")
    @ApiOperation(value = "${RoleController.insert}")
    public String insertRole(@RequestBody Role role) {
        int beforeInsert = maxRole();
        Role savedRole = roleRepository.save(role);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(savedRole.getRoleId()).toUri();
        int afterInsert = maxRole();

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
