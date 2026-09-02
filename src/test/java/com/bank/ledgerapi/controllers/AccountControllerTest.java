package com.bank.ledgerapi.controllers;

import com.bank.ledgerapi.dtos.TransferRequestDTO;
import com.bank.ledgerapi.repositories.UserRepository;
import com.bank.ledgerapi.security.TokenService;
import com.bank.ledgerapi.services.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AccountService accountService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve transferir o dinheiro entre contas com sucesso.")
    void shouldTransferMoneySuccessfully() throws Exception {
        TransferRequestDTO requestDTO = new TransferRequestDTO("12345", "54321", BigDecimal.valueOf(100));

        mockMvc.perform(post("/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))

                .andExpect(status().isOk())
                .andExpect(content().string("Transferência realizada com sucesso!"));
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request quando regra de negócio for violada.")
    void shouldReturn400WhenInsufficientBalance() throws Exception {
        TransferRequestDTO requestDTO = new TransferRequestDTO("12345", "54321", BigDecimal.valueOf(100));

        doThrow(new IllegalArgumentException("Saldo insuficiente para realizar a transferência."))
                .when(accountService).transferMoney(anyString(), anyString(), any());

        mockMvc.perform(post("/accounts/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))

                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Saldo insuficiente para realizar a transferência."));
    }
}
