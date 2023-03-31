/*
 * This servlet is in charge of the Category, the request, response handling, and URL mapping with the get and post methods. 
 * All the common operations for the category are handled here. such as adding, removing, updating, and viewing the category. 
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import customs.EscapeString;
import dao.DBData;
import model.CategoryModel;
import sun.nio.ch.IOUtil;


/*
 * Handles all the requests and responses for the "/category*" URL
 */
@MultipartConfig
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// DBData object to access the database
	DBData db = new DBData();

	// DoGet method to handle the get requests
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		System.out.println("Value of Action" + action);
		System.out.println(action);

		//if the action is not null and the action is new, the request will be forwarded to the newcategory.jsp page
		if (action != null) {
			if (action.equals("new")) {
				CategoryModel categoryModel = new CategoryModel();
				request.setAttribute("categories", categoryModel);
				request.setAttribute("action", "new");

				request.getRequestDispatcher("/newcategory.jsp").forward(request, response);
				System.out.println("In new page..");

			}

			//if the action is not null and the action is update, the request will be forwarded to the newcategory.jsp page
			else if (action.equals("update")) {
				int id = Integer.parseInt(request.getParameter("id").toString());
				CategoryModel categoryModel = db.getCategoryById(id);

				request.setAttribute("categories", categoryModel);
				request.setAttribute("action", "update");

				request.getRequestDispatcher("/newcategory.jsp").forward(request, response);
			}

			//if the action is not null and the action is delete, the request will be forwarded to the Category.jsp page
			else if (action.equals("delete")) {
				int id = Integer.parseInt(request.getParameter("id").toString());
				CategoryModel categoryModel = db.getCategoryById(id);
								
				db.deleteCategory(categoryModel);
				List<CategoryModel> categoryModel1 = db.getAllCategories();
				request.setAttribute("categories", categoryModel1);
				request.getRequestDispatcher("/Category.jsp").forward(request, response);
			}
		}
		//if the action is null, the request will be forwarded to the Category.jsp page
		else {

			List<CategoryModel> categoryModel = db.getAllCategories();
			request.setAttribute("categories", categoryModel);
			request.getRequestDispatcher("/Category.jsp").forward(request, response);
		}

	}


	// DoPost method to handle the post requests
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action").toString();

		//if the action is new, the request will be forwarded to the Category.jsp page
		if (action.equals("new")) {
			CategoryModel category = new CategoryModel();

			category.setCategoryName(request.getParameter("name"));
			category.setCategoryDescription(request.getParameter("description"));
			Part part = request.getPart("categoryImage");

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
			category.setImage(bytes);

			db.saveCategory(category);

			List<CategoryModel> categoryModels = db.getAllCategories();
			request.setAttribute("categories", categoryModels);

			request.getRequestDispatcher("/Category.jsp").forward(request, response);
		}

		//if the action is update, the request will be forwarded to the Category.jsp page
		else if (action.equals("update")) {
			int catId = Integer.parseInt(request.getParameter("categoryId").toString());
			CategoryModel category = db.getCategoryById(catId);
			category.setCategoryName(request.getParameter("name"));
			category.setCategoryDescription(request.getParameter("description"));


			db.updateCategory(category);

			List<CategoryModel> categoryModels = db.getAllCategories();
			request.setAttribute("categories", categoryModels);

			request.getRequestDispatcher("/Category.jsp").forward(request, response);

		}

	}
}
