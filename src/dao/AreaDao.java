/*
 * This Model defines the functions for all the area related operations to the database..
 * The return parameters are validated and tested before returning.
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale.Category;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.ietf.jgss.Oid;

import dao.Connection;
import javassist.bytecode.Descriptor.Iterator;
import model.BidModel;
import model.CartDetailsModel;
import model.CartModel;
import model.CategoryModel;
import model.CustomerModel;
import model.DeliveryPersonModel;
import model.DistrictModel;
import model.DivisionModel;
import model.OrdersModel;
import model.ProductModel;
import model.RecommendationModel;
import model.SellerModel;
import model.SubcategoryModel;
import model.UnionModel;
import model.UnitModel;
import model.UpazillaModel;
public class AreaDao {
	int counter=0;
	
	// This function returns all the district by division id. 
	public List<DistrictModel> getDistrictByDivisionId(int divId) {
		// TODO Auto-generated method stub
		String query = "from DistrictModel DistrictModel";
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery(query);
		List<DistrictModel> districtModels=new ArrayList<>();
		districtModels = queryExecuteable.list();

		java.util.Iterator<DistrictModel> it = districtModels.iterator();

		List<DistrictModel> districtModels2 = new ArrayList<DistrictModel>();
		
		while (it.hasNext()) {
			Object type = (Object) it.next();

			DistrictModel sub =  (DistrictModel) type;
			if (sub.getDivisionModel().getDivisionId() == divId) {
				districtModels2.add(sub);
				
				System.out.println(districtModels);
			}

		}
		

		session.flush();
		session.close();
		con.closeSessionFactory();
		return districtModels2;
	//	return cartDetailsModels;
	}
	
	// This function returns all the upazillas by district id.
	public List<UpazillaModel> getUpazillaByDistrictId(int disId) {
		// TODO Auto-generated method stub
		String query = "from UpazillaModel UpazillaModel";
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery(query);
		List<UpazillaModel> upazillaModels=new ArrayList<>();
		upazillaModels = queryExecuteable.list();

		java.util.Iterator<UpazillaModel> it = upazillaModels.iterator();

		List<UpazillaModel> upazillaModels2 = new ArrayList<UpazillaModel>();
		
		while (it.hasNext()) {
			Object type = (Object) it.next();

			UpazillaModel sub =  (UpazillaModel) type;
			if (sub.getDistrictModel().getDistrictId() == disId) {
				upazillaModels2.add(sub);
				
				System.out.println(upazillaModels);
			}

		}
		

		session.flush();
		session.close();
		con.closeSessionFactory();
		return upazillaModels2;
	//	return cartDetailsModels;
	}
	
	// This function returns all the unions by upazilla id.
	public List<UnionModel> getUnionByUpazillaId(int upaId) {
		// TODO Auto-generated method stub
		String query = "from UnionModel UnionModel";
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery(query);
		List<UnionModel> unionModels=new ArrayList<>();
		unionModels = queryExecuteable.list();

		java.util.Iterator<UnionModel> it = unionModels.iterator();

		List<UnionModel> unionModels2 = new ArrayList<UnionModel>();
		System.out.println("Upazilla Id "+ upaId);
		while (it.hasNext()) {
			Object type = (Object) it.next();
			
			UnionModel sub =  (UnionModel) type;
			
			if (sub.getUpazillaModel().getUpazillaId() == upaId) {
				unionModels2.add(sub);
				System.out.println(sub.getUnionId());
				
			}

		}

		session.flush();
		session.close();
		System.out.println(unionModels2);
		con.closeSessionFactory();
		return unionModels2;
	//	return cartDetailsModels;
	}

	// Returns the division by id.
	public DivisionModel getDivisionById(int id) {
		// TODO Auto-generated method stub
		String query = "from DivisionModel division where division.id=" + id;
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery(query);
		DivisionModel divisionModel = (DivisionModel) queryExecuteable.list().get(0);
		con.closeSessionFactory();
		return divisionModel;
	}
	]
	// Returns all the districts.
	public List<DistrictModel> getAllDistricts() {
		List<DistrictModel> districtModels = new ArrayList<>();
		String query = "from DistrictModel district";
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery("from DistrictModel district");
		districtModels = queryExecuteable.list();
		session.flush();
		session.close();
		
		con.closeSessionFactory();
		return districtModels;
	}
	
	// Returns all the Upazillas.
	public List<UpazillaModel> getAllUpazillas() {
		List<UpazillaModel> upazillaModels = new ArrayList<>();
		String query = "from UpazillaModel upazilla";
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery("from UpazillaModel upazilla");
		upazillaModels = queryExecuteable.list();
		session.flush();
		session.close();
		con.closeSessionFactory();
		return upazillaModels;
	}
	
	// Returns all the unions.
	public List<UnionModel> getAllUnions() {
		List<UnionModel> unionModels = new ArrayList<>();
		String query = "from UnionModel unionModels";
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery("from UnionModel unionModels");
		unionModels = queryExecuteable.list();
		session.flush();
		session.close();
		con.closeSessionFactory();
		return unionModels;
	}

	// Returns the district by id.
	public DistrictModel getDistrictById(int disId) {
		// TODO Auto-generated method stub
		String query = "from DistrictModel district where district.id=" + disId;
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery(query);
		DistrictModel districtModel = (DistrictModel) queryExecuteable.list().get(0);

		session.flush();
		session.close();
		con.closeSessionFactory();
		return districtModel;
	}

	// Returns the upazilla by id.
	public UpazillaModel getUpazillaById(int upaId) {
		// TODO Auto-generated method stub
		String query = "from UpazillaModel upazilla where upazilla.id=" + upaId;
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery(query);
		UpazillaModel upazillaModel = (UpazillaModel) queryExecuteable.list().get(0);

		session.flush();
		session.close();
		con.closeSessionFactory();
		return upazillaModel;
	}

	// Returns the union by id.
	public UnionModel getUnionById(int uniId) {
		// TODO Auto-generated method stub
		String query = "from UnionModel upazilla where upazilla.id=" + uniId;
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery(query);
		UnionModel upazillaModel = (UnionModel) queryExecuteable.list().get(0);

		session.flush();
		session.close();
		con.closeSessionFactory();
		return upazillaModel;
	}

	// Returns the count of divisions.
	public int countDivision() {
		// TODO Auto-generated method stub
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		int count = ((Long)session.createQuery("select count(*) from DivisionModel").uniqueResult()).intValue();
		
		System.out.println(count);

		session.flush();
		session.close();
		con.closeSessionFactory();
		return count;
	}

	// Saves the division.
	public void saveDivision(DivisionModel divisionModel) {
		// TODO Auto-generated method stub
		Connection con = new Connection();

		Session session = con.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.save(divisionModel);
		transaction.commit();
		
		session.flush();
		session.close();
		
		con.closeSessionFactory();
		System.out.println("Inserted...");
	}

	// Returns the last division id.
	public int getLastDivId() {
		// TODO Auto-generated method stub
		
		
		
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query query = session.createQuery("from DivisionModel ORDER BY divisionId DESC");
		query.setMaxResults(1);
		DivisionModel last = (DivisionModel) query.uniqueResult();

		session.flush();
		session.close();
		con.closeSessionFactory();
		return last.getDivisionId();
	}


	// Deletes the division.
	public void deleteDivision(DivisionModel divisionModel) {
		// TODO Auto-generated method stub
		Connection con = new Connection();

		Session session = con.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.delete(divisionModel);
		transaction.commit();
		session.flush();
		session.close();
		con.closeSessionFactory();
		System.out.println("Deleted...");
	}

	// Updates the division.
	public void updateDivision(DivisionModel divisionModel) {
		// TODO Auto-generated method stub
		Connection con = new Connection();

		Session session = con.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.update(divisionModel);
		transaction.commit();
		session.flush();
		session.close();
		con.closeSessionFactory();
		System.out.println("Updated...");
	}

	//deletes the district
	public void deleteDistrict(DistrictModel districtModel) {
		// TODO Auto-generated method stub
		Connection con = new Connection();

		Session session = con.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.delete(districtModel);
		transaction.commit();
		session.flush();
		session.close();
		con.closeSessionFactory();
		System.out.println("Deleted...");
	}

	//save the district
	public void saveDistrict(DistrictModel districtModel) {
		// TODO Auto-generated method stub
		Connection con = new Connection();

		Session session = con.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.save(districtModel);
		transaction.commit();
		
		session.flush();
		session.close();
		
		con.closeSessionFactory();
		System.out.println("Inserted...");
	}

	//update the district
	public void updateDistrict(DistrictModel districtModel) {
		// TODO Auto-generated method stub
		Connection con = new Connection();

		Session session = con.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.update(districtModel);
		transaction.commit();
		session.flush();
		session.close();
		con.closeSessionFactory();
		System.out.println("Updated...");
	}

	//save the upazilla
	public void saveUpazilla(UpazillaModel upazillaModel) {
		// TODO Auto-generated method stub
		Connection con = new Connection();

		Session session = con.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.save(upazillaModel);
		transaction.commit();
		
		session.flush();
		session.close();
		
		con.closeSessionFactory();
		System.out.println("Inserted...");
	}

	//delete the upazilla
	public void deleteUpazilla(UpazillaModel upazillaModel) {
		// TODO Auto-generated method stub
		Connection con = new Connection();

		Session session = con.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.delete(upazillaModel);
		transaction.commit();
		session.flush();
		session.close();
		con.closeSessionFactory();
		System.out.println("Deleted...");
	}

	//update the upazilla
	public void updateUpazilla(UpazillaModel upazillaModel) {
		// TODO Auto-generated method stub
		Connection con = new Connection();

		Session session = con.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.update(upazillaModel);
		transaction.commit();
		session.flush();
		session.close();
		con.closeSessionFactory();
		System.out.println("Updated...");
	}

	//save the union
	public void saveUnion(UnionModel unionModel) {
		// TODO Auto-generated method stub
		Connection con = new Connection();

		Session session = con.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.save(unionModel);
		transaction.commit();
		
		session.flush();
		session.close();
		
		con.closeSessionFactory();
		System.out.println("Inserted...");
	}

	//delete the union
	public void deleteUnion(UnionModel unionModel) {
		// TODO Auto-generated method stub
		Connection con = new Connection();

		Session session = con.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.delete(unionModel);
		transaction.commit();
		session.flush();
		session.close();
		con.closeSessionFactory();
		System.out.println("Deleted...");
	}

	//update the union
	public void updateUnion(UnionModel unionModel) {
		// TODO Auto-generated method stub
		Connection con = new Connection();

		Session session = con.getSessionFactory().openSession();

		Transaction transaction = session.beginTransaction();
		session.update(unionModel);
		transaction.commit();
		session.flush();
		session.close();
		con.closeSessionFactory();
		System.out.println("Updated...");
	}
}
