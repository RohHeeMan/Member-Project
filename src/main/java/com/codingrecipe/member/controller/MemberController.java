package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    // Save폼에서 전달 받은 데이터를 Post처리 함
    // @ModelAttribute : 사용자가 요청 시 전달하는 값을 오브젝트(객체) 형태로 매핑해주는 애노테이션이다
    //                   생성자를 자동으로 만들어주고 Model로 받을수 있도록 만들어 주는 어노테이션이다
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

    // 로그인 폼 불러오기
    @GetMapping("/member/login")
    public String loginForm(){
        return "/login";
    }

    /*
        전체 로그인 구현 해석
        1. MemberController: MemberDTO를 통해서 login.jsp 자료 받음
        1-1. memberService로 memberDTO로 넘겨서 결과를 loginResult에 반환
        1-2. 성공시 세션을 저장하고 main.jsp로 이동 / 실패시 다시 login.jsp로 돌아감

        2. memberService : memberRepository에서 memberMapper.xml 쿼리를 읽어서 해당 사용자가 존재하면
                           MemberDTO 객체에 true 아니면 false 반환

        3. memberRepository : memberMapper.xml 쿼리를 읽어 MemberService에 MemberDTO객체로 반환함
                              sql.selectOne("Member.login",memberDTO)
     */

    // 로그인 구현 ( 로그인의 경우는 세션도 가지고 다녀야 하므로 세션구현 )
    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        // memberDTO 전체를 넘긴다... 필요한 것만 사용하면 되니까.
        boolean loginResult = memberService.login(memberDTO);
        if (loginResult == true){
            // 세션 저장 ( session.setAttribute = name, value )
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            // 성공시 세션을 저장하고 main으로 이동
            return "main";
        }else {
            // 실패시 그냥 login폼으로 이동
            return "login";
        }
    }

    // 회원 목록 조회
    @GetMapping("/member")
    public String findAll(Model model){
        // DTO의 List로 받아 온다
        // 서비스를 통해 리파지터리에서 데이터를 받아와 memberDTOList에 List형태로 받음
        // memberService로 파라미터 없이 그냥 호출해서 model로 받아 list.jsp로 넘길 것임.
        List<MemberDTO> memberDTOList = memberService.findAll();
        // 매개변수로 전달받은 결과를 model.addAttribute("key", "value");
        // 메소드를 이용해서 view에 전달할 데이터를 key, value 쌍으로 전달함.
        // model에 추가하여 memberList이름으로 list.jsp로 넘긴다.
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }



}
