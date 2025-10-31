package com.itwillbs.test5.member.controller;

import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwillbs.test5.member.dto.MemberDTO;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/members")
@Log4j2
public class MemberController {

	// 뷰페이지로 포워딩 시 입력값 검증으로 활용되는 DTO 객체(빈 객체)를 Model 객체에 담아 함께 전달
	@GetMapping("/regist")
	public String registForm(Model model) {
		model.addAttribute("memberDTO", new MemberDTO());
		return "/member/member_regist_form";
	}
	
	// POST 방식으로 요청되는 "/regist" 요청 매핑
	// => 파라미터로 전달되는 값들을 MemberDTO 객체에 바인딩
	// => ItemDTO 객체에 바인딩되는 파라미터들에 대한 Validation Check(입력값 검증) 수행을 위해 
	//    메서드 선언부에 MemberDTO 타입 파라미터를 선언하고 @Valid 어노테이션을 적용
	// => 체크 결과를 뷰페이지에서 활용하기 위해 뷰페이지에서 접근할 DTO 객체 이름을 @ModelAttribute 어노테이션 속성으로 명시
	// => 전달된 파라미터들이 MemberDTO 객체에 바인딩되는 시점에 입력값 검증을 수행하고 이 결과를 BindingResult 타입 파라미터에 저장해줌
	@PostMapping("/regist")
	public String regist(@ModelAttribute("memberDTO") @Valid MemberDTO memberDTO, BindingResult bindingResult) {
		log.info(">>>>>>>>>>>>>> memberDTO : " + memberDTO);
		
		// BindingResult 객체의 각 메서드를 호출하여 입력값 검증 결과 얻어낼 수 있음 
//		log.info(">>>>>>>>>>>>>> bindingResult.hasErrors : " + bindingResult.hasErrors()); // 검증 오류 발생 여부(true/false) 리턴
//		log.info(">>>>>>>>>>>>>> bindingResult.getAllErrors : " + bindingResult.getAllErrors()); // 전체 검증 결과에 대한 정보 리턴
		
		// 입력값 검증 결과가 true 일 때(검증 오류 발생 시) 다시 입력폼으로 포워딩
		if(bindingResult.hasErrors()) {
			// Model 객체를 활용하여 MemberDTO 객체나 BindingResult 객체를 별도로 저장하지 않아도 오류 정보가 자동으로 뷰페이지로 전송됨
			return "/member/member_regist_form";
		}
		
		// 가입 성공 시 메인페이지로 리다이렉트
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		return "/member/member_login_form";
	}
	
	
}














