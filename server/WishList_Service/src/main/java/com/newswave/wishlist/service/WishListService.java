package com.newswave.wishlist.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.newswave.wishlist.exception.AlreadyExistinList;
import com.newswave.wishlist.model.WishList;
import com.newswave.wishlist.model.WishListDTO;
import com.newswave.wishlist.repository.WishlistRepository;
import com.newswave.wishlist.response.WishListResponse;

import lombok.extern.slf4j.Slf4j;


/**
 * @author niveditha
 *
 *
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "blogCache")
public class WishListService implements WishListimpl{

	@Autowired
	private WishlistRepository wishlistRepo;
	
	@Autowired
	 private MongoTemplate mongoTemplate;

//	    public WishListService(MongoTemplate mongoTemplate) {
//	        this.mongoTemplate = mongoTemplate;
//	    }
	  
	    /**
	     * @param userName
	     * @param title
	     * @return
	     */
	    @CacheEvict(value = "blogCache",allEntries = true)
	    public WishListResponse deleteWishListByTitle(String userName, String title) {

	        List<WishList> allFavs = getAllFavoriteNews(userName);

	        // Encode the title to handle special characters
	        String encodedTitle = UriUtils.encode(title, "UTF-8");

	        if (allFavs.isEmpty() || !allFavs.contains(encodedTitle)) {
	            return WishListResponse.builder().message(encodedTitle + " doesn't exist in wishList!").build();
	        }

	        Query query = new Query(Criteria.where("userName").is(userName));
	        Update update = new Update().pull("wishlist", Query.query(Criteria.where("title").is(encodedTitle)));
	        mongoTemplate.updateFirst(query, update, WishListDTO.class);

	        return WishListResponse.builder().message("Deleted " + encodedTitle).userName(userName).build();
	    }
	    
	    @CacheEvict(value = "blogCache",allEntries = true)
	    public WishListResponse deleteWishlistItem(String userName, int id, String category) {
	    	
	    	Optional<WishListDTO> userWishList=wishlistRepo.findByUserName(userName);
	    	if(userWishList.isEmpty()) {
				return WishListResponse.builder().message("WishList is Empty!").userName(userName).build();
		         
			}
	    	WishListDTO wishList = userWishList.get();
			
			//return wishList.get().getWishlist();
	    	
	    	List<WishList> allFavs=wishList.getWishlist();
	    	
	    	boolean removed = allFavs.removeIf(wishlist->id==wishlist.getId());
	    	if (!removed) {
	    		return WishListResponse.builder().message("WishList entry with ID: " + id + " and category: " + category + " not found").userName(userName).build();
	    	}
	    	wishList.setWishlist(allFavs);
	    	wishlistRepo.save(wishList);
	    	
	    		    	
	       return WishListResponse.builder().message("Successfully delted WishList entry with ID: " + id + " and category: " + category ).userName(userName).build();
	    }
	    
	    
//	    Query query = new Query(Criteria.where("_id").is(userName));
//        Update update = new Update().pull("wishlist", new Query(Criteria.where("_id").is(id).and("category").is(category)));
//
//        try {
//            mongoTemplate.updateFirst(query, update, "wishlist");
//            return WishListResponse.builder()
//                    .message("Deleted Wishlist entry with ID: " + id + " and category: " + category)
//                    .userName(userName)
//                    .build();
//        } catch (Exception e) {
//            return WishListResponse.builder()
//                    .message("Error deleting Wishlist entry: " + e.getMessage())
//                    .userName(userName)
//                    .build();
//        }
//	    public WishListResponse deleteWishListByIdAndCategory(String userName, int id, String category) {
//	        try {
//	            MongoCollection<Document> collection = mongoTemplate.getCollection("wishlist");
//
//	            // Construct the filter to match the specified fields
//	            Bson filter =Filters.and(
//	                Filters.eq("userName", userName),
//	                Filters.eq("wishlist.id", id),
//	                Filters.eq("wishlist.category", category)
//	            );
//
//	            // Delete the document that matches the filter
//	            collection.deleteOne(filter);
//	            
//	            return WishListResponse.builder().message("Deleted WishList entry with ID: " + id + " and category: " + category).userName(userName).build();
//	        } catch (Exception e) {
//	            return WishListResponse.builder().message("Failed to delete: " + e.getMessage()).userName(userName).build();
//	        }
//	    }
//	    public WishListResponse deleteWishListByIdAndCategory(String userName,int id, String category) {
//	    	
//	    	 Optional<WishList> wishListItem = wishlistRepo.findByUserNameAndWishlistIdAndWishlistCategory(userName, id, category);
//	    	 System.out.println("wishlistItem:"+wishListItem);;
//	    	 if (wishListItem.isPresent()) {
//	    		 wishlistRepo.deleteWishlistByUserNameAndWishlistIdAndWishlistCategory(userName, id, category);
//	         } else {
//	        	 return WishListResponse.builder().message("WishList entry with ID: " + id + " and category: " + category + " not found").userName(userName).build();
//	         }
////	    	List<WishList> allFavs = getAllFavoriteNews(userName);
////	    	log.info(allFavs.toString());
////	    	boolean idExists = false;
////
////	        // Iterate through the list to find if the id exists
////	    	Iterator<WishList> iterator = allFavs.iterator();
////	    	while (iterator.hasNext()) {
////	    	    WishList wishList = iterator.next();
////	    	    if (wishList.getId() == id) {
////	    	    	idExists=true;
////	    	        iterator.remove(); // Remove the element from the list
////	    	        break; // Stop the loop once the item is removed
////	    	    }
////	    	}
////	    	log.info(allFavs.toString());
////	        if (allFavs.isEmpty() || !idExists) {
////	            return WishListResponse.builder().message(id + " doesn't exist in wishList!").build();
////	        }
////	        
////	        updateUserFavoriteNews(userName, allFavs);
//
//	        return WishListResponse.builder().message("Deleted WishList entry with ID: " + id + " and category: " + category).userName(userName).build();
//	    }
	    
	    private void updateUserFavoriteNews(String userName, List<WishList> updatedList) {
	    	 List<WishList> existingList =getAllFavoriteNews(userName);
	    	 for (WishList updatedItem : updatedList) {
	    		 addNewsToWishList(updatedItem,userName);
	         }
	    }
	    
//	    public WishListResponse deleteWishListByTitle(String userName, String title) {
//	    	
//	    	List<WishList> allFavs=getAllFavoriteNews(userName);
//	    	if(allFavs.isEmpty() || !allFavs.contains(title)) {
//	    		return WishListResponse.builder().message(title+" doesn't exist in wishList!").build();
//	    	}
//	    	Query query = new Query(Criteria.where("userName").is(userName));
//	        Update update = new Update().pull("wishlist", Query.query(Criteria.where("title").is(title)));
//	        mongoTemplate.updateFirst(query, update, WishListDTO.class);
//	        
//	        return WishListResponse.builder().message("Deleted "+title).userName(userName).build();
//	    }
	
	/**
	 * To save the wish list into the existing wishlist if exists else will create new wishlist
	 * 
	 * @param wishlist object contains title,description,url
	 * 
	 * @param username
	 */
	@Override
	@CachePut(value="blogCache")
    @CacheEvict(allEntries = true)
	public WishListResponse addNewsToWishList(WishList wishlist, String userName) throws AlreadyExistinList{
		if(wishlist.getTitle().isEmpty() || wishlist.getDescription().isEmpty() || wishlist.getUrl().isEmpty()) {
			return WishListResponse.builder().userName(userName).message("Wishlist entry is null or empty!").build();
			
		}
		Optional<WishListDTO> wishListDTO = wishlistRepo.findByUserName(userName);
		log.info("WISHLIST_DTO....",wishListDTO);
		if(wishListDTO == null || wishListDTO.isEmpty()) {
			List<WishList> list=new ArrayList<>();
			list.add(wishlist);
			
			WishListDTO addingList=new WishListDTO();
			addingList.setUserName(userName);
			addingList.setWishlist(list);
			
			wishlistRepo.save(addingList);
		}else {
			
			List<WishList> list=wishListDTO.get().getWishlist();
			if(checkDuplicate(list, wishlist.getTitle())) {
				throw new AlreadyExistinList("Title already there in List...");
			}
			log.info(list.toString());
			list.add(wishlist);
			
			WishListDTO addingList=new WishListDTO();
			addingList.setUserName(userName);
			addingList.setWishlist(list);
			
			wishlistRepo.save(addingList);
		}
		return WishListResponse.builder().userName(userName).message("Added to Favourites!").build();
	}

	/**
	 * To get all the favorite news articles in the wishlist
	 * 
	 */
	@Override
	@Cacheable(value = "blogCache")
	
	public List<WishList> getAllFavoriteNews(String userName) {
		Optional<WishListDTO> wishList=wishlistRepo.findByUserName(userName);
		
		
		if(wishList.isEmpty()) {
			return new ArrayList<>();
		}
		return wishList.get().getWishlist();
	}



	/**
	 * To delete new articles from favourites
	 * @param userName
	 * @param title 
	 */
//	@Override
//	@CacheEvict(value = "blogCache", allEntries = true)
//	public String deleteNewsFromWishList(String title, String userName) {
//	    WishListDTO wishListDTO = wishlistRepo.findByUserName(userName);
//	    if (wishListDTO != null && wishListDTO.getWishlist() != null && !wishListDTO.getWishlist().isEmpty()) {
//
//	        List<WishList> list = wishListDTO.getWishlist();
//	        if (CheckDuplicate(list, title)) {
//	        	WishList listTodelte=new WishList();
//	        	for (WishList wishList : list) {
//	    			
//	    			if(wishList.getTitle().equals(title)) {
//	    				listTodelte.setTitle(title);
//	    				listTodelte.setDescription(wishList.getDescription());
//	    				listTodelte.setUrl(wishList.getUrl());
//	    			}
//	    		}
//	        	//wishlistRepo.deleteByWishList(listTodelte, userName);
//	            //wishlistRepo.deleteByTitle(title);
//	            return "Successfully deleted article from favs";
//	        }
//	    }
//
//	    throw new ArticleNotFound("Title Not Found");
//	}
//	
//	public WishListResponse deleteWishListEntryByTitle(String userName, String title) {
//        wishlistRepo.deleteByUserNameAndWishlistTitle(userName, title);
//        
//        return WishListResponse.builder().message("Deleted"+title).userName(userName).build();
//    }
//	@Override
//	@CacheEvict(value = "blogCache",allEntries = true)
//	public String deleteNewsFromWishList(String title, String userName) {
//		WishListDTO wishListDTO = wishlistRepo.findByUserName(userName);
//		if(wishListDTO != null || wishListDTO.getWishlist() != null || !wishListDTO.getWishlist().isEmpty() ) {
//			
//			List<WishList> list=wishListDTO.getWishlist();
//			if(CheckDuplicate(list, title)) {
//				/*
//				 * Optional<WishList> wishListToDelete = list.stream()
//				 * .filter(wishList->wishList.getTitle().equals(title)) .findFirst();
//				 */
//				if(wishlistRepo.deleteWishList(title)) {
//					return "Successfully deleted article from favs";
//				}
//			}
//			
//		}
//		
//		
//		throw new ArticleNotFound("Title Not Found");
//	}
	
//	public WishListDTO updateWishlist(String userName, WishList newWish) {
//        WishListDTO wishListDTO = wishlistRepo.findByUserName(userName);
//
//        if (wishListDTO == null || wishListDTO.getWishlist() == null || wishListDTO.getWishlist().isEmpty()) {
//            // If the wishlist doesn't exist or is empty, create a new record
//            List<WishList> newWishlist = new ArrayList<>();
//            newWishlist.add(newWish);
//            wishListDTO = new WishListDTO();
//            wishListDTO.setUserName(userName);
//            wishListDTO.setWishlist(newWishlist);
//        } else {
//            // Wishlist exists, add the new wish to the existing wishlist
//            List<WishList> currentWishlist = wishListDTO.getWishlist();
//            currentWishlist.add(newWish);
//            wishListDTO.setWishlist(currentWishlist);
//        }
//
//        return wishlistRepo.save(wishListDTO);
//    }
	
	
	/**
	 * @param list
	 * @param title
	 * @return
	 */
	public boolean checkDuplicate(List<WishList> list,String title) {
		 if (list == null) {
		        // Handle the case where the list is null, possibly throw an exception or return false
		        return false;
		    }
		for (WishList wishList : list) {
			
			if(wishList.getTitle().equals(title)) {
				return true;
			}
		}
		return false;
	}
}
