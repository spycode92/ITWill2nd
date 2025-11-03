package com.itwillbs.test5.member.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.itwillbs.test5.common.entity.CommonCode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "members")
@Getter
@Setter
@ToString
// @CreateDate 등의 어노테이션을 활용하여 날짜 정보 등을 자동으로 등록하기 위한 JPA 감사(Auditing) 기능 사용 시
// 감사 기능이 동작할 클래스(엔티티 또는 공통감사엔티티)에 @EntityListeners 어노테이션 추가하여 감사 대상 클래스로 지정하고
// 스프링 메인 클래스(XXXApplication) 또는 스프링 설정용 클래스에 @EnableJpaAuditing 어노테이션 추가 필요
@EntityListeners(AuditingEntityListener.class)
public class Member {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 사용자 아이디는 이메일을 활용
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false, length = 100) // 단방향 암호화 처리를 위해 패스워드 길이 100글자로 조정
	private String passwd;
	
	@Column(nullable = false, length = 20)
	private String name;
	
	@Column(nullable = false, length = 10)
	private String postCode; // 우편번호
	// => JPA 를 통해 자동으로 테이블 생성 시 Camel-case 표기법으로 선언된 필드명은 테이블의 컬럼명에서는 Snake-case 표기법으로 바뀜
	//    ex) 필드명 postCode => 컬럼명 post_code
	
	@Column(nullable = false, length = 100)
	private String address1; // 기본 주소
	
	@Column(nullable = false, length = 100)
	private String address2; // 상세 주소
	
	// ---------------------------------------------------------------------------------------
	// JPA 에서 특정 날짜 및 시각(또는 다른 형식도 가능) 필드값을 자동으로 생성하려면
	// JPA 감사(Auditing) 기능 사용을 위한 어노테이션 조합을 통해 자동 등록 필드로 지정
	// => 자동 등록될 필드가 위치한 클래스(엔티티)와 스프링 메인 클래스에 어노테이션 설정 추가 필요
	@CreatedDate // 엔티티 최초 생성 시점에 날짜 및 시각이 자동으로 등록되는 필드로 설정
	private LocalDate regDate; // 가입일자(엔티티 생성 시 자동 등록)
	
	// ---------------------------------------------------------------
	// 사용자는 사용자권한(CommonCode) 와 다대다(N:M) 관계
	// => 따라서, 완충 작용을 담당할 MemberRole 엔티티를 정의함
	// => Member 와 MemberRole 은 1:N 관계
	// => @OneToMany 어노테이션으로 1:N 관계에서 1 에 해당하는 엔티티 입장에서의 관계 지정
	// 1) mappedBy = "member" : 현재 엔티티가 연관관계의 주인이 아니므로, 상대방의 필드 지정하여 해당 필드 기준으로 매핑 수행
	// 2) cascade = CascadeType.ALL : 부모 엔티티가 저장/삭제 될 경우 자식 엔티티도 저장/삭제
	// 3) orphanRemoval = true : 부모 엔티티와 연관관계가 끊어진 자식엔티티(고아객체) 자동으로 삭제
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MemberRole> roles = new ArrayList<>(); // 1명의 회원이 여러개의 권한을 가질 수 있으므로 MemberRole 의 List 를 사용
	
	// 사용자 권한을 추가하는 addRole() 메서드 정의
	public void addRole(CommonCode role) {
		// MemberRole 인스턴스 생성 시 생성자 파라미터로 현재 엔티티(Member)와 공통코드 엔티티(CommonCode) 전달
		MemberRole memberRole = new MemberRole(this, role);
		// 사용자 권한 목록 객체(List<MemberRole>)에 1개의 권한이 저장된 MemberRole 엔티티 추가
		roles.add(memberRole);
	}
	// 위의 메서드 인식 불가 버그로 인해 새로운 메서드로 추가(동일한 코드)
	public void addMemberRole(CommonCode role) {
		// MemberRole 인스턴스 생성 시 생성자 파라미터로 현재 엔티티(Member)와 공통코드 엔티티(CommonCode) 전달
		MemberRole memberRole = new MemberRole(this, role);
		// 사용자 권한 목록 객체(List<MemberRole>)에 1개의 권한이 저장된 MemberRole 엔티티 추가
		roles.add(memberRole);
	}
}























