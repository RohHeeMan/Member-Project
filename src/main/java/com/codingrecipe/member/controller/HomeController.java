package com.codingrecipe.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // servlet-context.xml에서 /WEB-INF/views/에 jsp파일을 놓기로 설정함
    @GetMapping("/")
    public String Index(){
        return "index";
    }
}
