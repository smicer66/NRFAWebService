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

import com.probase.nrfa.enumerations.AccountStatus;
import com.probase.nrfa.enumerations.AccountType;
  
@Entity
@Table(name="traffic_exceptions")  
public class TrafficException implements Serializable {  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id; 
	String vehicleRegNo;
	@Column(nullable = false)
	String carOwnerDetails;
	@Column(nullable = false)
	Double discountPercentageApplicable;
	@Column(nullable = false)
	String routeCode;
	@Column(nullable = false)
	Date created_at;
	Date updated_at;
	Date deleted_at;
	Long ecardId;
	String userMobileNo;
	String userFullName;
	@OneToOne  
    @JoinColumn
	TrafficScheme trafficScheme;
	@OneToOne  
    @JoinColumn
	User setupByUser;
	
	
	

	@PrePersist
	protected void onCreate() {
		this.created_at = new Date();
		this.updated_at = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_at = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	

	public Double getDiscountPercentageApplicable() {
		return discountPercentageApplicable;
	}

	public void setDiscountPercentageApplicable(Double discountPercentageApplicable) {
		this.discountPercentageApplicable = discountPercentageApplicable;
	}

	public String getVehicleRegNo() {
		return vehicleRegNo;
	}

	public void setVehicleRegNo(String vehicleRegNo) {
		this.vehicleRegNo = vehicleRegNo;
	}

	public String getCarOwnerDetails() {
		return carOwnerDetails;
	}

	public void setCarOwnerDetails(String carOwnerDetails) {
		this.carOwnerDetails = carOwnerDetails;
	}

	public Long getEcardId() {
		return ecardId;
	}

	public void setEcardId(Long ecardId) {
		this.ecardId = ecardId;
	}

	public String getUserMobileNo() {
		return userMobileNo;
	}

	public void setUserMobileNo(String userMobileNo) {
		this.userMobileNo = userMobileNo;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}


	public TrafficScheme getTrafficScheme() {
		return trafficScheme;
	}

	public void setTrafficScheme(TrafficScheme trafficScheme) {
		this.trafficScheme = trafficScheme;
	}

	public User getSetupByUser() {
		return setupByUser;
	}

	public void setSetupByUser(User setupByUser) {
		this.setupByUser = setupByUser;
	}
	
}
