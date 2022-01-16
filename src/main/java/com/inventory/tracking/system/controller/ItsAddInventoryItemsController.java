package com.inventory.tracking.system.controller;

import java.sql.Timestamp;
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
import org.springframework.web.servlet.ModelAndView;

import com.inventory.tracking.system.constants.ItsConstants;
import com.inventory.tracking.system.constants.ItsSqlConstants;
import com.inventory.tracking.system.dao.ItsDao;
import com.inventory.tracking.system.helper.ItsHelper;


@Controller
@RequestMapping(ItsConstants.INVENTORY_TRACKING_SYSTEM_REQUEST)
public class ItsAddInventoryItemsController {
	
	private static Logger logger = LoggerFactory.getLogger(ItsAddInventoryItemsController.class);
	
	@Autowired
	ItsDao itsDao;
	
	@Autowired
	ItsAddWarehouseController itsAddWarehouseController;
	
	
	
	@RequestMapping(value = ItsConstants.REQUEST_ADD_INVENTORY_ITEMS_PAGE, method = RequestMethod.GET)
	public ModelAndView request_add_inventory_items_page(ModelAndView mv) {
		try {
			logger.info("\nInside request_add_inventory_items_page() :::::: ItsAddInventoryItemsController : Loading the Add Inventory Item page for Inventory Tracking System");
			
			// Get all warehouse details
			JSONArray warehouseDetailsArr = itsAddWarehouseController.getAllWarehouseDetails();
			
			mv.setViewName(ItsConstants.VIEW_ITS_ADD_INVENTORY_ITEMS_PAGE);
			mv.addObject("warehouseDetailsArr", warehouseDetailsArr);
		}
		catch(Exception e) {
			logger.error("Exception occurred inside request_add_items_page() :::::: ItsAddInventoryItemsController : ", e);
		}
		
		return mv;
	}
	
	
	@RequestMapping(value = ItsConstants.REQUEST_CREATE_INVENTORY, method = RequestMethod.POST)
	@ResponseBody
	public String create_inventory(HttpServletRequest request) {
		JSONObject createItemsResponse = new JSONObject();
		
		try {
			logger.info("\nInside create_inventory() :::::: ZendeskHomePageController : Create new inventory item ");
			
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
			
			
			String msg = "";
			String status = ItsConstants.FAILURE;
			
			
			// Get warehouse id from - warehouseName, location, zipCode
			int warehouseId = itsDao.get_warehouse_id(ItsSqlConstants.GET_INVENTORY_WAREHOUSE_ID, new Object[] {warehouseName, location, zipCode});
			logger.info("Warehouse details = warehouseName : "+warehouseName +", location : "+location +", zipCode : "+zipCode +" :::: warehouseId : "+warehouseId);
			
			if(warehouseId > 0) {
				String inventoryName = null;
				if(paramsMap.containsKey(ItsConstants.INVENTORY_ITEM_NAME)) {
					inventoryName = paramsMap.get(ItsConstants.INVENTORY_ITEM_NAME)[0];
				}
				
				int quantity = 0;
				if(paramsMap.containsKey(ItsConstants.INVENTORY_ITEM_QUANTITY)) {
					quantity = Integer.parseInt(paramsMap.get(ItsConstants.INVENTORY_ITEM_QUANTITY)[0]);
				}
				
				float price = 0;
				if(paramsMap.containsKey(ItsConstants.INVENTORY_ITEM_PRICE)) {
					price = Float.parseFloat(paramsMap.get(ItsConstants.INVENTORY_ITEM_PRICE)[0]);
				}
				
				int lotNo = 0;
				if(paramsMap.containsKey(ItsConstants.INVENTORY_ITEM_LOT_NO)) {
					lotNo = Integer.parseInt(paramsMap.get(ItsConstants.INVENTORY_ITEM_LOT_NO)[0]);
				}
				
				String rcvdDt = null;
				if(paramsMap.containsKey(ItsConstants.INVENTORY_ITEM_RECEIVED_DATE)) {
					rcvdDt = paramsMap.get(ItsConstants.INVENTORY_ITEM_RECEIVED_DATE)[0];
				}
				
				logger.info("Entered Item details = warehouseId : " +warehouseId +", itemName : "+inventoryName +", quantity : "+quantity +", price : "+price +", lotNo : "+lotNo +", rcvdDt : "+rcvdDt);
				
				String isActive = ItsConstants.ACTIVE;
				String recordCreatedBy = ItsConstants.ADMIN;
				Timestamp recordCreatedDt = ItsHelper.getCurrentTimestamp();
				
				
				// Check if Inventory item already exists : warehouse_id, itemName, price
				int count = itsDao.baseDao.select_record_int(ItsSqlConstants.CHECK_IF_INVENTORY_ITEM_ALREADY_EXISTS, new Object[] {warehouseId, inventoryName, price});
				
				if(count == 0) {
					Object params[] = {warehouseId, inventoryName, quantity, price, lotNo, rcvdDt, isActive, recordCreatedBy, recordCreatedDt};
					
					// Create inventory item
					status = itsDao.create_inventory_items(ItsSqlConstants.INSERT_INTO_INVENTORY_ITEMS_DETAILS, params);
					
					if(status.equals(ItsConstants.SUCCESS)) { 
						// Success
						msg = ItsConstants.CREATE_INVENTORY_ITEM_SUCCESS_MSG;
					}
					else {
						// Error
						msg = ItsConstants.CREATE_INVENTORY_ITEM_ERROR_MSG;
					}
				}
				else {
					// Inventory already exists
					msg = ItsConstants.CREATE_INVENTORY_ITEM_ALREADY_EXISTS_MSG;
				}
			}
			else {
				// Warehouse does not exists
				msg = ItsConstants.CREATE_INVENTORY_ITEM_WAREHOUSE_NOT_EXISTS_MSG;
			}
			
			createItemsResponse.put("status", status);
			createItemsResponse.put("msg", msg);
			
			logger.info("Create Item response = status : "+status +", msg : "+msg);
		}
		catch(Exception e) {
			logger.error("Exception occurred inside request_view_items_page() :::::: ItsViewItemsController : ", e);
		}
		
		return createItemsResponse.toString();
	}
		
}