package dev.bruno.jbank.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.bruno.jbank.model.WalletModel;
import dev.bruno.jbank.repository.dto.StatementViewProjection;

@Repository
public interface WalletRepository extends JpaRepository<WalletModel, Long>{

    String SQL_STATEMENT = """
                SELECT
                    id_transfer as statement_id,
                    'transfer' as type,
                    value_transfer as statement_value,
                    id_sender as wallet_sender,
                    id_receiver as wallet_receiver,
                    date_transfer as statement_date_time
                FROM
                    tb_transfers
                WHERE id_sender = ?1 OR id_receiver = ?1

                UNION ALL

                SELECT
                    id_deposit as statement_id,
                    'deposit' as type,
                    sent_value as statement_value,
                    id_wallet as wallet_receiver,
                    CAST(NULL AS BIGINT) as wallet_sender,  -- Usando NULL com Cast explícito
                    date_time_deposit as statement_date_time
                FROM tb_deposits
                WHERE id_wallet = ?1        
            """;

    String SQL_COUNT = """
            SELECT COUNT(*) FROM 
            (
            """ + SQL_STATEMENT + """
            )AS total
            """;
    
    @Query(nativeQuery = true, 
           countQuery = SQL_COUNT, 
           value = SQL_STATEMENT)
    Page<StatementViewProjection> findStatements(Long idWallet, PageRequest pageRequest);
    
    boolean existsByCpfOrEmail(String cpf, String email);
}