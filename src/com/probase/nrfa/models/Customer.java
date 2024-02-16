package com.probase.nrfa.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;  
import javax.persistence.Entity;  
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;  
import javax.persistence.GenerationType;
import javax.persistence.Id;  
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Enumerated;

import com.probase.nrfa.enumerations.CardType;
import com.probase.nrfa.enumerations.CustomerStatus;
import com.probase.nrfa.enumerations.CustomerType;
  
@Entity
@Table(name="customers")  
public class Customer implements Serializable {  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@Column(nullable = false)
	String verificationNumber;
	String firstName;
	String lastName;
	String otherName;
	String carPlateNumber;
	@OneToOne  
    @JoinColumn
	User user;
	@Column(nullable = false)
	String contactMobile;
	String altContactMobile1;
	String altContactMobile2;
	String contactEmail;
	CustomerStatus status;
	@Column(nullable = false)
	Date created_at;
	Date updated_at;
	Date deleted_at;
	@Column(nullable = false)
	CustomerType customerType;
	
	@PrePersist
	protected void onCreate() {
		this.created_at = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_at = new Date();
	}

	
	
	@Enumerated(EnumType.STRING)
	public CustomerStatus getStatus() {
	    return status;
	}
	
	@Enumerated(EnumType.STRING)
	public CustomerType getCustomerType() {
		return customerType;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVerificationNumber() {
		return verificationNumber;
	}

	public void setVerificationNumber(String verificationNumber) {
		this.verificationNumber = verificationNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public Date getDeleted_at() {
		return deleted_at;
	}

	public void setDeleted_at(Date deleted_at) {
		this.deleted_at = deleted_at;
	}
	
	public void setStatus(CustomerStatus status) {
		this.status = status;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public String getCarPlateNumber() {
		return carPlateNumber;
	}

	public void setCarPlateNumber(String carPlateNumber) {
		this.carPlateNumber = carPlateNumber;
	}

	public String getAltContactMobile1() {
		return altContactMobile1;
	}

	public void setAltContactMobile1(String altContactMobile1) {
		this.altContactMobile1 = altContactMobile1;
	}

	public String getAltContactMobile2() {
		return altContactMobile2;
	}

	public void setAltContactMobile2(String altContactMobile2) {
		this.altContactMobile2 = altContactMobile2;
	}
}
