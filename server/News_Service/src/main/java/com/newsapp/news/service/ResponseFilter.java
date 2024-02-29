package com.newsapp.news.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.newsapp.news.response.Response;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ResponseFilter {
	
	public List<Response> doResponseFilter(String uri,String category){
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
		log.info(response.toString());
		int increment=1;
		int minDigits = 10000; // Minimum 5-digit number
        int id = ThreadLocalRandom.current().nextInt(minDigits, Integer.MAX_VALUE);
       
		log.info("Filtering News...");
		List<Response> responseObj=new ArrayList<>();
		if (response.getStatusCode().is2xxSuccessful()) {
			String responseBody = response.getBody();
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
			if (jsonObject.has("articles")) {
				JsonArray articlesArray = jsonObject.getAsJsonArray("articles");
				

				for (JsonElement articleElement : articlesArray) {
					
					JsonObject articleObject = articleElement.getAsJsonObject();
					Response res=new Response(id+(increment++),articleObject.get("title").getAsString(),
							//articleObject.get("description").toString(),
							articleObject.get("description") != null && !articleObject.get("description").isJsonNull()
					        ? articleObject.get("description").getAsString()
					        : articleObject.get("title").getAsString(),
							articleObject.get("url").getAsString(),
							//articleObject.get("urlToImage").toString()
							articleObject.get("urlToImage") != null && !articleObject.get("urlToImage").isJsonNull()
					        ? articleObject.get("urlToImage").getAsString()
					        : null,
					        category
							);
					
					responseObj.add(res);
				}

			}
		}

		
		
		return responseObj;
	}

}
