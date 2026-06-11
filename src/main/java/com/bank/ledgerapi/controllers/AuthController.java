package com.bank.ledgerapi.controllers;

import com.bank.ledgerapi.dtos.AuthRequestDTO;
import com.bank.ledgerapi.dtos.AuthResponseDTO;
import com.bank.ledgerapi.entities.User;
import com.bank.ledgerapi.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para login e geração de tokens JWT.")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    @Operation(summary = "Realizar login na API")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        // Junção do CPF e Senha passados.
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.cpf(), request.password());

        // Verificação dos dados de CPF e senha criptografada no DB
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Geração do Token JWT
        var token = this.tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
