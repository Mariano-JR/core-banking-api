package com.bank.ledgerapi.services;

import com.bank.ledgerapi.entities.User;
import com.bank.ledgerapi.repositories.AccountRepository;
import com.bank.ledgerapi.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Deve criar um usuário com sucesso e vincular uma conta.")
    void shouldCreateUserSuccessfully() {
        String cpf = "111.222.333-44";
        String password = "StrongPassword";

        when(userRepository.findByCpf(cpf)).thenReturn(null);
        when(passwordEncoder.encode(password)).thenReturn("hashed_password");
        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);

        User mockSavedUser = new User("John Doe", cpf, "johndoe@test.com", "hashed_password");
        when(userRepository.save(any(User.class))).thenReturn(mockSavedUser);

        User result = userService.createUser("John Doe", cpf, "johndoe@test.com", password);

        assertNotNull(result, "O usuário retornado não pode ser nulo");
        assertEquals("hashed_password", result.getPassword(), "A senha deve estar criptografada");

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar usuário com CPF já existente")
    void shouldThrowExceptionWhenCpfAlreadyExists() {
        String cpf = "111.222.333-44";
        User existingUser = new User("Marie Doe", cpf, "mariedoe@test.com", "OtherStrongPassword");

        when(userRepository.findByCpf(cpf)).thenReturn(existingUser);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("John Doe", cpf, "johndoe@test.com", "StrongPassword");
        });

        assertEquals("Operação negada: Este CPF já esta cadastrado.", exception.getMessage());

        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}
