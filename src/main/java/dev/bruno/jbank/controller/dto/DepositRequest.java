package dev.bruno.jbank.controller.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record DepositRequest(
    @DecimalMin(value = "1.00") @NotNull BigDecimal value
) {}