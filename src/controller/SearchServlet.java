/*
 * This servlet is in charge of the search, the request, response handling, and URL mapping with the get and post methods.
 * All the common operations for the search are handled here. such as searching for a product.
 */
package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SearchDao;
import model.ProductModel;
import model.SellersProduct;

/*
 * This servlet will be handling all the request and response from the url /search
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	// SearchDao DB object to access the database
	SearchDao sd = new SearchDao();
	
	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		
		//if the action is search, the request will be forwarded to the explore.jsp page
		if (action.equalsIgnoreCase("search")) {
			
			String keyword = request.getParameter("keyword");
			int catId = Integer.parseInt(request.getParameter("searchtable"));
			
			List<ProductModel> productList1 = sd.getProductByKeywordAndCategoryId(keyword, catId);			
			List<SellersProduct> productList = sd.getSellerProductByKeywordAndCategoryId(keyword, catId);
			
			request.setAttribute("productList", productList);
			request.setAttribute("productList1", productList1);
			request.setAttribute("name", "Search result for "+ keyword);

			request.getRequestDispatcher("/explore.jsp").forward(request, response);
			
		}
	}

}
