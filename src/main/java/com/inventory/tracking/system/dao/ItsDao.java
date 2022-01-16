package com.inventory.tracking.system.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.inventory.tracking.system.constants.ItsConstants;
import com.inventory.tracking.system.constants.ItsSqlConstants;
import com.inventory.tracking.system.pojo.InventoryItem;
import com.inventory.tracking.system.pojo.Warehouse;

@Configuration
public class ItsDao {
	
	private static Logger logger = LoggerFactory.getLogger(ItsDao.class);
	
	@Autowired
	public BaseDao baseDao;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	
	/*** Warehouse related : Start ***/
	
	public String check_warehouse(String sqlQuery, Object params[]) {
		String status = ItsConstants.FAILURE;
		
		try {
			int count = baseDao.select_record_int(sqlQuery, params);
			
			if(count == 0) {
				status = ItsConstants.SUCCESS;
			}
		}
		catch(Exception e) {
			logger.error("Exception occurred inside check_warehouse() :::::: ItsDao : ", e);
		}
		
		return status;
	}
	
	
	public String create_warehouse(String sqlQuery, Object params[]) throws Exception {
		return baseDao.insert_or_update_record(sqlQuery, params);
	}
	
	
	public List<Warehouse> get_warehouse_list() throws Exception {
		return jdbcTemplate.query(ItsSqlConstants.GET_INVENTORY_WAREHOUSE_DETAILS, new RowMapper<Warehouse>() {

			@Override
			public Warehouse mapRow(ResultSet rs, int rowNum) throws SQLException {
				Warehouse warehouse = new Warehouse();
				warehouse.setWarehouseName(rs.getString(1));
				warehouse.setLocation(rs.getString(2));
				warehouse.setZipCode(rs.getInt(3));
				return warehouse;
			}
		});
	}
	
	/*** Warehouse related : End ***/
	
	
	
	
	/*** Add Inventory : Start ***/
	public int get_warehouse_id(String sqlQuery, Object[] params) throws Exception {
		return baseDao.select_record_int(sqlQuery, params);
	}
	
	
	public String create_inventory_items(String sqlQuery, Object params[]) throws Exception {
		return baseDao.insert_or_update_record(sqlQuery, params);
	}
	
	/*** Add Inventory Items : End ***/
	
	
	
	
	/*** View Inventory : Start ***/
	
	public List<InventoryItem> get_inventory_items_warehouses_list(String sqlQuery) throws Exception {
		return jdbcTemplate.query(sqlQuery, new RowMapper<InventoryItem>() {

			@Override
			public InventoryItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				InventoryItem item = new InventoryItem();
				
				Warehouse warehouse = new Warehouse();
				warehouse.setWarehouseId(rs.getInt(1));
				warehouse.setWarehouseName(rs.getString(2));
				warehouse.setLocation(rs.getString(3));
				warehouse.setZipCode(rs.getInt(4));
				
				item.setWarehouse(warehouse);
				item.setItemId(rs.getInt(5));
				item.setItemName(rs.getString(6));
				item.setQuantity(rs.getInt(7));
				item.setPrice(rs.getFloat(8));
				item.setLotNo(rs.getInt(9));
				item.setReceivedDate(rs.getString(10));
				return item;
			}
		});
	}


	public List<InventoryItem> get_inventory_items_list(String sqlQuery) throws Exception {
		return jdbcTemplate.query(sqlQuery, new RowMapper<InventoryItem>() {

			@Override
			public InventoryItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				InventoryItem item = new InventoryItem();
				
				Warehouse warehouse = new Warehouse();
				warehouse.setWarehouseId(rs.getInt(1));
				warehouse.setWarehouseName(rs.getString(2));
				warehouse.setLocation(rs.getString(3));
				warehouse.setZipCode(rs.getInt(4));
				item.setWarehouse(warehouse);
				
				item.setItemId(rs.getInt(5));
				item.setItemName(rs.getString(6));
				item.setQuantity(rs.getInt(7));
				item.setPrice(rs.getFloat(8));
				item.setLotNo(rs.getInt(9));
				item.setReceivedDate(rs.getString(10));
				return item;
			}
		});
	}
	
	/*** View Inventory : End ***/
	
	
	
	
	/*** Edit Inventory : Start ***/
	
	public String update_inventory_items(String sqlQuery, Object params[]) throws Exception {
		return baseDao.insert_or_update_record(sqlQuery, params);
	}
	
	/*** Edit Inventory : End ***/
	
	
	
	
	/*** Remove Inventory : Start ***/
	
	public String delete_inventory_items(String sqlQuery, Object params[]) throws Exception {
		return baseDao.insert_or_update_record(sqlQuery, params);
	}
	
	/*** Remove Inventory : End ***/
	
}