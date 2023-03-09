package com.hanul.iot;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import board.BoardFileVO;
import board.BoardPageVO;
import board.BoardServiceImpl;
import board.BoardVO;
import common.CommonUtility;

@Controller
public class BoardController {
	@Autowired private BoardServiceImpl service;
	
	//방명록 새글신규저장처리 요청
	@RequestMapping("/insert.bo")
	public String insert(BoardVO vo, MultipartFile[] file
						, HttpServletRequest request) {
		//첨부파일 처리
		if( file.length > 1 ) {
			List<BoardFileVO> list = attached_file(file, request);
			vo.setFileInfo(list);
		}
		//화면에서 입력한 정보로 DB에 신규저장
		service.board_insert(vo);
		//화면연결
		return "redirect:list.bo";
	}
	
	@Autowired private CommonUtility common;
	
	//첨부한 파일정보 관리
	private List<BoardFileVO>  attached_file(MultipartFile[] file
								, HttpServletRequest request) {
		List<BoardFileVO> list = null;
		for( MultipartFile attached : file ) {
			if( attached.isEmpty() ) continue;
			if(list==null) list = new ArrayList<BoardFileVO>();
			BoardFileVO fileVO = new BoardFileVO();
			fileVO.setFilename( attached.getOriginalFilename() );
			fileVO.setFilepath( 
					common.fileUpload(attached, "board", request) );
			list.add(fileVO);
		}
		return list;
	}
	
	//방명록 새글쓰기화면 요청
	@RequestMapping("/new.bo")
	public String board() {
		return "board/regist";
	}

	//방명록 목록화면 요청
	@RequestMapping("/list.bo")
	public String board(Model model, HttpSession session
						, BoardPageVO page) {
		session.setAttribute("category", "bo");
		//DB에서 방명록 목록을 조회해온다
		page = service.board_list(page);		
		//화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("page", page);
		return "board/list";
	}
}
