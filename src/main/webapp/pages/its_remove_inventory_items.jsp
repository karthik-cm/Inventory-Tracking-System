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
		
		<!-- Navigation Bar : Section -->
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
			      <li><a href="#" class="editInventory" onclick="redirect('editInventory')">Edit Inventory</a></li>
			      <li><a href="#" class="removeInventory its-activelink" onclick="redirect('removeInventory')">Remove Inventory</a></li>
			    </ul>
			  </div>
			</nav>
		</div>
		
		<!-- Remove Inventory : Section-->
		<div>
			<div style="padding-bottom: 15px;"><span class="errormsg hide"></span></div>
		
			<table class="table table-bordered edit-items-table">
			    <thead>
			      <tr>
			      	<th class="hide">Item ID</th>
			      	<th>Sl No.</th>
			        <th>Warehouse</th>
			        <th>Inventory Item Name</th>
			        <th>Quantity</th>
			        <th>Price</th>
			        <th>Lot No</th>
			        <th>Received Date</th>
			        <th>Delete</th>
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
	var selectedWarehouseDetailsArr = JSON.parse(JSON.stringify(${selectedWarehouseDetailsArr}));
	
	// Form Inventory items table
	formItemsTableDetails(inventoryItemsDetailsArr, selectedWarehouseDetailsArr);
});


function formItemsTableDetails(inventoryItemsDetailsArr, selectedWarehouseDetailsArr){
	var tbodyHtml = ''
	
	for(var i=0; i<inventoryItemsDetailsArr.length; i++){
		var currItemsDetailsArr = inventoryItemsDetailsArr[i];
		
		tbodyHtml += 
			'<tr id="item_'+(i+1)+'">'
				+'<td id="itemid_'+i+'" class="hide">' +currItemsDetailsArr[0] +'</td>'
				
				+'<td>' +(i+1) +'</td>'
				+'<td>' +selectedWarehouseDetailsArr[i] +'</td>'		
				+'<td>' +currItemsDetailsArr[1] +'</td>'
				+'<td>' +currItemsDetailsArr[2] +'</td>'
				+'<td>' +currItemsDetailsArr[3] +'</td>'
				+'<td>' +currItemsDetailsArr[4] +'</td>'
				+'<td>' +currItemsDetailsArr[5] +'</td>'
				+'<td><button type="button" class="btn btn-danger" style="" onclick="deleteInventoryItemDetails($(this))">Delete</button>' +'</td>'
			+'</tr>';
	}
	
	$('tbody#items_tbody').html(tbodyHtml);
}


function deleteInventoryItemDetails(obj){
	var trObj = $(obj).parents('tr[id^="item_"]');
	var itemId = $(trObj).find('td[id^="itemid_"]').text();
	
	
	var confirmFlag = confirm('Are you sure you want to delete this Inventory ?');
	
	if(confirmFlag){
		$.ajax({
	        type: 'POST',
	        url: '/InventoryTrackingSystem/deleteInventory',
	        data: {
	        	'itemId': itemId
	        },
	        success: function(response){
	            var responseJson = JSON.parse(response);
	            var status = responseJson.status;
	            var msg = responseJson.msg;
	            alert(msg);
	            
	            if(status == 'SUCCESS'){
	            	$('ul.its-navbar').find('a.removeInventory').trigger('click');
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