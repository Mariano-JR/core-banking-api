package com.bank.ledgerapi.controllers;

import com.bank.ledgerapi.dtos.AuthRequestDTO;
import com.bank.ledgerapi.entities.User;
import com.bank.ledgerapi.repositories.UserRepository;
import com.bank.ledgerapi.security.TokenService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve retornar o Token se o login for realizado com sucesso.")
    void shouldReturnTokenWhenLoginSuccessful() throws Exception {
        AuthRequestDTO requestDTO = new AuthRequestDTO("111.222.333-44", "test123");
        User mockUser = new User("John Doe", "111.222.333-44", "johndoe@test.com", "test123");

        Authentication authMock = mock(Authentication.class);

        when(authMock.getPrincipal()).thenReturn(mockUser);
        when(authenticationManager.authenticate(any())).thenReturn(authMock);
        when(tokenService.generateToken(any(User.class))).thenReturn("token.jwt.test");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token.jwt.test"));
    }

    @Test
    @DisplayName("Deve retornar um erro quando as credenciais forem inválidas.")
    void shouldReturnErrorWhenCredentialsAreInvalid() throws Exception {
        AuthRequestDTO requestDTO = new AuthRequestDTO("111.222.333-44", "test123");

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Credenciais inválidas. Verifique seu CPF e senha."));

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))

                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Credenciais inválidas. Verifique seu CPF e senha."));
    }
}
