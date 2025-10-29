package com.itwillbs.test4_thymeleaf.item;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class ItemDTO {
	private Long id; // 상품코드
	private String itemNm; // 상품명
	private String itemDetail; // 상품 상세설명
	private int price; // 가격
	private int stockQty; // 재고수량
	private LocalDateTime regTime; // 상품등록일시
	
	@Builder
	public ItemDTO(Long id, String itemNm, String itemDetail, int price, int stockQty, LocalDateTime regTime) {
		super();
		this.id = id;
		this.itemNm = itemNm;
		this.itemDetail = itemDetail;
		this.price = price;
		this.stockQty = stockQty;
		this.regTime = regTime;
	}
	
	
}



















