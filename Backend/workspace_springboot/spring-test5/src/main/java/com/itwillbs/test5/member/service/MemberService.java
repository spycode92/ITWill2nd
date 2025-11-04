package com.itwillbs.test5.member.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itwillbs.test5.common.entity.CommonCode;
import com.itwillbs.test5.common.service.CommonCodeService;
import com.itwillbs.test5.member.dto.MemberDTO;
import com.itwillbs.test5.member.entity.Member;
import com.itwillbs.test5.member.entity.MemberRole;
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
	
	// 사용자 정보 조회
	public MemberDTO getMember(String email) {
		// MemberRepository - findByEmail() 메서드 호출하여 사용자 정보 조회(리턴타입 : Member)
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email + " 에 해당하는 회원이 없습니다!"));
		
		// ---------------------------------------------------------------------------------------------------------
		// Member 엔티티 - MemberRole 엔티티 - CommonCode 엔티티 사이의 연관관계 설정에 따라
		// Member 엔티티를 조회하더라도 MemberRole - CommonCode 에 해당하는 엔티티에 대한 조회는 즉시 이루어지지 않는다!
		// 즉, Member 엔티티만 조회하고 Member 내의 연관관계에 있는 엔티티들은 조회를 수행하지 않는다!
//		Hibernate: select m1_0.id,m1_0.address1,m1_0.address2,m1_0.email,m1_0.name,m1_0.passwd,m1_0.post_code,m1_0.reg_date from members m1_0 where m1_0.email=?
		// => members 테이블만 조회가 일어남
		// ---------------------------------------------------------------------------------------------------------
		// 만약, Member 엔티티에 접근할 경우 Member 엔티티에서 접근해야하는 MemberRole 엔티티가 존재해야하므로
		// 이 시점에 MemberRole 엔티티에 대한 조회가 수행된다! = 지연 로딩(LAZY LOADING 이라고 함)
//		log.info(">>>>>>>>>>>>>> Member 엔티티 : " + member); // 이 시점에 List<MemberRole> 에 대한 조회를 위해 member_role 테이블의 조회가 수행됨
//		Hibernate: select m1_0.id,m1_0.address1,m1_0.address2,m1_0.email,m1_0.name,m1_0.passwd,m1_0.post_code,m1_0.reg_date from members m1_0 where m1_0.email=?
//		Hibernate: select r1_0.member_id,r1_0.id,r1_0.member_role_id from member_role r1_0 where r1_0.member_id=?
		// => member 테이블에 대한 조회가 수행된 후 다시 member_role 테이블에 대한 조회가 수행됨
		// ---------------------------------------------------------------------------------------------------------
		// 만약, MemberRole 엔티티 내의 CommonCode 엔티티에 접근할 경우 
		// 다시 CommonCode 엔티티에 대한 common_code 테이블 조회가 수행된다!
		for(MemberRole role : member.getRoles()) {
			log.info(">>>>>>>>>>>>>> CommonCode 엔티티 : " + role.getRole()); // 이 시점에 CommonCode 에 대한 조회를 위해 common_code 테이블의 조회가 수행됨 
		}
		// ---------------------------------------------------------------------------------------------------------
		// Member 엔티티 -> MemberDTO 객체로 변환하여 리턴
		return MemberDTO.fromEntity(member);
	}
	
	
	
}











