/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.HadirApp.MasterManagement.controller;

import com.HadirApp.MasterManagement.entity.Division;
import com.HadirApp.MasterManagement.entity.Role;
import com.HadirApp.MasterManagement.entity.Users;
import com.HadirApp.MasterManagement.repository.UsersRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Date;
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

/**
 *
 * @author herli
 */
@RestController
@RequestMapping("/api/master/users")
@Api(tags = "UserManagement")
public class UsersController {

    @Autowired
    private UsersRepository repository;

    @GetMapping("/getuser")
    @ApiOperation(value = "${UsersController.getuser}")
    public String getAllUser() {
        List<Users> users = repository.findAll();

        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();

        for (Users u : users) {
            JSONObject jSONObject1 = new JSONObject();

            jSONObject1.put("id", u.getUserId());
            jSONObject1.put("username", u.getUserFullname());
            jSONObject1.put("email", u.getUserEmail());
            jSONObject1.put("divisionID", u.getDivisionId().getDivisionId());
            jSONObject1.put("divisionName", u.getDivisionId().getDivisionName());
            jSONObject1.put("roleID", u.getRoleId().getRoleId());
            jSONObject1.put("roleName", u.getRoleId().getRoleName());
            jSONObject1.put("activeStatus", u.getUserActive());

            jSONArray.add(jSONObject1);
        }

        jSONObject.put("users", jSONArray);
        return jSONObject.toString();

    }

    @GetMapping("/getusersactive")
    @ApiOperation(value = "${UsersController.getusersactive}")
    public String getUsersActive() {
        List<Users> users = repository.getActiveUsers();
        JSONArray jsona = new JSONArray();
        JSONObject jsono = new JSONObject();

        for (Users u : users) {

            JSONObject jSONObject1 = new JSONObject();
            jSONObject1.put("id", u.getUserId());
            jSONObject1.put("username", u.getUserFullname());
            jSONObject1.put("email", u.getUserEmail());
            jSONObject1.put("divisionID", u.getDivisionId().getDivisionId());
            jSONObject1.put("divisionName", u.getDivisionId().getDivisionName());
            jSONObject1.put("roleID", u.getRoleId().getRoleId());
            jSONObject1.put("roleName", u.getRoleId().getRoleName());
            jSONObject1.put("activeStatus", u.getUserActive());

            jsona.add(jSONObject1);
        }
        jsono.put("users", jsona);

        return jsono.toString();
    }

    @GetMapping("/getuser/{id}")
    @ApiOperation(value = "${UsersController.getuserbyid}")
    public String getUserByID(@PathVariable String id) {

        Optional<Users> users = repository.getUsersByID(id);

        if (!users.isPresent()) {
            System.out.println("id not found");
        }
        JSONArray jsona = new JSONArray();
        JSONObject jsono = new JSONObject();
        JSONObject jSONObject1 = new JSONObject();

        jSONObject1.put("id", users.get().getUserId());
        jSONObject1.put("username", users.get().getUserFullname());
        jSONObject1.put("email", users.get().getUserEmail());
        jSONObject1.put("divisionID", users.get().getDivisionId().getDivisionId());
        jSONObject1.put("divisionName", users.get().getDivisionId().getDivisionName());
        jSONObject1.put("roleID", users.get().getRoleId().getRoleId());
        jSONObject1.put("roleName", users.get().getRoleId().getRoleName());
        jSONObject1.put("activeStatus", users.get().getUserActive());

        jsona.add(jSONObject1);
        jsono.put("users", jsona);
        return jsono.toString();
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "${UsersController.updatebyid}")
    public String updateUser(@RequestBody Map<String, ?> input, @PathVariable String id) {

//        Optional<Users> user = repository.getUsersByID(id);
        Users user = repository.getEntityUsersByID(id);
        if (user == null) {
            System.out.println("user not found");
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
                
        
        JSONArray jsona = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject1 = new JSONObject();

        jSONObject1.put("status", "true");
        jSONObject1.put("description", "update successfully");
        jsona.add(jSONObject1);

        jSONObject.put("status", jsona);

        return jSONObject.toString();

    }

    @PostMapping("/insert")
    @ApiOperation(value = "${UsersController.insert}")
    public String insertNewUser(@RequestBody Map<String, ?> input) {

        System.out.println("insert new user running");

        String userFullname = (String) input.get("userFullname");
        String userEmail = (String) input.get("userEmail");
        String userActive = (String) input.get("userActive");
        String userPhoto = (String) input.get("userPhoto");
        
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
        return "test";
    }
    
    @PutMapping("/changepassword/{id}")
    @ApiOperation(value="${UsersController.changepassword}")
    public String changePassword(@PathVariable String id, @RequestBody Map<String, ?> input){
        
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        
        String oldPassword = (String) input.get("oldPassword");
        String newPassword = (String) input.get("newPassword");
        
        Users user = repository.getEntityUsersByID(id);
        if(user == null ){
            return "user not found";
        }
        
        String activePassword = user.getUserPassword();
        
        boolean a = encoder.matches(oldPassword, activePassword);
        System.out.println("matches password: "+a);
        
        if(a){
            newPassword = encoder.encode(newPassword);
            user.setUserPassword(newPassword);
            repository.save(user);
            return "change password success";
        }
        return "change failed";
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

}
