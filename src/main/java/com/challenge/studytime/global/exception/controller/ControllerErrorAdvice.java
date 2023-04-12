package com.challenge.studytime.global.exception.controller;

import com.challenge.studytime.global.exception.ErrorResponse;
import com.challenge.studytime.global.exception.coupon.CouponDuplicationException;
import com.challenge.studytime.global.exception.coupon.CouponNameDuplicationException;
import com.challenge.studytime.global.exception.coupon.CouponNotAvailableException;
import com.challenge.studytime.global.exception.coupon.NotFoundCoupon;
import com.challenge.studytime.global.exception.member.NotMatchPassword;
import com.challenge.studytime.global.exception.member.UserEmailDuplicationException;
import com.challenge.studytime.global.exception.refreshToken.NotFoundRefreshToken;
import com.challenge.studytime.global.exception.studyroom.NotFoundStudyRoom;
import com.challenge.studytime.global.exception.studyroom.NotillegalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorResponse UsernameNotFoundException() {
        log.error("user not found member");
        return new ErrorResponse("400", "user not found member");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotMatchPassword.class)
    public ErrorResponse NotMatchPassword() {
        log.error("NotMatchPassword");
        return new ErrorResponse("400", "NotMatchPassword");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CouponNameDuplicationException.class)
    public ErrorResponse CouponNameDuplicationException() {
        log.error("CouponName is already existed");
        return new ErrorResponse("400", "CouponName is already existed");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundCoupon.class)
    public ErrorResponse NotFoundCoupon() {
        log.error("NOT Found COUPON ");
        return new ErrorResponse("400", "Not Found Coupon");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CouponDuplicationException.class)
    public ErrorResponse CouponDuplicationException(){
        log.error("Cannot issue coupon");
        return new ErrorResponse("400", "Cannot issue coupon");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CouponNotAvailableException.class)
    public ErrorResponse CouponNotAvailableException(){
        log.error("Not available coupon");
        return new ErrorResponse("400", "Not available coupon");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotFoundStudyRoom.class)
    public ErrorResponse NotFoundStudyRoom(){
        log.error("Not Found StudyRoom");
        return new ErrorResponse("400", "Not Found StudyRoom");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotillegalException.class)
    public ErrorResponse NotillegalException(){
        log.error("Not Valid value");
        return new ErrorResponse("400", "Not Valid value");
    }
}
