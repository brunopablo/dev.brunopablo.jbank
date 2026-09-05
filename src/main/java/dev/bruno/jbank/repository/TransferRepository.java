package dev.bruno.jbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.bruno.jbank.model.TransferModel;

@Repository
public interface TransferRepository extends JpaRepository<TransferModel, Long>{}