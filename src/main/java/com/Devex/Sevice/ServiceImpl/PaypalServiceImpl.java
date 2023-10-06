package com.Devex.Sevice.ServiceImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Entity.Customer;
import com.Devex.Entity.User;
import com.Devex.Sevice.CustomerService;
import com.Devex.Sevice.PaypalService;
import com.Devex.Sevice.SessionService;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@SessionScope
@Service("paypalService")
public class PaypalServiceImpl implements PaypalService {
	@Autowired
	SessionService session;

	@Autowired
	CustomerService customerService;

	private static final String CLIENT_ID = "Ac2GkDKEx7G808mFaXRMq-0Zr5iiZs9hjF8ZeCqDzkNdX6kuPQDMwPk6Zzgq0cdSdX0GV-wV5vaHWkEc";
	private static final String CLIENT_SECRET = "EA0cQUuhUA-i4AlZNM0pvBNluJUgG3cH1XCt3DHHZirnGfu5_NcZl_lmbeix_5gmMBR5r5JmCmox1C0s";
	private static final String MODE = "sandbox";

	@Override
	public String authorizePayment(List<CartDetailDTo> cartDetailDTo) throws PayPalRESTException {

		Payer payer = getPayerInformation();
		System.out.println(1);
		RedirectUrls redirectUrls = getRedirectURLs();
		List<Transaction> listTransaction = getTransactionInformation(cartDetailDTo);
		System.out.println(2);

		Payment requestPayment = new Payment();
		requestPayment.setTransactions(listTransaction);
		System.out.println(3);
		requestPayment.setRedirectUrls(redirectUrls);
		System.out.println(4);
		requestPayment.setPayer(payer);
		System.out.println(5);
		requestPayment.setIntent("authorize");
		System.out.println(6);
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
		System.out.println(7);
		Payment approvedPayment = requestPayment.create(apiContext);
		System.out.println(8);
		return getApprovalLink(approvedPayment);

	}

	private Payer getPayerInformation() {
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		User user = session.get("user");
		Customer customer = customerService.findById(user.getUsername()).get();

		PayerInfo payerInfo = new PayerInfo();
		payerInfo.setFirstName(customer.getFullname())
				.setEmail("sb-h0j5e27353747@personal.example.com");

		payer.setPayerInfo(payerInfo);

		return payer;
	}

	private RedirectUrls getRedirectURLs() {
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://localhost:8888/cancel");
		redirectUrls.setReturnUrl("http://localhost:8888/order/success");

		return redirectUrls;
	}

	@Override
	public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
		return Payment.get(apiContext, paymentId);
	}

	@Override
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(payerId);

		Payment payment = new Payment().setId(paymentId);

		APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);

		return payment.execute(apiContext, paymentExecution);
	}

	private List<Transaction> getTransactionInformation(List<CartDetailDTo> listItemOrder) {
//        Details details = new Details();
//        details.setShipping(orderDetail.getShipping());
//        details.setSubtotal(orderDetail.getSubtotal());
//        details.setTax(orderDetail.getTax());
//     	
        Amount amount = new Amount();
        // Định dạng số tiền sử dụng DecimalFormat
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
        String formattedTotalAmount = decimalFormat.format(listItemOrder.stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum());
//        Double exponentialNumber = listItemOrder.stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
        System.out.println(formattedTotalAmount);
//        double normalNumber = Double.parseDouble(formattedTotalAmount);
        amount.setCurrency("USD");
        System.out.println(formattedTotalAmount);
        amount.setTotal(formattedTotalAmount);
//        amount.setDetails(details);
        
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(listItemOrder.stream()
        	    .map(item -> item.getNameProduct()) // Lấy tên của từng mục
        	    .collect(Collectors.joining(", ")));
//         
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
//         
        for (CartDetailDTo itemOrder : listItemOrder) {
	        Item item = new Item();
	        item.setCurrency("USD");
	        item.setName(itemOrder.getNameProduct());
	        System.out.println(decimalFormat.format(itemOrder.getPrice()));
	        String formattedPriceItem = decimalFormat.format(itemOrder.getPrice());
	        item.setPrice(formattedPriceItem);
	        System.out.println(item.getPrice());
	        
	//        item.setTax(orderDetail.getTax());
	        item.setQuantity(String.valueOf(itemOrder.getQuantity()));
	         
	        items.add(item);
        }
        
        itemList.setItems(items);
        transaction.setItemList(itemList);
//     
		List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);  

		return listTransaction;
	}

	private String getApprovalLink(Payment approvedPayment) {
		List<Links> links = approvedPayment.getLinks();
		String approvalLink = null;

		for (Links link : links) {
			if (link.getRel().equalsIgnoreCase("approval_url")) {
				System.out.println(9);
				approvalLink = link.getHref();
				System.out.println(approvalLink);
				break;
			}
		}

		return approvalLink;
	}

}
