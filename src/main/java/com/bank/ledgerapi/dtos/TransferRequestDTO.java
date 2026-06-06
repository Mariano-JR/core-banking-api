package com.bank.ledgerapi.dtos;

import java.math.BigDecimal;

public record TransferRequestDTO(
        String fromAccountNumber,
        String toAccountNumber,
        BigDecimal amount
) {
}
