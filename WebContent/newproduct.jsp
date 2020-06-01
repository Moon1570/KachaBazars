
<%@page import="model.UnitModel"%>
<%@page import="model.ProductModel"%>
<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="model.SubcategoryModel"%>
<%@page import="java.util.List"%>
<%@page import="dao.DBData"%>
<%@page import="model.CategoryModel"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="javascript/jquery.js"></script>
<link rel="stylesheet" href="./css/admin.css">
<link rel="stylesheet" href="./css/menubar.css">
<link
	href="https://fonts.googleapis.com/css?family=Poppins&display=swap"
	rel="stylesheet">
<title>Products</title>
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


	<div class="header" align="center">
		<h2>All Products</h2>
	</div>
	


		<div class="container">
		
		<form enctype="multipart/form-data" action="./products" method="post">
			<div class="box">
			<table>
				<tr>
					<th colspan="2" rowsapn="2">
					<div class="table-header">
						<h3>Insert Product Information</h3>
						</div>
					</th>
				</tr>
				<tr>
				</tr>
				<tr>
					<td class="name-label">Product Name</td>
					<td><input type="text" name="productName" placeholder="Enter Product Name" value="${product.productName}" required="required"></td>
				</tr>

				<tr>
					<td class="name-label">Product Image</td>
					<td>
					
				
					       	<input type="file" name="productImage" id="file" class="upload"/>
							<label for="file">Choose a file</label>
					</td>
				</tr>
				
				
				<%
					DBData db = new DBData();
					List<UnitModel> unitModels = db.getAllUnits();
					request.setAttribute("units", unitModels);
				%>

				<tr>
					<td class="label">Unit</td>
					<td><select class="dropdown" name="unit" required="required">
							<c:forEach items="${units}" var="unit">
								<option value="${unit.unitId}">  ${unit.unit}</option>
							</c:forEach>
					</select></td>
				</tr>

				<%
				
					List<CategoryModel> categoryModel = db.getAllCategories();
					request.setAttribute("categoryioptions", categoryModel);
				%>


				<tr>
					<td class="label">Product Category</td>
					<td><select class="dropdown" name="dropdownProductCategory" required="required">
							<option value="${product.productCategory.categoryId}" >${product.productCategory.categoryName}</option>
							<c:forEach items="${categoryioptions}" var="category">
								<option value="${category.categoryId}">${category.categoryId}  ${category.categoryName}</option>
							</c:forEach>
					</select></td>
				</tr>
				
				

				
				

				<%
					DBData db2 = new DBData();
					List<SubcategoryModel> subcategoryModel = db2.getAllSubcategories();
					request.setAttribute("subcategorieoptions", subcategoryModel);
				%>

				<tr>
					<td class="name-label">Product Sub-category</td>
					<td><select class="dropdown" name="dropdownProductSubcategory" required="required">
						<option value="${product.productSubcategory.subcategoryId}">${product.productSubcategory.subcategoryName} </option>
							<c:forEach items="${subcategorieoptions}" var="subcategory">
								<option value="${subcategory.subcategoryId}">${subcategory.subcategoryId}
									${subcategory.subcategoryName}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td class="name-label">Product Price</td>
					<td><input type="text" name="productPrice" placeholder="BDT /=" value="${product.productPrice}" required="required"></td>
				</tr>
				
				<tr>
					<td class="name-label">Stock</td>
					<td><input type="text" name="productStock" placeholder="Stock" value="${product.productStock}" required="required"></td>
				</tr>
				
				
				<tr>
					<td class="name-label">Government Price</td>
					<td><input type="text" name="governmentPrice" placeholder="BDT /=" value="${product.governmentPrice}" required="required"></td>
				</tr>
				
				<tr>
					<td class="name-label">Product Description</td>
					<td><input type="text" name="productDescription" placeholder="Enter Product Description" value="${product.productDescription}" required="required"></td>
				</tr>
				
				<tr>
					<td class="name-label">Product Type</td>
					<td>
						<select name="productType">
							<option value="${product.type}">${product.type}</option>
							<option value="general">General</option>
							<option value="Featured">Featured</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td>
					
					<c:if test="${action=='update'}">
						<input type="submit" value="update" name="action">
					</c:if>
					<input type="hidden" value="${product.productId}" name="pid">
					
					<c:choose>
					    <c:when test="${action=='update'}">
					        <div class="submit-button">
								<input type="hidden" value="addmore" name="action">
							</div>
					    </c:when>    
					    <c:otherwise>
					        <div class="submit-button">
								<input type="submit" value="addmore" name="action">
							</div>
					    </c:otherwise>
					</c:choose>
			</td>
			<td>
					
					
					<c:choose>
					    <c:when test="${action=='update'}">
					        <div class="submit-button">
								<input type="hidden" value="submit" name="action">
							</div>
					    </c:when>    
					    <c:otherwise>
					        <div class="submit-button">
								<input type="submit" value="submit" name="action">
							</div>
					    </c:otherwise>
					</c:choose>
			</td>
				</tr>
			</table>
			</div>

		</form>

	</div>


</body>

<script>
	window.onscroll = function() {
		myFunction()
	};

	var navbar = document.getElementById("navbar");
	var sticky = navbar.offsetTop;

	function myFunction() {
		if (window.pageYOffset >= sticky) {
			navbar.classList.add("sticky")
		} else {
			navbar.classList.remove("sticky");
		}
	}
</script>
</html>