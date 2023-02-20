package com.hanul.app.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.hanul.app.command.AnCommand;
import com.hanul.app.command.AnDeleteMultiCommand;
import com.hanul.app.command.AnInsertMultiCommand;
import com.hanul.app.command.AnJoinCommand;
import com.hanul.app.command.AnLoginCommand;
import com.hanul.app.command.AnSelectMultiCommand;
import com.hanul.app.command.AnUpdateMultiCommand;
import com.hanul.app.command.AnUpdateMultiNoCommand;

@Controller
public class AnController {
	
	AnCommand command;

	@RequestMapping(value="/anJoin", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anJoin(HttpServletRequest req, Model model){
		System.out.println("anJoin()");
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 	
		
		//1. 안드로이드에서 보낸 값을 req로 받아서 변수에 저장
		String id = (String) req.getParameter("id");
		String passwd = (String) req.getParameter("passwd");
		String name = (String) req.getParameter("name");
		String phonenumber = (String) req.getParameter("phonenumber");
		String address = (String) req.getParameter("address");
		
		//2. 한번 찍어본다
		System.out.println(id);
		System.out.println(passwd);
		System.out.println(name);
		System.out.println(phonenumber);
		System.out.println(address);
		
		//3. command 에서 사용하기 위해 model에 저장
		model.addAttribute("id", id);
		model.addAttribute("passwd", passwd);
		model.addAttribute("name", name);
		model.addAttribute("phonenumber", phonenumber);
		model.addAttribute("address", address);
		
		//4. AnJoinCommand를 생성해서 모델에 담긴 데이터를 넘겨준다
		command = new AnJoinCommand();
		command.execute(model);
		
		//11. 응답을 위해 views에서 anJoin.jsp를 찾아 실행한다.
		return "anJoin";
	}
	
	@RequestMapping(value="/anLogin", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anLogin(HttpServletRequest req, Model model){
		System.out.println("anLogin()");
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		String id = (String) req.getParameter("id");
		String passwd = (String) req.getParameter("passwd");
		
		System.out.println(id);
		System.out.println(passwd);
		
		model.addAttribute("id", id);
		model.addAttribute("passwd", passwd);
		
		command = new AnLoginCommand();
		command.execute(model);
		
		return "anLogin";
	}
	
	@RequestMapping(value="/anSelectMulti", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anSelectMulti(HttpServletRequest req, Model model){
		System.out.println("anSelectMulti()");
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 			
		
		command = new AnSelectMultiCommand();
		command.execute(model);
		
		return "anSelectMulti";
	}
	
	@RequestMapping(value="/anInsertMulti", method = {RequestMethod.GET, RequestMethod.POST}  )
	public String anInsertMulti(HttpServletRequest req, Model model){
		System.out.println("anInsertMulti()");	
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		
		String id = (String) req.getParameter("id");
		String name = (String) req.getParameter("name");
		String date = (String) req.getParameter("date");
		String dbImgPath = (String) req.getParameter("dbImgPath");
		
		System.out.println(id);
		System.out.println(name);
		System.out.println(date);
		System.out.println(dbImgPath);
		
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("date", date);	
		model.addAttribute("dbImgPath", dbImgPath);	
		
		// 안드로이드에서 보낸 파일 받는부분
		MultipartRequest multi = (MultipartRequest)req;
		MultipartFile file = multi.getFile("image");
		
			
		if(file != null) {
			String fileName = file.getOriginalFilename();
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
				
		command = new AnInsertMultiCommand();
		command.execute(model);
		
		return "anInsertMulti";
	}
		
	public void makeDir(HttpServletRequest req){
		File f = new File(req.getSession().getServletContext()
				.getRealPath("/resources"));
		if(!f.isDirectory()){
			f.mkdir();
		}	
	}
	
	@RequestMapping(value="/anUpdateMulti", method = {RequestMethod.GET, RequestMethod.POST})
	public void anUpdateMulti(HttpServletRequest req, Model model){
		System.out.println("anUpdateMulti()");	
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		String id = (String) req.getParameter("id");
		String name = (String) req.getParameter("name");
		String date = (String) req.getParameter("date");
		String dbImgPath = (String) req.getParameter("dbImgPath");
		String pDbImgPath = (String) req.getParameter("pDbImgPath");
		
		System.out.println(id);
		System.out.println(name);
		System.out.println(date);
		System.out.println("Sub1Update:dbImgPath " + dbImgPath);
		System.out.println("Sub1Update:pDbImgPath " + pDbImgPath);
		
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("date", date);
		model.addAttribute("dbImgPath", dbImgPath);
		
		// 이미지가 서로 같으면 삭제하지 않고 다르면 기존이미지 삭제
		if(!dbImgPath.equals(pDbImgPath)){			
			
			String pFileName = req.getParameter("pDbImgPath").split("/")[req.getParameter("pDbImgPath").split("/").length -1];
			String delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/" + pFileName);
			
			File delfile = new File(delDbImgPath);
			System.out.println(delfile.getAbsolutePath());
			
	        if(delfile.exists()) {
	        	boolean deleteFile = false;
	            while(deleteFile != true){
	            	deleteFile = delfile.delete();
	            }	            
	            
	        }//if(delfile.exists())
		
		}//if(!dbImgPath.equals(pDbImgPath))  
		
		MultipartRequest multi = (MultipartRequest)req;
		MultipartFile file = null;
		
		file = multi.getFile("image");
			
		if(file != null) {
			String fileName = file.getOriginalFilename();
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
				fileName = "FileFail.jpg";
				String realImgPath = req.getSession().getServletContext()
						.getRealPath("/resources/" + fileName);
				System.out.println(fileName + " : " + realImgPath);
						
			}			
			
		}
		
		command = new AnUpdateMultiCommand();
		command.execute(model);		
		
	}
	
	@RequestMapping(value="/anUpdateMultiNo", method = {RequestMethod.GET, RequestMethod.POST})
	public void anUpdateMultiNo(HttpServletRequest req, Model model){
		System.out.println("anUpdateMultiNo()");	
		
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		String id = (String) req.getParameter("id");
		String name = (String) req.getParameter("name");
		String date = (String) req.getParameter("date");		
		
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("date", date);
		
		command = new AnUpdateMultiNoCommand();
		command.execute(model);		
		
	}
		
	
	@RequestMapping(value="/anDeleteMulti", method = {RequestMethod.GET, RequestMethod.POST})
	public void anDeleteMulti(HttpServletRequest req, Model model){
		System.out.println("anDeleteMulti()");		
		
		model.addAttribute("id", req.getParameter("id"));		
				
		System.out.println((String)req.getParameter("id"));
		System.out.println((String)req.getParameter("delDbImgPath"));
		
		String pFileName = req.getParameter("delDbImgPath").split("/")[req.getParameter("delDbImgPath").split("/").length -1];
		String delDbImgPath = req.getSession().getServletContext().getRealPath("/resources/" + pFileName);		
		
		// 이미지 파일지우기
		File delfile = new File(delDbImgPath);
		System.out.println(delfile.getAbsolutePath());
		
        if(delfile.exists()) {
            System.out.println("Sub1Del:pDelImagePath " + delfile.exists());
            boolean deleteFile = false;
            while(deleteFile != true){
            	deleteFile = delfile.delete();
            }     
        }	
		
		command = new AnDeleteMultiCommand();
		command.execute(model);	
		
	}
		
	
	
}
