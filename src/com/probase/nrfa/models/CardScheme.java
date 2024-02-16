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
  
@Entity
@Table(name="cardschemes")  
public class CardScheme  implements Serializable{  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id; 
	@Column(nullable = false)
	String schemeName;
	@Column(nullable = false)
	Double overrideTransactionFee;
	@Column(nullable = false)
	Double overrideFixedFee;
	@Column(nullable = false)
	String schemeCode;
	@Column(nullable = false)
	Date created_at;
	Date updated_at;
	Date deleted_at;
	String schemeDetail;
	@Column(nullable = false)
	Boolean cardSchemeStatus;
	
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

	public Double getOverrideTransactionFee() {
		return overrideTransactionFee;
	}

	public void setOverrideTransactionFee(Double overrideTransactionFee) {
		this.overrideTransactionFee = overrideTransactionFee;
	}

	public Double getOverrideFixedFee() {
		return overrideFixedFee;
	}

	public void setOverrideFixedFee(Double overrideFixedFee) {
		this.overrideFixedFee = overrideFixedFee;
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

	public Boolean getCardSchemeStatus() {
		return cardSchemeStatus;
	}

	public void setCardSchemeStatus(Boolean cardSchemeStatus) {
		this.cardSchemeStatus = cardSchemeStatus;
	}
}
