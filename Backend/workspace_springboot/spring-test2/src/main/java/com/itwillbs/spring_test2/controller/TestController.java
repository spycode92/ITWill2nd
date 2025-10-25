package com.itwillbs.spring_test2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	
	@ResponseBody // 응답 데이터 그대로 클라이언트 측으로 전송
	@GetMapping("/test")
	public String test() {
		return "/test 주소 매핑 완료!";
	}
	
	// 응답 데이터로 "확인(줄바꿈)테스트 페이지" 전송
	@ResponseBody
	@GetMapping("/test2")
	public String test2() {
//		return "확인<br>테스트 페이지";
		
//		return "확인<br>"
//				+ "테스트 페이지";
		
		// ----------------------------------------------------
		// 자바 15 버전부터 텍스트 블럭 기능이 제공
		// => 문자열("") 기호 사이에 " 기호를 단순 문자열 그대로 추가 가능하도록 텍스트에 대한 블럭을 지정하는 기능
		// => """ 기호와 """ 기호 사이에 줄바꿈 등의 원하는 문자열을 자유롭게 작성 (자바스크립트의 백틱과 유사한 기능)
//		return """
//				확인<br>
//				테스트 페이지222
//				"""; 
		
//		return """
//				{
//					"name": "홍길동",
//					"age": 20
//				}
//				""";
		// ----------------------------------------------------
		String name = "홍길동";
		int age = 20;
		double height = 180.154;
		
		// 이름 : 홍길동, 나이 : 20, 키 : 180.1cm
		System.out.println("이름 : " + name + ", 나이 : " + age + ", 키 : " + height + "cm");
		
		// System.out.printf() 메서드를 활용하여 포맷팅 가능
		System.out.printf("이름 : %s, 나이 : %d, 키 : %.1f cm", name, age, height);
		
		// 자바 15 부터 String 타입 문자열에 formatted() 메서드 연결하여 printf() 메서드와 동일하게 포맷팅 가능
		return "이름 : %s<br>나이 : %d<br>키 : %.1f cm".formatted(name, age, height);
	}
	
	
	
}














