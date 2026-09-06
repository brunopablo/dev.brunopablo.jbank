package dev.bruno.jbank.repository.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface StatementViewProjection {
    String getStatementId();
    String getType();
    BigDecimal getStatementValue();
    String getWalletReceiver();
    String getWalletSender();
    LocalDateTime getStatementDateTime();
}

// STATEMENT_ID  	TYPE  	STATEMENT_VALUE  	WALLET_RECEIVER  	WALLET_SENDER  	STATEMENT_DATE_TIME 