package com.bank.ledgerapi.services;

import com.bank.ledgerapi.entities.User;
import com.bank.ledgerapi.entities.Account;
import com.bank.ledgerapi.repositories.AccountRepository;
import com.bank.ledgerapi.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String name, String cpf, String email, String password) {
        UserDetails existingUser = userRepository.findByCpf(cpf);

        if (existingUser != null) {
            throw new IllegalArgumentException("Operação negada: Este CPF já esta cadastrado.");
        }

        String hashedPassword = passwordEncoder.encode(password);

        User newUser = new User(name, cpf, email, hashedPassword);

        String uniqueAccountNumber = generateUniqueAccountNumber();
        Account newAccount = new Account(uniqueAccountNumber, newUser);

        newUser.bindAccount(newAccount);

        return userRepository.save(newUser);
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        Random random = new Random();

        do {
            int number = random.nextInt(999999);
            accountNumber = String.format("%06d", number) + "-0";
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}
