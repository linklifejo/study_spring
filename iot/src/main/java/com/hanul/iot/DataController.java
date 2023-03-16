package com.hanul.iot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.CommonUtility;

@Controller
public class DataController {
	//공공데이터 키
	private String key = 
			"FPgj2NXbJw46TcGkmAfZEiYFDbxilys7KLjk3KaB7AfeJE00ZhPNM0M8unwbsI69fSmT8SNfVEimE6ZZ2U14hA%3D%3D";
	
	@Autowired private CommonUtility common;
	
	//약국정보조회 요청
	@ResponseBody @RequestMapping("/data/pharmacy")
	public Object pharmacy_list( int pageNo, int rows ) {
		StringBuffer url = new StringBuffer(
				"http://apis.data.go.kr/B551182/pharmacyInfoService/getParmacyBasisList");
		url.append("?ServiceKey=").append(key);
		url.append("&_type=json");
		url.append("&pageNo=").append( pageNo );
		url.append("&numOfRows=").append( rows );
		return common.requestAPItoMap(url);
	}
	
	//공공데이터 목록화면 요청
	@RequestMapping("/list.da")
	public String list() {
		return "data/list";
	}
}
