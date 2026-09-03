package dev.bruno.jbank.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import dev.bruno.jbank.dto.SaveWalletRequestDto;
import dev.bruno.jbank.exception.WalletDataAlreadyExistException;
import dev.bruno.jbank.model.WalletModel;
import dev.bruno.jbank.repository.WalletRepository;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public WalletModel saveWallet(SaveWalletRequestDto walletData) {
        
        var dataSaved = walletRepository.existsByCpfOrEmail(walletData.cpf(),walletData.email());

        if (dataSaved) {
            throw new WalletDataAlreadyExistException("cpf or email already exists");
        }

        var walletModel = new WalletModel();

        walletModel.setCpf(walletData.cpf());
        
        walletModel.setEmail(walletData.email());

        walletModel.setName(walletData.name());

        walletModel.setBalance(BigDecimal.ZERO);


        return walletRepository.save(walletModel);
    }
}