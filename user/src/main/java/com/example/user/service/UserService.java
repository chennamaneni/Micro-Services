package com.example.user.service;

import com.example.user.DTOs.UserRequestDTO;
import com.example.user.model.Account;
import com.example.user.model.User;
import com.example.user.repository.AccountRepository;
import com.example.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements UserServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    public List<User> findUsersByUserName(String userName) {
        return (List<User>) repository.findUsersByUserName(userName);
    }

    @Override
    public User findByUserName(String userName) {
        return (User) repository.findByUserName(userName);
    }

    @Override
    public String addUserRecord(UserRequestDTO userInfo) {
        User addUser = new User();
        addUser.setUser_name(userInfo.getUser_name());
        addUser.setPswd(userInfo.getPassword());
        addUser.setEmail_id(userInfo.getEmail_id());
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        addUser.setCreate_date(currentTime);
        addUser.setLast_updated(currentTime);
        List<Account> accounts =  addAccount(userInfo, addUser);
        addUser.setAccounts(accounts);
        repository.save(addUser);
        log.debug("user {} added successfully", userInfo.getUser_name());
        return "user with username "+userInfo.getUser_name()+" saved successfully";

    }

    @Override
    public String updateUser(User userInfo) {
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        userInfo.setLast_updated(currentTime);
        repository.save(userInfo);
        log.debug("user {} updated successfully", userInfo.getUser_name());
        return "user "+userInfo.getUser_name()+" updated Successfully.";
    }

    public static List<Account> addAccount(UserRequestDTO userInfo, User user) {
        Account[] allAccounts = new Account[userInfo.getAccounts().size()];
        for(int i=0; i<userInfo.getAccounts().size(); i++) {
            Account account = new Account();
            account.setAccount_number(userInfo.getAccounts().get(i).getAccountNumber());
            account.setAccount_type(userInfo.getAccounts().get(i).getAccountType());
            account.setUser(user);
            allAccounts[i] = account;
        }
        return Arrays.asList(allAccounts);
    }


    @Override
    public String deleteUser(String userName) {
        repository.deleteUser(userName);
        log.debug("user %s deleted successfully", userName);
        return "user "+userName+" successfully deleted";
    }

    @Override
    public String deleteUserAccount(String accountId) {
        accountRepository.deleteUserAccountById(Long.parseLong(accountId));
        log.debug("account with id %s deleted successfully", accountId);
        return "account with id "+accountId+" successfully deleted";
    }


    @Override
    public Account findByAccountId(String accountId) {
        return (Account) accountRepository.findByAccountId(Long.parseLong(accountId));
    }

}

