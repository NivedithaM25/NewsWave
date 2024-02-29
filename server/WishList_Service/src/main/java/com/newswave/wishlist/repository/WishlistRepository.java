package com.newswave.wishlist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.newswave.wishlist.model.WishList;
import com.newswave.wishlist.model.WishListDTO;

@Repository
public interface WishlistRepository extends MongoRepository<WishListDTO,Integer>{

	@Query("{'userName':?0}")
	//@Query(value = "{ 'userName' : ?0 }", fields = "{ 'wishList' : 1, '_id' : 0 }")
	Optional<WishListDTO> findByUserName(String userName);

	@Query("{'title' : ?0}")
	void deleteByTitle(String title);
	
	//void deleteByWishList(WishList wishList, String userName);
	
    @Query(value = "{'userName' : ?0, 'wishlist.title' : ?1}", delete = true)
    void deleteByUserNameAndWishlistTitle(String userName, String title);

    @Query(value = "{'userName' : ?0, 'wishlist.id' : ?2, 'wishlist.category':?1}",delete=true)
    void deleteWishListByIdAndCategory(String userName,int id, String category);
    @Query(value = "{'userName' : ?0, 'wishlist.id' : ?2, 'wishlist.category':?1}")
    Optional<WishList> findByUserNameAndWishlistIdAndWishlistCategory(String userName, int id, String category);
    
    void deleteWishlistByUserNameAndWishlistIdAndWishlistCategory(String userName, int id, String category);
}
