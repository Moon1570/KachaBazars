/*
 * This servlet is in charge of the bids, the request, response handling, and URL mapping with the get and post methods. 
 * Will contain common operations for the bids.
 */

package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBData;
import model.BidModel;
import model.ProductModel;

// Handles all the requests and responses for the "/bids*" URL
public class BidsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// DBData object to access the database
	DBData db = new DBData();
	
	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action").toString();
		
		// If the action is bid, then the bid is saved in the database
		if (action.equals("bid")) {		
			BidModel bidModel = new BidModel();
			int pid = Integer.parseInt(request.getParameter("pid"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String bidDate = request.getParameter("date").toString();
			Date date;
			try {
				date = simpleDateFormat.parse(bidDate);
				bidModel.setDate(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
						
			ProductModel productModel = db.getProductById(pid);
			
			bidModel.setBidQuantity(quantity);
			bidModel.setProductModel(productModel);

			db.saveBid(bidModel);
			
			request.setAttribute("pid", pid);
			request.getRequestDispatcher("/view_demand.jsp").forward(request, response);
		}
	}


	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
