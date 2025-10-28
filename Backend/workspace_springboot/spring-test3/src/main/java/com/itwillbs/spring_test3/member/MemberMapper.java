package com.itwillbs.spring_test3.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	// member 테이블 목록을 조회하여 복수개의 Member 엔티티를 List 로 묶어 리턴 
	List<Member> selectMemberList();

	// Member 엔티티에 저장된 데이터를 member 테이블에 저장
//	Member insertMember(Member member);
	void insertMember(Member member);

}
