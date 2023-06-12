package com.hnbcoffee.DTO;


import java.io.Serializable;

import com.hnbcoffee.Entity.Beverage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopBeverage implements Serializable{
	@Id
	Beverage beverage;
	long sum;
}
