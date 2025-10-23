package com.itwillbs.spring_test3.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Spring Data JPA 가 관리하는 엔티티 클래스(DB 테이블에 대응하는 클래스) 정의
@Entity // 현재 클래스를 엔티티로 선언하는 어노테이션
//@Entity(name = "MyMember") // 엔티티 이름 지정 가능
// => 엔티티 이름은 기본적으로 클래스명을 사용하지만 다른 이름으로 변경 가능
//    @Entity(name = "MyMember") 형태로 이름 변경 가능 => 주의! 테이블명과는 다르다!!!!
//@Table // 매핑할 테이블을 지정하는 어노테이션(생략 가능)
//@Table(name = "my_member") // 테이블명 지정 가능
// => 대상 테이블 이름은 기본적으로 클래스명 사용하지만 다른 이름으로 변경 가능
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자에 대한 접근제한자 설정 가능
@AllArgsConstructor
@ToString
public class Member { // 클래스명 Member 와 동일한 이름의 테이블이 DB 에 자동으로 생성됨
	@Id // 현재 필드(id)를 기본키(PK)로 설정(@Id 어노테이션과 필드명(멤버변수명) id 는 이름이 무관함 = 달라도 됨)
	@Column(name = "id", updatable = false) // 테이블 컬럼 설정
//	@Column(name = "member_no", updatable = false) // 테이블 컬럼명을 member_no 로 설정
	// name = "id" 속성 : 테이블 내의 컬럼명을 id 로 설정(생략 시 필드명과 동일한 이름의 컬럼이 생성됨)
	// updatable = false 속성 : 해당 컬럼은 데이터가 저장되면 변경 불가능한 컬럼으로 지정(기본값은 true 로 변경 가능한 컬럼으로 정의됨)
	@GeneratedValue(strategy = GenerationType.AUTO) // @Id 에 해당하는 컬럼값 자동 생성 방법을 AUTO 로 지정(JPA 정책에 따라 자동 생성 방법을 위임)
	// => stratege 속성값 - IDENTITY : MySQL 의 AUTO_INCREMENT 방식 사용, SEQUENCE : ORACLE 의 시퀀스 생성 방식 사용
	private Long id; // h2 데이터베이스 기준 BIGINT 타입으로 id 컬럼 생성
//	private Long memberNo;

	@Column(length = 16, nullable = false)
	// 필드명과 동일한 이름으로 컬럼명 생성됨
	// 컬럼 길이가 16 이므로 String 타입에 대한 VARCHAR(16) 등의 형식으로 선언(length 속성 생략 시 String 타입의 기본은 VARCHAR(255))
	// nullable = false 속성 설정 시 해당 컬럼은 NOT NULL 제약조건 설정됨
	private String name;
}



















