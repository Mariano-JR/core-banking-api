package com.bank.ledgerapi.controllers;

import com.bank.ledgerapi.dtos.CreateUserRequestDTO;
import com.bank.ledgerapi.entities.Account;
import com.bank.ledgerapi.entities.User;
import com.bank.ledgerapi.repositories.UserRepository;
import com.bank.ledgerapi.security.TokenService;
import com.bank.ledgerapi.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve criar um usuário com sucesso e retornar 201 Created")
    void shouldCreateUserSuccessfully() throws Exception {
        CreateUserRequestDTO requestDTO = new CreateUserRequestDTO("John Doe", "111.222.333-44", "johndoe@test.com", "testpassword123");
        User mockSavedUser = new User("John Doe", "111.222.333-44", "johndoe@test.com", "testpassword123");
        Account mockAccount = new Account();
        mockSavedUser.bindAccount(mockAccount);

        when(userService.createUser(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(mockSavedUser);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.cpf").value("111.222.333-44"));
    }
}
