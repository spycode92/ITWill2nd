package com.itwillbs.test5.item.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.test5.item.dto.ItemDTO;
import com.itwillbs.test5.item.dto.ItemImgDTO;
import com.itwillbs.test5.item.entity.Item;
import com.itwillbs.test5.item.entity.ItemImg;
import com.itwillbs.test5.item.repository.ItemRepository;

import jakarta.persistence.EntityNotFoundException;
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
	public Long registItem(ItemDTO itemDTO, List<MultipartFile> itemImgFiles) throws IOException {
		// ItemDTO 객체 -> Item 엔티티로 변환
		Item item = itemDTO.toEntity();
		log.info(">>>>>>>>>>>>>>>>>> item : " + item);
		
		// ItemRepository - save() 메서드 호출하여 상품 정보 INSERT
		itemRepository.save(item); // INSERT 과정에서 PK 값(Long id)이 자동 생성된 후 엔티티에 자동으로 저장됨
		
		// ----------------------------------------------------
		// ItemImgService - registItemImg() 메서드 호출하여 상품 이미지(파일) 등록 요청
		// => 파라미터 : Item 엔티티, 상품이미지(첨부파일) 목록 List 객체
		itemImgService.registItemImg(item, itemImgFiles);
		// ----------------------------------------------------
		
		return item.getId(); // 새로 등록된 엔티티의 id 값 리턴
	}
	
	// ====================================================
	// 상품 목록 조회
	public List<ItemDTO> getItemList() {
		List<Item> itemList = itemRepository.findAll();
		
		// List<Item> -> List<ItemDTO> 타입으로 변환하여 리턴
		return itemList.stream() // 1) List<ItemImg> 객체에 대한 자바 스트림 생성
				// 2) 생성된 스트림 내의 각 요소에 존재하는 fromEntity() 메서드를 호출하여 ItemImg -> ItemImgDTO 객체로 변환
//				.map(item -> ItemDTO.fromEntity(item))
				// 2번 코드에 대한 화살표 함수 호출 축약형 코드(= 메서드 참조(Method reference) 라고 함)
				.map(ItemDTO::fromEntity)
				// 3) ItemImgDTO 를 List 객체에 담기
				.collect(Collectors.toList());
	}
	
	// 상품 목록 조회 + 페이징
	// => 리턴타입을 List<ItemDTO> 대신 페이징 정보를 포함하는 Page<ItemDTO> 타입으로 변경
	public Page<ItemDTO> getItemList(Integer page, Integer pageSize) {
		// 목록에 대한 페이징 처리를 위해 현재 페이지와 페이지 사이즈를 PageRequest.of() 메서드로 전달하여 Pageable 객체 리턴받기
		// PageRequest.of() 메서드 파라미터
		// 1) 페이지 번호(기본 페이지가 0부터 시작하므로 전달받은 페이지번호 - 1)
		// 2) 페이지 크기
		// 3) 정렬방식 => Sort.by() 메서드에 Direction.ASC 또는 DESC, 정렬할 필드명
		Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Direction.DESC, "id"));
		
		// 기존 목록 요청 시 사용하던 findAll() 메서드 대신 findAll(Pageable) 메서드 호출하여 목록 조회하여 Page<T> 타입으로 리턴받기
		Page<Item> pageItem = itemRepository.findAll(pageable);
		// List<Item> -> List<ItemDTO> 로 변환한 방식과 동일한 기능을 Page 객체가 map 메서드로 제공해줌
		// => map() 메서드 호출하여 변환 기능을 수행하는 메서드만 람다식으로 기술
		return pageItem.map(ItemDTO::fromEntity);
	}
	// ====================================================
	// 상품 상세정보 조회
	public ItemDTO getItem(Long itemId) {
		// 상품 1개 정보 조회
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new EntityNotFoundException(itemId + " 번 상품이 존재하지 않습니다!"));
		
		// Item -> ItemDTO 변환
		ItemDTO itemDTO = ItemDTO.fromEntity(item);
		
		// Item 엔티티 조회 시 List<ItemImg> 타입 필드는 FetchType.LAZY 설정(지연로딩)에 의해 동시 조회가 일어나지 않는다! 
		// 해당 데이터가 실제로 사용되는 시점에 조회가 일어나므로 추가적인 접근 코드를 통해 조회가 일어날 수 있도록 해야함
		// => Item 엔티티의 getItemImgs() 메서드를 호출하여 이미지 리스트 가져옴
		List<ItemImg> itemImgList = item.getItemImgs(); // 아직 실제 엔티티(ItemImg)를 사용하기 전이므로 조회가 일어나지 않는다!
//		log.info(">>>>>>>>>>> itemImgList : " + itemImgList); // 해당 엔티티(ItemImg)에 직접 접근 시점인 이 코드가 실행될 때 실제 조회가 발생함!
//		log.info(">>>>>>>>>>> 첨부파일 갯수 : " + itemImgList.size());
		
		// List<ItemImg> -> List<ItemImgDTO> 타입으로 변환하여 ItemDTO 객체에 저장
		itemDTO.setItemImgDTOList(
			// 1) List<ItemImg> 객체에 대한 자바 스트림 생성
			itemImgList.stream()
			// 2) 생성된 스트림 내의 각 요소에 존재하는 fromEntity() 메서드를 호출하여 ItemImg -> ItemImgDTO 객체로 변환
//			.map(itemImg -> ItemImgDTO.fromEntity(itemImg))
			// 2번 코드에 대한 화살표 함수 호출 축약형 코드(= 메서드 참조(Method reference) 라고 함)
			.map(ItemImgDTO::fromEntity)
			// 3) ItemImgDTO 를 List 객체에 담기
			.collect(Collectors.toList())
		);
		
		return itemDTO;
	}
	
	// ======================================================
	// 상품 정보 수정
	@Transactional // 필수!
	public void modifyItem(@Valid ItemDTO itemDTO, List<MultipartFile> itemImgFiles) throws IOException {
		// 수정할 상품 엔티티 조회하여 Item 엔티티 타입으로 리턴받기
		Long itemId = itemDTO.getId();
		Item item = itemRepository.findById(itemId)
						.orElseThrow(() -> new EntityNotFoundException(itemId + " 번 상품이 존재하지 않습니다!"));
		
		// 상품 수정을 위해 Item 객체(엔티티)에서 필드값을 변경하는 메서드를 별도로 정의하여
		// 변경할 값이 저장된 객체 등을 전달받아 실제 엔티티의 필드값 변경 시
		// 더티 체킹에 의해 자동으로 UPDATE 수행됨 => 트랜잭션 적용 필수!
		item.changeItem(itemDTO);
		// ----------------------------------------------------
		// ItemImgService - registItemImg() 메서드 호출하여 상품 이미지(첨부파일) 등록 요청
		// => 파라미터 : Item 엔티티, 첨부파일 목록 List 객체
		itemImgService.registItemImg(item, itemImgFiles);
	}
	
	// ======================================================
	// 상품 정보 삭제
//	@Transactional // 선택사항
	public void removeItem(Long id) {
		// 삭제할 상품 엔티티 조회하여 Item 엔티티 타입으로 리턴받기
		Item item = itemRepository.findById(id)
						.orElseThrow(() -> new EntityNotFoundException(id + " 번 상품이 존재하지 않습니다!"));
		
		// 상품 엔티티 내의 첨부파일 목록 조회하여 List<ItemImg> 객체로 리턴받기
		List<ItemImg> itemImgList = item.getItemImgs();
//		log.info(">>>>>>>>>>>>>> 파일명 : " + itemImgList.get(0).getImgName());
		
		// ItemImgService - removeItemImgs() 메서드 호출하여 복수개의 첨부파일 삭제
		// => 파라미터 : List<ItemImg> 객체(첨부파일 목록 엔티티)
		itemImgService.removeItemImgs(itemImgList);
		
		// ItemRepository - delete() 메서드를 호출하여 엔티티 삭제(또는 deleteById() 메서드에 id 를 전달하여 바로 삭제도 가능)
		itemRepository.delete(item);
		
		
	}
	
}















