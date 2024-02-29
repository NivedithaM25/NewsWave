package com.newsapp.news.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.newsapp.news.response.Response;
import com.newsapp.news.service.NewsServiceImpl;


@RestController
@RequestMapping("/newswave")
@CrossOrigin(origins = "*")
public class NewsController {
	
	@Autowired
	private NewsServiceImpl newsService;
	
	@GetMapping("/Health-Check")
	public String getHealthCheck() {
		return "Health Check";
	}
	
	
				
	@GetMapping("/tesla/{date}")
	public ResponseEntity<?> getTeslaNews(@PathVariable String date){
		
		List<Response> response =newsService.getTeslanews(date);
		return new ResponseEntity(response,HttpStatus.OK);
	}
	
	@GetMapping("/Apple/{from_date}/{to_date}")
	public ResponseEntity<?> getAppleArticles(@PathVariable("from_date") String fromDate,
			@PathVariable("to_date") String toDate){
		
		List<Response> response= newsService.getAppleNews(fromDate,toDate);	
		return new ResponseEntity(response,HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param isoCode
	 * @param category
	 * @return
	 * 
	 * Top headlines /top-headlines
	 *This endpoint provides live top and breaking headlines for a country, specific category in a country, 
	 *single source, or multiple sources.
	 *
	 *isoCodes that works: ae ar at au be bg br ca ch cn co cu cz de eg fr gb gr hk hu id ie il in 
	 *it jp kr lt lv ma mx my ng nl no nz ph pl pt ro rs ru sa se sg si sk th tr tw ua us ve za
	 *
	 *category: business entertainment general health science sports technology
	 */
	
	@GetMapping("/top-headlines/{isoCode}/{category}")
	public ResponseEntity<?> getBusinessHeadlines(@PathVariable("isoCode") String isoCode,
			@PathVariable("category") String category){
		
		List<Response> response= newsService.getHeadlines(isoCode,category);	
		return new ResponseEntity(response,HttpStatus.OK);
	}

}
