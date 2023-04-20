package com.training.JWEBPraticeT02.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_subscribe")
public class SubscribeSQL extends BaseEntity {
	@Column(name="sub_name",length = 100, nullable = false)
	private String Name;
	
	@Column(name="sub_email",length = 100, nullable = false)
	private String Email;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	
	
}
