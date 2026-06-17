package com.bank.ledgerapi.dtos;

import com.bank.ledgerapi.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record UserResponseDTO(

        @Schema(description = "ID do usuário")
        UUID id,

        @Schema(description = "Nome Completo do Cliente")
        String name,

        @Schema(description = "CPF do cliente")
        String cpf,

        @Schema(description = "Número da conta Bancária")
        String accountNumber
) {
    public static UserResponseDTO from(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getCpf(),
                user.getAccount().getAccountNumber()
        );
    }
}
