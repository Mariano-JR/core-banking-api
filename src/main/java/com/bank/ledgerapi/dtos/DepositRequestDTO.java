package com.bank.ledgerapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DepositRequestDTO(
        @NotNull
        @Schema(description = "Conta de deposito")
        String accountNumber,

        @NotNull
        @Positive
        @Schema(description = "Valor de deposito")
        BigDecimal amount
) {
}
