package com.challenge.studytime.domain.study.entity;

import com.challenge.studytime.domain.image.entity.ImageData;
import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.study.dto.request.StudyModifyRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;

    @Column(name = "study_title", length = 100, nullable = false)
    private String title;

    @Column(name = "study_content", length = 255, nullable = false)
    private String content;

    @Column
    private int membersCount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StudyMember> studyMembers = new ArrayList<>();

    @OneToMany(mappedBy = "studys", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ImageData> imageDataList = new ArrayList<>();


    @Builder.Default
    private boolean deleteStudy = false;

    public void changeStudy() {
        deleteStudy = true;
    }



    public void decreaseMembersCount() {
        this.membersCount--;
    }

    public void updateStudy(StudyModifyRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.membersCount = requestDto.getMembersCount();

    }

    //연관관계 편의 메소드
    public void addMemberWithStudy(Member member) {
        this.member = member;
        member.getStudyList().add(this);
    }

}
