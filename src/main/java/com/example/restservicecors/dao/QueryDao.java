package com.example.restservicecors.dao;

import com.example.restservicecors.dto.QueryResponseDto;

public interface QueryDao {
	public QueryResponseDto getQueryResponse(String id, String query);
}
