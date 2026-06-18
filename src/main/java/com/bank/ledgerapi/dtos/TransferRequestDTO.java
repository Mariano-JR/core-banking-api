package com.bank.ledgerapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequestDTO(

        @NotNull
        @Schema(description = "Conta de Origem da transação")
        String fromAccountNumber,

        @NotNull
        @Schema(description = "Conta de Destino da transação")
        String toAccountNumber,

        @NotNull
        @Positive
        @Schema(description = "Valor da transação")
        BigDecimal amount
) {
}
