package com.himart.restservicecors.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.himart.restservicecors.dao.JsonTestDao;
import com.himart.restservicecors.dao.QueryDao;
import com.himart.restservicecors.dto.QueryResponseDto;
import com.himart.restservicecors.dto.SampleDto;
import com.himart.restservicecors.dto.SampleVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class QueryService {
//	@Autowired
//	private QueryDao queryDao;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JsonTestDao jsonDao;
	
	public  QueryResponseDto getQueryResponse(int id, String query) {
		QueryResponseDto res = new QueryResponseDto();
		long beforeTime = System.currentTimeMillis();
		try {
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection conn = DriverManager.getConnection(url,"adm","oracle");
            logger.info(id + " : DB 접속 성공");
            
            PreparedStatement stmt = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            //todo : large data
//          Statement stmt = readOnlyConn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY)
//          stmt.setFetchSize(Integer.MIN_VALUE);
            
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount(); 
            String[] header = new String[columnCount];
            for(int i=1; i<=columnCount; i++) {
    	        header[i-1] = rsmd.getColumnName(i);
    	    }
            rs.last();
            int rowCount =rs.getRow();
            ArrayList[] set = new ArrayList[rowCount];
            int i=0;
            rs.beforeFirst();
    		while(rs.next()) {
    			set[i] = new ArrayList<String>();
    			for(int j=1; j<=columnCount; j++) {
    				set[i].add(rs.getString(j));
    		    } 
    			i++;
    		}
    		long afterTime = System.currentTimeMillis();
    		res.setMiltime(afterTime-beforeTime);
            res.setColnum(columnCount);
            res.setRownum(rowCount);
            res.setHeader(header);
            res.setData(set);
            logger.info(id + " : Runngin TIME : " + (afterTime - beforeTime) + "ms");
            logger.info(id + " : DB 접속 종료!");
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
        	System.err.println("Got an exception! ");
        	logger.error(e.getMessage());
            res.setStaus(e.getMessage());
        }
        return res;
    }
	
	public List<SampleDto> getJsonTest(){
		return jsonDao.getJsonTest();
	}
	
	public SampleVO getJsonTest2(int i,String q){
		SampleVO t = new SampleVO();
		t.setName(q);
		t.setNo(i);
		return t;
	}
}
