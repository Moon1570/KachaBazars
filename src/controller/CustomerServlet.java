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
import model.CartModel;
import model.CategoryModel;
import model.CustomerModel;
import model.DistrictModel;
import model.DivisionModel;
import model.ProductModel;
import model.SellerModel;
import model.UnionModel;
import model.UpazillaModel;

@MultipartConfig
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DBData db = new DBData();
	AreaDao aDao = new AreaDao();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = null;
		
		HttpSession session = request.getSession();
		
		
		String action = request.getParameter("action");


		
		System.out.println(session.getAttribute("cid"));
		if (action.equals("view")) {
			List<CustomerModel> customerModels = db.getAllCustomers();
			request.setAttribute("customers", customerModels);
			rd = request.getRequestDispatcher("/Customers.jsp");
		}
		else if (action.equals("reg")) {
			CustomerModel customerModel = new CustomerModel();
			
			request.setAttribute("registration", customerModel);
			request.setAttribute("action", "reg");
			
			rd = request.getRequestDispatcher("/CustomerRegistration.jsp");
		}
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

			rd = request.getRequestDispatcher("/CustomerLogin.jsp");
		}
		else if (action.equals("logout")) {
		
			String url = request.getParameter("url");
			url = null;
			request.setAttribute("url", url);
			
			session.removeAttribute("cid");
			session.removeAttribute("name");
			
			request.setAttribute("action", "login");
			rd = request.getRequestDispatcher("/Homepage.jsp");
		}
		rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = null;
		String action = request.getParameter("action");
		
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
		else if (action.equals("login")) {
			CustomerModel customerModel = new CustomerModel();
			
			String customerPhone = EscapeString.EscapePassword(request.getParameter("customerPhone"));
			String customerPassword = EscapeString.EscapePassword(request.getParameter("customerPassword"));
			
			customerModel =db.getCustomerPasswordByPhone(customerPhone);
			
			
			if(customerModel==null)
			{
				request.setAttribute("message", "Account id Invalid...");
				request.setAttribute("action", "login");
				request.getRequestDispatcher("/CustomerLogin.jsp").forward(request, response);
			}
			else if (customerPhone.equals(customerModel.getCustomerPhone()) && customerPassword.equals(customerModel.getCustomerPassword())) {
				int cid = customerModel.getCustomerId();
				
				HttpSession session=request.getSession();
				session.setAttribute("message", "" );
				session.setAttribute("name", customerModel.getCustomerFirstName());
				session.setAttribute("cid", cid);
				
				
			//	System.out.println("line 195 " + request.getParameter("url") + " pid " + request.getParameter("pid"));
				
				if (request.getParameter("url").isEmpty()) {
					
					request.getRequestDispatcher("/Homepage.jsp").forward(request, response);
					
				//	request.getRequestDispatcher(url).forward(request, response);
				}
				else {
					int pid = Integer.parseInt(request.getParameter("pid"));
					String url = request.getParameter("url");
					String page = request.getParameter("page");
					url = request.getContextPath () + "/orders?action="+page+"&pid="+pid;
					
					
					System.out.println("line 226 " +url);
					
					response.sendRedirect(url);
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
