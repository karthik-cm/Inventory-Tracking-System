<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Inventory Tracking System</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<!--  CDN Import : Bootstrap 3, jQuery 3.4.1-->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  	
  	<!--  Import : CSS / JS -->
  	<link rel="stylesheet" href="../css/inventory_tracking_system.css">
  	<script src="../js/inventory_tracking_system.js"></script>
</head>
<body>
	<div style="padding: 25px">
		<h2>Inventory Tracking System</h2>
		
		<!-- Navigation Bar : Section-->
		<div style="margin-top: 25px;">
			<nav class="navbar navbar-inverse">
			  <div class="container-fluid">
			    <div class="navbar-header">
			      <a class="navbar-brand" href="#">ITS</a>
			    </div>
			    <ul class="nav navbar-nav its-navbar">
			      <li><a href="#" class="home" onclick="redirect('home')">Home</a></li>
			      <li><a href="#" class="addWarehouse" onclick="redirect('addWarehouse')">Add Warehouse</a></li>
			      <li><a href="#" class="addInventory" onclick="redirect('addInventory')">Add Inventory</a></li>
			      <li><a href="#" class="viewInventory" onclick="redirect('viewInventory')">View Inventory</a></li>
			      <li><a href="#" class="editInventory its-activelink" onclick="redirect('editInventory')">Edit Inventory</a></li>
			      <li><a href="#" class="removeInventory" onclick="redirect('removeInventory')">Remove Inventory</a></li>
			    </ul>
			  </div>
			</nav>
		</div>
		
		<!-- Edit Inventory : Section -->
		<div>
			<div style="padding-bottom: 15px;"><span class="errormsg hide"></span></div>
		
			<table class="table table-bordered edit-items-table">
			    <thead>
			      <tr>
			      	<th class="hide">Item ID</th>
			      	<th>Sl No.</th>
			        <th>Warehouse</th>
			        <th>Item Name</th>
			        <th>Quantity</th>
			        <th>Price</th>
			        <th>Lot No</th>
			        <th>Received Date</th>
			        <th>Edit</th>
			        <th>Update</th>
			      </tr>
			    </thead>
			    
			    <tbody id="items_tbody"></tbody>
			</table>
		</div>
	</div>
</body>

<script>

$(document).ready(function(){
	var inventoryItemsDetailsArr = JSON.parse(JSON.stringify(${inventoryItemsDetailsArr}));
	var warehouseDetailsArr = JSON.parse(JSON.stringify(${warehouseDetailsArr}));
	
	// Form items table  
	formItemsTableDetails(inventoryItemsDetailsArr, warehouseDetailsArr);
	
	var selectedWarehouseDetailsArr = JSON.parse(JSON.stringify(${selectedWarehouseDetailsArr}));
	
	// Select warehouse dropdown automatically
	$('table.edit-items-table').find('tbody').find('tr').each(function(ind, obj) {
		$(obj).find('#warehouse').val(selectedWarehouseDetailsArr[ind]);
	});
});


function formItemsTableDetails(inventoryItemsDetailsArr, warehouseDetailsArr){
	var tbodyHtml = ''
	
	for(var i=0; i<inventoryItemsDetailsArr.length; i++){
		var currItemsDetailsArr = inventoryItemsDetailsArr[i];
		
		tbodyHtml += 
			'<tr id="item_'+(i+1)+'">'
				+'<td class="hide"><input type="text" class="form-control" name="itemid" id="itemid" value="'+currItemsDetailsArr[0]+'" disabled />' +'</td>'
				+'<td>' +(i+1) +'</td>'
				+'<td>' +formWarehouseDropdown(warehouseDetailsArr) +'</td>'		
				+'<td><input type="text" class="form-control" name="inventoryname" id="inventoryname" value="'+currItemsDetailsArr[1]+'" disabled /></td>'
				+'<td><input type="number" min="1" class="form-control" name="quantity" id="quantity" value="'+currItemsDetailsArr[2]+'" disabled /></td>'
				+'<td><input type="number" min="0.1" class="form-control" name="price" id="price" value="'+currItemsDetailsArr[3]+'" disabled /></td>'
				+'<td><input type="number" min="1" class="form-control" name="lotno" id="lotno" value="'+currItemsDetailsArr[4]+'" disabled /></td>'
				+'<td><input class="form-control" name="rcvddate" id="rcvddate" type="date" value="'+currItemsDetailsArr[5]+'" disabled /></td>'
				
				+'<td><button type="button" class="btn btn-info" style="" onclick="editFields($(this))">Edit</button>' +'</td>'
				+'<td><button type="button" class="btn btn-primary" style="" onclick="updateItemDetails($(this))">Update</button>' +'</td>'
			+'</tr>';
	}
	
	$('tbody#items_tbody').html(tbodyHtml);
}


function formWarehouseDropdown(warehouseDetailsArr){
	var dropdown = '';
	
	dropdown += 
		'<select id="warehouse" class="form-control" name="warehouse" disabled>'
			+'<option value="Select">--Select--</option>';
			
			for(var i=0; i<warehouseDetailsArr.length; i++){
				dropdown += '<option value="'+warehouseDetailsArr[i]+'">'+warehouseDetailsArr[i] +'</option>';
			}
			
		+'</select>';
	
	return dropdown;
}


function editFields(obj){
	var trObj = $(obj).parents('tr[id^="item_"]');
	$(trObj).find('select, input').removeAttr('disabled');
	$(trObj).find('input#itemid').attr('disabled', true);
}


function updateItemDetails(obj){
	var trObj = $(obj).parents('tr[id^="item_"]');
	$('.errormsg').addClass('hide').text('');
	var flag = false;
	
	var itemId = $(trObj).find('input#itemid').val();
	var warehouse = $(trObj).find('select#warehouse').val();
	var inventoryName = $(trObj).find('input#inventoryname').val();
	var quantity = $(trObj).find('input#quantity').val();
	var price = $(trObj).find('input#price').val();
	var lotNo = $(trObj).find('input#lotno').val();
	var rcvdDt = $(trObj).find('input#rcvddate').val();
	
	if(warehouse != null && warehouse.length > 0 && inventoryName != null && inventoryName.length > 0 && quantity != null && quantity.length > 0 &&
			price != null && price.length > 0 && lotNo != null && lotNo.length > 0 && rcvdDt != null && rcvdDt.length > 0){
		flag = true;
	}
	else{
		$('.errormsg').removeClass('hide').text('Error: Missing mandatory fields');
		return;
	}
	
	
	if(flag){
		warehouse = warehouse.split(',');
		var warehouseName = warehouse[0].trim();
		var location = warehouse[1].trim();
		var zipCode = warehouse[2].trim();
		
		$.ajax({
	        type: 'POST',
	        url: '/InventoryTrackingSystem/updateInventory',
	        data: {
	        	'itemId': itemId,
	        	'warehouseName': warehouseName,
	        	'location': location,
	        	'zipCode': zipCode,
	            'inventoryName': inventoryName,
	            'quantity': quantity,
	            'price': price,
	            'lotNo': lotNo,
	            'rcvdDt': rcvdDt
	        },
	        success: function(response){
	            var responseJson = JSON.parse(response);
	            var status = responseJson.status;
	            var msg = responseJson.msg;
	            alert(msg);
	            
	            if(status == 'SUCCESS'){
	            	$('ul.its-navbar').find('a.editInventory').trigger('click');
	            }
	        },
	        error: function(response){
	            alert('error response : '+JSON.stringify(response));
	        }
	    });
	}
	
}
</script>
</html>