package com.koreait.restapi.mapper;

import com.koreait.restapi.dto.StudyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudyMapper {
    void insertStudy(StudyDTO studyDTO);
    // 전체 목록 조회 (페이지네이션)
    List<StudyDTO> findAll(@Param("offset") int offset, @Param("size") int size);
    // 총 게시물 수 조회
    int countAll();
    // 단일 스터디 조회
    StudyDTO findById(@Param("id") int id);
    // 스터디 수정
    void update(StudyDTO dto);
    // 스터디 삭제
    void delete(@Param("id") int id);
    //해당 스터디 신청횟수 확인
    int countApplication(@Param("studyId") int studyId, @Param("memberId") int memberId);
    // 현재 신청자수 확인
    int countCurrentApplicants(@Param("studyId") int studyId);
    int getMaxPeopleByStudyId(@Param("studyId") int studyId);
    // 스터디 신청
    void insertApplication(@Param("studyId") int studyId, @Param("memberId") int memberId);
    // 스터디 목록 찾기
    List<StudyDTO> findStudiesByMemberId(int memberId);
}
