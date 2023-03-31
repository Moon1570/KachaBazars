/*
 * This servlet is in charge of the DeliveryPerson, the request, response handling, and URL mapping with the get and post methods. 
 * All the common operations for the delivery person are handled here. such as adding, removing, updating, and viewing the delivery person.
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import customs.EscapeString;
import dao.DBData;
import dao.DeliveryDao;
import model.DeliveryPersonModel;
import model.OrderSellerProductModel;
import model.OrdersModel;

/*
 * Handles all the requests and responses for the "/deliveryperson*" URL
 */
public class DeliveryPersonOperationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// DBData object to access the database
	DeliveryDao dd = new DeliveryDao();

	// DoGet method to handle the get requests
	DBData db = new DBData();


	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		//if the action is inventoryorderview, the request will be forwarded to the orderdetails.jsp page
		if (action.equalsIgnoreCase("inventoryorderview")) {

			int oid = Integer.parseInt(request.getParameter("oid"));

			OrdersModel ordersModel = db.getOrderById(oid);

			double amount = ordersModel.getOrderQuantity()
					* Double.parseDouble(ordersModel.getProductModel().getProductPrice());

			String paymentStatus = "";

			if (ordersModel.isPaymentStatus()) {
				request.setAttribute("payment", "done");
			} else {
				request.setAttribute("payment", "pending");
			}

			request.setAttribute("amount", amount);
			request.setAttribute("orderdetails", ordersModel);

			request.getRequestDispatcher("/dm-order-details.jsp").forward(request, response);
		} 

		// if action is viewsellerorder, the request will be forwarded to the /dm-seller-order-details.jsp page
		else if (action.equalsIgnoreCase("viewsellerorder")) {
			int soid = Integer.parseInt(request.getParameter("soid"));

			OrderSellerProductModel orderSellerProductModel = db.getOrderSellerProductById(soid);

			double amount = orderSellerProductModel.getOrderQuantity()
					* orderSellerProductModel.getSellersProduct().getProductPrice();

			String paymentStatus = "";

			if (orderSellerProductModel.isPaymentStatus()) {
				request.setAttribute("payment", "done");
			} else {
				request.setAttribute("payment", "pending");
			}

			request.setAttribute("amount", amount);
			request.setAttribute("orderdetails", orderSellerProductModel);

			request.getRequestDispatcher("/dm-seller-order-details.jsp").forward(request, response);
		}
	}

	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		//if the action is dlogin, the request will be forwarded to the devilery-person-login.jsp page
		if (action.equalsIgnoreCase("dlogin")) {

			String phone = EscapeString.EscapePassword(request.getParameter("deliveryPersonPhone"));
			String password = EscapeString.EscapePassword(request.getParameter("deliveryPersonPassword"));

			DeliveryPersonModel deliveryPersonModel = new DeliveryPersonModel();

			deliveryPersonModel = dd.getDeliveryPersonPasswordByPhone(phone);

			// if the delivery person is not found, the request will be forwarded to the devilery-person-login.jsp page
			if (deliveryPersonModel == null) {
				request.setAttribute("message", "Account id Invalid...");
				request.setAttribute("action", "login");
				request.getRequestDispatcher("/devilery-person-login.jsp").forward(request, response);
			}
			
			// if the delivery person is found, check if the password is correct, if it is correct, the request will be forwarded to the delivery-person-homepage.jsp page
			else if (phone.equals(deliveryPersonModel.getDeliveryPersonPhone())
					&& password.equals(deliveryPersonModel.getDeliveryPersonPassword())) {
				int did = deliveryPersonModel.getDeliveryPersonId();
				HttpSession session = request.getSession();
				session.setAttribute("message", "login");
				session.setAttribute("name", deliveryPersonModel.getDeliveryPersonFirstName());
				session.setAttribute("did", did);

				request.getRequestDispatcher("/delivery-person-homepage.jsp").forward(request, response);

			}

			// if the password is wrong, the request will be forwarded to the devilery-person-login.jsp page
			else {
				request.setAttribute("message", "Account id or wrong password...");
				request.setAttribute("action", "login");
				request.getRequestDispatcher("/devilery-person-login.jsp").forward(request, response);
			}

		} 
		
		//if the action is update, the request will be forwarded to the delivery-person-homepage.jsp page
		else if (action.equalsIgnoreCase("update")) {

			int oid = Integer.parseInt(request.getParameter("oid"));

			OrdersModel ordersModel = db.getOrderById(oid);
			String orderStatus = request.getParameter("orderStatus");

			System.out.println(orderStatus);

			ordersModel.setOrderStatus(orderStatus);
			db.updateOrder(ordersModel);

			request.getRequestDispatcher("/delivery-person-homepage.jsp").forward(request, response);

		}

		//if the action is updatesellerorder, the request will be forwarded to the delivery-person-homepage.jsp page
		else if (action.equalsIgnoreCase("updatesellerorder")) {
			int soid = Integer.parseInt(request.getParameter("soid"));

			OrderSellerProductModel orderSellerProductModel = db.getOrderSellerProductById(soid);
			String orderStatus = request.getParameter("orderStatus");

			System.out.println(orderStatus);

			orderSellerProductModel.setOrderStatus(orderStatus);
			db.updateOrderSellerProduct(orderSellerProductModel);

			request.getRequestDispatcher("/delivery-person-homepage.jsp").forward(request, response);
		}

	}

}
