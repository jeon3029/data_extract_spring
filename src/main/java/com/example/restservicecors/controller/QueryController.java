package com.example.restservicecors.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservicecors.dto.QueryResponse;
import com.example.restservicecors.dto.SampleVO;
import com.example.restservicecors.service.JsonService;

import org.springframework.jdbc.core.BeanPropertyRowMapper;

@RestController
public class QueryController {
	@Autowired
	JdbcTemplate jdbc;
	
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String isRunning() {
        return "I'm Alive!";
    }
    
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public QueryResponse getData() {
        String sql = "select * from emp";
        QueryResponse res = new QueryResponse();
		try {
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection conn = DriverManager.getConnection(url,"adm","oracle");
            System.out.println("DB 접속 성공!");
            
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
            PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount(); 
            
            String[] header = new String[columnCount];
            
            for(int i=1; i<=columnCount; i++) {
    	        System.out.print(rsmd.getColumnName(i) +"\t");
    	        header[i-1] = rsmd.getColumnName(i);
    	    }
            System.out.println(); 
            rs.last();
            int rowCount =rs.getRow();
            System.out.println("rowcount : " + rowCount);
            ArrayList[] set = new ArrayList[rowCount];
            int i=0;
            rs.beforeFirst();
    		while(rs.next()) {
//    			set[i] = new ArrayList<String>();
    			for(int j=1; j<=columnCount; j++) {
//    				set[i].add(rs.getString(j));
    		        System.out.print(rs.getString(j) +"\t"); 
    		    }
    			System.out.println(); 
    			i++;
    		}
            res.setColNum(columnCount);
//            res.setHeader(header);
//            res.setData(set);            
            System.out.println("DB 접속 종료!");
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            res.setStatus(e.getMessage());
        }
        return res;
    }
    
	
	@RequestMapping("/jsontest")
	public SampleVO restJson() {
		SampleVO sampleVO = new SampleVO();
		sampleVO.setNo(1);
		sampleVO.setName("제이슨 객체입니다.");
		return sampleVO;
	}
	
	//for testing : not finished
	@PostMapping("/jsontest2")
    public List<HashMap<Object, Object>> getLikeCntByType(@RequestBody HashMap<String, String> map) {
    	String user = map.get("user");
    	String query = map.get("query");
    	List<HashMap<String, String>> ret = new ArrayList<HashMap<String,String>>();
//    	ret["user"] = user;
//    	ret["query"] = query;
    	return JsonService.test2(user,query);
    }
}

