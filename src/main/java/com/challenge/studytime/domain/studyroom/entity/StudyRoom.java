package com.challenge.studytime.domain.studyroom.entity;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.studyroom.dto.request.StudyRoomModifyRequestDto;
import com.challenge.studytime.domain.studyroom.dto.request.StudyRoomRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_room_id")
    private Long id;
    @Column(name = "max_capacity",length = 2, nullable = false)
    private int capacity;
    @Column(name = "study_room_price",length = 10, nullable = false)
    private int price;
    @Column(name = "study_room_name",length = 20, nullable = false)
    private String name;
    @Column(name = "study_room_location",length = 100, nullable = false)
    private String location;
    @Column(name = "study_room_description", length = 255, nullable = false)
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    public void setMemberFromStudyRoom(Member member){
        this.member = member;
        if(member != null && !member.getStudyRooms().contains(this)){
            member.getStudyRooms().add(this);
        }
    }
    public void modifyStudyRoom(StudyRoomModifyRequestDto requestDto){
        if (requestDto.getCapacity() > 0)
            this.capacity = requestDto.getCapacity();
        if (requestDto.getPrice() > 0)
            this.price = requestDto.getPrice();
        if (requestDto.getName() != null)
            this.name = requestDto.getName();
        if (requestDto.getLocation() != null)
            this.location = requestDto.getLocation();
        if (requestDto.getDescription() != null)
            this.description = requestDto.getDescription();
    }
}
