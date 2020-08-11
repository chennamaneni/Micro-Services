package com.example.user.controller;


import com.example.user.DTOs.UserRequestDTO;
import com.example.user.model.User;
import com.example.user.service.ServiceUtils;
import com.example.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/getUsers")
    @ResponseBody
    public List<User> getUsers() {
        return userService.findAll();

    }

    @GetMapping("/getUsers/{username}")
    @ResponseBody
    public List<User> getUsersByUserName(@PathVariable("username") String username) {

        return userService.findUsersByUserName(username);
    }


    @PostMapping("/addUser")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        User user = userService.findByUserName(userRequestDTO.getUser_name());
        if(user != null) {
            return new ResponseEntity(("user "+userRequestDTO.getUser_name()+" already exists"), HttpStatus.CONFLICT);
        }
        return new ResponseEntity(userService.addUserRecord(userRequestDTO), HttpStatus.CREATED);
    }

//    @PostMapping("/updateUser")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<UUID> updateUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
//        return new ResponseEntity(userService.addUserRecord(userRequestDTO), HttpStatus.CREATED);
//    }

    @PostMapping("/updateUser")
    public ResponseEntity<UUID> updateUser(@RequestBody UserRequestDTO userRequestDTO)  {
        User user = userService.findByUserName(userRequestDTO.getUser_name());
        if(user == null) {
            return new ResponseEntity(("user "+userRequestDTO.getUser_name()+" not found"), HttpStatus.NOT_FOUND);
        }
        else {
            user.setAccounts(ServiceUtils.accountDTOToObject(userRequestDTO.getAccounts()));
            user.setEmail_id(userRequestDTO.getEmail_id());
            user.setPswd(userRequestDTO.getPassword());
            return new ResponseEntity(userService.updateUser(user), HttpStatus.OK);
        }

    }

    @DeleteMapping("/deleteUser/{userName}")
    @ResponseStatus(HttpStatus.GONE)
    public ResponseEntity<String> deleteUser(@PathVariable("userName") String userName){
        User user = userService.findByUserName(userName);
        if(user == null) {
            return new ResponseEntity(("user "+userName+" not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(userService.deleteUser(userName), HttpStatus.GONE);
    }

}
