/*
 * This servlet is in charge of the admin, the request, response handling, and URL mapping with the get and post methods. 
 * It controls Login, Validation, Authentication and profile management for the admin. 
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
import model.AdminModel;
import model.CustomerModel;


/*
 * Handles all the requests and responses for the "/admin*" URL
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	// DBData object to access the database
	DBData db = new DBData();
	
	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
		String action = request.getParameter("action");
		
		// If the action is adminlogin, then the admin is redirected to the admin-login.jsp page
		if (action.equals("adminlogout")) {
			
			session.removeAttribute("adminlogin");
			session.removeAttribute("admin");
						
			request.getRequestDispatcher("admin-login.jsp").forward(request, response);
			
		}

		// If the action is viewprofile, then the admin is redirected to the admininfo.jsp page
		else if (action.equalsIgnoreCase("viewprofile")) {
			
			if (session.getAttribute("admin") != null) {
				
				AdminModel adminModel = (AdminModel) session.getAttribute("admin");
								
				request.setAttribute("admin", adminModel);
				
				request.getRequestDispatcher("/admininfo.jsp").forward(request, response);
			}
			else {
				request.getRequestDispatcher("/admin-login.jsp").forward(request, response);
			}
			
		}
	}

	
	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		
		// If the action is adminlogin, then the admin is authenticated and redirected to the index.jsp page
		if (action.equalsIgnoreCase("adminlogin")) {
			
			String name, password;
			
			name = EscapeString.Escape(request.getParameter("adminName"));
			password = request.getParameter("adminPassword");
			
			AdminModel adminModel =db.getAdminPasswordByName(name);
			
			// If the adminModel is null, then the admin is redirected to the admin-login.jsp page with an error message
			if(adminModel==null)
			{
				request.setAttribute("message", "Account id Invalid...");
				request.setAttribute("action", "login");
				request.getRequestDispatcher("/admin-login.jsp").forward(request, response);
			}

			// If the adminModel is not null, then the admin is authenticated and redirected to the index.jsp page
			else if (name.equalsIgnoreCase(adminModel.getAdminName()) && password.equalsIgnoreCase(adminModel.getAdminPassword())) {
				int aid = adminModel.getAdminId();
				HttpSession session=request.getSession();
				session.setAttribute("name", adminModel.getAdminName());
				session.setAttribute("admin", adminModel);
				session.setAttribute("aid", aid);
				session.setAttribute("adminlogin", "true");
				
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			
			}
			// If the adminModel is not null, then the admin is redirected to the admin-login.jsp page with an error message
			else {
				request.setAttribute("message", "Account id or wrong password...");
				request.setAttribute("action", "login");
				request.getRequestDispatcher("/admin-login.jsp").forward(request, response);
			}
			
		}
		
	}

}
