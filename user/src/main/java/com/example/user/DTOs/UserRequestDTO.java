package com.example.user.DTOs;


import com.example.user.model.Account;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

public class UserRequestDTO {

    private long user_id;

    @Size(min=6, max=15, message = "User name length should be a minimum of 6 and a maximum of 15")
    @NotNull(message = "userName cannot be null or blank")
    @NotBlank(message = "userName cannot be null or blank")
    private String user_name;

    @NotNull(message = "email cannot be null or blank")
    @NotBlank(message = "email cannot be null or blank")
    @Email(message = "Email should be valid")
    private String email_id;

    @Size(min=8, max=15, message = "password length should be a minimum of 8 and a maximum of 15")
    @NotBlank(message = "password cannot be null or blank")
    @NotNull(message = "password cannot be null or blank")
    private String password;

    @NotNull(message = "Atleast one account is required to add a user")
    @Valid
    private List<AccountDTO> accounts;

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
