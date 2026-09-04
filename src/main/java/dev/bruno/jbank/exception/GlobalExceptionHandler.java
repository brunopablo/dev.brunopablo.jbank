package dev.bruno.jbank.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.bruno.jbank.exception.dto.InvalidFieldRequestDto;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(JBankException.class)
    public ProblemDetail handleJBankException(JBankException e){

        return e.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        var reasons = e.getFieldErrors().stream().map(
            reason -> new InvalidFieldRequestDto(reason.getField(), reason.getDefaultMessage())
        ).toList();

        var pd = ProblemDetail.forStatus(400);

        pd.setTitle("Invalid Request Parameters");
        
        pd.setDetail("There are invalid data on the request fields!");

        pd.setProperty("Invalid Fields", reasons);

        return pd;
    }
}