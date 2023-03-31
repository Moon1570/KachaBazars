/*
 * This servlet is in charge of the browsing products, the request, response handling, and URL mapping with the get and post methods. 
 * It will contain all the common operations for the browsing products.
 */

package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;

import dao.DBData;
import model.CustomerModel;


/*
 * Handles all the requests and responses for the "/browse*" URL
 */
public class BrowseServlet extends HttpServlet {
	
	
	HttpSession session;
	RequestDispatcher rd = null;

	private static final long serialVersionUID = 1L;

	// DBData object to access the database
	DBData db = new DBData();
	
	
	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		if (action.equals("ourshop")) {
			session = request.getSession();
			if (session.getAttribute("cid") != null) {
				int cid = Integer.parseInt(session.getAttribute("cid").toString());
				CustomerModel customerModel = db.getCustomerById(cid);
				request.setAttribute("customer", customerModel);
			}
			
			
			rd = request.getRequestDispatcher("/browse.jsp");
		}
	rd.forward(request, response);
			
	}


	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
