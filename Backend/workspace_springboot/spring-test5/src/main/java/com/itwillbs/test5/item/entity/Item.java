package com.itwillbs.test5.item.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.itwillbs.test5.item.constant.ItemCategory;
import com.itwillbs.test5.item.constant.ItemSellStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 상품 엔티티 정의
@Entity
@Table(name = "items")
@NoArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class) // JPA 감사 기능을 활용하여 날짜 정보 등의 자동 등록에 활용
// JPA 에서 사용할 오라클의 시퀀스 관련 설정
@SequenceGenerator(
	name = "ITEMS_SEQ_GENERATOR", // JPA 에서 사용하는 시퀀스 이름(DB 의 시퀀스 이름이 아님!)
	sequenceName = "ITEMS_SEQ",   // 오라클에서 사용하는 시퀀스 이름
	initialValue = 1,			  // 초기값(오라클 시퀀스의 start with 값과 동일)
	allocationSize = 1			  // 증가값(오라클 시퀀스의 increment by 값과 동일)
)
public class Item {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ITEMS_SEQ_GENERATOR") // 위에서 설정한 시퀀스 생성 이름 지정
	@Column(updatable = false)
	private Long id; // 상품번호
	
	@Column(length = 100, nullable = false)
	private String itemNm; // 상품명
	
	@Column(nullable = false)
	private String itemDetail; // 상품상세설명
	
	private Integer price; // 가격
	private Integer stockQty; // 재고수량
	
	// ----------------------------------------------------------------------------
	// 엔티티를 통해 DB 에 enum 타입 데이터 연결 시 String 타입으로 처리하도록 하기
	// => enum 타입을 DB 기본타입인 enum 타입으로 사용 시 번호 형태(ORDINAL)로 관리되는데
	//    enum 상수 순서가 변경되면 값이 바뀌게 되므로 추천하지 않는다!
	@Enumerated(EnumType.STRING) // EnumType.ORDINAL : 순서번호 형태로 관리됨
	@Column(nullable = false)
	private ItemCategory category; // 상품카테고리(enum)
	
	@Enumerated(EnumType.STRING) // EnumType.ORDINAL : 순서번호 형태로 관리됨
	@Column(nullable = false)
	private ItemSellStatus sellStatus; // 상품판매상태(enum)
	// ----------------------------------------------------------------------------
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime regTime; // 상품등록일시(수정 불가능한 필드로 설정. 날짜 및 시각 자동 등록)

	@LastModifiedDate // 엔티티 변경 시점에 날짜 및 시각 자동 갱신
	private LocalDateTime updateTime; // 상품정보수정일시
	
	// ----------------------------------------------------------------------------
	// 상품(1) : 상품이미지(N) 연관관계 매핑
	// => 1에 해당하는 엔티티 클래스에서 @OneToMany 어노테이션을 지정
	@OneToMany(mappedBy = "item", fetch = FetchType.LAZY) // 지연로딩 설정
	private List<ItemImg> itemImgs = new ArrayList<>();

	// ----------------------------------------------------------------------------
	// 상품 기본 정보(아이디, 날짜, 이미지 목록 제외)에 대한 생성자 정의 = 빌더 패턴 적용
	@Builder
	public Item(String itemNm, String itemDetail, Integer price, Integer stockQty, ItemCategory category,
			ItemSellStatus sellStatus) {
		super();
		this.itemNm = itemNm;
		this.itemDetail = itemDetail;
		this.price = price;
		this.stockQty = stockQty;
		this.category = category;
		this.sellStatus = sellStatus;
	}
	
	
}






















