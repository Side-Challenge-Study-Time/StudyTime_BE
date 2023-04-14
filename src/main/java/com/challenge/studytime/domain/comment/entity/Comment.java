package com.challenge.studytime.domain.comment.entity;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.study.entity.Study;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Comment parent;

    @Builder.Default
    @JsonManagedReference
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> replies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "study_id")
    private Study study;

    public void addCommentWithMember(Member member) {
        this.member = member;
        member.getComments().add(this);
    }

    public void addCommentWithStudy(Study study) {
        this.study = study;
        study.getComments().add(this);
    }
}