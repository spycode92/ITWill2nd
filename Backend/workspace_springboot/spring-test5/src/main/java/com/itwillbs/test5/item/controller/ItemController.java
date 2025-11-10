package com.itwillbs.test5.item.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.itwillbs.test5.item.dto.ItemDTO;
import com.itwillbs.test5.item.dto.ItemImgDTO;
import com.itwillbs.test5.item.service.ItemImgService;
import com.itwillbs.test5.item.service.ItemService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/items")
@Log4j2
public class ItemController {
	// 파일 업로드에 사용할 경로를 properties 파일에서 가져오기
	// => 변수 선언부에 @Value("${프로퍼티속성명}") 형태로 선언
	@Value("${file.uploadBaseLocation}")
	private String uploadBaseLocation;
	
	@Value("${file.itemImgLocation}")
	private String itemImgLocation;
	
	private final ItemService itemService;
	private final ItemImgService itemImgService;

	public ItemController(ItemService itemService, ItemImgService itemImgService) {
		this.itemService = itemService;
		this.itemImgService = itemImgService;
	}
	
	// =============================================================================
	@GetMapping("/regist")
	public String registForm(Model model) {
		model.addAttribute("itemDTO", new ItemDTO());
		return "/item/item_regist_form";
	}
	
	@PostMapping("/regist")
	public String registItem(@ModelAttribute("itemDTO") @Valid ItemDTO itemDTO, BindingResult bindingResult, Model model,
			@RequestParam("itemImgFiles") List<MultipartFile> itemImgFiles) throws IOException {
		// 기본 상품 정보 입력값 검증
//		log.info(">>>>>>>>>>>>>>>> bindingResult.hasErrors() : " + bindingResult.hasErrors());
//		log.info(">>>>>>>>>>>>>>>> bindingResult.getAllErrors() : " + bindingResult.getAllErrors());
		
		if(bindingResult.hasErrors()) {
			return "/item/item_regist_form";
		}
		// ------------------------------------------------
		// 이미지 파일을 하나도 선택하지 않았을 경우
		// => 주의! 무조건 List 및 List 첫번째 요소로 MultipartFile 객체는 생성되기 때문에 size() 등으로 파일 업로드 여부 판별 불가
//		log.info(">>>>>>>>>>>>>>>> itemImgFiles.size : " + itemImgFiles.size());
//		log.info(">>>>>>>>>>>>>>>> itemImgFiles.get(0) : " + itemImgFiles.get(0));
//		log.info(">>>>>>>>>>>>>>>> itemImgFiles.get(0).isEmpty() : " + itemImgFiles.get(0).isEmpty());
		if(itemImgFiles.get(0).isEmpty()) { // 첫번째 파일이 비어있는지 판별
			model.addAttribute("errorMessage", "최소 한 개 이상의 파일 선택 필수!");
			return "/item/item_regist_form";
		}
		// -------------------------------------------------------------------------
		// ItemService - registItem() 메서드 호출하여 상품정보 등록 요청
		// => 파라미터 : ItemDTO 객체, 파일 목록 저장된 List<MultipartFile> 객체
		//    리턴타입 : 상품번호 타입(Long)
		Long itemId = itemService.registItem(itemDTO, itemImgFiles);
//		log.info(">>>>>>>>>>>>>>>> itemId : " + itemId);
		
		// =========================================================================
		// 상품 등록 완료 시 리턴받은 상품아이디(itemId) 값을 "/items" 경로에 결합하여
		// 상품 상세정보 조회 페이지로 리다이렉트
		return "redirect:/items/" + itemId;
	}
	// =============================================================================
	// "/items"(GET) 요청에 대한 상품 목록 조회 뷰페이지 포워딩
	@GetMapping("")
	public String getItemListForm() {
		// ItemService - getItemList() 메서드 호출하여 상품 목록 조회 요청
		// => 파라미터 : 없음   리턴타입 : List<ItemDTO>
		return "/item/item_list_with_toast_grid";
	}
	
	// AJAX 활용한 상품 목록 조회 요청
	// Toast Grid 를 사용하여 그리드 형식 목록 출력하기 위해 목록 객체를 JSON 으로 변환하여 리턴
	@ResponseBody
	@GetMapping("/itemList")
	public List<ItemDTO> getItemList() {
		// ItemService - getItemList() 메서드 호출하여 상품 목록 조회 요청
		// => 파라미터 : 없음   리턴타입 : List<ItemDTO>
		return itemService.getItemList();
	}
	
	// =============================================================================
	// "/items/xxx" 요청에 대한 상품 상세정보 조회
	// => xxx 부분은 경로변수 itemId 로 처리
	@GetMapping("/{itemId}")
	public String itemDetail(@PathVariable("itemId") Long itemId, Model model) {
		ItemDTO itemDTO = itemService.getItem(itemId);
//		log.info(">>>>>>>>>>>>>>> 상품정보 : " + itemDTO);
		
		model.addAttribute("itemDTO", itemDTO);
		return "/item/item_detail";
	}
	
	// =============================================================================
	// "/items/xxx" 요청에 대한 상품 상세정보 조회
	// => xxx 부분은 경로변수 itemId 로 처리
	@DeleteMapping("/{itemId}")
	public String itemDelete(@PathVariable("itemId") Long itemId, Model model) {
//		log.info(">>>>>>>>>>>>>>> delete itemId : " + itemId);
		return "/item/item_detail";
	}
	
	// =============================================================================
	// 파일 다운로드 요청에 대한 처리(상품 이미지 아이디 경로변수 처리)
	/*
	 * 상품 이미지 다운로드를 서버측에서 직접 처리하기 위해 리턴타입을 ResponseEntity<Resource> 타입으로 지정
	 * => ResponseEntity<T> 는 스프링MVC 에서 HTTP 응답을 직접 제어할 수 있도록 해주는 클래스
	 * => 단순히 뷰 이름을 반환하는 것과 달리 HTTP 상태코드나 헤더, 바디 등을 모두 포함하여 응답 가능
	 *    (바디에 실제 데이터를 포함할 수 있으며 데이터타입은 T 이다)
	 * 
	 * [ Resource(org.springframework.core.io) ] 
	 * => 스프링에서 파일, 클래스패스 자원, URL, 입력스트림 등 다양한 외부 자원을 추상화하여 제공
	 * => 주로, 파일을 직접 읽어서 스트림으로 전송할 때 사용함
	 * => FileSystemResource, ClassPathResource 등의 구현체를 사용함
	 * => 실제 파일을 Resource 타입으로 감싼 후 ResponseEntity<Resource> 타입으로 HTTP 응답 정보를 구성하여 응답 처리함
	 */
	@GetMapping("/download/{itemImgId}")
	public ResponseEntity<Resource> getFile(@PathVariable("itemImgId") Long itemImgId) {
//		log.info(">>>>>>>>>>>>>>> 상품 이미지 아이디 : " + itemImgId);
		
		// ItemImgService - getItemImg() 메서드 호출하여 상품 이미지 1개 정보 조회 요청
		// => 파라미터 : 상품이미지번호(itemImgId)   리턴타입 : ItemImgDTO(itemImgDTO)
		ItemImgDTO itemImgDTO = itemImgService.getItemImg(itemImgId);
//		log.info(">>>>>>>>>>>>>>> 다운로드 할 이미지 정보 : " + itemImgDTO);
		// ----------------------------------------------------------------------------
		try {
			// [ 파일 다운로드 처리 ]
			// => ResponseEntity<Resource> 타입 객체를 활용하여 HTTP 응답 데이터로 처리
			// 1) 다운로드 할 파일 정보를 서버 상의 실제 업로드 디렉토리를 활용하여 가져오기
			Path path = Paths.get(uploadBaseLocation, itemImgDTO.getImgLocation()) // 기본 경로와 파일별 상세경로를 결합하여 Path 객체 생성
							.resolve(itemImgDTO.getImgName()) // 디렉토리에 실제 파일명 결합(get() 메서드 파라미터에 추가로 기술해도 됨)
							.normalize();
//			log.info(">>>>>>>>>>>>>>> path : " + path);
			
			// 2) 경로 및 파일 존재 여부 판별하고 실제로 파일에 접근 가능한지 여부도 확인
			if(Files.notExists(path)) { // 경로 및 파일이 존재하지 않을 경우
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "다운로드 할 파일이 존재하지 않습니다!");
			} else if(!Files.isReadable(path)) { // 경로 및 파일은 존재하나, 파일 읽기 권한이 없는 경우
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "파일 접근 권한이 없습니다!");
			}
			
			// 3) 다운로드 할 파일 객체에 대한 Resource 객체 생성(파일을 Resource 객체로 포장)
			// => 스프링의 Resource 인터페이스 구현체 중 URL 기반 리소스 추상화를 제공하는 UrlResource 객체 활용(지정한 경로상의 자원 관리)
			//    로컬 파일만 처리 가능한 것이 아니라 HTTP, FTP 등의 원격 리소스도 다룰 수 있다!
			Resource resource = new UrlResource(path.toUri()); // MalformedURLException 예외 처리 필요
			
			// 4) 파일의 MIME 타입(= 컨텐츠 타입) 설정
			// 4-1) 실제 파일로부터 타입 알아내기
			String contentType = Files.probeContentType(path); // IOException 예외 처리 필요
//			log.info(">>>>>>>>>>>>>>> contentType : " + contentType);
			
			// 4-2) 컨텐츠 타입 조회 실패 시 기본 타입을 일반적인 바이너리 파일 타입으로 강제 고정
			if(contentType == null) {
//				contentType = "application/octet-stream";
				// 스프링의 라이브러리 중 컨텐츠 타입(MIME)을 enum 처럼 관리하는 MediaType 클래스 제공
				contentType = MediaType.APPLICATION_OCTET_STREAM.toString();
//				log.info(">>>>>>>>>>>>>>> 컨텐츠 타입 식별 불가로 기본값 설정");
			}
//			log.info(">>>>>>>>>>>>>>> contentType2 : " + contentType);
			
			// -----------------------------------------------------------
			// 한글, 공백 등의 포함된 파일은 별도의 처리 필요
			// -----------------------------------------------------------
			// 5) org.springframework.http 패키지의 ContentDisposition 객체 활용(참고. 스프링 5.2 부터 사용 가능하며, IE 11 이전 버전 브라우저 인식 불가)
			ContentDisposition contentDisposition = ContentDisposition.builder("attachment") // 첨부파일 다운로드 형식을 위한 ContentDisposition 객체 선언(빌더패턴 활용)
					.filename(itemImgDTO.getOriginalImgName(), StandardCharsets.UTF_8) // 파일명에 대한 인코딩 방식을 UTF-8 타입으로 설정
					.build(); // 객체 생성
//			log.info(">>>>>>>>>>>>>>> contentDisposition : " + contentDisposition);
			
			// 6) 응답 데이터 리턴
			// => ResponseEntity<Resource> 타입으로 리턴
			return ResponseEntity.ok() // 정상적인 응답(HTTP 200) 설정
					.contentType(MediaType.parseMediaType(contentType)) // 파일로부터 확인된 미디어 타입명을 파싱
					.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString()) // 헤더 정보에 ContentDisposition 헤더값 설정(문자열로 변환)
					.body(resource); // 본문 데이터는 파일을 감싼 Resource 객체 전달 
									 // => 리턴타입이 ResponseEntity<T> -> ResponseEntity<Resource> 로 변경됨
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "다운로드 할 파일이 존재하지 않습니다!");
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 타입 식별 에러!");
		}
		
		
	}
	
}











