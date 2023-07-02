package com.Devex.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Comment")
public class Comment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	@Column(name = "Content")
	private String content;
	@Column(name = "Rating")
	private int rating;
	@Column(name = "CreatedAt")
	private Date createdAt;
	
	@ManyToOne
	@JoinColumn(name = "Product_ID")
	private Product productComment;
	
	@ManyToOne
	@JoinColumn(name = "Customer_ID")
	private Customer customerComment;
	
	@OneToMany(mappedBy = "comment")
	private List<CommentReply> commentReplies;

}
