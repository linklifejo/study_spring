package com.hanul.iot;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {

	//방명록 목록화면 요청
	@RequestMapping("/list.bo")
	public String board(Model model, HttpSession session) {
		session.setAttribute("category", "bo");
		//DB에서 방명록 목록을 조회해온다
		//화면에 출력할 수 있도록 Model에 담는다
		return "board/list";
	}
}
