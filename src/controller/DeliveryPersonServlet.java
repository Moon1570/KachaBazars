/*
 * This servlet is in charge of the DeliveryPerson, the request, response handling, and URL mapping with the get and post methods. 
 * All the common operations for the delivery person are handled here. such as adding, removing, updating, and viewing the delivery person.
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
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import customs.EscapeString;
import dao.AreaDao;
import dao.DBData;
import model.DeliveryPersonModel;
import model.DivisionModel;
import model.SellerModel;


/*
 * Handles all the requests and responses for the "/delivery_person*" URL
 */
@MultipartConfig
public class DeliveryPersonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// DBData object to access the database
	DBData db = new DBData();


	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		//if the action is view, the request will be forwarded to the delivery_person.jsp page
		if (action.equals("view")) {
			List<DeliveryPersonModel> deliveryPersonModels = db.getAllDeliveryPersons();
			request.setAttribute("delivery", deliveryPersonModels);
			request.setAttribute("page", request.getParameter("page"));
			request.getRequestDispatcher("/delivery_person.jsp").forward(request, response);
		} 
		
		//if the action is add, the request will be forwarded to the new_delivery_person.jsp page
		else if (action.equals("add")) {

			request.setAttribute("action", "add");
			request.setAttribute("page", request.getParameter("page"));
			request.getRequestDispatcher("/new_delivery_person.jsp").forward(request, response);
			
		}
		
		//if the action is update, the request will be forwarded to the new_delivery_person.jsp page
		else if (action.equals("update")) {
			int dpid = Integer.parseInt(request.getParameter("dpid"));
			DeliveryPersonModel deliveryPersonModel = db.getDeliveryPersonById(dpid);
			
			request.setAttribute("page", request.getParameter("page"));
			request.setAttribute("delivery", deliveryPersonModel);
			request.getRequestDispatcher("/new_delivery_person.jsp").forward(request, response);
		}
		
		//if the action is delete, the request will be forwarded to the delivery_person.jsp page
		
		else if (action.equals("delete")) {
			int dpid = Integer.parseInt(request.getParameter("dpid"));
			DeliveryPersonModel deliveryPersonModel = db.getDeliveryPersonById(dpid);
			request.setAttribute("page", request.getParameter("page"));
			db.deleteDeliveryPerson(deliveryPersonModel);
			request.getRequestDispatcher("/delivery_person.jsp").forward(request, response);
		}
	}


	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		AreaDao ad = new AreaDao();
		DBData db = new DBData();


		//if the action is add, the request will be forwarded to the delivery_person.jsp page with the new delivery person added
		if (action.equals("add")) {

			DeliveryPersonModel deliveryPersonModel = new DeliveryPersonModel();

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
			String deliveryPersonDOB = request.getParameter("deliveryPersonDOB");
			
			try {
				deliveryPersonModel.setDeliveryPersonDOB(simpleDateFormat.parse(deliveryPersonDOB));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			
			deliveryPersonModel
					.setDeliveryPersonFirstName(EscapeString.Escape(request.getParameter("deliveryPersonFirstName")));
			deliveryPersonModel
					.setDeliveryPersonLastName(EscapeString.Escape(request.getParameter("deliveryPersonLastName")));
			deliveryPersonModel
					.setDeliveryPersonPhone(EscapeString.EscapePassword(request.getParameter("deliveryPersonPhone")));
			deliveryPersonModel.setDeliveryPersonNID(EscapeString.EscapePassword(request.getParameter("deliveryPersonNID")));
			deliveryPersonModel
					.setDeliveryPersonGender(EscapeString.Escape(request.getParameter("deliveryPersonGender")));
			deliveryPersonModel.setDeliveryVillage(request.getParameter("deliveryVillage"));
			deliveryPersonModel.setDelieryStreet(request.getParameter("deliveryStreet"));
			deliveryPersonModel
					.setDeliveryHoldingNumber(request.getParameter("deliveryHoldingNumber"));
			deliveryPersonModel.setDeliveryPersonPhone(EscapeString.EscapePassword(request.getParameter("deliveryPersonPhone")));
			deliveryPersonModel.setDeliveryPersonPassword(EscapeString.EscapePassword(request.getParameter("deliveryPassword")));

			deliveryPersonModel
					.setDivisionmodel(ad.getDivisionById(Integer.parseInt(request.getParameter("deliveryDivision"))));
			deliveryPersonModel
					.setDistrictModel(ad.getDistrictById(Integer.parseInt(request.getParameter("deliveryDistrict"))));
			deliveryPersonModel
					.setUpazillaModel(ad.getUpazillaById(Integer.parseInt(request.getParameter("deliveryUpazilla"))));
			deliveryPersonModel.setUnionModel(ad.getUnionById(Integer.parseInt(request.getParameter("deliveryUnion"))));

			Part part = request.getPart("deliveryImage");

			InputStream inputStream = null;
			// prints out some information for debugging
			System.out.println(part.getName());
			System.out.println(part.getSize());
			System.out.println(part.getContentType());

			// obtains input stream of the upload file

			inputStream = part.getInputStream();
			byte[] bytes = IOUtils.toByteArray(inputStream);
			
			deliveryPersonModel.setImage(bytes);
			
			db.saveDeliveryPerson(deliveryPersonModel);			
			request.getRequestDispatcher("/delivery_person.jsp").forward(request, response);
			
			
		} 
		
		//if the action is update, the request will be forwarded to the delivery_person.jsp page with the updated delivery person
		else if (action.equals("update")) {
			
			int dpid = Integer.parseInt(request.getParameter("dpid"));
			DeliveryPersonModel deliveryPersonModel = db.getDeliveryPersonById(dpid);
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
			String deliveryPersonDOB = request.getParameter("deliveryPersonDOB");
			
			try {
				deliveryPersonModel.setDeliveryPersonDOB(simpleDateFormat.parse(deliveryPersonDOB));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			
			deliveryPersonModel
					.setDeliveryPersonFirstName(EscapeString.Escape(request.getParameter("deliveryPersonFirstName")));
			deliveryPersonModel
					.setDeliveryPersonLastName(EscapeString.Escape(request.getParameter("deliveryPersonLastName")));
			deliveryPersonModel
					.setDeliveryPersonPhone(EscapeString.EscapePassword(request.getParameter("deliveryPersonPhone")));
			deliveryPersonModel.setDeliveryPersonNID(EscapeString.EscapePassword(request.getParameter("deliveryPersonNID")));
			deliveryPersonModel
					.setDeliveryPersonGender(EscapeString.Escape(request.getParameter("deliveryPersonGender")));
			deliveryPersonModel.setDeliveryVillage(EscapeString.Escape(request.getParameter("deliveryVillage")));
			deliveryPersonModel.setDelieryStreet(EscapeString.Escape(request.getParameter("deliveryStreet")));
			deliveryPersonModel.setDeliveryPersonPhone(EscapeString.EscapePassword(request.getParameter("deliveryPersonPhone")));
			deliveryPersonModel.setDeliveryPersonPassword(EscapeString.EscapePassword(request.getParameter("deliveryPassword")));
			
			deliveryPersonModel
					.setDeliveryHoldingNumber(EscapeString.EscapePassword(request.getParameter("deliveryHoldingNumber")));

			deliveryPersonModel
					.setDivisionmodel(ad.getDivisionById(Integer.parseInt(request.getParameter("deliveryDivision"))));
			deliveryPersonModel
					.setDistrictModel(ad.getDistrictById(Integer.parseInt(request.getParameter("deliveryDistrict"))));
			deliveryPersonModel
					.setUpazillaModel(ad.getUpazillaById(Integer.parseInt(request.getParameter("deliveryUpazilla"))));
			deliveryPersonModel.setUnionModel(ad.getUnionById(Integer.parseInt(request.getParameter("deliveryUnion"))));

			Part part = request.getPart("deliveryImage");

			InputStream inputStream = null;
			// prints out some information for debugging
			System.out.println(part.getName());
			System.out.println(part.getSize());
			System.out.println(part.getContentType());

			// obtains input stream of the upload file

			inputStream = part.getInputStream();
			byte[] bytes = IOUtils.toByteArray(inputStream);
			
			deliveryPersonModel.setImage(bytes);
			
			db.updateDeliveryPerson(deliveryPersonModel);
			request.getRequestDispatcher("/delivery_person.jsp").forward(request, response);
			
		}

	}

	//this method will return the file name of the image
	private String getImageFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf("=") + 2, token.length() - 1);
			}
		}
		return "";
	}

}
