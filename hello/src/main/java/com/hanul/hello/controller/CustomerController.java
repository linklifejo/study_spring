package com.hanul.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller @RequestMapping("/customer")
public class CustomerController {

	//고객목록화면요청
	@RequestMapping("/list")
	public String list(Model model) {
		
		model.addAttribute("list", "");
		return "customer/list";
	}
}
