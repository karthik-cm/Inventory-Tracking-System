package com.inventory.tracking.system.constants;

public class ItsConstants {
	
	public static final String INVENTORY_TRACKING_SYSTEM_REQUEST = "/InventoryTrackingSystem";
	
	// fields
	public static final String ACTIVE = "Y";
	public static final String INACTIVE = "N";
	
	public static final String ADMIN = "ITS_ADMIN";
	
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";
	
	
	
	
	// Form params
	
	// Warehouse
	public static final String WAREHOUSE_NAME = "warehouseName";
	public static final String WAREHOUSE_LOCATION = "location";
	public static final String WAREHOUSE_ZIP_CODE = "zipCode";
	
	// Inventory Item
	public static final String INVENTORY_ITEM_ID = "itemId";
	public static final String INVENTORY_ITEM_NAME = "inventoryName";
	public static final String INVENTORY_ITEM_QUANTITY = "quantity";
	public static final String INVENTORY_ITEM_PRICE = "price";
	public static final String INVENTORY_ITEM_LOT_NO = "lotNo";
	public static final String INVENTORY_ITEM_RECEIVED_DATE = "rcvdDt";
	
	
	
	
	// Home
	public static final String REQUEST_HOME_PAGE = "/home";
	public static final String VIEW_ITS_HOME_PAGE = "its_home";

	
	
	// Add Warehouse
	public static final String REQUEST_ADD_WAREHOUSE_PAGE = "/addWarehouse";
	public static final String VIEW_ITS_ADD_WAREHOUSE_PAGE = "its_add_warehouse";
	
	public static final String REQUEST_CREATE_WAREHOUSE = "/createWarehouse";
	
	public static final String CREATE_WAREHOUSE_SUCCESS_MSG = "Success: Warehouse added successfully!";
	public static final String CREATE_WAREHOUSE_ERROR_MSG = "Error: Warehouse could not be added!";
	public static final String CREATE_WAREHOUSE_ALREADY_EXISTS_MSG = "Info: Warehouse already exists! Kindly check the details";
	
	
	
	// Add Inventory (CREATE)
	public static final String REQUEST_ADD_INVENTORY_ITEMS_PAGE = "/addInventory";
	public static final String VIEW_ITS_ADD_INVENTORY_ITEMS_PAGE = "its_add_inventory_items";
	
	public static final String REQUEST_CREATE_INVENTORY = "/createInventory";
	
	public static final String CREATE_INVENTORY_ITEM_SUCCESS_MSG = "Success: Inventory item added successfully!";
	public static final String CREATE_INVENTORY_ITEM_ERROR_MSG = "Error: Inventory item could not be added!";
	public static final String CREATE_INVENTORY_ITEM_ALREADY_EXISTS_MSG = "Info: Inventory item already exists! Please update quantity in Edit Inventory";
	public static final String CREATE_INVENTORY_ITEM_WAREHOUSE_NOT_EXISTS_MSG = "Error: Warehouse does not exists! Please check";
	
	
	
	
	// View Inventory (READ)
	public static final String REQUEST_VIEW_INVENTORY_PAGE = "/viewInventory";
	public static final String VIEW_ITS_VIEW_INVENTORY_ITEMS_PAGE = "its_view_inventory_items";
	
	
	
	// Edit Inventory (UPDATE)
	public static final String REQUEST_EDIT_INVENTORY_PAGE = "/editInventory";
	public static final String VIEW_ITS_EDIT_INVENTORY_ITEMS_PAGE = "its_edit_inventory_items";
	
	public static final String REQUEST_UPDATE_INVENTORY = "/updateInventory";
	
	public static final String UPDATE_INVENTORY_ITEM_SUCCESS_MSG = "Success: Inventory item updated successfully!";
	public static final String UPDATE_INVENTORY_ITEM_ERROR_MSG = "Error: Inventory item could not be updated!";
	public static final String UPDATE_WAREHOUSE_ALREADY_EXISTS_MSG = "Error: Warehouse does not exists! Please check";
	
	
	
	// Remove Inventory (DELETE)
	public static final String REQUEST_REMOVE_INVENTORY_PAGE = "/removeInventory";
	public static final String VIEW_ITS_REMOVE_INVENTORY_ITEMS_PAGE = "its_remove_inventory_items";
	
	public static final String REQUEST_DELETE_INVENTORY = "/deleteInventory";
	
	public static final String DELETE_INVENTORY_ITEM_SUCCESS_MSG = "Success: Item removed successfully!";
	public static final String DELETE_INVENTORY_ITEM_ERROR_MSG = "Error: Item could not be removed!";
	
	
	
	
	// Error Handling
	public static final String REQUEST_ERROR = "/error";
	public static final String VIEW_ITS_ERROR_PAGE = "its_error";

}
