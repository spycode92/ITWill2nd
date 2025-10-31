package com.itwillbs.test5.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/members")
@Log4j2
public class MemberController {

	@GetMapping("/regist")
	public String registForm() {
		return "/member/member_regist_form";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "/member/member_login_form";
	}
	
	
}
