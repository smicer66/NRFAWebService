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
@Table(name="banks")  
public class Bank  implements Serializable {  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id; 
	@Column(nullable = false)
	String bankName;
	@Column(nullable = false)
	String bankCode;
	String onlineBankingURL;
	String access_exodus;
	Date created_at;
	Date updated_at;
	Date deleted_at;
	
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getOnlineBankingURL() {
		return onlineBankingURL;
	}

	public void setOnlineBankingURL(String onlineBankingURL) {
		this.onlineBankingURL = onlineBankingURL;
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

	public String getAccess_exodus() {
		return access_exodus;
	}

	public void setAccess_exodus(String access_exodus) {
		this.access_exodus = access_exodus;
	}
}
