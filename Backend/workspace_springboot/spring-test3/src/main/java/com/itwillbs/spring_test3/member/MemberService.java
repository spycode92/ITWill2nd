package com.itwillbs.spring_test3.member;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Component // 스프링 빈으로 등록하는 공통 어노테이션
@Service // 스프링 빈으로 등록하는 어노테이션. 서비스 역할(= 비즈니스 로직 처리)을 수행하는 용도라는 의미의 어노테이션(@Component 어노테이션을 포함함)
public class MemberService {
	// 스프링 빈으로 등록된 MemberRepository 객체 자동 주입 설정
	@Autowired
	private MemberRepository memberRepository;
	
	// DI 확인을 위한 임시 메서드
//	public void test() {
//		System.out.println("MemberService - test()");
//	}
	
	public List<Member> getMemberList() {
		System.out.println("MemberService - getMemberList()");
		
		// 전체 member 테이블(정확히는 Member 엔티티의 목록)을 조회하는 작업 요청
		// => JPA 에서 데이터베이스 작업을 실질적으로 처리해주는 Repository 의 특정 메서드를 호출하여 데이터 조회 요청 가능
		// => 테이블의 전체 레코드를 조회하는 구문(SELECT * FROM xxx) 을 수행하는 메서드는
		//    별도의 쿼리 메서드 정의없이 기본으로 제공되는 findAll() 메서드 호출하여 목록 조회 및 데이터 리턴 가능 
		// => findAll() 메서드 리턴타입이 기본적으로 List<T> 타입인데
		//    MemberRepository 인터페이스 정의 시 extends JpaRepository<Member, Long> 형태로 상속을 표현했을 때
		//    제네릭타입 첫번째로 지정된 엔티티 클래스 타입 Member 가 T 부분에 해당하는 타입으로 적용됨
		//    따라서, 해당 엔티티와 연결된 테이블을 조회하여 List<T> 타입 대신 엔티티에 대한 타입인 List<Member> 타입으로 변경되어 객체가 리턴됨 
		return memberRepository.findAll();
	}

	public Member getMember(Long id) {
		System.out.println("MemberService - getMember()");
		
		// member 테이블 내에서 id 값이 일치하는 1개의 레코드 조회(SELECT * FROM member WHERE id = ?)
		// => JpaRepository 를 통해 findByXXX() 메서드가 제공됨(특정 대상(XXX)을 기준으로 레코드를 검색하는 용도의 메서드)
		// => @Id 가 붙은 필드(Long id)를 기준으로 레코드를 검색하기 위해 findById() 메서드 호출
		//    (주의! id 컬럼명이 아닌 @Id 어노테이션이 붙은 필드에 연결된 컬럼(PK 컬럼)을 뜻함)
		// => findById() 메서드 리턴값에 대해 get() 메서드 호출 시 조회된 엔티티 1개가 리턴됨
//		return memberRepository.findById(id).get(); // Optional<Member> 내에서 Member 엔티티를 꺼내서 리턴
		// => findById() 메서드 리턴타입은 Optional<T> 타입이며, 현재 T 타입이 Member 엔티티 타입이므로 Optional<Member> 타입 리턴됨
		// => 주의! 조회 결과가 없을 경우 조회되는 엔티티도 비어있게 되며, 이 때 Optional<T> 객체의 get() 메서드 호출 시 예외 발생함
		//    (java.util.NoSuchElementException: No value present)
		// => 따라서, 조회 대상이 없을 경우에 대한 처리를 Optional 객체를 통해 추가적으로 수행하는 것이 안전함
		// --------------------------------------------------
		// [ 첫번째 방법 ]
		// Optional<Member> 타입을 리턴받아서 조회 결과가 있을 경우에 조회 결과를 담은 Member 엔티티를 리턴하고
		// 조회 결과가 없을 경우 Member 엔티티를 리턴하지 않거나 빈 엔티티를 리턴하도록 처리하는 방법 예시
//		Optional<Member> optionalMember = memberRepository.findById(id);
//		System.out.println("optionalMember : " + optionalMember);
		// => 조회 결과가 없을 경우 optionalMember : Optional.empty
		// => 조회 결과가 있을 경우 optionalMember : Optional[Member(id=2, name=이순신)]
		
		// 조회 결과가 없을 경우를 판별하여 추가적인 작업 수행 가능
		// => Optional 객체의 isEmpty() 메서드를 사용하여 데이터가 없음을 판별 가능 <-> isPresent() 메서드로 데이터가 있음을 판별 가능
//		if(optionalMember.isEmpty()) {
//			System.out.println("조회 결과 없음!");
//			
//			throw new NoSuchElementException("존재하지 않는 회원입니다!");
//			// 또는 
//			return new Member(); // 빈 객체를 생성하여 리턴하는 등의 추가 작업 가능
//		}
//		
//		System.out.println("조회 결과 있음!");
//		return optionalMember.get();
		// --------------------------------------------------
		// [ 두번째 방법 ]
		// Optional 객체의 orElse() 또는 orElseThrow() 메서드 활용하여 조회 결과가 없을 경우까지 대비하는 작업을 압축된 코드로 수행
		// 1) findByXXX() 메서드 뒤에 orElseThrow() 메서드를 연쇄적으로 호출하여 메서드 파라미터로
		//    조회 결과가 없을 때 실행할 예외 처리 클래스의 인스턴스를 화살표 함수 형태로 지정
		//    => 조회 결과가 있으면 get() 메서드가 자동으로 호출되어 조회된 엔티티가 리턴되고
		//       아니면, 지정한 예외 인스턴스가 실행되어 예외 메세지가 전달됨(별도의 예외 처리 클래스로 처리 가능)
//		return memberRepository.findById(id)
//								.orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다!"));
		
		// 2) orElse() 메서드 사용 시 예외를 발생시키는 대신 새로운 엔티티(빈 엔티티 등)를 생성하여 리턴
		//    => 즉, 예외가 발생하지 않고 빈 값이 저장된 엔티티를 외부로 전달하여 안전하게 처리 가능
		return memberRepository.findById(id)
								.orElse(new Member());
	}

	// 이름으로 Member 엔티티 검색 요청
	public Member getMemberByName(String name) {
		// MemberRepository - findByXXX() 메서드가 제공되므로 findByName() 메서드 호출하면 이름으로 조회 가능
		// => findById() 외의 나머지 컬럼(필드)을 조건으로 갖는 메서드는 자동으로 생성되어 제공되지 않으므로 추가로 정의해야함 
		return memberRepository.findByName(name)
				.orElseThrow(() -> new NoSuchElementException("해당 이름의 회원이 존재하지 않습니다!"));
	}

	// 아이디와 이름으로 Member 엔티티 검색 요청
	public Member searchMember(Map<String, String> params) {
		// MemberRepository - findByIdAndName() 메서드 추가 정의 필요
		// 주의! Map<String, String> 타입일 때 id 파라미터는 Long 타입으로 전달되어야 하므로 문자열 -> Long 변환 필수!
		return memberRepository.findByIdAndName(Long.parseLong(params.get("id")), params.get("name"))
				.orElseThrow(() -> new NoSuchElementException("해당 번호와 이름이 일치하는 회원이 존재하지 않습니다!"));
	}

}















