package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@Slf4j

@Controller
// RequestMapping으로 선언해 놓고 하단에서는 하위 경로만 써도 된다.
// WEB-INF/views/각종 뷰 호출
//@RequestMapping("/member")

// 롬복을 이용해서 자동으로 생성자 생성(의존성 주입하기 위해서)
// 객체로 넘기기 위해
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
    // 사용자가 입력한 자료를 전달 받아 로그인 후 세션을 저장한다.
    // memberDTO => 사용자가 입력한 값을 전달
    // memberDTO를 넘겨 받아 ( @RequestParam을 사용해서 처리 할 수 있으나 귀찮으니 DTO로 전달받아 처리함 )
    // @PostMapping의 경우는 @ModelAttribute를 이용해서 객체를 파라미터로 받을수 있음.
    // HttpSession을 사용해서 세션을 저장해야 한다.
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

    // 회원 목록 조회 ( 🎈🎈💕💕 == 중요 ==
    //                /member/ 경로로 들어오면 요청만을 처리하고
    //                /member 경로로 들어오는 요청은 처리 하지 않음
    //                )
    @GetMapping("/member/")
    // Model을 이용해서 전달 할 것이다.
    public String findAll(Model model){
        // 단순히 서비스를 호출 한다.(넘길 파라미터가 없음)
        // List의 형태로 MemberDTO의 여러가지 결과값을 전달 받는다.
        List<MemberDTO> memberDTOList = memberService.findAll();
        // 매개변수로 전달받은 결과를 model.addAttribute("key", "value");
        // 메소드를 이용해서 view에 전달할 데이터를 key, value 쌍으로 전달함.
        // model에 추가하여 memberList이름으로 list.jsp로 memberDTOList를 넘긴다.
        model.addAttribute("memberList",memberDTOList);
        return "list";
    }

    // 상세 조회 ( /member까지 밖에 없고 그 뒤에 id가 따라오므로 /를 쓰면 안됨 )
    // 상세 조회 ( 🎈🎈💕💕 == 중요 ==
    //            /member/ 경로로 들어오면 요청만을 처리하고
    //            /member 경로로 들어오는 요청은 처리 하지 않음
    //                )
    // /member?id=1

     // 상세 조회 : @RequestParam 사용 방법 ( 파라미터를 사용하여 처리 )
    @GetMapping("/member")
    // MemberDTO의 id가 Long형태이므로 Long형으로 @RequestParam에 넘겨줘야 함
    public String findById(@RequestParam(name="id") Long id,Model model){
        // memberDTO의 형태로 사용자를 찾아서 결과를 받은 후
        MemberDTO memberDTO = memberService.findById(id);
        // 모델에 추가해서
        model.addAttribute("member",memberDTO);
        // detail.jsp를 호출한다.
        return "detail";
    }

//    // 상세 조회 : MemberDTO 사용 방법 ( MemberDTO 객체를 넘겨 처리 )
//    @GetMapping("/member")
//    public String findById(@ModelAttribute MemberDTO memberDTO,Model model){
//        // memberDTO 객체를 전부 넘겨 처리 한다.
//        MemberDTO memberfindById = memberService.findById(memberDTO);
//        model.addAttribute("member",memberfindById);
//        return "detail";
//    }

    // 삭제 처리 하기 ( 그냥 리턴값을 받지 않고 처리 할수도 있다 )
    @GetMapping("/member/delete")
    public String delete(@RequestParam("id") Long id){
        int deleteResult = memberService.delete(id);
        // boolean으로 처리 해도 되고 int로 처리해도 된다.
        if (deleteResult > 0){
            // 반드시 뒤에 /(slash)가 있어야 됨. 현재페이지로 다시 돌아옴.
            return "redirect:/member/";
        } else {
            return "redirect:/member/error";
        }
    }
}
