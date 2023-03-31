
/*
 * This servlet is in charge of the RestDelivery, the request, response handling, and URL mapping with the get and post methods.
 * This is to handle the REST API calls for the android app.
 * All the common operations for the RestDelivery are handled here. such as viewing the RestDelivery page.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ietf.jgss.Oid;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.DBData;
import dao.OrderDao;
import model.DeliveryPersonModel;
import model.OrderSellerProductModel;
import model.OrdersModel;


/*
 * This servlet will be handling all the request and response from the url /restdelivery
 */
public class RestDeliveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	
	
	// OrderDao a object to access the database
	OrderDao od = new OrderDao();

	// DBData object to access the database
	DBData db = new DBData();
   
	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		
		// if the action is null, then no action is given
		if (action == null) {
			System.out.println("No action is GIVEN");
		}

		// if the action is getorders, then the orders will be returned in JSON format to the android app REST client
		else if (action.equals("getorders")) {
			double qty = 0;
			
			int delId = Integer.parseInt(request.getParameter("delId"));
			
			ArrayList<OrdersModel> ordersModels = od.getNewOrdersForDeliveryManByDelId(delId);
			JSONArray jsonArray = new JSONArray();
		
			
			Iterator<OrdersModel> it = ordersModels.iterator();
			
			while (it.hasNext()) {
				Object type = (Object) it.next();

				OrdersModel sub =  (OrdersModel) type;
				
				JSONObject JO = new JSONObject();

				qty = sub.getOrderQuantity() * Double.parseDouble(sub.getProductModel().getProductPrice());
				
				try {
					JO.put("orderId", sub.getOrderId());
					JO.put("name", sub.getCareOfContact());
					JO.put("customer", sub.getCustomerModel().getCustomerId());
					JO.put("customerName", sub.getCustomerModel().getCustomerFirstName() + " " + sub.getCustomerModel().getCustomerLastName());
					JO.put("date", sub.getExpectedDeliveryDate());
					JO.put("quantity", sub.getOrderQuantity());
					JO.put("status", sub.getOrderStatus());
					JO.put("orderVillage", sub.getOrderVillage());
					JO.put("orderSteet", sub.getOrderStreet());
					JO.put("productName", sub.getProductModel().getProductName());
					JO.put("productId", sub.getProductModel().getProductId());
					JO.put("orderPhone", sub.getPhoneNumber());
					JO.put("division", sub.getDivisionModel().getDivisionName());
					JO.put("district", sub.getDistrictModel().getDistrictName());
					JO.put("upazilla", sub.getUpazillaModel().getUpazillaName());
					JO.put("union", sub.getUnionModel().getUnionBanglaName());
					JO.put("orderingDate", sub.getOrderDate());
					
					 
					if(sub.isPaymentStatus()) {
						JO.put("payment", "complete");
					}
					else {
						JO.put("payment", "due " + qty + " tk");
					}
					
					jsonArray.put(JO);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			
			PrintWriter printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
			
						
		}

		// if the action is markAsComplete, then the order will be marked as complete in the database
		else if (action.equals("markAsComplete")) {
			

			int oid = Integer.parseInt(request.getParameter("orderId"));
			OrdersModel ordersModel = db.getOrderById(oid);
			ordersModel.setOrderStatus("completed");
			ordersModel.setOrderId(oid);
			
			db.updateOrder(ordersModel);
			
			JSONArray jsonArray = new JSONArray();
			PrintWriter pw = response.getWriter();
			
			pw.write(jsonArray.toString());
		}

		//if action is getsellerorders, then the seller orders will be returned in JSON format to the android app REST client
		else if (action.equals("getsellerorders")) {
			double qty = 0;
			
			int delId = Integer.parseInt(request.getParameter("delId"));
			
			ArrayList<OrderSellerProductModel> ordersModels = od.getNewOrdersFromSellerForDeliveryManByDelId(delId);
			JSONArray jsonArray = new JSONArray();
		
			
			Iterator<OrderSellerProductModel> it = ordersModels.iterator();
			
			while (it.hasNext()) {
				Object type = (Object) it.next();

				OrderSellerProductModel sub =  (OrderSellerProductModel) type;
				
				JSONObject JO = new JSONObject();

				qty = sub.getOrderQuantity() * sub.getSellersProduct().getProductPrice();
				
				try {
					JO.put("orderId", sub.getOrderId());
					JO.put("name", sub.getCareOfContact());
					JO.put("customer", sub.getCustomerModel().getCustomerId());
					JO.put("customerName", sub.getCustomerModel().getCustomerFirstName() + " " + sub.getCustomerModel().getCustomerLastName());
					JO.put("date", sub.getExpectedDeliveryDate());
					JO.put("quantity", sub.getOrderQuantity());
					JO.put("status", sub.getOrderStatus());
					JO.put("orderVillage", sub.getOrderVillage());
					JO.put("orderSteet", sub.getOrderStreet());
					JO.put("productName", sub.getSellersProduct().getProductName());
					JO.put("productId", sub.getSellersProduct().getProductId());
					JO.put("orderPhone", sub.getPhoneNumber());
					JO.put("division", sub.getDivisionModel().getDivisionName());
					JO.put("district", sub.getDistrictModel().getDistrictName());
					JO.put("upazilla", sub.getUpazillaModel().getUpazillaName());
					JO.put("union", sub.getUnionModel().getUnionBanglaName());
					JO.put("orderingDate", sub.getOrderDate());
					JO.put("sellerName", sub.getSellerModel().getSellerFirstName()+sub.getSellerModel().getSellerLastName());
					JO.put(("sellerAddress"), sub.getUnionModel().getUnionBanglaName()+", "+sub.getUpazillaModel().getUpazillaBangaName()+", "+sub.getDistrictModel().getDistrictBanglaName()+", "+sub.getDivisionModel().getDivisionBanglaName());
					
					 
					if(sub.isPaymentStatus()) {
						JO.put("payment", "complete");
					}
					else {
						JO.put("payment", "due " + qty + " tk");
					}
					
					jsonArray.put(JO);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			
			PrintWriter printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
		}
		
		// if the action is markAsCompletesellerOrder, then the order will be marked as complete in the database
		else if (action.equals("markAsCompletesellerOrder")) {
			

			int oid = Integer.parseInt(request.getParameter("orderId"));
			OrderSellerProductModel ordersModel = db.getOrderSellerProductById(oid);
			ordersModel.setOrderStatus("completed");
			ordersModel.setOrderId(oid);
			
			db.updateOrderSellerProduct(ordersModel);;
			
			JSONArray jsonArray = new JSONArray();
			PrintWriter pw = response.getWriter();
			
			pw.write(jsonArray.toString());
		}
	}


	// this method is to handle post request
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		
		System.out.println(action);
		
		//if the action is null, then the action is not given
		if (action == null) {
			System.out.println("No action is GIVEN");
		}

		//if the action is delilogin, Deliveryman login will be done after authentication
		else if (action.equals("deliLogin")) {
			
			String phone, pass;
			
			phone = request.getParameter("phone");
			pass = request.getParameter("pass");
			

			DeliveryPersonModel deliveryPersonModel = new DeliveryPersonModel();
			deliveryPersonModel = od.getDeliveryPersonPasswordByPhone(phone);
			
			System.out.println(deliveryPersonModel);
			
			if(deliveryPersonModel==null)
			{
				request.setAttribute("message", "Account id Invalid...");
				request.setAttribute("action", "login");
				request.getRequestDispatcher("/CustomerLogin.jsp").forward(request, response);
			}
			else if (phone.equals(deliveryPersonModel.getDeliveryPersonPhone()) && pass.equals(deliveryPersonModel.getDeliveryPersonPassword())) {
				
				JSONArray jsonArray = new JSONArray();
				JSONObject jsonObject = new JSONObject();
				
				try {
					jsonObject.put("delId", deliveryPersonModel.getDeliveryPersonId());
					jsonObject.put("delName", deliveryPersonModel.getDeliveryPersonFirstName() + " " + deliveryPersonModel.getDeliveryPersonLastName());
					jsonArray.put(jsonObject);
					PrintWriter pw = response.getWriter();
					pw.write(jsonArray.toString());
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			else {
				request.setAttribute("message", "Account id or wrong password...");
				request.setAttribute("action", "login");
				request.getRequestDispatcher("/CustomerLogin.jsp").forward(request, response);
			}
		}
	}
}
