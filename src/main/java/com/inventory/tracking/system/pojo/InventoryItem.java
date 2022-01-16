package com.inventory.tracking.system.pojo;

public class InventoryItem {
	
	private Warehouse warehouse;
	private int itemId;
	private String itemName;
	private int quantity;
	private float price;
	private int lotNo;
	private String receivedDate;
	
	
	public InventoryItem() {}


	public InventoryItem(Warehouse warehouse, int itemId, String itemName, int quantity, float price, int lotNo, String receivedDate) {
		super();
		this.warehouse = warehouse;
		this.itemId = itemId;
		this.itemName = itemName;
		this.quantity = quantity;
		this.price = price;
		this.lotNo = lotNo;
		this.receivedDate = receivedDate;
	}


	public Warehouse getWarehouse() {
		return warehouse;
	}


	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	

	public int getItemId() {
		return itemId;
	}


	public void setItemId(int itemId) {
		this.itemId = itemId;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public int getLotNo() {
		return lotNo;
	}


	public void setLotNo(int lotNo) {
		this.lotNo = lotNo;
	}


	public String getReceivedDate() {
		return receivedDate;
	}


	public void setReceivedDate(String receivedDate) {
		this.receivedDate = receivedDate;
	}


	@Override
	public String toString() {
		return "Item [warehouse=" + warehouse + ", itemId=" + itemId + ", itemName=" + itemName
				+ ", quantity=" + quantity + ", price=" + price + ", lotNo=" + lotNo + ", receivedDate=" + receivedDate
				+ "]";
	}

}