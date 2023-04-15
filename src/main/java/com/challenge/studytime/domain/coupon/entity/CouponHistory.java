package com.challenge.studytime.domain.coupon.entity;

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
    @Column(name = "coupon_history_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @Column(name = "coupon_used", length = 10, nullable = false)
    private Boolean used;
    @Column(name = "coupon_code",length = 100 , nullable = false)
    private String uuid;
    public void setCouponFromCouponHistory(Coupon coupon){
        this.coupon = coupon;
        if (coupon != null && !coupon.getCouponHistories().contains(this)) {
            coupon.getCouponHistories().add(this);
        }
    }
    public void setMemberFromCouponHistory(Member member){
        this.member = member;
        if(member != null && !member.getCouponHistories().contains(this)){
            member.getCouponHistories().add(this);
        }
    }
    @Builder
    public CouponHistory(UUID uuid) {
        this.used = false;
        this.uuid = uuid.toString();
    }

    public void couponUsed(boolean b) {
        this.used = b;
    }
}
