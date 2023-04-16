//package com.challenge.studytime.domain.payment.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Payment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "payment_id")
//    private Long id;
//    @Column(name = "payment_method",length = 40, nullable = false)
//    private String method;
//    @Column(name = "payment_date", length = 40, nullable = false)
//    private LocalDateTime paymentDate;
//    @Column(name = "payment_amount", length = 40, nullable = false)
//    private Long amount;
//
//}
