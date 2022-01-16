package com.inventory.tracking.system.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.inventory.tracking.system.pojo.InventoryItem;
import com.inventory.tracking.system.pojo.Warehouse;


@Controller
@RequestMapping(ItsConstants.INVENTORY_TRACKING_SYSTEM_REQUEST)
public class ItsEditInventoryItemsController {
	
	private static Logger logger = LoggerFactory.getLogger(ItsEditInventoryItemsController.class);
	
	@Autowired
	ItsDao itsDao;
	
	@Autowired
	ItsAddWarehouseController itsAddWarehouseController;
	
	
	
	@RequestMapping(value = ItsConstants.REQUEST_EDIT_INVENTORY_PAGE, method = RequestMethod.GET)
	public ModelAndView request_edit_inventory_item_page(ModelAndView mv) {
		try {
			logger.info("\nInside request_edit_inventory_item_page() :::::: ItsEditInventoryItemsController : Loading the edit Inventory item page for Inventory Tracking System");
			
			JSONArray inventoryItemsDetailsArr =  new JSONArray();
			JSONArray selectedWarehouseDetailsArr = new JSONArray();
			
			// Get Inventory items list details
			List<InventoryItem> itemsList = itsDao.get_inventory_items_list(ItsSqlConstants.GET_INVENTORY_ITEMS_WAREHOUSES_DETAILS);
			
			for(InventoryItem item : itemsList) {
				JSONArray currItemDetailsArr = new JSONArray();
				
				Warehouse warehouse = item.getWarehouse();
				String selectedWarehouseDetails = warehouse.getWarehouseName() +", " +warehouse.getLocation() +", " +warehouse.getZipCode();
				selectedWarehouseDetailsArr.put(selectedWarehouseDetails);
				
				currItemDetailsArr.put(item.getItemId());
				currItemDetailsArr.put(item.getItemName());
				currItemDetailsArr.put(item.getQuantity());
				currItemDetailsArr.put(item.getPrice());
				currItemDetailsArr.put(item.getLotNo());
				currItemDetailsArr.put(item.getReceivedDate());
				
				inventoryItemsDetailsArr.put(currItemDetailsArr);
			}
			
			mv.setViewName(ItsConstants.VIEW_ITS_EDIT_INVENTORY_ITEMS_PAGE);
			mv.addObject("inventoryItemsDetailsArr", inventoryItemsDetailsArr);
			
			mv.addObject("selectedWarehouseDetailsArr", selectedWarehouseDetailsArr);
			
			
			// Get warehouse details
			JSONArray warehouseDetailsArr = itsAddWarehouseController.getAllWarehouseDetails();
			mv.addObject("warehouseDetailsArr", warehouseDetailsArr);
		}
		catch(Exception e) {
			logger.error("Exception occurred inside request_edit_inventory_item_page() :::::: ItsEditInventoryItemsController : ", e);
		}
		
		return mv;
	}
	
	
	@RequestMapping(value = ItsConstants.REQUEST_UPDATE_INVENTORY, method = RequestMethod.POST)
	@ResponseBody
	public String update_inventory(HttpServletRequest request, HttpSession session) {
		JSONObject updateItemsResponse = new JSONObject();
		
		try {
			logger.info("\nInside update_inventory() :::::: ItsEditInventoryItemsController : Update Inventory ");
			
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
			
			
			// Check for valid Warehouse details 
			int warehouseId = itsDao.get_warehouse_id(ItsSqlConstants.GET_INVENTORY_WAREHOUSE_ID, new Object[] {warehouseName, location, zipCode});
			
			if(warehouseId > 0) {
				int itemId = 0;
				if(paramsMap.containsKey(ItsConstants.INVENTORY_ITEM_ID)) {
					itemId = Integer.parseInt(paramsMap.get(ItsConstants.INVENTORY_ITEM_ID)[0]);
				}
				
				String itemName = null;
				if(paramsMap.containsKey(ItsConstants.INVENTORY_ITEM_NAME)) {
					itemName = paramsMap.get(ItsConstants.INVENTORY_ITEM_NAME)[0];
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
				
				String recordUpdatedBy = ItsConstants.ADMIN;
				Timestamp recordUpdatedDt = ItsHelper.getCurrentTimestamp();
				
				
				
				logger.info("Entered Item details = warehouseId : " +warehouseId +", itemName : "+itemName +", quantity : "+quantity +", price : "+price +", lotNo : "+lotNo +", rcvdDt : "+rcvdDt);
				
				Object params[] = {warehouseId, itemName, quantity, price, lotNo, rcvdDt, recordUpdatedBy, recordUpdatedDt, itemId};
				
				// Update Inventory item details
				status = itsDao.update_inventory_items(ItsSqlConstants.UPDATE_INVENTORY_ITEMS_DETAILS, params);
				
				if(status.equals(ItsConstants.SUCCESS)) {
					// Success
					msg = ItsConstants.UPDATE_INVENTORY_ITEM_SUCCESS_MSG;
				}
				else {
					// Error
					msg = ItsConstants.UPDATE_INVENTORY_ITEM_ERROR_MSG;
				}
			}
			else {
				// Warehouse does not exists
				msg = ItsConstants.UPDATE_WAREHOUSE_ALREADY_EXISTS_MSG;
			}
			
			updateItemsResponse.put("status", status);
			updateItemsResponse.put("msg", msg);
			
			
			logger.info("Update Item response = status : "+status +", msg : "+msg);
		}
		catch(Exception e) {
			logger.error("Exception occurred inside update_inventory_item() :::::: ItsEditInventoryItemsController : ", e);
		}
		
		return updateItemsResponse.toString();
	}
	
}