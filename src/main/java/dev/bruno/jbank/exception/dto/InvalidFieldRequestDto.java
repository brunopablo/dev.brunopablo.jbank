package dev.bruno.jbank.exception.dto;

public record InvalidFieldRequestDto(
    String nameField,
    String reason
) {}