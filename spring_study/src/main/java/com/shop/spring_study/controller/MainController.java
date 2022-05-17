package com.shop.spring_study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.shop.spring_study.vo.MemberVo;

// spring boot는 처음 생성시 지정한 패키지 이하에 있는
// @Controller or @Respcontroller에 맵핑된 함수를 찾아서 url에 등록한다.
@Controller
public class MainController {
	@GetMapping("/")
	public String index() {
		return "index"; // request.getRequestDispatcher(/index.html).forward()
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
//	@PostMapping("/login")
//	public String loginAction(String id,String pw,@RequestParam byte state) {
//		// String id=request.getParameter("id")
//		System.out.println(id+"/"+pw+"("+state+") 로그인 중");
//		return "index";
//	}
	@PostMapping("/login")
	public String loginAction(MemberVo mem) {
		// String id=request.getParameter("id")
		System.out.println(mem);
		return "index";
	}
}




