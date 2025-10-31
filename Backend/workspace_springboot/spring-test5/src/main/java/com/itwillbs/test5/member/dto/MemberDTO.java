package com.itwillbs.test5.member.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 엔티티를 직접 공유하는 대신 별도의 DTO 클래스를 통해 엔티티 데이터를 외부로 제공
@Getter
@Setter
@ToString
public class MemberDTO {
	// Java Bean Validation 기능을 활용하여 입력값 검증 수행 => Validation 의존성(spring-boot-starter-validation) 추가 필요
	// => 다양한 어노테이션을 조합하여 복합 검증 조건 설정. 컨트롤러에서 바인딩 시점에 체크 가능
	// => 기본 문법 : @XXX(message = "오류 발생 시 표시할 메세지") 
	//    (필요에 따라 value 속성을 통한 값 지정도 가능)
	// @NotEmpty : 문자열이 null 이거나 길이가 0인 문자열(널스트링)을 허용하지 않음
	// @NotBlank : 문자열이 null 이거나 길이가 0인 문자열(널스트링) 또는 공백만 포함한 문자열을 허용하지 않음
	// @NotNull : 객체값 null 을 허용하지 않음
	// @Positive : 0 제외 양수만 허용(x > 0)
	// @PositiveOrZero : 0 이상의 양수만 허용(x >= 0)
	// @Negative : 0 제외 음수만 허용(x < 0)
	// @NegativeOrZero : 0 이하의 음수만 허용(x <= 0)
	// @Min(value) : 최소값 제한(x >= value)
	// @Max(value) : 최대값 제한(x <= value)
	// @Pattern(regexp = "정규표현식패턴문자열") : 특정 정규표현식에 매칭되는 문자열 탐색 후 일치하지 않을 경우 허용하지 않음
	// ---------------------------------------------------------------------------------------------------------------------
	private Long id;
	
	@NotBlank(message = "이메일은 필수 입력값입니다!")
	@Email(message = "이메일 형식에 맞게 입력해 주세요!")
	private String email;
	
	@NotBlank(message = "패스워드는 필수 입력값입니다!")
	@Length(min = 4, max = 16, message = "패스워드는 4 ~ 16자리 필수!")
	// @Pattern(regexp = "") 속성을 통해 상세 정규표현식 설정도 가능
	private String passwd;
	
	@NotBlank(message = "이름은 필수 입력값입니다!")
	@Length(min = 2, max = 20, message = "이름은 2 ~ 20자리 필수!")
	private String name;
	
	@NotBlank(message = "우편번호는 필수 입력값입니다!")
	private String postCode; // 우편번호
	@NotBlank(message = "기본 주소는 필수 입력값입니다!")
	private String address1; // 기본 주소
	private String address2; // 상세 주소(검증 조건 없음)
	
	private LocalDate regDate; // 가입일자(엔티티 생성 시 자동 등록)
}














