package com.hanul.app.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hanul.app.dao.AnDao;
import com.hanul.app.dto.MemberDTO;

@Controller
public class maincontroller {	
	
	Gson gson = new Gson();
	
	@RequestMapping(value = "/anJoin", method = {RequestMethod.GET, RequestMethod.POST} )
	public String anJoin(HttpServletRequest req, MultipartRequest mReq, Model model) {
		System.out.println("anJoin() 실행됨");
		
		// 서버<->클라이언트(안드)
		// 통신을할때 데이터 이동을 String으로 함 
		
		// 데이터 받는 부분
		String data = (String) req.getParameter("param");
		System.out.println("data : " + data);
		
		MemberDTO dto = new Gson().fromJson(data, MemberDTO.class);
		
		//2. 한번 찍어본다
		System.out.println(dto.getId());
		System.out.println(dto.getPw());
		System.out.println(dto.getName());
		System.out.println(dto.getPhone());
		System.out.println(dto.getAddress());	
		
		// 사진 받는 부분
		MultipartFile file = mReq.getFile("file");
		String fileName = "";
		if(file != null) {
			fileName = file.getOriginalFilename();
			System.out.println(fileName);
			fileName = "My" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
			System.out.println(fileName);
			// 디렉토리 존재하지 않으면 생성
			makeDir(req);	
				
			if(file.getSize() > 0){			
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/");
				
				System.out.println( fileName + " : " + realImgPath);
				System.out.println( "fileSize : " + file.getSize());					
												
			 	try {
			 		// 이미지파일 저장
					file.transferTo(new File(realImgPath, fileName));										
				} catch (Exception e) {
					e.getMessage();
				} 
									
			}else{
				// 이미지파일 실패시
				fileName = "FileFail.jpg";
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
						
			}		
		}
		
		// dto의 profile값을 이미지 파일 이름으로 변경한다
		dto.setProfile(fileName);

		// 데이터베이스에 저장한다
		AnDao dao = new AnDao();
		dao.anJoin(dto);
		
		return "";
	}	
	
	@RequestMapping(value = "/anLogin", method = {RequestMethod.GET, RequestMethod.POST} )
	public String anLogin(HttpServletRequest req, Model model) {
		System.out.println("anLogin() 실행됨");
		
		// 데이터 받는 부분
		String id = (String) req.getParameter("id");
		String pw = (String) req.getParameter("pw");
		System.out.println("id : " + id + ", pw : " + pw);
		
		// 데이터베이스에서 로그인한 아이디와 암호가 맞는지 확인한다
		AnDao dao = new AnDao();
		MemberDTO dto = dao.anLogin(id, pw);
		
		model.addAttribute("anLogin", dto); 
		
		return "anLogin";
	}	
	
	@RequestMapping(value = "/selectMembers", method = {RequestMethod.GET, RequestMethod.POST} )
	public String selectMembers(HttpServletRequest req, Model model) {
		System.out.println("selectMembers() 실행됨");
		
		// 데이터 받는 부분
		String id = (String) req.getParameter("id");		
		System.out.println("id : " + id );
		
		// 데이터베이스에서 모든 멤버의 데이터를 가져온다
		AnDao dao = new AnDao();
		ArrayList<MemberDTO> dtos = dao.selectMembers();
		
		model.addAttribute("selectMembers", dtos); 
		
		return "selectMembers";
	}	
	
	@RequestMapping(value = "/deleteMember", method = {RequestMethod.GET, RequestMethod.POST} )
	public String deleteMember(HttpServletRequest req, Model model) {
		System.out.println("deleteMember() 실행됨");
		
		// 데이터 받는 부분
		String id = (String) req.getParameter("id");		
		System.out.println("id : " + id );
		
		// 데이터베이스에서 모든 멤버의 데이터를 가져온다
		AnDao dao = new AnDao();
		ArrayList<MemberDTO> dtos = dao.deleteMember(id);
		
		model.addAttribute("deleteMember", dtos); 
		
		return "deleteMember";
	}	

		
	public void makeDir(HttpServletRequest req){
		File f = new File(req.getSession().getServletContext()
				.getRealPath("/resources"));
		if(!f.isDirectory()){
			f.mkdir();
		}	
	}
	
	@RequestMapping(value = "/file.f", produces = "text/html;charset=utf-8")
	public String file(MultipartRequest mReq, HttpServletRequest req) {
		// 파일 여러개 전송 시 mReq.getFileMap().get("")

		MultipartFile file = mReq.getFile("file");
		if(file != null) {
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);
			fileName = "My" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
			System.out.println(fileName);
			// 디렉토리 존재하지 않으면 생성
			makeDir(req);	
				
			if(file.getSize() > 0){			
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/");
				
				System.out.println( fileName + " : " + realImgPath);
				System.out.println( "fileSize : " + file.getSize());					
												
			 	try {
			 		// 이미지파일 저장
					file.transferTo(new File(realImgPath, fileName));										
				} catch (Exception e) {
					e.printStackTrace();
				} 
									
			}else{
				// 이미지파일 실패시
				fileName = "FileFail.jpg";
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
						
			}			
		
		}				
		
		return "";
	}
	
	
	public String fileUpload(String category, MultipartFile file, HttpServletRequest request) {
		// 업로드할 물리적 위치
		// D:\Study_Spring\Workspace\.metadata\.plugins\org...er.core\tmp1\wtpwebapps\iot\resources
		// String path =
		// request.getSession().getServletContext().getRealPath("resources");
		// d://app/iot
		String path = "d://app" + request.getContextPath();

		// upload/profile/2022/08/23
		String upload = "/upload/" + category + new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
		// D:\Study_Spring\Wo....\iot\resources/upload/profile/2022/08/23
		path += upload;

		// 해당 경로 폴더가 없으면 만든다
		File folder = new File(path);
		if (!folder.exists())
			folder.mkdirs();

		// 파일 업로드
		// 파일명에 고유id를 붙인다
		// dafqer32-g38fgfa_abc.png
		String uuid = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

		try {
			file.transferTo(new File(path, uuid));
		} catch (Exception e) {
		}

		// /upload/profile/2022/08/23/dafqer32-g38fgfa_abc.png
		return upload + "/" + uuid;
	}

}
