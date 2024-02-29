package com.newsapp.news.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.newsapp.news.response.Response;

@ExtendWith(MockitoExtension.class)
public class NewsServiceImplTest {

	
	@Mock
    private ResponseFilter responseFilter;

    @InjectMocks
    private NewsServiceImpl newsService;

    @Test
    void getAppleNews_ValidDates_ReturnsListOfResponses() {
        // Mock data
        String fromDate = "2023-12-01";
        String toDate = "2023-12-05";
        List<Response> mockedResponse = List.of(new Response(1,"Sample Title","Sample Description","Sample URL","Image Url","business"),
        		new Response(2,"Sample Title1","Sample Description1","Sample URL1","IMage Url","business"));

        // Mock behavior
        when(responseFilter.doResponseFilter(anyString(),anyString())).thenReturn(mockedResponse);

        // Test method
        List<Response> result = newsService.getAppleNews(fromDate, toDate);

        // Assertions
        assertEquals(mockedResponse, result);
    }

    @Test
    void getTeslanews_ValidDate_ReturnsListOfResponses() {
        // Mock data
        String fromDate = "2023-12-01";
        List<Response> mockedResponse = List.of(new Response(1,"Sample Title","Sample Description","Sample URL","image URl","entertainment"));

        // Mock behavior
        when(responseFilter.doResponseFilter(anyString(),anyString())).thenReturn(mockedResponse);

        // Test method
        List<Response> result = newsService.getTeslanews(fromDate);

        // Assertions
        assertEquals(mockedResponse, result);
    }
}
