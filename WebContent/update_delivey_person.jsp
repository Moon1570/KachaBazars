<%@page import="model.DivisionModel"%>
<%@page import="dao.AreaDao"%>
<%@page import="java.util.List"%>
<%@page import="dao.DBData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
<link rel="stylesheet" href="./css/admin.css">
<link rel="stylesheet" href="./css/menubar.css">
	<link href="https://fonts.googleapis.com/css?family=Poppins&display=swap" rel="stylesheet">
	<title>Add Delivery Person</title>
	
	<script type="text/javascript">
	

	function loadDistricts(str) {
		var xhttp = new XMLHttpRequest();
		
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {

				var o = JSON.parse(this.responseText); // is a javascript object
				json = JSON.stringify(o)

				var ele = document.getElementById('districtsDropDown');
				ele.innerHTML="";
				for (x in o) {
				
					ele.innerHTML = ele.innerHTML
							+ '<option value="' + x + '">'
							+ o[x] + '</option>';
				}
				/*
				for (x in o) {
					document.getElementById("divisions").innerHTML += x + o[x]
							+ "</br>";
				}
*/
			}

		};
		xhttp.open("GET",
				"./dropdown?divisionsId=" + str + "&action=divisions", true);
		xhttp.send();
		//alert("Hello...")

	}

	function loadUpazillas(str) {
		var xhttp = new XMLHttpRequest();
		
		
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {

				var o = JSON.parse(this.responseText); // is a javascript object
				json = JSON.stringify(o)

				var ele = document.getElementById('upazillasDropDown');
				ele.innerHTML="";
				for (x in o) {
				
					ele.innerHTML = ele.innerHTML
							+ '<option value="' + x + '">'
							+ o[x] + '</option>';
				}
			}

		};
		xhttp.open("GET",
				"./dropdown?districtId="+str+"&action=districts", true);
		xhttp.send();
		//alert("Hello...")

	}

	function loadUnions(str) {
		var xhttp = new XMLHttpRequest();
		
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {

				var o = JSON.parse(this.responseText); // is a javascript object
				json = JSON.stringify(o)

				var ele = document.getElementById('unionsDropDown');
				ele.innerHTML="";
				for (x in o) {
					ele.innerHTML = ele.innerHTML
							+ '<option value="' + x + '">'
							+ o[x] + '</option>';
				}


			}

		};
		xhttp.open("GET",
				"./dropdown?upazillasId="+str+"&action=unions", true);
		xhttp.send();
		//alert("Hello...")

	}
</script>
</head>
<body>

	<div id="navbar">
	
	<div class="logo">
		KachaBazar.com
	</div>
		<div class="search">
			<input class="search-box" placeholder="Type to search"> <input
				type="submit" class="search-button" value="Search">
		</div>
		<div class="menu-items">
			<a class="active" href="javascript:void(0)">Home</a> 
			<a href="">Profile</a> 
			<a href="">Notification</a>
		</div>
	</div>
	
	

	<div class="wrapper-box">
		<div class="side-bar">
			<h2>Menu</h2>
			<ul>
				<li class="options-div"><a class="options"
					href="./orders?action=view">Dashboard</a></li>
				<li class="options-div" id="product"><a class="options"
					href="#product">Products</a>
					<div class="sub-menu">
						<a href="./products?action=new">Add Products</a> <a
							href="./products?action=view">View Products</a>
					</div></li>
				<li class="options-div" id='category'><a class="options"
					href="#category">Category</a>
					<div class="sub-menu">
						<a href="./categories?action=new">Add Category</a> <a
							href="Category.jsp">View Category</a> 
					</div></li>

				<li class="options-div" id="subcat"><a class="options"
					href="#subcat">Sub-Category</a>
					<div class="sub-menu">
						<a class="options"
							href="./subcategories?action=new&page=admin">Add Sub-category</a> <a
							href="./subcategories?action=view&page=admin">View Sub-category</a>
					</div></li>
				
				<li class="options-div" id="sellers"><a class="options"
					href="#sellers">Sellers</a>
					<div class="sub-menu">
						<a href="./sellers?action=new">Add Sellers</a> <a
							href="./sellers?action=view">View Sellers</a>
					</div></li>
				<li class="options-div"><a class="options"
					href="./customers?action=view">Customers</a></li>
				<li class="options-div" id="deliverer"><a class="options"
					href="#deliverer">Delivery Persons</a>
					<div class="sub-menu">
						<a href="./deliveries?action=add">Add Deliverers</a> <a
							href="./deliveries?action=view">View Deliverers</a>
					</div></li>
			</ul>
		</div>
	</div>
	
	
	<div class="header">
		<h2>Add New Delivery Person</h2>
		</div>
	
	
	<div class="container">
		<form enctype="multipart/form-data" action="./deliveries?action=update" method="post">
		<div class="box">
			
			<table>
				<tr>
					<td>First Name</td>
					<td><input type="text" name="deliveryPersonFirstName" value="" required="required"></td>
				</tr>
				
				<tr>
					<td>Last Name</td>
					<td><input type="text" name="deliveryPersonLastName" value="" required="required"></td>
				</tr>
				
				<tr>
					<td>Phone Number</td>
					<td><input type="text" name="deliveryPersonPhone" value="" required="required"></td>
				</tr>
				
				<tr>
					<td>Date of Birth</td>
					<td><input type="date" name="deliveryPersonDOB" value="" required="required"></td>
				</tr>
				
				<tr>
					<td>Image</td>
					<td><input type="file" name="deliveryImage" value="" required="required"></td>
				</tr>
				
				<tr>
					<td>NID</td>
					<td><input type="text" name="deliveryPersonNID" value="" required="required"></td>
				</tr>
				
				<tr>
					<td>Gender</td>
					<td><select name="deliveryPersonGender" required="required">
					
						<option value="male">Male</option>
						<option value="female">Female</option>
						<option value="other">Other</option>
						</select></td>
				</tr>
				
				<%
						DBData db = new DBData();
						AreaDao ad = new AreaDao();

						List<DivisionModel> divisionModels = db.getAllDivision();
						request.setAttribute("divisions", divisionModels);
					%>

					<tr>
						<td>Delivery Division</td>
						<td><select class="input-field" id="divisionsDropDown" onchange="loadDistricts(this.value)"  name="deliveryDivision" required="required">
								<c:forEach items="${divisions}" var="division">
									<option value="${division.divisionId }">${division.divisionBanglaName}</option>
								</c:forEach>
						</select></td>
					</tr>

					<tr>
						<td>Delivery District</td>
						<td><select class="input-field" id="districtsDropDown" onchange="loadUpazillas(this.value)" name="deliveryDistrict" required="required">
						
						</select></td>
					</tr>


					<tr>
						<td>Delivery Upazilla</td>
						<td><select class="input-field" name="deliveryUpazilla" id="upazillasDropDown" onchange="loadUnions(this.value)" required="required">
								
						</select></td>
					</tr>



					<tr>
						<td>Delivery Union</td>
						<td><select class="input-field" id="unionsDropDown" name="deliveryUnion" required="required">
								
						</select></td>
					</tr>
					
					<tr>
						<td>Delivery Village</td>
						<td><input type="text" class="input-field" name="deliveryVillage" required="required">
								
						</td>
					</tr>
					<tr>
						<td>Delivery Street</td>
						<td><input type="text" class="input-field"  name="deliveryStreet" required="required">
								
						</td>
					</tr>
					
					<tr>
						<td>Delivery Holding Number</td>
						<td><input type="text" class="input-field"  name="deliveryHoldingNumber" required="required">
								
						</td>
					</tr>
					
					<tr>
						<td>Password</td>
						<td><input type="password" class="input-field" value="" name="deliveryPassword" required="required">
								
						</td>
					</tr>
	
				
			</table>
			
			
			<div class="submit-button" align="center">
				<input type="submit" value="Submit">
			</div>
		</div>
	</form>
	</div>
</body>
</html>