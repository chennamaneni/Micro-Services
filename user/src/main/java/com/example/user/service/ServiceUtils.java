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

    public static List<Account> accountDTOToObject(List<AccountDTO> accounts, User user) {
//        List<Account> existingAccounts = user.getAccounts();
//        List<Account> accountsToAdd;
//        List<Account> accountsToDelete;
//        for(int i=0; i<accounts.size(); i++) {
//            for(int j=0; j<user.getAccounts().size(); j++) {
//                if(accounts.get(i).getAccountNumber().equals(user.getAccounts().get(j).getAccount_number())) {
//                    accounts.remove(i);
//                }
//            }
//        }
        List<Account>  allAccounts = new ArrayList<Account>();
        for(int i=0; i<accounts.size(); i++) {
            Account account = new Account();
            account.setAccount_number(accounts.get(i).getAccountNumber());
            account.setAccount_type(accounts.get(i).getAccountType());
            account.setUser(user);
            allAccounts.add(account);
        }
        return allAccounts;
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

    public static boolean validateAccountFields(List<AccountDTO> accounts) {
        for(int i=0; i<accounts.size(); i++) {
            if(accounts.get(i).getAccountNumber().isEmpty()|| accounts.get(i).getAccountType().isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
