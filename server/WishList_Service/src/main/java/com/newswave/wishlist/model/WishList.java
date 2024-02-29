package com.newswave.wishlist.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishList {
	
	
	private int id;
	private String title;
	private String description;
	private String url;
	private String imageUrl;
	private String category;
}
