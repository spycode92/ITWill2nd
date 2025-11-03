package com.itwillbs.test5.item.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 상품 판매상태를 관리할 enum 정의
@Getter
@RequiredArgsConstructor
public enum ItemSellStatus {
	AVAILABLE("판매중"),
	OUT_OF_STOCK("품절"),
	DISCONTINUED("단종");
	
	private final String label;
	
	public String getCode() {
		return this.name();
	}
}
