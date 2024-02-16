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

import com.probase.nrfa.enumerations.DeviceStatus;
import com.probase.nrfa.enumerations.PaymentMeans;
import com.probase.nrfa.enumerations.RequestType;
  
@Entity
@Table(name="audit_trail")  
public class AuditTrail implements Serializable {  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@Column(nullable = false)
	String ip_address;
	@Column(nullable = false)
	RequestType requestType;
	String requestId;
	String username;
	Long primaryObjectIdHandled;
	String primaryObjectType;

	@Column(nullable = false)
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

	@Enumerated(EnumType.STRING)
	public RequestType getRequestType() {
	    return requestType;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getPrimaryObjectIdHandled() {
		return primaryObjectIdHandled;
	}

	public void setPrimaryObjectIdHandled(Long primaryObjectIdHandled) {
		this.primaryObjectIdHandled = primaryObjectIdHandled;
	}

	public String getPrimaryObjectType() {
		return primaryObjectType;
	}

	public void setPrimaryObjectType(String primaryObjectType) {
		this.primaryObjectType = primaryObjectType;
	}
}
