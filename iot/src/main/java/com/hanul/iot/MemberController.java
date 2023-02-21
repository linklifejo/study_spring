package com.hanul.iot;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import member.MemberServiceImpl;
import member.MemberVO;

@Controller
public class MemberController {
	@Autowired private MemberServiceImpl service;
	
	//로그인처리 요청
	@ResponseBody @RequestMapping("/iotLogin")
	public boolean login(String id, String pw, HttpSession session) {
		//화면에서 입력한 아이디/비번이 일치하는 회원정보를 조회해온다
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("pw", pw);
		MemberVO vo = service.member_login(map);
		session.setAttribute("loginInfo", vo);
		return vo==null ? false : true;
	}
	
	//로그인화면 요청
	@RequestMapping("/login")
	public String login() {
		return "default/member/login";
	}
}
