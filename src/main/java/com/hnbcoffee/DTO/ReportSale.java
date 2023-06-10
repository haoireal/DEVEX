package com.hnbcoffee.DTO;

import java.io.Serializable;
import java.util.Date;

import com.hnbcoffee.Entity.Beverage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportSale implements Serializable{
	@Id
	Beverage beverage;
	long qty;
	double total;
}
