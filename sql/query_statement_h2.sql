SELECT COUNT(*) FROM (
    SELECT
        id_transfer as statement_id,
        'transfer' as type,
        value_transfer as statement_value,
        id_sender as wallet_receiver,
        id_receiver as wallet_sender,
        date_transfer as statement_date_time
    FROM
        tb_transfers
    WHERE id_receiver = 1 OR id_sender = 1

    UNION ALL

    SELECT
        id_deposit as statement_id,
        'deposit' as type,
        sent_value as statement_value,
        id_wallet as wallet_receiver,
        CAST(NULL AS BIGINT) as wallet_sender,
        date_time_deposit as statement_date_time
    FROM tb_deposits
    WHERE id_wallet = 1
) AS total;