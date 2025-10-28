package com.itwillbs.test4_thymeleaf;

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
		
		
		return new String();
	}
	
}














