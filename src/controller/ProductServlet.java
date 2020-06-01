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
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;

import customs.EscapeString;
import dao.DBData;
import model.CategoryModel;
import model.ProductModel;
import model.SubcategoryModel;
import model.UnitModel;

@MultipartConfig
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DBData db = new DBData();
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action=request.getParameter("action");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

	//	System.out.println("jhm"+action);
		if(action.equals("new"))
		{
			ProductModel productModel=new ProductModel();
			request.setAttribute("product", productModel);
			request.setAttribute("action", "new");
			request.getRequestDispatcher("/newproduct.jsp").forward(request, response);
		}
		else if (action.equals("view")) {
			ProductModel productModel = new ProductModel();
			request.setAttribute("products", productModel);
			request.setAttribute("action", "view");
			
			request.getRequestDispatcher("/product.jsp").forward(request, response);
		}
		else if (action.equals("update")) {
			int pid = Integer.parseInt(request.getParameter("pid").toString());
			ProductModel productModel = db.getProductById(pid);
			request.setAttribute("pid", pid);
			request.setAttribute("product", productModel);
			request.setAttribute("action", "update");
			
			
			request.getRequestDispatcher("/newproduct.jsp").forward(request, response);
		}else if (action.equals("delete")) {
			int pid = Integer.parseInt(request.getParameter("pid").toString());
			ProductModel productModel = db.getProductById(pid);
			db.deleteProduct(productModel);
			request.getRequestDispatcher("/product.jsp").forward(request, response);
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		System.out.println("line 50 "+action);
		
		if (action.equals("submit")) {
			String productName = request.getParameter("productName");
			String productDescription = request.getParameter("productDescription");
			Part part = request.getPart("productImage");
			String productPrice = request.getParameter("productPrice");
			String governmentPrice = request.getParameter("governmentPrice");;
			String productType = request.getParameter("productType");
			double productStock = Double.parseDouble(request.getParameter("productStock"));
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
			LocalDateTime now = LocalDateTime.now();  
			String time = (dtf.format(now));  

		
		//	String savePath=("C:\\Users\\HP\\git\\test\\ecommerce\\WebContent\\images\\products"+File.separator+productImageName);
			

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
			
			
			int cid;
			cid = Integer.parseInt(request.getParameter("dropdownProductCategory"));
			categoryModel = db.getCategoryById(cid);
			
			int sid;
			sid = Integer.parseInt(request.getParameter("dropdownProductSubcategory"));
			subcategoryModel = db.getSubcategoryById(sid);
			
			int uid;
			uid = Integer.parseInt(request.getParameter("unit").toString());
			UnitModel unitModel = db.getUnitById(uid);
			
			ProductModel productModel = new ProductModel();
			
			productModel.setProductStock(productStock);
			productModel.setProductName(productName);
			productModel.setProductDescription(productDescription);
			productModel.setProductCategory(categoryModel);
			productModel.setProductSubcategory(subcategoryModel);
			productModel.setImage(bytes);
			productModel.setProductPrice(productPrice);
			productModel.setGovernmentPrice(governmentPrice);
			productModel.setProductUnit(unitModel);
			productModel.setType(productType);
			
			db.saveProduct(productModel);
			request.getRequestDispatcher("/product.jsp").forward(request, response);

		}
		
		else if(action.equals("addmore")) {
			String productName = request.getParameter("productName");
			String productDescription = request.getParameter("productDescription");
			Part part = request.getPart("productImage");
			String productPrice = request.getParameter("productPrice");
			//String productCategory = request.getParameter("dropdownProductCategory").toString();
			String productSubcategory = request.getParameter("dropdownProductSubcategory").toString();
			String governmentPrice = request.getParameter("governmentPrice");
			String productType = request.getParameter("productType");
			double productStock = Double.parseDouble(EscapeString.Escape(request.getParameter("productStock")));

			
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
			
			
			int cid;
			cid = Integer.parseInt(request.getParameter("dropdownProductCategory"));
			categoryModel = db.getCategoryById(cid);
			
			int sid;
			sid = Integer.parseInt(request.getParameter("dropdownProductSubcategory"));
			subcategoryModel = db.getSubcategoryById(sid);
			
			int uid;
			uid = Integer.parseInt(request.getParameter("unit").toString());
			UnitModel unitModel = db.getUnitById(uid);
			
			ProductModel productModel = new ProductModel();
			
			productModel.setProductStock(productStock);
			productModel.setProductName(productName);
			productModel.setProductDescription(productDescription);
			productModel.setProductCategory(categoryModel);
			productModel.setProductSubcategory(subcategoryModel);
			productModel.setImage(bytes);
			productModel.setProductPrice(productPrice);
			productModel.setGovernmentPrice(governmentPrice);
			productModel.setProductUnit(unitModel);
			productModel.setType(productType);
			
			db.saveProduct(productModel);
			request.setAttribute("action", "addmore");
			request.getRequestDispatcher("/newproduct.jsp").forward(request, response);
		}
		else if (action.equals("update")) {
			String productImageName = null;
			String productName = request.getParameter("productName");
			String productDescription = request.getParameter("productDescription");
			Part part;
			ProductModel productModel = new ProductModel();
			ProductModel productModel2 = new ProductModel();
			int pid = Integer.parseInt(request.getParameter("pid").toString());
			productModel2 = db.getProductById(pid);
			
				
			
				part = request.getPart("productImage");
				
				System.out.println(part.getSize() + " heree");
				
				if (part.getSize() == 0) {
					productModel.setImage(productModel2.getImage());
				} else {
					InputStream inputStream = null;

					// prints out some information for debugging
					System.out.println(part.getName());
					System.out.println(part.getSize());
					System.out.println(part.getContentType());

					// obtains input stream of the upload file

					inputStream = part.getInputStream();
					byte[] bytes = IOUtils.toByteArray(inputStream);
					productModel.setImage(bytes);
				}
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");  
				LocalDateTime now = LocalDateTime.now();  
				String time = (dtf.format(now));  
		
			
			double productStock = Double.parseDouble(request.getParameter("productStock"));
			String productPrice = request.getParameter("productPrice");
			//String productCategory = request.getParameter("dropdownProductCategory").toString();
			String productSubcategory = request.getParameter("dropdownProductSubcategory").toString();
			String governmentPrice = request.getParameter("governmentPrice");
			String productType = request.getParameter("productType");

			
			int uid;
			uid = Integer.parseInt(request.getParameter("unit").toString());
			UnitModel unitModel = db.getUnitById(uid);
			
			CategoryModel categoryModel = new CategoryModel();
			SubcategoryModel subcategoryModel = new SubcategoryModel();
			
			int cid;
			cid = Integer.parseInt(request.getParameter("dropdownProductCategory").toString());			
			categoryModel = db.getCategoryById(cid);
			
			int sid;
			sid = Integer.parseInt(request.getParameter("dropdownProductSubcategory"));
			subcategoryModel = db.getSubcategoryById(sid);			
			productModel.setProductId(pid);
			productModel.setProductUnit(unitModel);
			productModel.setProductName(productName);
			productModel.setProductDescription(productDescription);
			productModel.setProductCategory(categoryModel);
			productModel.setProductSubcategory(subcategoryModel);
			productModel.setProductPrice(productPrice);
			productModel.setGovernmentPrice(governmentPrice);
			productModel.setType(productType);
	
			productModel.setProductStock(productStock);
			
			db.updateProduct(productModel);
			request.getRequestDispatcher("/product.jsp").forward(request, response);
			
			
		}
		
		
		
		
	}

}
