package com.hanul.hello.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
public class Customer {
	@Id @Column(name="id") private int cid;
	private String name, gender, email, phone;
}
