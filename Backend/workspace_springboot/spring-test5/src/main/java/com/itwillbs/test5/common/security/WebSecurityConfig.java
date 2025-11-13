package com.itwillbs.test5.common.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 기능 설정 클래스로 지정
// 메서드별 권한 체크를 수행하는 메서드 시큐리티(어노테이션 활용) 활성화
// => 기본값(prePostEnabled = true) : @PreAuthrize 어노테이션 활성화
@EnableMethodSecurity(prePostEnabled = true) 
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
//					.requestMatchers("/items/**").authenticated() // "/items" 로 시작하는 하위 경로 포함 모든 경로를 제어 
					.requestMatchers("/items/regist").hasAnyAuthority("ROLE_ADMIN", "ROLE_ADMIN_SUB")// 상품등록 경로는 관리자들만 접근 가능
					.requestMatchers(HttpMethod.GET, "/items", "/items/*").permitAll() // 상품 목록, 상세정보 조회 경로(GET)는 모든 사용자가 접근 가능
					.requestMatchers(HttpMethod.DELETE, "/items/*").hasAuthority("ROLE_ADMIN") // 상품삭제 경로(DELETE, 경로변수 포함)는 관리자만 접근 가능
					.requestMatchers("/", "/members/regist").permitAll() // 메인페이지, 회원가입 경로는 모든 사용자가 접근 가능
					.requestMatchers("/members/**").authenticated() // 나머지 회원 관련 정보는 로그인 대상만 허용
					// 정적 리소스 경로(static/js, static/css, static/images 등)를 시큐리티 필터링 대상에서 모두 허용
					.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
					.anyRequest().authenticated() // 그 외의 모든 요청은 인증된 사용자만 접근 가능
//					.anyRequest().denyAll() // 그 외의 모든 요청은 거부
				)
				// ---------- 로그인 처리 설정 ---------
				.formLogin(formLogin -> formLogin
					.loginPage("/members/login") // 로그인 폼 요청에 사용할 URL 지정(컨트롤러에서 매핑 처리)
					.loginProcessingUrl("/members/login") // 로그인 폼에서 제출된 데이터 처리용 요청 주소(자동으로 POST 방식으로 처리됨)
					// => 여기서 지정한 경로는 컨트롤러에서 별도의 매핑 불필요(단, 로그인 처리 과정에서 부가적인 기능 추가할 경우 매핑 필요)
					// => 이 과정에서 UserDetailsService(또는 구현체) 객체의 loadByUsername() 메서드가 자동으로 호출됨
					.usernameParameter("email") // 로그인 과정에서 사용할 사용자명(username)을 이메일(email)로 지정(기본값 : username)
					.passwordParameter("passwd") // 로그인 과정에서 사용할 패스워드 지정(기본값 : password)
//					.defaultSuccessUrl("/", true) // 로그인 성공 시 항상 리디렉션 할 기본 URL 설정
					.successHandler(new CustomAuthenticationSuccessHandler()) // 로그인 성공 시 별도의 추가 작업을 처리할 핸들러 지정
					.permitAll() // 로그인 경로 관련 요청 주소를 모두 허용
				)
				// ---------- 로그아웃 처리 설정 ---------
				.logout(logoutCustomizer -> logoutCustomizer
					.logoutUrl("/members/logout") // 로그아웃 요청 URL 지정(주의! POST 방식 요청으로 취급함)
					.logoutSuccessUrl("/") // 로그아웃 성공 후 리디렉션 할 URL 지정
					.permitAll()
				)
				// ---------- 자동 로그인 처리 설정(쿠키 활용 => 브라우저 개발자 도구(크롬 기준) Application - Cookies 항목에서 확인) ---------\
				.rememberMe(rememberMeCustormizer -> rememberMeCustormizer
					.rememberMeParameter("remember-me") // 자동 로그인 수행을 위한 체크박스 파라미터명 지정(체크 여부 자동으로 판별)
					.key("my-fixed-secret-key") // 서버 재시작해도 이전 로그인에서 사용했던 키 동일하게 사용
					.tokenValiditySeconds(60 * 60 * 24) // 자동 로그인 토큰 유효기간 설정(기본값 14일 => 1일로 변경)
				)
				.build();
	}
	
	// ===================================================
	// 사용자 인증 과정에서 패스워드 인코딩 시 사용될 단방향 암호화 인코더 객체 생성 메서드 정의
	// => 생략 시 패스워드 인코딩에 사용할 객체를 선택하지 못해 예외 발생
	//    (java.lang.IllegalArgumentException: Given that there is no default password encoder configured, each password must have a password encoding prefix. Please either prefix this password with '{noop}' or set a default password encoder in `DelegatingPasswordEncoder`.)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}


















