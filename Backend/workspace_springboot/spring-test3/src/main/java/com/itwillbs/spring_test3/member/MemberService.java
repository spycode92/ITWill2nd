package com.itwillbs.spring_test3.member;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

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

	// ===============================================================
	// Member 엔티티를 생성하여 이름 저장 요청 - INSERT
	// INSERT, DELETE, UPDATE 등의 작업을 수행하는 메서드는 상단에 @Transactional 어노테이션을 적용하여 트랜잭션 적용 필요
	@Transactional
	public Member registMember(String name) {
		// INSERT 작업에 사용될 Member 엔티티 객체 생성
		// => 엔티티 생성 시 PK 값으로 사용되는 id값(@Id 어노테이션이 붙은 id 필드)은 null 값 전달하면 자동으로 부여됨
		//    만약, 기본 생성자를 호출할 경우에는 id 값을 제외한 다른 필드의 Setter 메서드를 호출하여 저장
		// => 주의! data.sql 파일을 통해 더미데이터를 저장할 경우 PK 값 중복으로 인한 예외 발생할 수 있으므로
		//    옵션을 통해 data.sql 등의 스키마가 실행되지 않도록 하거나, 파일명을 data.sql 이 아닌 이름으로 변경하거나
		//    또는 data.sql 내의 INSERT 구문의 키값(PK값 = id 컬럼)을 1번부터가 아닌 더 뒤의 값으로 수동으로 설정 
		Member member = new Member(); // INSERT 작업에 사용할 엔티티 생성
		member.setName(name); // 엔티티 내에 필드값 저장
		
		// MemberRepository - save() 메서드 호출하여 INSERT 작업 요청(별도의 정의 불필요)
		// => 파라미터 : 엔티티 객체   리턴타입 : 엔티티 타입 객체(INSERT 작업 성공 시 해당 엔티티 리턴됨)
		return memberRepository.save(member); // save() 메서드가 호출되는 시점에 엔티티의 필드값이 DB 에 반영(INSERT)됨
	}

	// 번호(PK)로 Member 엔티티를 통해 레코드 삭제 - DELETE
	@Transactional
	public void removeMemberById(Long id) {
		// [ 첫번째 방법.  ]
		// 리턴타입이 void 이므로 삭제 결과 알 수 없음. 단, 조회를 먼저 수행하므로 조회 결과에 따라 삭제 결과 예상 가능
		
		// [ 두번째 방법. 삭제할 엔티티를 먼저 조회한 후 delete() 메서드를 통해 엔티티 객체를 전달하여 삭제 처리 ]
		// 리턴타입이 void 이므로 삭제 결과 알 수 없음. 단, 조회를 먼저 수행하므로 조회 결과에 따라 삭제 결과 예상 가능
		// 1) 삭제할 id 에 대한 엔티티 조회(없을 경우 예외 발생 시키기)
		Member member = memberRepository.findById(id)
							.orElseThrow(() -> new NoSuchElementException("번호에 해당하는 회원이 존재하지 않습니다!"));
		
		// 2) MemberRepository - delete() 메서드 호출하여 레코드 삭제 요청
		//    => 파라미터 : 삭제할 엔티티 => 엔티티 조회 작업이 선행되어야 함!
		memberRepository.delete(member);
	}

	// 이름으로 Member 엔티티를 통해 레코드 삭제 - DELETE
	@Transactional
	public void removeMemberByName(String name) {
		// deleteById() 외의 나머지 엔티티 필드 기준 삭제 작업은 메서드가 제공되지 않음
		// => 따라서, findByName() 메서드를 통해 이름 기준 조회를 수행 후 delete() 메서드로 삭제만 가능 
		// 1) 삭제할 name 에 대한 엔티티 조회(없을 경우 예외 발생 시키기)
		Member member = memberRepository.findByName(name)
							.orElseThrow(() -> new NoSuchElementException("이름에 해당하는 회원이 존재하지 않습니다!"));
		
		// 2) MemberRepository - delete() 메서드 호출하여 레코드 삭제 요청
		//    => 파라미터 : 삭제할 엔티티 => 엔티티 조회 작업이 선행되어야 함!
		memberRepository.delete(member);
	}

	// ==============================================================
	// 정보 수정(번호에 해당하는 이름 수정) - UPDATE
	@Transactional
	public Member modifyMember(Long id, String name) {
		// 1) 번호에 해당하는 엔티티 조회
		Member member = memberRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("번호에 해당하는 회원이 존재하지 않습니다!"));
		System.out.println(member);
		
		// UPDATE 작업 수행은 Repository 의 쿼리 메서드 호출이 아닌 엔티티 객체의 필드값 변경하는 메서드를 정의하여 작업 수행
		// => Member 엔티티 클래스 내에 changeName() 등의 메서드를 정의하고, 메서드 내에서 엔티티 필드값을 변경하는 코드를 작성하면
		//    해당 메서드가 호출되어 필드값이 변경되면 JPA 더티체킹(Dirty Checking = 엔티티 값이 변경된 것을 자동으로 감지)에 의해
		//    변경된 값에 대한 트랜잭션 커밋 시점에 DB UPDATE 작업이 자동으로 수행됨 
		// => 주의! 새 엔티티가 아닌 기존에 조회된 엔티티를 대상으로 변경 메서드를 호출해야한다!
		member.changeName(name);
		
		return member;
	}
	
	

}















