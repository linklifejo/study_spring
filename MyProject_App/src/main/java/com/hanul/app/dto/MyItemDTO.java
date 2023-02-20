package com.hanul.app.dto;

import java.sql.Date;

public class MyItemDTO {

	int id;
	String name;	
	Date hire_date;
	String image_path;		

	public MyItemDTO(int id, String name, Date hire_date, String image_path) {		
		this.id = id;
		this.name = name;
		this.hire_date = hire_date;
		this.image_path = image_path;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getHire_date() {
		return hire_date;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	
}
