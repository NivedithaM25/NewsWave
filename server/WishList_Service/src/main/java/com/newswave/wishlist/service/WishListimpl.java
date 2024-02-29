package com.newswave.wishlist.service;

import java.util.List;

import com.newswave.wishlist.model.WishList;
import com.newswave.wishlist.response.WishListResponse;

public interface WishListimpl {
	
	WishListResponse addNewsToWishList(WishList wishlist,String userName);
	List<WishList> getAllFavoriteNews(String userName);
	//String deleteNewsFromWishList(String title,String userName);
}
