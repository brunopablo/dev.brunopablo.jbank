package dev.bruno.jbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.bruno.jbank.model.DepositModel;

@Repository
public interface DeposityRepository extends JpaRepository<DepositModel, Long>{}