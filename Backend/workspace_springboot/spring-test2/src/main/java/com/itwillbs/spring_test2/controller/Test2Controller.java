package com.itwillbs.spring_test2.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwillbs.spring_test2.dto.Test2DTO;

// 모든 매핑 메서드가 @ResponseBody 어노테이션을 사용할 경우 컨트롤러 클래스를 @RestController 로 선언 시 @ResponseBody 생략 가능
@RestController
public class Test2Controller {
	
//	@RequestMapping(name = "/members/new", method = RequestMethod.GET)
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
	
	// 파라미터들을 DTO 타입 대신 java.util.Map 타입을 활용하여 바인딩 가능 => 가장 유연한 바인딩
	// => 주의! Map 타입은 기본적으로 내부에 아무런 Entry(key, value)를 갖지 않으므로 기본적으로 파라미터가 바인딩 될 수 없다!
	//    따라서, 자동으로 파라미터에 해당하는 엔트리를 추가(바인딩)하도록 하려면
	//    Map 타입 파라미터 선언 시 바인딩 용도로 지정하는 @RequestParam 어노테이션 필수!
	@GetMapping("/members/list3")
	public String memberList3(@RequestParam Map<String, String> params) {
		// 주의! 모든 파라미터의 key, value 가 String 타입으로 바인딩 됨(%d 포맷 대신 %s 사용)
		return """
				/members/list3 요청<br>
				page 파라미터 : %s<br>
				limit 파라미터 : %s<br>
				name 파라미터 : %s
				""".formatted(params.get("page"), params.get("limit"), params.get("name"));
	}
	
	
	
	
	
}














