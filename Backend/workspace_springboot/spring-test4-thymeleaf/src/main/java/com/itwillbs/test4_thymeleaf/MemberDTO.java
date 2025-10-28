package com.itwillbs.test4_thymeleaf;

import java.time.LocalDateTime;

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
public class MemberDTO {
	private Long id; // 회원번호
	private String name; // 회원이름
	private String userId; // 아이디
	private String passwd; // 패스워드
	private int grade; // 회원등급
	private LocalDateTime regTime; // 회원가입일시
}






















