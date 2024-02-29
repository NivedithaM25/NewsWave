//package com.newswave.wishlist.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.fail;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//
//import com.newswave.wishlist.exception.AlreadyExistinList;
//import com.newswave.wishlist.exception.ArticleNotFound;
//import com.newswave.wishlist.model.WishList;
//import com.newswave.wishlist.model.WishListDTO;
//import com.newswave.wishlist.repository.WishlistRepository;
//import com.newswave.wishlist.response.WishListResponse;
//
//
//
///**
// * @author Niveditha
// *
// *This class test the service class implementations
// */
//@ExtendWith(MockitoExtension.class)
//public class WishListServiceTest {
//
//	
//		@Mock
//	    private WishlistRepository wishlistRepository;
//
//	    @InjectMocks
//	    private WishListService wishListService;
//	    
//	    @Mock
//	    private MongoTemplate mongoTemplate;
//
//
//	    @Test
//	    void deleteWishListByTitleSuccessfullyDeleted() {
//	        // Mock data
//	        String userName = "testUser";
//	        String title = "Sample Title";
//
//	        Query query = new Query(Criteria.where("userName").is(userName));
//	        Update update = new Update().pull("wishlist", Query.query(Criteria.where("title").is(title)));
//
//	        // Stubbing the mongoTemplate behavior
//	        when(mongoTemplate.updateFirst(query, update, WishListDTO.class)).thenReturn(null);
//
//	        // Invoke the method
//	        WishListResponse response = wishListService.deleteWishListByTitle(userName, title);
//
//	        // Verify the method behavior
//	        assertNotNull(response);
//	        assertEquals("Deleted" + title, response.getMessage());
//	        assertEquals(userName, response.getUserName());
//
//	        // Verify if the updateFirst method is called with correct arguments
//	        verify(mongoTemplate, times(1)).updateFirst(query, update, WishListDTO.class);
//	    }
//	    
//	    @Test
//	    void addNewsToWishListEmptyWishlistEntryReturnsErrorMessage() throws AlreadyExistinList {
//	        // Setup
//	        WishList wishlist = new WishList(0, "", "","","",""); // Empty wishlist
//	        String userName = "testUser";
//
//	        // Execution
//	        WishListResponse response = wishListService.addNewsToWishList(wishlist, userName);
//
//	        // Assertion
//	        assertNotNull(response);
//	        assertEquals("Wishlist entry is null or empty!", response.getMessage());
//	        assertEquals(userName, response.getUserName());
//	    }
//	    
//	    
//	    
//	    @Test
//	    void addNewsToWishListNullTitleReturnsErrorMessage() throws AlreadyExistinList {
//	        // Mock data
//	        String userName = "testUser";
//	        WishList wishlist = new WishList(0,"", "Description", "URL"."image url","category");
//
//	        // Test method
//	        WishListResponse response = wishListService.addNewsToWishList(wishlist, userName);
//
//	        // Assertions
//	        assertNotNull(response);
//	        assertEquals("Wishlist entry is null or empty!", response.getMessage());
//	    }
//
//	    @Test
//	    void addNewsToWishList_DuplicateTitle_ThrowsAlreadyExistinList() {
//	        // Mock data
//	        String userName = "testUser";
//	        WishList wishlist = new WishList(1,"Title", "Description", "URL","image url","category");
//	        List<WishList> existingWishlist = new ArrayList<>();
//	        existingWishlist.add(new WishList(2,"Title", "Description1", "URL1","image url","category"));
//	        WishListDTO wishListDTO = new WishListDTO(userName, existingWishlist);
//
//	        // Mock behavior
//	        when(wishlistRepository.findByUserName(userName)).thenReturn(wishListDTO);
//
//	        // Test method and assertion
//	        AlreadyExistinList exception = assertThrows(
//	            AlreadyExistinList.class,
//	            () -> wishListService.addNewsToWishList(wishlist, userName)
//	        );
//	        assertEquals("Title already there in List...", exception.getMessage());
//	    }
//
//	    @Test
//	    void addNewsToWishList_ValidEntry_SuccessfullyAddedToFavourites() throws AlreadyExistinList {
//	        // Mock data
//	        String userName = "testUser";
//	        WishList wishlist = new WishList("New Title", "Description", "URL");
//	        WishListDTO emptyWishListDTO = new WishListDTO(userName, new ArrayList<>());
//
//	        // Mock behavior
//	        when(wishlistRepository.findByUserName(userName)).thenReturn(emptyWishListDTO);
//
//	        // Test method
//	        WishListResponse response = wishListService.addNewsToWishList(wishlist, userName);
//
//	        // Assertions
//	        assertNotNull(response);
//	        assertEquals("Added to Favourites!", response.getMessage());
//	    }
//	    
//	    
//	    @Test
//	    void getAllFavoriteNews_EmptyWishlist_ReturnsNull() {
//	        // Mock data
//	        String userName = "testUser";
//	        WishListDTO emptyWishListDTO = new WishListDTO(userName, new ArrayList<>());
//
//	        // Mock behavior
//	        when(wishlistRepository.findByUserName(userName)).thenReturn(emptyWishListDTO);
//
//	        // Test method
//	        List<WishList> result = wishListService.getAllFavoriteNews(userName);
//
//	        // Assertions
//	        assertNull(result);
//	    }
//
//	    @Test
//	    void getAllFavoriteNews_NonEmptyWishlist_ReturnsWishlist() {
//	        // Mock data
//	        String userName = "testUser";
//	        List<WishList> expectedWishlist = new ArrayList<>();
//	        expectedWishlist.add(new WishList("Title1", "Description1", "URL1"));
//	        expectedWishlist.add(new WishList("Title2", "Description2", "URL2"));
//	        WishListDTO nonEmptyWishListDTO = new WishListDTO(userName, expectedWishlist);
//
//	        // Mock behavior
//	        when(wishlistRepository.findByUserName(userName)).thenReturn(nonEmptyWishListDTO);
//
//	        // Test method
//	        List<WishList> result = wishListService.getAllFavoriteNews(userName);
//
//	        // Assertions
//	        assertNotNull(result);
//	        assertEquals(expectedWishlist.size(), result.size());
//	        assertEquals(expectedWishlist.get(0).getTitle(), result.get(0).getTitle());
//	        assertEquals(expectedWishlist.get(1).getDescription(), result.get(1).getDescription());
//	        assertEquals(expectedWishlist.get(1).getUrl(), result.get(1).getUrl());
//	    }
//}
