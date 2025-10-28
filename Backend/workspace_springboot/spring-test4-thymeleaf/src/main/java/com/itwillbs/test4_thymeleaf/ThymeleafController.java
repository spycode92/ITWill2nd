package com.itwillbs.test4_thymeleaf;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {
	// 단일 데이터를 Model 객체에 담아 타임리프 템플릿 페이지(= 뷰페이지)에서 출력하기
	@GetMapping("test1")
	public String test1(Model model) {
		// 데이터 공유 객체 Model 객체에 "data" 라는 속성명으로 단일 텍스트 저장
		model.addAttribute("data", "타임리프 예제");
		
		// Model 객체에 "data2" 라는 속성명으로 정수 데이터 저장
		model.addAttribute("data2", 100);
		
		// return 문 뒤에 포워딩 할 뷰페이지를 명시
		// 포워딩 기준이 되는 경로는 src/main/resources/templates 디렉토리(패키지) 가 된다
		return "/thymeleaf_test1"; // 확장자명(.html) 생략 가능
	}
	// -----------------------------------------------
	@GetMapping("test2")
	public String test2(Model model) {
		// MemberDTO 객체 생성
		// 1) 기본 생성자 활용
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setId(1L);
		memberDTO.setName("홍길동");
		memberDTO.setUserId("hong");
		memberDTO.setPasswd("1234");
		memberDTO.setGrade(1);
		memberDTO.setRegTime(LocalDateTime.now());
		
		
		
		// Model 객체에 "memberDTO" 라는 속성명으로 MemberDTO 객체 저장
		model.addAttribute("memberDTO", memberDTO);
		
		return "/thymeleaf_test2";
	}
	
}














