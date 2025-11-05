package com.itwillbs.test5.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

// 파일 업로드 처리 작업을 모듈화 한 클래스
@Component
@Log4j2
public class FileUtils {
	// 파일 업로드에 사용할 경로를 properties 파일에서 가져오기
	// => 변수 선언부에 @Value("${프로퍼티속성명}") 형태로 선언
	@Value("${file.uploadBaseLocation}")
	private String uploadBaseLocation;
	
	@Value("${file.itemImgLocation}")
	private String itemImgLocation;
	
	public void test() {
		log.info(">>>>>>>>>>>>>> uploadBaseLocation : " + uploadBaseLocation);
		log.info(">>>>>>>>>>>>>> itemImgLocation : " + itemImgLocation);
	}
	
}























