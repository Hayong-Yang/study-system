package com.koreait.restapi.controller;

import com.koreait.restapi.dto.MemberDTO;
import com.koreait.restapi.jwt.JwtUtil;
import com.koreait.restapi.service.CustomUserDetailsService;
import com.koreait.restapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins="http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")  //api/member/register
    public ResponseEntity<?> register(@RequestBody MemberDTO member) {
        if (service.isUsernameTaken(member.getUsername())) {
            return ResponseEntity.badRequest().body("이미 사용 중인 아이디입니다.");
        }
        service.register(member);
        return ResponseEntity.ok("회원가입 성공");
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            return ResponseEntity.status(401).body("로그인되지 않았습니다.");
        }

        String username = authentication.getName();
        MemberDTO member = service.getUserByUsername(username);
        if (member != null) {
            member.setPassword(null); // 비밀번호 제거
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody MemberDTO member) {
        if (authentication == null || authentication.getName().equals("anonymousUser")) {
            return ResponseEntity.status(401).body("로그인되지 않았습니다.");
        }

        String username = authentication.getName();
        service.update(username, member);
        return ResponseEntity.ok("회원정보 수정 성공");
    }

} // class
