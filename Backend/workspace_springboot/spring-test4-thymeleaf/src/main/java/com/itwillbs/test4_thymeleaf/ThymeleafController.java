package com.itwillbs.test4_thymeleaf;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwillbs.test4_thymeleaf.item.ItemDTO;

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
	
	
}














