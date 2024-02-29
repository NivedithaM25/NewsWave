package com.newswave.wishlist.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("wishlist")
public class WishListDTO {

	@Id
	private String userName;
	private List<WishList> wishlist;
}
