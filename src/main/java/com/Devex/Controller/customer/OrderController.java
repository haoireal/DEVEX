package com.Devex.Controller.customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Entity.Customer;
import com.Devex.Entity.FlashSale;
import com.Devex.Entity.Order;
import com.Devex.Entity.OrderDetails;
import com.Devex.Entity.OrderDiscount;
import com.Devex.Entity.Payment;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.User;
import com.Devex.Entity.Voucher;
import com.Devex.Entity.VoucherProduct;
import com.Devex.Sevice.CartDetailService;
import com.Devex.Sevice.CartService;
import com.Devex.Sevice.CustomerService;
import com.Devex.Sevice.FlashSalesService;
import com.Devex.Sevice.OrderDetailService;
import com.Devex.Sevice.OrderDiscountService;
import com.Devex.Sevice.OrderService;
import com.Devex.Sevice.OrderStatusService;
import com.Devex.Sevice.PaymentService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.ShoppingCartService;
import com.Devex.Sevice.VoucherDetailService;
import com.Devex.Sevice.VoucherProductService;
import com.Devex.Sevice.VoucherService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class OrderController {
	@Autowired
	private CartDetailService cartDetailService;
	@Autowired
	private CartService cartService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private SessionService session;
	@Autowired
	private ProductVariantService productVariantService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private FlashSalesService flashSalesService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private OrderStatusService orderStatusService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private VoucherProductService voucherProductService;

	@Autowired
	private VoucherService voucherService;

	@Autowired
	private VoucherDetailService voucherDetailService;

	@Autowired
	private OrderDiscountService orderDiscountService;

	@Autowired
	private HttpServletResponse resp;
	@Autowired
	private HttpServletRequest req;
	@Autowired
	private ShoppingCartService shoppingCartService;

	@PostMapping("/cash-payment")
	public String paymentCash(Model model) {
		session.set("payment", "cash");
		return "redirect:/order/success";
	}

	@GetMapping("/cart/detail-order")
	public String showDetailOrder(Model model) {
		return "user/cartproductFake";
	}

	@GetMapping("/order/success")
	public String showSuccess(Model model) {
		List<CartDetailDTo> listOrder = session.get("listItemOrder", null);
		List<Voucher> listVoucher = session.get("listVoucherApply", null);
		// Sử dụng Collections.sort() với một trình tự Comparator để sắp xếp danh sách
		if (listVoucher != null) {
			Collections.sort(listVoucher, new Comparator<Voucher>() {
				@Override
				public int compare(Voucher voucher1, Voucher voucher2) {
					return Integer.compare(voucher2.getCategoryVoucher().getId(),
							voucher1.getCategoryVoucher().getId());
				}
			});
		}
//		Customer user = sessionService.get("user");
		User user = session.get("user");
		Customer customer = null;
		if (user != null) {
			customer = customerService.findById(user.getUsername()).get();
		}
		// Xử lí thêm order
		Order order = new Order();
		order.setCreatedDay(new Date());
		order.setNote("Đóng gói kĩ và giao vào giờ hành chính");
		order.setAddress(customer.getAddress());
		order.setPhone(customer.getPhoneAddress());
//		order.setTotalShip(0.0);
		order.setCustomerOrder(customer);
		order.setOrderStatus(orderStatusService.findById(1001).get());
		// Xử lí phương thức thanh toán
		String payment = session.get("payment", "cash");
		if (payment.equals("paypal")) {
			Payment p = paymentService.findById(1003).get();
			order.setPayment(p);
		} else if (payment.equals("vnpay")) {
			Payment p = paymentService.findById(1002).get();
			order.setPayment(p);
		} else {
			Payment p = paymentService.findById(1001).get();
			order.setPayment(p);
		}
		// Tiền ship
		List<String> listShop = new ArrayList<>();
		for (CartDetailDTo item : listOrder) {
			if (!listShop.contains(item.getIdShop())) {
				listShop.add(item.getIdShop());
			}
		}
		Double ship = (double) (15000 * listShop.size());
		order.setTotalShip(ship);
		// Tổng tiền sản phẩm
		order.setTotal(listOrder.stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum());
		orderService.save(order);

		// biến tổng tiền để tính voucher
		Double total = order.getTotal();
		Double totalShip = order.getTotalShip();
		// Xử lí voucher
		if (listVoucher != null) {

			for (Voucher item : listVoucher) {
				System.out.println(item.getName());
				Order orders = orderService.findLatestOrder();
				OrderDiscount od = new OrderDiscount();
				Double sale = 0.0;
				if (item.getCategoryVoucher().getId() == 100004) { // Voucher product
					Double price = 0.0;
					// Xử lí các mặt hàng được giảm giá
					for (CartDetailDTo it : listOrder) {
						for (VoucherProduct vp : voucherProductService.findAllByVoucher(item.getId())) {
							if (it.getIdProduct().equals(vp.getProduct().getId())) {
								price += (it.getPrice() * it.getQuantity());
								System.out.println(price);
							}
						}
					}
					// Xử lí giá giảm
					if (item.getDiscount() < 1) {
						total -= price;
						sale = price * item.getDiscount();
						price -= sale;
						if (price < 0)
							price = 0.0;
						total += price;
					} else {
						total -= price;
						sale = item.getDiscount();
						price -= sale;
						if (price < 0)
							price = 0.0;
						total += price;
					}
					System.out.println(total);
				} else if (item.getCategoryVoucher().getId() == 100003) { // Vouher all shop
					Double price = 0.0;
					// Xử lí các mặt hàng được giảm giá
					for (CartDetailDTo it : listOrder) {
						if (it.getIdShop().equals(item.getCreator().getUsername())) {
							price += (it.getPrice() * it.getQuantity());
							System.out.println(price);
						}
					}

					// Xử lí giá giảm
					if (item.getDiscount() < 1) {
						total -= price;
						sale = price * item.getDiscount();
						price -= sale;
						if (price < 0)
							price = 0.0;
						total += price;
					} else {
						total -= price;
						sale = item.getDiscount();
						price -= sale;
						if (price < 0)
							price = 0.0;
						total += price;
					}
					System.out.println(total);
				} else if (item.getCategoryVoucher().getId() == 100002) { // Voucher ship
//					Double price = 0.0;
					// Xử lí giá giảm
					if (item.getDiscount() < 1) {
//						total -= ship;
						sale = totalShip * item.getDiscount();
						totalShip -= sale;
						if (totalShip < 0)
							totalShip = 0.0;
//						total += price;
					} else {
//						total -= ship;
						sale = item.getDiscount();
						totalShip -= sale;
						if (totalShip < 0)
							totalShip = 0.0;
//						total += price;
					}
					System.out.println(total);
				} else if (item.getCategoryVoucher().getId() == 100001) { // Voucher Devex
//					Double price = listOrder.stream().mapToDouble(i -> item.getQuantity() * i.getPrice()).sum();

					// Xử lí giá giảm
					if (item.getDiscount() < 1) {
						sale = total * item.getDiscount();
						total -= sale;
						if (total < 0)
							total = 0.0;
//						total = price + ship;
					} else {
						sale = item.getDiscount();
						total -= sale;
						if (total < 0)
							total = 0.0;
//						total = price + ship;
					}
					System.out.println(total);
				}

				System.out.println(total);
				System.out.println(totalShip);
				System.out.println(1);
				orderService.updatePriceOrder(total, totalShip, orders.getId());
				System.out.println(2);
				od.setPriceDiscount(sale);
				od.setOrder(orders);
				od.setVoucher(item);
				System.out.println(3);
				orderDiscountService.save(od);
				System.out.println(new Date());
				// Giảm voucher đã sử dụng đi 1
				Voucher v = voucherService.findById(item.getId()).get();
				v.setQuantity(v.getQuantity() - 1);
				System.out.println(4);
				voucherService.save(v);
				System.out.println(5);
				voucherDetailService.appliedVoucher(customer.getUsername(), item.getId()); // chuyển trạng thái voucher
																							// thành đã áp dụng
			}

		}
		System.out.println(6);
		// Xử lí các mặt hàng
		for (CartDetailDTo item : listOrder) {
			OrderDetails orderDetails = new OrderDetails();
			Order orders = orderService.findLatestOrder();
			orderDetails.setOrder(orders);
			orderDetails.setPrice(item.getPrice());
//			CartDetail cartDetail = cartDetailService.getById(item.getId());
//			int id = cartDetail.getProductCart().getId();
			ProductVariant prod = productVariantService.findById(item.getIdProductVariant()).get();
			orderDetails.setProductVariant(prod);
			orderDetails.setQuantity(item.getQuantity());
			// Xử lí số lượng sản phẩm flash sale
			if (item.getIdFlashSale() != null) {
				FlashSale fl = flashSalesService.findById(item.getIdFlashSale()).get();
				if (item.getQuantity() <= fl.getAmountSell()) {
					fl.setAmountSell(fl.getAmountSell() - item.getQuantity());
					flashSalesService.save(fl);
				}

			}
			// Xử lí số lượng của sản phẩm
			int totalquantity = prod.getQuantity();
			int countquantity = totalquantity - item.getQuantity();
			productVariantService.updateQuantity(countquantity, prod.getId());
			orderDetails.setStatus(orderStatusService.findById(1001).get());
			orderDetailService.save(orderDetails);
			cartDetailService.deleteById(item.getId());
		}

		return "user/paymentSuccess";
	}

}
