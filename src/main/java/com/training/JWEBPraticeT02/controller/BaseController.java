package com.training.JWEBPraticeT02.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.training.JWEBPraticeT02.Service.CategoryService;
import com.training.JWEBPraticeT02.entity.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;


public abstract class BaseController {

	@Autowired
	private CategoryService categoriesService;

//	@ModelAttribute("isLogined")
//	public boolean isLogined() {
//		boolean isLogined = false;
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if (principal instanceof UserDetails) {
//			isLogined = true;
//		}
//		return isLogined;
//	}
//
//	@ModelAttribute("userLogined")
//	public User getUserLogined() {
//		Object userLogined = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if(userLogined != null && userLogined instanceof UserDetails)
//			return (User) userLogined;
//
//		return null;
//	}
	
	@ModelAttribute("categories")
	public List<Category> getAllCategories() {
		return categoriesService.findAll();
	}
	public int getCurrentPage(HttpServletRequest request) {
		try {
			return Integer.parseInt(request.getParameter("page"));
		} catch (Exception e) {
			return 1;
		}
	}
	
	
	public Integer getInteger(HttpServletRequest request, String paramName) {
		try {
			return Integer.parseInt(request.getParameter(paramName));
		} catch (Exception e) {
			return null;
		}
	}
}
