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
			      <li><a href="#" class="addInventory its-activelink" onclick="redirect('addInventory')">Add Inventory</a></li>
			      <li><a href="#" class="viewInventory" onclick="redirect('viewInventory')">View Inventory</a></li>
			      <li><a href="#" class="editInventory" onclick="redirect('editInventory')">Edit Inventory</a></li>
			      <li><a href="#" class="removeInventory" onclick="redirect('removeInventory')">Remove Inventory</a></li>
			    </ul>
			  </div>
			</nav>
		</div>
		
		<!-- Add Inventory : Section-->
		<div style="margin-left: 75px;">
			<form id="createInventoryForm">
				<div style="padding-bottom: 15px;"><span class="errormsg hide"></span></div>
			
			    <div id="warehouseDiv" style="width: 35%; padding-bottom: 15px;"></div>
			    
			    <div style="width: 35%; padding-bottom: 15px;">
			    	<label for="item">Inventory Name:</label>   
			        <input type="text" class="form-control" name="inventoryname" id="inventoryname" placeholder="Enter Inventory Name" />
			    </div>
			    
			    <div style="width: 35%; padding-bottom: 15px;">
			      	<label  for="quantity">Quantity:</label>        
			        <input type="number" min="1" class="form-control" name="quantity" id="quantity" placeholder="Enter Quantity" />
			    </div>
			    
			      
			    <div style="width: 35%; padding-bottom: 15px;">
			      	<label  for="pwd">Price (Per Unit):</label>      
			        <input type="number" min="0.1" step="any" class="form-control" name="price" id="price" placeholder="Enter Price" />
			    </div>
			    
			    <div style="width: 35%; padding-bottom: 15px;">
			      	<label  for="pwd">Lot No:</label>     
			        <input type="number" min="1" class="form-control" name="lotno" id="lotno" placeholder="Enter Lot No" />
			    </div>
			    
			    <div style="width: 35%; padding-bottom: 15px;">
			      	<label  for="pwd">Received Date:</label>       
			        <input class="form-control" name="rcvddate" id="rcvddate" type="date" />
			    </div>
			    
			    
			    <div style="padding-top: 30px;">
                  	<button type="reset" class="btn btn-danger" style="width: 14%; padding: 10px">Reset</button>
                    <button type="button" class="btn btn-success" style="width: 14%; padding: 10px; margin-left: 15px" onclick="submitCreateInventoryDetails()">Submit</button>  
                </div>
			</form>
		</div>
	</div>
</body>

<script>

$(document).ready(function(){
	var warehouseDetailsArr = JSON.parse(JSON.stringify(${warehouseDetailsArr}));
	
	// Form warehouse details dropdown
	formWarehouseDropdown(warehouseDetailsArr);
});


function formWarehouseDropdown(warehouseDetailsArr){
	var dropdown = '';
	dropdown += '<label for="category">Warehouse:</label>';
	
	dropdown += 
		'<select id="warehouse" class="form-control" name="warehouse">'
			+'<option value="Select">--Select--</option>';
		
			
			for(var i=0; i<warehouseDetailsArr.length; i++){
				dropdown += '<option value="'+warehouseDetailsArr[i]+'">'+warehouseDetailsArr[i] +'</option>';
			}
		
			
		+'</select>';
	
	$('div#warehouseDiv').html(dropdown);
	
	return dropdown;
}


function submitCreateInventoryDetails(){
	var formObj = $('form#createInventoryForm');
	$(formObj).find('.errormsg').addClass('hide').text('');
	var flag = false;
	
	var warehouse = $(formObj).find('select#warehouse').val();
	var inventoryName = $(formObj).find('input#inventoryname').val();
	var quantity = $(formObj).find('input#quantity').val();
	var price = $(formObj).find('input#price').val();
	var lotNo = $(formObj).find('input#lotno').val();
	var rcvdDt = $(formObj).find('input#rcvddate').val();
	
	if(warehouse != null && warehouse.length > 0 && inventoryName != null && inventoryName.length > 0 && quantity != null && quantity.length > 0 
			&& price != null && price.length > 0 && lotNo != null && lotNo.length > 0 && rcvdDt != null && rcvdDt.length > 0){
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
		var zipCode = warehouse[2].trim();
		
		$.ajax({
	        type: 'POST',
	        url: '/InventoryTrackingSystem/createInventory',
	        data: {
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
	            	$('ul.its-navbar').find('a.addInventory').trigger('click');
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