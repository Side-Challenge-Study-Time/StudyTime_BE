package com.challenge.studytime.domain.image.entity;

import com.challenge.studytime.domain.study.entity.Study;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ImageData")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "study_id")
    private Study studys;


    public void setStudy(Study study) {
        this.studys = study;
        if (study != null && !study.getImageDataList().contains(this)) {
            study.getImageDataList().add(this);
        }
    }

    @Builder
    public ImageData(String name, String type, byte[] imageData) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }
}