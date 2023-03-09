package com.hanul.iot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import member.MemberServiceImpl;
import member.MemberVO;

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
	
	//방명록 첨부파일 다운로드 요청
	@RequestMapping("/download.bo")
	public void download(int file
						, HttpServletRequest req
						, HttpServletResponse res) {
		//해당 첨부파일정보를 DB에서 조회해온다
		BoardFileVO vo = service.board_file_info(file);
		//다운로드 처리한다
		common.fileDownload(vo.getFilename(), vo.getFilepath()
							, req, res);
	}
	
	
	//방명록 새글쓰기화면 요청
	@RequestMapping("/new.bo")
	public String board() {
		return "board/regist";
	}

	//선택한 방명록 글화면 요청
	@RequestMapping("/info.bo")
	public String info(Model model, int id, BoardPageVO page) {
		//조회수처리
		service.board_read(id);
		//선택한 글의 정보를 DB에서 조회해온다
		BoardVO vo = service.board_info(id);
		//화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("page", page);
		model.addAttribute("vo", vo);
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "board/info";
	}
	
	
	
	@Autowired private MemberServiceImpl member;
	//방명록 목록화면 요청
	@RequestMapping("/list.bo")
	public String board(Model model, HttpSession session
						, BoardPageVO page) {
		// 임의로 관리자로 로그인해 둔다 -----------------
		HashMap<String, String> map = new HashMap<String, String>();
		String id = "hong2023";
		map.put("id", id);		
		map.put("pw", "Hong2023");
		String salt = member.member_salt(id);
		map.put("pw", common.getEncrypt(map.get("pw"), salt) );
		
		MemberVO vo = member.member_login( map);
		session.setAttribute("loginInfo", vo);
		//-----------------------------------------
		
		session.setAttribute("category", "bo");
		//DB에서 방명록 목록을 조회해온다
		page = service.board_list(page);		
		//화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("page", page);
		return "board/list";
	}
}
