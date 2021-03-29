package com.himart.restservicecors.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.himart.restservicecors.dto.QueryDto;
import com.himart.restservicecors.service.AsyncService;
import com.himart.restservicecors.service.QueryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class QueryController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	QueryService queryService;
		
	//추후 삭제(테스트용)
	//RETURN : "I'm Alive" - network testing
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String isRunning() {
        return "I'm Alive!";
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
}

