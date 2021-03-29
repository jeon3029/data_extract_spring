package com.himart.restservicecors.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.himart.restservicecors.service.AsyncService;
import com.himart.restservicecors.service.QueryRunningService;

@RestController
public class QueryRunningController {
	@Autowired
	QueryRunningService queryRunningService;
	
	@Autowired
	AsyncService asyncService;
	//쿼리 수행
    @PostMapping("/query_async")
    public Object getQueryAsyncResponse(HttpSession httpSession,
    		@RequestBody HashMap<String, String> map) throws Exception{
    	if(queryRunningService.getSessionCount()>=2) {
    		return new ResponseEntity<>("session full", HttpStatus.LOCKED);
    	}
		int id = Integer.parseInt(map.get("user"));
		if(queryRunningService.checkSession(id)) {
			return new ResponseEntity<>("already running", HttpStatus.CONFLICT);
		}
		String query = map.get("query");
		asyncService.getQueryResponse(id, query);
		return new ResponseEntity<>(id, HttpStatus.OK);
    }
    
    @PostMapping("/query_check")
    public boolean checkSession(@RequestBody HashMap<String, String> map) {
		int id = Integer.parseInt(map.get("user"));
		boolean b = queryRunningService.checkSession(id);
		return b;
    }
    
    @PostMapping("/query_kill")
    public String killSession(@RequestBody HashMap<String, String> map) {
		int id = Integer.parseInt(map.get("user"));
		queryRunningService.killSession(id);
		return id + " : kill Session Confirmed / Current sessionCount : "+queryRunningService.getSessionCount();
    }
    
    @PostMapping("/query_download")
    public ResponseEntity<Resource> downloadFile(@RequestBody HashMap<String, String> map) throws IOException {
    	
    	int fileName = Integer.parseInt(map.get("user"));
    	Path path = Paths.get("c:\\" + fileName + ".csv");
    	String contentType = Files.probeContentType(path);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add(HttpHeaders.CONTENT_TYPE, contentType);
    	
    	Resource resource = new InputStreamResource(Files.newInputStream(path));
    	return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
    
    
}
