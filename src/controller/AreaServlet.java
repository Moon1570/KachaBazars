package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import dao.AreaDao;
import dao.DBData;
import model.CategoryModel;
import model.DistrictModel;
import model.DivisionModel;
import model.OrdersModel;
import model.SellersProduct;
import model.UnionModel;
import model.UpazillaModel;

public class AreaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DBData db = new DBData();
	AreaDao ad = new AreaDao();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher rd = null;
		String action = request.getParameter("action").toString();
		System.out.println(action + "In get");
		HttpSession session = request.getSession();
		
		
		if (action.equals("div")) {
			List<DivisionModel> div = db.getAllDivision();
			
			com.google.gson.JsonObject gson = new JsonObject();
			JsonArray array = new JsonArray();
			
			java.util.Iterator<DivisionModel> it = div.iterator();
			int count = 0;
			while (it.hasNext()) {
				Object type = (Object) it.next();
				JsonObject item = new JsonObject();
				DivisionModel sub =  (DivisionModel) type;
				count++;
				
				item.addProperty("count", count);
				item.addProperty("id", sub.getDivisionId());
				item.addProperty("name", sub.getDivisionBanglaName());	
				
				array.add(item);
				
			}

			gson.add("demo", array); 
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			response.getWriter().write(gson.toString());
		}
		else if (action.equals("dis")) {
			List<DistrictModel> dis = ad.getAllDistricts();
			
			com.google.gson.JsonObject gson = new JsonObject();
			JsonArray array = new JsonArray();
			
			java.util.Iterator<DistrictModel> it = dis.iterator();
			int count = 0;

			while (it.hasNext()) {
				Object type = (Object) it.next();
				JsonObject item = new JsonObject();
				DistrictModel sub =  (DistrictModel) type;
				
				count++;
				
				item.addProperty("id", sub.getDistrictId());
				item.addProperty("count", count);
				item.addProperty("name", sub.getDistrictBanglaName());
				item.addProperty("div", sub.getDivisionModel().getDivisionId() + " " + sub.getDivisionModel().getDivisionBanglaName());
				
				array.add(item);
				
				
			}

			gson.add("demo", array); 
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(gson.toString());
		}
		
		else if (action.equals("upa")) {
			List<UpazillaModel> upa = ad.getAllUpazillas();
			
			com.google.gson.JsonObject gson = new JsonObject();
			JsonArray array = new JsonArray();
			
			java.util.Iterator<UpazillaModel> it = upa.iterator();
			
			int count=0;
			while (it.hasNext()) {
				Object type = (Object) it.next();
				JsonObject item = new JsonObject();
				UpazillaModel sub =  (UpazillaModel) type;
				
				count++;
				
				item.addProperty("count", count);
				item.addProperty("id", sub.getUpazillaId());
				item.addProperty("name", sub.getUpazillaBangaName());
				item.addProperty("dis", sub.getDistrictModel().getDistrictId() + " " + sub.getDistrictModel().getDistrictBanglaName());
				
				array.add(item);
				
				
			}

			gson.add("demo", array); 
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(gson.toString());
		}
		else if (action.equals("uni")) {
			List<UnionModel> uni = ad.getAllUnions();
			
			com.google.gson.JsonObject gson = new JsonObject();
			JsonArray array = new JsonArray();
			
			java.util.Iterator<UnionModel> it = uni.iterator();
			
			int count=0;
			
			while (it.hasNext()) {
				Object type = (Object) it.next();
				JsonObject item = new JsonObject();
				UnionModel sub =  (UnionModel) type;
				
				count++;
				
				item.addProperty("count", count);
				item.addProperty("id", sub.getUnionId());
				item.addProperty("name", sub.getUnionBanglaName());
				item.addProperty("upa", sub.getUpazillaModel().getUpazillaId() + " " + sub.getUpazillaModel().getUpazillaBangaName());
				
				array.add(item);
				
				
			}

			gson.add("demo", array); 
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(gson.toString());
		}
		else if(action.equalsIgnoreCase("divPage")) {
			session.setAttribute("page", request.getParameter("page"));
			
			request.setAttribute("page", request.getParameter("page"));
			request.getRequestDispatcher("/view_division.jsp").forward(request, response);
			
		}
		else if(action.equalsIgnoreCase("disPage")) {
			session.setAttribute("page", request.getParameter("page"));
			request.setAttribute("page", request.getParameter("page"));
			request.getRequestDispatcher("/view_district.jsp").forward(request, response);
			
		}
		else if (action.equalsIgnoreCase("upaPage")) {
			session.setAttribute("page", request.getParameter("page"));
			request.setAttribute("page", request.getParameter("page"));
			request.getRequestDispatcher("/view_upazilla.jsp").forward(request, response);

		}
		else if (action.equalsIgnoreCase("uniPage")) {
			session.setAttribute("page", request.getParameter("page"));
			request.setAttribute("page", request.getParameter("page"));
			request.getRequestDispatcher("/view_union.jsp").forward(request, response);
		}
		else if(action.equalsIgnoreCase("adddiv")) {
			request.setAttribute("page", request.getParameter("page"));
			request.getRequestDispatcher("/new_division.jsp").forward(request, response);
		}
		else if(action.equals("delete")) {
			request.setAttribute("page", session.getAttribute("page"));
			int id = Integer.parseInt(request.getParameter("divId"));
			DivisionModel divisionModel = ad.getDivisionById(id);
			
			ad.deleteDivision(divisionModel);
			
			request.getRequestDispatcher("/view_division.jsp").forward(request, response);
			
		}
		else if(action.equals("update")) {
			request.setAttribute("page", session.getAttribute("page"));
			int id = Integer.parseInt(request.getParameter("divId"));
			DivisionModel divisionModel = ad.getDivisionById(id);
			
			request.setAttribute("divi", divisionModel);
			request.getRequestDispatcher("/update_division.jsp").forward(request, response);
		}
		else if(action.equals("deletedis")) {
			request.setAttribute("page", session.getAttribute("page"));
			int dis = Integer.parseInt(request.getParameter("disId"));
			DistrictModel districtModel = ad.getDistrictById(dis);
			ad.deleteDistrict(districtModel);
			request.getRequestDispatcher("/view_district.jsp").forward(request, response);
		}
		else if(action.equalsIgnoreCase("updatedis")) {
			request.setAttribute("page", session.getAttribute("page"));
			int dis = Integer.parseInt(request.getParameter("disId"));
			DistrictModel districtModel = ad.getDistrictById(dis);
			List<DivisionModel> divisionModels = db.getAllDivision();
			
			request.setAttribute("dist", districtModel);
			request.setAttribute("divlist", divisionModels);
			
			request.getRequestDispatcher("/update_district.jsp").forward(request, response);
		}
		else if(action.equalsIgnoreCase("deleteUpa")) {
			request.setAttribute("page", session.getAttribute("page"));
			int upaId = Integer.parseInt(request.getParameter("upaId"));
			UpazillaModel upazillaModel = ad.getUpazillaById(upaId);
			
			ad.deleteUpazilla(upazillaModel);
			
			request.getRequestDispatcher("/view_upazilla.jsp").forward(request, response);
		}
		else if(action.equalsIgnoreCase("updateUpa")) {
			request.setAttribute("page", session.getAttribute("page"));
			int upa = Integer.parseInt(request.getParameter("upaId"));
			UpazillaModel upazillaModel = ad.getUpazillaById(upa);
			List<DivisionModel> divisionModels = db.getAllDivision();
			
			request.setAttribute("divlist", divisionModels);
			request.setAttribute("upa", upazillaModel);
			
			request.getRequestDispatcher("/update_upazilla.jsp").forward(request, response);
		}
		else if(action.equalsIgnoreCase("deleteUni")) {
			request.setAttribute("page", session.getAttribute("page"));
			int upiId = Integer.parseInt(request.getParameter("uniId"));
			UnionModel upazillaModel = ad.getUnionById(upiId);
			System.out.println(upazillaModel.getUnionId());
			ad.deleteUnion(upazillaModel);
			
			request.getRequestDispatcher("/view_union.jsp").forward(request, response);
		}
		else if(action.equalsIgnoreCase("updateUni")) {
			request.setAttribute("page", session.getAttribute("page"));
			int uniId = Integer.parseInt(request.getParameter("uniId"));
			UnionModel unionModel = ad.getUnionById(uniId);
			
			List<DivisionModel> divisionModels = db.getAllDivision();
			
			request.setAttribute("divlist", divisionModels);
			request.setAttribute("uni", unionModel);
			
			request.getRequestDispatcher("/update_union.jsp").forward(request, response);
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher rd = null;
		String action = request.getParameter("action").toString();
		
		System.out.println(action);

		if (action.equals("adddiv")) {

			request.setAttribute("page", request.getParameter("page"));
			int count = ad.countDivision();
			count++;
			
			DivisionModel divisionModel =  new DivisionModel(); 
			
			divisionModel.setDivisionBanglaName(request.getParameter("divName"));
			ad.saveDivision(divisionModel);
			request.getRequestDispatcher("/view_division.jsp").forward(request, response);
			
		//	int id = ad.getLastDivId();
			
		//	JsonObject item = new JsonObject();
		//	item.addProperty("id", id);
		//	item.addProperty("count", count);
		//	item.addProperty("name", request.getParameter("divName"));
			
		//	System.out.println("Triggered");
		//	PrintWriter out = response.getWriter();
//			//response.setContentType("application/json");
//			//response.setCharacterEncoding("UTF-8");
//			//response.getWriter().write(gson.toString());
			//out.write(item.toString());
		} else if(action.equalsIgnoreCase("updatediv")) {
			request.setAttribute("page", request.getParameter("page"));
			int id = Integer.parseInt(request.getParameter("divId"));
			String divName = request.getParameter("divName");
			
			DivisionModel divisionModel = new DivisionModel();
			divisionModel.setDivisionId(id);
			divisionModel.setDivisionBanglaName(divName);
			
			ad.updateDivision(divisionModel);
			request.getRequestDispatcher("/view_division.jsp").forward(request, response);
		} else if(action.equals("adddis")) {
			request.setAttribute("page", request.getParameter("page"));
			int divId = Integer.parseInt(request.getParameter("divisiondd"));
			String name = request.getParameter("disName");
			DivisionModel divisionModel = ad.getDivisionById(divId);
			DistrictModel districtModel = new DistrictModel();
			districtModel.setDivisionModel(divisionModel);
			districtModel.setDistrictBanglaName(name);
			
			ad.saveDistrict(districtModel);
			request.getRequestDispatcher("/view_district.jsp").forward(request, response);
		} else if(action.equals("updatedis")) {
			request.setAttribute("page", request.getParameter("page"));
			int disId = Integer.parseInt(request.getParameter("disId"));
			int divId = Integer.parseInt(request.getParameter("divisiondd"));
			String name = request.getParameter("disName");
			DistrictModel districtModel = ad.getDistrictById(disId);
			DivisionModel divisionModel = ad.getDivisionById(divId);
			
			districtModel.setDistrictBanglaName(name);
			districtModel.setDivisionModel(divisionModel);
			
			ad.updateDistrict(districtModel);
			request.getRequestDispatcher("/view_district.jsp").forward(request, response);
			
		} else if(action.equalsIgnoreCase("addupa")) {
			request.setAttribute("page", request.getParameter("page"));
			int disId = Integer.parseInt(request.getParameter("disdd"));
			String name = request.getParameter("upaName");
			
			UpazillaModel upazillaModel = new UpazillaModel();
			upazillaModel.setUpazillaBangaName(name);
			upazillaModel.setDistrictModel(ad.getDistrictById(disId));
			
			ad.saveUpazilla(upazillaModel);
			
			request.getRequestDispatcher("/view_upazilla.jsp").forward(request, response);
			
		} else if(action.equalsIgnoreCase("updateupa")) {
			request.setAttribute("page", request.getParameter("page"));
			int upaId = Integer.parseInt(request.getParameter("upaId"));
			UpazillaModel upazillaModel = ad.getUpazillaById(upaId);
			int dis = Integer.parseInt(request.getParameter("disdd"));
			DistrictModel districtModel = ad.getDistrictById(dis);
			String name = request.getParameter("upaName");
			
			upazillaModel.setUpazillaBangaName(name);
			upazillaModel.setDistrictModel(districtModel);
			
			ad.updateUpazilla(upazillaModel);
			
			request.getRequestDispatcher("/view_upazilla.jsp").forward(request, response);
		} else if(action.equalsIgnoreCase("adduni")) {
			request.setAttribute("page", request.getParameter("page"));
			String name = request.getParameter("uniName");
			int upaId = Integer.parseInt(request.getParameter("upadd"));
			UpazillaModel upazillaModel = ad.getUpazillaById(upaId);
			
			UnionModel unionModel = new UnionModel();
			unionModel.setUnionBanglaName(name);
			unionModel.setUpazillaModel(upazillaModel);
			
			ad.saveUnion(unionModel);
			
			request.getRequestDispatcher("/view_union.jsp").forward(request, response);
		} else if(action.equalsIgnoreCase("updateuni")) {
			request.setAttribute("page", request.getParameter("page"));
			String name = request.getParameter("uniName");
			int uniId = Integer.parseInt(request.getParameter("uniId"));
			int upaId = Integer.parseInt(request.getParameter("upadd"));
			UpazillaModel upazillaModel = ad.getUpazillaById(upaId);
			
			UnionModel unionModel = ad.getUnionById(uniId);
			unionModel.setUnionBanglaName(name);
			unionModel.setUpazillaModel(upazillaModel);
			
			ad.updateUnion(unionModel);
			
			request.getRequestDispatcher("/view_union.jsp").forward(request, response);
		}
	}

}
