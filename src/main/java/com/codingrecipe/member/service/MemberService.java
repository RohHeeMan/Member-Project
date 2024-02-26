package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    // 의존성 주입으로 memberRepository를 사용할 것임
    private final MemberRepository memberRepository;

    public int save(MemberDTO memberDTO) {
        return memberRepository.save(memberDTO);
    }


    public boolean login(MemberDTO memberDTO) {
        // 로그인 구현
        // 1. memberRepository에서 읽어서 해당 사용자가 존재하면
        //    MemberDTO 객체에 true 아니면 false 반환
        MemberDTO loginMember = memberRepository.login(memberDTO);
        // 조회하여 찾아온 멤버가 있을 경우
        if (loginMember != null){
            return true;
        }else{
            return false;
        }
    }


    public List<MemberDTO> findAll() {
        return memberRepository.findAll();
    }
}
