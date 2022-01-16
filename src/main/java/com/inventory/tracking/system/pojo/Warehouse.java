package com.inventory.tracking.system.pojo;


public class Warehouse {
	private int warehouseId;
	private String warehouseName;
	private String location;
	private int zipCode;
	
	public Warehouse() {}
	
	public Warehouse(int warehouseId, String warehouseName, String location, int zipCode) {
		super();
		this.warehouseId = warehouseId;
		this.warehouseName = warehouseName;
		this.location = location;
		this.zipCode = zipCode;
	}

	public int getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "Warehouse [warehouseId=" + warehouseId + ", warehouseName=" + warehouseName + ", location=" + location
				+ ", zipCode=" + zipCode + "]";
	}
	
}