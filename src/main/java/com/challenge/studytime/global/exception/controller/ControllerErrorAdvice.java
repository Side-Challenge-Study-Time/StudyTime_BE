package com.challenge.studytime.global.exception.controller;

import com.challenge.studytime.global.exception.ErrorResponse;
import com.challenge.studytime.global.exception.member.UserEmailDuplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerErrorAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserEmailDuplicationException.class)
    public ErrorResponse handleUserEmailIsAlreadyExisted() {
        log.error("User's email address is already existed");
        return new ErrorResponse("400", "User's email address is already existed");
    }


}
