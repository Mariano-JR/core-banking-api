package com.bank.ledgerapi.controllers;

import com.bank.ledgerapi.dtos.TransferRequestDTO;
import com.bank.ledgerapi.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransferRequestDTO request) {

        accountService.transferMoney(
                request.fromAccountNumber(),
                request.toAccountNumber(),
                request.amount()
        );

        return ResponseEntity.ok("Transferência realizada com sucesso!");
    }
}
