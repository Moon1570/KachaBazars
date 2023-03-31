/*
 * This servlet is in charge of the seller, the request, response handling, and URL mapping with the get and post methods.
 * All the common operations for the seller are handled here. such as viewing the seller page.
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

import org.apache.commons.io.IOUtils;

import com.sun.org.apache.bcel.internal.generic.NEW;

import dao.AreaDao;
import dao.DBData;
import model.BidModel;
import model.CustomerModel;
import model.DistrictModel;
import model.DivisionModel;
import model.SellerModel;
import model.UnionModel;
import model.UpazillaModel;

/*
 * This servlet will be handling all the request and response from the url /seller
 */
@MultipartConfig
public class SellerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// DBData object to access the database
	DBData db = new DBData();

	// AreaDao object to access the database
	AreaDao aDao = new AreaDao();

	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
		String action = request.getParameter("action");

		//if the action is view, the request will be forwarded to the sellers.jsp page
		if (action.equals("view")) {
			List<SellerModel> sellerModels = db.getAllSellers();
			request.setAttribute("customers", sellerModels);
			request.setAttribute("page", request.getParameter("page"));
			
			request.getRequestDispatcher("/Sellers.jsp").forward(request, response);
		}

		//if the action is new, the request will be forwarded to the SellerRegistration.jsp page
		else if(action.equals("new")) {
			SellerModel sellerModel = new SellerModel();
			
			request.setAttribute("page", request.getParameter("page"));
			request.setAttribute("sellers", sellerModel);
			request.setAttribute("action", "reg");

			request.getRequestDispatcher("/SellerRegistration.jsp").forward(request, response);
		}

		// if the action is login, the request will be forwarded to the sellers_login.jsp page
		else if (action.equals("login")) {
			SellerModel sellerModel = new SellerModel();

			request.setAttribute("sellers", sellerModel);
			request.setAttribute("action", "login");

			request.getRequestDispatcher("/sellers_login.jsp").forward(request, response);
		}

		//if the action is logout, the session will be invalidated and the request will be forwarded to the sellers_login.jsp page
		else if (action.equals("logout")) {
			session.invalidate();
			request.setAttribute("action", "login");
			request.getRequestDispatcher("/sellers_login.jsp").forward(request, response);
		}
		
		//if the action is delete, the seller will be deleted from the database and the request will be forwarded to the sellers.jsp page
		else if(action.equals("delete")) {
			String page = request.getParameter("page");
			int sid = Integer.parseInt(request.getParameter("sid"));
			
			SellerModel sellerModel = db.getSellerById(sid);
			db.deleteSeller(sellerModel);
			
			request.setAttribute("page", request.getParameter("page"));
			request.getRequestDispatcher("/Sellers.jsp").forward(request, response);
			
		}
	}

	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		System.out.println("action = " + action);

		//if the action is reg, the seller will be registered to the database and the request will be forwarded to the sellers.jsp page
		if (action.equals("reg")) {
			SellerModel sellerModel = new SellerModel();

			String sellerFirstName = request.getParameter("sellerFirstName");
			String sellerLastName = request.getParameter("sellerLastName");
			String sellerNID = request.getParameter("sellerNID");
			String sellerGender = request.getParameter("sellerGender");
			String sellerPhone = request.getParameter("sellerPhone");
			String sellerPassword = request.getParameter("sellerPassword");

			int divId = Integer.parseInt(request.getParameter("sellerDivision"));
			DivisionModel sellerDivision = aDao.getDivisionById(divId);
			
			int disId = Integer.parseInt(request.getParameter("sellerDistrict"));
			DistrictModel districtModel = aDao.getDistrictById(disId);
			
			int upaId = Integer.parseInt(request.getParameter("sellerUpazilla"));
			UpazillaModel upazillaModel = aDao.getUpazillaById(upaId);
			
			int uniId = Integer.parseInt(request.getParameter("sellerUnion"));
			UnionModel unionModel = aDao.getUnionById(uniId);
			
			
			
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
			String sellerDOB = request.getParameter("sellerDOB");

			try {
				sellerModel.setSellerDOB(simpleDateFormat.parse(sellerDOB));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			LocalDateTime now = LocalDateTime.now();
			String time = (dtf.format(now));

			Part part = request.getPart("sellerImage");
			
			InputStream inputStream = null;

			// prints out some information for debugging
			System.out.println(part.getName());
			System.out.println(part.getSize());
			System.out.println(part.getContentType());

			// obtains input stream of the upload file

			
			
			
			inputStream = part.getInputStream();
			byte[] bytes = IOUtils.toByteArray(inputStream);

			sellerModel.setUnionModel(unionModel);
			sellerModel.setUpazillaModel(upazillaModel);
			sellerModel.setDistrictModel(districtModel);
			sellerModel.setDivisionmodel(sellerDivision);
			sellerModel.setImage(bytes);
			sellerModel.setSellerPhone(sellerPhone);
			sellerModel.setSellerFirstName(sellerFirstName);
			sellerModel.setSellerLastName(sellerLastName);
			sellerModel.setSellerNID(sellerNID);
			sellerModel.setSellerGender(sellerGender);
			sellerModel.setSellerPassword(sellerPassword);

			db.saveSeller(sellerModel);
			request.getRequestDispatcher("/Sellers.jsp").forward(request, response);
		}

		//if the action is login, the seller will be logged in to the system and the request will be forwarded to the seller_home.jsp page
		else if (action.equals("login")) {
			String phone = request.getParameter("sellerPhone");
			String password = request.getParameter("sellerPassword");

			SellerModel sellerModel = db.getPasswordByPhone(phone);

			if (sellerModel == null) {
				request.setAttribute("message", "Account id Invalid...");
				request.getRequestDispatcher("/sellers_login.jsp").forward(request, response);
			} else if (phone.equals(sellerModel.getSellerPhone()) && password.equals(sellerModel.getSellerPassword())) {
				HttpSession session = request.getSession();
				
				session.setAttribute("message", "Hi " + sellerModel.getSellerFirstName());
				
				int sid = sellerModel.getSellerId();
				session.setAttribute("sid", sid);

				request.getRequestDispatcher("/seller_home.jsp").forward(request, response);
			}
			else {
				request.setAttribute("message", "Account id or wrong password...");
				request.getRequestDispatcher("/sellers_login.jsp").forward(request, response);
			}
		}

	}

	private String getImageFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		System.out.println("content-disposition header= " + contentDisp);
		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf("=") + 2, token.length() - 1);
			}
		}
		return "";
	}

}
