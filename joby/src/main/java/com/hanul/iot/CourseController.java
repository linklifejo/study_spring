package com.hanul.iot;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import course.CourseVO;
import location.LocationVO;
import course.CourseServiceImpl;

@Controller
public class CourseController {
	@Autowired private CourseServiceImpl service;
		
	//신규고객등록처리 요청
	@RequestMapping("/insert.co")
	public String insert(CourseVO vo) {
		//화면에서 입력한 정보로  DB에 신규삽입저장한다.
		service.course_insert(vo);
		//응답화면연결 - 고객목록
		return "redirect:list.co";
	}
	
	
	//신규고객등록화면 요청
	@RequestMapping("/new.co")
	public String course(Model model) {
		List<LocationVO> list = service.location_list();
		model.addAttribute("list", list);
		return "course/new";
	}
	
	//선택한 고객정보 수정저장처리 요청
	@RequestMapping("/update.co")
	public String update(CourseVO vo) {
		//화면에서 변경입력한 정보를 DB에 변경저장한다
		service.course_update(vo);
		//응답화면연결-고객정보
		return "redirect:info.co?id=" + vo.getId();
	}
	
	
	
	//선택한 고객정보 수정화면 요청
	@RequestMapping("/modify.co")
	public String modify(Model model, int id) {
		//선택한 고객정보를 DB에서 조회해와
		CourseVO vo = service.course_info(id);
		List<LocationVO> list = service.location_list();
		//고객수정화면에서 조회한 정보를 출력할 수 있도록 Model에 담는다
		model.addAttribute("vo", vo);
		model.addAttribute("list", list);
		//응답화면연결 - 고객수정
		return "course/modify";
	}
	
	
	//선택한 고객정보 삭제처리 요청
	@RequestMapping("/delete.co")
	public String delete(int id) {
		//선택한 고객정보를 DB에서 삭제
		service.course_delete(id);
		//응답화면연결 - 고객목록
		return "redirect:list.co";
	}
	
	
	//선택한 고객정보화면 요청
	@RequestMapping("/info.co")
	public String info(int id, Model model) {
		//해당 고객정보를 DB에서 조회해온다
		CourseVO vo = service.course_info(id);
		//화면에 출력할 수 있도록 Model에 attribute로 담는다
		model.addAttribute("vo", vo);
		
		//응답화면연결
		return "course/info";
	}
	
	
	//고객목록화면 요청
	@RequestMapping("/list.co")
	public String list(Model model, HttpSession session ) {
		session.setAttribute("category", "cu");
		List<CourseVO> list = service.course_list();
		model.addAttribute("list", list);
		return "course/list";
	}
}
