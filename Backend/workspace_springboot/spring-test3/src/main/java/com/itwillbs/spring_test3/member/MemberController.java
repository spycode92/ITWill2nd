package com.itwillbs.spring_test3.member;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
	
	@GetMapping("/memberList")
	public List<Member> showMemberList() {
		MemberService memberService = new MemberService();
		
		// MemberService - getMemberList() 메서드 호출하여 비즈니스 로직 처리 요청
		// => 파라미터 : 없음    리턴타입 : List<Member>(memberList)
		List<Member> memberList = memberService.getMemberList();
		
		// 스프링부트는 Jackson 라이브러리가 기본으로 포함되어 동작하기 때문에
		// 매핑 메서드에서 객체 리턴 시 자동으로 JSON 형식으로 변환되어 응답 데이터로 전송함
		return memberList; // List<Member> 타입 객체가 JSON 형식 문자열로 자동 변환되어 리턴
	}
	
	
}
