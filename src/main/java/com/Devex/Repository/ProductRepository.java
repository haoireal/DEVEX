package com.Devex.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.DTO.ProductDTO;
import com.Devex.DTO.infoProductDTO;
import com.Devex.Entity.Product;

@EnableJpaRepositories
@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product, String> {

	@Query("SELECT p FROM Product p WHERE p.id = ?1")
	Product findByIdProduct(String id);

	@Query("SELECT DISTINCT p FROM Product p " + "JOIN FETCH p.sellerProduct s "
			+ "WHERE s.username like ?1 OR s.shopName Like ?1")
	List<Product> findProductBySellerUsername(String sellerUsername);
	
	@Query("SELECT DISTINCT p FROM Product p " +
			"WHERE p.sellerProduct.username like ?1 AND p.isdelete = false")
	List<Product> findProductBySellerUsernameAndIsdeleteProduct(String sellerUsername);

	@Query(value = "EXEC FindProductsByKeyword :keywords", nativeQuery = true)
	List<Product> findByKeywordName(@Param("keywords") String keywords);

	@Query(value = "EXEC FillerProductBy :keywords, :sortby, :sorthow", nativeQuery = true)
	List<Product> fillerProductBy(@Param("keywords") String keywords, @Param("sortby") String sortby,
			@Param("sorthow") String sorthow);

	@Query("SELECT p FROM Product p WHERE p.id = ?1")
	List<Product> findAllProductById(String id);

	@Query("SELECT p FROM Product p WHERE p.categoryDetails.category.id = :categoryId")
	List<Product> findProductsByCategoryId(@Param("categoryId") int categoryId);

	@Query("SELECT COUNT(o) FROM Product o WHERE o.name LIKE :keywords")
	long countByKeywordName(@Param("keywords") String keywords);
	
	 @Query("select p from Product p join p.sellerProduct s where p.id = :id")
	 Product findProductById( String id);
	
	@Modifying
	@Query("UPDATE Product p SET p.name = :name, p.productbrand.id = :brand, p.description = :description, p.createdDay = :createdDay, p.active = :active, p.sellerProduct.username = :sellerId, p.categoryDetails.id = :categoryDetailsId WHERE p.id = :id")
	void updateProduct(@Param("id") String id, @Param("name") String name, @Param("brand") int brand, @Param("description") String description, @Param("createdDay") Date createdDay, @Param("active") Boolean active, @Param("sellerId") String sellerId, @Param("categoryDetailsId") int categoryDetailsId);

	@Modifying
	@Query(value = "UPDATE Product SET Isdelete = :isdelete WHERE ID = :id", nativeQuery = true)
	void updateProductIsDeleteById(@Param("isdelete") boolean isdelete, @Param("id") String id);
	
	@Query("SELECT DISTINCT p FROM Product p " + "JOIN FETCH p.sellerProduct s "
			+ "WHERE s.username like ?1 AND p.isdelete = true AND p.active = true")
	List<Product> findProductBySellerUsernameAndIsdeleteTrueAndActiveTrueProduct(String sellerUsername);

	@Modifying
	@Query(value = "INSERT INTO Product (ID, Name, Description, Createdday, Active, Isdelete, Shop_ID, Category_ID, Brand_ID) VALUES (:id, :name, :description, :createdDay, :active, :isdelete, :shopId, :categoryId, :brand)", nativeQuery = true)
	void insertProduct(@Param("id") String id, @Param("name") String name, @Param("brand") int brand,
			@Param("description") String description, @Param("createdDay") Date createdDay,
			@Param("active") Boolean active, @Param("isdelete") Boolean isdelete, @Param("shopId") String shopId,
			@Param("categoryId") Integer categoryId);
	
	@Query("SELECT p FROM Product p WHERE p.sellerProduct.username = :username ORDER BY p.createdDay DESC LIMIT 1")
	Product findLatestProductBySellerUsername(@Param("username") String username);
	
	@Query("SELECT COUNT(p) FROM Product p WHERE p.sellerProduct.username like ?1")
	int getCountProductBySellerUsername(String username);
//
//	@Query(" SELECT p FROM Product p JOIN p.seller s WHERE p.name LIKE %:productName% AND s.address LIKE %:sellerAddress%")
//    List<Product> findByPlaceOfSale(String keyWordName ,String keyWordAddress);
//	@Quẻy 
	
	@Query("SELECT p FROM Product p " +
	           "JOIN p.productVariants pv " +
	           "JOIN pv.listFlashSale fs " +
	           "JOIN fs.flashSaleTime fst " +
	           "WHERE fst.ID = :flashSaleTimeId AND fs.status = :flashSaleStatus")
	    List<Product> findProductsByFlashSaleTimeAndStatus(
	        @Param("flashSaleTimeId") int flashSaleTimeId,
	        @Param("flashSaleStatus") Boolean flashSaleStatus
	    );

	@Query("SELECT COUNT(p) FROM Product p " + 
			"JOIN p.productVariants pv " + 
			"JOIN pv.orderDetails od " +
			"JOIN od.order o " + 
			"JOIN p.sellerProduct s " +
			"WHERE s.username = :username " +
			"AND o.orderStatus.id = :statusorderid " +
			"AND od.status.id = :statusorderdetailsid ")
	int getCountProductSellBySellerUsername(@Param("username") String username, @Param("statusorderid") int statusorderid, @Param("statusorderdetailsid") int statusorderdetailsid);

	@Query("SELECT p FROM Product p " + 
			"JOIN p.productVariants pv " + 
			"JOIN pv.orderDetails od " +
			"JOIN od.order o " + 
			"JOIN p.sellerProduct s " +
			"JOIN p.categoryDetails cd " +
			"WHERE cd.id = :cateid " +
			"AND FUNCTION('YEAR', o.createdDay) = :year " +
			"ORDER BY p.soldCount")
	List<Product> getListProductByCategoryDetailsIdAndYear(@Param("cateid") int cateid, @Param("year") int year);
	
	@Query("SELECT p.id FROM Product p JOIN p.productVariants pv WHERE pv.id = :productVariantId")
    String findProductIdByProductVariantId(@Param("productVariantId") int productVariantId);
	
}
