package dev.bruno.jbank.exception;

import org.springframework.http.ProblemDetail;

// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.ResponseStatus;

// @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT) opcao para tratamento de excecao
public class WalletDataAlreadyExistException extends JBankException{

    private final String detail;

    public WalletDataAlreadyExistException(String detail) {
        
        super(detail);

        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(422);

        pd.setTitle("Wallet Data Already exists");

        pd.setDetail(detail);

        return pd;
    }
}