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
  	<script src="../js/tableHTMLExport.js"></script>
</head>
<body>
	<div style="padding: 25px">
		<h2>Inventory Tracking System</h2>
		
		<!-- Navigation Bar -->
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
			      <li><a href="#" class="viewInventory its-activelink" onclick="redirect('viewInventory')">View Inventory</a></li>
			      <li><a href="#" class="editInventory" onclick="redirect('editInventory')">Edit Inventory</a></li>
			      <li><a href="#" class="removeInventory" onclick="redirect('removeInventory')">Remove Inventory</a></li>
			    </ul>
			  </div>
			</nav>
		</div>
		
		<div>
			<div style="padding-top: 10px; padding-bottom: 15px;">
				<button class="btn btn-success" onclick="exportTableAsCsv()">Export to CSV</button>
			</div>
			
			<table class="table table-bordered items-table">
			    <thead>
			      <tr>
			     	<th class="hide">Warehouse ID</th>
			     	<th>Sl No.</th>
			     	<th>Warehouse Name</th>
			     	<th>Location</th>
			     	<th>Zip Code</th>
			        <th class="hide">Item ID</th>
			        <th>Inventory Item Name</th>
			        <th>Quantity</th>
			        <th>Price</th>
			        <th>Lot No</th>
			        <th>Received Date</th>
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
	
	// form Inventory Items table
	formInventoryItemsTableDetails(inventoryItemsDetailsArr);
});


function formInventoryItemsTableDetails(inventoryItemsDetailsArr){
	var tbodyHtml = ''
	
	for(var i=0; i<inventoryItemsDetailsArr.length; i++){
		var currItemsDetailsArr = inventoryItemsDetailsArr[i];
		
		tbodyHtml += 
			'<tr>'
				tbodyHtml += '<td>' +(i+1) +'</td>'
			
				for(var j=0; j<currItemsDetailsArr.length; j++){
					tbodyHtml += '<td>' +currItemsDetailsArr[j] +'</td>'
				}
				
			+'</tr>';
	}
	
	$('tbody#items_tbody').html(tbodyHtml);
	$('tbody#items_tbody').find('tr').find('td:eq(1), td:eq(5)').hide();
}


function exportTableAsCsv(){
	var timestamp = Date.now();
	var filename = 'inventory_items_extract_' +timestamp +'.csv';
	
	var type = 'csv';
	
	$('.items-table').tableHTMLExport({
	  type: type,
	  filename: filename,
	});
}



function submitOrderDetails(){
	var formObj = $('form#createItemForm');
	$(formObj).find('.errormsg').addClass('hide').text('');
	var flag = false;
	
	var warehouse = $(formObj).find('select#warehouse').val();
	var itemname = $(formObj).find('input#itemname').val();
	var quantity = $(formObj).find('input#quantity').val();
	var price = $(formObj).find('input#price').val();
	var lotno = $(formObj).find('input#lotno').val();
	var rcvddt = $(formObj).find('input#rcvddate').val();
	
	if(warehouse != null && warehouse.length > 0 && itemname != null && itemname.length > 0 && quantity != null && quantity.length > 0 
			&& price != null && price.length > 0 && lotno != null && lotno.length > 0 && rcvddt != null && rcvddt.length > 0){
		flag = true;
	}
	else{
		$(formObj).find('.errormsg').removeClass('hide').text('Error: Missing mandatory fields');
		return;
	}
	
	
	if(flag){
		warehouse = warehouse.split(',');
		var warehouseName = warehouse[0].trim();
		var location = warehouse[1].trim();
		var zipcode = warehouse[2].trim();
		
		$.ajax({
	        type: 'POST',
	        url: '/InventoryTrackingSystem/createItem',
	        data: {
	        	'warehouseName': warehouseName,
	        	'location': location,
	        	'zipcode': zipcode,
	            'itemname': itemname,
	            'quantity': quantity,
	            'price': price,
	            'lotno': lotno,
	            'rcvddt': rcvddt
	        },
	        success: function(response){
	            // alert('Response : '+JSON.stringify(response));
	            var responseJson = JSON.parse(response);
	            var status = responseJson.status;
	            var msg = responseJson.msg;
	            alert(msg);
	            
	            if(status == 'SUCCESS'){
	            	$('ul.its-navbar').find('a.addItems').trigger('click');
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