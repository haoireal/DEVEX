package com.hnbcoffee.Controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hnbcoffee.DTO.ReportSale;
import com.hnbcoffee.Entity.Beverage;
import com.hnbcoffee.Sevice.OrderDetailService;
import com.hnbcoffee.Sevice.SessionService;

@Controller
@RequestMapping("/hnbcoffee/admin")
public class AdminReportSaleController {
	
	@Autowired
	SessionService session;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping("/sales")
	public String showReport(Model model) {
//		Pageable pageable = (Pageable) PageRequest.of(p.orElse(0), 5, Sort.by(Sort.Direction.DESC, field.orElse("qty")));
//		Page<ReportSale> page = orderDetailService.reportSale(pageable);
//		Sort sort = Sort.by(Direction.DESC, field.orElse("quantity"));
//		List<ReportSale> list = orderDetailService.reportSale(sort);
		List<ReportSale> list = orderDetailService.reportSale();
		model.addAttribute("list", list);
		return "admin/sales";
	}
	
}
