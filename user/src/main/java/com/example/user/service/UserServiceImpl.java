package com.example.user.service;

import com.example.user.DTOs.UserRequestDTO;
import com.example.user.model.User;

import java.util.List;

public interface UserServiceImpl {

    public List<User> findAll();
    public String addUserRecord(UserRequestDTO requestDTO);
    public String updateUser(User userInfo);
    public List<User> findUsersByUserName(String userName);
    public User findByUserName(String userName);
    public String deleteUser(String userName);
}
