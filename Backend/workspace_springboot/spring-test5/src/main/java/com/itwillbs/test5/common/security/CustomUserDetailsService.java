package com.itwillbs.test5.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.itwillbs.test5.member.entity.Member;
import com.itwillbs.test5.member.repository.MemberRepository;

import lombok.extern.log4j.Log4j2;

// 스프링 시큐리티에서 인증 처리(로그인 등)를 수행하는 서비스 클래스 정의
// => UserDetailsService(스프링 시큐리티에서 인증 관련 사용자 정보(UserDetails 객체로 관리됨)를 불러오는 핵심 인터페이스)를 상속받아 정의
// => UserDetailsService 를 구현한 구현체는 인증에 사용되는 사용자 정보를 DB 로 부터 조회 가능
@Service
@Log4j2
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;
	
	public CustomUserDetailsService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	// ==================================================================
	// 스프링 시큐리티에서 사용자 정보 조회에 사용될 loadUserByUsername() 메서드 오버라이딩
	// => 파라미터로 전달받는 문자열(username)은 실제 조회에 사용될 유니크 한 정보여야한다(ex. 사용자 아이디, 이메일, 전화번호 등)
	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // 파라미터명 변경 가능
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // 이메일을 사용자명으로 사용
		log.info("◆◆◆◆◆◆◆◆◆◆◆◆◆ 사용자 정보 조회용 username : " + email);
		
		// email 을 사용하여 Member 엔티티 조회
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email + " : 사용자 조회 실패!"));
		log.info("◆◆◆◆◆◆◆◆◆◆◆◆◆ 사용자 정보  : " + member);
		
		return null;
	}

}

















