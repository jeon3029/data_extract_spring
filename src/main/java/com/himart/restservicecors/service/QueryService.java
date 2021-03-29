package com.himart.restservicecors.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.himart.restservicecors.dao.QueryDao;
import com.himart.restservicecors.dto.QueryDto;


@Service
public class QueryService {
	@Autowired
	private QueryDao queryDao;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<QueryDto> getAllQueryListByOrgId(String org_id){
		return queryDao.getQueryListByOrgId(org_id);
	}
	public QueryDto getQueryDetailByCode(int qcode){
		return queryDao.getQueryDetailByCode(qcode);
	}
	public void updateQuery(QueryDto query) {
		queryDao.updateQuery(query);
	}
	public void insertQuery(QueryDto query) {
		queryDao.insertQuery(query);
	}
}
