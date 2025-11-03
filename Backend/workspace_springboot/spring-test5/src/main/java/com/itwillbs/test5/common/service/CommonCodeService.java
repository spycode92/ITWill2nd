package com.itwillbs.test5.common.service;

import org.springframework.stereotype.Service;

import com.itwillbs.test5.common.entity.CommonCode;
import com.itwillbs.test5.common.repository.CommonCodeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommonCodeService {
	private final CommonCodeRepository commonCodeRepository;

	public CommonCodeService(CommonCodeRepository commonCodeRepository) {
		this.commonCodeRepository = commonCodeRepository;
	}
	// ==================================================================
	// 사용자 권한 조회
	public CommonCode getMemberRole(String commonCode) {
		// CommonCodeRepository - findByGroupCodeAndCommonCode()
		// => 첫번째 파라미터(groupCode)는 "MEMBER_ROLE" 값 고정
		return commonCodeRepository.findByGroupCodeAndCommonCode("MEMBER_ROLE", commonCode)
				.orElseThrow(() -> new EntityNotFoundException("해당 권한 코드가 존재하지 않습니다!"));
	}
	
	
}












