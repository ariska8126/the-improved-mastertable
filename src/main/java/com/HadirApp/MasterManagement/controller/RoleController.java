/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.Role;
import com.HadirApp.MasterManagement.entity.Users;
import com.HadirApp.MasterManagement.repository.RoleRepository;
import com.HadirApp.MasterManagement.repository.UsersRepository;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author creative
 */
@RestController
@RequestMapping("/api/master/role")
@Api(tags = "Role Managemet")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private UsersRepository userRepository;

    public int maxRole() {
        String maxRoleStr = roleRepository.getMaxRole();
        int maxRole = Integer.parseInt(maxRoleStr);

        return maxRole;
    }

    // READ
    @GetMapping("/getrole")
    @ApiOperation(value = "${RoleController.getrole}")
    public String getAllRole(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1 || roleId == 4 || roleId == 2) {
                System.out.println("you're authorized to access this operation");

                List<Role> role = roleRepository.findAll();

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject2 = new JSONObject();

                for (Role roles : role) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("roleId", roles.getRoleId());
                    jsonObject.put("roleName", roles.getRoleName());
                    jsonObject.put("roleActive", roles.getRoleActive());
                    jsonArray.add(jsonObject);
                }

                jsonObject2.put("roleList", jsonArray);

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
    @GetMapping("/getactiverole")
    @ApiOperation(value = "${RoleController.getactiverole}")
    public String getActiveRole(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1 || roleId == 4 || roleId == 2) {
                System.out.println("you're authorized to access this operation");

                List<Role> role = roleRepository.getActiveRole();

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject2 = new JSONObject();

                for (Role roles : role) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("roleId", roles.getRoleId());
                    jsonObject.put("roleName", roles.getRoleName());
                    jsonObject.put("roleActive", roles.getRoleActive());
                    jsonArray.add(jsonObject);
                }

                jsonObject2.put("roleList", jsonArray);

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
    @GetMapping("/getrole/{id}")
    @ApiOperation(value = "${RoleController.getrolebyid}")
    public String getRoleById(@PathVariable int id,
            @RequestHeader("bearer") String header) throws RoleNotFoundException {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1 || roleId == 2 || roleId == 3 || roleId == 4 || roleId == 5) {
                System.out.println("you're authorized to access this operation");

                Optional<Role> role = roleRepository.findById(id);
                if (!role.isPresent()) {
                    throw new RoleNotFoundException("id-" + id);
                }

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject2 = new JSONObject();

                jsonObject.put("roleId", role.get().getRoleId());
                jsonObject.put("roleName", role.get().getRoleName());
                jsonObject.put("roleActive", role.get().getRoleActive());
                jsonArray.add(jsonObject);

                jsonObject2.put("role", jsonArray);

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
    @PutMapping("/update/{id}")
    @ApiOperation(value = "${RoleController.update}")
    public String updateRole(@RequestBody Role role, @PathVariable int id,
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

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject2 = new JSONObject();

                Optional<Role> roleOptional = roleRepository.findById(id);
                if (!roleOptional.isPresent()) {
                    jsonObject.put("status", "false");
                    jsonObject.put("description", "update unsuccessfully");
                    jsonArray.add(jsonObject);
                }

                String name = roleOptional.get().getRoleName();
                String active = roleOptional.get().getRoleActive();
                role.setRoleId(id);
                roleRepository.save(role);
                String nameUpdate = roleOptional.get().getRoleName();
                String activeUpdate = roleOptional.get().getRoleActive();

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
    @PostMapping("/insert")
    @ApiOperation(value = "${RoleController.insert}")
    public String insertRole(@RequestBody Role role,
            @RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1 || roleId == 2 || roleId == 3 || roleId == 4) {
                System.out.println("you're authorized to access this operation");

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