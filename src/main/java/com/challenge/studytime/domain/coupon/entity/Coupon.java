package com.challenge.studytime.domain.coupon.entity;

import com.challenge.studytime.domain.coupon.dto.CouponRequestDto;
import com.challenge.studytime.domain.couponhistory.entity.CouponHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;

import javax.persistence.*;
import java.time.LocalDate;
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
    //할인율
    private Integer discountValue;
    //총 발급한 갯수
    private Integer issuedCount;
    //최대 가능한 발급 갯수
    private Integer maxissuedCount;
    //발급 시간
    private LocalDate assignedAt;
    //만료 시간
    private LocalDate endAt;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
    private List<CouponHistory> couponHistories = new ArrayList<>();
    public Coupon (CouponRequestDto requestDto){
        this.discountValue = requestDto.getDiscountValue();
        this.issuedCount = 0;
        this.maxissuedCount = requestDto.getMaxissuedCount();
        this.assignedAt = LocalDate.now();
        this.endAt = requestDto.getEndAt();
    }
    public void setIssuedCount(int issuedCount) {
        this.issuedCount = issuedCount + 1;
    }
}
