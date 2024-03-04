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

    // 로그인 구현
    public Boolean login(MemberDTO memberDTO) {
        // true / false를 MemberController에 넘겨주면 MemberController에서 받아서 어디로 분기할지 판단한다.
        // boolean으로 리턴하지만 리턴할 경우 DTO형태로 받아서 판단한다.
        // MemberDTO 형태로 결과를 받아서 true / false를 반환한다.
        // 1. memberRepository에서 읽어서 해당 사용자가 존재하면
        //    MemberDTO 객체에 true 아니면 false 반환
        MemberDTO loginResult = memberRepository.login(memberDTO);
        if (loginResult != null){
            return true;
        }else {
            return false;
        }
    }

    public List<MemberDTO> findAll() {
        // memberRepository의 findAll을 호출해서 그것의 결과를
        // List<MemberDTO>의 형태로 전달 받는다.
        return memberRepository.findAll();
    }

    // 상세조회 : RequestParam이용 방법
    public MemberDTO findById(Long id) {
        // MemberDTO의 형태로 리턴 받을 것임.
        return memberRepository.findById(id);
    }

//    // 상세조회 : memberDTO 이용 방법
//    public MemberDTO findById(MemberDTO memberDTO) {
//        // MemberDTO의 형태로 리턴 받을 것임.
//        return memberRepository.findById(memberDTO);
//    }

//    public boolean delete(Long id) {
//        int deleteResult = memberRepository.delete(id);
//        return deleteResult > 0;
//    }

    public int delete(Long id) {
        int deleteResult = memberRepository.delete(id);

        // delete가 삭제된 행의 수를 나타내므로 그냥 값을 넘겨주면 된다.
        return deleteResult;

    }

}
