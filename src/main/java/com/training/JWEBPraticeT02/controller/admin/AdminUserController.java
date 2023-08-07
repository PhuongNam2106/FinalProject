package com.training.JWEBPraticeT02.controller.admin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.training.JWEBPraticeT02.Service.SaleOrderService;
import com.training.JWEBPraticeT02.Service.UserService;
import com.training.JWEBPraticeT02.controller.BaseController;
import com.training.JWEBPraticeT02.entity.ContactSQL;
import com.training.JWEBPraticeT02.entity.Product;
import com.training.JWEBPraticeT02.entity.SaleOder;
import com.training.JWEBPraticeT02.entity.User;
import com.training.JWEBPraticeT02.model.UserSearchModel;
import com.training.JWEBPraticeT02.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminUserController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private SaleOrderService saleOrderService;
	@Autowired
	private UserRepository userRepository;
	@GetMapping(value = { "/admin/userList" })
	public String userList(final Model model, final HttpServletRequest request,
							final HttpServletResponse response,
						   @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
						   @RequestParam(value = "keyword", required = false) String keyword) throws IOException {
		int pageSize = 10;
		Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
		Page<User> users ;

		if("".equals(keyword)||keyword==null)
		{
			users = userRepository.findAll(pageable);
		}
		else
		{

			users = userRepository.findByCustomerNameContainingIgnoreCase(keyword ,pageable);
		}
		List<User> user;
		user=userService.findAll();
		model.addAttribute("currentPage", pageIndex);
		model.addAttribute("users", users);


		return "html/AdminView/userView/userList";
	}

	@RequestMapping(value = {"/admin/user/delete/{userId}"},method = RequestMethod.GET)
    public String Delete(final Model model,
              final HttpServletRequest request,
              final HttpServletResponse response,
              @PathVariable("userId") int userId) throws IOException{
		saleOrderService.setNullUserId(userId);
		userService.deleteUserRoleByUserId(userId);
		userRepository.deleteById(userId);
//        userService.deleteById(userId);
        return "redirect:/admin/userList";
    }

	@GetMapping("/admin/user/editStatus/{id}/{statusUser}")
	public String editStatus(@PathVariable("id") Integer userId,@PathVariable("statusUser") Boolean statusUser) {
		Optional<User> userOptional = userRepository.findById(userId);
		User user = userOptional.get();
		user.setStatus(statusUser);
		userRepository.save(user);
		return "redirect:/admin/userList";

	}
}
