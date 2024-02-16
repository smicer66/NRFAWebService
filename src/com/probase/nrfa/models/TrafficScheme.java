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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Enumerated;

import com.probase.nrfa.enumerations.DeviceType;
import com.probase.nrfa.enumerations.NFRACurrency;
  
@Entity
@Table(name="trafficschemes")  
public class TrafficScheme  implements Serializable{  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id; 
	@Column(nullable = false)
	String schemeName;
	String schemeDetail;
	@Column(nullable = false)
	Double schemeAmount;
	@Column(nullable = false)
	String schemeCode;
	@Column(nullable = false)
	Date created_at;
	Date updated_at;
	Date deleted_at;
	@Column(nullable = false)
	Boolean trafficSchemeStatus;
	@Column(nullable = false)
	NFRACurrency currency;
	
	
	@PrePersist
	protected void onCreate() {
		this.created_at = new Date();
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

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
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

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public String getSchemeDetail() {
		return schemeDetail;
	}

	public void setSchemeDetail(String schemeDetail) {
		this.schemeDetail = schemeDetail;
	}

	public Double getSchemeAmount() {
		return schemeAmount;
	}

	public void setSchemeAmount(Double schemeAmount) {
		this.schemeAmount = schemeAmount;
	}

	public Boolean getTrafficSchemeStatus() {
		return trafficSchemeStatus;
	}

	public void setTrafficSchemeStatus(Boolean trafficSchemeStatus) {
		this.trafficSchemeStatus = trafficSchemeStatus;
	}
	
	@Enumerated(EnumType.STRING)
	public NFRACurrency getCurrency() {
	    return currency;
	}
	
	
	public void setCurrency(NFRACurrency currency) {
	    this.currency = currency;
	}
}
