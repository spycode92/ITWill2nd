package com.itwillbs.spring_test3.member;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	/*
	 * A 클래스가 B 클래스에 의존적일 때 B 클래스에 대한 인스턴스를 직접 생성하지 않고,
	 * 의존성 자동 주입(DI = Dependency Injection) 기능을 통해 인스턴스를 전달받아 사용할 수 있다!
	 * 
	 * [ 의존성 주입을 받는 방법 3가지(+ 1가지) ]
	 * 0) 매핑 메서드 파라미터로 주입받을 클래스 타입 매개변수를 선언하는 방법
	 *    => 매핑 메서드가 호출될 때마다 해당 클래스 인스턴스가 생성되어 주입됨(매번 새로운 객체)
	 *    => 단, 이 클래스 타입의 매개변수는 메서드 내에서만 접근 가능하며
	 *       다른 메서드에서는 또 다른 인스턴스를 통해 주입받아 사용해야하므로 매개변수 선언 또 필요
	 *    => 해당 클래스를 평소처럼 정의하면 됨(별다른 추가 작업(어노테이션 설정) 불필요)
	 * -------------------------------------------------------------------------
	 * 만약, 멤버변수(필드) 형태로 주입받을 클래스 타입 변수를 선언하여 메서드마다 공유하는 경우
	 * => 해당 클래스(주입받을 클래스) 정의 시 @Component, @Service 등의 어노테이션을 통해 스프링 빈으로 등록 필수!
	 * => 어노테이션 미지정 시 자동 주입 불가능한 상태로 오류 발생함
	 * 1) 생성자를 통해 의존성 객체를 주입받는 방법
	 *    => 빈으로 등록하지 않았을 경우 오류 메세지 : Parameter 0 of constructor in com.itwillbs.spring_test3.member.MemberController required a bean of type 'com.itwillbs.spring_test3.member.MemberService' that could not be found.)
	 * 2) Setter 메서드를 통해 의존성 객체를 주입받는 방법(Setter 메서드에 @Autowired 어노테이션 필수)
	 *    => 필드에 @Autowired 어노테이션 생략 시 오류 메세지 : java.lang.NullPointerException: Cannot invoke "com.itwillbs.spring_test3.member.MemberService.getMemberList()" because "this.memberService" is null
	 * 3) 멤버변수 선언 시 의존성 객체를 주입받는 방법(멤버변수에 @Autowired 어노테이션 필수)
	 * 4) final 필드 형태로 선언한 후 생성자를 통해 객체 주입
	 *    => 파라미터 생성자를 직접 정의하거나 Lombok 의 @RequiredArgsConstructor 어노테이션을 지정하여 주입
	 */
	
	// 1) 생성자를 통해 의존성 객체를 주입받는 방법
//	private MemberService memberService;
//	public MemberController(MemberService memberService) {
//		System.out.println("MemberController 생성자 호출됨!");
//		this.memberService = memberService;
//	}
	// ---------------------
	// 2) Setter 메서드를 통해 의존성 객체를 주입받는 방법(Setter 메서드에 @Autowired 어노테이션 필수)
//	private MemberService memberService;
//	
//	@Autowired
//	public void setMemberService(MemberService memberService) {
//		this.memberService = memberService;
//	}
	// ---------------------
	// 3) 멤버변수 선언 시 의존성 객체를 주입받는 방법(멤버변수에 @Autowired 어노테이션 필수)
//	@Autowired
//	private MemberService memberService;
	// ---------------------
	// 4) final 필드 형태로 선언한 후 생성자를 통해 객체 주입
	// => 첫번째방법) 생성자를 직접 정의해서 MemberService 타입 파라미터를 선언
	// => 두번째방법) 생성자를 직접 정의하는 대신 Lombok 의 @RequiredArgsConstructor 어노테이션을 지정
	private final MemberService memberService;
//	public MemberController(MemberService memberService) {
//		System.out.println("MemberController 생성자 호출됨!");
//		this.memberService = memberService;
//	}
	// => 주의! @RequiredArgsConstructor 어노테이션 사용 시 생성자를 직접 정의하면 안됨! (= 생성자 중복됨)
	// ====================================================================================

	@GetMapping("/memberList")
//	public List<Member> showMemberList(MemberService memberService) { // 메서드 호출 시점에 스프링 빈으로 등록된 MemberService 인스턴스 주입됨
	public List<Member> showMemberList() {
		// 개발자가 객체가 필요한 시점에 직접 인스턴스를 생성하여 사용하는 경우
//		MemberService memberService = new MemberService();
//		memberService.test();
//		return null;
		// ==========================================================================
		// 스프링 DI 에 의해 MemberService 객체를 자동 주입받은 상태
		// ---------------------------------------------------------
		// MemberService - getMemberList() 메서드 호출하여 비즈니스 로직 처리 요청
		// => 파라미터 : 없음    리턴타입 : List<Member>(memberList)
		List<Member> memberList = memberService.getMemberList();
		
		// @ResponseBody 또는 @RestController 가 적용된 메서드에서 특정 객체를 리턴할 경우
		// 스프링부트에 Jackson 라이브러리(spring-boot-starter-web 라이브러리 추가 시 함께 로딩됨)가 기본 JSON 처리 라이브러리로 동작하므로
		// 매핑 메서드에서 객체 리턴 시 자동으로 JSON 형식의 문자열로 변환되어 응답 데이터로 전송함
		// => 리턴할 객체를 대상으로 Jackson 라이브러리의 메세지 컨버터에 의해 JSON 문자열 형태로 직렬화를 수행한다!
		return memberList; // List<Member> 타입 객체가 JSON 형식 문자열로 자동 변환되어 리턴
	}
	
	
	// /members/memberInfo/xxx 에 대한 요청 매핑
	// => xxx 부분을 경로 변수 Long 타입 id 로 바인딩
	@GetMapping("/memberInfo/{id}")
	public Member showMemberInfo(@PathVariable("id") Long id) {
//		System.out.println("id : " + id);
		
		// MemberService - getMember() 메서드 호출하여 비즈니스 로직 처리 요청
		// => 파라미터 : id   리턴타입 : Member
		Member member = memberService.getMember(id);
		
		return member;
	}
	
	// 이름으로 조회 요청
	@GetMapping("/memberInfoByName")
	public Member memberInfoByName(@RequestParam("name") String name) {
		System.out.println("name : " + name);
		
		// MemberService - getMemberByName() 메서드 호출하여 비즈니스 로직 처리 요청
		// => 파라미터 : name   리턴타입 : Member
		Member member = memberService.getMemberByName(name);
		
		return member;
	}
	
	// 이름으로 조회 요청
	@GetMapping("/searchMember")
	public Member searchMember(@RequestParam Map<String, String> params) {
		System.out.println("id : " + params.get("id"));
		System.out.println("name : " + params.get("name"));
		
		// MemberService - searchMember() 메서드 호출하여 비즈니스 로직 처리 요청
		// => 파라미터 : Map 객체   리턴타입 : Member
		Member member = memberService.searchMember(params);
		
		return member;
	}
	
	// ==========================================================================
	// 이름 등록 요청
	@PostMapping("/regist")
	public Member registMember(@RequestParam("name") String name) {
		System.out.println("name : " + name);
		
		// MemberService - registMember() 메서드 호출하여 비즈니스 로직 처리 요청
		// => 파라미터 : 이름   리턴타입 : Member(member)
		Member member = memberService.registMember(name);
		// => INSERT 작업 성공 시 성공한 엔티티 객체가 리턴됨
		return member;
	}
	
	// 번호로 삭제 요청
	@PostMapping("/removeById")
	public String removeMemberById(@RequestParam("id") Long id) {
		System.out.println("id : " + id);
		
		// MemberService - removeMemberById() 메서드 호출하여 비즈니스 로직 처리 요청
		// => 파라미터 : 번호   리턴타입 : void
		memberService.removeMemberById(id);
		// NoSuchElementException 및 다른 예외가 발생하지 않았을 경우 정상 삭제 완료이므로 return 문 정상 실행됨 
		return id + " 번 회원 삭제 완료!";
	}
	
	// 이름으로 삭제 요청
	@PostMapping("/removeByName")
	public String removeMemberByName(@RequestParam("name") String name) {
		System.out.println("name : " + name);
		
		// MemberService - removeMemberByName() 메서드 호출하여 비즈니스 로직 처리 요청
		// => 파라미터 : 번호   리턴타입 : void
		memberService.removeMemberByName(name);
		// NoSuchElementException 및 다른 예외가 발생하지 않았을 경우 정상 삭제 완료이므로 return 문 정상 실행됨 
		return name + " 회원 삭제 완료!";
	}
	
	// 번호에 해당하는 이름 수정 요청
	@PostMapping("/modify")
	public Member modifyMember(@RequestParam("id") Long id, @RequestParam("name") String name) {
		System.out.println("id : " + id);
		System.out.println("name : " + name);
		
		// MemberService - modifyMember() 메서드 호출하여 비즈니스 로직 처리 요청
		// => 파라미터 : 번호, 이름   리턴타입 : Member(member)
		Member member = memberService.modifyMember(id, name);
		// => 수정 성공 시 수정된 Member 엔티티가 리턴됨
		return member;
	}
	
	
}











