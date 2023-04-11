package com.challenge.studytime.domain.coupon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@DynamicUpdate
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;
    @Column(name = "coupon_name", length = 40, nullable = false)
    private String couponName;
    @Column(name = "discount_value", length = 10, nullable = false)
    private int discountValue;
    @Column(name = "max_value", length = 10 , nullable = false)
    private int maxissuedCount;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "start_time", length = 45, nullable = false)
    private LocalDateTime assignedAt;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Column(name = "end_time", length = 45,nullable = false)
    private LocalDateTime endAt;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
    private List<CouponHistory> couponHistories = new ArrayList<>();
    @Builder
    public Coupon(String couponName, int discountValue, int maxissuedCount, LocalDateTime endAt){
        this.couponName = couponName;
        this.discountValue = discountValue;
        this.maxissuedCount = maxissuedCount;
        this.assignedAt = LocalDateTime.now();
        this.endAt = endAt;
    }
    public void modifyTime(int time){
        this.endAt = endAt.plusDays(time);
    }
}
