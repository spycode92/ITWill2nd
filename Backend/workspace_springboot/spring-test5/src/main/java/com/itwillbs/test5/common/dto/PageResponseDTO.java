package com.itwillbs.test5.common.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 스프링 페이징 객체인 Page 타입을 포함하여 응답 데이터를 생성할 DTO 정의
// => Page 타입 객체를 직접 응답하는 대신 DTO 타입으로 변환하여 안전한 응답 처리를 위함
@Getter
@Setter
@NoArgsConstructor
public class PageResponseDTO<T> {
	private List<T> content; // 페이징으로 관리할 목록 객체를 다양한 타입으로 다루기 위해 List<T> 타입으로 선언
	private int page;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean first;
	private boolean last;
	
	// 생성자를 통해 Page<T> 타입 객체를 전달받아 각 필드 초기화하도록 정의
	public PageResponseDTO(Page<T> page) { // Page<ItemDTO>, Page<MemberDTO> 등 다양하게 전달받도록 제네릭타입 활용
		this.content = page.getContent(); // 실제 데이터 목록(List<T>)
		this.page = page.getNumber(); // 현재 페이지
		this.pageSize = page.getSize(); // 페이지 크기
		this.totalElements = page.getTotalElements(); // 전체 데이터 목록 갯수
		this.totalPages = page.getTotalPages(); // 전체 페이지 수
		this.first = page.isFirst(); // 첫 페이지 여부
		this.last = page.isLast(); // 끝 페이지 여부
	}
	
}







