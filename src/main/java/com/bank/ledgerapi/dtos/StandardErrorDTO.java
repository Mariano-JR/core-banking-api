package com.bank.ledgerapi.dtos;

import java.time.LocalDateTime;

public record StandardErrorDTO(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String path
) {
}
