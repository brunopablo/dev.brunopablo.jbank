package dev.bruno.jbank.service;

import org.springframework.stereotype.Service;

import dev.bruno.jbank.controller.dto.PostTransferRequestDto;
import dev.bruno.jbank.exception.InsufficientBalanceTransfer;
import dev.bruno.jbank.exception.WalletNotFoundException;
import dev.bruno.jbank.model.TransferModel;
import dev.bruno.jbank.model.WalletModel;
import dev.bruno.jbank.repository.TransferRepository;
import dev.bruno.jbank.repository.WalletRepository;
import jakarta.transaction.Transactional;

@Service
public class TransferService {

    private final TransferRepository transferRepository;

    private final WalletRepository walletRepository;

    public TransferService(TransferRepository transferRepository, WalletRepository walletRepository){
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;
    }


    @Transactional
    public TransferModel postTransfer(PostTransferRequestDto transferData) {

        var sender = walletRepository.findById(transferData.idSender()).orElseThrow(
            () -> new WalletNotFoundException("Please Check the Wallet Data!")
        );

        var receiver = walletRepository.findById(transferData.idReceiver()).orElseThrow(
            () -> new WalletNotFoundException("Please Check the Wallet Data!")
        );

        if (sender.getBalance().compareTo(transferData.transferValue()) == -1) {
            throw new InsufficientBalanceTransfer("Insufficient Balance!");
        }

        updateWallets(transferData, sender, receiver);
        
        return persistTransfer(transferData, sender, receiver);
    }


    private void updateWallets(PostTransferRequestDto transferData, WalletModel sender, WalletModel receiver) {
        sender.setBalance(sender.getBalance().subtract(transferData.transferValue()));

        receiver.setBalance(receiver.getBalance().add(transferData.transferValue()));

        walletRepository.save(sender);

        walletRepository.save(receiver);
    }


    private TransferModel persistTransfer(PostTransferRequestDto transferData, WalletModel sender, WalletModel receiver) {
        var transfer = new TransferModel();

        transfer.setSender(sender);

        transfer.setReceiver(receiver);

        transfer.setTransferValue(transferData.transferValue());

        return transferRepository.save(transfer);
    }
}