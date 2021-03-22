package com.example.restservicecors.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.restservicecors.dao.QueryDao;


public class QueryResponseDto {
	private String status;
	private Integer colNum;
	private String[] header;
	private ArrayList[] data;
}
