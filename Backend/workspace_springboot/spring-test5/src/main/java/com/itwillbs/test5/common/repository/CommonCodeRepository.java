package com.itwillbs.test5.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itwillbs.test5.common.entity.CommonCode;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, Long> {
	
	Optional<CommonCode> findByGroupCodeAndCommonCode(String groupCode, String commonCode);

}
