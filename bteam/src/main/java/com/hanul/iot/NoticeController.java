package com.hanul.iot;

import java.io.File;
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

import common.CommonUtility;
import member.MemberServiceImpl;
import member.MemberVO;
import notice.NoticeServiceImpl;
import notice.NoticeVO;

@Controller
public class NoticeController {
	@Autowired private NoticeServiceImpl service;
	
	//첨부파일 다운로드처리
	@RequestMapping("/download.no")
	public void download(int id, HttpServletRequest request
						, HttpServletResponse response) {
		//선택한 공지글의 첨부파일을 서버에서 클라이언트에 다운로드한다
		NoticeVO vo = service.notice_info(id);
		common.fileDownload( vo.getFilename(), vo.getFilepath()
							, request, response);	
	}
	
	
	//선택한 공지글삭제처리 요청
	@RequestMapping("/delete.no")
	public String delete(int id, HttpServletRequest request) {
		//첨부파일이 있는 공지글은 서버에서 물리적인 파일도 삭제
		NoticeVO vo = service.notice_info(id);
		
		//선택한 공지글정보를  DB에서 삭제한다
		if( service.notice_delete(id) == 1 ) {
			file_delete(vo.getFilepath(), request);
		}
		//목록화면연결
		return "redirect:list.no";
	}
	
	private void file_delete(String filepath, HttpServletRequest request) {
		//http://localhost/iot/upload/notice/2023/03/03/e7227d4f-b3cf-48e8-a560-b7dfc755e76d_2-1.훈련운영계획서.hwp
		//--> "d://app/iot/upload/notice/2023/03/03/abc34y-afdl_abc.txt"
		if( filepath != null ) {
			filepath = filepath.replace( 
				common.appURL(request), "d://app/" + request.getContextPath() );
			File file = new File( filepath );
			if( file.exists() ) file.delete();
		}
	}
	
	
	//선택한 공지글수정저장처리 요청
	@RequestMapping("/update.no")
	public String update(NoticeVO vo, MultipartFile file
						, HttpServletRequest request) {
		NoticeVO notice = service.notice_info( vo.getId() );
		
		//파일 첨부하지 않는 경우
		if( file.isEmpty() ) {
			if( vo.getFilename().isEmpty() ) {				
				//원래 첨부파일 X --> 첨부X
				//원래 첨부파일 O --> 첨부X
				file_delete(notice.getFilepath(), request);
				
			}else {
				//원래 첨부파일 O --> 그대로 사용: 원래 정보로 담아둔다
				vo.setFilename( notice.getFilename() );
				vo.setFilepath( notice.getFilepath() );
			}
		
		}else {
		//파일 첨부하는 경우
		//원래 첨부파일 X --> 첨부
			vo.setFilename( file.getOriginalFilename() );
			vo.setFilepath( common.fileUpload(file, "notice", request) );
			
		//원래 첨부파일 O --> 바꿔 첨부
			file_delete(notice.getFilepath(), request);
		}
		
		//화면에서 변경입력한 정보로 DB에 변경저장한다
		service.notice_update(vo);
		//공지글안내화면연결
		return "redirect:info.no?id="+ vo.getId();
	}
	
	
	
	//선택한 공지글수정화면 요청
	@RequestMapping("/modify.no")
	public String modify(int id, Model model) {
		//선택한 공지글정보를 DB에서 조회해온다
		NoticeVO vo = service.notice_info(id);
		//화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("vo", vo);
		return "notice/modify";
	}
	
	
	
	//선택한 공지글정보화면 요청
	@RequestMapping("/info.no")
	public String info(int id, Model model) {
		//조회수 증가처리
		service.notice_read(id);
		
		model.addAttribute("crlf", "\r\n");		
		model.addAttribute("lf", "\n");		
		
		//선택한 공지글정보를 DB에서 조회한다
		NoticeVO vo = service.notice_info(id);
		//화면에 출력할 수 있도록 Model에 담는다
		model.addAttribute("vo", vo);
		return "notice/info";
	}
	
	//신규공지글 저장처리 요청
	@RequestMapping("/insert.no")
	public String insert(NoticeVO vo, MultipartFile file
						, HttpServletRequest request) {
		//첨부파일이 있는 경우 서버의 물리적영역에 첨부된 파일을 저장
		//첨부된 파일을 물리적으로 어디에, 어떤이름으로 저장했는지를 DB에 저장
		if( ! file.isEmpty() ) {
			vo.setFilename( file.getOriginalFilename() );
			vo.setFilepath( 
					common.fileUpload(file, "notice", request) );
		}
		
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
