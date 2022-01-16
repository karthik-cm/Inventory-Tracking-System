package com.inventory.tracking.system.controller;

import java.util.List;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.inventory.tracking.system.constants.ItsConstants;
import com.inventory.tracking.system.constants.ItsSqlConstants;
import com.inventory.tracking.system.dao.ItsDao;
import com.inventory.tracking.system.pojo.InventoryItem;


@Controller
@RequestMapping(ItsConstants.INVENTORY_TRACKING_SYSTEM_REQUEST)
public class ItsViewInventoryItemsController {
	
	private static Logger logger = LoggerFactory.getLogger(ItsViewInventoryItemsController.class);
	
	@Autowired
	ItsDao itsDao;
	
	
	
	@RequestMapping(value = ItsConstants.REQUEST_VIEW_INVENTORY_PAGE, method = RequestMethod.GET)
	public ModelAndView request_view_inventory_items_page(ModelAndView mv) {
		
		try {
			logger.info("\nInside request_view_inventory_items_page() :::::: ItsViewItemsController : Loading the view Inventory items page for Inventory Tracking System");
			JSONArray inventoryItemsDetailsArr =  new JSONArray();
			
			// Get list of Items & Warehouses details
			List<InventoryItem> inventoryItemsList = itsDao.get_inventory_items_warehouses_list(ItsSqlConstants.GET_INVENTORY_ITEMS_WAREHOUSES_DETAILS);
			
			for(InventoryItem inventoryItem : inventoryItemsList) {
				JSONArray currItemDetailsArr = new JSONArray();
				currItemDetailsArr.put(inventoryItem.getWarehouse().getWarehouseId());
				currItemDetailsArr.put(inventoryItem.getWarehouse().getWarehouseName());
				currItemDetailsArr.put(inventoryItem.getWarehouse().getLocation());
				currItemDetailsArr.put(inventoryItem.getWarehouse().getZipCode());
				currItemDetailsArr.put(inventoryItem.getItemId());
				currItemDetailsArr.put(inventoryItem.getItemName());
				currItemDetailsArr.put(inventoryItem.getQuantity());
				currItemDetailsArr.put(inventoryItem.getPrice());
				currItemDetailsArr.put(inventoryItem.getLotNo());
				currItemDetailsArr.put(inventoryItem.getReceivedDate());
				
				inventoryItemsDetailsArr.put(currItemDetailsArr);
			}
			
			mv.setViewName(ItsConstants.VIEW_ITS_VIEW_INVENTORY_ITEMS_PAGE);
			mv.addObject("inventoryItemsDetailsArr", inventoryItemsDetailsArr);
		}
		catch(Exception e) {
			logger.error("Exception occurred inside request_view_inventory_items_page() :::::: ItsViewItemsController : ", e);
		}
		
		return mv;
	}
	
	
}