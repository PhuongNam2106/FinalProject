package com.training.JWEBPraticeT02.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_contact")
public class ContactSQL extends BaseEntity{
	@Column(name = "customer_name", length = 100, nullable = false)
	private String customerName;
	
	
	@Column(name = "email", length = 100, nullable = false)
	private String email;
	
	
	@Column(name = "message", length = 1000, nullable = false)
	private String message;

	
	

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
