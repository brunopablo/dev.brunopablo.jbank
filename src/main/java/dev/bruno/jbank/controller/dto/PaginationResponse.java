package dev.bruno.jbank.controller.dto;

public record PaginationResponse(
    Integer page,
    Integer pageSize,
    Long totalElements,
    Integer totalPages
) {}
