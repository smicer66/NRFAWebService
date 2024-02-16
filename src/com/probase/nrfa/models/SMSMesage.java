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
@Table(name="sms_messages")  
public class SMSMesage  implements Serializable {  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id; 
	@Column(nullable = false)
	String receipentMobileNumber;
	@Column(nullable = false)
	String message;
	Integer responseCode;
	Date created_at;
	Date updated_at;
	Date deleted_at;
	String status;
	String dataResponse;
	
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

	public String getReceipentMobileNumber() {
		return receipentMobileNumber;
	}

	public void setReceipentMobileNumber(String receipentMobileNumber) {
		this.receipentMobileNumber = receipentMobileNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDataResponse() {
		return dataResponse;
	}

	public void setDataResponse(String dataResponse) {
		this.dataResponse = dataResponse;
	}
}
