
/*
 * This servlet is in charge of the DropDown, the request, response handling, and URL mapping with the get and post methods. 
 * All the common operations for the DropDown are handled here. such as adding, removing, updating, and viewing the DropDown elements.
 */
package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.persistence.Convert;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.DropDownDAO;
import model.DistrictModel;
import model.UpazillaModel;


/*
 * Handles all the requests and responses for the "/dropdown*" URL
 */

public class DropDownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// DBData object to access the database
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		System.out.println("Hello World...");
		RequestDispatcher rd = null;
		int divisionsId = 0;

		// DBData object to access the database
		DropDownDAO db = new DropDownDAO();
		List<DistrictModel> listOfDistrictsModel = null;
		List<UpazillaModel> listOfUpazillasModel = null;
		String action = request.getParameter("action");


		// If the action is divisions, then get all divisions from the request and write the response
		if (action.equals("divisions")) {
			divisionsId = Integer.parseInt(request.getParameter("divisionsId"));

			Map<String, String> options = db.getAllDistrictsByDivisionsMap(divisionsId);

			
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();

			String json = new Gson().toJson(options);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

		}


		// If the action is districts, then get all districts from the request and write the response
		else if (action.equals("districts")) {

			int districtsId = Integer.parseInt(request.getParameter("districtId"));
			/*
			 * System.out.println("Hello From AJAX: "+divisionsId);
			 * listOfDistrictsModel=db.getAllDistrictsByDivisions(divisionsId);
			 * request.setAttribute("divisionsId", divisionsId);
			 * request.setAttribute("listOfDistrictsModel", listOfDistrictsModel);
			 * rd=request.getRequestDispatcher("testdropdown.jsp");
			 */
			Map<String, String> options = db.getAllUpazillasByDistrictsMap(districtsId);
			// Map<String, String> options = optionDAO.getAllDeliveryPersonId(id);

//				   Map<String, String> options = new HashMap<>();
//				   options.put("1", "Test");
//				   options.put("2", "Two");
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();

			String json = new Gson().toJson(options);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			/*
			 * //divisionsId=Integer.parseInt(request.getParameter("divisionsId")); Integer
			 * districtsId=Integer.parseInt(request.getParameter("districtsId"));
			 * listOfUpazillasModel=db.getAllUpazillasByDistricts(districtsId);
			 * request.setAttribute("divisionsId", divisionsId);
			 * request.setAttribute("districtsId", districtsId);
			 * request.setAttribute("listOfUpazillasModel", listOfUpazillasModel);
			 * 
			 * System.out.println("List of Upazilla: "+listOfUpazillasModel);
			 * rd=request.getRequestDispatcher("testdropdown.jsp");
			 */
		}


		// If the action is upazillas, then get all upazillas from the request and write the response

		else if (action.equals("upazillas")) {

			int upazillasId = Integer.parseInt(request.getParameter("upazillasId"));

			Map<String, String> options = db.getAllUnionsByUpazillasMap(upazillasId);

			Gson gson = new GsonBuilder().disableHtmlEscaping().create();

			String json = new Gson().toJson(options);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

		}

		// If the action is unions, then get all unions from the request and write the response
		else if (action.equalsIgnoreCase("unions")) {
			int upazillasId = Integer.parseInt(request.getParameter("upazillasId"));

			Map<String, String> options = db.getAllUnionsByUpazillasMap(upazillasId);

			Gson gson = new GsonBuilder().disableHtmlEscaping().create();

			String json = new Gson().toJson(options);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}

		// If the action is delivery, then get all delivery from the request and write the response
		else if (action.equals("delivery")) {
			int unionId = Integer.parseInt(request.getParameter("unionId"));

			Map<String, String> options = db.getAllDeliveryManByUnionIdMap(unionId);

			Gson gson = new GsonBuilder().disableHtmlEscaping().create();

			String json = new Gson().toJson(options);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}

	}

	
	// Post method
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
