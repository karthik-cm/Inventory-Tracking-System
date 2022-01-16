package com.inventory.tracking.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.inventory.tracking.system.constants.ItsConstants;

@Controller
public class ItsErrorController implements ErrorController {
	
	private static Logger logger = LoggerFactory.getLogger(ItsErrorController.class);
	
	
	@RequestMapping(value = ItsConstants.REQUEST_ERROR, method = RequestMethod.GET)
    public String handle_error(HttpServletRequest request) {
		logger.info("Inside handle_error() :::::: ItsErrorController - Redirecting to Error page after handling request from unknown url");
		return ItsConstants.VIEW_ITS_ERROR_PAGE;
    }
	
	
	public String getErrorPath() {
        return ItsConstants.REQUEST_ERROR;
    }
}