package dev.bruno.jbank.controller.dto;

import java.util.List;

public record StatementResponse(
    WalletResponse walletData,
    List<StatementItemResponse> statementItems,
    PaginationResponse paginationData
) {}