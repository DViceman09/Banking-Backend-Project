package com.sandesh.banking_app.service.impl;

import com.sandesh.banking_app.DTO.AccountDTO;
import com.sandesh.banking_app.DTO.TransactionDTO;
import com.sandesh.banking_app.DTO.TransferFundDTO;
import com.sandesh.banking_app.Exceptions.AccountExceptions;
import com.sandesh.banking_app.entity.Account;
import com.sandesh.banking_app.entity.Transaction;
import com.sandesh.banking_app.mapper.AccountMapping;
import com.sandesh.banking_app.repository.AccountRepo;
import com.sandesh.banking_app.repository.TransactionRepo;
import com.sandesh.banking_app.service.AccountService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccServiceImpl implements AccountService {

    private final AccountRepo accountRepo;

    private final TransactionRepo transactionRepo;

    private static final String Transaction_TYPE_DEPOSIT = "DEPOSIT";

    private static final String Transaction_TYPE_WITHDRAW = "WITHDRAW";

    private static final String Transaction_TYPE_TRANSFER = "TRANSFER";


    public AccServiceImpl(AccountRepo accountRepo, TransactionRepo transactionRepo)
    {
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = AccountMapping.MapToAccount(accountDTO);
        Account SavedAccount = accountRepo.save(account);
        return AccountMapping.MapToAccountDTO(SavedAccount);
    }

    @Override
    public AccountDTO getAccountByID(Long id) {
       Account account =  accountRepo.
               findById(id).
               orElseThrow(() -> new AccountExceptions("Account not found"));
       return AccountMapping.MapToAccountDTO(account);
    }

    @Override
    public AccountDTO DepositAmount(Long id, double amount) {
        Account account =  accountRepo.
                findById(id).
                orElseThrow(() -> new AccountExceptions("Account not found"));
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepo.save(account);
        Transaction transaction = new Transaction();
        transaction.setAccId(id);
        transaction.setAmount(amount);
        transaction.setTransactionType(Transaction_TYPE_DEPOSIT);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepo.save(transaction);
        return AccountMapping.MapToAccountDTO(savedAccount);
    }

    @Override
    public AccountDTO WithdrawAmount(Long id, double withdrawAmount) {
        Account account = accountRepo.findById(id).orElseThrow(() ->
                                                    new AccountExceptions("Account not found"));
        double getCurrentTotal = account.getBalance();
        if(getCurrentTotal < withdrawAmount) {
            throw new AccountExceptions("Insufficient funds in Bank");
        }
        else {
            double total = getCurrentTotal - withdrawAmount;
            account.setBalance(total);
            Account savedAccount = accountRepo.save(account);
            Transaction transaction = new Transaction();
            transaction.setAccId(id);
            transaction.setAmount(withdrawAmount);
            transaction.setTransactionType(Transaction_TYPE_WITHDRAW);
            transaction.setTimestamp(LocalDateTime.now());
            transactionRepo.save(transaction);
            return AccountMapping.MapToAccountDTO(savedAccount);
        }
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepo.findAll();
        return accounts.stream().map((account) -> AccountMapping.MapToAccountDTO(account)).collect(Collectors.toList());
    }

    @Override
    public void DeleteAccount(Long id) {
        Account account =  accountRepo.
                findById(id).
                orElseThrow(() -> new AccountExceptions("Account not found"));
        accountRepo.deleteById(id);
    }

    @Override
    public void TransferFund(TransferFundDTO transferFundDTO) {
        Account senderAccount = accountRepo.findById(transferFundDTO.senderAccountId()).orElseThrow(() ->
                new AccountExceptions("Account does not exist"));
        Account receiverAccount = accountRepo.findById(transferFundDTO.receiverAccountId()).orElseThrow(() ->
                new AccountExceptions("Account does not exist"));
        double senderAccountBalance = senderAccount.getBalance();
        if(senderAccountBalance >= transferFundDTO.amount()) {
            senderAccount.setBalance(senderAccount.getBalance() - transferFundDTO.amount());
            receiverAccount.setBalance(receiverAccount.getBalance() + transferFundDTO.amount());
            accountRepo.save(senderAccount);
            accountRepo.save(receiverAccount);
            Transaction transaction = new Transaction();
            transaction.setAccId(transferFundDTO.senderAccountId());
            transaction.setAmount(transferFundDTO.amount());
            transaction.setTransactionType(Transaction_TYPE_TRANSFER);
            transaction.setTimestamp(LocalDateTime.now());
            transactionRepo.save(transaction);
        }
        else
        {
            throw new AccountExceptions("Insufficient funds in Bank");
        }
    }

    @Override
    public List<TransactionDTO> getAllTransactions(Long accId) {
        List<Transaction> transactions = transactionRepo.
                findByAccIdOrderByTimestampDesc(accId);

        return transactions.stream().map((transaction) -> convertEntityToDTO(transaction)).collect(Collectors.toList());
    }

    private TransactionDTO convertEntityToDTO(Transaction transaction) {
        return new TransactionDTO(transaction.getId(), transaction.getAccId(), transaction.getAmount() ,transaction.getTransactionType(), transaction.getTimestamp());
    }
}
