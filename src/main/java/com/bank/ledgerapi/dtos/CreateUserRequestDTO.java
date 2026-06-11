package com.bank.ledgerapi.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateUserRequestDTO(

        @Schema(description = "Nome completo do cliente", example = "John Doe")
        String name,

        @Schema(description = "CPF válido do cliente", example = "000.111.222-33")
        String cpf,

        @Schema(description = "E-mail corporativo ou pessoal", example = "johndoe@email.com")
        String email,

        @Schema(description = "Senha do usuário", example = "StrongPassword")
        String password
) {
}
