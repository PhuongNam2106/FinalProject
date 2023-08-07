package com.training.JWEBPraticeT02.model;

public class UserSearchModel extends BaseSearchModel{
	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



		// tÃ¬m theo keyword
		public String keyword;

		// tÃ¬m theo category
		public Integer Id;

		public String userName;

		public String customerName;

		public String getKeyword() {
			return keyword;
		}



		public Integer getId() {
			return Id;
		}



		public void setId(Integer id) {
			Id = id;
		}



		public String getUsername() {
			return userName;
		}



		public void setUsername(String username) {
			this.userName = username;
		}



		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}

		

		
}
