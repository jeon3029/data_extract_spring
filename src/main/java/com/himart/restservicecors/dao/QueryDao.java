package com.himart.restservicecors.dao;

import com.himart.restservicecors.dto.QueryResponseDto;

public interface QueryDao {
	public QueryResponseDto getQueryResponse(String id, String query);
}
