package com.itwillbs.test5.common.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 기능 설정 클래스로 지정
public class WebSecurityConfig {
	// 시큐리티 정보 조회에 사용할 서비스 클래스 주입
	private final CustomUserDetailsService userDetailsService;

	public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	// ====================================================================
	// 스프링 시큐리티 보안 필터 설정
	// => 메서드 이름은 무관하나 반드시 리턴타입이 SecurityFilterChain 타입을 리턴하는 메서드여야함
	// => 메서드 파라미터에 HttpSecurity 타입을 선언하여 보안 처리용 객체를 자동 주입
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// HttpSecurity 객체의 다양한 메서드를 메서드 체이닝 형태로 호출하여 스프링 시큐리티 관련 설정을 수행하고
		// 마지막에 build() 메서드 호출하여 HttpSecurity 객체 생성하여 리턴
		return httpSecurity
				// ---------- 요청에 대한 접근 허용 여부 등의 요청 경로에 대한 권한 설정 ---------
				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//					.anyRequest().permitAll() // 모든 요청에 대해 접근 허용
					.requestMatchers("/items/regist").authenticated() // 상품등록 경로는 로그인 한(인증된) 사용자만 접근 가능
					.requestMatchers("/", "/members/regist").permitAll() // 메인페이지, 회원가입 경로는 모든 사용자가 접근 가능
					// 정적 리소스 경로(static/js, static/css, static/images 등)를 시큐리티 필터링 대상에서 모두 허용
					.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
					.anyRequest().authenticated() // 그 외의 모든 요청은 인증된 사용자만 접근 가능
				)
				// ---------- 로그인 처리 설정 ---------
				.formLogin(formLogin -> formLogin
					.loginPage("/members/login") // 로그인 폼 요청에 사용할 URL 지정(컨트롤러에서 매핑 처리)
					.loginProcessingUrl("/members/login") // 로그인 폼에서 제출된 데이터 처리용 요청 주소(자동으로 POST 방식으로 처리됨)
					// => 여기서 지정한 경로는 컨트롤러에서 별도의 매핑 불필요(단, 로그인 처리 과정에서 부가적인 기능 추가할 경우 매핑 필요)
					// => 이 과정에서 UserDetailsService(또는 구현체) 객체의 loadByUsername() 메서드가 자동으로 호출됨
					.usernameParameter("email") // 로그인 과정에서 사용할 사용자명(username)을 이메일(email)로 지정(기본값 : username)
					.passwordParameter("passwd") // 로그인 과정에서 사용할 패스워드 지정(기본값 : password)
					.permitAll() // 로그인 경로 관련 요청 주소를 모두 허용
				)
				.build();
	}
}


















