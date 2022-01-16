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
public class ItsRemoveInventoryItemsController {
	
	private static Logger logger = LoggerFactory.getLogger(ItsRemoveInventoryItemsController.class);
	
	@Autowired
	ItsDao itsDao;
	
	@Autowired
	ItsAddWarehouseController itsWarehouseController;
	
	
	
	@RequestMapping(value = ItsConstants.REQUEST_REMOVE_INVENTORY_PAGE, method = RequestMethod.GET)
	public ModelAndView request_remove_inventory_items_page(ModelAndView mv) {
		try {
			logger.info("\nInside request_remove_inventory_items_page() :::::: ItsRemoveInventoryItemsController : Loading the remove Inventory items page for Inventory Tracking System");
			JSONArray inventoryItemsDetailsArr =  new JSONArray();
			JSONArray selectedWarehouseDetailsArr = new JSONArray();
			
			// Get inventory items & warehouses details
			List<InventoryItem> itemsList = itsDao.get_inventory_items_list(ItsSqlConstants.GET_INVENTORY_ITEMS_WAREHOUSES_DETAILS);
			
			for(InventoryItem inventoryItem : itemsList) {
				JSONArray currInventoryItemDetailsArr = new JSONArray();
				
				Warehouse warehouse = inventoryItem.getWarehouse();
				String selectedWarehouseDetails = warehouse.getWarehouseName() +", " +warehouse.getLocation() +", " +warehouse.getZipCode();
				selectedWarehouseDetailsArr.put(selectedWarehouseDetails);
				
				currInventoryItemDetailsArr.put(inventoryItem.getItemId());
				currInventoryItemDetailsArr.put(inventoryItem.getItemName());
				currInventoryItemDetailsArr.put(inventoryItem.getQuantity());
				currInventoryItemDetailsArr.put(inventoryItem.getPrice());
				currInventoryItemDetailsArr.put(inventoryItem.getLotNo());
				currInventoryItemDetailsArr.put(inventoryItem.getReceivedDate());
				
				inventoryItemsDetailsArr.put(currInventoryItemDetailsArr);
			}
			
			mv.setViewName(ItsConstants.VIEW_ITS_REMOVE_INVENTORY_ITEMS_PAGE);
			mv.addObject("inventoryItemsDetailsArr", inventoryItemsDetailsArr);
			
			
			mv.addObject("selectedWarehouseDetailsArr", selectedWarehouseDetailsArr);
			
			
			// Get warehouse details
			JSONArray warehouseDetailsArr = itsWarehouseController.getAllWarehouseDetails();
			mv.addObject("warehouseDetailsArr", warehouseDetailsArr);
		}
		catch(Exception e) {
			logger.error("Exception occurred inside request_remove_inventory_items_page() :::::: ItsRemoveInventoryItemsController : ", e);
		}
		
		return mv;
	}
	
	
	
	@RequestMapping(value = ItsConstants.REQUEST_DELETE_INVENTORY, method = RequestMethod.POST)
	@ResponseBody
	public String delete_inventory_item(HttpServletRequest request, HttpSession session) {
		JSONObject createItemsResponse = new JSONObject();
		
		try {
			logger.info("\nInside delete_inventory_item() :::::: ItsRemoveInventoryItemsController : Delete item ");
			
			Map<String, String[]> paramsMap = request.getParameterMap();
			
			String msg = "";
			String status = ItsConstants.FAILURE;
			
			int itemId = 0;
			if(paramsMap.containsKey(ItsConstants.INVENTORY_ITEM_ID)) {
				itemId = Integer.parseInt(paramsMap.get(ItsConstants.INVENTORY_ITEM_ID)[0]);
			}
			
			String isActive = ItsConstants.INACTIVE;
			String recordUpdatedBy = ItsConstants.ADMIN;
			Timestamp recordUpdatedDt = ItsHelper.getCurrentTimestamp();
			
				
			Object params[] = {isActive, recordUpdatedBy, recordUpdatedDt, itemId};
			
			
			// Delete Inventory item method (logical deletion)
			status = itsDao.delete_inventory_items(ItsSqlConstants.REMOVE_INVENTORY_ITEMS_DETAILS, params);
			
			if(status.equals(ItsConstants.SUCCESS)) {
				// Success
				msg = ItsConstants.DELETE_INVENTORY_ITEM_SUCCESS_MSG;
			}
			else {
				// Error
				msg = ItsConstants.DELETE_INVENTORY_ITEM_ERROR_MSG;
			}
			
			createItemsResponse.put("status", status);
			createItemsResponse.put("msg", msg);
			
			logger.info("Delete Item response = status : "+status +", msg : "+msg);
		}
		catch(Exception e) {
			logger.error("Exception occurred inside delete_inventory_item() :::::: ItsRemoveInventoryItemsController : ", e);
		}
		
		return createItemsResponse.toString();
	}
	
}