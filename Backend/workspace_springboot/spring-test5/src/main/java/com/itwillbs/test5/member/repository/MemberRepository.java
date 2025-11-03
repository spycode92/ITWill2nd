package com.itwillbs.test5.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.itwillbs.test5.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	// 이메일로 사용자 조회
	Optional<Member> findByEmail(String email);
	
	/*
	 * [ JPQL(Java Persistence Query Language) ]
	 * - JPA 에서 제공하는 객체지향 쿼리 언어
	 * - 기존 SQL 문 처럼 DB 테이블을 대상으로 하는 것이 아니라, 엔티티 객체 및 필드를 대상으로 쿼리 작성
	 * - JPA 가 JPQL 을 해석하여 실제 DB 의 SQL 문장으로 변환하여 실행
	 * - 주의사항! 기본적인 문법 구조는 SQL 과 거의 동일하나, 테이블명 대신 엔티티명, 테이블 컬럼명 대신 엔티티 필드명 사용
	 */
	// email 을 기준으로 Member 엔티티와 함께 사용자 권한을 관리하는 MemberRole 엔티티도 함께 조회될 수 있도록 JPQL 작성
	@Query("SELECT m FROM Member m" // FROM 절 뒤에는 테이블명 member 가 아닌 엔티티명 Member 로 지정
			+ " JOIN FETCH m.roles r" // Member 엔티티와 roles 컬렉션에 해당하는 MemberRole 엔티티를 즉시 로딩(= EAGER)하여 가져옴(즉, JOIN FETCH 는 연관된 엔티티까지 한번에 SELECT)
			+ " JOIN FETCH r.role" // 중간 엔티티에 해당하는 MemberRole 엔티티를 통해 CommonCode 엔티티를 다시 JOIN 해서 가져오기
			+ " WHERE m.email = :email") // Member 엔티티의 email값(m.email)이 메서드 파라미터로 전달된 email(:email)과 같은 조건 설정
	Optional<Member> findByEmailWithRoles(@Param("email") String email); // email 파라미터를 JPQL 에서 접근 가능하도록 @Param 어노테이션 지정(패키지 주의!)

}




















