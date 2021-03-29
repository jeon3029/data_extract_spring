package com.himart.restservicecors.dao;

import java.util.List;

import com.himart.restservicecors.dto.QueryListDto;
import com.himart.restservicecors.dto.QueryResponseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QueryDao {
	public QueryResponseDto getQueryResponse(String id, String query); //not used
	public List<QueryListDto> getQueryListByOrgId(String org_id);
}
