package com.hnbcoffee.Entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.OneToMany;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
<<<<<<< HEAD:src/main/java/com/hnbcoffee/Entity/TypeOfBeverage.java

@Table(name = "TypeOfBeverage")
public class TypeOfBeverage implements Serializable {
=======
@Table(name = "Beverage_Category")
public class BeverageCategory implements Serializable {
>>>>>>> cb64f82f25dee311b943a2d3af14f87d4b392ab3:src/main/java/com/hnbcoffee/Entity/BeverageCategory.java
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	@Column(name = "Name")
	private String name;
	@OneToMany(mappedBy = "category")
	List<Beverage> beverages;
}
