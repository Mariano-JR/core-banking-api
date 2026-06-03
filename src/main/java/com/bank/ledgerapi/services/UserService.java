package com.bank.ledgerapi.services;

import com.bank.ledgerapi.User;
import com.bank.ledgerapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByCpf(user.getCpf());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Operação negada: Este CPF já esta cadastrado.");
        }

        return userRepository.save(user);
    }
}
