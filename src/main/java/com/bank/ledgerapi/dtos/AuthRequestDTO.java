package com.bank.ledgerapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDTO(

        @NotNull
        @Schema(description = "CPF do usuário", example = "000.111.222-33")
        String cpf,

        @NotNull
        @Schema(description = "Senha do usuário", example = "StrongPassword")
        String password
) {
}
