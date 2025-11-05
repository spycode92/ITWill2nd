package com.itwillbs.test5.item.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.itwillbs.test5.item.constant.ItemCategory;
import com.itwillbs.test5.item.constant.ItemSellStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 상품 이미지 엔티티
@Entity
@Table(name = "item_img")
@Getter
@Setter
//JPA 에서 사용할 오라클의 시퀀스 관련 설정
@SequenceGenerator(
	name = "ITEM_IMG_SEQ_GENERATOR", // JPA 에서 사용하는 시퀀스 이름(DB 의 시퀀스 이름이 아님!)
	sequenceName = "ITEM_IMG_SEQ",   // 오라클에서 사용하는 시퀀스 이름
	initialValue = 1,			  // 초기값(오라클 시퀀스의 start with 값과 동일)
	allocationSize = 1			  // 증가값(오라클 시퀀스의 increment by 값과 동일)
)
public class ItemImg {
	@Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "ITEM_IMG_SEQ_GENERATOR")
	@Column(name = "item_img_id", updatable = false)
	private Long id; // 상품번호
	private String imgName; // 실제 이미지 파일명
	private String originalImgName; // 원본 이미지 파일명
	private String imgLocation; // 이미지 파일 위치
	private String repImgYn; // 대표 이미지 여부("Y"/"N")
	// --------------------------------------------------------
	// 상품(1) : 상품이미지(N) 연관관계 매핑
	@ManyToOne
	@JoinColumn(name = "item_id") // 외래키(FK) 이름(Item 테이블 참조)
	private Item item;
}




















