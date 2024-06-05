package com.sandesh.banking_app.DTO;

public record TransferFundDTO(Long senderAccountId, Long receiverAccountId, double amount) {
}
