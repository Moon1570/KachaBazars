<%@page import="model.UnionModel"%>
<%@page import="model.UpazillaModel"%>
<%@page import="model.DistrictModel"%>
<%@page import="model.DivisionModel"%>
<%@page import="dao.AreaDao"%>
<%@page import="model.CustomerModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.ProductModel"%>
<%@page import="model.SubcategoryModel"%>
<%@page import="java.util.List"%>
<%@page import="dao.DBData"%>
<%@page import="model.CategoryModel"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Seller Registration</title>
<link rel="stylesheet" href="./css/admin.css">
<link rel="stylesheet" href="./css/menubar.css">
<link
	href="https://fonts.googleapis.com/css?family=Poppins&display=swap"
	rel="stylesheet">
<title>Kachabazars</title>

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
		
		<a href="./pagination?action=paging">View Page</a>
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
	
	

	<c:choose>
		<c:when test="${page == 'sad'}">
		
		<div class="wrapper-box">
		<div class="side-bar">
			<h2>Menu</h2>
			<ul>
				<li class="options-div" id="order"><a class="options"
					href="#order">Orders</a>
					<div class="sub-menu">
						<a href="./orders?action=viewsad">Inventory Orders</a> <a
							href="sad-seller-orders.jsp">Seller Orders</a>
					</div></li>
				
				<li class="options-div" id="sellers"><a class="options"
					href="#sellers">Sellers</a>
					<div class="sub-menu">
						<a href="./sellers?action=new&page=sad">Add Sellers</a> <a
							href="./sellers?action=view&page=sad">View Sellers</a>
					</div></li>
				<li class="options-div"><a class="options"
					href="./customers?action=view&page=sad">Customers</a></li>
				<li class="options-div" id="deliverer"><a class="options"
					href="#deliverer">Delivery Persons</a>
					<div class="sub-menu">
						<a href="./deliveries?action=add&page=sad">Add Deliverers</a> <a
							href="./deliveries?action=view&page=sad">View Deliverers</a>
					</div></li>

				<li class="options-div" id="area"><a class="options"
					href="#area">Areas</a>
					<div class="sub-menu">
						<a href="./areas?action=divPage&page=sad">Division</a> <a
							href="./areas?action=disPage&page=sad">District</a> <a
							href="./areas?action=upaPage&page=sad">Upazilla</a> <a
							href="./areas?action=uniPage&page=sad">Union</a>
					</div></li>
					
				<li class="options-div" id="report"><a class="options"
					href="get-report.jsp">Report Generate</a>
					</li>

			</ul>
		</div>
	</div>
		
	 	</c:when>
	 	
	 	
		<c:otherwise>
		
		<div class="wrapper-box">
		<div class="side-bar">
			<h2>Menu</h2>
			<ul>
				<li class="options-div" id="order"><a class="options"
					href="#order">Orders</a>
					<div class="sub-menu">
						<a href="./orders?action=view">Inventory Orders</a> <a
							href="seller_orders.jsp">Seller Orders</a>
					</div></li>
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
						<a href="./sellers?action=new&page=admin">Add Sellers</a> <a
							href="./sellers?action=view&page=admin">View Sellers</a>
					</div></li>
				<li class="options-div"><a class="options"
					href="./customers?action=view&page=admin">Customers</a></li>
				<li class="options-div" id="deliverer"><a class="options"
					href="#deliverer">Delivery Persons</a>
					<div class="sub-menu">
						<a href="./deliveries?action=add&page=admin">Add Deliverers</a> <a
							href="./deliveries?action=view&page=admin">View Deliverers</a>
					</div></li>

				<li class="options-div" id="area"><a class="options"
					href="#area">Areas</a>
					<div class="sub-menu">
						<a href="./areas?action=divPage&page=admin">Division</a> <a
							href="./areas?action=disPage&page=admin">District</a> <a
							href="./areas?action=upaPage&page=admin">Upazilla</a> <a
							href="./areas?action=uniPage&page=admin">Union</a>
					</div></li>
					
				<li class="options-div" id="subadmin"><a class="options"
					href="view-sub-admin.jsp">Sub Admin</a>
					
					</li>

			</ul>
		</div>
	</div>

		
 		</c:otherwise>
	</c:choose>
	
	<div class="header" align="center">
			<h2>Seller Registration</h2>
		</div>
	
	
	<div class="container">
	
		
		<form action="./sellers" method="post" enctype="multipart/form-data">
			<div class="box">
				<table>
					<tr>
						<th colspan="2" rowsapn="2">
							<div class="table-header">
								<h3>Insert Category Information</h3>
							</div>
						</th>
					</tr>
				<tr>
					<td>Seller First Name</td>
					<td><input type="text" name="sellerFirstName" required="required"></td>
				</tr>
				
				<tr>
					<td>Seller Last Name</td>
					<td><input type="text" name="sellerLastName" required="required"></td>
				</tr>
				
				<tr>
					<td>Seller Phone Number</td>
					<td><input type="text" name="sellerPhone" required="required"></td>
				</tr>
				
				
				
				
				
				
				
				<%
						DBData db = new DBData();
						AreaDao ad = new AreaDao();

						List<DivisionModel> divisionModels = db.getAllDivision();
						request.setAttribute("divisions", divisionModels);
					%>

					<tr>
						<td>Seller Division</td>
						<td><select class="input-field" id="divisionsDropDown" onchange="loadDistricts(this.value)"  name="sellerDivision" required="required">
								<c:forEach items="${divisions}" var="division">
									<option value="${division.divisionId }">${division.divisionBanglaName}</option>
								</c:forEach>
						</select></td>
					</tr>

					<tr>
						<td>Seller District</td>
						<td><select class="input-field" id="districtsDropDown" onchange="loadUpazillas(this.value)" name="sellerDistrict" required="required">
						
						</select></td>
					</tr>


					<tr>
						<td>Seller Upazilla</td>
						<td><select class="input-field" name="sellerUpazilla" id="upazillasDropDown" onchange="loadUnions(this.value)" required="required">
								
						</select></td>
					</tr>



					<tr>
						<td>Seller Union</td>
						<td><select class="input-field" id="unionsDropDown" name="sellerUnion" required="required">
								
						</select></td>
					</tr>
				
				
				
				
				
				
				
				<tr>
					<td>Seller Village</td>
					<td><input type="text" name="sellerVillage" required="required"></td>
				</tr>
				
				<tr>
					<td>Seller Street</td>
					<td><input type="text" name="sellerStreet" required="required"></td>
				</tr>
				
				<tr>
					<td>Seller Holding Number</td>
					<td><input type="text" name="sellerHoldingNumber" required="required"></td>
				</tr>
				
				<tr>
					<td>Seller Date of Birth</td>
					<td><input type="date" name="sellerDOB" required="required"></td>
				</tr>
				
				<tr>
					<td>Seller Image</td>
					<td><input type="file" name="sellerImage" required="required"></td>
				</tr>
				
				<tr>
					<td>Seller NID</td>
					<td><input type="text" name="sellerNID" required="required"></td>
				</tr>
				
				<tr>
					<td>Seller Gender</td>
					<td><select name="sellerGender" required="required">
					
						<option value="male">Male</option>
						<option value="female">Female</option>
						<option value="other">Other</option>
						</select></td>
				</tr>
				<tr>
					<td>Seller Password</td>
					<td><input type="password" name="sellerPassword" required="required"></td>
				</tr>
				
			</table>
				<c:if test="${action == 'reg' }">
					<input type="hidden" value="reg" name="action">
				</c:if>
				
				<input type="submit" value="submit">
					
		
		</form>
	</div>
</body>
</html>