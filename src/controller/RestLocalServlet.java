
/*
 * This servlet is in charge of the RestLocal, the request, response handling, and URL mapping with the get and post methods.
 * This is to handle the REST API calls for the android app.
 * All the common operations for the RestLocal are handled here. such as viewing the RestLocal page.
 */
package controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.zip.Deflater;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import antlr.collections.List;
import dao.AreaDao;
import dao.DBData;
import model.CartModel;
import model.CustomerModel;
import model.DistrictModel;
import model.DivisionModel;
import model.SellersProduct;
import model.UnionModel;
import model.UpazillaModel;

/*
 * This servlet will be handling all the request and response from the url /restlocal
 */
public class RestLocalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// DBData object to access the database
	DBData db = new DBData();

	// AreaDao object to access the database
	AreaDao areaDao = new AreaDao();

	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		// if the action is null, then no action is given
		if (action == null) {
			System.out.println("No action given");
		}

		//if the action is getLocalProducts, then the products will be returned in JSON format to the android app REST client
		else if (action.equals("getLocalProducts")) {
			int cid = Integer.parseInt(request.getParameter("cid"));
			CustomerModel customerModel = db.getCustomerById(cid);

			int divId = customerModel.getDivisionmodel().getDivisionId();
			int disId = customerModel.getDistrictModel().getDistrictId();
			int upaId = customerModel.getUpazillaModel().getUpazillaId();
			int uniId = customerModel.getUnionModel().getUnionId();

			ArrayList<SellersProduct> sellersProductsUnion = (ArrayList<SellersProduct>) db.getProductByUnion(uniId);
			ArrayList<SellersProduct> sellersProductsUpazilla = (ArrayList<SellersProduct>) db
					.getProductByUpazilla(upaId);
			ArrayList<SellersProduct> sellersProductsDistrict = (ArrayList<SellersProduct>) db
					.getProductByDistrict(disId);

			JSONArray jsonArray = new JSONArray();
			JSONArray jsonArray2 = new JSONArray();
			JSONArray jsonArray3 = new JSONArray();
			JSONArray jsonArray4 = new JSONArray();

			Iterator<SellersProduct> it = sellersProductsUnion.iterator();

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

					JO.put("govtPrice", sub.getSubcategoryModel().getGovtPrice());
					JO.put("productName", sub.getProductName());
					JO.put("productDescription", sub.getProductDescription());
					JO.put("productQuantity", sub.getProductQuantity());
					JO.put("productImageName", string);
					JO.put("productId", sub.getProductId());
					JO.put("sellerId", sub.getSellerModel().getSellerId());
					JO.put("sellerName",
							sub.getSellerModel().getSellerFirstName() + " " + sub.getSellerModel().getSellerLastName());
					JO.put("productPrice", sub.getProductPrice());

					jsonArray2.put(JO);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			Iterator<SellersProduct> it1 = sellersProductsUpazilla.iterator();

			while (it1.hasNext()) {
				Object type = (Object) it1.next();

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
					byte[] compressedData1 = bos.toByteArray();

					String string1 = Base64.getEncoder().encodeToString(compressedData1);

					JO.put("productName", sub.getProductName());
					JO.put("productDescription", sub.getProductDescription());
					JO.put("productQuantity", sub.getProductQuantity());
					JO.put("productImageName", string1);
					JO.put("productId", sub.getProductId());
					JO.put("sellerId", sub.getSellerModel().getSellerId());
					JO.put("sellerName",
							sub.getSellerModel().getSellerFirstName() + " " + sub.getSellerModel().getSellerLastName());
					JO.put("productPrice", sub.getProductPrice());

					jsonArray3.put(JO);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			Iterator<SellersProduct> it2 = sellersProductsDistrict.iterator();

			while (it2.hasNext()) {
				Object type = (Object) it2.next();

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
					byte[] compressedData1 = bos.toByteArray();

					String string1 = Base64.getEncoder().encodeToString(compressedData1);

					JO.put("productName", sub.getProductName());
					JO.put("productDescription", sub.getProductDescription());
					JO.put("productQuantity", sub.getProductQuantity());
					JO.put("productImageName", string1);

					JO.put("productId", sub.getProductId());
					JO.put("sellerId", sub.getSellerModel().getSellerId());
					JO.put("sellerName",
							sub.getSellerModel().getSellerFirstName() + " " + sub.getSellerModel().getSellerLastName());
					JO.put("productPrice", sub.getProductPrice());

					jsonArray4.put(JO);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			jsonArray.put(jsonArray2);
			jsonArray.put(jsonArray3);
			jsonArray.put(jsonArray4);

			System.out.println(jsonArray4.toString() + disId);

			PrintWriter pw = response.getWriter();

			pw.write(jsonArray.toString());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");


		// Registration
		if (action.equalsIgnoreCase("reg")) {

			int divId = Integer.parseInt(request.getParameter("divId"));
			int a = Integer.parseInt(request.getParameter("disId"));
			int b = Integer.parseInt(request.getParameter("upaId"));
			int c = Integer.parseInt(request.getParameter("uniId"));
			String encoded = request.getParameter("img");
			String fname, lname, phone, pass, village, street, zip, hnum, date, imageName;

			fname = request.getParameter("fname");
			lname = request.getParameter("lname");
			phone = request.getParameter("phone");
			village = request.getParameter("village");
			street = request.getParameter("street");
			hnum = request.getParameter("hnum");
			zip = request.getParameter("zip");
			pass = request.getParameter("pass");
			date = request.getParameter("dob");

			ArrayList<DistrictModel> districtModels = (ArrayList<DistrictModel>) areaDao.getDistrictByDivisionId(divId);
			int disId = districtModels.get(a).getDistrictId();
			ArrayList<UpazillaModel> upazillaModels = (ArrayList<UpazillaModel>) areaDao.getUpazillaByDistrictId(disId);
			int upaId = upazillaModels.get(b).getUpazillaId();
			ArrayList<UnionModel> unionModels = (ArrayList<UnionModel>) areaDao.getUnionByUpazillaId(upaId);

			DivisionModel divisionModel = areaDao.getDivisionById(divId);
			DistrictModel districtModel = districtModels.get(a);
			UpazillaModel upazillaModel = upazillaModels.get(b);
			UnionModel unionModel = unionModels.get(c);

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			LocalDateTime now = LocalDateTime.now();
			String time = (dtf.format(now));

			byte[] decodedBytes = Base64.getMimeDecoder().decode(encoded);

			CustomerModel customerModel = new CustomerModel();

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");

			try {
				customerModel.setCustomerDOB(simpleDateFormat.parse(date));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			customerModel.setCustomerFirstName(fname);
			customerModel.setCustomerLastName(lname);
			customerModel.setCustomerHoldingNumber(hnum);
			customerModel.setImage(decodedBytes);
			customerModel.setCustomerPassword(pass);
			customerModel.setCustomerPhone(phone);
			customerModel.setCustomerStreet(street);
			customerModel.setCustomerVillage(village);
			customerModel.setCustomerZipcode(zip);
			customerModel.setDistrictModel(districtModel);
			customerModel.setDivisionmodel(divisionModel);
			customerModel.setUnionModel(unionModel);
			customerModel.setUpazillaModel(upazillaModel);

			db.saveCustomer(customerModel);

			CartModel cartModel = new CartModel();
			CustomerModel customerModel2 = new CustomerModel();

			customerModel2 = db.getCustomerPasswordByPhone(phone);
			cartModel.setCustomerModel(customerModel2);

			request.setAttribute("cid", customerModel2.getCustomerId());

			db.saveCart(cartModel);

			PrintWriter pWriter = response.getWriter();

			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject(customerModel);

			jsonArray.put(jsonObject);
			pWriter.write(jsonArray.toString());

		} 
		// if the action is updateprofile, the customer profile will be updated
		else if (action.equals("updateprofile")) {

			int cid = Integer.parseInt(request.getParameter("cid"));

			CustomerModel customerModel = db.getCustomerById(cid);

			String passchange = request.getParameter("passchange");
			String addchange = request.getParameter("addchange");

			if (addchange.equals("true")) {
				int divId = Integer.parseInt(request.getParameter("divId"));
				int a = Integer.parseInt(request.getParameter("disId"));
				int b = Integer.parseInt(request.getParameter("upaId"));
				int c = Integer.parseInt(request.getParameter("uniId"));

				String village, street, zip, hnum;

				village = request.getParameter("village");
				street = request.getParameter("street");
				zip = request.getParameter("zip");
				hnum = request.getParameter("holding");

				ArrayList<DistrictModel> districtModels = (ArrayList<DistrictModel>) areaDao
						.getDistrictByDivisionId(divId);
				int disId = districtModels.get(a).getDistrictId();
				ArrayList<UpazillaModel> upazillaModels = (ArrayList<UpazillaModel>) areaDao
						.getUpazillaByDistrictId(disId);
				int upaId = upazillaModels.get(b).getUpazillaId();
				ArrayList<UnionModel> unionModels = (ArrayList<UnionModel>) areaDao.getUnionByUpazillaId(upaId);

				DivisionModel divisionModel = areaDao.getDivisionById(divId);
				DistrictModel districtModel = districtModels.get(a);
				UpazillaModel upazillaModel = upazillaModels.get(b);
				UnionModel unionModel = unionModels.get(c);

				customerModel.setDivisionmodel(divisionModel);
				customerModel.setDistrictModel(districtModel);
				customerModel.setUpazillaModel(upazillaModel);
				customerModel.setUnionModel(unionModel);
				customerModel.setCustomerStreet(street);
				customerModel.setCustomerHoldingNumber(hnum);
				customerModel.setCustomerVillage(village);
				customerModel.setCustomerZipcode(zip);
			}
			if (passchange.contentEquals("true")) {
				String pass = request.getParameter("pass");
				customerModel.setCustomerPassword(pass);
			}

			String fname, lname, dob, phone;
			String encoded = request.getParameter("img");
			byte[] decodedBytes = Base64.getMimeDecoder().decode(encoded);

			fname = request.getParameter("fname");
			lname = request.getParameter("lname");
			phone = request.getParameter("phone");
			dob = request.getParameter("dob");

			customerModel.setImage(decodedBytes);

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");

			try {
				customerModel.setCustomerDOB(simpleDateFormat.parse(dob));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			customerModel.setCustomerFirstName(fname);
			customerModel.setCustomerLastName(lname);
			customerModel.setCustomerPhone(phone);
			
			db.updateCustomer(customerModel);

			JSONArray jsonArray = new JSONArray();
			PrintWriter pw = response.getWriter();
			pw.write(jsonArray.toString());
		}
	}

}
