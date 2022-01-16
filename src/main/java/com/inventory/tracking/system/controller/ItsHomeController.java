package com.inventory.tracking.system.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.inventory.tracking.system.constants.ItsConstants;

@Controller
@RequestMapping(ItsConstants.INVENTORY_TRACKING_SYSTEM_REQUEST)
public class ItsHomeController {
		
	private static Logger logger = LoggerFactory.getLogger(ItsHomeController.class);
	
	
	@RequestMapping(value = ItsConstants.REQUEST_HOME_PAGE, method = RequestMethod.GET)
	public String get_home_page() {
		logger.info("\nInside get_home_page() :::::: ItsHomeController : Loading the home page for Inventory Tracking System");
		return ItsConstants.VIEW_ITS_HOME_PAGE;
	}
}
