package dev.bruno.jbank.exception;

public class WalletDataAlreadyExistException extends JBankException{

    public WalletDataAlreadyExistException(String message) {
        super(message);
    }

    public WalletDataAlreadyExistException(Throwable cause) {
        super(cause);
    }
}