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
		
		<!-- Navigation Bar -->
		<div style="margin-top: 25px;">
			<nav class="navbar navbar-inverse">
			  <div class="container-fluid">
			    <div class="navbar-header">
			      <a class="navbar-brand" href="#">ITS</a>
			    </div>
			    <ul class="nav navbar-nav its-navbar">
			      <li><a href="#" class="home" onclick="redirect('home')">Home</a></li>
			      <li><a href="#" class="addWarehouse its-activelink" onclick="redirect('addWarehouse')">Add Warehouse</a></li>
			      <li><a href="#" class="addInventory" onclick="redirect('addInventory')">Add Inventory</a></li>
			      <li><a href="#" class="viewInventory" onclick="redirect('viewInventory')">View Inventory</a></li>
			      <li><a href="#" class="editInventory" onclick="redirect('editInventory')">Edit Inventory</a></li>
			      <li><a href="#" class="removeInventory" onclick="redirect('removeInventory')">Remove Inventory</a></li>
			    </ul>
			  </div>
			</nav>
		</div>
		
		<!-- Create Warehouse : Section-->
		<div style="margin-left: 75px;">
			<form id="createWarehouseForm">
				<div style="padding-bottom: 15px;"><span class="errormsg hide"></span></div>
			
			    <div style="width: 35%; padding-bottom: 15px;">
			      	<label for="warehousename">Warehouse Name:</label>   
			        <input type="text" class="form-control" name="warehousename" id="warehousename" placeholder="Enter Warehouse Name" />
			    </div>
			      
			    <div style="width: 35%; padding-bottom: 15px;">
			      	<label for="location">Location:</label>   
			        <input type="text" class="form-control" name="location" id="location" placeholder="Enter Location" />
			    </div>
			    
			    <div style="width: 35%; padding-bottom: 15px;">
			      	<label for="zipcode">Zip Code:</label>        
			        <input type="number" min="1" maxlength="5" class="form-control" name="zipcode" id="zipcode" placeholder="Enter Zip Code" />
			    </div>
			    
			    <div style="padding-top: 30px;">
                	<button type="reset" class="btn btn-danger" style="width: 14%; padding: 10px">Reset</button>
                    <button type="button" class="btn btn-success" style="width: 14%; padding: 10px; margin-left: 15px" onclick="submitOrderDetails()">Submit</button>  
                </div>
			</form>
		</div>
	</div>
</body>

<script>

function submitOrderDetails(){
	var formObj = $('form#createWarehouseForm');
	$(formObj).find('.errormsg').addClass('hide').text('');
	var flag = false;
	
	var warehouseName = $(formObj).find('input#warehousename').val();
	var location = $(formObj).find('input#location').val();
	var zipCode = $(formObj).find('input#zipcode').val();
	
	if(warehouseName != null && warehouseName.length > 0 && location != null && location.length > 0 && zipCode != null && zipCode.length > 0){
		flag = true;
	}
	else{
		$(formObj).find('.errormsg').removeClass('hide').text('Error: Missing mandatory fields');
		return;
	}
	
	
	if(flag){
		$.ajax({
	        type: 'POST',
	        url: '/InventoryTrackingSystem/createWarehouse',
	        data: {
	            'warehouseName': warehouseName,
	            'location': location,
	            'zipCode': zipCode
	        },
	        success: function(response){
	            var responseJson = JSON.parse(response);
	            var status = responseJson.status;
	            var msg = responseJson.msg;
	            alert(msg);
	            
	            if(status == 'SUCCESS'){
	            	$('ul.its-navbar').find('a.addWarehouse').trigger('click');
	            }
	        },
	        error: function(response){
	            alert('Some error occurred : '+JSON.stringify(response));
	        }
	    });
	}
}

</script>
</html>