package com.training.JWEBPraticeT02.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.training.JWEBPraticeT02.Service.SaleOrderProductService;
import com.training.JWEBPraticeT02.Service.SaleOrderService;
import com.training.JWEBPraticeT02.controller.BaseController;
import com.training.JWEBPraticeT02.entity.Product;
import com.training.JWEBPraticeT02.entity.SaleOder;
import com.training.JWEBPraticeT02.entity.SaleOderProduct;
import com.training.JWEBPraticeT02.entity.User;
import com.training.JWEBPraticeT02.model.SaleOrderSearchModel;
import com.training.JWEBPraticeT02.repositories.SaleOrderProductRepository;
import com.training.JWEBPraticeT02.repositories.SaleOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminSaleOrderController extends BaseController {
	@Autowired
	private SaleOrderService saleOrderService;
	@Autowired
	private SaleOrderProductRepository saleOrderProductRepository;
	@Autowired
	private SaleOrderRepository saleOrderRepository;
	@Autowired
	private SaleOrderProductService saleOrderProductService;

	  @RequestMapping(value = {"/admin/saleOrderList"}, method = RequestMethod.GET)
	    public String getOrder(final Model model,
	                           final HttpServletRequest request,
	                           final HttpServletResponse response,
							   @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
							   @RequestParam(value = "keyword", required = false) String keyword) throws Exception{
			  int pageSize = 10;
			  Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
			  Page<SaleOder> saleOders ;
			  if("".equals(keyword)||keyword==null)
			  {
//				  saleOders = saleOrderRepository.findAll(pageable);
				  saleOders = saleOrderRepository.findAllByOrderByIdDesc(pageable);

			  }
			  else
			  {
				  saleOders = saleOrderRepository.findByCodeContainingIgnoreCase(keyword ,pageable);
			  }
		  	model.addAttribute("currentPage", pageIndex);
			model.addAttribute("saleOrderWithPaging",saleOders);
			return "html/AdminView/OrderView/saleOrderList";
	    }

	  @RequestMapping(value = {"/admin/saleOrderList/{Id}"}, method = RequestMethod.GET)
	    public String listSaleOrder(final Model model,
	                           final HttpServletRequest request,
	                           final HttpServletResponse response,
	                           @PathVariable("Id") int id ) throws Exception{

	        List<SaleOderProduct> saleOderProduct = saleOrderProductRepository.findBySaleOrder_Id(id);
	        model.addAttribute("saleOrderProducts",saleOderProduct);
	        return "html/AdminView/OrderView/saleOrderDetailList";
	    }

	  @RequestMapping(value = {"/admin/saleOrderList/delete/{Id}"}, method = RequestMethod.GET)
	    public String deleteSaleOrder(final Model model,
	                           final HttpServletRequest request,
	                           final HttpServletResponse response,
	                           @PathVariable("Id") int id ) throws Exception{

	       saleOrderService.deleteById(id);
	        return "redirect:/admin/saleOrderList";
	    }

	@GetMapping("/admin/saleOrder/{id}/{statusOrder}")
	public String editStatus(@PathVariable("id") Integer orderId, @PathVariable("statusOrder") Integer statusOrder) {
		Optional<SaleOder> optionalOrder = saleOrderRepository.findById(orderId);
		SaleOder order = optionalOrder.get();
		order.setStatusOrder(statusOrder);
		SaleOder updatedOrder = saleOrderRepository.save(order);
		return "redirect:/admin/saleOrderList";

	}
}
