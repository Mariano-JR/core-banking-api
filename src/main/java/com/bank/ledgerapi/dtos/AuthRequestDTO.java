package com.bank.ledgerapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthRequestDTO(
        @Schema(description = "CPF do usuário", example = "000.111.222-33")
        String cpf,

        @Schema(description = "Senha do usuário", example = "StrongPassword")
        String password
) {
}
