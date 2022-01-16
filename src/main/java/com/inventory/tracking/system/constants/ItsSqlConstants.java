package com.inventory.tracking.system.constants;

public class ItsSqlConstants {
	
	
	// Add Warehouse
	
	public static final String CHECK_INVENTORY_WAREHOUSE_DETAILS = "Select count(*) from INVENTORY_WAREHOUSE_DETAILS where is_active = 'Y' and warehouse_name=? and location=? and zipcode=?";
	
	public static final String INSERT_INTO_INVENTORY_WAREHOUSE_DETAILS = "Insert into INVENTORY_WAREHOUSE_DETAILS (warehouse_name, location, zipcode, is_active, record_created_by, record_created_dt) values (?, ?, ?, ?, ?, ?)";
	
	public static final String GET_INVENTORY_WAREHOUSE_DETAILS = "Select warehouse_name, location, zipcode from INVENTORY_WAREHOUSE_DETAILS where is_active='Y'";
	
	
	
	
	// Add Items
	
	public static final String GET_INVENTORY_WAREHOUSE_ID = "Select warehouse_id from INVENTORY_WAREHOUSE_DETAILS where warehouse_name=? and location=? and zipcode=? and is_active='Y'";
	
	public static final String CHECK_IF_INVENTORY_ITEM_ALREADY_EXISTS = "Select count(*) from INVENTORY_ITEMS_DETAILS where warehouse_id=? and item_name=? and price=? and is_active='Y'";
	
	public static final String INSERT_INTO_INVENTORY_ITEMS_DETAILS = "Insert into INVENTORY_ITEMS_DETAILS (warehouse_id, item_name, quantity, price, lot_no, received_date, is_active, record_created_by, record_created_dt) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	
	
	
	// View Items
	
	public static final String GET_INVENTORY_ITEMS_WAREHOUSES_DETAILS = "Select w.warehouse_id, w.warehouse_name, w.location, w.zipcode, i.item_id, i.item_name, i.quantity, i.price, i.lot_no, i.received_date"
			+ "	FROM INVENTORY_WAREHOUSE_DETAILS w inner join INVENTORY_ITEMS_DETAILS i"
			+ "	ON w.warehouse_id = i.warehouse_id"
			+ "	WHERE w.is_active = 'Y' and i.is_active ='Y' ORDER BY i.item_id";

	
	
	
	// Edit Items
	
	public static final String GET_INVENTORY_ITEM_ID = "Select item_id from INVENTORY_ITEMS_DETAILS where warehouse_id=? and item_name=? and price=? and is_active='Y'";
	
	public static final String UPDATE_INVENTORY_ITEMS_DETAILS = "Update INVENTORY_ITEMS_DETAILS set warehouse_id=?, item_name=?, quantity=?, price=?, lot_no=?, received_date=?, record_updated_by=?, record_updated_dt=? where is_active='Y' and item_id=?";
	
	
	
	
	// Remove Items (Logical deletion)
	
	public static final String REMOVE_INVENTORY_ITEMS_DETAILS = "Update INVENTORY_ITEMS_DETAILS set is_active=?, record_updated_by=?, record_updated_dt=? where item_id=?";
	
}
