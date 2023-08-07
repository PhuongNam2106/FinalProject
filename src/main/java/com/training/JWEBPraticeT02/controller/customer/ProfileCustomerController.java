package com.training.JWEBPraticeT02.controller.customer;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.training.JWEBPraticeT02.Service.UserService;
import com.training.JWEBPraticeT02.controller.BaseController;
import com.training.JWEBPraticeT02.entity.User;
import com.training.JWEBPraticeT02.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ProfileCustomerController extends BaseController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@RequestMapping(value = { "/profile" }, method = RequestMethod.GET)
	public String profile(final Model model, final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		User user = super.getUserLogined();

		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getCustomerName());
		System.out.println(user.getAddress());
		model.addAttribute("user",user);
		return "html/CustomerView/profile";
	}

	@RequestMapping(value = { "/profile" }, method = RequestMethod.POST)
	public String editProfile(final Model model, final HttpServletRequest request, final HttpServletResponse response, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes )
			throws IOException {
		System.out.println(user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getAddress());
		 redirectAttributes.addFlashAttribute("message","Cập nhật thông tin thành công");
		 userRepository.save(user);
//		 userService.saveOrUpdate(user);

		return "redirect:/home";
	}

}
