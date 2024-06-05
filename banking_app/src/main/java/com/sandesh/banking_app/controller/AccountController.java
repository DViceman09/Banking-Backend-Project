package com.sandesh.banking_app.controller;

import com.sandesh.banking_app.DTO.AccountDTO;
import com.sandesh.banking_app.DTO.TransactionDTO;
import com.sandesh.banking_app.DTO.TransferFundDTO;
import com.sandesh.banking_app.entity.Account;
import com.sandesh.banking_app.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO) {
        return new ResponseEntity<>(accountService.createAccount(accountDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> GetAccountById(@PathVariable Long id) {
        AccountDTO account = accountService.getAccountByID(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDTO> Deposit(@PathVariable Long id,
                                              @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDTO accountDTO =  accountService.DepositAmount(id, amount);
        return ResponseEntity.status(HttpStatus.OK).body(accountDTO);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDTO> Withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDTO accountDTO =  accountService.WithdrawAmount(id, amount);
        return ResponseEntity.status(HttpStatus.OK).body(accountDTO);
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> GetAllAccounts() {
        List<AccountDTO> accounts =  accountService.getAllAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeleteAccount(@PathVariable Long id) {
        accountService.DeleteAccount(id);
        return ResponseEntity.ok("Account has been deleted successfully");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> TransferFund(@RequestBody TransferFundDTO transferFundDTO) {
        accountService.TransferFund(transferFundDTO);
        return ResponseEntity.ok("Amount has been transferred successfully");
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDTO>> FetchAccountTransactions(@PathVariable("id") Long accId) {
        List<TransactionDTO> transactions = accountService.getAllTransactions(accId);
        return ResponseEntity.ok(transactions);
    }
}
