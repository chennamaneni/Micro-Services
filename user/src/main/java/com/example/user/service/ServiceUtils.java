package com.example.user.service;

import com.example.user.DTOs.AccountDTO;
import com.example.user.DTOs.UserRequestDTO;
import com.example.user.model.Account;
import com.example.user.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceUtils {

// unmodifiable list repository.save dont want fixed size list

//    public static List<Account> accountDTOToObject(List<AccountDTO> accounts) {
//        Account[] allAccounts = new Account[accounts.size()];
//        for(int i=0; i<accounts.size(); i++) {
//            Account account = new Account();
//            account.setAccount_number(accounts.get(i).getAccountNumber());
//            account.setAccount_type(accounts.get(i).getAccountType());
//            allAccounts[i] = account;
//        }
//        return Arrays.asList(allAccounts);
//    }

    public static List<Account> accountDTOToObject(List<AccountDTO> accounts) {
        List<Account>  allAccounts = new ArrayList<Account>();
        for(int i=0; i<accounts.size(); i++) {
            Account account = new Account();
            account.setAccount_number(accounts.get(i).getAccountNumber());
            account.setAccount_type(accounts.get(i).getAccountType());
            allAccounts.add(i, account);
        }
        return allAccounts;
    }

}
