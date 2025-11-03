package com.itwillbs.test5.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itwillbs.test5.common.entity.CommonCode;
import com.itwillbs.test5.common.service.CommonCodeService;
import com.itwillbs.test5.member.dto.MemberDTO;
import com.itwillbs.test5.member.entity.Member;
import com.itwillbs.test5.member.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MemberService {
	private final MemberRepository memberRepository;
	private final CommonCodeService commonCodeService;
	
	public MemberService(MemberRepository memberRepository, CommonCodeService commonCodeService) {
		this.memberRepository = memberRepository;
		this.commonCodeService = commonCodeService;
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
		log.info(">>>>>>>>>>>>>> 암호화 된 비밀번호 : " + memberDTO.getPasswd());
		
		// MemberDTO -> Member 엔티티로 변환
		Member member = memberDTO.toEntity();
		
		// -----------------------------------------
		// 회원 등록 시 사용자 권한을 일반 사용자 권한으로 등록하기 위해 공통코드 테이블의 일반 사용자 값을 조회하여 사용
		// 1) CommonCodeService - getMemberRole() 메서드 호출하여 사용자 권한 조회
		CommonCode commonCode = commonCodeService.getMemberRole("ROLE_USER");
		log.info(">>>>>>>>>>>>>> 사용자 권한 코드 : " + commonCode.getGroupCode() + ", " + commonCode.getCommonCode());
		
		// 2) Member 엔티티에 공통코드(사용자 계정 유형) 추가
		member.addMemberRole(commonCode);
		log.info(">>>>>>>>>>>>>> Member 엔티티 : " + member);
		// -----------------------------------------
		// MemberRepository - save() 메서드 호출하여 엔티티 등록 요청
		Member resultMember = memberRepository.save(member);
		
		if(resultMember == null) {
			throw new IllegalStateException("회원 등록 실패!");
		}
	}
	
	
	
}











