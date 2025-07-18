package com.koreait.restapi.service;

import com.koreait.restapi.dto.MemberDTO;

public interface MemberService {
    void register(MemberDTO member);
    boolean isUsernameTaken(String username);
    MemberDTO getUserByUsername(String username);

    void update(String token, MemberDTO member);
}
