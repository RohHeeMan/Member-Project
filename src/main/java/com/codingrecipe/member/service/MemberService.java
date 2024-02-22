package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    // 의존성 주입으로 memberRepository를 사용할 것임
    private final MemberRepository memberRepository;
    public int save(MemberDTO memberDTO) {
        return memberRepository.save(memberDTO);
    }
}
