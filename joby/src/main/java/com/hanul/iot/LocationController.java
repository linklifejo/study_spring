package com.hanul.iot;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import location.LocationServiceImpl;
import location.LocationVO;

@Controller
public class LocationController {
	@Autowired private LocationServiceImpl service;
		
	//신규고객등록처리 요청
	@RequestMapping("/insert.lo")
	public String insert(LocationVO vo) {
		//화면에서 입력한 정보로  DB에 신규삽입저장한다.
		service.location_insert(vo);
		//응답화면연결 - 고객목록
		return "redirect:list.lo";
	}
	
	
	//신규고객등록화면 요청
	@RequestMapping("/new.lo")
	public String location() {
		return "location/new";
	}
	
	//선택한 고객정보 수정저장처리 요청
	@RequestMapping("/update.lo")
	public String update(LocationVO vo) {
		//화면에서 변경입력한 정보를 DB에 변경저장한다
		service.location_update(vo);
		//응답화면연결-고객정보
		return "redirect:info.lo?id=" + vo.getId();
	}
	
	
	
	//선택한 고객정보 수정화면 요청
	@RequestMapping("/modify.lo")
	public String modify(Model model, int id) {
		//선택한 고객정보를 DB에서 조회해와
		LocationVO vo = service.location_info(id);
		//고객수정화면에서 조회한 정보를 출력할 수 있도록 Model에 담는다
		model.addAttribute("vo", vo);
		//응답화면연결 - 고객수정
		return "location/modify";
	}
	
	
	//선택한 고객정보 삭제처리 요청
	@RequestMapping("/delete.lo")
	public String delete(int id) {
		//선택한 고객정보를 DB에서 삭제
		service.location_delete(id);
		//응답화면연결 - 고객목록
		return "redirect:list.lo";
	}
	
	
	//선택한 고객정보화면 요청
	@RequestMapping("/info.lo")
	public String info(int id, Model model) {
		//해당 고객정보를 DB에서 조회해온다
		LocationVO vo = service.location_info(id);
		//화면에 출력할 수 있도록 Model에 attribute로 담는다
		model.addAttribute("vo", vo);
		
		//응답화면연결
		return "location/info";
	}
	
	
	//고객목록화면 요청
	@RequestMapping("/list.lo")
	public String list(Model model, HttpSession session ) {
		session.setAttribute("category", "lo");
		List<LocationVO> list = service.location_list();
		model.addAttribute("list", list);
		return "location/list";
	}
}
