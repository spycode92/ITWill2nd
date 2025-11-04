package com.itwillbs.test5.item.dto;

import org.modelmapper.ModelMapper;

import com.itwillbs.test5.item.entity.Item;
import com.itwillbs.test5.item.entity.ItemImg;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemImgDTO {
	private Long id; // 상품번호
	private String imgName; // 실제 이미지 파일명
	private String originalImgName; // 원본 이미지 파일명
	private String imgLocation; // 이미지 파일 위치
	private String repImgYn; // 대표 이미지 여부("Y"/"N")
	// ===========================================================
	// DTO <-> Entity 변환 메서드 구현
	private static ModelMapper modelMapper = new ModelMapper();
	
	public ItemImg toEntity() {
		return modelMapper.map(this, ItemImg.class);
	}
	
	public static ItemImgDTO fromEntity(ItemImg itemImg) {
		return modelMapper.map(itemImg, ItemImgDTO.class);
	}
}







