package com.itwillbs.test5.member.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import com.itwillbs.test5.item.dto.ItemDTO;
import com.itwillbs.test5.member.entity.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
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
	// ===================================================================
	// Item 엔티티 <-> ItemDTO 간의 변환 처리
	// 1) 파라미터 생성자 정의(빌더패턴 생략 가능)
//	@Builder
//	public MemberDTO(Long id, String email, String passwd, String name, String postCode, String address1, String address2, LocalDate regDate) {
//		this.id = id;
//		this.email = email;
//		this.passwd = passwd;
//		this.name = name;
//		this.postCode = postCode;
//		this.address1 = address1;
//		this.address2 = address2;
//		this.regDate = regDate;
//	}
	
	// 2) Entity -> DTO 로 변환하는 fromEntity() 메서드 정의
	// => 파라미터 : 엔티티 객체   리턴타입 : DTO 타입
	// => 단, DTO 객체가 없는 상태에서 Member 엔티티로부터 객체를 생성해야하므로 MemberDTO 내에서 static 메서드로 정의
	//    (또는 Member 엔티티 클래스에서 정의해도 된다!)
//	public static MemberDTO fromEntity(Member member) {
//		return MemberDTO.builder()
//				.id(member.getId())
//				.name(member.getName())
//				.email(member.getEmail())
//				.passwd(member.getPasswd())
//				.postCode(member.getPostCode())
//				.address1(member.getAddress1())
//				.address2(member.getAddress2())
//				.regDate(member.getRegDate())
//				.build();
//	}
	
	// 3) DTO -> Entity 로 변환하는 toEntity() 메서드 정의(생략)
//	public Member toEntity() {
//		return Item.builder()
//		// 생략
//	}
	// -----------------------------------------
	// DTO <-> Entity 변환 메서드를 개발자가 직접 구현하지 않고 ModelMapper 라이브러리를 활용하여 간편하게 구현 가능
	// 단, 기본적으로 두 클래스간의 필드명이 동일한 필드끼리만 자동으로 변환 처리됨
	private static ModelMapper modelMapper = new ModelMapper();
	
	// ModelMapper 객체의 map() 메서드를 활용하여 객체 변환 수행
	// 1) MemberDTO -> Member(엔티티) 타입으로 변환하는 toEntity() 메서드 정의
	public Member toEntity() {
		// 첫번째 파라미터 : 변환 전 원본 객체
		// 두번째 파라미터 : 변환 할 대상 클래스(.class 포함)
		return modelMapper.map(this, Member.class);
	}
	
	// 2) Entity -> DTO 로 변환하는 fromEntity() 메서드 정의
	public static MemberDTO fromEntity(Member member) {
		return modelMapper.map(member, MemberDTO.class);
	}
	
}














