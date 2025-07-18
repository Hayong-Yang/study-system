package com.koreait.restapi.service;

import com.koreait.restapi.dto.StudyDTO;

import java.util.List;
import java.util.Map;

public interface StudyService {
    void createStudy(StudyDTO studyDTO);
    Map<String, Object> getStudyList(int page, int size);
    void updateStudy(StudyDTO dto);
    void deleteStudy(int id, int loginUserId);
    void applyToStudy(int studyId, int memberId);
    List<StudyDTO> getMyApplications(int memberId);
}
