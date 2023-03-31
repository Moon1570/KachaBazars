<%@page import="java.util.Iterator"%>
<%@page import="model.CartDetailsModel"%>
<%@page import="model.UnionModel"%>
<%@page import="model.UpazillaModel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="model.ProductModel"%>
<%@page import="model.SubcategoryModel"%>
<%@page import="model.SellersProduct"%>
<%@page import="dao.AreaDao"%>
<%@page import="model.DistrictModel"%>
<%@page import="model.DivisionModel"%>
<%@page import="model.CustomerModel"%>
<%@page import="java.util.List"%>
<%@page import="dao.DBData"%>
<%@page import="model.CategoryModel"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="navbar.jsp" />
<jsp:include page="sidenavbar.jsp" />

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="images/favicon.png">
<link rel="stylesheet" type="text/css" href="css/oneclickbuy.css">
<title>KachaBazar</title>

	<!-- Loads Area info from DB -->


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

	function loadDelivery(str) {
		var xhttp = new XMLHttpRequest();

		alert(str);
		
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {

				var o = JSON.parse(this.responseText); // is a javascript object
				json = JSON.stringify(o)

				var ele = document.getElementById('deliverydd');
				ele.innerHTML="";
				for (x in o) {
					ele.innerHTML = ele.innerHTML
							+ '<option value="' + x + '">'
							+ o[x] + '</option>';
				}


			}

		};
		xhttp.open("GET",
				"./dropdown?unionId="+str+"&action=delivery", true);
		xhttp.send();
		//alert("Hello...")

	}
	
</script>

<script src="javascript/jquery.js"></script>

</head>
<body>

	<%
		DBData db = new DBData();
		int cid = Integer.parseInt(request.getAttribute("cid").toString());
		CustomerModel customerModels = db.getCustomerById(cid);
		System.out.println(cid);
		int cartId = Integer.parseInt(request.getParameter("cartId"));

		double total = 0;

		List<CartDetailsModel> cartDetailsModels = db.getCartDetailsByCartId(cartId);
		Iterator<CartDetailsModel> it = cartDetailsModels.iterator();

		while (it.hasNext()) {

			Object type = (Object) it.next();
			CartDetailsModel cdm = (CartDetailsModel) type;

			total = total + (Double.parseDouble(cdm.getProductModel().getProductPrice())
					* cdm.getCartProductQuantity());
		}

		request.setAttribute("cartId", cartId);
		request.setAttribute("total", total);
		request.setAttribute("customers", customerModels);
	%>

	<%
		AreaDao ad = new AreaDao();

		List<DivisionModel> divisionModels = db.getAllDivision();
		request.setAttribute("divisions", divisionModels);
	%>
	<%
		List<DistrictModel> districtModels = ad.getAllDistricts();
		request.setAttribute("districts", districtModels);
	%>


	<%
		List<UpazillaModel> upazillaModels = ad.getAllUpazillas();
		request.setAttribute("upazillas", upazillaModels);
	%>

	<%
		List<UnionModel> unionModels = ad.getAllUnions();
		request.setAttribute("unions", unionModels);
	%>




	<div class="page-info">
		<h2>
			<u>Buy Product</u>
		</h2>
	</div>

	<!-- Use current Information to buy -->

	<div class="detail-container">
		<div class="current-info">
			<h3>Use your current Information to buy</h3>
			<div class="customer-image">
				<img src="./getimage?id=${customers.customerId}&action=customer">
			</div>
			<form
				action="./orders?action=cartconfirmuserinfo&cid=${customers.customerId}&cartId=${cartId}" method="post">

				<p>
					<u>Expected Date</u>
				</p>
				<p>
					<input class="input-field" type="date" name="deliveryDatesameinfo"
						required="required" placeholder="Expected Date">
				</p>
				<button class="button medium red">Order</button>
			</form>
		</div>

			<!-- Fill In Information to buy -->


		<div class="new-info">
			<h3>or add new Information</h3>
			<form class="new"
				action="./orders?action=cartconfirm&cid=${customers.customerId}&cartId=${cartId}"
				method="post">
				<h4>Shipping Information</h4>
				<p>
					<input class="input-field" type="text" name="careOfContact"
						required="required" placeholder="Name">
				</p>

				<p>
					<input class="input-field" type="text" name="deliveryPhone"
						required="required" placeholder="Phone">
				</p>

				<p>
					<select class="input-field" name="deliveryDivision" id="divisionsDropDown" onchange="loadDistricts(this.value)"
						required="required">
						<option selected="selected" disabled="disabled">Select Division</option>
						<c:forEach items="${divisions}" var="division">
							<option value="${division.divisionId }">${division.divisionBanglaName}</option>
						</c:forEach>
					</select>
				</p>

				<p>
					<select class="input-field" name="deliveryDistrict"
						required="required" id="districtsDropDown" onchange="loadUpazillas(this.value)">
						
					</select>
				</p>

				<p>
					<select class="input-field" name="deliveryUpazilla" id="upazillasDropDown" onchange="loadUnions(this.value)"
						required="required">
						
					</select>
				</p>

				<p>
					<select class="input-field" name="deliveryUnion" id="unionsDropDown"
						required="required">
						
					</select>
				</p>


				<p>
					<input class="input-field" type="text" name="deliveryVillage"
						required="deliveryVillage" placeholder="Village">
				</p>

				<p>
					<input class="input-field" type="text" name="deliveryStreet"
						required="required" placeholder="Street">
				</p>
				<p>
					<input class="input-field" type="text" name="deliveryZipCode"
						required="required" placeholder="ZipCode">
				</p>

				<p>
					<u>Expected Date</u>
				</p>

				<p>
					<input class="input-field" type="date" name="deliveryDate"
						required="required" placeholder="Expected Date">

				</p>

				<p>
					<button class="button blue medium">Checkout</button>
				</p>
		</div>
	</div>



	</div>



	</form>
</body>
</html>