package com.hanul.iot;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import common.CommonUtility;
import member.MemberServiceImpl;
import member.MemberVO;
import notice.NoticeServiceImpl;
import notice.NoticeVO;

@Controller
public class NoticeController {
	@Autowired private NoticeServiceImpl service;
	
	//신규공지글 저장처리 요청
	@RequestMapping("/insert.no")
	public String insert(NoticeVO vo) {
		//화면에서 입력한 정보로 DB에 신규저장
		service.notice_insert(vo);
		//응답화면연결 -목록
		return "redirect:list.no";
	}
	
	
	//공지글신규작성화면 요청
	@RequestMapping("/regist.no")
	public String regist() {
		return "notice/regist";
	}
	
	@Autowired private MemberServiceImpl member;
	@Autowired private CommonUtility common;
	
	//공지글목록화면 요청
	@RequestMapping("/list.no")
	public String list(Model model, HttpSession session) {
		// 임의로 관리자로 로그인해 둔다 -----------------
		HashMap<String, String> map = new HashMap<String, String>();
		String id = "admin2";
		map.put("id", id);		
		map.put("pw", "manager");
		String salt = member.member_salt(id);
		map.put("pw", common.getEncrypt(map.get("pw"), salt) );
		
		MemberVO vo = member.member_login( map);
		session.setAttribute("loginInfo", vo);
		//-----------------------------------------
		
		//DB 에서 공지글목록을 조회해온다
		List<NoticeVO> list = service.notice_list();
		
		//조회해온 정보를 화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("list", list);
		return "notice/list";
	}
}
