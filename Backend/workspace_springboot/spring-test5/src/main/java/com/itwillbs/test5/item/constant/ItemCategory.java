package com.itwillbs.test5.item.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemCategory {
	// enum 값은 대문자로 지정
//	ELECTRONICS, CLOTHING, FOODS, BOOKS, KIDS
	// ---------------------------------------
	// enum 값을 상수명(고유문자열) => 실제사용할값("화면에표시할값") 형태로 생성
	// => 단, 생성자를 통해 문자열을 전달받아 초기화할 수 있도록 정의해야함(The constructor ItemCategory(String) is undefined)
	ELECTRONICS("전자제품"), 
	CLOTHING("의류"), 
	FOODS("식품"), 
	BOOKS("도서"), 
	KIDS("아동");
	
	// enum 상수값에 연결된 고유문자열을 관리할 변수(필드) 및 메서드 정의
	// 1) 화면에 표시될 값을 저장할 필드 선언
	private final String label;

	// 2) 생성자 정의(label 필드값을 파라미터로 전달받아 초기화)
	// => enum 의 각 상수가 할당될 때 내부적으로 자동으로 생성자가 호출되고, 이 때 고유문자열값이 자동으로 label 필드에 초기화
	// => 외부에서 직접 호출 불가능하도록 private 접근제한자로 선언
	// => 단, Lombok 의 @RequiredArgsConstructor 어노테이션으로 대체 가능(final 필드에 대한 생성자 정의됨)
//	private ItemCategory(String label) {
//		this.label = label;
//	}
	
	// 3) Getter 정의(label 값 리턴) => @Getter 어노테이션으로 대체
//	public String getLabel() {
//		return label;
//	}
	
	// ---------------------------------
	// 4) getCode() 메서드 정의
	// => 실제 enum 상수값을 리턴하는 용도의 메서드(고유문자열(레이블)이 아닌 상수값)
	// => 별도의 code 필드 선언 불필요
	public String getCode() {
		// 현재 enum 객체의 name() 메서드 호출 시 자동으로 enum 상수값 리턴됨
		return this.name();
	}
	
	
}



















