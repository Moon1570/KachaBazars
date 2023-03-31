
/*
 * This servlet is in charge of the ImageGetter, the request, response handling, and URL mapping with the get and post methods.
 * All the common operations for the ImageGetter are handled here. such as getting the image from the database.
 * 
 */

package controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aj.org.objectweb.asm.Type;
import dao.DBData;
import model.CategoryModel;
import model.CustomerModel;
import model.DeliveryPersonModel;
import model.ProductModel;
import model.SellerModel;
import model.SellersProduct;

/*
 * Handles all the requests and responses for the "/image*" URL
 */
public class ImageGetterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// DoGet method to handle the get requests
	DBData db = new DBData();
	

	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		//if action is category, then the category image is retrieved from the database and sent to the client
		if (action.equals("category")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			CategoryModel categoryModel = db.getCategoryById(id);
			
			byte[] bytes = categoryModel.getImage();
			OutputStream os = response.getOutputStream();
			os.write(bytes);
			os.flush();
			os.close();
		}
		
		//if action is product, then the product image is retrieved from the database and sent to the client
		else if (action.equals("product")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			ProductModel productModel = db.getProductById(id);
			byte[] bytes = productModel.getImage();
			OutputStream os = response.getOutputStream();
			os.write(bytes);
			os.flush();
			os.close();
		}
		
		//if action is customer, then the customer image is retrieved from the database and sent to the client
		else if (action.equals("customer")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			CustomerModel customerModel = db.getCustomerById(id);
			byte[] bytes = customerModel.getImage();
			OutputStream os = response.getOutputStream();
			os.write(bytes);
			os.flush();
			os.close();
		}
		
		//if action is seller, then the seller image is retrieved from the database and sent to the client
		else if (action.equals("seller")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			SellerModel sellerModel = db.getSellerById(id);
			byte[] bytes = sellerModel.getImage();
			OutputStream os = response.getOutputStream();
			os.write(bytes);
			os.flush();
			os.close();
		}
		
		//if action is sellerproduct, then the sellerproduct image is retrieved from the database and sent to the client
		else if (action.equals("sellerproduct")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			SellersProduct sellersProduct = db.getSellerProductById(id);
			byte[] bytes = sellersProduct.getImage();
			OutputStream os = response.getOutputStream();
			os.write(bytes);
			os.flush();
			os.close();
		}
		
		//if action is delivery, then the delivery image is retrieved from the database and sent to the client
		else if (action.equals("delivery")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			DeliveryPersonModel deliveryPersonModel = db.getDeliveryPersonById(id);
			byte[] bytes = deliveryPersonModel.getImage();
			OutputStream os = response.getOutputStream();
			os.write(bytes);
			os.flush();
			os.close();
		}
	}

}
