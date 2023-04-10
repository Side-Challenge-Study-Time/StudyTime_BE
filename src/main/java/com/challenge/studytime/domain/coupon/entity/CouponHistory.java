package com.challenge.studytime.domain.coupon.entity;

import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @Column(nullable = false, length = 10)
    private Boolean used;
    //연관 관계 메소드 수정
    @Builder
//    set말고 명칭 수정
    public void setCoupon(Member member, Coupon coupon) {
        this.coupon = coupon;
        this.used = false;
        this.member = member;
    }
}
