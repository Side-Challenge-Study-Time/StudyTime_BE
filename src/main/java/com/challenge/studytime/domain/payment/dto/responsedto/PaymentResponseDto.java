package com.challenge.studytime.domain.payment.dto.responsedto;

import com.challenge.studytime.domain.payment.dto.requestdto.PaymentRequestDto;
import com.challenge.studytime.domain.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private String method;
    private LocalDateTime paymentDate;
    private int price;
    private int amount;
    private int coupon_amount;
    public static PaymentResponseDto toDto(Payment payment){
        return PaymentResponseDto.builder()
                .method(payment.getMethod())
                .paymentDate(payment.getPaymentDate())
                .price(payment.getPrice())
                .amount(payment.getAmount())
                .coupon_amount(payment.getCoupon_amount())
                .build();
    }
}
