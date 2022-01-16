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
			      <a class="navbar-brand" href="#" onclick="redirect('home')">ITS</a>
			    </div>
			    <ul class="nav navbar-nav">
			      <li><a href="#" class="its-activelink" onclick="redirect('home')">Home</a></li>
			      <li><a href="#" class="addWarehouse" onclick="redirect('addWarehouse')">Add Warehouse</a></li>
			      <li><a href="#" class="addInventory" onclick="redirect('addInventory')">Add Inventory</a></li>
			      <li><a href="#" class="viewInventory" onclick="redirect('viewInventory')">View Inventory</a></li>
			      <li><a href="#" class="editInventory" onclick="redirect('editInventory')">Edit Inventory</a></li>
			      <li><a href="#" class="removeInventory" onclick="redirect('removeInventory')">Remove Inventory</a></li>
			    </ul>
			  </div>
			</nav>
		</div>
		
		<!-- Home : Section-->
		<div style="padding: 30px 70px;">
			<h3>Welcome to Inventory Tracking System!</h3>
			
			<div style="padding-top: 15px;">
				<h4>Quick outline of the features: </h4>
				
				<div style="padding-top: 5px;">
					<h4>> Add Warehouse</h4> 
					<h5>Create Warehouse with details like Warehouse Name, Location, Zip Code</h5>
				</div>
				
				<div style="padding-top: 5px;">
					<h4>> Add Inventory</h4> 
					<h5>Create Inventory details with details like Inventory Name, Quantity, Price, Lot No, Received Date</h5>
				</div>
				
				<div style="padding-top: 5px;">
					<h4>> View Inventory</h4> 
					<h5>View added Inventory items in the form of a list</h5>
				</div>
				
				<div style="padding-top: 5px;">
					<h4>> Edit Inventory</h4> 
					<h5>Update Inventory items if any changes made to respective Inventory item</h5>
				</div>
				
				<div style="padding-top: 5px;">
					<h4>> Remove Inventory</h4> 
					<h5>Delete Inventory item when no longer required</h5>
				</div>
			</div>
		</div>
	</div>
</body>
</html>