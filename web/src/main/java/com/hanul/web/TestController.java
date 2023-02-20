package com.hanul.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import member.MemberVO;

@Controller
public class TestController {

	//로그인처리 요청
	@RequestMapping("/login_result")
	public String login(String id, String pw) {
		//id: admin, pw: 1234 인 경우 로그인되게
		//로그인 성공: home.jsp 로 연결
		//로그인 실패: login.jsp 로 연결
		if( id.equals("admin") && pw.equals("1234") ) {
			//return "home"; //화면연결 forward
			return "redirect:/"; //화면연결 redirect
		}else {
			//return "member/login"; //화면연결 forward
			return "redirect:login"; //화면연결 redirect
		}
		
	}
	
	//로그인화면 요청
	@RequestMapping("/login")
	public String login() {
		return "member/login";
	}
	
	//회원가입정보 접근 - 경로
	@RequestMapping("/joinPath/{name}/{g}/{email}")
	public String member(@PathVariable String name
			, @PathVariable("g") String gender
			, Model model
			, @PathVariable String email
			) {
		model.addAttribute("gender", gender);
		model.addAttribute("email", email);
		model.addAttribute("name", name);
		model.addAttribute("method", "@PathVariable방식");
		return "member/info";
	}
	
	//회원가입정보 접근 - 데이터객체
	@RequestMapping("/joinData")
	public String member(MemberVO vo, Model model) {
		model.addAttribute("vo", vo);
		model.addAttribute("method", "데이터객체");
		return "member/info";
	}
	
	//회원가입정보 접근 - @RequestParam
	@RequestMapping("/joinParam")
	public String member(@RequestParam("name") String id
						, @RequestParam String gender
						, String email
						, Model model
						) {
		
		model.addAttribute("name", id);
		model.addAttribute("gender", gender);
		model.addAttribute("email", email);
		model.addAttribute("method", "@RequestParam 방식");
		
		//응답화면
		return "member/info"; 
	}
	
	
	//회원가입정보 접근 - HttpServletRequest
//	@RequestMapping(value="/joinRequest"
//				, method = { RequestMethod.POST, RequestMethod.GET })
	@RequestMapping("/joinRequest")
	public String member(HttpServletRequest request, Model model) {
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		
		model.addAttribute("email", email);
		model.addAttribute("gender", gender);
		model.addAttribute("name", name);
		model.addAttribute("method", "HttpServletRequest");
		//응답화면
		return "member/info";
	}
	
	@RequestMapping("/member")
	public String member() { //회원가입화면 요청
		return "member/join";//응답화면
	}
	
	@RequestMapping("/second")
	public ModelAndView test() {
		ModelAndView model = new ModelAndView();
		String now 
		= new SimpleDateFormat("hh시 mm분 ss초").format(new Date());
		model.addObject("now", now);
		model.setViewName("view");
		return model;
	}
	
	@RequestMapping("/first")
	public String abc(Model model) {
		Date date = new Date();
		String today = new SimpleDateFormat("yyyy년 MM월 dd일")
						.format(date);
		model.addAttribute("today", today);
		return "view";
	}
}
