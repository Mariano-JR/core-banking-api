package com.bank.ledgerapi.security;

import com.bank.ledgerapi.entities.Account;
import com.bank.ledgerapi.entities.User;
import org.h2.command.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    private User defaultUser;
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secret", "safe-secret-test-123");
        defaultUser = new User("John Doe", "111.222.333-44", "johndoe@test.com", "SuperSecretPassword");
    }

    @Test
    @DisplayName("Deve gerar um token JWT com sucesso.")
    void shouldGenerateTokenSuccessfully() {
        String token = tokenService.generateToken(defaultUser);

        assertNotNull(token, "O token gerado não deve ser nulo");
        assertFalse(token.isEmpty(), "O token gerado não deve estar vazio");
    }

    @Test
    @DisplayName("Deve validar o token gerado com sucesso.")
    void shouldValidateTokenSuccessfully() {
        String token = tokenService.generateToken(defaultUser);
        String validatedCpf = tokenService.validateToken(token);

        assertEquals(defaultUser.getCpf(), validatedCpf, "O CPF retornado pelo token deve ser igual ao do usuário original");
    }

    @Test
    @DisplayName("Deve retornar um subject vazio caso o token seja inválido.")
    void shouldReturnAEmptySubjectWhenTokenInvalid() {
        String tokenInvalid = "any.token.invalid.123";
        String subject = tokenService.validateToken(tokenInvalid);

        assertTrue(subject.isEmpty(), "O subject retornado deve ser vazio.");
    }

    @Test
    @DisplayName("Deve lançar uma exception caso a chave esteja faltando")
    void shouldThrowAExceptionWhenKeyIsMissing() {
        TokenService missingKeyTokenService = new TokenService();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            missingKeyTokenService.generateToken(defaultUser);
        });

        assertEquals("Erro interno: Chave JWT não configurada", exception.getMessage());
    }
}
