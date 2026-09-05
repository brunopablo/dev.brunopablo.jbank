package dev.bruno.jbank.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bruno.jbank.controller.dto.PostTransferRequestDto;
import dev.bruno.jbank.service.TransferService;

@RestController
@RequestMapping(path = "/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<Void> postTransfer(@RequestBody PostTransferRequestDto transferData){

        var transfer = transferService.postTransfer(transferData);

        return ResponseEntity.created(URI.create("/transfer/" + transfer.getId())).build();
    }
}