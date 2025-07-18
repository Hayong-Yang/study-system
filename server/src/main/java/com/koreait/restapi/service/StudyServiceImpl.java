package com.koreait.restapi.service;

import com.koreait.restapi.dto.StudyDTO;
import com.koreait.restapi.mapper.StudyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    private final StudyMapper studyMapper;

    @Override
    public void createStudy(StudyDTO studyDTO) {
        studyMapper.insertStudy(studyDTO);
    }

    @Override
    public Map<String, Object> getStudyList(int page, int size) {
        int offset = (page - 1) * size;
        List<StudyDTO> studies = studyMapper.findAll(offset, size);
        int total = studyMapper.countAll();

        Map<String, Object> result = new HashMap<>();
        result.put("studies", studies);
        result.put("totalCount", total);
        return result;
    }

    @Override
    public void updateStudy(StudyDTO dto) {
        studyMapper.update(dto);
    }

    @Override
    public void deleteStudy(int id, int loginUserId) {
        StudyDTO existing = studyMapper.findById(id);
        if (!Integer.valueOf(existing.getWriterId()).equals(loginUserId)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        studyMapper.delete(id);
    }

    @Override
    public void applyToStudy(int studyId, int memberId) {
        // 이미 신청했는지 확인
        int existing = studyMapper.countApplication(studyId, memberId);
        if (existing > 0) {
            throw new IllegalArgumentException("이미 신청한 스터디입니다.");
        }

        // 현재 신청자 수 확인
        int currentApplicants = studyMapper.countCurrentApplicants(studyId);
        int maxPeople = studyMapper.getMaxPeopleByStudyId(studyId);

        if (currentApplicants >= maxPeople) {
            throw new IllegalStateException("모집 정원을 초과했습니다.");
        }

        // 신청 등록
        studyMapper.insertApplication(studyId, memberId);
    }

    @Override
    public List<StudyDTO> getMyApplications(int memberId) {
        return studyMapper.findStudiesByMemberId(memberId);
    }



} // class
