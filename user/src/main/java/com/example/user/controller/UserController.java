package com.example.user.controller;


import com.example.user.DTOs.AccountDTO;
import com.example.user.DTOs.UserRequestDTO;
import com.example.user.model.Account;
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
import javax.websocket.server.PathParam;
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
    public ResponseEntity<List<User>> getUsersByUserName(@RequestHeader("session-id") String sessionId,  @PathVariable("username") String username) {
        try {
            ThreadContext.put("sessionId",sessionId);
            log.debug("Request received to get user Info for username {} with sesssionId {}", username, sessionId);
            User user = userService.findByUserName(username);
            if (user == null) {
                log.error("user " + username + " not found to in the request to getUsers by username");
                return new ResponseEntity(("user " + username + " not found"), HttpStatus.NOT_FOUND);
            } else {
                log.debug("Successfully returned the response for the call to get users by username");
                return new ResponseEntity(userService.findUsersByUserName(username), HttpStatus.OK);
            }

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
          //  ThreadContext.put("sessionId", sessionId);
            //log.debug("Request received to add new user with username {} with sesssionId {}", userRequestDTO.getUser_name(), sessionId);
            User user = userService.findByUserName(userRequestDTO.getUser_name());
            if (user != null) {
                log.error("user with username {} already exists, request rejected to add user", userRequestDTO.getUser_name());
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
    public ResponseEntity<Object> updateUser(@RequestHeader("session-id") String sessionId, @RequestBody  @Valid UserRequestDTO userRequestDTO)  {
        try {
            ThreadContext.put("sessionId", sessionId);
            log.debug("received a request to update user {} with sessionId {}", userRequestDTO.getUser_name(), sessionId);
            User user = userService.findByUserName(userRequestDTO.getUser_name());
            if (user == null) {
                log.error("user with username {} doesnt exist, request rejected to update user", userRequestDTO.getUser_name());
                return new ResponseEntity(("user " + userRequestDTO.getUser_name() + " not found"), HttpStatus.NOT_FOUND);
            } else {
                user.setAccounts(ServiceUtils.accountDTOToObject(userRequestDTO.getAccounts(), user));
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

    @PostMapping("/addUserAccount")
    public ResponseEntity<UUID> updateUserAccount(@RequestHeader("session-id") String sessionId, @RequestBody UserRequestDTO userRequestDTO)  {
        try {
            ThreadContext.put("sessionId", sessionId);
            log.debug("received a request to update user {} with sessionId {}", userRequestDTO.getUser_name(), sessionId);
            User user = userService.findByUserName(userRequestDTO.getUser_name());
            if (user == null) {
                log.error("UserName is invalid to add an account, username in the request {}",userRequestDTO.getUser_name());
                return new ResponseEntity(("user " + userRequestDTO.getUser_name() + " not found"), HttpStatus.NOT_FOUND);
            } else {
                // validate for accountType and accountNumber not null
                boolean invalidInput = ServiceUtils.validateAccountFields(userRequestDTO.getAccounts());
                if(invalidInput) {
                    log.error("Received null/blank value for an account type/account Number to be added, responding a bad request error");
                    return new ResponseEntity(("request to add userAccount has a null/bank accountType/accountType, rejected the request "), HttpStatus.BAD_REQUEST);
                }
                user.setAccounts(ServiceUtils.accountDTOToObject(userRequestDTO.getAccounts(), user));
                return new ResponseEntity(userService.updateUser(user), HttpStatus.OK);
            }
        }
        finally {
            // clear the thread context retained for next request
            ThreadContext.clearMap();
        }
    }

    @DeleteMapping("/deleteUserAccount/{id}")
    public ResponseEntity<UUID> deleteUserAccount(@RequestHeader("session-id") String sessionId, @PathVariable("id") String accountId)  {
        try {
            ThreadContext.put("sessionId", sessionId);
            log.debug("received a request to delete user Account with accountId {} and  sessionId {}", accountId, sessionId);
            Account account = userService.findByAccountId(accountId);
            if (account == null) {
                log.error("uaccountId to delete account is invalid in the request, rejecting");
                return new ResponseEntity(("Account Id " + accountId + " not found"), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity(userService.deleteUserAccount(accountId), HttpStatus.OK);
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
                log.error("user with username {} not found, request rejected to delete user", userName);
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
