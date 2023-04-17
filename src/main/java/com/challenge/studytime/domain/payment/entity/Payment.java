package com.challenge.studytime.domain.payment.entity;

import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.domain.reservation.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;
    @Column(name = "payment_method",length = 40, nullable = false)
    private String method;
    @Column(name = "payment_date", length = 40, nullable = false)
    @CreatedDate
    private LocalDateTime paymentDate;
    @Column(name = "reservation_price", length = 40, nullable = false)
    private int price;
    @Column(name = "payment_amount", length = 40, nullable = false)
    private int amount;
    @Column(name = "payment_coupon", length = 40, nullable = false)
    private int coupon_amount;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", unique = true)
    private Reservation reservation;

    public void setReservationFromPayment(Reservation reservation){
        this.reservation = reservation;
    }
}
