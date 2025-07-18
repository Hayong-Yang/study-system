package com.koreait.restapi.service;

import com.koreait.restapi.dto.MemberDTO;
import com.koreait.restapi.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(MemberDTO member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRole("ROLE_USER");
        mapper.save(member);
    }

    @Override
    public boolean isUsernameTaken(String username) {
        return mapper.findByUserName(username) != null;
    }

    @Override
    public MemberDTO getUserByUsername(String username) {
        return mapper.findByUserName(username);
    }

    @Override
    public void update(String sessionUsername, MemberDTO member) {
        MemberDTO original = mapper.findByUserName(sessionUsername);
        if (original != null) {
            if (StringUtils.hasText(member.getPassword())) {
                String hashed = passwordEncoder.encode(member.getPassword());
                original.setPassword(hashed);
            }
            if (StringUtils.hasText(member.getName())) {
                original.setName(member.getName());
            }
            mapper.update(original);
        }
    }
} // class
