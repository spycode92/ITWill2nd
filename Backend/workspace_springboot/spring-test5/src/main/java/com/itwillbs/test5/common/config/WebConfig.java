package com.itwillbs.test5.common.config;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 스프링 설정 파일로 사용하기 위해 @Configuration 어노테이션 지정
// MVC 관련 설정을 위해 WebMvcConfigurer 인터페이스 구현체로 정의
@Configuration
public class WebConfig implements WebMvcConfigurer {
	// application.properties 에서 선언한 업로드 기본 경로 가져오기
	@Value("${file.uploadBaseLocation}")
	private String uploadBaseLocation;

	// 자원 핸들링 처리를 위한 WebMvcConfigurer - addResourceHandlers() 메서드 오버라이딩
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 클라이언트가 요청하는 가상의 축약경로(ex. /files)를 서버상의 실제 경로로 변환 처리
		// 1) 실제 서버상의 업로드 디렉토리를 URI 형식으로 가져오기
		String uploadDir = Paths.get(uploadBaseLocation).toUri().toString(); // ex) file:///D:/usr/local/tomcat/upload/
		
		// 2) 클라이언트 요청 URL 패턴이 "/files/xxx" 형식일 경우 실제 경로로 치환 처리
		registry.addResourceHandler("/files/**") // "/files" 경로 및 하위 경로를 포함하는 모든 요청 URL 등록(콤마로 구분하여 복수개 지정도 가능)
				.addResourceLocations(uploadDir); // 치환(바인딩)되는 실제 경로 등록
	}
	
}















