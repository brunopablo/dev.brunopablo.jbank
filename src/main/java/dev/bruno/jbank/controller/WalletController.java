package dev.bruno.jbank.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.bruno.jbank.controller.dto.DepositRequest;
import dev.bruno.jbank.controller.dto.SaveWalletRequestDto;
import dev.bruno.jbank.controller.dto.StatementRequest;
import dev.bruno.jbank.controller.dto.StatementResponse;
import dev.bruno.jbank.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping()
    public ResponseEntity<Void> saveWallet(@RequestBody @Valid SaveWalletRequestDto walletData){

        var walletEntity = walletService.saveWallet(walletData);

        return ResponseEntity.created(URI.create("/wallet/" + walletEntity.getId())).build();
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<Void> deleteWallet(@PathVariable Long walletId){

        var deleted = walletService.deleteWallet(walletId);

        return deleted ?
            ResponseEntity.noContent().build() :
            ResponseEntity.notFound().build();
    }

    @PostMapping("/{walletId}")
    public ResponseEntity<Void> postDeposit(@PathVariable Long walletId,
                                            @Valid @RequestBody DepositRequest depositData,
                                            HttpServletRequest request
    ){

        var deposit = walletService.postDeposit(walletId, depositData, request);

        return ResponseEntity.created(URI.create("/wallet/" + deposit.getId())).build();
    }

    @GetMapping("/{walletId}/statements")
    public ResponseEntity<StatementResponse> getStatements(
        @PathVariable Long walletId,
        @RequestParam(name = "page", defaultValue = "0") Integer page,
        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ){

        var statements = walletService.getStatements(walletId, page, pageSize);

        return ResponseEntity.ok(statements);
    }
}