package com.challenge.studytime.domain.point.entity;

import com.challenge.studytime.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;
    @Column(name = "member_point" ,length = 10, nullable = false)
    private int wallet;
    @Column(name = "point_create_time", length = 40, nullable = false)
    @CreatedDate
    private LocalDateTime createAt;
    @Column(name = "point_modify_time", length = 40, nullable = false)
    @LastModifiedDate
    private LocalDateTime modifyAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    public void setMemberFromPoint(Member member){
        this.member = member;
    }

    public void chargePoint(int point){
        this.wallet += point;
    }
    public void usePoint(int point){
        this.wallet -= point;
    }
}
