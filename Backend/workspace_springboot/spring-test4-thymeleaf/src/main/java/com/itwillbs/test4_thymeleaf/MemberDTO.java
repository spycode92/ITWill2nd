package com.itwillbs.test4_thymeleaf;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class MemberDTO {
	private Long id; // 회원번호
	private String name; // 회원이름
	private String userId; // 아이디
	private String passwd; // 패스워드
	private int grade; // 회원등급
	private LocalDateTime regTime; // 회원가입일시
	
	// ------------------------------------------
	// 파라미터 생성자 정의
	// 빌더 패턴 형식으로 객체 생성이 가능하도록 Lombok 의 @Builder 어노테이션을 생성자 상단에 지정
	@Builder
	public MemberDTO(Long id, String name, String userId, String passwd, int grade, LocalDateTime regTime) {
		super();
		this.id = id;
		this.name = name;
		this.userId = userId;
		this.passwd = passwd;
		this.grade = grade;
		this.regTime = regTime;
	}
	
}






















