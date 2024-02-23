package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
// RequestMapping으로 선언해 놓고 하단에서는 하위 경로만 써도 된다.
//@RequestMapping("/member")

// 롬복을 이용해서 자동으로 생성자 생성
@RequiredArgsConstructor
public class MemberController {
    // 의존성 주입, 생성자 주입
    // private final MemberService memberService;는
    // MemberService 타입의 memberService라는 이름을 가진 변경할 수 없는
    // (private final) 필드를 선언하는 것을 의미합니다.
    // 이 필드는 해당 클래스 내에서만 접근 가능하며 한 번 초기화되면 다시 할당할 수 없습니다.
    private final MemberService memberService;

    //@GetMapping("/save")
    // Save폼 불러오기
    @GetMapping("/member/save")
    public String SaveForm(){
        return "save";
    }

    // 로그인 폼 불러오기
    @GetMapping("/member/login")
    public String loginForm(){
        return "/login";
    }

    // Save폼에서 전달 받은 데이터를 Post처리 함
    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        // return 하기 위해서 Service와 Repository를 만들어 준다.
        int saveResult = memberService.save(memberDTO);
        // 저장 성공
        if (saveResult > 0){
            return "login";
        }
        // 실패시 다시 저장 하면으로 복귀
        else
        {
            return "save";
        }
    }
}
