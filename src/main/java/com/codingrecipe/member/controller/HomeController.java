package com.codingrecipe.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // servlet-context.xml에서 /WEB-INF/views/에 jsp파일을 놓기로 설정함
    // 최초 Commit당시에는 root-context.xml에 database관련 셋팅이 있는데
    // 그것이 있을 경우 에러가 나니 기본 셋팅만 할것

    //
    @GetMapping("/")
    public String Index(){
        return "index";
    }
}
