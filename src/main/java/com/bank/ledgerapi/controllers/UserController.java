package com.bank.ledgerapi.controllers;

import com.bank.ledgerapi.dtos.CreateUserRequestDTO;
import com.bank.ledgerapi.dtos.StandardErrorDTO;
import com.bank.ledgerapi.entities.User;
import com.bank.ledgerapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Endpoints para gestão de clientes e criação automática de contas.")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo usuário", responses = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso."),
            @ApiResponse(
                    responseCode = "400",
                    description = "Regra de negócio violada (ex: CPF já existe)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StandardErrorDTO.class)
                    )
            )
    })
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequestDTO user) {
        User savedUser = userService.createUser(user.name(), user.cpf(), user.email(), user.password());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
