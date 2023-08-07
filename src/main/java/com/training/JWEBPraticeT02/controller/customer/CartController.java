package com.training.JWEBPraticeT02.controller.customer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.training.JWEBPraticeT02.DTO.Cart;
import com.training.JWEBPraticeT02.DTO.CartItem;
import com.training.JWEBPraticeT02.Service.PaypalService;
import com.training.JWEBPraticeT02.Service.ProductService;
import com.training.JWEBPraticeT02.Service.SaleOrderService;
import com.training.JWEBPraticeT02.Utils.Utils;
import com.training.JWEBPraticeT02.conf.PaypalPaymentIntent;
import com.training.JWEBPraticeT02.conf.PaypalPaymentMethod;
import com.training.JWEBPraticeT02.controller.BaseController;
import com.training.JWEBPraticeT02.entity.*;
import com.training.JWEBPraticeT02.repositories.ProductRepository;
import com.training.JWEBPraticeT02.repositories.ProductSizeRepository;
import com.training.JWEBPraticeT02.repositories.SizeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.ModelMap;




@Controller
public class CartController extends BaseController {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private SaleOrderService saleOderService;
	@Autowired
	private SizeRepository sizeRepository;
	@Autowired
	private ProductSizeRepository productSizeRepository;

	@Autowired
	private PaypalService paypalService;

	public static final String URL_PAYPAL_SUCCESS = "pay/success";
	public static final String URL_PAYPAL_CANCEL = "pay/cancel";
	private Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = { "/ajax/addToCart" }, method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> ajax_AddToCart(final Model model, final HttpServletRequest request,
															  final HttpServletResponse response, final @RequestBody CartItem cartItem
															  ) {

		// để lấy session sử dụng thông qua request
		// session tương tự như kiểu Map và được lưu trên main memory.
		HttpSession session = request.getSession();

		// Lấy thông tin giỏ hàng.
		Cart cart = null;
		// kiểm tra xem session có tồn tại đối tượng nào tên là "cart"
		if (session.getAttribute("cart") != null) {
			cart = (Cart) session.getAttribute("cart");
		} else {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}

		// Lấy danh sách sản phẩm có trong giỏ hàng
		List<CartItem> cartItems = cart.getCartItems();

		// kiểm tra nếu có trong giỏ hàng thì tăng số lượng
		boolean isExists = false;
		for (CartItem item : cartItems) {
			if (item.getProductId() == cartItem.getProductId() && item.getSize()== cartItem.getSize()) {
				isExists = true;
				item.setQuanlity(item.getQuanlity() + cartItem.getQuanlity());
			}
		}

		// nếu sản phẩm chưa có trong giỏ hàng
		if (!isExists) {
			System.out.println(cartItem.getSize());
			System.out.println(cartItem.getSize());
			System.out.println(cartItem.getSize());
			System.out.println("lỗi ở đây: "+cartItem.getProductId());
			Optional<Product> temp = productRepository.findById(cartItem.getProductId());
			Product  productInDb = temp.get();
			Size sizeTemp = sizeRepository.findById(cartItem.getSize()).get();
			cartItem.setSizeName(sizeTemp.getName());
			cartItem.setProductAvatar(productInDb.getAvatar());
			cartItem.setProductName(productInDb.getTitle());
			cartItem.setPriceUnit(productInDb.getPriceSale());

			cart.getCartItems().add(cartItem);
		}

		// tính tổng tiền
		this.calculateTotalPrice(request);

		// trả về kết quả
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult.put("code", 200);
		jsonResult.put("status", "TC");
		jsonResult.put("totalItems", getTotalItems(request));

		session.setAttribute("totalItems", getTotalItems(request));
		return ResponseEntity.ok(jsonResult);
	}

	private int getTotalItems(final HttpServletRequest request) {
		HttpSession httpSession = request.getSession();

		if (httpSession.getAttribute("cart") == null) {
			return 0;
		}

		Cart cart = (Cart) httpSession.getAttribute("cart");
		List<CartItem> cartItems = cart.getCartItems();

		int total = 0;
		for (CartItem item : cartItems) {
			total += item.getQuanlity();
		}

		return total;
	}

	private void calculateTotalPrice(final HttpServletRequest request) {

		// để lấy session sử dụng thông qua request
		// session tương tự như kiểu Map và được lưu trên main memory.
		HttpSession session = request.getSession();

		// Lấy thông tin giỏ hàng.
		Cart cart = null;
		if (session.getAttribute("cart") != null) {
			cart = (Cart) session.getAttribute("cart");
		} else {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}

		// Lấy danh sách sản phẩm có trong giỏ hàng
		List<CartItem> cartItems = cart.getCartItems();
		BigDecimal total = BigDecimal.ZERO;

		for(CartItem ci : cartItems) {
			total = total.add(ci.getPriceUnit().multiply(BigDecimal.valueOf(ci.getQuanlity())));
		}

		cart.setTotalPrice(total);
	}

	@RequestMapping(value = { "/cart/view" }, method = RequestMethod.GET)
	public String cartView(final Model model, final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		return "html/CustomerView/cart";
	}

	@RequestMapping(value = { "/cart/checkout" }, method = RequestMethod.POST)
	public String cartFinish(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response,
							 RedirectAttributes redirectAttributes,
							 @RequestParam("payment") int paymentMethod,
							 @RequestParam("price") Double price)
			throws Exception {

		// ThÃ´ng tin khÃ¡ch hÃ ng

		System.out.println("payment: "+paymentMethod);
		System.out.println("price: "+price);


		String customerFullName = request.getParameter("customerFullName");
		String customerAddress = request.getParameter("customerAddress");
		String customerEmail = request.getParameter("customerEmail");
		String customerPhone = request.getParameter("customerPhone");

//		BigDecimal customerTotal = new BigDecimal(request.getParameter("customerTotal"));

		// táº¡o hÃ³a Ä‘Æ¡n
		SaleOder saleOrder = new SaleOder();
		saleOrder.setCustomerName(customerFullName);
		saleOrder.setCustomerEmail(customerEmail);
		saleOrder.setCustomerAddress(customerAddress);
		saleOrder.setCustomerPhone(customerPhone);
		saleOrder.setStatusOrder(0);
		//luu phương thức thanh toán
		if(paymentMethod==0)
		{
			saleOrder.setPaypal(false);
		}
		else
		{
			saleOrder.setPaypal(true);
		}
		System.out.println("user dat hang: " + super.getUserLogined());
		saleOrder.setUser(super.getUserLogined());


		// mÃ£ hÃ³a Ä‘Æ¡n
		saleOrder.setCode(String.valueOf(System.currentTimeMillis()));


		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		//lưu tổng tiền hóa đơn
		saleOrder.setTotal(cart.getTotalPrice());

		for (CartItem cartItem : cart.getCartItems()) {
			SaleOderProduct saleOrderProducts = new SaleOderProduct();
			Optional<Product> temp = productRepository.findById(cartItem.getProductId());
			Product product = temp.get();
			saleOrderProducts.setProduct(product);
			saleOrderProducts.setQuality(cartItem.getQuanlity());
			saleOrderProducts.setSize(cartItem.getSizeName());
			ProductSize updateQuantityProductSize = productSizeRepository.findByProductIdAndSizeId(cartItem.getProductId(),cartItem.getSize());
			updateQuantityProductSize.setQuantity(updateQuantityProductSize.getQuantity()- cartItem.getQuanlity());
			productSizeRepository.save(updateQuantityProductSize);

			// sá»­ dá»¥ng hÃ m tiá»‡n Ã­ch add hoáº·c remove Ä‘á»›i vá»›i cÃ¡c quan há»‡ onetomany
			saleOrder.addSaleOrderProduct(saleOrderProducts);
		}

		// lÆ°u vÃ o cÆ¡ sá»Ÿ dá»¯ liá»‡u
		if(paymentMethod==0)
		{

			saleOderService.saveOrUpdate(saleOrder);

			// xÃ³a dá»¯ liá»‡u giá»� hÃ ng trong session
			session.setAttribute("cart", null);
			session.setAttribute("totalItems", "0");
			redirectAttributes.addFlashAttribute("message", "Cảm ơn bạn đã đặt hàng ở chúng tôi");

			return "redirect:/home";
		}
		else
		{
			saleOderService.saveOrUpdate(saleOrder);
			// xÃ³a dá»¯ liá»‡u giá»� hÃ ng trong session
			session.setAttribute("cart", null);
			session.setAttribute("totalItems", "0");
//			redirectAttributes.addFlashAttribute("price", price);

			String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
			String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
			try {
				Payment payment = paypalService.createPayment(
						price,
						"USD",
						PaypalPaymentMethod.paypal,
						PaypalPaymentIntent.sale,
						"payment description",
						cancelUrl,
						successUrl);
				for(Links links : payment.getLinks()){
					if(links.getRel().equals("approval_url")){
						return "redirect:" + links.getHref();
					}
				}
			} catch (PayPalRESTException e) {
				log.error(e.getMessage());
			}
			return "redirect:/";
		}

	}

//	@RequestMapping(value = { "/cart/checkout" }, method = RequestMethod.POST)
//	public String cartFinishUsingPaypal(final ModelMap model, final HttpServletRequest request, final HttpServletResponse response,RedirectAttributes redirectAttributes)
//			throws Exception {
//
//		// ThÃ´ng tin khÃ¡ch hÃ ng
//		String customerFullName = request.getParameter("customerFullName");
//		String customerAddress = request.getParameter("customerAddress");
//		String customerEmail = request.getParameter("customerEmail");
//		String customerPhone = request.getParameter("customerPhone");
//
////		BigDecimal customerTotal = new BigDecimal(request.getParameter("customerTotal"));
//
//		// táº¡o hÃ³a Ä‘Æ¡n
//		SaleOder saleOrder = new SaleOder();
//		saleOrder.setCustomerName(customerFullName);
//		saleOrder.setCustomerEmail(customerEmail);
//		saleOrder.setCustomerAddress(customerAddress);
//		saleOrder.setCustomerPhone(customerPhone);
//		saleOrder.setStatusOrder(0);
//		saleOrder.setUser(super.getUserLogined());
//
//		// kiá»ƒm tra xem khÃ¡ch hÃ ng cÃ³ pháº£i Ä‘Ã£ login hay chÆ°a?
////		if(super.isLogined()) {
////			User userLogined = super.getUserLogined();
////			saleOrder.setUser(userLogined); //khÃ³a ngoáº¡i user_id
////
////			saleOrder.setCustomerName(userLogined.getUsername());
////			saleOrder.setCustomerEmail(userLogined.getEmail());
////			saleOrder.setCustomerAddress(userLogined.getAddress());
////			saleOrder.setCustomerPhone(userLogined.getPhone());
////			saleOrder.setStatusOrder(0);
////		} else {
////			saleOrder.setCustomerName(customerFullName);
////			saleOrder.setCustomerEmail(customerEmail);
////			saleOrder.setCustomerAddress(customerAddress);
////			saleOrder.setCustomerPhone(customerPhone);
////		}
//
//		// mÃ£ hÃ³a Ä‘Æ¡n
//		saleOrder.setCode(String.valueOf(System.currentTimeMillis()));
//
//
////		if(getUserLogined() != null) {
////
////		}
//
//		// káº¿t cÃ¡c sáº£n pháº©m trong giá»� hÃ ng cho hÃ³a Ä‘Æ¡n
//		HttpSession session = request.getSession();
//		Cart cart = (Cart) session.getAttribute("cart");
//		//lưu tổng tiền hóa đơn
//		saleOrder.setTotal(cart.getTotalPrice());
//		saleOrder.setPaypal(true);
//		for (CartItem cartItem : cart.getCartItems()) {
//			SaleOderProduct saleOrderProducts = new SaleOderProduct();
//			Optional<Product> temp = productRepository.findById(cartItem.getProductId());
//			Product product = temp.get();
//			saleOrderProducts.setProduct(product);
//			saleOrderProducts.setQuality(cartItem.getQuanlity());
//			saleOrderProducts.setSize(cartItem.getSizeName());
//			ProductSize updateQuantityProductSize = productSizeRepository.findByProductIdAndSizeId(cartItem.getProductId(),cartItem.getSize());
//			updateQuantityProductSize.setQuantity(updateQuantityProductSize.getQuantity()- cartItem.getQuanlity());
//			productSizeRepository.save(updateQuantityProductSize);
//
//			// sá»­ dá»¥ng hÃ m tiá»‡n Ã­ch add hoáº·c remove Ä‘á»›i vá»›i cÃ¡c quan há»‡ onetomany
//			saleOrder.addSaleOrderProduct(saleOrderProducts);
//		}
//
//		// lÆ°u vÃ o cÆ¡ sá»Ÿ dá»¯ liá»‡u
//		saleOderService.saveOrUpdate(saleOrder);
//
//		// xÃ³a dá»¯ liá»‡u giá»� hÃ ng trong session
//		session.setAttribute("cart", null);
//		session.setAttribute("totalItems", "0");
//		redirectAttributes.addFlashAttribute("message", "Cảm ơn bạn đã đặt hàng ở chúng tôi");
//
//		return "redirect:/home";
//	}



	@RequestMapping(value = { "/ajax/plusQuantityCart" }, method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> ajax_plusQuanlityCart(final Model model, final HttpServletRequest request,
			final HttpServletResponse response, final @RequestBody CartItem cartItem, RedirectAttributes redirectAttributes) {

		// Ä‘á»ƒ láº¥y session sá»­ dá»¥ng thÃ´ng qua request
		// session tÆ°Æ¡ng tá»± nhÆ° kiá»ƒu Map vÃ  Ä‘Æ°á»£c lÆ°u trÃªn main memory.
		HttpSession session = request.getSession();

		// Láº¥y thÃ´ng tin giá»� hÃ ng.
		Cart cart = null;
		// kiá»ƒm tra xem session cÃ³ tá»“n táº¡i Ä‘á»‘i tÆ°á»£ng nÃ o tÃªn lÃ  "cart"
		if (session.getAttribute("cart") != null) {
			cart = (Cart) session.getAttribute("cart");
		} else {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}

		// Láº¥y danh sÃ¡ch sáº£n pháº©m cÃ³ trong giá»� hÃ ng
		List<CartItem> cartItems = cart.getCartItems();

		// kiá»ƒm tra náº¿u cÃ³ trong giá»� hÃ ng thÃ¬ tÄƒng sá»‘ lÆ°á»£ng
		int currentProductQuality = 0;
		for (CartItem item : cartItems) {
			if (item.getProductId() == cartItem.getProductId()) {
				ProductSize pz = productSizeRepository.findByProductIdAndSizeId(item.getProductId(),item.getSize());
				currentProductQuality = item.getQuanlity() + 1;
				if(currentProductQuality > pz.getQuantity())
				{
					session.setAttribute("TB", "Số lượng bạn đặt đã quá số lượng còn lại ở trong kho");
				}
				else
				{

					session.setAttribute("TB", null);
					item.setQuanlity(currentProductQuality);
				}
			}
		}

		// tÃ­nh tá»•ng tiá»�n
		this.calculateTotalPrice(request);

		// tráº£ vá»� káº¿t quáº£
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult.put("code", 200);
		jsonResult.put("status", "TC");
		jsonResult.put("totalItems", getTotalItems(request));
		jsonResult.put("currentProductQuality", currentProductQuality);

		session.setAttribute("totalItems", getTotalItems(request));
		return ResponseEntity.ok(jsonResult);
	}

	@RequestMapping(value = { "/ajax/minusQuantityCart" }, method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> ajax_minusQuanlityCart(final Model model, final HttpServletRequest request,
			final HttpServletResponse response, final @RequestBody CartItem cartItem) {

		// Ä‘á»ƒ láº¥y session sá»­ dá»¥ng thÃ´ng qua request
		// session tÆ°Æ¡ng tá»± nhÆ° kiá»ƒu Map vÃ  Ä‘Æ°á»£c lÆ°u trÃªn main memory.
		HttpSession session = request.getSession();

		// Láº¥y thÃ´ng tin giá»� hÃ ng.
		Cart cart = null;
		// kiá»ƒm tra xem session cÃ³ tá»“n táº¡i Ä‘á»‘i tÆ°á»£ng nÃ o tÃªn lÃ  "cart"
		if (session.getAttribute("cart") != null) {
			cart = (Cart) session.getAttribute("cart");
		} else {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}

		// Láº¥y danh sÃ¡ch sáº£n pháº©m cÃ³ trong giá»� hÃ ng
		List<CartItem> cartItems = cart.getCartItems();

		// kiá»ƒm tra náº¿u cÃ³ trong giá»� hÃ ng thÃ¬ tÄƒng sá»‘ lÆ°á»£ng
		int currentProductQuality = 0;
		for (CartItem item : cartItems) {
			if (item.getProductId() == cartItem.getProductId()) {
				currentProductQuality = item.getQuanlity() - 1;
				if (currentProductQuality<1) {
					session.setAttribute("TB", "Số lượng không được nhỏ hơn 1");

				}else
				{
					item.setQuanlity(currentProductQuality);
					session.setAttribute("TB", null);
				}
			}
		}

		// tÃ­nh tá»•ng tiá»�n
		this.calculateTotalPrice(request);

		// tráº£ vá»� káº¿t quáº£
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		jsonResult.put("code", 200);
		jsonResult.put("status", "TC");
		jsonResult.put("totalItems", getTotalItems(request));
		jsonResult.put("currentProductQuality", currentProductQuality);

		session.setAttribute("totalItems", getTotalItems(request));
		return ResponseEntity.ok(jsonResult);
	}

	@RequestMapping(value = { "/cart/delete/{productId}" }, method = RequestMethod.GET)
	public String removeItem(final Model model, final HttpServletRequest request,
							 final HttpServletResponse response, @PathVariable("productId") int productId
							 ) throws IOException{
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		for (int i = 0; i < cart.getCartItems().size(); i++) {
			if(cart.getCartItems().get(i).getProductId()==productId)
				cart.getCartItems().remove(i);
		}
		this.calculateTotalPrice(request);
		session.setAttribute("totalPrice", cart.getTotalPrice());
		session.setAttribute("totalItems", getTotalItems(request));
		return "redirect:/cart/view";
	}
}


