/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.Attendance;
import com.HadirApp.MasterManagement.entity.Bootcamp;
import com.HadirApp.MasterManagement.entity.BootcampDetail;
import com.HadirApp.MasterManagement.entity.Division;
import com.HadirApp.MasterManagement.entity.Role;
import com.HadirApp.MasterManagement.entity.Users;
import com.HadirApp.MasterManagement.mail.SpringMailServices;
import com.HadirApp.MasterManagement.repository.BootcampDetailRepository;
import com.HadirApp.MasterManagement.repository.BootcampRepository;
import com.HadirApp.MasterManagement.repository.UsersRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *
 * @author herli
 */
@RestController
@RequestMapping("/api/master/users")
@Api(tags = "User Management")
public class UsersController {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private BootcampRepository bootcampRepository;

    @Autowired
    BootcampDetailRepository bootcampDetailRepository;

    @Autowired
    private SpringMailServices springMailServices;

    @GetMapping("/getuser")
    @ApiOperation(value = "${UsersController.getuser}")
    public String getAllUser(@RequestHeader("bearer") String header) {

        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = repository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1) {
                System.out.println("you're authorized to access this operation");

                List<Users> users = repository.findAll();

                for (Users u : users) {
                    JSONObject jSONObject1 = new JSONObject();
                    jSONObject1.put("userId", u.getUserId());
                    jSONObject1.put("userFullname", u.getUserFullname());
                    jSONObject1.put("userEmail", u.getUserEmail());
                    jSONObject1.put("userPhoto", u.getUserPhoto());
                    jSONObject1.put("divisionId", u.getDivisionId().getDivisionId());
                    jSONObject1.put("divisionName", u.getDivisionId().getDivisionName());
                    jSONObject1.put("roleId", u.getRoleId().getRoleId());
                    jSONObject1.put("roleName", u.getRoleId().getRoleName());
                    jSONObject1.put("userActive", u.getUserActive());
                    jSONArray.add(jSONObject1);
                }
                jSONObject.put("userList", jSONArray);
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

    @GetMapping("/getallemployee")
    @ApiOperation(value = "${UsersController.getallemployee}")
    public String getAllEmployee(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = repository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1 || roleId == 2 || roleId == 3 || roleId == 4) {
                System.out.println("you're authorized to access this operation");

                String role = "employee";

                JSONArray jsona = new JSONArray();
                JSONObject jsono = new JSONObject();

                List<Users> employee = repository.getUsersByRole(role);

                for (Users u : employee) {

                    JSONObject jSONObject1 = new JSONObject();
                    jSONObject1.put("userId", u.getUserId());
                    jSONObject1.put("userFullname", u.getUserFullname());
                    jSONObject1.put("userEmail", u.getUserEmail());
                    jSONObject1.put("userPhoto", u.getUserPhoto());
                    jSONObject1.put("divisionId", u.getDivisionId().getDivisionId());
                    jSONObject1.put("divisionName", u.getDivisionId().getDivisionName());
                    jSONObject1.put("roleId", u.getRoleId().getRoleId());
                    jSONObject1.put("roleName", u.getRoleId().getRoleName());
                    jSONObject1.put("userActive", u.getUserActive());

                    jsona.add(jSONObject1);
                }
                jsono.put("EmployeeList", jsona);
                jsono.put("status", "true");

                return jsono.toString();

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
    
    @GetMapping("/gettrainerandemployee")
    @ApiOperation(value = "Get all trainner and employee")
    public String getTrainnerAndEmployee(){
        JSONArray jsona = new JSONArray();
        JSONObject jsono = new JSONObject();
        
        Iterable<Users> users = repository.getTrainerAndEmployee();
        for (Users u : users) {

            JSONObject jSONObject1 = new JSONObject();
            jSONObject1.put("userId", u.getUserId());
            jSONObject1.put("userFullname", u.getUserFullname());
            jSONObject1.put("userEmail", u.getUserEmail());
            jSONObject1.put("userPhoto", u.getUserPhoto());
            jSONObject1.put("divisionId", u.getDivisionId().getDivisionId());
            jSONObject1.put("divisionName", u.getDivisionId().getDivisionName());
            jSONObject1.put("roleId", u.getRoleId().getRoleId());
            jSONObject1.put("roleName", u.getRoleId().getRoleName());
            jSONObject1.put("userActive", u.getUserActive());

            jsona.add(jSONObject1);
        }
        jsono.put("userList", jsona);
        jsono.put("status", "true");
        return jsono.toString();
        
    }

    @GetMapping("/getalltrainer")
    @ApiOperation(value = "${UsersController.getalltrainer}")
    public String getAllTrainer(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = repository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1 || roleId == 2 || roleId == 3) {
                System.out.println("you're authorized to access this operation");

                String role = "trainer";

                JSONArray jsona = new JSONArray();
                JSONObject jsono = new JSONObject();

                List<Users> employee = repository.getUsersByRole(role);
                System.out.println("test get trainer");

                for (Users u : employee) {

                    JSONObject jSONObject1 = new JSONObject();
                    jSONObject1.put("userId", u.getUserId());
                    jSONObject1.put("userFullname", u.getUserFullname());
                    jSONObject1.put("userEmail", u.getUserEmail());
                    jSONObject1.put("userPhoto", u.getUserPhoto());
                    jSONObject1.put("divisionId", u.getDivisionId().getDivisionId());
                    jSONObject1.put("divisionName", u.getDivisionId().getDivisionName());
                    jSONObject1.put("roleId", u.getRoleId().getRoleId());
                    jSONObject1.put("roleName", u.getRoleId().getRoleName());
                    jSONObject1.put("userActive", u.getUserActive());

                    jsona.add(jSONObject1);
                }
                jsono.put("trainnerList", jsona);
                jsono.put("status", "true");

                return jsono.toString();

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

    @GetMapping("/getallmanageractive")
    @ApiOperation(value = "${UsersController.getallmanagera}")
    public String getAllManagerActive(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = repository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1) {
                System.out.println("you're authorized to access this operation");

                String role = "manager active";

                JSONArray jsona = new JSONArray();
                JSONObject jsono = new JSONObject();

                List<Users> employee = repository.getUsersByRole(role);
                System.out.println("test get user");

                for (Users u : employee) {

                    JSONObject jSONObject1 = new JSONObject();
                    jSONObject1.put("userId", u.getUserId());
                    jSONObject1.put("userFullname", u.getUserFullname());
                    jSONObject1.put("userEmail", u.getUserEmail());
                    jSONObject1.put("userPhoto", u.getUserPhoto());
                    jSONObject1.put("divisionId", u.getDivisionId().getDivisionId());
                    jSONObject1.put("divisionName", u.getDivisionId().getDivisionName());
                    jSONObject1.put("roleId", u.getRoleId().getRoleId());
                    jSONObject1.put("roleName", u.getRoleId().getRoleName());
                    jSONObject1.put("userActive", u.getUserActive());

                    jsona.add(jSONObject1);
                }
                jsono.put("managerActiveList", jsona);
                jsono.put("status", "true");

                return jsono.toString();
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

    @GetMapping("/getallmanagerpassive")
    @ApiOperation(value = "${UsersController.getallmanagerp}")
    public String getAllManagerPassive(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = repository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1) {
                System.out.println("you're authorized to access this operation");

                String role = "manager passive";

                JSONArray jsona = new JSONArray();
                JSONObject jsono = new JSONObject();

                List<Users> employee = repository.getUsersByRole(role);
                System.out.println("test get user");

                for (Users u : employee) {

                    JSONObject jSONObject1 = new JSONObject();
                    jSONObject1.put("userId", u.getUserId());
                    jSONObject1.put("userFullname", u.getUserFullname());
                    jSONObject1.put("userEmail", u.getUserEmail());
                    jSONObject1.put("userPhoto", u.getUserPhoto());
                    jSONObject1.put("divisionId", u.getDivisionId().getDivisionId());
                    jSONObject1.put("divisionName", u.getDivisionId().getDivisionName());
                    jSONObject1.put("roleId", u.getRoleId().getRoleId());
                    jSONObject1.put("roleName", u.getRoleId().getRoleName());
                    jSONObject1.put("userActive", u.getUserActive());

                    jsona.add(jSONObject1);
                }
                jsono.put("managerPassiveList", jsona);
                jsono.put("status", "true");

                return jsono.toString();
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

    @GetMapping("/getalladmin")
    @ApiOperation(value = "${UsersController.getalladmin}")
    public String getAllAdmin(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = repository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1) {
                System.out.println("you're authorized to access this operation");

                String role = "admin";

                JSONArray jsona = new JSONArray();
                JSONObject jsono = new JSONObject();

                List<Users> employee = repository.getUsersByRole(role);
                System.out.println("test get user");

                for (Users u : employee) {

                    JSONObject jSONObject1 = new JSONObject();
                    jSONObject1.put("userId", u.getUserId());
                    jSONObject1.put("userFullname", u.getUserFullname());
                    jSONObject1.put("userEmail", u.getUserEmail());
                    jSONObject1.put("userPhoto", u.getUserPhoto());
                    jSONObject1.put("divisionId", u.getDivisionId().getDivisionId());
                    jSONObject1.put("divisionName", u.getDivisionId().getDivisionName());
                    jSONObject1.put("roleId", u.getRoleId().getRoleId());
                    jSONObject1.put("roleName", u.getRoleId().getRoleName());
                    jSONObject1.put("userActive", u.getUserActive());

                    jsona.add(jSONObject1);
                }
                jsono.put("adminList", jsona);
                jsono.put("status", "true");

                return jsono.toString();
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

    @GetMapping("/getusersactive")
    @ApiOperation(value = "${UsersController.getusersactive}")
    public String getUsersActive(@RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = repository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1 || roleId == 2 || roleId == 3 || roleId == 4) {
                System.out.println("you're authorized to access this operation");

                List<Users> users = repository.getActiveUsers();
                JSONArray jsona = new JSONArray();
                JSONObject jsono = new JSONObject();

                for (Users u : users) {

                    JSONObject jSONObject1 = new JSONObject();
                    jSONObject1.put("userId", u.getUserId());
                    jSONObject1.put("userFullname", u.getUserFullname());
                    jSONObject1.put("userEmail", u.getUserEmail());
                    jSONObject1.put("userPhoto", u.getUserPhoto());
                    jSONObject1.put("divisionId", u.getDivisionId().getDivisionId());
                    jSONObject1.put("divisionName", u.getDivisionId().getDivisionName());
                    jSONObject1.put("roleId", u.getRoleId().getRoleId());
                    jSONObject1.put("roleName", u.getRoleId().getRoleName());
                    jSONObject1.put("userActive", u.getUserActive());

                    jsona.add(jSONObject1);
                }
                jsono.put("userActiveList", jsona);
                jsono.put("status", "true");

                return jsono.toString();

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

    @GetMapping("/getuserexpectadmin")
    @ApiOperation(value = "Get all users expect admin")
    public String getUsersExpectAdmin() {
        List<Users> users = repository.getActiveUsersExpectAdmin();
        JSONArray jsona = new JSONArray();
        JSONObject jsono = new JSONObject();

        for (Users u : users) {

            JSONObject jSONObject1 = new JSONObject();
            jSONObject1.put("userId", u.getUserId());
            jSONObject1.put("userFullname", u.getUserFullname());
            jSONObject1.put("userEmail", u.getUserEmail());
            jSONObject1.put("userPhoto", u.getUserPhoto());
            jSONObject1.put("divisionId", u.getDivisionId().getDivisionId());
            jSONObject1.put("divisionName", u.getDivisionId().getDivisionName());
            jSONObject1.put("roleId", u.getRoleId().getRoleId());
            jSONObject1.put("roleName", u.getRoleId().getRoleName());
            jSONObject1.put("userActive", u.getUserActive());

            jsona.add(jSONObject1);
        }
        jsono.put("userActiveList", jsona);

        return jsono.toString();
    }

    @GetMapping("/getemployeebytrainerbootcamp/{id}")
    @ApiOperation(value = "Get all employee related with trainner")
    public String getEmployeeByTrainner(@PathVariable String id) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        List<Bootcamp> bootcamp = bootcampRepository.getBootcamp(id);
        List<String> data = new ArrayList<String>();

        for (int i = 0; i < bootcamp.size(); i++) {
            data.add(bootcamp.get(i).getBootcampId());
            Iterable<Users> userByBootcamp = repository.getUserByBootcampTrainner(data.get(i));

            for (Users userData : userByBootcamp) {
                JSONObject jSONObject1 = new JSONObject();
                jSONObject1.put("userId", userData.getUserId());
                jSONObject1.put("userFullname", userData.getUserFullname());
                jSONObject1.put("userEmail", userData.getUserEmail());
                jSONObject1.put("userPhoto", userData.getUserPhoto());
                jSONObject1.put("divisionId", userData.getDivisionId().getDivisionId());
                jSONObject1.put("divisionName", userData.getDivisionId().getDivisionName());
                jSONObject1.put("roleId", userData.getRoleId().getRoleId());
                jSONObject1.put("roleName", userData.getRoleId().getRoleName());
                jSONObject1.put("userActive", userData.getUserActive());

                jsonArray.add(jSONObject1);
            }
            jsonObject.put("userList", jsonArray);
            jsonObject.put("status", "true");

            return jsonObject.toString();
        }
        
        jsonObject.put("status", "false");
        jsonObject.put("description", "data not found");

        return jsonObject.toJSONString();
    }

    @GetMapping("/getuser/{id}")
    @ApiOperation(value = "${UsersController.getuserbyid}")
    public String getUserByID(@RequestHeader("bearer") String header, @PathVariable String id) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = repository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1 || roleId == 2 || roleId == 3 || roleId == 4 || roleId == 5) {
                System.out.println("you're authorized to access this operation");

                Optional<Users> users = repository.getUsersByID(id);

                if (!users.isPresent()) {
                    System.out.println("id not found");
                }
                JSONArray jsona = new JSONArray();
                JSONObject jsono = new JSONObject();
                JSONObject jSONObject1 = new JSONObject();

                jSONObject1.put("userId", users.get().getUserId());
                jSONObject1.put("userFullname", users.get().getUserFullname());
                jSONObject1.put("userEmail", users.get().getUserEmail());
                jSONObject1.put("userPhoto", users.get().getUserPhoto());
                jSONObject1.put("divisionId", users.get().getDivisionId().getDivisionId());
                jSONObject1.put("divisionName", users.get().getDivisionId().getDivisionName());
                jSONObject1.put("roleId", users.get().getRoleId().getRoleId());
                jSONObject1.put("roleName", users.get().getRoleId().getRoleName());
                jSONObject1.put("userActive", users.get().getUserActive());

                jsona.add(jSONObject1);
                jsono.put("userList", jsona);
                return jsono.toString();

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
    
    @PutMapping("/updatephoto/{id}")
    @ApiOperation(value = "Udate users photo")
    public String updatePhoto(@RequestBody Map<String, ?> input, @PathVariable String id){
        String userPhoto = (String) input.get("userPhoto");
        
        repository.updateUserPhoto(userPhoto, id);
        
       JSONObject jSONObject1 = new JSONObject();

        jSONObject1.put("status", "true");
        jSONObject1.put("description", "update succefully");

        return jSONObject1.toJSONString(); 
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "${UsersController.updatebyid}")
    public String updateUser(@RequestHeader("bearer") String header, @RequestBody Map<String, ?> input,
            @PathVariable String id
    ) {
        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users userbyToken = repository.findUserByToken(header);
            System.out.println("user email: " + userbyToken.getUserEmail());
            int roleIdC = userbyToken.getRoleId().getRoleId();
            System.out.println("roleId: " + roleIdC);
            if (roleIdC == 1 || roleIdC == 2 || roleIdC == 3 || roleIdC == 4 || roleIdC == 5) {
                System.out.println("you're authorized to access this operation");

//        JSONObject jSONObject = new JSONObject();
                Iterable<Users> userlist = repository.getUsersListByID(id);
                System.out.println("user: " + userlist);
                Users user = repository.getEntityUsersByID(id);

                if (user == null) {

                    jSONObject.put("status", "false");
                    jSONObject.put("description", "update unsuccessfully, userId not found");

                    return jSONObject.toString();
                }

                String userFullname = (String) input.get("userFullname");
                String userEmail = (String) input.get("userEmail");
                String userActive = (String) input.get("userActive");
                String userPhoto = (String) input.get("userPhoto");

                String role = (String) input.get("roleId");
                String division = (String) input.get("divisionId");
                int roleId = Integer.parseInt(role);
                int divisionId = Integer.parseInt(division);

                user.setUserFullname(userFullname);
                user.setUserEmail(userEmail);
                user.setUserActive(userActive);
                user.setUserPhoto(userPhoto);
                user.setRoleId(new Role(roleId));
                user.setDivisionId(new Division(divisionId));
                repository.save(user);

                System.out.println("update berhasil");

                jSONObject.put("status", "true");
                jSONObject.put("description", "update successfully");

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

    @PostMapping("/insert")
    @ApiOperation(value = "${UsersController.insert}")
    public String insertNewUser(@RequestBody Map<String, ?> input,
            @RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = repository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleIdC = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleIdC);
            if (roleIdC == 1 || roleIdC == 2) {
                System.out.println("you're authorized to access this operation");

                System.out.println("insert new user running");

                String userFullname = (String) input.get("userFullname");
                String userEmail = (String) input.get("userEmail");
                String userActive = (String) input.get("userActive");
                String userPhoto = (String) input.get("userPhoto");

                int emailexist = repository.findIfExistEmail(userEmail);
                if (emailexist == 1) {
                    JSONObject jSONObject1 = new JSONObject();

                    jSONObject1.put("status", "false");
                    jSONObject1.put("description", "insert unsuccessfully, email already exist");

                    return jSONObject1.toString();
                }

                String role = (String) input.get("roleId");
                String division = (String) input.get("divisionId");
                int roleId = Integer.parseInt(role);
                int divisionId = Integer.parseInt(division);

                String newID = getAlphaNumericString(8);
                System.out.println("new generate ID: " + newID);
                int a = repository.findIfExistID(newID);
                System.out.println("if exist: " + a);
                if (a == 1) {
                    do {
                        newID = getAlphaNumericString(8);
                        System.out.println("iterate generate: " + newID);
                        a = repository.findIfExistID(newID);
                        System.out.println("if exist: " + a);
                    } while (a == 1);
                }
                System.out.println("newID: " + newID);

                String dummyPassword = UUID.randomUUID().toString();
                dummyPassword = dummyPassword.substring(0, 8);
                String dummyuserUnixcodeValue = UUID.randomUUID().toString();
                dummyuserUnixcodeValue = dummyuserUnixcodeValue.substring(0, 6);

                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

                String encodePassword = encoder.encode(dummyPassword);

                String userId = newID; //set user id
                String userPassword = encodePassword; //set user password
                String userUnixcodeValue = dummyuserUnixcodeValue; //set user unicodevalue
                Date userUnixcodeDate = new Date(); //set user UnixcodeDate

                Users users = new Users(userId, userFullname, userEmail, userPassword,
                        userActive, userUnixcodeValue, userUnixcodeDate, userPhoto,
                        new Role(roleId), new Division(divisionId));

                repository.save(users);
                System.out.println("new user saved");

                JSONObject jSONObject1 = new JSONObject();

                jSONObject1.put("status", "true");
                jSONObject1.put("description", "insert successfully");

                //send mail
                String sbj = "Metrodata Coding Camp New User";
                String title = "Welcome Aboard!";
//        String content = "Username: " + userEmail + ",\n Password: "+dummyPassword;
                String login = "https://www.instagram.com/";
                String content = "please change your password immediately!";
                String welcome = "We are gladly happy for accepting you as our new family in Metrodata Coding Camp, congratulation! Before you join with us, we will introduce you to our presence system called HadirApp. HadirApp will help us to know about attendance, here your email and password for your login requirement.";

                System.out.println("send mail running");

                Map<String, Object> model = new HashMap<>();
                model.put("title", title);
                model.put("name", userFullname);
                model.put("username", userEmail);
                model.put("password", dummyPassword);
                model.put("message", content);
                model.put("welcome", welcome);
                model.put("login", login);

//        model.put("login", login);
                springMailServices.sendMail(model, sbj, userEmail);
                System.out.println("mail sent");
                //mail

                return jSONObject1.toString();
        
        //mail
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

    @PostMapping("/insertemployee")
    public String insertEmployee(@RequestBody Map<String, ?> input,
            @RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = repository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleIdC = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleIdC);
            if (roleIdC == 1 || roleIdC == 2 || roleIdC == 4) {
                System.out.println("you're authorized to access this operation");

                String userFullname = (String) input.get("userFullname");
                String userEmail = (String) input.get("userEmail");
                String userActive = (String) input.get("userActive");
                String userPhoto = (String) input.get("userPhoto");
                String bootcampIds = (String) input.get("bootcampId");

                int emailexist = repository.findIfExistEmail(userEmail);
                if (emailexist == 1) {

                    JSONObject jSONObject1 = new JSONObject();

                    jSONObject1.put("status", "false");
                    jSONObject1.put("description", "insert unsuccessfully, email already exist");

                    return jSONObject1.toString();
                }

                String role = (String) input.get("roleId");
                String division = (String) input.get("divisionId");
                int roleId = Integer.parseInt(role);
                int divisionId = Integer.parseInt(division);

                String newID = getAlphaNumericString(8);
                System.out.println("new generate ID: " + newID);
                int a = repository.findIfExistID(newID);
                System.out.println("if exist: " + a);
                if (a == 1) {
                    do {
                        newID = getAlphaNumericString(8);
                        System.out.println("iterate generate: " + newID);
                        a = repository.findIfExistID(newID);
                        System.out.println("if exist: " + a);
                    } while (a == 1);
                }

                String dummyPassword = UUID.randomUUID().toString();
                dummyPassword = dummyPassword.substring(0, 8);
                String dummyuserUnixcodeValue = UUID.randomUUID().toString();
                dummyuserUnixcodeValue = dummyuserUnixcodeValue.substring(0, 6);

                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

                String encodePassword = encoder.encode(dummyPassword);

                String userId = newID; //set user id
                String userPassword = encodePassword; //set user password
                String userUnixcodeValue = dummyuserUnixcodeValue; //set user unicodevalue
                Date userUnixcodeDate = new Date(); //set user UnixcodeDate

                // Generate bootcamp detail id
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                Date date = new Date();
                String currentDate = formatter.format(date);
                String bootcampDetailId = newID + currentDate;

                // Save Emplolee 
                Users users = new Users(userId, userFullname, userEmail, userPassword,
                        userActive, userUnixcodeValue, userUnixcodeDate, userPhoto,
                        new Role(roleId), new Division(divisionId));

                // Save Bootcamp Detail
                BootcampDetail bootcampDetail = new BootcampDetail(bootcampDetailId, new Users(newID), new Bootcamp(bootcampIds));

                repository.save(users);
                bootcampDetailRepository.save(bootcampDetail);

                System.out.println("new user saved");

                JSONObject jSONObject1 = new JSONObject();

                jSONObject1.put("status", "true");
                jSONObject1.put("description", "insert successfully");

                //send mail
                String sbj = "Metrodata Coding Camp New User";
                String title = "Welcome Aboard!";
//        String content = "Username: " + userEmail + ",\n Password: "+dummyPassword;
                String login = "https://www.instagram.com/";
                String content = "please change your password immediately!";
                String welcome = "We are gladly happy for accepting you as our new family in Metrodata Coding Camp, congratulation! Before you join with us, we will introduce you to our presence system called HadirApp. HadirApp will help us to know about attendance, here your email and password for your login requirement.";

                System.out.println("send mail running");

                Map<String, Object> model = new HashMap<>();
                model.put("title", title);
                model.put("name", userFullname);
                model.put("username", userEmail);
                model.put("password", dummyPassword);
                model.put("message", content);
                model.put("welcome", welcome);

//        model.put("login", login);
                springMailServices.sendMail(model, sbj, userEmail);
                System.out.println("mail sent");
                //mail

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

    @PostMapping("/assigntrainer")
    @ApiOperation(value = "Assign trainer to bootcamp")
    public String assignEmployee(@RequestBody Map<String, ?> input) {
        String userId = (String) input.get("userId");
        String bootcampId = (String) input.get("bootcampId");

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        int existBootcamp = bootcampDetailRepository.findExistBootcampDetail(userId, bootcampId);
        if (existBootcamp == 0) {
            // Generate bootcamp detail id
            String newID = getAlphaNumericString(8);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            String currentDate = formatter.format(date);
            String bootcampDetailId = userId + bootcampId;

            // Save Bootcamp Detail
            BootcampDetail bootcampDetail = new BootcampDetail(bootcampDetailId, new Users(userId), new Bootcamp(bootcampId));
            bootcampDetailRepository.save(bootcampDetail);
            jsonObject.put("status", "true");
            jsonObject.put("description", "assign successfully");

            return jsonObject.toJSONString();
        } else {
            jsonObject.put("status", "false");
            jsonObject.put("description", "assign unsuccessfully, already exist");

            return jsonObject.toJSONString();
        }
    }

    @PutMapping("/changepassword/{id}")
    @ApiOperation(value = "${UsersController.changepassword}")
    public String changePassword(@PathVariable String id,
            @RequestBody Map<String, ?> input, @RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users userCek = repository.findUserByToken(header);
            System.out.println("user email: " + userCek.getUserEmail());
            int roleId = userCek.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1 || roleId == 2 || roleId == 3 || roleId == 4) {
                System.out.println("you're authorized to access this operation");

                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

                String oldPassword = (String) input.get("oldPassword");
                String newPassword = (String) input.get("newPassword");

                Users user = repository.getEntityUsersByID(id);
                if (user == null) {
                    JSONObject jSONObject1 = new JSONObject();

                    jSONObject1.put("status", "false");
                    jSONObject1.put("description", "update unsuccessfully, user not found");

                    return jSONObject1.toString();
                }

                String activePassword = user.getUserPassword();

                boolean a = encoder.matches(oldPassword, activePassword);
                System.out.println("matches password: " + a);

                if (a) {
                    newPassword = encoder.encode(newPassword);
                    user.setUserPassword(newPassword);
                    repository.save(user);
//            return "change password success";

                    JSONObject jSONObject1 = new JSONObject();

                    jSONObject1.put("status", "true");
                    jSONObject1.put("description", "your password has been successfully changed");

                    return jSONObject1.toString();
                }

                JSONObject jSONObject1 = new JSONObject();

                jSONObject1.put("status", "false");
                jSONObject1.put("description", "update unsuccessfully, your old password is invalid");

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
//        return "change failed";
    }

    @DeleteMapping("/deleteuser/{id}")
    @ApiOperation(value = "Hard delete users!")
    public String hardDeleteUser(@PathVariable String id, @RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = repository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1) {
                System.out.println("you're authorized to access this operation");

                repository.deleteUserById(id);

                JSONObject jSONObject1 = new JSONObject();

                jSONObject1.put("status", "true");
                jSONObject1.put("description", "delete succefully");

                return jSONObject1.toJSONString();

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

    @DeleteMapping("/cancelassign/{id}")
    @ApiOperation(value = "Cancel Assign Trainner")
    public String cancelAssignTrainer(@PathVariable String id) {

        repository.deleteTrainnerInBootcamp(id);

        JSONObject jSONObject1 = new JSONObject();

        jSONObject1.put("status", "true");
        jSONObject1.put("description", "trainer were unassign");

        return jSONObject1.toJSONString();

    }

    @GetMapping("/gettrainerbootcamp/{id}")
    @ApiOperation(value = "Get Bootcamp by Trainer")
    public String getTrainerBootcampList(@PathVariable String id,
            @RequestHeader("bearer") String header) {

        JSONObject jSONObject = new JSONObject();
        int cekIfExistToken = repository.findIfExistToken(header);
        System.out.println("exist token: " + cekIfExistToken);

        if (cekIfExistToken == 1) {
            Users user = repository.findUserByToken(header);
            System.out.println("user email: " + user.getUserEmail());
            int roleId = user.getRoleId().getRoleId();
            System.out.println("roleId: " + roleId);
            if (roleId == 1 || roleId == 2 || roleId == 3 || roleId == 4) {
                System.out.println("you're authorized to access this operation");

                JSONArray jsona = new JSONArray();
                JSONObject jsono = new JSONObject();

                List<Bootcamp> bootcamp = bootcampRepository.getBootcamp(id);

                if (bootcamp == null) {
                    JSONObject jSONObject1 = new JSONObject();

                    jSONObject1.put("status", "false");
                    jSONObject1.put("description", "trainer not found");
                    return jSONObject1.toString();

                }

                for (Bootcamp b : bootcamp) {

                    JSONObject jSONObject1 = new JSONObject();
                    jSONObject1.put("bootcampId", b.getBootcampId());
                    jSONObject1.put("bootcampActive", b.getBootcampActive());
                    jSONObject1.put("bootcampLocation", b.getBootcampLocation());
                    jSONObject1.put("bootcampName", b.getBootcampName());

                    jsona.add(jSONObject1);
                }
                jsono.put("bootcampList", jsona);

                return jsono.toString();
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

    @GetMapping("/getatteandancebytrainner/{id}")
    @ApiOperation(value = "Get Atttendance by Trainner")
    public String getAttendanceByTrainner(@PathVariable String id) {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();

        List<Bootcamp> bootcamp = bootcampRepository.getBootcamp(id);
        List<String> data = new ArrayList<String>();

        for (int i = 0; i < bootcamp.size(); i++) {
            data.add(bootcamp.get(i).getBootcampId());
            Iterable<Attendance> attendanceByBootcamp = repository.getAttendanceByBootcamp(data.get(i));

            for (Attendance attendance : attendanceByBootcamp) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("attendanceId", attendance.getAttendanceId());
                jsonObject.put("attendanceDate", attendance.getAttendanceDate().toString());
                jsonObject.put("attendanceTime", attendance.getAttendanceTime().toString());
                jsonObject.put("attendanceRemark", attendance.getAttendanceRemark());
                jsonObject.put("attendanceAttachment", attendance.getAttendanceAttachment());
                jsonObject.put("attendanceType", attendance.getAttendanceType());
                jsonObject.put("attendanceStatusId", attendance.getAttendanceStatusId().getAttendanceStatusId());
                jsonObject.put("attendanceStatusName", attendance.getAttendanceStatusId().getAttendanceStatusName());
                jsonObject.put("userId", attendance.getUserId().getUserId());
                jsonObject.put("userFullname", attendance.getUserId().getUserFullname());
                jsonObject.put("attendanceLongitude", attendance.getAttendanceLogitude());
                jsonObject.put("attendanceLatitude", attendance.getAttendanceLatitude());

                jsonArray.add(jsonObject);
            }

            jsonObject1.put("attendanceList", jsonArray);

            return jsonObject1.toString();
        }

        return "test";
    }

    static String getAlphaNumericString(int n) {
        // chose a Character random from this String 
        String AlphaNumericString = "0123456789";
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {// generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    static void fun(String... a) {
        System.out.println("Number of argument : " + a.length);

        List<String> result = new ArrayList<String>();

        for (String i : a) //System.out.print("'"+i+"'" + ",");
        {
            result.add(i);
        }
        System.out.println(result);

        System.out.println();
    }

    @GetMapping("/test")
    public String test() {
        String id = "56298879";
        List<Bootcamp> bootcamp = bootcampRepository.getBootcamp(id);
        List<String> data = new ArrayList<String>();

        for (int i = 0; i < bootcamp.size(); i++) {
            data.add(bootcamp.get(i).getBootcampId());
            Iterable<Attendance> attendanceByBootcamp = repository.getAttendanceByBootcamp(data.get(i));

            for (Attendance attendance : attendanceByBootcamp) {
                System.out.println(attendance.getAttendanceId());
            }
        }

        System.out.println(data);
        String inserted = "'";
        int index = 8;
        String value = data.toString().replace("[", "'").replace("]", "'").replace(" ", "'");

        System.out.println(insertString(value, inserted, index));

        String getParam = insertString(value, inserted, index);

        //fun(data);
        return "test";
    }

    public static String insertString(
            String originalString,
            String stringToBeInserted,
            int index) {

        // Create a new string
        String newString = new String();

        for (int i = 0; i < originalString.length(); i++) {

            // Insert the original string character
            // into the new string
            newString += originalString.charAt(i);

            if (i == index) {

                // Insert the string to be inserted
                // into the new string
                newString += stringToBeInserted;
            }
        }

        // return the modified String
        return newString;
    }

}
