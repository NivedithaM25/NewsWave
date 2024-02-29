package com.newsapp.news.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.newsapp.news.response.Response;
import com.newsapp.news.service.NewsServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class NewsControllerTest {

	
	@Mock
    private NewsServiceImpl newsService;

    @InjectMocks
    private NewsController newsController;

    private MockMvc mockMvc;

    @Test
    void getHealthCheck_ReturnsHealthCheckString() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();

        mockMvc.perform(get("/newswave/Health-Check"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getBusinessHeadlines_ReturnsListOfResponses() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();

        // Mocking the newsService to return a list of responses
        List<Response> mockResponseList = new ArrayList<>();
        // Add mock responses to the list...

        when(newsService.getHeadlines(anyString(), anyString())).thenReturn(mockResponseList);

        ResponseEntity<List<Response>> expectedResponse = new ResponseEntity<>(mockResponseList, HttpStatus.OK);

        mockMvc.perform(get("/newswave/top-headlines/{isoCode}/{category}", "us", "business"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
