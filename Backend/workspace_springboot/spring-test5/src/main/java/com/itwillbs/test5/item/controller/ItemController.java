package com.itwillbs.test5.item.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itwillbs.test5.item.dto.ItemDTO;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Controller
@RequestMapping("/items")
@Log4j2
public class ItemController {

	@GetMapping("/regist")
	public String registForm(Model model) {
		model.addAttribute("itemDTO", new ItemDTO());
		return "/item/item_regist_form";
	}
	
	@PostMapping("/regist")
	public String registItem(@ModelAttribute("itemDTO") @Valid ItemDTO itemDTO, BindingResult bindingResult, Model model,
			@RequestParam("itemImgFiles") List<MultipartFile> itemImgFiles) {
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
		
		return "redirect:/";
	}
	
	
	
}











