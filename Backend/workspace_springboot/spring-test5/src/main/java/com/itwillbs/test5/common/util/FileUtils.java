package com.itwillbs.test5.common.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itwillbs.test5.item.dto.ItemImgDTO;

import lombok.extern.log4j.Log4j2;

// 파일 업로드 처리 작업을 모듈화 한 클래스
@Component
@Log4j2
public class FileUtils {
	// 파일 업로드에 사용할 경로를 properties 파일에서 가져오기
	// => 변수 선언부에 @Value("${프로퍼티속성명}") 형태로 선언
	@Value("${file.uploadBaseLocation}")
	private String uploadBaseLocation;
	
	@Value("${file.itemImgLocation}")
	private String itemImgLocation;
	
	// ======================================================================
	// 실제 서버상에 업로드 된 파일 제거(단일 파일 삭제)
	public void deleteFile(ItemImgDTO itemImgDTO) {
		log.info(">>>>>>>>>>>>>> uploadBaseLocation : " + uploadBaseLocation);
		log.info(">>>>>>>>>>>>>> itemImgLocation : " + itemImgLocation);
		
		Path path = Paths.get(uploadBaseLocation, itemImgDTO.getImgLocation()) // 기본 경로와 파일별 상세경로를 결합하여 Path 객체 생성
				.resolve(itemImgDTO.getImgName()) // 디렉토리에 실제 파일명 결합(get() 메서드 파라미터에 추가로 기술해도 됨)
				.normalize();
		
		// Files 클래스의 deleteIfExists() 메서드 호출하여 해당 파일이 서버상에 존재할 경우 삭제 처리
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 실제 서버상에 업로드 된 파일 제거(다중 파일 삭제)
	public void deleteFiles(List<ItemImgDTO> itemImgDTOList) {
		// deleteFile() 메서드 재사용하기 위하여 반복문을 통해 리스트 내의 ItemImgDTO 객체를 파라미터로 전달
		for(ItemImgDTO itemImgDTO : itemImgDTOList) {
			deleteFile(itemImgDTO);
		}
	}
	
}























