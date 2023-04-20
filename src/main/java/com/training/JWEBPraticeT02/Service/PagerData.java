package com.training.JWEBPraticeT02.Service;

import java.util.List;

import com.training.JWEBPraticeT02.entity.BaseEntity;




public class PagerData<E extends BaseEntity> {

	private List<E> data;

	private int totalItems;

	private int currentPage;

	public List<E> getData() {
		return data;
	}

	public void setData(List<E> data) {
		this.data = data;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}