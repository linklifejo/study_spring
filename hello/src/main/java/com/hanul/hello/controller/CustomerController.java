package com.hanul.hello.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanul.hello.domain.Customer;
import com.hanul.hello.repository.CustomerRepository;

@Controller @RequestMapping("/customer")
public class CustomerController {
	@Autowired private CustomerRepository repository;
	
	//신규고객등록처리 요청
	@RequestMapping("/insert")
	public String insert(Customer vo){
		repository.save(vo);
		return "redirect:list";
	}
	
	//신규고객등록화면요청
	@RequestMapping("/new")
	public String customer() {
		return "customer/new";
	}
	
	//선택한고객정보 삭제처리요청
	@RequestMapping("/delete")
	public String delete(int id) {
//		Customer vo = repository.findByCid(id);
//		repository.delete(vo);
		repository.deleteById(id);
		return "redirect:list";
	}
	
	
	//선택한고객정보 수정저장처리요청
	@RequestMapping("/update")
	public String update( Customer vo ) {		
		repository.save(vo);
		return "redirect:info/" + vo.getCid();
	}
	
	//선택한고객정보 수정화면요청
	@RequestMapping("/modify")
	public String modify(int id, Model model) {
		Customer vo = repository.findByCid(id);
		model.addAttribute("vo", vo);
		return "customer/modify";
	}
	
	
	//선택한고객정보 화면요청
	@RequestMapping("/info/{id}")
	public String info(@PathVariable int id, Model model) {
//		Optional<Customer> vo =  repository.findById(id);
//		Customer customer = vo.isPresent() ? vo.get() : null;
		Customer customer = repository.findById(id).orElse(null);
		model.addAttribute("vo", customer);
		return "customer/info";
	}
	
	
	//고객목록화면요청
	@RequestMapping("/list")
	public String list(Model model, @RequestParam(defaultValue = "") String name) {
		//List<Customer> list = repository.findAll();
		//List<Customer> list = repository.findByOrderByCidDesc();
		List<Customer> list = null;
		if( name.isEmpty() ) {
			list = repository.findByOrderByNameAsc();
		}else {
			list = repository.findByNameContainingOrderByName(name);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("name", name);
		return "customer/list";
	}
}
