package com.himart.restservicecors.dto;

import lombok.Data;

@Data
public class QueryDto {
	// 변수명 Camel로 해야 mapping 됨
	// db는 snake형태
	private int qCode;
	private String qTitle;
	private String qText;
	private String qExpl;
}
