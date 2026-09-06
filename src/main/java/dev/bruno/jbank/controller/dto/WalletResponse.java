package dev.bruno.jbank.controller.dto;

import java.math.BigDecimal;

public record WalletResponse(
    String name,
    String cpf,
    String email,
    BigDecimal balance
) {}