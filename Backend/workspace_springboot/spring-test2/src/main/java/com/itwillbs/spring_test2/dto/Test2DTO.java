package com.itwillbs.spring_test2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Test2DTO {
	private Long id;
	private int page = 1; // DTO 클래스에서 기본값 설정
	private int limit;
}









