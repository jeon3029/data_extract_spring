package com.himart.restservicecors.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	QueryRunningService queryRunningService;

	//비동기
	//추후 삭제(테스트용)
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
        queryRunningService.genCsvFileWithQuery(id, query);
        logger.info(id + " : finished on Async");
    }
	
    //동기
	//추후 삭제(테스트용)
    public void onSync() {
        try {
            Thread.sleep(5000);
            logger.info("onSync");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

