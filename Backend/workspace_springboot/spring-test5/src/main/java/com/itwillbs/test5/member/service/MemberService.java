package com.itwillbs.test5.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itwillbs.test5.member.dto.MemberDTO;
import com.itwillbs.test5.member.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class MemberService {
	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		super();
		this.memberRepository = memberRepository;
	}
	// =======================================================
	// 회원 등록 요청
	public void registMember(@Valid MemberDTO memberDTO) {
		// 이메일로 사용자 조회 후 조회 결과가 있을 경우 예외 발생시키기! (주의! orElseThrow() 가 아닌 ifPresent() 메서드 사용)
		memberRepository.findByEmail(memberDTO.getEmail())
						.ifPresent(m -> new EntityNotFoundException("이미 가입된 회원입니다!"));
		
		// 입력받은 비밀번호 단방향 암호화(평문 -> 암호문) 처리
		// => BCryptPasswordEncoder 객체의 encode() 메서드 활용
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		memberDTO.setPasswd(passwordEncoder.encode(memberDTO.getPasswd()));
	}
	
	
	
}











