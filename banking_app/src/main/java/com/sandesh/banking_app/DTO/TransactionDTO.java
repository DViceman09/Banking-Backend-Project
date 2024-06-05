package com.sandesh.banking_app.DTO;

import java.time.LocalDateTime;

public record TransactionDTO(Long id, Long accId, double amount, String transactionType, LocalDateTime timestamp)
{

}
