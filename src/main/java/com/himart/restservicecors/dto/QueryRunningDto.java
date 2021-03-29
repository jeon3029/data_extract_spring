package com.himart.restservicecors.dto;
import java.util.HashMap;

import lombok.Data;

@Data
public class QueryRunningDto {
	private String qcode;
	private String hrusrId;
	private HashMap<String,String> conditions;
}
