package com.training.JWEBPraticeT02.model;

public class CategorySearchModel extends BaseSearchModel{
		public String keyword;


		public Integer categoryId;
		
		public String seo;
		

		public String getKeyword() {
			return keyword;
		}


		public Integer getCategoryId() {
			return categoryId;
		}
		
		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}


		public void setCategoryId(Integer categoryId) {
			this.categoryId = categoryId;
		}


		public void setSeo(String seo) {
			this.seo = seo;
		}


		public String getSeo() {
			return seo;
		}
}
