package com.hanul.app.command;

import org.springframework.ui.Model;

import com.hanul.app.dao.AnDao;

public class AnJoinCommand implements AnCommand{

	@Override
	public void execute(Model model) {
		
		//5. controller 에서 넘겨받은 model에서 값 추출
		String id = (String) model.asMap().get("id");
		String passwd = (String)model.asMap().get("passwd");	
		String name = (String)model.asMap().get("name");
		String phonenumber = (String)model.asMap().get("phonenumber");
		String address = (String)model.asMap().get("address");
		
		//6. 추출한 값을 Dao에 넘겨준다
		AnDao adao = new AnDao(); 
		//9. Dao에서 넘겨준 값을 같은 타입으로 받는다
		int state = adao.anJoin(id, passwd, name, phonenumber, address);
		
		//10. jsp에서 사용하기 위해 모델에 저장한다.
		model.addAttribute("anJoin", String.valueOf(state));
	}	
	
	

}
