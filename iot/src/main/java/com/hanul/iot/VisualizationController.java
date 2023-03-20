package com.hanul.iot;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VisualizationController {

	//시각화화면 요청
	@RequestMapping("/visual/list")
	public String list(HttpSession session) {
		session.setAttribute("category", "vi");
		return "visual/list";
	}
}
