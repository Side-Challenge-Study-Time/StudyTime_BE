package com.challenge.studytime.domain.studyroom.service;

import com.challenge.studytime.domain.member.entity.Member;
import com.challenge.studytime.domain.member.repositry.MemberRepositry;
import com.challenge.studytime.domain.studyroom.dto.request.StudyRoomModifyRequestDto;
import com.challenge.studytime.domain.studyroom.dto.request.StudyRoomRequestDto;
import com.challenge.studytime.domain.studyroom.dto.response.StudyRoomResponseDto;
import com.challenge.studytime.domain.studyroom.entity.StudyRoom;
import com.challenge.studytime.domain.studyroom.repository.StudyRoomRepository;
import com.challenge.studytime.global.exception.member.NotFoundMemberid;
import com.challenge.studytime.global.exception.studyroom.NotFoundStudyRoom;
import com.challenge.studytime.global.exception.studyroom.NotillegalException;
import com.challenge.studytime.global.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyRoomService {
    private final MemberRepositry memberRepositry;
    private final StudyRoomRepository studyRoomRepository;
    private void validateRequestDto(StudyRoomRequestDto requestDto) {
        if (requestDto.getPrice() <= 0 || requestDto.getCapacity() <= 0) {
            throw new NotillegalException();
        }
    }
    @Transactional
    public StudyRoomResponseDto registerStudyRoom(LoginUserDto userDto, StudyRoomRequestDto requestDto){
        Member member = memberRepositry.findById(userDto.getMemberId())
                .orElseThrow(() -> new NotFoundMemberid(userDto.getMemberId()));
        validateRequestDto(requestDto);
        StudyRoom studyRoom = studyRoomRepository.save(StudyRoom.builder()
                .price(requestDto.getPrice())
                .capacity(requestDto.getCapacity())
                .name(requestDto.getName())
                .location(requestDto.getLocation())
                .description(requestDto.getDescription())
                .build());
        studyRoom.setMemberFromStudyRoom(member);
        return StudyRoomResponseDto.toDto(studyRoom);
    }
    public List<StudyRoomResponseDto> convertToDtoList(List<StudyRoom> studyRooms) {
        List<StudyRoomResponseDto> studyRoomResponseDtoList = new ArrayList<>();
        for (StudyRoom studyRoom : studyRooms) {
            StudyRoomResponseDto studyRoomResponseDto = StudyRoomResponseDto.builder()
                    .id(studyRoom.getId())
                    .price(studyRoom.getPrice())
                    .capacity(studyRoom.getCapacity())
                    .name(studyRoom.getName())
                    .location(studyRoom.getLocation())
                    .description(studyRoom.getDescription())
                    .build();
            studyRoomResponseDtoList.add(studyRoomResponseDto);
        }
        return studyRoomResponseDtoList;
    }
    @Transactional(readOnly = true)
    public List<StudyRoomResponseDto> detailStudyRoom(Long roomId){
        List<StudyRoom> studyRooms = studyRoomRepository.findAllById(roomId);
        if (studyRooms.isEmpty()){
            throw new NotFoundStudyRoom();
        }
        return convertToDtoList(studyRooms);
    }
    @Transactional(readOnly = true)
    public List<StudyRoomResponseDto> fullSearchStudyRoom(){
        List<StudyRoom> studyRooms = studyRoomRepository.findAll();
        return convertToDtoList(studyRooms);
    }
    @Transactional
    public void deleteStudyRoom(Long roomId){
        StudyRoom studyRoom = studyRoomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundStudyRoom());
        studyRoomRepository.deleteById(studyRoom.getId());
    }
    @Transactional
    public void modifyStudyRoom(Long memberId, Long roomId, StudyRoomModifyRequestDto requestDto){
        if (!studyRoomRepository.existsByIdAndMemberId(roomId, memberId)){
            throw new NotFoundStudyRoom();
        }
        StudyRoom studyRoom = studyRoomRepository.findByIdAndMemberId(roomId, memberId);
        studyRoom.modifyStudyRoom(requestDto);
    }
}
