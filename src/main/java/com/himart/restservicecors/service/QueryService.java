package com.himart.restservicecors.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.himart.restservicecors.dao.JsonTestDao;
import com.himart.restservicecors.dao.QueryDao;
import com.himart.restservicecors.dao.SessionDao;
import com.himart.restservicecors.dto.QueryResponseDto;
import com.himart.restservicecors.dto.SessionDto;
import com.himart.restservicecors.dto.MapperTestDto;
import com.himart.restservicecors.dto.TestDto;
import com.opencsv.CSVWriter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
	
	@Autowired
	private SessionDao sessionDao;
	
	public  QueryResponseDto getQueryResponse(int id, String query) {
		
		QueryResponseDto res = new QueryResponseDto();
		long beforeTime = System.currentTimeMillis();
		try {
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection conn = DriverManager.getConnection(url,"adm","oracle");
            logger.info(id + " : DB 접속 성공");
            
            PreparedStatement stmt = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        
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
    		res.setMilTime(afterTime-beforeTime);
    		
            res.setColNum(columnCount);
            res.setRowNum(rowCount);
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
        	res.setStatus(e.getMessage());
        }
        return res;
    }
	public void genCsvFileWithQuery(int id,String query) {
		long beforeTime = System.currentTimeMillis();
		try {
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection conn = DriverManager.getConnection(url,"adm","oracle");
            logger.info(id + " : DB 접속 성공");
            
            String sessionQuery = "select sid, serial# from V$SESSION where AUDSID = userenv('SESSIONID')";
            PreparedStatement tstmt = conn.prepareStatement(sessionQuery,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet trs = tstmt.executeQuery();
            logger.info(id + " : 세션 정보 획득 성공");
            trs.next();
            int sid = Integer.parseInt(trs.getString(1));
            int session = Integer.parseInt(trs.getString(2));
            logger.info(id + " : " + sid + " / " + session);
            sessionDao.setId(id, sid, session);
            
            //안에 " 포함되어있으면 문제 될 수 있음
            Statement stmt = conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
//            stmt.setFetchSize(Integer.MIN_VALUE);
            
            ResultSet rs = stmt.executeQuery(query);
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
             
            CSVWriter csvWriter = new CSVWriter(new FileWriter("C:\\"+id+".csv"), ',');
            
            csvWriter.writeAll(rs, true);
    		long afterTime = System.currentTimeMillis();
    		
            logger.info(id + " : Runngin TIME : " + (afterTime - beforeTime) + "ms");
            csvWriter.close();
            rs.close();
            stmt.close();
            conn.close();
            logger.info(id + " : DB 접속 종료!");
            sessionDao.removeId(id);
            logger.info(id + " : Session접속 종료!");
        } catch (Exception e) {
        	System.err.println(id + " : Got an exception! ");
        	logger.error(e.getMessage());
        } 
	}
	
	public void killSession(int id) {
		//session not alive
		if (sessionDao.checkId(id)==false) return;
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
            Connection conn = DriverManager.getConnection(url,"adm","oracle");
            SessionDto session = sessionDao.getSession(id);
            String killQuery = "ALTER SYSTEM KILL SESSION \'" + session.getSid() +"," + session.getSession()+"\'";
            
            
            PreparedStatement stmt = conn.prepareStatement(killQuery,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            sessionDao.removeId(id);
            rs.close();
            stmt.close();
            conn.close();
		} catch(Exception e) {
			
		}
	}
	
	public boolean checkSession(int id) {
		return sessionDao.checkId(id);
	}
	public List<MapperTestDto> getJsonTest(){
		return jsonDao.getJsonTest();
	}
	public int getSessionCount() {
		return sessionDao.getSessionCount();
	}
	
	public TestDto getJsonTest2(int i,String q){
		TestDto t = new TestDto();
		t.setName(q);
		t.setNo(i);
		return t;
	}
}
