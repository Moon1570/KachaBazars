<%@page import="dao.DBData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<link rel="stylesheet" href="./css/menubar.css">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">



<script type="text/javascript"
	src="https://cdn.datatables.net/v/bs4-4.1.1/jq-3.3.1/dt-1.10.20/b-1.6.1/b-flash-1.6.1/datatables.min.js"></script>

<link
	href="https://fonts.googleapis.com/css?family=Poppins&display=swap"
	rel="stylesheet">
</head>

<body>
	
	<div id="navbar">

		<div class="logo">
			KachaBazar.com <a href="./Homepage.jsp">View Page</a>
		</div>
		<div class="search">
			<input class="search-box" placeholder="Type to search"> <input
				type="submit" class="search-button" value="Search">
		</div>
		<div class="menu-items">
			
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

	<div class="container float-right mt-5"
		style="z-index: 5; position: relative;">
		<div class="table-responsive my-5">
			<form class="form-inline" method="post"
				action="./areas?action=adddiv" id="form">
				<div class="form-group mx-sm-3 mb-2">
					<label for="exampleInputEmail1">
						<h4>Add Division</h4> &nbsp&nbsp
					</label> <input type="text" class="form-control" name="divName"
						id="exampleInputEmail1" aria-describedby="emailHelp"
						placeholder="Enter division name" required="required"> <small id="emailHelp"
						class="form-text text-muted">Please write it in Bangla </small>
				</div>

				<button type="submit" class="btn btn-primary">Submit</button> 
			</form>
		</div>
		<hr
			style="-moz-border-bottom-colors: none; -moz-border-image: none; -moz-border-left-colors: none; -moz-border-right-colors: none; -moz-border-top-colors: none; border-color: #EEEEEE -moz-use-text-color #FFFFFF; border-style: solid none; border-width: 3px 0; margin: 10px 0;">

		<div class="text-center">
			<h2 class="d-inline">Division List</h2>
			<hr
				style="-moz-border-bottom-colors: none; -moz-border-image: none; -moz-border-left-colors: none; -moz-border-right-colors: none; -moz-border-top-colors: none; border-color: #EEEEEE -moz-use-text-color #FFFFFF; border-style: solid none; border-width: 3px 0; margin: 18px 0;">
		</div>
	</div>


	<div class="container float-right"
		style="z-index: 5; position: relative;">
		<div class="table-responsive">
			<table id="myTable"
				class="table table-striped table-bordered table-hover text-center">
				<thead class="thead-dark">
					<tr>
						<th scope="col">Id</th>
						<th scope="col">Division Name</th>
						<th scope="col">Action</th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>

		</div>
	</div>


	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var table = $('#myTable')
									.DataTable(
											{

												ajax : {
													method : "GET",
													url : "./areas?action=div",
													dataSrc : "demo"
												},
												columns : [
														{
															"data" : "count"
														},
														{
															"data" : "name"
														},
														{
															"data" : null,
															"mRender" : function(
																	data, type,
																	full) {
																return '<a class="btn btn-outline-info btn-md" href=./areas?action=update&divId='
																		+ data.id
																		+ '>'
																		+ 'Edit'
																		+ '</a>   <a class="btn btn-outline-danger btn-md" href=./areas?action=delete&divId='
																		+ data.id
																		+ '>'
																		+ 'Delete'
																		+ '</a>';
															}
														} ]
											});
						});
/*
		var form = $('#form');
		form.submit(function() {

			$.ajax({
				type : form.attr('method'),
				url : form.attr('action'),
				data : form.serialize(),
				success : function(data) {
					//var json = $.parseJSON(data);
					//$('#myTable tr:last').after('<tr><td>'+json.count+'</td><td>'+json.name+'</td> <td></td></tr>');
				}
			});

			return false;
		});*/
	</script>

</body>
</html>