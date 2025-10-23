package com.itwillbs.spring_test2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/members2") // 요청 URL 중 앞부분의 "/members2" 에 해당하는 공통 주소 부분을 모두 매핑(클래스 내에서는 서브 도메인(하위 URL)만 매핑하면 됨)
public class Test3Controller {
	// 요청 URL 중에서 중복되는 부분을 클래스 상단에 분리
	// 1) URL 에서 기본 도메인을 제외한 요청 주소 중 공통 주소 부분(/members)을 컨트롤러 상단 별도의 매핑으로 분리(@RequestMapping 활용)
	// 2) 컨트롤러 내에서 공통 경로 매핑 후 남은 하위 경로를 각각 매핑
	@GetMapping("/new")
	public String newMember() {
		return "/members2/new 요청";
	}
	
	@GetMapping("/list")
	public String memberList() {
		return "/members2/list 요청";
	}
	// ==============================================================
	// RESTful API 형태로 요청 주소 처리
	// [ 1. GET 방식 요청 - 데이터 조회(READ) 용도의 요청으로 사용) ]
	// 1) "/members2" 요청 주소 매핑 - 회원 목록 조회(SELECT * FROM members) 요청하는 용도
	@GetMapping("") // 이미 컨트롤러 단에서 "/members2" 주소를 매핑했으므로 남은 주소 없음 = 널스트링 지정하여 매핑
	public String memberList2() {
		return "/members2 요청";
	}
	
	// 2) "/members2/id값" 요청 주소 매핑 - 단일 회원 정보 조회(SELECT * FROM members WHERE id = ?) 요청하는 용도
	// => 목록 조회와 달리 "/members2/xxx" 형식으로 아이디값이 함께 서브도메인 형태로 전달됨(ex. /members/5)
	//    이 때, 서브 도메인(경로) 형태로 전달되는 값을 추출 가능(경로를 마치 파라미터처럼 활용) = 경로 변수
	// => "/도메인/{추출할경로명}" 형식으로 매핑 URL 을 지정하고, 매핑 메서드에서 @PathVariable("추출할경로명") 형태로 바인딩
	@GetMapping("/{id}") // 공통 주소 부분인 "/members2" 제외한 뒷부분을 매핑 => 해당 경로가 현재 요청의 바인딩 대상
	public String memberInfo(@PathVariable("id") Long id) { // URL 중에서 "/members" 뒷부분의 경로(/5)가 바인딩 됨
//		return "/members2/" + id + " 요청";
		return """
				/members2/%d 요청222"
				""".formatted(id);
	}
	
	// =================================================
	// [ 2. POST 방식 요청 - 데이터 추가(CREATE) 용도의 요청으로 사용) ]
	// 회원 정보 추가(INSERT xxx) 를 요청하는 "/members2" 주소 매핑
	// => 회원 목록 조회를 요청하는 GET 방식 요청과 주소가 동일하지만, POST 방식 요청이므로 요청 메서드를 다르게 하여 서로 구분
	@PostMapping("") // 이미 컨트롤러 단에서 "/members2" 주소를 매핑했으므로 남은 주소 없음 = 널스트링 지정하여 매핑
	public String registMember(@RequestParam Map<String, String> params) {
		return """
				/members2(POST) 요청<br>
				이름 : %s<br>
				나이 : %s
				""".formatted(params.get("name"), params.get("age"));
	}
	
	
	
	
}













