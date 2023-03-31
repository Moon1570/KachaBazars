
/*
 * This servlet is in charge of the Customer, the request, response handling, and URL mapping with the get and post methods. 
 * All the common operations for the Customer are handled here. such as adding, removing, updating, and viewing the customer. 
 */
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.websocket.Session;

import org.apache.commons.io.IOUtils;

import customs.EscapeString;
import dao.AreaDao;
import dao.DBData;
import model.CartDetailsModel;
import model.CartModel;
import model.CategoryModel;
import model.CustomerModel;
import model.DistrictModel;
import model.DivisionModel;
import model.ProductModel;
import model.SellerModel;
import model.UnionModel;
import model.UpazillaModel;


/*
 * Handles all the requests and responses for the "/customer*" URL
 */
@MultipartConfig
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// DBData object to access the database
	DBData db = new DBData();

	// AreaDao object to access the database
	AreaDao aDao = new AreaDao();
	

	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");		
		HttpSession session = request.getSession();
		
		
		String action = request.getParameter("action");


		
		//if the action is reg, the request will be forwarded to the Customers.jsp page
		if (action.equals("view")) {
			List<CustomerModel> customerModels = db.getAllCustomers();
			request.setAttribute("customers", customerModels);
			request.setAttribute("page", request.getParameter("page"));
			request.getRequestDispatcher("/Customers.jsp").forward(request, response);
		}

		//if the action is reg, the request will be forwarded to the CustomerRegistration.jsp page
		else if (action.equals("reg")) {
			CustomerModel customerModel = new CustomerModel();
			
			request.setAttribute("registration", customerModel);
			request.setAttribute("action", "reg");
			
			request.getRequestDispatcher("/CustomerRegistration.jsp").forward(request, response);
		}

		//if the action is login, the request will be forwarded to the CustomerLogin.jsp page
		else if (action.equals("login")) {
			
			CustomerModel customerModel = new CustomerModel();
			request.setAttribute("customer", customerModel);
			request.setAttribute("action", "login");
			String url = request.getParameter("url");
			
			
			
			if (url != null) {
				request.setAttribute("url", request.getParameter("url"));
				request.setAttribute("pid", request.getParameter("pid"));
				request.setAttribute("page", request.getParameter("page"));
			}
			else {
				url = null;
			}

			request.getRequestDispatcher("/CustomerLogin.jsp").forward(request, response);
		}

		//if action is logout, the session will be invalidated and the request will be forwarded to the Homepage.jsp page
		else if (action.equals("logout")) {
		
			String url = request.getParameter("url");
			url = null;
			request.setAttribute("url", url);
			
			session.removeAttribute("cid");
			session.removeAttribute("name");
			
			request.setAttribute("action", "login");
			request.getRequestDispatcher("/Homepage.jsp").forward(request, response);
		} 
	}


	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		//if the action is reg, the customer model will be added to the database and the request will be forwarded to the Homepage.jsp page
		if (action.equals("reg")) {
			CustomerModel customerModel = new CustomerModel();
			
			String customerFirstName = EscapeString.Escape(request.getParameter("customerFirstName"));
			String customerLastName = EscapeString.Escape(request.getParameter("customerLastName"));
			String customerPhone = EscapeString.EscapePassword(request.getParameter("customerPhone"));
			int customerDivisionId = Integer.parseInt(request.getParameter("customerDivision"));
			String customerPassword = EscapeString.EscapePassword(request.getParameter("customerPassword"));
			String customerVillage = EscapeString.Escape(request.getParameter("customerVillage"));
			String customerStreet = EscapeString.Escape(request.getParameter("customerStreet"));
			String customerHoldingNumber = EscapeString.EscapePassword(request.getParameter("customerHoldingNumber"));
			String customerZipcode = EscapeString.EscapePassword(request.getParameter("customerZipcode"));
			
			
			DivisionModel divisionModel = aDao.getDivisionById(customerDivisionId);
			
			//formatting the date
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
			LocalDateTime now = LocalDateTime.now();  
			String time = (dtf.format(now));
			
			Part part = request.getPart("customerImage");
						
			InputStream inputStream = null;

			// prints out some information for debugging
			System.out.println(part.getName());
			System.out.println(part.getSize());
			System.out.println(part.getContentType());

			// obtains input stream of the upload file

			
			
			
			//getting the input stream
			inputStream = part.getInputStream();
			byte[] bytes = IOUtils.toByteArray(inputStream);
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
			String customerDOB = request.getParameter("customerDOB");
			
			try {
				customerModel.setCustomerDOB(simpleDateFormat.parse(customerDOB));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			int divId = Integer.parseInt(request.getParameter("customerDivision"));
			DivisionModel sellerDivision = aDao.getDivisionById(divId);
			
			int disId = Integer.parseInt(request.getParameter("customerDistrict"));
			DistrictModel districtModel = aDao.getDistrictById(disId);
			
			int upaId = Integer.parseInt(request.getParameter("customerUpazilla"));
			UpazillaModel upazillaModel = aDao.getUpazillaById(upaId);
			
			int uniId = Integer.parseInt(request.getParameter("customerUnion"));
			UnionModel unionModel = aDao.getUnionById(uniId);
			
			customerModel.setImage(bytes);
			customerModel.setCustomerStreet(customerStreet);
			customerModel.setCustomerVillage(customerVillage);
			customerModel.setCustomerHoldingNumber(customerHoldingNumber);
			customerModel.setUnionModel(unionModel);
			customerModel.setUpazillaModel(upazillaModel);
			customerModel.setDistrictModel(districtModel);
			customerModel.setDivisionmodel(sellerDivision);
			customerModel.setDivisionmodel(divisionModel);
			customerModel.setCustomerFirstName(customerFirstName);
			customerModel.setCustomerLastName(customerLastName);
			customerModel.setCustomerPhone(customerPhone);
			customerModel.setCustomerPassword(customerPassword);
			
			customerModel.setCustomerZipcode(customerZipcode);
		
			db.saveCustomer(customerModel);
			
			CartModel cartModel = new CartModel();
			CustomerModel customerModel2 = new CustomerModel();
			
			customerModel2 = db.getCustomerPasswordByPhone(customerPhone);
			cartModel.setCustomerModel(customerModel2);
			
			request.setAttribute("cid", customerModel2.getCustomerId());
						
			db.saveCart(cartModel);
			
			int cid = customerModel.getCustomerId();
			System.out.println("line 154 " + cid);
			
			HttpSession session=request.getSession();
			session.setAttribute("message", "login" );
			session.setAttribute("name", customerFirstName);
			session.setAttribute("cid", cid);
			
			request.getRequestDispatcher("/Homepage.jsp").forward(request, response);
		}

		//if the action is login, the customer model will be retrieved from the database and the request will be forwarded to the Homepage.jsp page
		else if (action.equals("login")) {
			CustomerModel customerModel = new CustomerModel();
			
			String customerPhone = request.getParameter("customerPhone");
			String customerPassword = request.getParameter("customerPassword");
			
			customerModel =db.getCustomerPasswordByPhone(customerPhone);
			
			//if the customer model is null, the request will be forwarded to the CustomerLogin.jsp page
			if(customerModel==null)
			{
				request.setAttribute("message", "Account id Invalid...");
				request.setAttribute("action", "login");
				request.getRequestDispatcher("/CustomerLogin.jsp").forward(request, response);
			}
			//if the customer model is not null, the request will be forwarded to the Homepage.jsp page after checking the password and phone number
			else if (customerPhone.equals(customerModel.getCustomerPhone()) && customerPassword.equals(customerModel.getCustomerPassword())) {
				int cid = customerModel.getCustomerId();
				
				HttpSession session=request.getSession();
				session.setAttribute("message", "" );
				session.setAttribute("name", customerModel.getCustomerFirstName());
				session.setAttribute("cid", cid);
				
				
			//	System.out.println("line 195 " + request.getParameter("url") + " pid " + request.getParameter("pid"));
				
			// check current url that the customer is in
				if (request.getParameter("url").isEmpty()) {
					
					request.getRequestDispatcher("/Homepage.jsp").forward(request, response);
					
				//	request.getRequestDispatcher(url).forward(request, response);
				}
				// if the customer is in the product page, the customer will be redirected to the product page after login
				else {
					int pid = Integer.parseInt(request.getParameter("pid"));
					String url = request.getParameter("url");
					String page = request.getParameter("page");
					url = request.getContextPath () + "/orders?action="+page+"&pid="+pid;
					
					
					System.out.println("line 226 " +url);
					
					response.sendRedirect(url);
				}
			}
			//if the customer model is not null, the request will be forwarded to the CustomerLogin.jsp page after checking the password and phone number
			else {
				request.setAttribute("message", "Account id or wrong password...");
				request.setAttribute("action", "login");
				request.getRequestDispatcher("/CustomerLogin.jsp").forward(request, response);
			}
		}
	}

}
