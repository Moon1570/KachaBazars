/*
 * This Model refelects the Seller table in the database.
 * All the properties, fucntions, getters, setters, realation between entities and constructors are defined here.
 */
package model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "sellers")
public class SellerModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seller_id")
	private int sellerId;
	
	@Column(name = "seller_first_name")
	private String sellerFirstName;
	
	@Column(name = "seller_last_name")
	private String sellerLastName;
	
	// Many sellers can be from one division.
	@ManyToOne(cascade = CascadeType.MERGE)
	private DivisionModel divisionmodel;
	
	// Many sellers can be from one district.
	@ManyToOne(cascade = CascadeType.MERGE)
	private DistrictModel districtModel;
	
	// Many sellers can be from one upazilla.
	@ManyToOne(cascade = CascadeType.MERGE)
	private UpazillaModel upazillaModel;
	
	// Many sellers can be from one union.
	@ManyToOne(cascade = CascadeType.MERGE)
	private UnionModel unionModel;
	
	@Column(name = "village")
	private String customerVillage;
	
	@Column(name = "street")
	private String customerStreet;
	
	@Column(name = "holding_number")
	private String customerHoldingNumber;
	
	@Column(name = "seller_phone")
	private String sellerPhone;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "seller_dob")
	private Date sellerDOB;
	
	@Lob
    @Column(name="seller_image", nullable=false, columnDefinition="mediumblob")
    private byte[] image;
	
	@Column(name = "nid")
	private String sellerNID;
	
	@Column(name = "seller_gender")
	private String sellerGender;
	
	@Column(name = "seller_password")
	private String sellerPassword;

	
	
	public String getSellerPassword() {
		return sellerPassword;
	}

	public void setSellerPassword(String sellerPassword) {
		this.sellerPassword = sellerPassword;
	}

	public String getSellerNID() {
		return sellerNID;
	}

	public void setSellerNID(String sellerNID) {
		this.sellerNID = sellerNID;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerFirstName() {
		return sellerFirstName;
	}

	public void setSellerFirstName(String sellerFirstName) {
		this.sellerFirstName = sellerFirstName;
	}

	public String getSellerLastName() {
		return sellerLastName;
	}

	public void setSellerLastName(String sellerLastName) {
		this.sellerLastName = sellerLastName;
	}

	public DivisionModel getDivisionmodel() {
		return divisionmodel;
	}

	public void setDivisionmodel(DivisionModel divisionmodel) {
		this.divisionmodel = divisionmodel;
	}

	public DistrictModel getDistrictModel() {
		return districtModel;
	}

	public void setDistrictModel(DistrictModel districtModel) {
		this.districtModel = districtModel;
	}

	public UpazillaModel getUpazillaModel() {
		return upazillaModel;
	}

	public void setUpazillaModel(UpazillaModel upazillaModel) {
		this.upazillaModel = upazillaModel;
	}

	public UnionModel getUnionModel() {
		return unionModel;
	}

	public void setUnionModel(UnionModel unionModel) {
		this.unionModel = unionModel;
	}

	public String getCustomerVillage() {
		return customerVillage;
	}

	public void setCustomerVillage(String customerVillage) {
		this.customerVillage = customerVillage;
	}

	public String getCustomerStreet() {
		return customerStreet;
	}

	public void setCustomerStreet(String customerStreet) {
		this.customerStreet = customerStreet;
	}

	public String getCustomerHoldingNumber() {
		return customerHoldingNumber;
	}

	public void setCustomerHoldingNumber(String customerHoldingNumber) {
		this.customerHoldingNumber = customerHoldingNumber;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	

	public Date getSellerDOB() {
		return sellerDOB;
	}

	public void setSellerDOB(Date sellerDOB) {
		this.sellerDOB = sellerDOB;
	}

	public String getSellerGender() {
		return sellerGender;
	}

	public void setSellerGender(String sellerGender) {
		this.sellerGender = sellerGender;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	
}
