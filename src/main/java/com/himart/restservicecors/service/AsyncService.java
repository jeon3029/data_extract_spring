package com.himart.restservicecors.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.himart.restservicecors.dto.QueryResponseDto;

@Service
public class AsyncService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	QueryService queryService;
	//비동기
	@Async
    public void onAsync() {
        try {
            Thread.sleep(5000);
            logger.info("onAsync");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	
	@Async
    public void getQueryResponse(int id,String query){
        queryService.genCsvFileWithQuery(id, query);
        
        logger.info(id + " : finished on Async");
    }
	
    //동기
    public void onSync() {
        try {
            Thread.sleep(5000);
            logger.info("onSync");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

