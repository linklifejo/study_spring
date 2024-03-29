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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import board.BoardCommentVO;
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
	
	
	//방명록 글 수정저장처리 요청
	@RequestMapping("/update.bo")
	public String update(int id, BoardPageVO page, Model model
							, BoardVO vo, String removed
							, HttpServletRequest request
							, MultipartFile[] file) {
		//첨부되어진 파일이 있다면 해당 파일 정보를 저장한다
		List<BoardFileVO> files = attached_file(file, request); //파일목록
		vo.setFileInfo(files);
		
		//화면에서 변경입력한 정보로 DB에 변경저장한다		
		service.board_update(vo);
		
		//삭제하려는 대상파일정보 조회
		if( ! removed.isEmpty() ) { 
			List<BoardFileVO> remove_files  
				= service.board_removed_file(removed);
			
			//DB에서 삭제 + 물리적인 파일 삭제
			if( service.board_file_delete(removed) > 0 ) {
				for(BoardFileVO f : remove_files) {
					common.file_delete(f.getFilepath(), request);
				}
			}
		}
		
		//화면연결 - 정보화면		
		model.addAttribute("url", "info.bo");
		model.addAttribute("page", page);
		model.addAttribute("id", id);
		return "board/redirect";
	}
	
	
	
	//방명록 글 수정화면 요청
	@RequestMapping("/modify.bo")
	public String modify(Model model, int id, BoardPageVO page) {
		//선택한 글정보를 DB에서 조회한다.
		BoardVO vo = service.board_info(id);		
		//화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
		return "board/modify";
	}
	
	
	
	//방명록 글 삭제처리 요청
	@RequestMapping("/delete.bo")
	public String delete(int id, BoardPageVO page, Model model
							, HttpServletRequest request) {
		//첨부파일정보를 조회해둔다
		List<BoardFileVO> files 
			= service.board_info(id).getFileInfo();
		
		//선택한 글을 DB에서 삭제한다
		if( service.board_delete(id)==1 ) {
			//첨부되어진 파일을 물리적으로 저장된 영역에서 삭제한다
			for(BoardFileVO vo : files) {
				common.file_delete(vo.getFilepath(), request);
			}
		}
		
		//응답화면연결 - 목록
		//redirect 화면에서 출력할 정보를 Model에 담는다
		model.addAttribute("url", "list.bo");
		model.addAttribute("id", id);
		model.addAttribute("page", page);
		return "board/redirect";
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
	
	//방명록 댓글목록 조회 요청
	@RequestMapping("/board/comment/list/{id}")
	public String board_comment_list(@PathVariable int id, Model model) {
		//해당 방명록글에 대한 댓글목록을 DB에서 조회해온다
		List<BoardCommentVO> list = service.board_comment_list(id);
		//화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("list", list);
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "board/comment/comment_list";
	}
	
	//방명록 댓글 삭제처리 요청
	@ResponseBody @RequestMapping("/board/comment/delete/{id}")
	public void board_comment_delete(@PathVariable int id) {
		//선택한 댓글을 DB에서 삭제
		service.board_comment_delete(id);
	}
	
	
	//방명록 댓글 변경저장처리 요청
	@ResponseBody @RequestMapping(value="/board/comment/update"
									, produces="application/text; charset=utf-8")
	public String board_comment_update(@RequestBody BoardCommentVO vo) {
		return service.board_comment_update(vo)==1 ? "성공" : "실패";
	}
	
	//방명록 댓글 저장처리 요청
	@ResponseBody @RequestMapping("/board/comment/insert")
	public boolean board_comment_regist(BoardCommentVO vo) {
		//화면에서 입력한 댓글정보로 DB에 신규저장
		return service.board_comment_regist(vo)==1 ? true : false;
	}
	
	@Autowired private MemberServiceImpl member;
	//방명록 목록화면 요청
	@RequestMapping("/list.bo")
	public String board(Model model, HttpSession session
						, BoardPageVO page) {
		// 임의로 관리자로 로그인해 둔다 -----------------
		HashMap<String, String> map = new HashMap<String, String>();
		String id = "park2023";
		map.put("id", id);		
		map.put("pw", "Park2023");
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
