<%@page import="model.OrderSellerProductModel"%>
<%@page import="controller.OrderFromSeller"%>
<%@page import="model.OrdersModel"%>
<%@page import="model.DeliveryPersonModel"%>
<%@page import="model.SellerModel"%>
<%@page import="model.CustomerModel"%>
<%@page import="java.util.List"%>
<%@page import="dao.DBData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<!-- 
<script src="javascript/jquery.js"></script>
<script>
	$(document).ready(function() {
		$('#dd1').change(function() {
			fillOptions('dd2', this);
		});
		$('#dd2').change(function() {
			fillOptions('dd3', this);
		});
	});
	function fillOptions(ddId, callingElement) {
		var dd = $('#' + ddId);
		$.getJSON(
				'dropdown?dd=' + ddId + '&val=' + $(callingElement).val(),
				function(opts) {
					$('>option', dd).remove(); // Clean old options first.
					if (opts) {
						$.each(opts, function(key, value) {
							dd.append($('<option/>').val(key).text(value));
						});
					} else {
						dd.append($('<option/>').text("Please select parent"));
					}
				});
	}
</script>

 -->


<head>
<link rel="stylesheet" href="./css/menubar.css">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">


<script type="text/javascript"
	src="https://cdn.datatables.net/v/bs4-4.1.1/jq-3.3.1/dt-1.10.20/b-1.6.1/b-flash-1.6.1/datatables.min.js"></script>
<title>Admin</title>

<link
	href="https://fonts.googleapis.com/css?family=Poppins&display=swap"
	rel="stylesheet">
</head>
<body>

	<%
		session = request.getSession();
		if (session.getAttribute("adminlogin") == "false" || session.getAttribute("adminlogin") == null) {
			//	request.getRequestDispatcher("admin-login.jsp");
			System.out.println(session.getAttribute("adminlogin"));
			response.sendRedirect("admin-login.jsp");

		} else {
	%>


	<div id="navbar">

		<div class="logo">
			KachaBazar.com <a href="./Homepage.jsp">View Page</a>
		</div>
		
<!-- Dropdown subsites for admin -->
		
<select onChange="window.location.href=this.value" class="form-control float-right" style="width: 10%">
	<option value="#">sub-site</option>
	<option value="./customers?action=login">Customer site</option>
	<option value="./sellers?action=login">Seller site</option>
	<option value="./deliveries?action=view">Delivery site</option>
	<option value="sub-admin-login.jsp">sub-admin site</option>
</select>


		<div class="menu-items">

			<a href="./admin?action=viewprofile">Profile</a> <a
				href="./admin?action=adminlogout">Logout</a>
		</div>
	</div>

<!-- Sidebar for admin -->

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
					<li class="options-div" id="android"><a class="options"
					href="./android?action=getDetails">Android</a>
					
					</li>

			</ul>
		</div>
	</div>

<!-- End of sidebar -->

<!-- Start of main content -->

	<div class="header" align="center">
		<h2>Orders From Inventory</h2>
	</div>

	<%
		DBData db = new DBData();
			List<OrdersModel> ordersModels = db.getAllOrders();
			request.setAttribute("orders", ordersModels);
	%>

	
	<!-- Start of table for inventory orders -->

	<div class="container float-right my-5"
		style="z-index: -1; position: relative;">

		<div class="table-responsive my-3">

			<table id="myTable"
				class="table table-striped table-bordered table-hover text-center">
				<thead class="thead-dark">
					<tr>
						<th scope="col">Order ID</th>
						<th scope="col">Order Date</th>
						<th scope="col">Delivery Person</th>
						<th scope="col">Order Status</th>
						<th scope="col">Order Expected Date</th>
						<th scope="col">Payment Type</th>
						<th scope="col">Action</th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>

		</div>
	</div>


<!-- End of table for inventory orders -->

<!-- Jquery data-table for auto pagination and dara parsing -->

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							var table = $('#myTable')
									.DataTable(
											{

												ajax : {
													method : "GET",
													url : "./ordertable?action=getInventoryOrders",
													dataSrc : "demo"
												},
												columns : [
														{
															"data" : "count"
														},
														{
															"data" : "odate"
														},
														{
															"data" : "edate"
														},
														{
															"data" : "delivery"
														},
														{
															"data" : "status"
														},
														{
															"data" : "paytype"
														},
														{
															"data" : null,
															"mRender" : function(
																	data, type,
																	full) {
																return '<a class="btn btn-outline-info btn-md" href=./orderoperations?action=vieworder&page=admin&oid='
																		+ data.id
																		+ '>'
																		+ 'View'
																		+ '</a>'
															}
														} ]
											});
						});
	</script>

	<%
		}
	%>

</body>
</html>