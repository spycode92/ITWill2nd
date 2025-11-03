package com.itwillbs.test5.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String registItem(@ModelAttribute("itemDTO") @Valid ItemDTO itemDTO, BindingResult bindingResult, Model model) {
		log.info(">>>>>>>>>>>>>>>> bindingResult.hasErrors() : " + bindingResult.hasErrors());
		log.info(">>>>>>>>>>>>>>>> bindingResult.getAllErrors() : " + bindingResult.getAllErrors());
		
		if(bindingResult.hasErrors()) {
			return "/item/item_regist_form";
		}
		
		
		return "redirect:/";
	}
	
	
	
}











