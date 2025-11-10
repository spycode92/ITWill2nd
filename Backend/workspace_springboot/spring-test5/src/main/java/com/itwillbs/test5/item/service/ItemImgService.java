package com.itwillbs.test5.item.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.test5.common.util.FileUtils;
import com.itwillbs.test5.item.dto.ItemImgDTO;
import com.itwillbs.test5.item.entity.Item;
import com.itwillbs.test5.item.entity.ItemImg;
import com.itwillbs.test5.item.repository.ItemImgRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ItemImgService {
	// 파일 업로드에 사용할 경로를 properties 파일에서 가져오기
	// => 변수 선언부에 @Value("${프로퍼티속성명}") 형태로 선언
	@Value("${file.uploadBaseLocation}")
	private String uploadBaseLocation;
	
	@Value("${file.itemImgLocation}")
	private String itemImgLocation;
	// --------------------------------------------------------
	private final ItemImgRepository itemImgRepository;
	private final FileUtils fileUtils;

	public ItemImgService(FileUtils fileUtils, ItemImgRepository itemImgRepository) {
		this.itemImgRepository = itemImgRepository;
		this.fileUtils = fileUtils;
	}
	// --------------------------------------------------------
	// 상품 이미지 첨부파일 등록
	public void registItemImg(Item item, List<MultipartFile> itemImgFiles) throws IOException {
		// ItemImg 엔티티 목록을 저장할 List<ItemImg> 객체 생성
		List<ItemImg> itemImgList = new ArrayList<>();
		
		// --------------------------------------------------------------------------------------
		// [ 파일 저장될 디렉토리 생성 ]
		// 파일 관리할 디렉토리를 하나의 디렉토리로 사용하지 않도록 서브 디렉토리 구분 => 날짜 정보 활용
		LocalDate today = LocalDate.now(); // 현재 시스템의 날짜 정보 생성(2025-11-05)
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // 날짜 포맷 변경
		String subDir = today.format(dtf); // 포맷을 적용한 날짜 문자열 생성
		log.info(">>>>>>>>>>>> subDir : " + subDir);
		
		// 파일 저장 경로에 대한 Path 객체 생성하고 해당 경로를 실제 서버상에 생성
		Path uploadDir = Paths.get(uploadBaseLocation, itemImgLocation, subDir).toAbsolutePath().normalize();
		log.info(">>>>>>>>>>>> uploadDir : " + uploadDir);
		
		// 생성된 Path 객체에 해당하는 디렉토리가 실제 서버상에 존재하지 않을 경우 새로 생성
		if(!Files.exists(uploadDir)) {
			Files.createDirectories(uploadDir); // 하위 경로를 포함한 경로 상의 모든 디렉토리 생성
		}
		// ---------------------------------------------------------------------------------------
		// [ 파일 실제 업로드 처리 ]
		// 임시) 첫번째 이미지를 대표이지미로 생성하기 위한 인덱스 선언
		int index = 1;
		
		// 파일이 저장된 List<MultipartFile> 객체 반복
		for(MultipartFile mFile : itemImgFiles) {
			// 원본 파일명 추출
			String originalFileName = mFile.getOriginalFilename();
			log.info(">>>>>>>>>>>> originalFileName : " + originalFileName);
			
			// 파일 이름 중복 방지 대책(UUID 활용)
			// => UUID 뒤에 "_" 기호화 원본 파일명 결합
			String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
			// => 현재 시각 정보 + 난수 조합 활용도 가능
//			String fileName = System.currentTimeMillis() + new SecureRandom().nextInt(10) + "_" + originalFileName;
			log.info(">>>>>>>>>>>> fileName : " + fileName);
			
			// 디렉토리와 파일명 결합한 새 Path 객체 생성
			// => 기존 경로를 담고 있는 Path 객체의 resolve() 메서드 호출하여 기존 경로에 파일명 추가로 결합
			Path uploadPath = uploadDir.resolve(fileName);
			log.info(">>>>>>>>>>>> uploadPath : " + uploadPath);
			
			// 임시 경로에 보관되어 있는 첨부파일 1개를 실제 경로 상으로 이동
			// => MultipartFile 객체의 transferTo() 메서드 활용
			mFile.transferTo(uploadPath);
			// -------------------------------------------------------------------------
			// ItemImg 엔티티를 파일 갯수만큼 생성하여 List<ItemImg> 객체에 추가
			ItemImg itemImg = new ItemImg();
			itemImg.setItem(item);
			itemImg.setImgName(fileName);
			itemImg.setOriginalImgName(originalFileName);
			itemImg.setImgLocation(itemImgLocation + "/" + subDir); // 상품 서브디렉토리와 날짜 서브디렉토리명 결합
			itemImg.setRepImgYn("N");
			
			// 인덱스가 1인 파일만 대표이미지로 지정
			if(index == 1) {
				itemImg.setRepImgYn("Y");
			} 
			index++;
			
			// 생성된 ItemImg 엔티티를 리스트에 추가
			itemImgList.add(itemImg);
			
		} // 파일 목록 객체에 대한 반복 끝
		
		// ItemImgRepository - saveAll() 메서드 호출하여 첨부파일 리스트를 한꺼번에 INSERT 요청
		itemImgRepository.saveAll(itemImgList);
		
	}
	
	// 파일 다운로드를 위한 파일 정보 조회
	public ItemImgDTO getItemImg(Long itemImgId) {
//		ItemImg itemImg = itemImgRepository.findById(itemImgId)
//				.orElseThrow(() -> new EntityNotFoundException("해당 이미지 파일이 존재하지 않습니다!"));
//		return ItemImgDTO.fromEntity(itemImg);
		
		return ItemImgDTO.fromEntity(itemImgRepository.findById(itemImgId)
				.orElseThrow(() -> new EntityNotFoundException("해당 이미지 파일이 존재하지 않습니다!")));
	}

}
















