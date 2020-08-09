package com.example.user.DTOs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AccountDTO {

    @NotNull(message = "Account Type cannot be null or blank")
    @NotBlank(message = "Account Type cannot be null or blank")
    private String accountType;
    @NotNull(message = "Account Number cannot be null or blank")
    @NotBlank(message = "Account Number cannot be null or blank")
    private String accountNumber;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
