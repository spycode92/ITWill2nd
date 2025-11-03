package com.itwillbs.test5.item.dto;

import java.time.LocalDateTime;

import com.itwillbs.test5.item.constant.ItemCategory;
import com.itwillbs.test5.item.constant.ItemSellStatus;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 상품 정보를 관리할 DTO 클래스
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemDTO {
	private Long id; // 상품코드
	
	@NotBlank(message = "상품명은 필수 입력값입니다!") // 공백만 있거나 길이가 0인 문자열, null 값을 허용하지 않음
	@Pattern(regexp = "^[A-Za-z가-힣0-9]{1,100}$", message = "상품명은 영문자, 숫자, 한글 1 ~ 100글자만 사용 가능!")
	private String itemNm; // 상품명
	
	@NotEmpty(message = "상품상세설명은 필수 입력값입니다!") // 길이가 0인 문자열, null 값을 허용하지 않음
	private String itemDetail; // 상품상세설명
	
	// 숫자 데이터는 사용자가 입력하지 않은 상태와 직접 0을 입력한 상태를 구별하기 위해 int 대신 Integer 등의 참조타입 사용
	// => 숫자 데이터에 대한 체크는 @NotEmpty 가 아닌 @NotNull 로 판별
	@NotNull(message = "가격은 필수 입력값입니다!")
	@PositiveOrZero(message = "가격은 최소 0원 이상 입력 필수!")
	private Integer price; // 가격
	
	@NotNull(message = "수량은 필수 입력값입니다!")
	@Positive(message = "수량은 최소 1개 이상 입력 필수!")
	@Max(value = 99999, message = "최대 수량은 99999개를 초과할 수 없음!")
	private Integer stockQty; // 재고수량
	
	// enum 타입 검증 시 널스트링("") 전달되면 null 값으로 취급됨
	@NotNull(message = "상품카테고리는 필수 선택입니다!")
	private ItemCategory category; // 상품카테고리(enum)
	
	// enum 타입 검증 시 널스트링("") 전달되면 null 값으로 취급됨
	@NotNull(message = "상품카테고리는 필수 선택입니다!")
	private ItemSellStatus sellStatus; // 상품판매상태(enum)
	
	private LocalDateTime regTime; // 상품등록일시
	private LocalDateTime updateTime; // 상품정보수정일시
}























