/*
 * This Model refelects the Product table in the database.
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product")

public class ProductModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int productId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "product_description")
	private String productDescription;

	
	@Column(name = "product_price")
	private String productPrice;
	
	@Column(name = "government_price")
	private String governmentPrice;
	
	@Column(name = "stock")
	private double productStock;
	
	//Many products can have one unit.
	@ManyToOne(cascade = CascadeType.MERGE)
	private UnitModel productUnit;
	
	@Column(name = "product_type")
	private String type;
	
	@Lob
    @Column(name="product_image", nullable=false, columnDefinition="mediumblob")
    private byte[] image;
	
	
	public UnitModel getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(UnitModel productUnit) {
		this.productUnit = productUnit;
	}
	
	
	
	
	public double getProductStock() {
		return productStock;
	}
	public void setProductStock(double productStock) {
		this.productStock = productStock;
	}
	public String getGovernmentPrice() {
		return governmentPrice;
	}
	public void setGovernmentPrice(String governmentPrice) {
		this.governmentPrice = governmentPrice;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}

	@ManyToOne(cascade = CascadeType.MERGE)
	private CategoryModel productCategory;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private SubcategoryModel productSubcategory;
	
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescriptionString) {
		this.productDescription = productDescriptionString;
	}
	
	public CategoryModel getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(CategoryModel productCategory) {
		this.productCategory = productCategory;
	}
	public SubcategoryModel getProductSubcategory() {
		return productSubcategory;
	}
	public void setProductSubcategory(SubcategoryModel productSubcategory) {
		this.productSubcategory = productSubcategory;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
}
