package com.bank.ledgerapi.services;

import com.bank.ledgerapi.entities.Account;
import com.bank.ledgerapi.entities.User;
import com.bank.ledgerapi.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;


    @Test
    @DisplayName("Deve realizar a transferencia de saldo entre contas com sucesso.")
    void shouldTransferMoneyBetweenAccounts() {
        Account fromAccount = createAccount("123456-1", "111.222.333-44", BigDecimal.valueOf(100));
        Account toAccount = createAccount("654321-1", "111.222.333=45", BigDecimal.ZERO);

        when(accountRepository.findByAccountNumber("123456-1")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByAccountNumber("654321-1")).thenReturn(Optional.of(toAccount));

        accountService.transferMoney("123456-1", "654321-1", BigDecimal.valueOf(50));

        assertEquals(new BigDecimal(50), fromAccount.getBalance(), "O saldo de origem deve ter subtraido 50");
        assertEquals(new BigDecimal(50), toAccount.getBalance(), "O saldo de destino deve ter somado 50");

        verify(accountRepository, times(1)).save(fromAccount);
        verify(accountRepository, times(1)).save(toAccount);
    }

    @Test
    @DisplayName("Deve lançar uma exceção caso o valor da transferencia seja 0 ou negativo")
    void shouldThrowExceptionWhenTransferValueIsZeroOrLess() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.transferMoney("123456-1", "654321-1", BigDecimal.ZERO);
        });

        assertEquals("O valor da transferência deve ser maior que zero.", exception.getMessage());

        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Deve lançar uma exceção caso a conta de origem não exista")
    void shouldThrowExceptionWhenFromAccountNotExist() {
        when(accountRepository.findByAccountNumber("123456-1")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.transferMoney("123456-1", "654321-1", BigDecimal.valueOf(50));
        });

        assertEquals("Conta de origem não encontrada.", exception.getMessage());

        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("Deve lançar uma exceção caso a conta de destino não exista")
    void shouldThrowExceptionWhenToAccountNotExist() {
        Account fromAccount = createAccount("654321-1", "111.222.333-45", BigDecimal.ZERO);

        when(accountRepository.findByAccountNumber("123456-1")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByAccountNumber("654321-1")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.transferMoney("123456-1", "654321-1", BigDecimal.valueOf(50));
        });

        assertEquals("Conta de destino não encontrada.", exception.getMessage());

        verify(accountRepository, never()).save(fromAccount);
    }

    @Test
    @DisplayName("Deve lançar uma exceção caso o valor da transferencia seja maior que o saldo da conta de origem")
    void shouldThrowExceptionWhenTransferValueIsBiggestToFromAccountBalance() {
        Account fromAccount = createAccount("123456-1", "111.222.333-44", BigDecimal.valueOf(100));
        Account toAccount = createAccount("654321-1", "111.222.333=45", BigDecimal.ZERO);

        when(accountRepository.findByAccountNumber("123456-1")).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByAccountNumber("654321-1")).thenReturn(Optional.of(toAccount));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            accountService.transferMoney("123456-1", "654321-1", BigDecimal.valueOf(200));
        });

        assertEquals("Saldo insuficiente para realizar a transferência.", exception.getMessage());

        verify(accountRepository, never()).save(fromAccount);
        verify(accountRepository, never()).save(toAccount);
    }

    private Account createAccount(String accountNumber, String cpf, BigDecimal initialBalance) {
        User user = new User("Test User", cpf, "default@test.com", "password");
        Account account = new Account(accountNumber, user);
        account.addBalance(initialBalance);
        return account;
    }
}
