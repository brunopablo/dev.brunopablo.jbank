package dev.bruno.jbank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_transfers")
public class TransferModel {

    @Id
    @Column(name = "id_transfer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sender")
    private WalletModel sender;
    
    @ManyToOne
    @JoinColumn(name = "id_receiver")
    private WalletModel receiver;
    
    @Column(name = "value_transfer")
    private BigDecimal transferValue;
    
    @Column(name = "date_transfer")
    private LocalDateTime dateTransfer;

    public TransferModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WalletModel getSender() {
        return sender;
    }

    public void setSender(WalletModel sender) {
        this.sender = sender;
    }

    public WalletModel getReceiver() {
        return receiver;
    }

    public void setReceiver(WalletModel receiver) {
        this.receiver = receiver;
    }

    public BigDecimal getTransferValue() {
        return transferValue;
    }

    public void setTransferValue(BigDecimal transferValue) {
        this.transferValue = transferValue;
    }

    public LocalDateTime getDateTransfer() {
        return dateTransfer;
    }

    public void setDateTransfer(LocalDateTime dateTransfer) {
        this.dateTransfer = dateTransfer;
    }   
}