package com.challenge.studytime.domain.coupon.entity;

import com.challenge.studytime.domain.couponhistory.entity.CouponHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //할인 코드
    private Code code;
    //할인율
    private double discountValue;
    //총 발급한 갯수
    private Integer issuedCount;
    //최대 가능한 발급 갯수
    private Integer maxissuedCount;
    //발급 시간
    private LocalDateTime assignedAt;
    //만료 시간
    private LocalDateTime endAt;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
    private List<CouponHistory> couponHistories = new ArrayList<>();
    public void updateIssuedCount() {
        this.issuedCount++;
    }
}
