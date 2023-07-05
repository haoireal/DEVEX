package com.Devex.DTO;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ColorDTO{

	/**
	 * 
	 */
	
	private int id;
	private String name;
	
}
