package com.Devex.Sevice.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Product;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.ProductService;

import jakarta.transaction.Transactional;

@SessionScope
@Service("productService")
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;

	@Override
	public Product save(Product entity) {
		entity.setId("1");
		return productRepository.save(entity);
	}

	@Override
	public Product findProductById(String id) {
		return productRepository.findProductById(id);
	}

	@Override
	public Optional<Product> findById(String id) {
		return productRepository.findById(id);
	}

	@Override
	public List<Product> saveAll(List<Product> entities) {
		return productRepository.saveAll(entities);
	}

	@Override
	public List<Product> findAll(Sort sort) {
		return productRepository.findAll(sort);
	}

	@Override
	public Optional<Product> findOne(Example<Product> example) {
		return productRepository.findOne(example);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> findAllById(Iterable<String> ids) {
		return productRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@Override
	public void deleteById(String id) {
		productRepository.deleteById(id);
	}

	@Override
	public Product getById(String id) {
		return productRepository.getById(id);
	}

	@Override
	public void delete(Product entity) {
		productRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		productRepository.deleteAll();
	}

	@Override

	public List<Product> findProductBySellerUsername(String sellerUsername) {
		return productRepository.findProductBySellerUsername(sellerUsername);
	}

	@Override
	public List<Product> findByKeywordName(String keyword) {

		return productRepository.findByKeywordName(keyword);
	}

	@Override
	public long countByKeywordName(String keywords) {
		return 0;
	}

	@Override
	public List<Product> findProductsByCategoryId(int cID) {
		return productRepository.findProductsByCategoryId(cID);
	}

	public List<Product> findAllProductById(String id) {
		return productRepository.findAllProductById(id);
	}

	@Override
	public Product findByIdProduct(String id) {
		return productRepository.findByIdProduct(id);
	}

	@Override
	@Transactional
	public void updateProduct(String id, String name, int brand, String description, Date createdDay, Boolean active,
			String sellerId, int categoryDetailsId) {
		productRepository.updateProduct(id, name, brand, description, createdDay, active, sellerId, categoryDetailsId);
	}

	@Override
	@Transactional
	public void updateProductIsDeleteById(boolean isdelete, String id) {
		productRepository.updateProductIsDeleteById(isdelete, id);
	}

	@Override
	public List<Product> findProductBySellerUsernameAndIsdeleteProduct(String sellerUsername) {
		return productRepository.findProductBySellerUsernameAndIsdeleteProduct(sellerUsername);
	}

	@Override
	public List<Product> findProductBySellerUsernameAndIsdeleteTrueAndActiveTrueProduct(String sellerUsername) {
		return productRepository.findProductBySellerUsernameAndIsdeleteTrueAndActiveTrueProduct(sellerUsername);
	}

	@Override
	public long getProductCount() {
        return productRepository.count();
    }

	@Override
	@Transactional
	public void insertProduct(String id, String name, int brand, String description, Date createdDay, Boolean active,
			Boolean isdelete, String shopId, Integer categoryId) {
		productRepository.insertProduct(id, name, brand, description, createdDay, active, isdelete, shopId, categoryId);
	}

	@Override
	public Product findLatestProductBySellerUsername(String username) {
		return productRepository.findLatestProductBySellerUsername(username);
	}
	
	

}
