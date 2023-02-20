package com.hanul.app.command;

import org.springframework.ui.Model;

import com.hanul.app.dao.AnDao;


public class AnDeleteMultiCommand implements AnCommand{	
	
	@Override
	public void execute(Model model) {
		int id = Integer.parseInt((String)model.asMap().get("id"));
		
		AnDao adao = new AnDao();
		adao.anDeleteMulti(id);		
			
	}	 

}
