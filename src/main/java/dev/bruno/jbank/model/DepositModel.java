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
@Table(name = "tb_deposits")
public class DepositModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deposit")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_wallet")
    private WalletModel wallet;

    @Column(name = "sent_value")
    private BigDecimal sentValue;

    @Column(name = "date_time_deposit")
    private LocalDateTime dateDeposit;
    
    @Column(name = "ip_deposit")
    private String ip;

    public DepositModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WalletModel getWallet() {
        return wallet;
    }

    public void setWallet(WalletModel wallet) {
        this.wallet = wallet;
    }

    public BigDecimal getSentValue() {
        return sentValue;
    }

    public void setSentValue(BigDecimal sentValue) {
        this.sentValue = sentValue;
    }

    public LocalDateTime getDateDeposit() {
        return dateDeposit;
    }

    public void setDateDeposit(LocalDateTime dateDeposit) {
        this.dateDeposit = dateDeposit;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }   
}