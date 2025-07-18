package com.koreait.restapi.controller;

import com.koreait.restapi.dto.MemberDTO;
import com.koreait.restapi.dto.StudyDTO;
import com.koreait.restapi.service.MemberService;
import com.koreait.restapi.service.StudyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins="http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;
    private final MemberService memberService;

    @PostMapping("/create")
    public ResponseEntity<?> createStudy(@RequestBody StudyDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        User user = (User) authentication.getPrincipal(); // Spring Security User 객체
        String username = user.getUsername();

        // username으로 Member 정보 가져오기
        MemberDTO loginUser = memberService.getUserByUsername(username);
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원 정보 없음");
        }

        dto.setWriterId(loginUser.getId());
        studyService.createStudy(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Map<String, Object> getStudies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return studyService.getStudyList(page, size);
    }

    // 스터디 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudy(@PathVariable int id, HttpSession session) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new RuntimeException("로그인 필요");
        }

        studyService.deleteStudy(id, loginUser.getId());
        return ResponseEntity.ok().build();
    }

    // 스터디 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudy(
            @PathVariable int id,
            @RequestBody StudyDTO dto,
            HttpSession session
    ) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            throw new RuntimeException("로그인 필요");
        }

        dto.setId(id);
        dto.setWriterId(loginUser.getId());
        studyService.updateStudy(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/apply/{id}")
    public ResponseEntity<?> applyToStudy(@PathVariable("id") int studyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();

        MemberDTO loginUser = memberService.getUserByUsername(username);
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("회원 정보 없음");
        }

        try {
            studyService.applyToStudy(studyId, loginUser.getId());
            return ResponseEntity.ok().body("신청 완료");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/my-applications")
    public List<StudyDTO> getMyApplications() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 필요");
        }

        User user = (User) authentication.getPrincipal();
        MemberDTO loginUser = memberService.getUserByUsername(user.getUsername());
        return studyService.getMyApplications(loginUser.getId());
    }


} // class
