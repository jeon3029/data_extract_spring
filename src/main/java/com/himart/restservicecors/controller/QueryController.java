package com.himart.restservicecors.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.himart.restservicecors.dto.QueryResponseDto;
import com.himart.restservicecors.dto.MapperTestDto;
import com.himart.restservicecors.dto.TestDto;
import com.himart.restservicecors.service.AsyncService;
import com.himart.restservicecors.service.QueryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class QueryController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	QueryService queryService;
	
	@Autowired
	AsyncService asyncService;
	
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String isRunning() {
        return "I'm Alive!";
    }
    
    @PostMapping("/query")
    public QueryResponseDto getQueryResponse(@RequestBody HashMap<String, String> map) {
		int id = Integer.parseInt(map.get("user"));
		String query = map.get("query");
		return queryService.getQueryResponse(id,query);
    }
    
    @PostMapping("/query_async")
    public String getQueryAsyncResponse(@RequestBody HashMap<String, String> map) {
		int id = Integer.parseInt(map.get("user"));
		String query = map.get("query");
		asyncService.getQueryResponse(id, query);
		return id + " : async Request Confirmed";
    }
    
    //for testing : RETURN SAMPLE JSON DATA
	@RequestMapping("/jsontest")
	public TestDto getjsonTest1() {
		TestDto sampleVO = new TestDto();
		sampleVO.setNo(1);
		sampleVO.setName("제이슨 객체입니다.");
		return sampleVO;
	}
	
	//for testing : RETURN JSON TEST SET BY QUERY MAPPER
	@GetMapping("/jsontest2")
    public List<MapperTestDto> getJsonTest2() {
    	return queryService.getJsonTest();
    }
	
	//for testing : RETURN JSON TEST SET BY ARGUMENT
	@PostMapping("/jsontest3")
    public TestDto getJsonTest3(@RequestBody HashMap<String, String> map) {
		int id = Integer.parseInt(map.get("user"));
		String query = map.get("query");
    	return queryService.getJsonTest2(id,query);
    }
}

