package com.itwillbs.test5.member.dto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.itwillbs.test5.item.dto.ItemDTO;
import com.itwillbs.test5.member.entity.Member;
import com.itwillbs.test5.member.entity.MemberRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 로그인 인증에 사용될 인증 전용 DTO 클래스 정의(선택사항)	=> MemberDTO 그대로 사용도 가능
// => 현재 클래스가 스프링 시큐리티 인증 객체로 사용되도록 하려면 UserDetails 인터페이스를 구현체로 정의해야함
@Getter
@Setter
@ToString
public class MemberLoginDTO implements UserDetails {
	private String email;
	private String passwd;
	private String name;
	private List<MemberRole> roles; // 사용자 권한 목록
	// ---------------------------------------------------
	// 필수 오버라이딩 메서드
	// 1) 사용자명(= 아이디 역할)을 리턴하는 메서드
	// => 현재 사용자명을 이메일로 대체하여 사용하므로 이메일 주소 리턴
	@Override
	public String getUsername() {
		return email;
	}
	
	// 2) 사용자 패스워드를 리턴하는 메서드
	@Override
	public String getPassword() {
		return passwd;
	}
	
	// 3) 사용자의 권한 목록을 리턴하는 메서드
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 컬렉션(List, Set 등)을 또 다른 컬렉션 형태로 변환
		return roles.stream() // 컬렉션 요소를 자바 스트림으로 변환
				.map(role -> new SimpleGrantedAuthority(role.getRole().getCommonCode())) // 스트림의 각 요소를 다른 객체로 변환(스프링시큐리티가 관리하는 권한 객체(SimpleGrantedAuthority)로 변환)
				.collect(Collectors.toList()); // 최종적으로 변환된 SimpleGrantedAuthority 객체들을 List 객체로 모아서 리턴
	}
	// --------------------------------------------
	// 선택적 오버라이딩 메서드
	// 4) 계정 만료 여부 리턴
	@Override
	public boolean isAccountNonExpired() {
		// 실제 계정 만료 여부 확인하는 서비스 로직 추가 필요
		// ex) memberRepository.isAccountNonExpired() 등의 메서드로 조회
		return true;
	}

	// 5) 계정 잠금 여부 리턴
	@Override
	public boolean isAccountNonLocked() {
		// 실제 계정 잠금 여부 확인하는 서비스 로직 추가 필요
		return true;
	}

	// 6) 패스워드 기간 만료 여부 리턴
	@Override
	public boolean isCredentialsNonExpired() {
		// 실제 패스워드 기간 만료 여부 확인하는 서비스 로직 추가 필요
		return true;
	}

	// 7) 계정 사용 가능(활성화) 여부 리턴
	@Override
	public boolean isEnabled() {
		// 실제 계정 활성화 여부 확인하는 서비스 로직 추가 필요
		return true;
	}
	
	
}














