<%@page import="java.util.List"%>
<%@page import="dao.DBData"%>
<%@page import="model.CategoryModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add New Subcategory</title>
<link rel="stylesheet" href="./css/admin.css">
<link rel="stylesheet" href="./css/menubar.css">
	<link href="https://fonts.googleapis.com/css?family=Poppins&display=swap" rel="stylesheet">
	<title>Add New Area</title>
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
	
	
	<div class="header">
		<h2>Add New Subcategory</h2>
		</div>
	
	<div class="container">
	<form action="/ecommerce/subcategories" method="post">
		<div class="box">
		<table align="center">
			<th colspan="2" rowsapn="2">
				<div class="table-header">
					<h3>Insert Subcategory Information</h3>
				</div>
			</th>
			<tr>
				<td class="name-label">Subcategory Name</td>
				<td><input type="text" placeholder="Enter Subcategory Name" name="subcategory_name"  value="${subcategories.subcategoryName }" required="required"></td>
			</tr>
			
			<tr>
				<td class="name-label">Description</td>
				<td><input type="text" placeholder="Enter Subcategory Description" name="subcategory_description" value="${subcategories.subcategoryDescription }" required="required"></td>
			</tr>
			
			<tr>
				<td class="name-label">Government price</td>
				<td><input type="number" placeholder="Enter Government Price" name="subcategory_govt_price"  value="${subcategories.govtPrice }" required="required"></td>
			</tr>
			
			<%
			
			DBData db = new DBData();
			List<CategoryModel> categoryModel = db.getAllCategories();
			request.setAttribute("categories", categoryModel);

			%>
			
		<tr>
		<td class="name-label">Category ID</td>
		<td>
		<select class="dropdown" name="dropdownCategory" required="required">
		
		<c:if test="${action == 'update' }">
			<option value="${subcategories.categoryInformation.categoryId }">${subcategories.categoryInformation.categoryName }</option>
		</c:if>
		
    <c:forEach items="${categories}" var="category">
        <option value="${category.categoryId}"> ${category.categoryName}</option>
    </c:forEach>
</select>
		</td>
		</tr>
		
		
			<tr>
				<td>
				
				<c:if test="${action=='update'}">
				<input type="hidden" value="update" name="action">	
				<input type="hidden" value="${subcategories.subcategoryId }" name="sid">	
				</c:if>
				
				<c:if test="${action=='new'}">
				<input type="hidden" value="new" name="action">
				</c:if>
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