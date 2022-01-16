package com.inventory.tracking.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.inventory.tracking.system.constants.ItsConstants;

@Configuration
public class BaseDao {
	
	private static Logger logger = LoggerFactory.getLogger(BaseDao.class);
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	
	public String insert_or_update_record(String sqlQuery, Object params[]) {
		String status = ItsConstants.FAILURE;
		
		int result = 0;
		try {
			result = jdbcTemplate.update(sqlQuery, params);
			
			if(result == 1) {
				status = ItsConstants.SUCCESS;
			}
		}
		catch(DataAccessException e) {
			logger.error("DataAccessException occurred inside insert_or_update_record() :::::: BaseDao : ", e);
		}
		catch(Exception e) {
			logger.error("Exception occurred inside insert_or_update_record() :::::: BaseDao : ", e);
		}
		
		return status;
	}
	
	
	public int select_record_int(String sqlQuery, Object params[]) {
		int result = 0;
		
		try {
			List<Integer> list = jdbcTemplate.query(sqlQuery, new RowMapper<Integer>() {
				
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getInt(1);
				}
				
			}, params);
			
			result = list.get(0);
		}
		catch(DataAccessException e) {
			logger.error("DataAccessException occurred inside select_record_int() :::::: BaseDao : ", e);
		}
		catch(Exception e) {
			logger.error("Exception occurred inside select_record_int() :::::: BaseDao : ", e);
		}
		
		return result;
	}
	
}