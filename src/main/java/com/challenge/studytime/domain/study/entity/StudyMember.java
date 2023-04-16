package com.challenge.studytime.domain.study.entity;

import com.challenge.studytime.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudyMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "study_id")
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;


    public void setStudy(Study study) {
        this.study = study;
        if (study != null && !study.getStudyMembers().contains(this)) {
            study.getStudyMembers().add(this);
        }
    }

    public void setMember(Member member) {
        this.member = member;
        if (member != null && !member.getStudyMembers().contains(this)) {
            member.getStudyMembers().add(this);
        }
    }
}
