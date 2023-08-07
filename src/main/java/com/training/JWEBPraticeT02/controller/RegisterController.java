package com.training.JWEBPraticeT02.controller;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.training.JWEBPraticeT02.Service.RoleService;
import com.training.JWEBPraticeT02.Service.UserService;
import com.training.JWEBPraticeT02.entity.Role;
import com.training.JWEBPraticeT02.entity.User;
import com.training.JWEBPraticeT02.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class RegisterController  {
	@Autowired
	 private UserRepository userRepository ;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@RequestMapping(value = {"/register"},
			method = RequestMethod.GET)
	public String register (final Model model, final HttpServletRequest request, final HttpServletResponse response)
	throws IOException {
		User user = new User();
//		User user2 = userRepository.findByUsername(user2.getUsername());
		model.addAttribute("userModel",user);

		return "html/CustomerView/register";
	}

	@RequestMapping(value = {"/register"},
			method = RequestMethod.POST)
	public String register_post (final Model model,
								final HttpServletRequest request,
								final HttpServletResponse response,
								final @ModelAttribute("userModel") User user,
								RedirectAttributes redirectAttributes)
	throws IOException {

//		boolean match = false;
//		 List<User> users ;
//		 users=userService.findAll();
//		 for (int i = 0; i < users.size(); i++) {
//			System.out.println("ds user "+users.get(i).getUsername());
//			if (user.getUsername().equalsIgnoreCase(users.get(i).getUsername())) {
//				match=true;
//				break;
//			}
//			if()
//		}
		if(userRepository.findByEmail(user.getEmail())!=null)
		{
			model.addAttribute("TB","Email này đã được dùng để đăng ký, vui lòng dùng email khác");
			return "html/CustomerView/register";
		}
		if (userRepository.findByUsername(user.getUsername())!=null) {
			model.addAttribute("TB","Tên tài khoản này đã tồn tại, vui lòng thử tên tài khoản khác");
			return "html/CustomerView/register";
		}
		else
		{
			user.setPassword(new BCryptPasswordEncoder(4).encode(user.getPassword()));
			Role role = roleService.getOneByNativeSQL("select * from tbl_roles r where r.name = 'GUEST'");
	        user.addRoles(role);
			userService.saveOrUpdate(user);
			redirectAttributes.addFlashAttribute("message", "Bạn đã đăng ký thành công");

			return "redirect:/login";

		}

}
}
