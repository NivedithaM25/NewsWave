package com.newsapp.news.service;



import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.newsapp.news.exception.DataNotAvailableException;
import com.newsapp.news.response.Response;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

	//@Value("${API_KEY}")
	private String API_KEY="e9bb867e08384cfea76800e1b6fc3411";
	
	@Autowired
	ResponseFilter responseFilter;

	@Override
	public List<Response> getAppleNews(String fromDate, String toDate) {
		// TODO Auto-generated method stub
		String prefix="https://newsapi.org/v2/everything?q=apple&from=";
		String sufix="&sortBy=popularity&apiKey=";
		//to=2023-12-05
		return responseFilter.doResponseFilter(prefix+fromDate+"&to="+toDate+sufix+API_KEY,"business");
	}

	@Override
	public List<Response> getTeslanews(String fromDate) {

		String prefixUri = "https://newsapi.org/v2/everything?q=tesla&from=";
		String sufixUri = "&sortBy=publishedAt&apiKey=";
		return responseFilter.doResponseFilter(prefixUri + fromDate + sufixUri + API_KEY,"business");
	}

	@Override
	public List<Response> getHeadlines(String isoCode,String category) {
		// TODO Auto-generated method stub
		
		String prefix="https://newsapi.org/v2/top-headlines?country=";
		String sufix="&apiKey=";
		return responseFilter.doResponseFilter(prefix+isoCode+"&category="+category+sufix+API_KEY,category);
	}

	@Override
	public List<Response> getHeadlinesFromTechCrunch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Response> getArticlesByWallStreetjournals() {
		// TODO Auto-generated method stub
		return null;
	}

}
