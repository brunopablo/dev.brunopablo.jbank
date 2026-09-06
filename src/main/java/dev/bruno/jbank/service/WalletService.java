package dev.bruno.jbank.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import dev.bruno.jbank.controller.dto.DepositRequest;
import dev.bruno.jbank.controller.dto.PaginationResponse;
import dev.bruno.jbank.controller.dto.SaveWalletRequestDto;
import dev.bruno.jbank.controller.dto.StatementItemResponse;
import dev.bruno.jbank.controller.dto.StatementOperation;
import dev.bruno.jbank.controller.dto.StatementResponse;
import dev.bruno.jbank.controller.dto.WalletResponse;
import dev.bruno.jbank.exception.DeleteWalletException;
import dev.bruno.jbank.exception.StatementException;
import dev.bruno.jbank.exception.WalletDataAlreadyExistException;
import dev.bruno.jbank.exception.WalletNotFoundException;
import dev.bruno.jbank.model.DepositModel;
import dev.bruno.jbank.model.WalletModel;
import dev.bruno.jbank.repository.DeposityRepository;
import dev.bruno.jbank.repository.WalletRepository;
import dev.bruno.jbank.repository.dto.StatementViewProjection;
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

    public StatementResponse getStatements(Long walletId, Integer page, Integer pageSize) {
        
        var walletModel = walletRepository.findById(walletId).orElseThrow(
            () -> new WalletNotFoundException("Please Check the Wallet Identifier!")
        );

        var pageRequest = PageRequest.of(
            page, pageSize, Sort.Direction.DESC, "statement_date_time"
        );

        var statements = walletRepository.findStatements(walletId, pageRequest)
            .map(projection -> mapToStatementItem(walletId, projection));

        return new StatementResponse(
            getWalletData(walletModel),
            statements.getContent(),
            getPaginationData(statements)
        );
    }

    private PaginationResponse getPaginationData(Page<StatementItemResponse> statements) {
        return new PaginationResponse(
            statements.getNumber(),
            statements.getNumberOfElements(),
            statements.getTotalElements(),
            statements.getTotalPages()
        );
    }

    private WalletResponse getWalletData(WalletModel walletModel) {
        return new WalletResponse(
            walletModel.getName(),
            walletModel.getCpf(),
            walletModel.getEmail(),
            walletModel.getBalance()
        );
    }

    private StatementItemResponse mapToStatementItem(Long walletId,
                                                     StatementViewProjection projection)
    {
        if (projection.getType().equalsIgnoreCase("deposit")) {
            return mapWhenIsDepositOperation(projection);
        }
    
        if (projection.getType().equalsIgnoreCase("transfer") && 
            projection.getStatementId().equalsIgnoreCase(walletId.toString())
        ) {
            return mapWhenPeopleIsSender(walletId, projection);
        }

        if (projection.getType().equalsIgnoreCase("transfer") && 
            !projection.getStatementId().equalsIgnoreCase(walletId.toString())
        ) {
            return mapWhenPeopleIsReceiver(walletId, projection);
        }

        throw new StatementException("Invalid Operation Type");
    }

    private StatementItemResponse mapWhenPeopleIsReceiver(Long walletId, 
                                                          StatementViewProjection projection) 
    {
        return new StatementItemResponse(
            projection.getStatementId(),
            projection.getType(),
            "money received from " + projection.getWalletSender(),
            projection.getStatementValue(),
            projection.getStatementDateTime(),
            StatementOperation.CREDIT  
        );
    }

    private StatementItemResponse mapWhenPeopleIsSender(Long walletId, 
                                                        StatementViewProjection projection)
    {
        return new StatementItemResponse(
            projection.getStatementId(),
            projection.getType(),
            "money sent to " + projection.getWalletReceiver(),
            projection.getStatementValue(),
            projection.getStatementDateTime(),
            StatementOperation.DEBIT
        );
    }

    private StatementItemResponse mapWhenIsDepositOperation(StatementViewProjection projection) {
        return new StatementItemResponse(
            projection.getStatementId(),
            projection.getType(),
            "money deposit",
            projection.getStatementValue(),
            projection.getStatementDateTime(),
            StatementOperation.CREDIT
        );
    }
}