package com.inventory.tracking.system.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inventory.tracking.system.constants.ItsConstants;
import com.inventory.tracking.system.constants.ItsSqlConstants;
import com.inventory.tracking.system.dao.ItsDao;
import com.inventory.tracking.system.helper.ItsHelper;
import com.inventory.tracking.system.pojo.Warehouse;

@Controller
@RequestMapping(ItsConstants.INVENTORY_TRACKING_SYSTEM_REQUEST)
public class ItsAddWarehouseController {
	
	private static Logger logger = LoggerFactory.getLogger(ItsAddWarehouseController.class);
	
	@Autowired
	ItsDao itsDao;
	
	
	
	@RequestMapping(value = ItsConstants.REQUEST_ADD_WAREHOUSE_PAGE, method = RequestMethod.GET)
	public String request_create_warehouse_page() {
		logger.info("\nInside request_create_warehouse_page() :::::: ItsAddWarehouseController : Loading the Create Warehouse page for Inventory Tracking System");
		return ItsConstants.VIEW_ITS_ADD_WAREHOUSE_PAGE;
	}
	
	
	@RequestMapping(value = ItsConstants.REQUEST_CREATE_WAREHOUSE, method = RequestMethod.POST)
	@ResponseBody
	public String create_warehouse(HttpServletRequest request) {
		JSONObject createWarehouseDetailsResponse = new JSONObject();
		
		try {
			logger.info("\nInside create_warehouse() :::::: ItsAddWarehouseController : Create new warehouse details");
			
			Map<String, String[]> paramsMap = request.getParameterMap();
			
			String warehouseName = null;
			if(paramsMap.containsKey(ItsConstants.WAREHOUSE_NAME)) {
				warehouseName = paramsMap.get(ItsConstants.WAREHOUSE_NAME)[0];
			}
			
			String location = null;
			if(paramsMap.containsKey(ItsConstants.WAREHOUSE_LOCATION)) {
				location = paramsMap.get(ItsConstants.WAREHOUSE_LOCATION)[0];
			}
			
			int zipCode = 0;
			if(paramsMap.containsKey(ItsConstants.WAREHOUSE_ZIP_CODE)) {
				zipCode = Integer.parseInt(paramsMap.get(ItsConstants.WAREHOUSE_ZIP_CODE)[0]);
			}
			
			logger.info("Entered details: warehouseName : "+warehouseName +", location : " +location +", zipCode : "+zipCode);
			
			
			// Check if Warehouse already exists
			String status = itsDao.check_warehouse(ItsSqlConstants.CHECK_INVENTORY_WAREHOUSE_DETAILS, new Object[] {warehouseName, location, zipCode});
			String msg = "";
			
			if(status.equals(ItsConstants.SUCCESS)) {
				String isActive = ItsConstants.ACTIVE;
				String recordCreatedBy = ItsConstants.ADMIN;
				Timestamp recordCreatedDt = ItsHelper.getCurrentTimestamp();
				
				Object params[] = {warehouseName, location, zipCode, isActive, recordCreatedBy, recordCreatedDt};
				
				// Create new Warehouse
				status = itsDao.create_warehouse(ItsSqlConstants.INSERT_INTO_INVENTORY_WAREHOUSE_DETAILS, params);
				
				if(status.equals(ItsConstants.SUCCESS)) {
					// Success
					msg = ItsConstants.CREATE_WAREHOUSE_SUCCESS_MSG;
				}
				else {
					// Error
					msg = ItsConstants.CREATE_WAREHOUSE_ERROR_MSG;
				}
			}
			else {
				// Warehouse already exists
				msg = ItsConstants.CREATE_WAREHOUSE_ALREADY_EXISTS_MSG;
			}
			
			createWarehouseDetailsResponse.put("status", status);
			createWarehouseDetailsResponse.put("msg", msg);
			
			logger.info("Create warehouse response = status : "+status +", msg : "+msg);
		}
		catch(Exception e) {
			logger.error("Exception occurred inside create_warehouse() :::::: ItsAddWarehouseController : ", e);
		}
		
		return createWarehouseDetailsResponse.toString();
	}
	
	
	public JSONArray getAllWarehouseDetails() {
		JSONArray warehouseDetailsArr = new JSONArray();
		
		try {
			List<Warehouse> warehouseList = itsDao.get_warehouse_list();
			
			for(Warehouse warehouse : warehouseList) {
				String warehouseDtl = warehouse.getWarehouseName() +", " +warehouse.getLocation() +", " +warehouse.getZipCode();
				warehouseDetailsArr.put(warehouseDtl);
				
			}
			
			logger.info("All warehouse details : "+warehouseDetailsArr);
		}
		catch(Exception e) {
			logger.error("Exception occurred inside getAllWarehouseDetails() :::::: ItsAddWarehouseController : ", e);
		}
		
		return warehouseDetailsArr;
	}
	
}
