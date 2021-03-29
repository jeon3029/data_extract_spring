package com.himart.restservicecors.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.himart.restservicecors.dao.QueryDao;
import com.himart.restservicecors.dto.QueryDto;
import com.himart.restservicecors.dto.QueryResponseDto;
import com.himart.restservicecors.dto.QueryRunningDto;
import com.himart.restservicecors.service.AsyncService;
import com.himart.restservicecors.service.QueryRunningService;

@RestController
public class QueryRunningController {
	@Autowired
	QueryRunningService queryRunningService;
		
	@Autowired
	AsyncService asyncService;
	
	@Autowired
	private QueryDao queryDao;
	
	//쿼리 수행
	//테스트용 - 추후 삭제
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @PostMapping("/query_async")
    public Object runQueryAsyncTest(HttpSession httpSession,
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
    
    @PostMapping("/query/run")
    public Object runQueryAsync(HttpSession httpSession,
    		@RequestBody QueryRunningDto qrDto) throws Exception{
    	if(queryRunningService.getSessionCount()>=2) {
    		return new ResponseEntity<>("session full", HttpStatus.LOCKED);
    	}
		int qcode = Integer.parseInt(qrDto.getQCode());
		int hrusr_id = Integer.parseInt(qrDto.getHrusrId());
		HashMap<String,String> conditions = qrDto.getConditions();
		System.out.println(conditions);
		if(queryRunningService.checkSession(hrusr_id)) {
			return new ResponseEntity<>("already running", HttpStatus.CONFLICT);
		}
		QueryDto query = queryDao.getQueryDetailByCode(qcode);
		logger.info(""+qcode+ hrusr_id);
		asyncService.getQueryResponse(hrusr_id, query.getQText());
		return new ResponseEntity<>(hrusr_id, HttpStatus.OK);
    }
    @GetMapping("/query/check")
    public boolean checkSession(int hrusr_id) {
		boolean b = queryRunningService.checkSession(hrusr_id);
		return b;
    }
    
    @GetMapping("/query/kill")
    public String killSession(int hrusr_id) {
		queryRunningService.killSession(hrusr_id);
		return hrusr_id + " : kill Session Confirmed / Current sessionCount : "+queryRunningService.getSessionCount();
    }
    
    @GetMapping("/query/file")
    public ResponseEntity<Resource> downloadFile(int hrusr_id) throws IOException {
    	int fileName = hrusr_id;
    	Path path = Paths.get("c:\\" + fileName + ".csv");
    	String contentType = Files.probeContentType(path);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add(HttpHeaders.CONTENT_TYPE, contentType);
    	
    	Resource resource = new InputStreamResource(Files.newInputStream(path));
    	return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
    
    //추후 삭제(테스트용)
  	//RETURN : json data
    @PostMapping("/query_json")
    public QueryResponseDto getQueryResponse(@RequestBody HashMap<String, String> map) {
    	int id = Integer.parseInt(map.get("user"));
  		String query = map.get("query");
  		return queryRunningService.getQueryResponse(id,query);
    }
}
