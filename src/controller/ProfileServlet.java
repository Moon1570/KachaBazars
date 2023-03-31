/*
 * This servlet is in charge of the profile, the request, response handling, and URL mapping with the get and post methods.
 * All the common operations for the profile are handled here. such as viewing the profile page.
 */
package controller;

import java.awt.color.ProfileDataException;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DBData;
import dao.ProfileDao;
import model.CustomerModel;
import model.OrderSellerProductModel;
import model.OrdersModel;


/*
 * This servlet will be handling all the request and response from the url /profile
 */
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	HttpSession session;
	ProfileDao pd = new ProfileDao();
    DBData db = new DBData();
	

	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		session = request.getSession();
		String action = request.getParameter("action");
		if(action.equals("customerprofile"))
		{
			if (session.getAttribute("cid") != null) {
				int cid = Integer.parseInt(session.getAttribute("cid").toString());
				CustomerModel customerModel = db.getCustomerById(cid);
				
				List<OrdersModel> ordersModels = pd.getOrdersByCID(cid);
				List<OrderSellerProductModel> orderSellerProductModels = pd.getOrderSellerProducts(cid);
				
				request.setAttribute("ordersseller", orderSellerProductModels);
				request.setAttribute("customer", customerModel);
				request.setAttribute("orders", ordersModels);
				request.getRequestDispatcher("profile.jsp").forward(request, response);
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
