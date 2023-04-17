package com.challenge.studytime.global.exception.payment;

public class AlreadyPaidReservation extends RuntimeException{
    public AlreadyPaidReservation(){
        super("Already Paid Reservation");
    }
}
