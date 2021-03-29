package com.himart.restservicecors.dao;

import java.util.List;

import com.himart.restservicecors.dto.QueryDto;
import com.himart.restservicecors.dto.QueryResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QueryDao {
	public QueryResponseDto getQueryResponse(String id, String query); //not used
	public List<QueryDto> getQueryListByOrgId(String org_id);
	public QueryDto getQueryDetailByCode(int qcode);
	public void updateQuery(QueryDto query);
	public void insertQuery(QueryDto query);
}
