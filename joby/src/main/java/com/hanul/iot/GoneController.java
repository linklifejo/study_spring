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

import gone.GoneCommentVO;
import gone.GoneFileVO;
import gone.GonePageVO;
import gone.GoneServiceImpl;
import gone.GoneVO;
import common.CommonUtility;
import member.MemberServiceImpl;
import member.MemberVO;

@Controller
public class GoneController {
	@Autowired private GoneServiceImpl service;
	
	//방명록 새글신규저장처리 요청
	@RequestMapping("/insert.go")
	public String insert(GoneVO vo, MultipartFile[] file
						, HttpServletRequest request) {
		//첨부파일 처리
		if( file.length > 1 ) {
			List<GoneFileVO> list = attached_file(file, request);
			vo.setFileInfo(list);
		}
		//화면에서 입력한 정보로 DB에 신규저장
		service.gone_insert(vo);
		//화면연결
		return "redirect:list.go";
	}
	
	@Autowired private CommonUtility common;
	
	//첨부한 파일정보 관리
	private List<GoneFileVO>  attached_file(MultipartFile[] file
								, HttpServletRequest request) {
		List<GoneFileVO> list = null;
		for( MultipartFile attached : file ) {
			if( attached.isEmpty() ) continue;
			if(list==null) list = new ArrayList<GoneFileVO>();
			GoneFileVO fileVO = new GoneFileVO();
			fileVO.setFilename( attached.getOriginalFilename() );
			fileVO.setFilepath( 
					common.fileUpload(attached, "gone", request) );
			list.add(fileVO);
		}
		return list;
	}
	
	//방명록 첨부파일 다운로드 요청
	@RequestMapping("/download.go")
	public void download(int file
						, HttpServletRequest req
						, HttpServletResponse res) {
		//해당 첨부파일정보를 DB에서 조회해온다
		GoneFileVO vo = service.gone_file_info(file);
		//다운로드 처리한다
		common.fileDownload(vo.getFilename(), vo.getFilepath()
							, req, res);
	}
	
	
	//방명록 글 수정저장처리 요청
	@RequestMapping("/update.go")
	public String update(int id, GonePageVO page, Model model
							, GoneVO vo, String removed
							, HttpServletRequest request
							, MultipartFile[] file) {
		//첨부되어진 파일이 있다면 해당 파일 정보를 저장한다
		List<GoneFileVO> files = attached_file(file, request); //파일목록
		vo.setFileInfo(files);
		
		//화면에서 변경입력한 정보로 DB에 변경저장한다		
		service.gone_update(vo);
		
		//삭제하려는 대상파일정보 조회
		if( ! removed.isEmpty() ) { 
			List<GoneFileVO> remove_files  
				= service.gone_removed_file(removed);
			
			//DB에서 삭제 + 물리적인 파일 삭제
			if( service.gone_file_delete(removed) > 0 ) {
				for(GoneFileVO f : remove_files) {
					common.file_delete(f.getFilepath(), request);
				}
			}
		}
		
		//화면연결 - 정보화면		
		model.addAttribute("url", "info.go");
		model.addAttribute("page", page);
		model.addAttribute("id", id);
		return "gone/redirect";
	}
	
	
	
	//방명록 글 수정화면 요청
	@RequestMapping("/modify.go")
	public String modify(Model model, int id, GonePageVO page) {
		//선택한 글정보를 DB에서 조회한다.
		GoneVO vo = service.gone_info(id);		
		//화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("vo", vo);
		model.addAttribute("page", page);
		return "gone/modify";
	}
	
	
	
	//방명록 글 삭제처리 요청
	@RequestMapping("/delete.go")
	public String delete(int id, GonePageVO page, Model model
							, HttpServletRequest request) {
		//첨부파일정보를 조회해둔다
		List<GoneFileVO> files 
			= service.gone_info(id).getFileInfo();
		
		//선택한 글을 DB에서 삭제한다
		if( service.gone_delete(id)==1 ) {
			//첨부되어진 파일을 물리적으로 저장된 영역에서 삭제한다
			for(GoneFileVO vo : files) {
				common.file_delete(vo.getFilepath(), request);
			}
		}
		
		//응답화면연결 - 목록
		//redirect 화면에서 출력할 정보를 Model에 담는다
		model.addAttribute("url", "list.go");
		model.addAttribute("id", id);
		model.addAttribute("page", page);
		return "gone/redirect";
	}
	
	
	//방명록 새글쓰기화면 요청
	@RequestMapping("/new.go")
	public String gone() {
		return "gone/regist";
	}

	//선택한 방명록 글화면 요청
	@RequestMapping("/info.go")
	public String info(Model model, int id, GonePageVO page) {
		//조회수처리
		service.gone_read(id);
		//선택한 글의 정보를 DB에서 조회해온다
		GoneVO vo = service.gone_info(id);
		//화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("page", page);
		model.addAttribute("vo", vo);
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "gone/info";
	}
	
	//방명록 댓글목록 조회 요청
	@RequestMapping("/gone/comment/list/{id}")
	public String gone_comment_list(@PathVariable int id, Model model) {
		//해당 방명록글에 대한 댓글목록을 DB에서 조회해온다
		List<GoneCommentVO> list = service.gone_comment_list(id);
		//화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("list", list);
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "gone/comment/comment_list";
	}
	
	//방명록 댓글 삭제처리 요청
	@ResponseBody @RequestMapping("/gone/comment/delete/{id}")
	public void gone_comment_delete(@PathVariable int id) {
		//선택한 댓글을 DB에서 삭제
		service.gone_comment_delete(id);
	}
	
	
	//방명록 댓글 변경저장처리 요청
	@ResponseBody @RequestMapping(value="/gone/comment/update"
									, produces="application/text; charset=utf-8")
	public String gone_comment_update(@RequestBody GoneCommentVO vo) {
		return service.gone_comment_update(vo)==1 ? "성공" : "실패";
	}
	
	//방명록 댓글 저장처리 요청
	@ResponseBody @RequestMapping("/gone/comment/insert")
	public boolean gone_comment_regist(GoneCommentVO vo) {
		//화면에서 입력한 댓글정보로 DB에 신규저장
		return service.gone_comment_regist(vo)==1 ? true : false;
	}
	
	@Autowired private MemberServiceImpl member;
	//방명록 목록화면 요청
	@RequestMapping("/list.go")
	public String gone(Model model, HttpSession session
						, GonePageVO page) {
		// 임의로 관리자로 로그인해 둔다 -----------------
		HashMap<String, String> map = new HashMap<String, String>();
		String id = "1111";
		map.put("id", id);		
		map.put("pw", "1111");
		String salt = member.member_salt(id);
		map.put("pw", common.getEncrypt(map.get("pw"), salt) );
		
		MemberVO vo = member.member_login( map);
		//session.setAttribute("loginInfo", vo);
		//-----------------------------------------
		
		session.setAttribute("category", "bo");
		//DB에서 방명록 목록을 조회해온다
		page = service.gone_list(page);		
		//화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("page", page);
		return "gone/list";
	}
}
