package dev.bruno.jbank.dto;

public record SaveWalletRequestDto(
    String cpf,
    String email,
    String name
) {}