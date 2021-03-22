package com.example.restservicecors.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restservicecors.dao.JsonTestDao;
import com.example.restservicecors.dao.QueryDao;
import com.example.restservicecors.dto.QueryResponseDto;
import com.example.restservicecors.dto.SampleDto;


@Service
public class QueryService {
//	@Autowired
//	private QueryDao queryDao;
	
	@Autowired
	private JsonTestDao jsonDao;
	
//	public  QueryResponseDto getQueryResponse(String id, String query) {
//        return queryDao.getQueryResponse(id, query);
//    }
	
	public List<SampleDto> getJsonTest(){
		return jsonDao.getJsonTest();
	}
}
