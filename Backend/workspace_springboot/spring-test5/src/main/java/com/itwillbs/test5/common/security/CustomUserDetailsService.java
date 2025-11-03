package com.itwillbs.test5.common.security;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.itwillbs.test5.member.dto.MemberLoginDTO;
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
//		Member member = memberRepository.findByEmail(email)
//				.orElseThrow(() -> new UsernameNotFoundException(email + " : 사용자 조회 실패!"));
		// => Member 엔티티의 List<MemberRole> 객체에 대한 지연 로딩(LAZY)이 설정되어 있는데(MemberRole - Member 타입에 대한 지연로딩 설정됨)
		//    이 Member 엔티티에 접근하려 할 경우 트랜잭션 경계 밖에서 코드들이 실행되므로 지연 로딩 필드에 접근이 불가능하다!
		// => List<MemberRole> 타입 정보(함께 연관되어 있는 CommonCode 엔티티까지)를 실제 조회될 수 있도록 즉시 로딩 해야함(= JOIN FETCH 필요)
		Member member = memberRepository.findByEmailWithRoles(email)
				.orElseThrow(() -> new UsernameNotFoundException(email + " : 사용자 조회 실패!"));
		log.info("◆◆◆◆◆◆◆◆◆◆◆◆◆ 사용자 정보  : " + member);
		
		/*
		 * 조회된 결과를 스프링 시큐리티가 자체적으로 제공하는 Users 객체 형태로 리턴하거나
		 * Users 클래스가 구현한 부모타입인 UserDetails 타입 객체를 리턴해도 됨
		 * 또한, 별도의 DTO 클래스 등을 UserDetails 인터페이스 구현체로 정의했을 경우 해당 객체를 리턴해도 됨
		 */
		// Member 엔티티 -> MemberLoginDTO 객체로 변환하여 리턴하기
		ModelMapper modelMapper = new ModelMapper();
		MemberLoginDTO memberLoginDTO = modelMapper.map(member, MemberLoginDTO.class);
		log.info("◆◆◆◆◆◆◆◆◆◆◆◆◆ memberLoginDTO.getUsername()  : " + memberLoginDTO.getUsername());
		log.info("◆◆◆◆◆◆◆◆◆◆◆◆◆ memberLoginDTO.getPassword()  : " + memberLoginDTO.getPassword());
		log.info("◆◆◆◆◆◆◆◆◆◆◆◆◆ memberLoginDTO.getAuthorities()  : " + memberLoginDTO.getAuthorities());
		
		
		// 사용자 인증 정보가 저장된 객체(UserDetails 타입) 리턴
		return memberLoginDTO; // UserDetails 의 구현체인 MemberLoginDTO 객체 리턴 시 UserDetails 타입으로 업캐스팅 됨
	}

}

















