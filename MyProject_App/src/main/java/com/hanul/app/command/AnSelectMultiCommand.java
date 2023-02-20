package com.hanul.app.command;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.hanul.app.dao.AnDao;
import com.hanul.app.dto.MyItemDTO;


public class AnSelectMultiCommand implements AnCommand{

	@Override
	public void execute(Model model) {			
		AnDao adao = new AnDao();
		ArrayList<MyItemDTO> adtos = adao.anSelectMulti();
		
		model.addAttribute("anSelectMulti", adtos); 
		
	}
	
}
