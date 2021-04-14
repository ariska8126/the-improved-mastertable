/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.CalendarHoliday;
import com.HadirApp.MasterManagement.entity.Users;
import com.HadirApp.MasterManagement.repository.HolidayRepository;
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
@RequestMapping("/api/master/holiday")
@Api(tags = "Holiday Calendar Management")
public class HolidayController {

    @Autowired
    HolidayRepository repository;

    @Autowired
    private UsersRepository userRepository;

    public int maxRole() {
        String maxRoleStr = repository.getCountHoliday();
        int maxRole = Integer.parseInt(maxRoleStr);

        return maxRole;
    }

    // READ
    @GetMapping("/getholiday")
    @ApiOperation(value = "${HolidayController.getholiday}")
    public String getAllHoliday(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1 || roleId == 2) {
                System.out.println("you're authorized to access this operation");

                List<CalendarHoliday> holiday = repository.findAll();

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject2 = new JSONObject();

                for (CalendarHoliday holy : holiday) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("calendarHolidayId", holy.getCalendarHolidayId());
                    jsonObject.put("calendarHolidayName", holy.getCalendarHolidayName().toString());
                    jsonObject.put("calendarHolidayRemark", holy.getCalendarHolidayRemark());
                    jsonObject.put("calendarHolidayActive", holy.getCalendarHolidayActive());
                    jsonArray.add(jsonObject);
                }

                jsonObject2.put("holidayList", jsonArray);

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
    @GetMapping("/getactiveholiday")
    @ApiOperation(value = "${HolidayController.getactiveholiday}")
    public String getActiveHoliday(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = userRepository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = userRepository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1 || roleId == 2) {
                System.out.println("you're authorized to access this operation");

                List<CalendarHoliday> holiday = repository.getActiveCalendarHoliday();

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject2 = new JSONObject();

                for (CalendarHoliday holy : holiday) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("calendarHolidayId", holy.getCalendarHolidayId());
                    jsonObject.put("calendarHolidayName", holy.getCalendarHolidayName().toString());
                    jsonObject.put("calendarHolidayRemark", holy.getCalendarHolidayRemark());
                    jsonObject.put("calendarHolidayActive", holy.getCalendarHolidayActive());
                    jsonArray.add(jsonObject);
                }

                jsonObject2.put("holidayList", jsonArray);

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
    @GetMapping("/getholiday/{id}")
    @ApiOperation(value = "${HolidayController.getholidaybyid}")
    public String getHolidayById(@PathVariable int id,
            @RequestHeader("bearer") String header) {

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

                Optional<CalendarHoliday> holiday = repository.findById(id);
                JSONObject jsonObject = new JSONObject();
                if (!holiday.isPresent()) {
                    jsonObject.put("status", "false");
                    jsonObject.put("description", "holiday not found");

                    return jsonObject.toString();
                }

                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject2 = new JSONObject();

                jsonObject.put("calendarHolidayId", holiday.get().getCalendarHolidayId());
                jsonObject.put("calendarHolidayName", holiday.get().getCalendarHolidayName().toString());
                jsonObject.put("calendarHolidayRemark", holiday.get().getCalendarHolidayRemark());
                jsonObject.put("calendarHolidayActive", holiday.get().getCalendarHolidayActive());
                jsonArray.add(jsonObject);

                jsonObject2.put("holiday", jsonArray);

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
    @ApiOperation(value = "${HolidayController.update}")
    public String updateRole(@RequestBody CalendarHoliday holiday,
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

                Optional<CalendarHoliday> holi = repository.findById(id);
                if (!holi.isPresent()) {
                    jsonObject.put("status", "false");
                    jsonObject.put("description", "update unsuccessfully, id not found");
                    jsonArray.add(jsonObject);
                }
                holiday.setCalendarHolidayId(id);
                repository.save(holiday);

                jsonObject.put("status", "true");
                jsonObject.put("description", "update successfully");
                jsonArray.add(jsonObject);
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

// CREATE
    @PostMapping("/insert")
    @ApiOperation(value = "${HolidayController.insert}")
    public String insertRole(@RequestBody CalendarHoliday holiday,
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

                int beforeInsert = maxRole();
                CalendarHoliday saved = repository.save(holiday);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                        .buildAndExpand(saved.getCalendarHolidayId()).toUri();
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
