package com.koreait.restapi.dto;

import lombok.*;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyDTO {
    private int id;
    private String title;          // 스터디 제목
    private String content;        // 스터디 설명
    private int maxPeople;         // 최대 모집 인원
    private Timestamp maxDate;     // 모집 마감일
    private int writerId;          // 작성자 ID
    private Timestamp createdAt;   // 생성일
    private Timestamp updatedAt;   // 수정일

} // CLASS
