package com.itwillbs.test4_thymeleaf;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwillbs.test4_thymeleaf.item.ItemDTO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/thymeleaf")
@Log4j2 // 클래스 내의 메서드에서 로그 메세지 출력문으로 사용할 log.xxx() 메서드를 제공하는 어노테이션
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
//		MemberDTO memberDTO = new MemberDTO();
//		memberDTO.setId(1L);
//		memberDTO.setName("홍길동");
//		memberDTO.setUserId("hong");
//		memberDTO.setPasswd("1234");
//		memberDTO.setGrade(1);
//		memberDTO.setRegTime(LocalDateTime.now());
		
		// 2) 파라미터 생성자 활용
//		MemberDTO memberDTO = new MemberDTO(1L, "홍길동", "hong", "1234", 1, LocalDateTime.now());
//		ItemDTO itemDTO = new ItemDTO(2L, "테스트상품", "테스트 상세설명", 100, 2, LocalDateTime.now());
		
		// 3) 파라미터 생성자에 대한 빌더 패턴 활용
		//    => Lombok 에서 제공하는 @Builder 어노테이션읊 파라미터 생성자에 선언 시
		//       해당 객체 생성 시 클래스명.builder() 메서드 호출하고 이어서 필드값 초기화하는 메서드를 연쇄적으로 호출하여 객체 초기화
		//       마지막에 build() 메서드 호출하면 해당 객체가 생성되어 리턴됨
		MemberDTO memberDTO = MemberDTO.builder()
										.id(3L)
										.name("이순신")
										.userId("leess")
										.passwd("1234")
										.regTime(LocalDateTime.now())
										.grade(2)
										.build();
		
		
		ItemDTO itemDTO = ItemDTO.builder()
									.id(1L)
									.itemNm("상품명")
									.itemDetail("상세설명")
									.price(500)
									.stockQty(3)
									.regTime(LocalDateTime.now())
									.build();
		
		// Model 객체에 "memberDTO" 라는 속성명으로 MemberDTO 객체 저장
		model.addAttribute("memberDTO", memberDTO);
		// Model 객체에 "itemDTO" 라는 속성명으로 ItemDTO 객체 저장
		model.addAttribute("itemDTO", itemDTO);
		
		return "/thymeleaf_test2";
	}
	
	
	// --------------------------------------------------------
	// 다중 객체 리스트를 Model 객체에 저장하여 타임리프 템플릿 페이지에서 출력
	@GetMapping("test3")
	public String test3(Model model) {
		List<ItemDTO> itemList = new ArrayList<>();
		
		for(int i = 1; i <= 10; i++) {
			ItemDTO itemDTO = ItemDTO.builder()
					.id((long)i)
					.itemNm("상품명" + i)
					.itemDetail("상세설명" + i)
					.price(10000 * i)
					.stockQty(new SecureRandom().nextInt(10 * i))
					.regTime(LocalDateTime.now())
					.build();
			
			itemList.add(itemDTO);
		}
		
		model.addAttribute("itemList", itemList);
		
		return "/thymeleaf_test3";
	}
	
	// --------------------------------------------------------
	// 조건문 연습
	@GetMapping("test4")
	public String test4(Model model) {
		List<ItemDTO> itemList = new ArrayList<>();
		
		for(int i = 1; i <= 10; i++) {
			ItemDTO itemDTO = ItemDTO.builder()
					.id((long)i)
					.itemNm("상품명" + i)
					.itemDetail("상세설명" + i)
					.price(10000 * i)
					.stockQty(new SecureRandom().nextInt(10 * i))
					.regTime(LocalDateTime.now())
					.build();
			
			itemList.add(itemDTO);
		}
		
		model.addAttribute("itemList", itemList);
		
		return "/thymeleaf_test4";
	}
	
	// --------------------------------------------------------
	// 하이퍼링크 연습
	@GetMapping("test5")
	public String test5(Model model) {
		model.addAttribute("data", "테스트 데이터");
		model.addAttribute("nextURL", "test5-2");
		return "/thymeleaf_test5";
	}
	
	@GetMapping("test5-2")
	public String test5_2(@RequestParam Map<String, String> params, Model model, HttpSession session) {
//		System.out.println("param1 : " + params.get("param1"));
		// => System.out.println() 등의 출력문 대신 로그 메세지로 출력하는 것이 성능 향상에 도움이 되며, 로그 메세지를 별도로 관리도 가능
		// => 로그 출력 시 Lombok 에서 제공하는 @Log4j2 어노테이션을 지정하고, log.xxx() 메서드로 로그메세지 출력
		//    log.xxx() 메서드의 xxx 에 해당하는 메서드명은 로그 심각도와 동일한 이름의 메서드를 지정
		//    로그 심각도 : TRACE < DEBUG < INFO < WARN < ERROR < FATAL 순(우측으로 갈 수록 심각도 높음)
		log.info("★★★★★★★★★★★★★ params : " + params);
		log.info("★★★★★★★★★★★★★ param1 : " + params.get("param1"));
		log.info("★★★★★★★★★★★★★ param2 : " + params.get("param2"));
		log.info("★★★★★★★★★★★★★ param3 : " + params.get("param3"));
		
		model.addAttribute("params", params);
		// -----------------------------------------------------------
		// 세션 객체에 id 라는 속성명으로 "admin" 문자열 저장
		session.setAttribute("id", "admin");
		
		return "/thymeleaf_test5-2";
	}
	
	// ==========================================
	// 타임리프 레이아웃
	@GetMapping("test6")
	public String test6() {
		return "/thymeleaf_test6_layout";
	}
	
	@GetMapping("test6-2")
	public String test6_2() {
		return "/thymeleaf_test6-2";
	}
	
	@GetMapping("test6-3")
	public String test6_3() {
		return "/thymeleaf_test6-3";
	}
	
	// Thymeleaf Layout Dialect 사용 시 실제 컨텐츠 페이지로 포워딩(레이아웃 페이지가 아님!)
	@GetMapping("test7")
	public String test7() {
		return "/thymeleaf_test7";
	}
	
}














