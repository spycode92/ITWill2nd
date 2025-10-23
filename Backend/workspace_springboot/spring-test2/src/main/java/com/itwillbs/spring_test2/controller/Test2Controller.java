package com.itwillbs.spring_test2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.spring_test2.dto.Test2DTO;

// 모든 매핑 메서드가 @ResponseBody 어노테이션을 사용할 경우 컨트롤러 클래스를 @RestController 로 선언 시 @ResponseBody 생략 가능
@RestController
public class Test2Controller {
	
	@GetMapping("/members/new")
	public String newMember() {
		return "/members/new 요청";
	}
	
	// 요청 URL 을 통해 전달되는 파라미터 바인딩
	// => 파라미터 변수 선언부 앞에 @RequestParam 어노테이션을 통해 파라미터명 지정하여 바인딩 필수!
	//    (파라미터명만 지정 시에는 name 속성 생략 가능)
	// => defaultValue 속성을 통해 전달되지 않은 파라미터일 경우 기본값 설정 가능
	@GetMapping("/members/list")
	public String memberList(
			@RequestParam(name = "page", defaultValue = "1") int page, 
			@RequestParam(name = "limit") int limit) {
		return """
				/members/list 요청<br>
				page 파라미터 : %d<br>
				limit 파라미터 : %d
				""".formatted(page, limit);
	}
	
	// 파라미터들을 DTO 타입 객체로 한꺼번에 바인딩할 경우 DTO 클래스 타입 선언 시 파라미터명과 일치하는 필드에 자동으로 바인딩
	// => 이러한 역할을 수행하는 파라미터 선언 시 @ModelAttribute 어노테이션을 지정(생략 가능)
	@GetMapping("/members/list2")
	public String memberList2(@ModelAttribute Test2DTO test2DTO, @RequestParam("name") String name) {
		return """
				/members/list2 요청<br>
				page 파라미터 : %d<br>
				limit 파라미터 : %d<br>
				name 파라미터 : %s
				""".formatted(test2DTO.getPage(), test2DTO.getLimit(), name);
	}
	
	
	
	
}














