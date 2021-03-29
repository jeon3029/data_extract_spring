package com.himart.restservicecors.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.himart.restservicecors.dto.QueryResponseDto;
import com.himart.restservicecors.dto.MapperTestDto;
import com.himart.restservicecors.dto.QueryDto;
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
	
	//추후 삭제(테스트용)
	//RETURN : "I'm Alive" - network testing
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String isRunning() {
        return "I'm Alive!";
    }
    
	//추후 삭제(테스트용)
	//RETURN : json data
    @PostMapping("/query_json")
    public QueryResponseDto getQueryResponse(@RequestBody HashMap<String, String> map) {
		int id = Integer.parseInt(map.get("user"));
		String query = map.get("query");
		return queryService.getQueryResponse(id,query);
    }
    
    //그룹별 쿼리 리스트
    @GetMapping("/query/all/{org_id}")
    public List<QueryDto> getQueryByOrgId(@PathVariable String org_id) {    	
        return queryService.getAllQueryListByOrgId(org_id);
    }
    
    //쿼리 정보
    @GetMapping("/query")
    public QueryDto getQueryDetailByCode(int qcode) {    	
        return queryService.getQueryDetailByCode(qcode);
    }
    
    //쿼리 수정
    @PutMapping("/query")
    public Object modifyQuery(HttpSession httpSession,
    		@RequestBody HashMap<String, String> map
    		) throws Exception{    	
    	int qcode = Integer.parseInt(map.get("qcode"));
    	String qExpl = map.get("qexpl");
    	String qTitle = map.get("qtitle");
    	String qText = map.get("qtext");
        QueryDto query = queryService.getQueryDetailByCode(qcode);
        if(query==null) {
        	HashMap<String, String> msg = new HashMap<String, String>();
    		msg.put("errMsg", "해당 쿼리가 존재하지 않습니다.");
        	return new ResponseEntity<>(msg ,HttpStatus.BAD_REQUEST);
        }
        query.setQExpl(qExpl);
        query.setQText(qText);
        query.setQTitle(qTitle);
        queryService.updateQuery(query);
        return new ResponseEntity<>(query.getQCode(), HttpStatus.OK);
    }
    
    //쿼리 삽입
    @PostMapping("/query")
    public Object insertQuery(HttpSession httpSession,
    		@RequestBody HashMap<String, String> map
    		) throws Exception{
    	QueryDto query = new QueryDto();
    	query.setQExpl(map.get("qexpl"));
    	query.setQTitle( map.get("qtitle"));
    	query.setQText( map.get("qtext"));
        queryService.insertQuery(query);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    
    //쿼리 수행
    @PostMapping("/query_async")
    public Object getQueryAsyncResponse(HttpSession httpSession,
    		@RequestBody HashMap<String, String> map) throws Exception{
    	if(queryService.getSessionCount()>=2) {
    		return new ResponseEntity<>("session full", HttpStatus.LOCKED);
    	}
		int id = Integer.parseInt(map.get("user"));
		if(queryService.checkSession(id)) {
			return new ResponseEntity<>("already running", HttpStatus.CONFLICT);
		}
		String query = map.get("query");
		asyncService.getQueryResponse(id, query);
		return new ResponseEntity<>(id, HttpStatus.OK);
    }
    
    @PostMapping("/query_check")
    public boolean checkSession(@RequestBody HashMap<String, String> map) {
		int id = Integer.parseInt(map.get("user"));
		boolean b = queryService.checkSession(id);
		return b;
    }
    
    @PostMapping("/query_kill")
    public String killSession(@RequestBody HashMap<String, String> map) {
		int id = Integer.parseInt(map.get("user"));
		queryService.killSession(id);
		return id + " : kill Session Confirmed / Current sessionCount : "+queryService.getSessionCount();
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
    
    //추후 삭제(테스트용)
    //RETURN SAMPLE JSON DATA
	@RequestMapping("/jsontest")
	public TestDto getjsonTest1() {
		TestDto sampleVO = new TestDto();
		sampleVO.setNo(1);
		sampleVO.setName("제이슨 객체입니다.");
		return sampleVO;
	}
	
	//추후 삭제(테스트용)
	//RETURN JSON TEST SET BY QUERY MAPPER
	@GetMapping("/jsontest2")
    public List<MapperTestDto> getJsonTest2() {
    	return queryService.getJsonTest();
    }
	
	//추후 삭제(테스트용)
	//RETURN JSON TEST SET BY ARGUMENT
	@PostMapping("/jsontest3")
    public TestDto getJsonTest3(@RequestBody HashMap<String, String> map) {
		int id = Integer.parseInt(map.get("user"));
		String query = map.get("query");
    	return queryService.getJsonTest2(id,query);
    }
}

