package com.sandesh.banking_app.service;

import com.sandesh.banking_app.DTO.AccountDTO;
import com.sandesh.banking_app.DTO.TransactionDTO;
import com.sandesh.banking_app.DTO.TransferFundDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO getAccountByID(Long id);
    AccountDTO DepositAmount(Long id, double amount);
    AccountDTO WithdrawAmount(Long id, double amount);
    List<AccountDTO> getAllAccounts();
    void DeleteAccount(Long id);
    void TransferFund(TransferFundDTO transferFundDTO);
    List<TransactionDTO> getAllTransactions(Long accId);
}

