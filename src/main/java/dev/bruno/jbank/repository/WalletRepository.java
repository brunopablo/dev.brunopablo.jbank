package dev.bruno.jbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.bruno.jbank.model.WalletModel;

@Repository
public interface WalletRepository extends JpaRepository<WalletModel, Long>{

    boolean existsByCpfOrEmail(String cpf, String email);
}