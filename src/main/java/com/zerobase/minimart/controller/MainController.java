package com.zerobase.minimart.controller;

// MainPage 클래스를 만든 목적!!
// 매핑하기 위해서...
// 주소와(논리적인주소인터넷주소) 물리적인파일 (프로그래밍을 해야하니까)매핑


// http://www.naver.com/new/list.do
// 하나의 주소에 대해서
// 어디서 매핑? 누가 매핑?
// 후보군? 클래스, 속성, 메소드
// http://localhost:8080/

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class MainController {


    @RequestMapping("/")
    public String index() { return "index"; }

    @RequestMapping("error/denied")
    public String errorDenied() {
        return "error/denied";
    }

}
