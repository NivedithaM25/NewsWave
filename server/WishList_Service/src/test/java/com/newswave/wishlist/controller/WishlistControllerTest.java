//package com.newswave.wishlist.controller;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.newswave.wishlist.model.WishList;
//import com.newswave.wishlist.response.WishListResponse;
//import com.newswave.wishlist.service.WishListService;
//
//
//
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(WishlistController.class)
//class WishlistControllerTest {
//	
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private WishListService wishListService;
//
//    @InjectMocks
//    private WishlistController wishlistController;
//
//    @Test
//    void getAllFavorites_ReturnsAllFavoriteNews_Successfully() throws Exception {
//        // Mock data
//        String userName = "testUser";
//        List<WishList> wishList = new ArrayList<>();
//        wishList.add(new WishList(1,"Title 1", "Description 1", "URL 1","image url1","category"));
//        wishList.add(new WishList(2,"Title 2", "Description 2", "URL 2","imageurl2","category"));
//
//        // Mock behavior
//        when(wishListService.getAllFavoriteNews(userName)).thenReturn(wishList);
//
////        // Test method
////        ResponseEntity<WishListResponse> responseEntity = wishlistController.getAllFavouriteNews("token",userName);
////
////        // Assertions
////        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
////        assertEquals(wishList, responseEntity.getBody());
//        when(wishListService.getAllFavoriteNews(anyString())).thenReturn(wishList);
//
//        mockMvc.perform(get("/getAllFavorites/" + userName)
//                .header("Authorization", "yourAuthorizationToken"))
//                .andExpect(status().isOk());
//    }
//
////    @Test
////    void addNewsToWishList_AddsNewsToFavorites_Successfully() throws Exception {
////        // Mock data
////        String user = "testUser";
////        WishList wishList = new WishList("Title 1", "Description 1", "URL 1");
////
////        // Mock behavior
////        when(wishListService.addNewsToWishList(wishList, user)).thenReturn(
////                WishListResponse.builder().userName(user).message("Added to Favourites!").build());
////
////        // Test method
////        ResponseEntity<?> responseEntity = wishlistController.addNewsToWishList(wishList, user);
////
////        // Assertions
////        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
////    }
//
////    @Test
////    void deleteNewsArticle_DeletesNewsArticle_Successfully() {
////        // Mock data
////        String userName = "testUser";
////        String title = "TitleToDelete";
////
////        // Mock behavior
////        when(wishListService.deleteWishListByTitle(userName, title)).thenReturn(
////                WishListResponse.builder().userName(userName).message("Deleted " + title).build());
////
////        // Test method
////        ResponseEntity<?> responseEntity = wishlistController.deleteNewsArticle(title, userName);
////
////        // Assertions
////        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
////    }
//}