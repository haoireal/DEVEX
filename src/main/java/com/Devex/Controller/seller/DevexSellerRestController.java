package com.Devex.Controller.seller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Devex.Entity.Category;
import com.Devex.Entity.CategoryDetails;
import com.Devex.Entity.User;
import com.Devex.Sevice.SessionService;
import com.Devex.Utils.FileManagerService;

import jakarta.websocket.server.PathParam;
@CrossOrigin("*")
@RestController
public class DevexSellerRestController {

	@Autowired
	FileManagerService fileManagerService;
	
	@Autowired
	SessionService session;
	
	@GetMapping("/img/product")
    public List<String> listImage() {
        // Kích hoạt FileManagerService
		String id = session.get("idproduct");
		System.out.println(id+"list");
        List<String> imageUrls = fileManagerService.list(id, "khanhtq");
        System.out.println(imageUrls.toString());
        return imageUrls;
    }
	
	@GetMapping("/img/product/{filename}")
	public byte[] download(@PathVariable("filename") String filename) {
		String id = session.get("idproduct");
		System.out.println(id+"dow");
		return fileManagerService.read("khanhtq", id, filename);
	}
	
	@DeleteMapping("/img/product/{filename}")
	public void delete(@PathVariable("filename") String filename) {
		String id = session.get("idproduct");
		System.out.println(id+"delete");
		fileManagerService.delete("khanhtq", id, filename);
	}
	
	@PostMapping("/img/product")
	public List<String> upload(@PathParam("files") MultipartFile[] files) {
		String id = session.get("idproduct");
		System.out.println(id+"up");
		return fileManagerService.save("khanhtq", id, files);
	}
	
//	@GetMapping("/categoryDetails/{idcategory}")
//    public List<Category> listcategory(@PathVariable("idproduct") String idproduct) {
//        // Kích hoạt FileManagerService
//        List<CategoryDetails> listcategory = fileManagerService.list(idproduct, "HappyHarvest");
//        System.out.println(imageUrls.toString());
//        return imageUrls;
//    }
	
}
