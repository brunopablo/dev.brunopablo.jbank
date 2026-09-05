package dev.bruno.jbank.controller.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record PostTransferRequestDto(
    @NotNull Long idSender,
    @NotNull Long idReceiver,
    @NotNull @DecimalMin(value = "0.01") BigDecimal transferValue   
) {}