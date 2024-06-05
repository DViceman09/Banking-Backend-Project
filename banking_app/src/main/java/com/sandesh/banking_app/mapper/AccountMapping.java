package com.sandesh.banking_app.mapper;

import com.sandesh.banking_app.DTO.AccountDTO;
import com.sandesh.banking_app.entity.Account;

public class AccountMapping {
    public static Account MapToAccount(AccountDTO accountDTO)
    {
        Account account = new Account(accountDTO.id(),accountDTO.balance(), accountDTO.AccHolderName());
        return account;
    }

    public static AccountDTO MapToAccountDTO(Account account)
    {
        AccountDTO accountDTO = new AccountDTO(account.getId(),account.getAccHolderName(), account.getBalance());
        return accountDTO;
    }
}
