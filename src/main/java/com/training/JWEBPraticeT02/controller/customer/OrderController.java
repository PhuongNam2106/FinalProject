package com.training.JWEBPraticeT02.controller.customer;

import com.training.JWEBPraticeT02.controller.BaseController;
import com.training.JWEBPraticeT02.entity.SaleOder;
import com.training.JWEBPraticeT02.entity.SaleOderProduct;
import com.training.JWEBPraticeT02.entity.User;
import com.training.JWEBPraticeT02.repositories.SaleOrderProductRepository;
import com.training.JWEBPraticeT02.repositories.SaleOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController extends BaseController {
    @Autowired
    private SaleOrderRepository saleOrderRepository;
    @Autowired
    SaleOrderProductRepository saleOrderProductRepository;

    @RequestMapping(value = { "/order" }, method = RequestMethod.GET)
    public String profile(final Model model, final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {
        User user = super.getUserLogined();
        List<SaleOder> orderList = saleOrderRepository.findByUser_Id(user.getId());
        model.addAttribute("orderList",orderList);
        return "html/CustomerView/order";
    }

    @RequestMapping(value = { "/order/{id}/{statusOrder}" }, method = RequestMethod.GET)
    public String cancelOrder(@PathVariable("id") Integer orderId, @PathVariable("statusOrder") Integer statusOrder)
            throws IOException {
        Optional<SaleOder> optionalOrder = saleOrderRepository.findById(orderId);
        SaleOder order = optionalOrder.get();
        order.setStatusOrder(statusOrder);
        SaleOder updatedOrder = saleOrderRepository.save(order);
        return "redirect:/order";
    }

    @RequestMapping(value = {"/order/{Id}"}, method = RequestMethod.GET)
    public String listSaleOrder(final Model model,
                                final HttpServletRequest request,
                                final HttpServletResponse response,
                                @PathVariable("Id") int id ) throws Exception{

        List<SaleOderProduct> saleOderProduct = saleOrderProductRepository.findBySaleOrder_Id(id);
        model.addAttribute("saleOrderProducts",saleOderProduct);
        return "html/CustomerView/orderDetail";
    }
}
