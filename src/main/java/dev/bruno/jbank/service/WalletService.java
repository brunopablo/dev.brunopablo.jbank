package dev.bruno.jbank.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import dev.bruno.jbank.controller.dto.DepositRequest;
import dev.bruno.jbank.controller.dto.SaveWalletRequestDto;
import dev.bruno.jbank.exception.DeleteWalletException;
import dev.bruno.jbank.exception.WalletDataAlreadyExistException;
import dev.bruno.jbank.exception.WalletNotFoundException;
import dev.bruno.jbank.model.DepositModel;
import dev.bruno.jbank.model.WalletModel;
import dev.bruno.jbank.repository.DeposityRepository;
import dev.bruno.jbank.repository.WalletRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final DeposityRepository depositRepository;

    public WalletService(WalletRepository walletRepository, DeposityRepository depositRepository) {
        this.walletRepository = walletRepository;
        this.depositRepository = depositRepository;
    }

    public WalletModel saveWallet(SaveWalletRequestDto walletData) {
        
        var dataSaved = walletRepository.existsByCpfOrEmail(walletData.cpf(),walletData.email());

        if (dataSaved) {
            throw new WalletDataAlreadyExistException("cpf or email already exist | teste");
        }

        var walletModel = new WalletModel();

        walletModel.setCpf(walletData.cpf());
        
        walletModel.setEmail(walletData.email());

        walletModel.setName(walletData.name());

        walletModel.setBalance(BigDecimal.ZERO);


        return walletRepository.save(walletModel);
    }

    public boolean deleteWallet(Long walletId) {
        
        var walletModel = walletRepository.findById(walletId);

        if (walletModel.isPresent()) {
            
            if (walletModel.get().getBalance().compareTo(BigDecimal.ZERO) != 0) {
                throw new DeleteWalletException("The balance is not zero!");
            }

            walletRepository.deleteById(walletId);
        }


        return walletModel.isPresent();
    }

    @Transactional
    public DepositModel postDeposit(Long walletId, 
                                    DepositRequest depositData, 
                                    HttpServletRequest request)
    {
        
        var walletModel = walletRepository.findById(walletId);

        if (!walletModel.isPresent()) {
            throw new WalletNotFoundException("Please Check the Wallet Identifier!");
        }
        
        var newBalance = walletModel.get().getBalance().add(depositData.value());

        walletModel.get().setBalance(newBalance);

        walletRepository.save(walletModel.get());

        var depositModel = new DepositModel();

        depositModel.setSentValue(depositData.value());

        depositModel.setWallet(walletModel.get());

        depositModel.setIp(request.getAttribute("x-user-ip").toString());

        return depositRepository.save(depositModel);
    }
}