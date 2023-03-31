/*
 * This servlet is in charge of the confirmation, the request, response handling, and URL mapping with the get and post methods. 
 * It will contain all the common operations for the confirmation. 
 */
package controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import dao.DBData;
import model.CartDetailsModel;
import model.ProductModel;
import model.SellersProduct;


/*
 * Handles all the requests and responses for the "/confirmation*" URL
 */
public class ConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpSession session;
	DBData db = new DBData();

	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		session = request.getSession();
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		// if the action is confirm, then the user will be redirected to the confirmation page
		if (action.equals("confirm")) {
			int pid = Integer.parseInt(request.getParameter("pid").toString());

			// if the user is not logged in, then the user will be redirected to the login page
			if (session.getAttribute("cid") == null) {
				String url = request.getRequestURI();

				String page = "confirm";

				String baseUrl = request.getContextPath() + "/customers?action=login&url=" + url + "&pid=" + pid
						+ "&page=" + page;

				response.sendRedirect(baseUrl);

			}

			// if the user is logged in, then the user will be redirected to the confirmation page
			else if (session.getAttribute("cid") != null) {
				int cid = Integer.parseInt(session.getAttribute("cid").toString());
				request.setAttribute("cid", cid);
				ProductModel productModel = new ProductModel();
				productModel = db.getProductById(pid);
				request.setAttribute("product", productModel);
				request.setAttribute("action", "buy");
				request.getRequestDispatcher("/Confirmation.jsp").forward(request, response);
			}

		} 

		//If the action is checkout, then the user will be redirected to the cart_confirmation.jsp page
		else if (action.equals("checkout")) {

			// if the user is not logged in, then the user will be redirected to the login page
			if (session.getAttribute("cid") != null) {
				int cid = Integer.parseInt(session.getAttribute("cid").toString());
				request.setAttribute("cid", cid);
				int cartId = Integer.parseInt(request.getParameter("cartId"));
				request.setAttribute("cartId", cartId);
				request.getRequestDispatcher("/cart_confirmation.jsp").forward(request, response);
			}
		} 
		//If the action is confirmsellerproduct, then the user will be redirected to the confirm_seller_product.jsp page
		else if (action.equals("confirmsellerproduct")) {
			int pid = Integer.parseInt(request.getParameter("pid").toString());

			// if the user is not logged in, then the user will be redirected to the login page
			if (session.getAttribute("cid") == null) {
				String url = request.getRequestURI();

				String page = "confirmsellerproduct";

				String baseUrl = request.getContextPath() + "/customers?action=login&url=" + url + "&pid=" + pid
						+ "&page=" + page;

				response.sendRedirect(baseUrl);

			}
			// if the user is logged in, then the user will be redirected to the confirmation page
			else if (session.getAttribute("cid") != null) {
				int cid = Integer.parseInt(session.getAttribute("cid").toString());
				request.setAttribute("cid", cid);
				SellersProduct sellersProduct = new SellersProduct();
				sellersProduct = db.getSellerProductById(pid);
				request.setAttribute("product", sellersProduct);
				request.setAttribute("action", "buy");
				System.out.println("here");
				request.getRequestDispatcher("/confirm_seller_product.jsp").forward(request, response);
			}
		}
	}


	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
