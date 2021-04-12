/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.Bootcamp;
import com.HadirApp.MasterManagement.entity.Users;
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
import com.HadirApp.MasterManagement.repository.BootcampRepository;
import com.HadirApp.MasterManagement.repository.UsersRepository;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *
 * @author creative
 */
@RestController
@RequestMapping("/api/master/bootcamp")
@Api(tags = "Bootcamp Management")
public class BootcampController {

    @Autowired
    BootcampRepository bootcampRepository;

    @Autowired
    private UsersRepository userRepository;

    public int maxId() {
        String maxIdStr = bootcampRepository.getMaxBootcamp();
        int maxId = Integer.parseInt(maxIdStr);

        return maxId;
    }

    static String getNumericString(int n) {

        String NumericString = "0123456789";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index
                    = (int) (NumericString.length()
                    * Math.random());

            // add Character one by one in end of sb 
            sb.append(NumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    // READ
    @GetMapping("/getbootcamp")
    @ApiOperation(value = "${BootcampController.getbootcamp}")
    public String getAllBootcamp(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);

            if (roleId != 5) {
                System.out.println("you're authorized to access this operation");

                List<Bootcamp> bootcamp = bootcampRepository.findAll();

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject2 = new JSONObject();

                for (Bootcamp bl : bootcamp) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("bootcampId", bl.getBootcampId());
                    jsonObject.put("bootcampName", bl.getBootcampName());
                    jsonObject.put("bootcampLocation", bl.getBootcampLocation());
                    jsonObject.put("bootcampActive", bl.getBootcampActive());
                    jsonArray.add(jsonObject);
                }

                jsonObject2.put("bootcampList", jsonArray);

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
    @GetMapping("/getactivebootcamp")
    @ApiOperation(value = "${BootcampController.getactivebootcamp}")
    public String getActiveBootcamp(@RequestHeader("bearer") String header) {

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

                List<Bootcamp> bootcamp = bootcampRepository.getActiveBootcamp();

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject2 = new JSONObject();

                for (Bootcamp bl : bootcamp) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("bootcampId", bl.getBootcampId());
                    jsonObject.put("bootcampName", bl.getBootcampName());
                    jsonObject.put("bootcampLocation", bl.getBootcampLocation());
                    jsonObject.put("bootcampActive", bl.getBootcampActive());
                    jsonArray.add(jsonObject);
                }

                jsonObject2.put("bootcampList", jsonArray);

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
    @GetMapping("/getbootcamp/{id}")
    @ApiOperation(value = "${BootcampController.getbootcampbyid}")
    public String getBootcampById(@PathVariable String id,
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

                Optional<Bootcamp> bootcamp = bootcampRepository.findById(id);

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject2 = new JSONObject();

                if (!bootcamp.isPresent()) {
                    jsonObject.put("description", "data not found");
                    jsonObject.put("status", "false");
                    jsonArray.add(jsonObject);

                    jsonObject2.put("bootcamp", jsonArray);

                    return jsonObject.toString();
                }

                jsonObject.put("bootcampId", bootcamp.get().getBootcampId());
                jsonObject.put("bootcampName", bootcamp.get().getBootcampName());
                jsonObject.put("bootcampLocation", bootcamp.get().getBootcampLocation());
                jsonObject.put("bootcamoActive", bootcamp.get().getBootcampActive());
                jsonObject2.put("bootcamp", jsonArray);

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

    @GetMapping("/getbootcampbyuserid/{id}")
    @ApiOperation(value = "Get bootcamp by User Id")
    public String getBootcampByUserId(@PathVariable String id, @RequestHeader("bearer") String header) {
        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);

            if (roleId != 5) {
                Iterable<Bootcamp> bootcamp = bootcampRepository.getBootcampByUser(id);

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject2 = new JSONObject();

                if (jsonArray == null) {
                    jsonObject2.put("description", "data not found");
                    jsonObject2.put("status", "false");
                    jsonArray.add(jsonObject2);

                    jsonObject2.put("bootcamp", jsonArray);

                    return jsonObject2.toString();
                } else {
                    for (Bootcamp bl : bootcamp) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("bootcampId", bl.getBootcampId());
                        jsonObject.put("bootcampName", bl.getBootcampName());
                        jsonObject.put("bootcampLocation", bl.getBootcampLocation());
                        jsonObject.put("bootcampActive", bl.getBootcampActive());
                        jsonArray.add(jsonObject);
                    }

                    jsonObject2.put("bootcampList", jsonArray);

                    return jsonObject2.toString();
                }
            } else {
                System.out.println("access denied");
                jSONObject.put("status", "false");
                jSONObject.put("description", "you don't have authorization to access");

                return jSONObject.toJSONString();
            }
        } else {
            jSONObject.put("status", "false");
            jSONObject.put("description", "your token didn't authorized to access");

            return jSONObject.toJSONString();
        }
    }

    // UPDATE AND SOFT DELETE
    @PutMapping("/update/{id}")
    @ApiOperation(value = "${BootcampController.update}")
    public String updateBootcamp(@RequestBody Bootcamp bootcamp,
            @PathVariable String id, @RequestHeader("bearer") String header) {

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

                Optional<Bootcamp> bootcampOptional = bootcampRepository.findById(id);
                if (!bootcampOptional.isPresent()) {
                    return "fail";
                }

                String name = bootcampOptional.get().getBootcampName();
                String active = bootcampOptional.get().getBootcampActive();
                String location = bootcampOptional.get().getBootcampLocation();

                bootcamp.setBootcampId(id);
                bootcampRepository.save(bootcamp);

                String nameUpdate = bootcampOptional.get().getBootcampName();
                String activeUpdate = bootcampOptional.get().getBootcampActive();
                String locationUpdate = bootcampOptional.get().getBootcampLocation();

                if (name.equals(nameUpdate) && active.equals(activeUpdate) && location.equals(locationUpdate)) {
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
    @ApiOperation(value = "${BootcampController.insert}")
    public String insertBootcamp(@RequestBody Bootcamp bootcamp,
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

                // Generate bootcamp id
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
                Date date = new Date();
                String currentYear = formatter.format(date);
                String randomNumber = getNumericString(4);
                String idBootcamp = currentYear + randomNumber;

                bootcamp.setBootcampId(idBootcamp);
                Bootcamp savedBL = bootcampRepository.save(bootcamp);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                        .buildAndExpand(savedBL.getBootcampId()).toUri();
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
