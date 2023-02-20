package com.hanul.app.command;

import org.springframework.ui.Model;

import com.hanul.app.dao.AnDao;



public class AnUpdateMultiNoCommand implements AnCommand{	
	
	@Override
	public void execute(Model model) {
		int id = Integer.parseInt((String)model.asMap().get("id"));
		String name = (String)model.asMap().get("name");
		String date = (String)model.asMap().get("date");		
		
		AnDao adao = new AnDao();
		
		adao.anUpdateMultiNo(id, name, date);
		
		
	}	 

}
