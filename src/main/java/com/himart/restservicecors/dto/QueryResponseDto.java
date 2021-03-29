package com.himart.restservicecors.dto;

import java.util.ArrayList;
import lombok.Data;

@Data
public class QueryResponseDto {
	private String status;
	private Integer colNum;
	private Integer rowNum;
	private String[] header;
	private ArrayList[] data;
	private long milTime;
	
	public QueryResponseDto() {
		this.status = "success";
	}
}
