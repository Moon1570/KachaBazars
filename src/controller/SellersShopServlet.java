/*
 * This servlet is in charge of the sellers shop, the request, response handling, and URL mapping with the get and post methods.
 * All the common operations for the sellers shop are handled here. such as adding a new product, updating a product, deleting a product, etc.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

import dao.DBData;
import model.CategoryModel;
import model.ProductModel;
import model.SellerModel;
import model.SellersProduct;
import model.SubcategoryModel;
import model.UnitModel;


/*
 * This servlet will be handling all the request and response from the url /sellers_shop
 */
@MultipartConfig
public class SellersShopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	RequestDispatcher rd = null;
	DBData db = new DBData();


	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action = request.getParameter("action");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// If the action is add, then the user will be redirected to the new_sellers_product.jsp page
		if (action.equals("add")) {
			request.setAttribute("action", "add");
			request.getRequestDispatcher("new_sellers_product.jsp").forward(request, response);
		}
	}

	
	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String action = request.getParameter("action").toString();
		int sid = Integer.parseInt(session.getAttribute("sid").toString());
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		// If the action is submit, then the user will be redirected to the new_sellers_product.jsp page
		if (action.equals("submit")) {
			String productName = request.getParameter("productName");
			String productDescription = request.getParameter("productDescription");
			Part part = request.getPart("productImage");
			String productImageName = getImageFileName(part);
			double productPrice = Double.parseDouble(request.getParameter("productPrice"));
	//		String productCategory = request.getParameter("dropdownProductCategory").toString();
	//		String productSubcategory = request.getParameter("dropdownProductSubcategory").toString();
			double productQuantity = Double.parseDouble(request.getParameter("productQuantity"));
		
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
			LocalDateTime now = LocalDateTime.now();  
			String time = (dtf.format(now));  
			
			InputStream inputStream = null;

			// prints out some information for debugging
			System.out.println(part.getName());
			System.out.println(part.getSize());
			System.out.println(part.getContentType());

			// obtains input stream of the upload file

			
			
			
			inputStream = part.getInputStream();
			byte[] bytes = IOUtils.toByteArray(inputStream);
			
			
			
			CategoryModel categoryModel = new CategoryModel();
			SubcategoryModel subcategoryModel = new SubcategoryModel();
			
			SellerModel sellerModel = db.getSellerById(sid);
			
			int catid;
			catid = Integer.parseInt(request.getParameter("dropdownProductCategory"));
			categoryModel = db.getCategoryById(catid);
			
			int subid;
			subid = Integer.parseInt(request.getParameter("dropdownProductSubcategory"));
			subcategoryModel = db.getSubcategoryById(subid);
			
			int uid;
			uid = Integer.parseInt(request.getParameter("unit").toString());
			UnitModel unitModel = db.getUnitById(uid);
			
			SellersProduct sellersProduct = new SellersProduct();
			
			sellersProduct.setSellerModel(sellerModel);
			sellersProduct.setProductName(productName);
			sellersProduct.setProductDescription(productDescription);
			sellersProduct.setCategoryModel(categoryModel);
			sellersProduct.setSubcategoryModel(subcategoryModel);
			sellersProduct.setImage(bytes);
			sellersProduct.setProductPrice(productPrice);
			sellersProduct.setUnitModel(unitModel);
			sellersProduct.setProductQuantity(productQuantity);
			
			
			db.saveSellerProduct(sellersProduct);
	//		rd = request.getRequestDispatcher("/product.jsp");
		}
	}


	// This method will get the image file name
	private String getImageFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
	}
}
