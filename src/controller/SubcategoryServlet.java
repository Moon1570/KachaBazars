package controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DBData;
import model.CategoryModel;
import model.SubcategoryModel;



public class SubcategoryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	DBData db = new DBData();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		if (action.equals("new")) {
			SubcategoryModel subcategoryModel = new SubcategoryModel();
			request.setAttribute("subcategories", subcategoryModel);
			request.setAttribute("action", "new");
			
			request.getRequestDispatcher("/newsubcategory.jsp").forward(request, response);
			
		}
		else if (action.equals("view")) {
			request.setAttribute("action", "view");
			request.getRequestDispatcher("/subcategory.jsp").forward(request, response);
		}
		else if (action.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id").toString());
			SubcategoryModel subcategoryModel = new SubcategoryModel();
			subcategoryModel.setSubcategoryId(id);
			
			db.deleteSubcategoryById(subcategoryModel);
			request.getRequestDispatcher("/subcategory.jsp").forward(request, response);
		}
		else if(action.equals("update")) {
			int subCatId = Integer.parseInt(request.getParameter("id"));
			SubcategoryModel subcategoryModel = db.getSubcategoryById(subCatId);
			
			request.setAttribute("action", "update");
			
			request.setAttribute("subcategories", subcategoryModel);
			request.getRequestDispatcher("/newsubcategory.jsp").forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action").toString();
		System.out.println(action);
		if (action.equals("new")) {
			SubcategoryModel subcategory = new SubcategoryModel();
			CategoryModel categoryModel=new CategoryModel();
			
			subcategory.setSubcategoryName(request.getParameter("subcategory_name").toString());
			subcategory.setSubcategoryDescription(request.getParameter("subcategory_description").toString());
			subcategory.setGovtPrice(request.getParameter("subcategory_govt_price").toString());

			int id;
			id = Integer.parseInt(request.getParameter("dropdownCategory"));

			categoryModel = db.getCategoryById(id);
			subcategory.setCategoryInformation(categoryModel);
			
			db.saveSubcategory(subcategory);

			
			request.getRequestDispatcher("/subcategory.jsp").forward(request, response);
		} else if(action.equals("update")) {
			SubcategoryModel subcategory = new SubcategoryModel();

			int sid = Integer.parseInt(request.getParameter("sid"));
			
			subcategory = db.getSubcategoryById(sid);
			
			subcategory.setSubcategoryName(request.getParameter("subcategory_name").toString());
			subcategory.setSubcategoryDescription(request.getParameter("subcategory_description").toString());
			subcategory.setGovtPrice(request.getParameter("subcategory_govt_price").toString());

			int id;
			id = Integer.parseInt(request.getParameter("dropdownCategory"));

			CategoryModel categoryModel = db.getCategoryById(id);
			subcategory.setCategoryInformation(categoryModel);
			
			db.updateSubcategory(subcategory);

			
			request.getRequestDispatcher("/subcategory.jsp").forward(request, response);;
		}
	}

}
