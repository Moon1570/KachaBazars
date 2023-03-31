

/*
 * This servlet is in charge of the order success servler, the request, response handling, and URL mapping with the get and post methods.
 * All the common operations for the order success are handled here. such as viewing the order success page.
 */
package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBData;
import dao.OrderDao;
import model.OrderSellerProductModel;
import model.OrdersModel;
import model.ProductModel;
import model.SellersProduct;
import sslcommerz.TransactionResponseValidator;


/*
 * This servlet will be handling all the request and response from the url /ordersuccess
 */
public class OrderSuccessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// DBData object to access the database
	DBData db = new DBData();

	// AreaDao object to access the database
	OrderDao od = new OrderDao();
	
	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		Map<String, String[]> map = request.getParameterMap();
		Map<String, String> map2 = new HashMap<String, String>();
	
		String[] act = map.get("action");

		//if the action is inventoryorder and the order is sucessfully done, the request will be forwarded to the success.jsp page
		if ((act[0]).equalsIgnoreCase("inventoryorder")) {
			TransactionResponseValidator transactionResponseValidator = new TransactionResponseValidator();
			//		transactionResponseValidator.receiveSuccessResponse(map);
			int line = 1;
			for (String var : map.keySet()) {
			//	System.out.println(map.get(var).length);
				String[] array = map.get(var);
				for (String string : array) {
					System.out.println("line "+ line++ + " -> " +var + " : " +string + "\n\n");	
					map2.put(var, string);
				}	
			}	
			try {
				
				if (transactionResponseValidator.receiveSuccessResponse(map2)) {
					
					String tranId = map2.get("tran_id");
					
					OrdersModel ordersModel = od.getOrderByTransactionId(tranId);
					
					ordersModel.setPaymentStatus(true);
					double qty = ordersModel.getOrderQuantity();
					double stock = ordersModel.getProductModel().getProductStock();
					
					stock = stock - qty;
					
					ProductModel productModel = ordersModel.getProductModel();
					productModel.setProductStock(stock);
					
					db.updateProduct(productModel);					
					db.updateOrder(ordersModel);
					
					request.getRequestDispatcher("/success.jsp").forward(request, response);
				}
				else {
					request.getRequestDispatcher("/fail.jsp").forward(request, response);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//if the action is cardorder, and the order is sucessfully done, the request will be forwarded to the success.jsp page
		else if ((act[0]).equalsIgnoreCase("cartorder")) {
			
			TransactionResponseValidator transactionResponseValidator = new TransactionResponseValidator();
			//		transactionResponseValidator.receiveSuccessResponse(map);
			int line = 1;
			for (String var : map.keySet()) {
			//	System.out.println(map.get(var).length);
				String[] array = map.get(var);
				for (String string : array) {
					System.out.println("line "+ line++ + " -> " +var + " : " +string + "\n\n");	
					map2.put(var, string);
				}	
			}	
			try {
				if (transactionResponseValidator.receiveSuccessResponse(map2)) {
					
					String tranId = map2.get("tran_id");
					
					OrdersModel ordersModel = od.getOrderByTransactionId(tranId);
					
					ordersModel.setPaymentStatus(true);
					
					
					
					List<OrdersModel> ordersModels = od.getCartOrderListByTransactionId(tranId);
					
					Iterator<OrdersModel> it = ordersModels.iterator();
					
					while (it.hasNext()) {
						Object type = (Object) it.next();

						OrdersModel sub =  (OrdersModel) type;
						
						sub.setPaymentStatus(true);
						
						double qty = sub.getOrderQuantity();
						double stock = sub.getProductModel().getProductStock();
						
						stock = stock - qty;
						
						ProductModel productModel = sub.getProductModel();
						productModel.setProductStock(stock);
						
						db.updateProduct(productModel);
						
						db.updateOrder(sub);

					}
					
					
					request.getRequestDispatcher("/success.jsp").forward(request, response);
				}
				else {
					request.getRequestDispatcher("/fail.jsp").forward(request, response);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//if the action is sellerproduct, and the order is sucessfully done, the request will be forwarded to the success.jsp page
		else if ((act[0]).equalsIgnoreCase("sellerproduct")) {
			
			TransactionResponseValidator transactionResponseValidator = new TransactionResponseValidator();
			//		transactionResponseValidator.receiveSuccessResponse(map);
			int line = 1;
			for (String var : map.keySet()) {
			//	System.out.println(map.get(var).length);
				String[] array = map.get(var);
				for (String string : array) {
					System.out.println("line "+ line++ + " -> " +var + " : " +string + "\n\n");	
					map2.put(var, string);
				}	
			}	
			try {
				if (transactionResponseValidator.receiveSuccessResponse(map2)) {
					
					String tranId = map2.get("tran_id");
					System.out.println(tranId + " Tran ID");
					
					OrderSellerProductModel ordersModel = od.getOrderSellerProductByTransactionId(tranId);
					
					ordersModel.setPaymentStatus(true);
					
					double qty = ordersModel.getOrderQuantity();
					SellersProduct sellersProduct = ordersModel.getSellersProduct();
					double stock = sellersProduct.getProductQuantity();
					
					stock = stock-qty;
					
					sellersProduct.setProductQuantity(stock);
					
					db.updateSellerProduct(sellersProduct);
					db.updateOrderSellerProduct(ordersModel);
					
					request.getRequestDispatcher("/success.jsp").forward(request, response);
				}
				else {
					request.getRequestDispatcher("/fail.jsp").forward(request, response);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
