package com.newsapp.news.service;

import java.util.List;

import com.newsapp.news.response.Response;

public interface NewsService {

	public List<Response> getAppleNews(String fromDate,String toDate);
	public List<Response> getTeslanews(String fromDate);
	public List<Response> getHeadlines(String isoCode,String category);
	public List<Response> getHeadlinesFromTechCrunch();
	public List<Response> getArticlesByWallStreetjournals();
	
}
