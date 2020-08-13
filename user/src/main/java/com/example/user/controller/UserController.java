package com.example.user.controller;


import com.example.user.DTOs.UserRequestDTO;
import com.example.user.model.User;
import com.example.user.service.ServiceUtils;
import com.example.user.service.UserServiceImpl;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/getUsers")
    @ResponseBody
    public List<User> getUsers(@RequestHeader("session-id") String sessionId) {
        try {
            ThreadContext.put("sessionId",sessionId);
            log.debug("received a request to get all users with sessionId {}", sessionId);
            return userService.findAll();
        }
        finally {
            // clear the thread context retained for next request
            ThreadContext.clearMap();
        }
    }

    @GetMapping("/getUsers/{username}")
    @ResponseBody
    public List<User> getUsersByUserName(@RequestHeader("session-id") String sessionId,  @PathVariable("username") String username) {
        try {
            ThreadContext.put("sessionId",sessionId);
            log.debug("Request received to get user Info for username {} with sesssionId {}", username, sessionId);
            return userService.findUsersByUserName(username);
        }
        finally {
            // clear the thread context retained for next request
            ThreadContext.clearMap();
        }
    }


    @PostMapping("/addUser")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UUID> createUser(@RequestHeader("session-id") String sessionId, @RequestBody @Valid UserRequestDTO userRequestDTO){
        try {
            ThreadContext.put("sessionId", sessionId);
            log.debug("Request received to add new user with username {} with sesssionId {}", userRequestDTO.getUser_name(), sessionId);
            User user = userService.findByUserName(userRequestDTO.getUser_name());
            if (user != null) {
                return new ResponseEntity(("user " + userRequestDTO.getUser_name() + " already exists"), HttpStatus.CONFLICT);
            }
            return new ResponseEntity(userService.addUserRecord(userRequestDTO), HttpStatus.CREATED);
        }
        finally {
            // clear the thread context retained for next request
            ThreadContext.clearMap();
        }
    }


    @PostMapping("/updateUser")
    public ResponseEntity<UUID> updateUser(@RequestHeader("session-id") String sessionId, @RequestBody UserRequestDTO userRequestDTO)  {
        try {
            ThreadContext.put("sessionId", sessionId);
            log.debug("received a request to update user {} with sessionId {}", userRequestDTO.getUser_name(), sessionId);
            User user = userService.findByUserName(userRequestDTO.getUser_name());
            if (user == null) {
                return new ResponseEntity(("user " + userRequestDTO.getUser_name() + " not found"), HttpStatus.NOT_FOUND);
            } else {
                user.setAccounts(ServiceUtils.accountDTOToObject(userRequestDTO.getAccounts()));
                user.setEmail_id(userRequestDTO.getEmail_id());
                user.setPswd(userRequestDTO.getPassword());
                return new ResponseEntity(userService.updateUser(user), HttpStatus.OK);
            }
        }
        finally {
            // clear the thread context retained for next request
            ThreadContext.clearMap();
        }
    }

    @DeleteMapping("/deleteUser/{userName}")
    @ResponseStatus(HttpStatus.GONE)
    public ResponseEntity<String> deleteUser(@RequestHeader("session-id") String sessionId, @PathVariable("userName") String userName) {
        try {
            ThreadContext.put("sessionId", sessionId);
            log.debug("Request received to delete user with username {} with sesssionId {}", userName, sessionId);
            User user = userService.findByUserName(userName);
            if (user == null) {
                return new ResponseEntity(("user " + userName + " not found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(userService.deleteUser(userName), HttpStatus.GONE);
        }
        finally {
            // clear the thread context retained for next request
            ThreadContext.clearMap();
        }

    }

}
