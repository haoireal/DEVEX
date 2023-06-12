package com.hnbcoffee.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    
    @Column(name = "Total")
    private Double total;
    
    @Column(name = "Date")
    private Date date;
    
    @Column(name = "Payment")
    private String payment = "Tiền mặt";
    
    @Column(name = "Status")
    private boolean status = true;
    
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Customer_ID")
    private User customer;
    
    @OneToMany(mappedBy = "order")
	List<OrderDetail> orderDetail;
}
