package com.itwillbs.test5.common.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommonCodeDTO {
	private Long id; // 공통코드번호
	private String groupCode; // 상위공통코드(= 공통코드 그룹)
	private String commonCode; // 공통코드
	private String commonCodeName; // 공통코드명(코드를 한국어로 표기할 컬럼)
	private String description; // 공통코드 상세설명
}
