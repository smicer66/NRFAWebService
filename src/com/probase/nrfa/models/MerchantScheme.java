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
import javax.persistence.Table;
import javax.persistence.Enumerated;
  
@Entity
@Table(name="merchantschemes")
public class MerchantScheme implements Serializable{

	private static final long serialVersionUID = 5332171426343295970L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;  
	@Column(nullable = false)
	String schemename;
	@Column(nullable = false)
	String schemecode;
	@Column(nullable = false)
	Double transactionPercentage;
	@Column(nullable = false)
	Double fixedCharge;
	@Column(nullable = false)
	Date created_at;
	Date updated_at;
	Date deleted_at;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSchemename() {
		return schemename;
	}
	public void setSchemename(String schemename) {
		this.schemename = schemename;
	}
	public Double getTransactionPercentage() {
		return transactionPercentage;
	}
	public void setTransactionPercentage(Double transactionPercentage) {
		this.transactionPercentage = transactionPercentage;
	}
	public Double getFixedCharge() {
		return fixedCharge;
	}
	public void setFixedCharge(Double fixedCharge) {
		this.fixedCharge = fixedCharge;
	}
	public String getSchemecode() {
		return schemecode;
	}
	public void setSchemecode(String schemecode) {
		this.schemecode = schemecode;
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
	
	
}
