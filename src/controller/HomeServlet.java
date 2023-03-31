
/*
 * This servlet is in charge of the Home, the request, response handling, and URL mapping with the get and post methods.
 * All the common operations for the Home are handled here. such as viewing the home page.
 */
package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CustomerModel;
import model.ProductModel;


/*
 * Handles all the requests and responses for the "/home*" URL
 */

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String action=request.getParameter("action");
		
		System.out.println("action : " + action);
		int cid = Integer.parseInt(request.getAttribute("cid").toString());
		System.out.println("cid : " +cid);


		//view the home page
		
		if(action.equals("view"))
		{
			ProductModel productModel=new ProductModel();
			request.setAttribute("product", productModel);
			request.setAttribute("action", "view");
			request.setAttribute("cid", cid);
			System.out.println("home : " + cid);
			request.getRequestDispatcher("/Homepage.jsp").forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
