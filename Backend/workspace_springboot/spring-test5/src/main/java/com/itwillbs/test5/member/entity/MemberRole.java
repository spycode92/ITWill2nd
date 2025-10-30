package com.itwillbs.test5.member.entity;

import com.itwillbs.test5.common.entity.CommonCode;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

// 회원 권한을 관리하는 엔티티(= 공통코드 테이블의 상위공통코드명 "MEMBER_ROLE" 에 대한 공통코드 관리)
// => Member 엔티티와 CommonCode 엔티티는 다대다(N:M) 관계이므로
//    다대다 관계를 풀어서 중간 완충 역할을 수행할 MemberRole 테이블을 가운데에 끼워넣어 간단한 구조로 풀기
//    Member(1) : MemberRole(N) 관계
//    CommonCode(1) : MemberRole(N) 관계
// ----------------------------------------------
@Entity
@Table(name = "member_role") // name 속성 생략 가능
@Getter
@Setter
public class MemberRole {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 사용자(MEMBERS) 테이블과 사용자 테이블과의 연관관계 설정
	@ManyToOne(fetch = FetchType.LAZY) // 지연로딩
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	
	// 공통코드(COMMON_CODE) 테이블과 사용자 테이블과의 연관관계 설정
	@ManyToOne(fetch = FetchType.LAZY) // 지연로딩
	@JoinColumn(name = "member_role_id", nullable = false)
	private CommonCode role;
	
	
}













