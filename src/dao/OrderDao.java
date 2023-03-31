/*
 * This Model defines the functions for all the order related operations to the database..
 * The return parameters are validated and tested before returning.
 */
package dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import model.CustomerModel;
import model.DeliveryPersonModel;
import model.OrderSellerProductModel;
import model.OrdersModel;
import model.SellersProduct;

public class OrderDao {
	int counter=0;

	// Returns all the new orders for the delivery person which are not delivered yet by the delivery person id
	public ArrayList<OrdersModel> getNewOrdersForDeliveryManByDelId(int delId) {
		// TODO Auto-generated method stub
		
			String query = "from OrdersModel ordersModel";
			Connection con = new Connection();
			Session session = con.getSessionFactory().openSession();

			Query queryExecuteable = session.createQuery(query);
			List<OrdersModel> ordersModels=new ArrayList<>();
			ordersModels = queryExecuteable.list();

			java.util.Iterator<OrdersModel> it = ordersModels.iterator();

			List<OrdersModel> ordersModels2 = new ArrayList<OrdersModel>();
			
			while (it.hasNext()) {
				Object type = (Object) it.next();

				OrdersModel sub =  (OrdersModel) type;
				
				if (sub.getDeliveryPersonModel() != null) {
					if (sub.getDeliveryPersonModel().getDeliveryPersonId() == delId) {
						ordersModels2.add(sub);
						
						System.out.println("Union Id = " + sub.getDeliveryPersonModel().getDeliveryPersonId());
					}
				}

			}
			

			session.flush();
			session.close();
			con.closeSessionFactory();
			return (ArrayList<OrdersModel>) ordersModels2;
		//	return cartDetailsModels;
		}

	// This function is used to get the Delivery Person details by the phone number
	public DeliveryPersonModel getDeliveryPersonPasswordByPhone(String phone) {
		// TODO Auto-generated method stub
		String query = "from DeliveryPersonModel deliverer where deliverer.deliveryPersonPhone='"+phone+"'";
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		//Query queryExecuteable = session.createQuery(query);
		Query queryExecuteable = session.createQuery(query);
		DeliveryPersonModel deliveryPersonModel = null;
		try {
			deliveryPersonModel = (DeliveryPersonModel) queryExecuteable.list().get(0);
		} catch (Exception e) {
		System.out.println("Problem with login...");
		}
		//SignupModel signupModel = (SignupModel) queryExecuteable.list().get(0);
		System.out.println(deliveryPersonModel);
		session.flush();
		session.close();
		con.closeSessionFactory();
		return deliveryPersonModel;
	}

	// Returns the order details by the transaction id
	public OrdersModel getOrderByTransactionId(String tranId) {
		// TODO Auto-generated method stub
		String query = "from OrdersModel ordersModel where ordersModel.tranId = '" + tranId + "'";
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery(query);
		OrdersModel ordersModels = new OrdersModel();
		ordersModels = (OrdersModel) queryExecuteable.list().get(0);
		
		session.flush();
		session.close();
		con.closeSessionFactory();
		return ordersModels;
	}

	// Returns the list of cart order details by the transaction id
	public List<OrdersModel> getCartOrderListByTransactionId(String tranId) {
		// TODO Auto-generated method stub
		String query = "from OrdersModel ordersModel where ordersModel.tranId = '" + tranId + "'";
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery(query);
		List<OrdersModel> ordersModels = new ArrayList<OrdersModel>();
		ordersModels = queryExecuteable.list();
		
		session.flush();
		session.close();
		con.closeSessionFactory();
		return ordersModels;
	}

	// Returns the seller product details by the transaction id
	public OrderSellerProductModel getOrderSellerProductByTransactionId(String tranId) {
		// TODO Auto-generated method stub
		String query = "from OrderSellerProductModel ordersModel where ordersModel.tranId = '" + tranId + "'";
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery(query);
		OrderSellerProductModel ordersModels = new OrderSellerProductModel();
		ordersModels = (OrderSellerProductModel) queryExecuteable.list().get(0);
		
		session.flush();
		session.close();
		con.closeSessionFactory();
		return ordersModels;
	}

	// Returns the new orders for seller product that are not delivered yet by the delivery person id
	public ArrayList<OrderSellerProductModel> getNewOrdersFromSellerForDeliveryManByDelId(int delId) {
		// TODO Auto-generated method stub
		String query = "from OrderSellerProductModel ordersModel";
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery(query);
		List<OrderSellerProductModel> ordersModels=new ArrayList<>();
		ordersModels = queryExecuteable.list();

		java.util.Iterator<OrderSellerProductModel> it = ordersModels.iterator();

		List<OrderSellerProductModel> ordersModels2 = new ArrayList<OrderSellerProductModel>();
		
		while (it.hasNext()) {
			Object type = (Object) it.next();

			OrderSellerProductModel sub =  (OrderSellerProductModel) type;
			
			if (sub.getDeliveryPersonModel() != null) {
				if (sub.getDeliveryPersonModel().getDeliveryPersonId() == delId) {
					ordersModels2.add(sub);
					
				}
			}

		}
		

		session.flush();
		session.close();
		con.closeSessionFactory();
		return (ArrayList<OrderSellerProductModel>) ordersModels2;
	}
		
}
