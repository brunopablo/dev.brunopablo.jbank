package dev.bruno.jbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InsufficientBalanceTransfer extends JBankException{

    private final String detail;

    public InsufficientBalanceTransfer(String detail) {
        super(detail);
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        
        var pd = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_CONTENT);

        pd.setTitle("Transfer Operation Error!");

        pd.setDetail(detail);

        return pd;
    }
}