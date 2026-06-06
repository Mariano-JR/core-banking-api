package com.bank.ledgerapi.services;

import com.bank.ledgerapi.entities.Account;
import com.bank.ledgerapi.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }

        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada."));

        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada."));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a transferência.");
        }

        fromAccount.subtractBalance(amount);
        toAccount.addBalance(amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
