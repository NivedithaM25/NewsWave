package com.newsapp.news.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.newsapp.news.response.ExceptionMessage;

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(DataNotAvailableException.class)
	public ExceptionMessage ExhandleDataNotAvailableException(DataNotAvailableException msg) {
		
		ExceptionMessage errorMsg=new ExceptionMessage();
		errorMsg.setMessage(msg.getMessage());
		errorMsg.setTimeStamp(msg.getStackTrace().toString());
		return errorMsg;
		
	}

}
