package dev.bruno.jbank.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bruno.jbank.dto.SaveWalletRequestDto;
import dev.bruno.jbank.service.WalletService;

@RestController()
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping()
    public ResponseEntity<Void> saveWallet(@RequestBody SaveWalletRequestDto walletData){

        var walletEntity = walletService.saveWallet(walletData);

        return ResponseEntity.created(URI.create("/wallet/" + walletEntity.getId())).build();
    }
}