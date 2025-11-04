package com.itwillbs.test5.item.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.test5.item.dto.ItemDTO;
import com.itwillbs.test5.item.entity.Item;
import com.itwillbs.test5.item.repository.ItemRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ItemService {
	private final ItemRepository itemRepository;
	private final ItemImgService itemImgService;
	
	public ItemService(ItemRepository itemRepository, ItemImgService itemImgService) {
		this.itemRepository = itemRepository;
		this.itemImgService = itemImgService;
	}
	// =================================================================
	// 상품 정보 등록 요청
	@Transactional
	public Long registItem(ItemDTO itemDTO, List<MultipartFile> itemImgFiles) {
		// ItemDTO 객체 -> Item 엔티티로 변환
		Item item = itemDTO.toEntity();
		log.info(">>>>>>>>>>>>>>>>>> item : " + item);
		
		// ItemRepository - save() 메서드 호출하여 상품 정보 INSERT
		itemRepository.save(item); // INSERT 과정에서 PK 값(Long id)이 자동 생성된 후 엔티티에 자동으로 저장됨
		
		
		
		return item.getId(); // 새로 등록된 엔티티의 id 값 리턴
	}
	
	
}















