
/*
 * This servlet is in charge of the image getting for other servlets, the request, response handling, and URL mapping with the get and post methods.
 * All the common operations for the image getting are handled here. 
 */
package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AreaDao;
import dao.DBData;
import model.DivisionModel;
import model.ProductModel;
import model.SellersProduct;



/*
 * This servlet is in charge of the Explore, the request, response handling, and URL mapping with the get and post methods.
 */
public class ExploreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// DBData object to access the database
	DBData db = new DBData();

	// AreaDao object to access the database
	AreaDao ad = new AreaDao();


	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		//if the action is null, the request will be forwarded to the explore.jsp page
		if (action == null) {
			System.out.println("No action GIVEN");
		}

		//if the action is union, the request will be forwarded to the explore.jsp page
		
		else if (action.equals("union")) {

			int uniId = Integer.parseInt(request.getParameter("uid"));

			List<SellersProduct> unionSellersProducts = db.getProductByUnion(uniId);
			request.setAttribute("productList", unionSellersProducts);
			request.setAttribute("name", "Union Products");

			request.getRequestDispatcher("/explore.jsp").forward(request, response);
		} 
		
		//if the action is upazilla, the request will be forwarded to the explore.jsp page
		else if (action.equals("upazilla")) {

			int upaId = Integer.parseInt(request.getParameter("upaId"));

			List<SellersProduct> upazillaSellersProducts = db.getProductByUpazilla(upaId);
			request.setAttribute("productList", upazillaSellersProducts);
			request.setAttribute("name", "Upazilla Products");

			request.getRequestDispatcher("/explore.jsp").forward(request, response);
		} 
		
		//if the action is district, the request will be forwarded to the explore.jsp page
		else if (action.equals("district")) {

			int disId = Integer.parseInt(request.getParameter("disId"));

			List<SellersProduct> districtSellersProducts = db.getProductByDistrict(disId);
			request.setAttribute("productList", districtSellersProducts);
			request.setAttribute("name", "District Products");

			request.getRequestDispatcher("/explore.jsp").forward(request, response);
		} 
		
		//if the action is division, the request will be forwarded to the explore.jsp page
		else if (action.equals("division")) {

			int divId = Integer.parseInt(request.getParameter("divId"));

			List<SellersProduct> divisionSellersProducts = db.getProductByDivision(divId);
			request.setAttribute("productList", divisionSellersProducts);
			request.setAttribute("name", "Division Products");

			request.getRequestDispatcher("/explore.jsp").forward(request, response);
		} 
		
		//if the action is category, the request will be forwarded to the explore.jsp page
		else if (action.equalsIgnoreCase("category")) {

			String catName = request.getParameter("catName");
			List<ProductModel> productList1 = db.getProductByCategoryName(catName);
			List<SellersProduct> productList = db.getSellerProductByCategoryName(catName);
			
			request.setAttribute("productList", productList);
			request.setAttribute("productList1", productList1);
			request.setAttribute("name", "All "+ catName);

			request.getRequestDispatcher("/explore.jsp").forward(request, response);

			System.out.println(" productList = " + productList);

		} 
		
		//if the action is divisionside, the request will be forwarded to the explorearea.jsp page
		else if (action.equalsIgnoreCase("divisionside")) {
			
			int divId = Integer.parseInt(request.getParameter("divId"));
			DivisionModel divisionModel = ad.getDivisionById(divId);
			
			List<SellersProduct> productModels = db.getProductByDivision(divId);
			
			request.setAttribute("productList", productModels);
			request.setAttribute("name", "In "+ divisionModel.getDivisionName());
			request.getRequestDispatcher("/explorearea.jsp").forward(request, response);
			
		} 
		
		//if the action is browseall, the request will be forwarded to the explore.jsp page
		else if (action.equalsIgnoreCase("browseall")) {
			
			List<ProductModel>productModels = db.getAllProducts();
			List<SellersProduct> products = db.getAllSellerProductrs();
			request.setAttribute("productList", products);
			request.setAttribute("productList1", productModels);
			request.setAttribute("name", "All Products");

			request.getRequestDispatcher("/explore.jsp").forward(request, response);

		}

	}

	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
