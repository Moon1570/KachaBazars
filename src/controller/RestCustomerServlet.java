/*
 * This servlet is in charge of the RestCustomer, the request, response handling, and URL mapping with the get and post methods.
 * This is to handle the REST API calls for the android app.
 * All the common operations for the RestCustomer are handled here. such as viewing the RestCustomer page.
 */
package controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.zip.Deflater;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.AreaDao;
import dao.DBData;
import model.CategoryModel;
import model.CustomerModel;
import model.DistrictModel;
import model.DivisionModel;
import model.OrdersModel;
import model.ProductModel;
import model.SellersProduct;
import model.UnionModel;
import model.UpazillaModel;


/*
 * This servlet will be handling all the request and response and REST API calls from the url /restcustomer
 */
public class RestCustomerServlet extends HttpServlet {

	// DBData object to access the database
	DBData db = new DBData();

	// AreaDao object to access the database
	AreaDao aDao = new AreaDao();

	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
		System.out.println(action);

		//if the action is null, the response will be empty
		if (action == null) {
			System.out.println(" ");
		}

		// if the action is divisions, the response will be getting all the divisions in JSON format
		else if (action.equals("divisions")) {

			PrintWriter pw = response.getWriter();
			ArrayList<DivisionModel> divisionModels = (ArrayList<DivisionModel>) db.getAllDivision();
			ArrayList<DistrictModel> districtModels = (ArrayList<DistrictModel>) aDao.getAllDistricts();
			ArrayList<UpazillaModel> upazillaModels = (ArrayList<UpazillaModel>) aDao.getAllUpazillas();
			ArrayList<UnionModel> unionModels = (ArrayList<UnionModel>) aDao.getAllUnions();

			JSONArray jsonArray = new JSONArray();
			jsonArray.put(divisionModels);
			jsonArray.put(districtModels);
			jsonArray.put(upazillaModels);
			jsonArray.put(unionModels);

			pw.write(jsonArray.toString());
		} 
		
		// if the action is getProducts, the response will be getting all the products in JSON format
		else if (action.equals("getProducts")) {

			PrintWriter pw = response.getWriter();
			JSONArray jsonArray = new JSONArray();
			ArrayList<ProductModel> productModels = db.getAllProductsArrayList();
			jsonArray.put(productModels);
			System.out.println("rest client " + action + " " + productModels);

			pw.write(jsonArray.toString());
		} 
		// if the action is getSellerProducts, the response will be getting all the seller products in JSON format
		else if (action.equals("getSellerProducts")) {
			PrintWriter pw = response.getWriter();
			JSONArray jsonArray = new JSONArray();
			ArrayList<SellersProduct> sellersProducts = db.getAllSellerProductrsArraylist();

			Iterator<SellersProduct> it = sellersProducts.iterator();

			while (it.hasNext()) {
				Object type = (Object) it.next();

				JSONObject JO = new JSONObject();

				SellersProduct sub = (SellersProduct) type;
				try {

					String string = Base64.getEncoder().encodeToString(sub.getImage());
					
					JO.put("productName", sub.getProductName());
					JO.put("productDescription", sub.getProductDescription());
					JO.put("productQuantity", sub.getProductQuantity());
					JO.put("productImageName", string);
					JO.put("productId", sub.getProductId());
					JO.put("sellerId", sub.getSellerModel().getSellerId());
					JO.put("sellerName",
							sub.getSellerModel().getSellerFirstName() + " " + sub.getSellerModel().getSellerLastName());
					JO.put("productPrice", sub.getProductPrice());
					JO.put("govtPrice", sub.getSubcategoryModel().getGovtPrice());

					System.out.println(
							sub.getSellerModel().getSellerFirstName() + " " + sub.getSellerModel().getSellerLastName());

					jsonArray.put(JO);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			pw.write(jsonArray.toString());
		} 
		
		// if the action is disctrictbydivision, the response will be getting all the districts by division id in JSON format
		else if (action.equals("disctrictbydivision")) {

			int divId = Integer.parseInt(request.getParameter("divId"));

			ArrayList<DistrictModel> districtModels = db.getDistrictByDivisionId(divId);

			JSONArray jsonArray = new JSONArray(districtModels);

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());

		} 
		
		// if the action is upazillabydistrict, the response will be getting all the upazillas by district id in JSON format
		else if (action.equals("upazillabydistrict")) {

			String dis = request.getParameter("dis");
			System.out.println(dis);

			int divId = Integer.parseInt(request.getParameter("divId"));
			ArrayList<DistrictModel> districtModels = db.getDistrictByDivisionId(divId);

			int disId = Integer.parseInt(request.getParameter("disId"));

			disId = districtModels.get(disId).getDistrictId();
			ArrayList<UpazillaModel> upazillaModels = db.getUpazillaByDistrictId(disId);

			JSONArray jsonArray = new JSONArray(upazillaModels);

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
		} 
		
		//if the action is getSellerProductByUpaId, the response will be getting all the seller products by upazilla id in JSON format
		else if (action.equals("getSellerProductByUpaId")) {

			int divId = Integer.parseInt(request.getParameter("divId"));
			ArrayList<DistrictModel> districtModels = db.getDistrictByDivisionId(divId);

			int disId = Integer.parseInt(request.getParameter("disId"));
			disId = districtModels.get(disId).getDistrictId();

			ArrayList<UpazillaModel> upazillaModels = db.getUpazillaByDistrictId(disId);
			int upaId = Integer.parseInt(request.getParameter("upaId"));
			upaId = upazillaModels.get(upaId).getUpazillaId();

			ArrayList<SellersProduct> sellersProducts = db.getSellerProductByUpazillaId(upaId);
			JSONArray jsonArray = new JSONArray();

			Iterator<SellersProduct> it = sellersProducts.iterator();

			while (it.hasNext()) {
				Object type = (Object) it.next();

				JSONObject JO = new JSONObject();

				SellersProduct sub = (SellersProduct) type;
				try {
					byte[] a = sub.getImage();
					Deflater compressor = new Deflater();
					compressor.setLevel(Deflater.BEST_COMPRESSION);
					
					  compressor.setInput(a);
					  compressor.finish();
					   
					  // Create an expandable byte array to hold the compressed data.
					  // It is not necessary that the compressed data will be smaller than
					  // the uncompressed data.
					  ByteArrayOutputStream bos = new ByteArrayOutputStream(a.length);
					   
					  // Compress the data
					  byte[] buf = new byte[1024];
					  while (!compressor.finished()) {
					      int count = compressor.deflate(buf);
					      bos.write(buf, 0, count);
					  }
					  try {
					      bos.close();
					  } catch (IOException e) {
					  }
					   
					  // Get the compressed data
					  byte[] compressedData = bos.toByteArray();
					
					
					String string = Base64.getEncoder().encodeToString(compressedData);

					JO.put("productName", sub.getProductName());
					JO.put("productDescription", sub.getProductDescription());
					JO.put("productQuantity", sub.getProductQuantity());
					JO.put("productImageName", string);
					JO.put("productId", sub.getProductId());
					JO.put("sellerId", sub.getSellerModel().getSellerId());
					JO.put("sellerName",
							sub.getSellerModel().getSellerFirstName() + " " + sub.getSellerModel().getSellerLastName());
					JO.put("productPrice", sub.getProductPrice());
					JO.put("govtPrice", sub.getSubcategoryModel().getGovtPrice());

					jsonArray.put(JO);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());

		} 
		
		//if the action is getCategories, the response will be getting all the categories in JSON format
		else if (action.equalsIgnoreCase("getCategories")) {

			JSONArray jsonArray = new JSONArray();

			List<CategoryModel> categoryModel = (ArrayList<CategoryModel>) db.getAllCategories();

			Iterator<CategoryModel> it = categoryModel.iterator();

			while (it.hasNext()) {
				Object type = (Object) it.next();

				JSONObject JO = new JSONObject();

				CategoryModel sub = (CategoryModel) type;
				try {
					
					String string = Base64.getEncoder().encodeToString(sub.getImage());
										
					JO.put("categoryId", sub.getCategoryId());
					JO.put("categoryName", sub.getCategoryName());
					JO.put("categoryDesc", sub.getCategoryDescription());
					JO.put("categoryImage", string);

					jsonArray.put(JO);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());

		} 
		
		//if the action is getProductByCategoryId, the response will be getting all the products by category id in JSON format
		else if (action.equalsIgnoreCase("getProductByCategoryId")) {

			JSONArray jsonArray = new JSONArray();
	
			int catId = Integer.parseInt(request.getParameter("catId"));

			CategoryModel categoryModel = db.getCategoryById(catId);

			ArrayList<ProductModel> productModels = (ArrayList<ProductModel>) db
					.getProductByCategoryName(categoryModel.getCategoryName());

			Iterator<ProductModel> it = productModels.iterator();

			while (it.hasNext()) {
				Object type = (Object) it.next();

				JSONObject JO = new JSONObject();

				ProductModel sub = (ProductModel) type;
				try {

					byte[] a = sub.getImage();
					Deflater compressor = new Deflater();
					compressor.setLevel(Deflater.BEST_COMPRESSION);
					
					  compressor.setInput(a);
					  compressor.finish();
					   
					  // Create an expandable byte array to hold the compressed data.
					  // It is not necessary that the compressed data will be smaller than
					  // the uncompressed data.
					  ByteArrayOutputStream bos = new ByteArrayOutputStream(a.length);
					   
					  // Compress the data
					  byte[] buf = new byte[1024];
					  while (!compressor.finished()) {
					      int count = compressor.deflate(buf);
					      bos.write(buf, 0, count);
					  }
					  try {
					      bos.close();
					  } catch (IOException e) {
					  }
					   
					  // Get the compressed data
					  byte[] compressedData = bos.toByteArray();
					
					
						String string = Base64.getEncoder().encodeToString(compressedData);
					
					
					JO.put("productName", sub.getProductName());
					JO.put("productDescription", sub.getProductDescription());
					JO.put("productQuantity", sub.getProductStock());
					JO.put("productImageName", string);
					JO.put("productId", sub.getProductId());
					JO.put("productPrice", sub.getProductPrice());
					JO.put("govtPrice", sub.getProductSubcategory().getGovtPrice());
					JO.put("stock", sub.getProductStock());
					JO.put("unit", sub.getProductUnit().getUnit());

					jsonArray.put(JO);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			System.out.println(jsonArray.toString());

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());

		}
		
		//if the action is unionbyupazilla, the response will be getting all the unions by upazilla id in JSON format
		else if (action.equals("unionbyupazilla")) {

			int divId = Integer.parseInt(request.getParameter("divId"));
			ArrayList<DistrictModel> districtModels = db.getDistrictByDivisionId(divId);

			int disId = Integer.parseInt(request.getParameter("disId"));
			disId = districtModels.get(disId).getDistrictId();

			ArrayList<UpazillaModel> upazillaModels = db.getUpazillaByDistrictId(disId);
			int upaId = Integer.parseInt(request.getParameter("upaId"));
			upaId = upazillaModels.get(upaId).getUpazillaId();
			
			
			ArrayList<UnionModel> unionModels = (ArrayList<UnionModel>) aDao.getUnionByUpazillaId(upaId);
			
			System.out.println(divId + " " + disId + " " + upaId + " ");

			JSONArray jsonArray = new JSONArray(unionModels);

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
		}
		
		//if the action is getSellerProductByCategoryId, the response will be getting all the seller products by category id in JSON format
		else if (action.equalsIgnoreCase("getSellerProductByCategoryId")) {

			JSONArray jsonArray = new JSONArray();
	
			int catId = Integer.parseInt(request.getParameter("catId"));

			CategoryModel categoryModel = db.getCategoryById(catId);

			ArrayList<SellersProduct> sellersProducts = (ArrayList<SellersProduct>) db
					.getSellerProductByCategoryName(categoryModel.getCategoryName());

			Iterator<SellersProduct> it = sellersProducts.iterator();

			while (it.hasNext()) {
				Object type = (Object) it.next();

				JSONObject JO = new JSONObject();

				SellersProduct sub = (SellersProduct) type;
				try {

					byte[] a = sub.getImage();
					Deflater compressor = new Deflater();
					compressor.setLevel(Deflater.BEST_COMPRESSION);
					
					  compressor.setInput(a);
					  compressor.finish();
					   
					  // Create an expandable byte array to hold the compressed data.
					  // It is not necessary that the compressed data will be smaller than
					  // the uncompressed data.
					  ByteArrayOutputStream bos = new ByteArrayOutputStream(a.length);
					   
					  // Compress the data
					  byte[] buf = new byte[1024];
					  while (!compressor.finished()) {
					      int count = compressor.deflate(buf);
					      bos.write(buf, 0, count);
					  }
					  try {
					      bos.close();
					  } catch (IOException e) {
					  }
					   
					  // Get the compressed data
					  byte[] compressedData = bos.toByteArray();
					
					
					String string = Base64.getEncoder().encodeToString(compressedData);
					
					
					
					JO.put("productName", sub.getProductName());
					JO.put("productDescription", sub.getProductDescription());
					JO.put("productQuantity", sub.getProductQuantity());
					JO.put("productImageName", string);
					JO.put("productId", sub.getProductId());
					JO.put("productPrice", sub.getProductPrice());
					JO.put("govtPrice", sub.getSubcategoryModel().getGovtPrice());
					JO.put("stock", sub.getProductQuantity());
					JO.put("sellerName", sub.getSellerModel().getSellerFirstName());
					JO.put("unit", sub.getUnitModel().getUnit());

					jsonArray.put(JO);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			System.out.println(jsonArray.toString());

			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());

		}
	}

	// All the post REST API will be here
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		String phone = request.getParameter("k1");
		String pass = request.getParameter("k2");

		CustomerModel customerModel = new CustomerModel();

		customerModel.setCustomerPhone(phone);
		customerModel.setCustomerPassword(pass);

		PrintWriter pw = response.getWriter();

		CustomerModel customerModel2 = db.getCustomerPasswordByPhone(phone);

		if (customerModel2.getCustomerPhone().equals(phone) && customerModel2.getCustomerPassword().equals(pass)) {
			ArrayList<CustomerModel> customerModels = new ArrayList<CustomerModel>();
			customerModels.add(customerModel2);

			JSONArray jsonArray = new JSONArray();

			jsonArray.put(customerModels);

			pw.write(jsonArray.toString());

		}

		/*
		 * for (CustomerModel c1:customerModels) { if
		 * (c1.getCustomerPhone().equals(phone) &&
		 * c1.getCustomerPassword().equals(pass)) {
		 * 
		 * JSONObject jsonObject = new JSONObject(); try { jsonObject.put("k1",
		 * c1.getCustomerId()); jsonObject.put("k2", c1.getCustomerFirstName());
		 * jsonObject.put("k3", c1.getCustomerLastName()); jsonObject.put("k4",
		 * c1.getDivisionmodel().getDivisionName()); jsonObject.put("k5",
		 * c1.getDistrictModel().getDistrictName()); jsonObject.put("k6",
		 * c1.getUpazillaModel().getUpazillaName()); jsonObject.put("k7",
		 * c1.getUnionModel().getUnionBanglaName()); jsonObject.put("k8",
		 * c1.getCustomerVillage()); jsonObject.put("k9", c1.getCustomerStreet());
		 * jsonObject.put("k10", c1.getCustomerPhone()); jsonObject.put("k11",
		 * c1.getCustomerDOB()); jsonObject.put("k12", c1.getCustomerImageName());
		 * jsonObject.put("k13", c1.getCustomerImagePath()); jsonObject.put("k14",
		 * c1.getCustomerPassword());
		 * 
		 * CustomerModel customerModel5 = c1; System.out.println("fdbfd" +
		 * customerModel5);
		 * 
		 * 
		 * 
		 * } catch (JSONException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } }
		 */

	}
}
