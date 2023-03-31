/*
 * This Model refelects the Seller Products table in the database.
 * All the properties, fucntions, getters, setters, realation between entities and constructors are defined here.
 */
package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sellers_product")
public class SellersProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int productId;
	
	@Column(name = "product_quantity")
	private double productQuantity;
	
	@Column(name = "product_name")
	private String productName;
	
	// One category can have many products.
	@ManyToOne(cascade = CascadeType.MERGE)
	private CategoryModel categoryModel;
	
	// One subcategory can have many products.
	@ManyToOne(cascade = CascadeType.MERGE)
	private SubcategoryModel subcategoryModel;
	
	@Column(name = "product_description")
	private String productDescription;
	
	@Lob
    @Column(name="category_image", nullable=false, columnDefinition="mediumblob")
    private byte[] image;
	
	@Column(name = "product_price")
	private double productPrice;
	
	// One unit can have many products.
	@ManyToOne(cascade = CascadeType.MERGE)
	private UnitModel unitModel;

	// One seller can have many products.
	@ManyToOne(cascade = CascadeType.MERGE)
	private SellerModel sellerModel;
	

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public double getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(double productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public CategoryModel getCategoryModel() {
		return categoryModel;
	}

	public void setCategoryModel(CategoryModel categoryModel) {
		this.categoryModel = categoryModel;
	}

	public SubcategoryModel getSubcategoryModel() {
		return subcategoryModel;
	}

	public void setSubcategoryModel(SubcategoryModel subcategoryModel) {
		this.subcategoryModel = subcategoryModel;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public UnitModel getUnitModel() {
		return unitModel;
	}

	public void setUnitModel(UnitModel unitModel) {
		this.unitModel = unitModel;
	}

	public SellerModel getSellerModel() {
		return sellerModel;
	}

	public void setSellerModel(SellerModel sellerModel) {
		this.sellerModel = sellerModel;
	}


	
	
}
