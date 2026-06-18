package com.bank.ledgerapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequestDTO(

        @NotNull
        @Schema(description = "Nome completo do cliente", example = "John Doe")
        String name,

        @NotNull
        @Schema(description = "CPF válido do cliente", example = "000.111.222-33")
        String cpf,

        @NotNull
        @Schema(description = "E-mail corporativo ou pessoal", example = "johndoe@email.com")
        String email,

        @NotNull
        @Schema(description = "Senha do usuário", example = "StrongPassword")
        String password
) {
}
