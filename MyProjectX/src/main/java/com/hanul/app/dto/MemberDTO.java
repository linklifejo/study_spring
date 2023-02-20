package com.hanul.app.dto;

public class MemberDTO {
	String id, pw, name, phone, address, profile;

	public MemberDTO() {
		super();
	}

	// 로그인할때 비밀번호 없이 멤버변수 보낼때
	public MemberDTO(String id, String name, String phone, String address, String profile) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.profile = profile;
	}
	
	// 데이터베이스에 멤버변수 추가할때
	public MemberDTO(String id, String pw, String name, String phone, String address, String profile) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.profile = profile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
}
