/*
 * This Model refelects the  delivery person table in the database.
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

import org.hibernate.annotations.Type;
@Entity
@Table(name = "delivery_person")
public class DeliveryPersonModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_person_id")
	private int deliveryPersonId;
	
	@Column(name = "delivery_person_first_name")
	private String deliveryPersonFirstName;
	
	@Column(name = "delivery_person_last_name")
	private String deliveryPersonLastName;
	
	// Many delivery persons can be from one division.
	@ManyToOne(cascade = CascadeType.MERGE)
	private DivisionModel divisionmodel;
	
	// Many delivery persons can be from one district.
	@ManyToOne(cascade = CascadeType.MERGE)
	private DistrictModel districtModel;
	
	// Many delivery persons can be from one upazilla.
	@ManyToOne(cascade = CascadeType.MERGE)
	private UpazillaModel upazillaModel;

	// Many delivery persons can be from one union.
	@ManyToOne(cascade = CascadeType.MERGE)
	private UnionModel unionModel;
	
	@Column(name = "village")
	private String deliveryVillage;
	
	@Column(name = "street")
	private String delieryStreet;
	
	@Column(name = "holding_number")
	private String deliveryHoldingNumber;
	
	@Column(name = "delivery_person_phone")
	private String deliveryPersonPhone;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "delivery_person_dob")
	private Date deliveryPersonDOB;
	
	@Lob
    @Column(name="delivery_image", nullable=false, columnDefinition="mediumblob")
    private byte[] image;
	
	@Column(name = "delivery_person_nid")
	private String deliveryPersonNID;
	
	@Column(name = "delivery_person_pass")
	private String deliveryPersonPassword;
	
	@Column(name = "delivery_Person_gender")
	private String deliveryPersonGender;
	
	
	public String getDeliveryPersonGender() {
		return deliveryPersonGender;
	}

	public void setDeliveryPersonGender(String deliveryPersonGender) {
		this.deliveryPersonGender = deliveryPersonGender;
	}

	public String getDeliveryPersonNID() {
		return deliveryPersonNID;
	}

	public void setDeliveryPersonNID(String deliveryPersonNID) {
		this.deliveryPersonNID = deliveryPersonNID;
	}

	public int getDeliveryPersonId() {
		return deliveryPersonId;
	}

	public void setDeliveryPersonId(int deliveryPersonId) {
		this.deliveryPersonId = deliveryPersonId;
	}

	public String getDeliveryPersonFirstName() {
		return deliveryPersonFirstName;
	}

	public void setDeliveryPersonFirstName(String deliveryPersonFirstName) {
		this.deliveryPersonFirstName = deliveryPersonFirstName;
	}

	public String getDeliveryPersonLastName() {
		return deliveryPersonLastName;
	}

	public void setDeliveryPersonLastName(String deliveryPersonLastName) {
		this.deliveryPersonLastName = deliveryPersonLastName;
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

	

	public String getDeliveryVillage() {
		return deliveryVillage;
	}

	public void setDeliveryVillage(String deliveryVillage) {
		this.deliveryVillage = deliveryVillage;
	}

	public String getDelieryStreet() {
		return delieryStreet;
	}

	public void setDelieryStreet(String delieryStreet) {
		this.delieryStreet = delieryStreet;
	}

	public String getDeliveryHoldingNumber() {
		return deliveryHoldingNumber;
	}

	public void setDeliveryHoldingNumber(String deliveryHoldingNumber) {
		this.deliveryHoldingNumber = deliveryHoldingNumber;
	}

	public String getDeliveryPersonPhone() {
		return deliveryPersonPhone;
	}

	public void setDeliveryPersonPhone(String deliveryPersonPhone) {
		this.deliveryPersonPhone = deliveryPersonPhone;
	}

	

	public Date getDeliveryPersonDOB() {
		return deliveryPersonDOB;
	}

	public void setDeliveryPersonDOB(Date deliveryPersonDOB) {
		this.deliveryPersonDOB = deliveryPersonDOB;
	}

	

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getDeliveryPersonPassword() {
		return deliveryPersonPassword;
	}

	public void setDeliveryPersonPassword(String deliveryPersonPassword) {
		this.deliveryPersonPassword = deliveryPersonPassword;
	}
}
