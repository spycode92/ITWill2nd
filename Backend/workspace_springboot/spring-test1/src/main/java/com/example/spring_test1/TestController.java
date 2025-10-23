package com.example.spring_test1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestController {
	
//	@ResponseBody
//	@GetMapping("/")
//	public String hello() {
//		return "main.html";
//	}
	
	@GetMapping("/")
	public String hello() {
		return "main.html";
		// 스프링부트 컨트롤러에서 기본적으로 return 문 뒤에 파일명 지정하면 해당 파일 내용을 렌더링하여 응답으로 전송
		// => 렌더링 할 페이지의 기본 탐색 경로 : src/main/resources/static
	}
	
	@GetMapping("/test")
	public String hello2() {
		return "test/main2.html";
	}
	
	
}
















