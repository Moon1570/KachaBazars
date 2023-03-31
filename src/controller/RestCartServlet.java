/*
 * This servlet is in charge of the RestCart, the request, response handling, and URL mapping with the get and post methods.
 * This is to handle the REST API calls for the android app.
 * All the common operations for the RestCart are handled here. such as viewing the RestCart page.
 */
package controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.zip.Deflater;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.AreaDao;
import dao.DBData;
import model.CartDetailsModel;
import model.CartModel;
import model.CustomerModel;
import model.DistrictModel;
import model.DivisionModel;
import model.OrdersModel;
import model.ProductModel;
import model.UnionModel;
import model.UpazillaModel;

/*
 * This servlet will be handling all the request and response REST API calls from the url /restcart
 */
public class RestCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// DBData object to access the database
	DBData db = new DBData();

	// AreaDao object to access the database
	AreaDao ad = new AreaDao();

	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");

		//if the action is getcartdetails, the response will be the cart details of the customer in JSON format
		if (action.equalsIgnoreCase("getcartdetails")) {

			int cid = Integer.parseInt(request.getParameter("cid"));

			CustomerModel customerModel = db.getCustomerById(cid);
			CartModel cartModel = db.getCartByCustomerId(cid);
			List<CartDetailsModel> cartDetailsModels = db.getCartDetailsByCartId(cartModel.getCartId());

			Iterator<CartDetailsModel> it = cartDetailsModels.iterator();

			JSONArray jsonArray = new JSONArray();

			while (it.hasNext()) {
				Object type = (Object) it.next();

				CartDetailsModel sub = (CartDetailsModel) type;

				JSONObject JO = new JSONObject();

				try {
					JO.put("productName", sub.getProductModel().getProductName());
					JO.put("price", sub.getProductModel().getProductPrice());
					JO.put("stotal",
							Double.parseDouble(sub.getProductModel().getProductPrice()) * sub.getCartProductQuantity());

					jsonArray.put(JO);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());

		}
		
		//if action is getProfile, then the profile info will be retrived from DB and response will be written in JSON format
		else if(action.equals("getProfile")) {
			int cid = Integer.parseInt(request.getParameter("cid"));
			
			CustomerModel customerModel= db.getCustomerById(cid);
			
			JSONArray jsonArray = new JSONArray();
			JSONObject JO = new JSONObject();
			
			byte[] a = customerModel.getImage();
			Deflater compressor = new Deflater();
			compressor.setLevel(Deflater.BEST_COMPRESSION);
			
			  compressor.setInput(a);
			  compressor.finish();
			   
			  // Create an expandable byte array to hold the compressed data.
			  // It is not necessary that the compressed data will be smaller than
			  // the uncompressed data.
			  ByteArrayOutputStream bos = new ByteArrayOutputStream(a.length);
			   
			  // Compress the data
			  byte[] buf = new byte[1024];
			  while (!compressor.finished()) {
			      int count = compressor.deflate(buf);
			      bos.write(buf, 0, count);
			  }
			  try {
			      bos.close();
			  } catch (IOException e) {
			  }
			   
			  // Get the compressed data
			  byte[] compressedData = bos.toByteArray();
			
			
			String string = Base64.getEncoder().encodeToString(compressedData);
			
			
			try {
				
				JO.put("name", customerModel.getCustomerFirstName() + " "+ customerModel.getCustomerLastName());
				JO.put("address", customerModel.getUnionModel().getUnionBanglaName()
						+", "+customerModel.getUpazillaModel().getUpazillaBangaName()
						+", "+customerModel.getDistrictModel().getDistrictBanglaName()
						+", "+customerModel.getDivisionmodel().getDivisionBanglaName());
				JO.put("home", customerModel.getCustomerVillage()+", "+customerModel.getCustomerStreet()+", "+
						customerModel.getCustomerHoldingNumber());
				JO.put("dob", customerModel.getCustomerDOB());
				JO.put("phone", customerModel.getCustomerPhone());
				JO.put("image", string);
				
				
				jsonArray.put(JO);
				
				PrintWriter pw = response.getWriter();
				pw.write(jsonArray.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//if action is getProfileseparated, then the profile info will be retrived from DB and response will be written in JSON format
		else if(action.equals("getProfileseparated")) {
			int cid = Integer.parseInt(request.getParameter("cid"));
			
			CustomerModel customerModel= db.getCustomerById(cid);
			
			JSONArray jsonArray = new JSONArray();
			JSONObject JO = new JSONObject();
			
			byte[] a = customerModel.getImage();
			Deflater compressor = new Deflater();
			compressor.setLevel(Deflater.BEST_COMPRESSION);
			
			  compressor.setInput(a);
			  compressor.finish();
			   
			  // Create an expandable byte array to hold the compressed data.
			  // It is not necessary that the compressed data will be smaller than
			  // the uncompressed data.
			  ByteArrayOutputStream bos = new ByteArrayOutputStream(a.length);
			   
			  // Compress the data
			  byte[] buf = new byte[1024];
			  while (!compressor.finished()) {
			      int count = compressor.deflate(buf);
			      bos.write(buf, 0, count);
			  }
			  try {
			      bos.close();
			  } catch (IOException e) {
			  }
			   
			  // Get the compressed data
			  byte[] compressedData = bos.toByteArray();
			
			
			String string = Base64.getEncoder().encodeToString(compressedData);
			
			
			try {
				
				JO.put("fname", customerModel.getCustomerFirstName());
				JO.put("lname", customerModel.getCustomerLastName());
				JO.put("village", customerModel.getCustomerVillage());
				JO.put("street", customerModel.getCustomerStreet());
				JO.put("holding", customerModel.getCustomerHoldingNumber());
				JO.put("zip", customerModel.getCustomerZipcode());
				JO.put("dob", customerModel.getCustomerDOB());
				JO.put("phone", customerModel.getCustomerPhone());
				JO.put("image", string);
				
				
				jsonArray.put(JO);
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
		}

	}
	


	//this method will handle the post request from the client side
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");


		//if the action is cardadd, then the cart details will be added to the database
		if (action.equalsIgnoreCase("cartadd")) {

			int cid = Integer.parseInt(request.getParameter("cid"));
			int pid = Integer.parseInt(request.getParameter("pid"));
			String qty = request.getParameter("qty");

			CustomerModel customerModel = db.getCustomerById(cid);
			ProductModel productModel = db.getProductById(pid);

			CartModel cartModel = db.getCartByCustomerId(cid);
			CartDetailsModel cartDetailsModel = new CartDetailsModel();

			cartDetailsModel.setCartId(cartModel);
			cartDetailsModel.setCartProductQuantity(Double.parseDouble(qty));
			cartDetailsModel.setCartProductStatus("Undecided");
			cartDetailsModel.setProductModel(productModel);

			db.saveCartDetails(cartDetailsModel);

			JSONArray jsonArray = new JSONArray();

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());

		} 
		
		//if the action is cartorder, then the cart details will be added to the database
		else if (action.equalsIgnoreCase("cartorder")) {

			int cid = Integer.parseInt(request.getParameter("cid"));

			CartModel cartModel = db.getCartByCustomerId(cid);
			List<CartDetailsModel> cartDetailsModel = db.getCartDetailsByCartId(cartModel.getCartId());

			String coc, phone, expectedDate, street, village, zip, paymentType;

			coc = request.getParameter("coc");
			phone = request.getParameter("phone");

			expectedDate = request.getParameter("date");
			street = request.getParameter("street");
			village = request.getParameter("village");
			zip = request.getParameter("zip");
			paymentType = request.getParameter("paymentType");
			
			cid = Integer.parseInt(request.getParameter("cid"));			
			
			int divId = Integer.parseInt(request.getParameter("divId"));
			int a = Integer.parseInt(request.getParameter("disId"));
			int b = Integer.parseInt(request.getParameter("upaId"));
			int c = Integer.parseInt(request.getParameter("uniId"));
			
			
			ArrayList<DistrictModel> districtModels = (ArrayList<DistrictModel>) ad.getDistrictByDivisionId(divId);
			int disId = districtModels.get(a).getDistrictId();
			ArrayList<UpazillaModel> upazillaModels = (ArrayList<UpazillaModel>) ad.getUpazillaByDistrictId(disId);
			int upaId = upazillaModels.get(b).getUpazillaId();
			ArrayList<UnionModel> unionModels = (ArrayList<UnionModel>) ad.getUnionByUpazillaId(upaId);

			DivisionModel divisionModel = ad.getDivisionById(divId);
			
			OrdersModel ordersModel = new OrdersModel();

			ordersModel.setUpazillaModel(upazillaModels.get(b));
			ordersModel.setUnionModel(unionModels.get(c));
			ordersModel.setDistrictModel(districtModels.get(a));
			ordersModel.setDivisionModel(divisionModel);

			CustomerModel customerModel = db.getCustomerById(cid);

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			String orderingTime = (dtf.format(now));

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

			try {
				ordersModel.setExpectedDeliveryDate(simpleDateFormat.parse(expectedDate));
			} catch (Exception e) {
				e.printStackTrace();
			}
			String hex = UUID.randomUUID().toString();

			LocalDateTime now1 = LocalDateTime.now();

			DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");

			String tranId = hex + dtf2.format(now1);

			Iterator<CartDetailsModel> it = cartDetailsModel.iterator();

			double total = 0;

			while (it.hasNext()) {

				Object type = (Object) it.next();
				CartDetailsModel cdm = (CartDetailsModel) type;

				ordersModel.setTranId(tranId);
				ordersModel.setPaymentStatus(false);
				ordersModel.setCareOfContact(coc);
				ordersModel.setCustomerModel(customerModel);
				ordersModel.setOrderDate(orderingTime);
				ordersModel.setOrderQuantity(cdm.getCartProductQuantity());
				ordersModel.setOrderStatus("unallocated");
				ordersModel.setOrderStreet(street);
				ordersModel.setOrderVillage(village);
				ordersModel.setOrderZipCode(zip);
				ordersModel.setPhoneNumber(phone);
				ordersModel.setProductModel(cdm.getProductModel());
				ordersModel.setPaymentType(paymentType);

				total = total
						+ (cdm.getCartProductQuantity() * Double.parseDouble(cdm.getProductModel().getProductPrice()));

				cdm.setCartId(null);
				cdm.setProductModel(null);

				System.out.println("here");

				db.saveOrder(ordersModel);
				db.deleteCartDetailsByCartId(cdm);

			}

			PrintWriter pw = response.getWriter();

			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();

			
				try {
					jsonObject.put("tranId", tranId);
					jsonObject.put("total", total+"");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				jsonArray.put(jsonObject);
				pw.write(jsonArray.toString());  
			

		} 
		
		//if the action is cartorderdone, then the cart details will be added to the database
		else if (action.equalsIgnoreCase("cartorderdone")) {

			String tranId = request.getParameter("tranId");

			List<OrdersModel> ordersModel = db.getOrderlistByTranId(tranId);

			Iterator<OrdersModel> it = ordersModel.iterator();
			
			while (it.hasNext()) {

				Object type = (Object) it.next();
				OrdersModel cdm = (OrdersModel) type;
				cdm.setPaymentStatus(true);
				db.updateOrder(cdm);
			}

			PrintWriter pw = response.getWriter();
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			String message = "Payment completed successfully";
			try {
				jsonObject.put("mes", message);
				jsonArray.put(jsonObject);
				pw.write(jsonArray.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
	}

}
