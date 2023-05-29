package com.hnbcoffee.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnbcoffee.Entity.CartItem;
import com.hnbcoffee.Utils.DataSharing;

@Controller
@RequestMapping("/hnbcoffee")
public class BeverageDetailController {
	
	
	@GetMapping("/coffee/detail/{beverageId}")
    public String showProductDetail(@PathVariable("beverageId") Integer beverageId, Model model) {
		CartItem items = DataSharing.items.get(beverageId);
        model.addAttribute("beverage", items);
        return "user/beveragedetail";
    }
	
	@ModelAttribute("sizes")
	public Map<String, String> geSize() {
		Map<String, String> sizes = new HashMap<>();
		sizes.put("S", "0");
		sizes.put("M", "5000");
		sizes.put("L", "10000");

		return sizes;
	}
	

}
