/*
 *  This servlet is in charge of the Cart, the request, response handling, and URL mapping with the get and post methods. 
 * All the common operations for the Cart are handled here. such as adding, removing, updating, and viewing the cart. 
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

import dao.DBData;
import model.CartDetailsModel;
import model.CartModel;


/*
 * Handles all the requests and responses for the "/cart*" URL
 */
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// DBData object to access the database
	DBData db = new DBData();
	
	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		int cid = Integer.parseInt(request.getParameter("cid"));
		
		// If the action is view, the request will be forwarded to the Cart.jsp page
		if (action.equals("view")) {
			CartModel cartModel = new CartModel();
			cartModel = db.getCartByCustomerId(cid);
			double total = 0;
			int cartId = cartModel.getCartId();
			List<CartDetailsModel> cartDetailsModel = db.getCartDetailsByCartId(cartModel.getCartId());
			
			request.setAttribute("totalprice", total);
			request.setAttribute("cartDetails", cartDetailsModel);
			request.setAttribute("cid", cid);
			request.setAttribute("cartId", cartId);
			request.setAttribute("action", "view");
			
			request.getRequestDispatcher("/Cart.jsp").forward(request, response);
		}
	}


	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
