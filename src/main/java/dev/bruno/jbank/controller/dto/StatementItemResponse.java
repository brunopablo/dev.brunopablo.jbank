package dev.bruno.jbank.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StatementItemResponse(
    String id,
    String type,
    String literal,
    BigDecimal value,
    LocalDateTime dateTime,
    StatementOperation operation
) {}