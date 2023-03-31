/*
 * This Model defines the functions for all the search related operations to the database..
 * The return parameters are validated and tested before returning.
 */
package dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import model.CategoryModel;
import model.ProductModel;
import model.SellersProduct;

public class SearchDao {
	int counter = 0;

	// Returns all the products by the keyword and category id
	public List<ProductModel> getProductByKeywordAndCategoryId(String keyword, int catId) {
		// TODO Auto-generated method stub
		String query = "from ProductModel product where product.productCategory=" + catId
				+ "and product.productName like '%" + keyword + "%'";

		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery(query);
		List<ProductModel> productModels = queryExecuteable.list();

		session.flush();
		session.close();
		con.closeSessionFactory();
		return productModels;
	}

	// Returns all the categories by the keyword and category id
	public List<SellersProduct> getSellerProductByKeywordAndCategoryId(String keyword, int catId) {
		// TODO Auto-generated method stub
		String query = "from SellersProduct sellersProduct where sellersProduct.categoryModel=" + catId
				+ " and sellersProduct.productName like '%" + keyword + "%'";

		
		Connection con = new Connection();
		Session session = con.getSessionFactory().openSession();

		Query queryExecuteable = session.createQuery(query);
		List<SellersProduct> productModels = queryExecuteable.list();

		session.flush();
		session.close();
		con.closeSessionFactory();
		return productModels;
	}
}
