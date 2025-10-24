package com.itwillbs.spring_test3.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	/*
	 * SELECT * FROM xxx 문장을 실행하는 쿼리 메서드는 별도로 정의 불필요
	 * => JpaRepository 에서 제공하는 findAll() 메서드 활용하면 된다!
	 * 
	 * SELECT * FROM xxx WHERE id = ? 문장을 실행할 경우 findById() 메서드 활용
	 * ------------------------------------------------------------------------
	 * SELECT * FROM member WHERE name = ? 문장 실행을 위한 쿼리 메서드를 추가로 정의
	 * 규칙1) 특정 레코드를 탐색하는 SELECT 구문 실행용 메서드이므로 findByXXX() 이며, 
	 *        여기서 XXX 부분은 탐색할 엔티티의 필드명 지정(주의! 테이블 컬럼명이 아님!)하고, 파라미터로 검색할 데이터 전달
	 * 규칙2) 검색 결과가 하나의 row(레코드) 일 경우 리턴타입을 Optional<T> 타입으로 지정(T = 엔티티 타입)하고
	 *        검색 결과가 복수개의 row(레코드) 일 경우 리턴타입을 List<T> 타입으로 지정
	 *        ex) name 필드값이 중복되지 않을 경우 Optional<Member>, 중복될 경우 List<Member>
	 */
	Optional<Member> findByName(String name);

	Optional<Member> findByIdAndName(Long id, String name);
	
}
