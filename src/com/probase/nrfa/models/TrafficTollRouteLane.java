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
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Enumerated;

import com.probase.nrfa.enumerations.Channel;
import com.probase.nrfa.enumerations.ServiceType;
import com.probase.nrfa.enumerations.TransactionStatus;
  
@Entity
@Table(name="traffic_toll_routes_lanes")  
public class TrafficTollRouteLane implements Serializable{  
	private static final long serialVersionUID = -6700990760991590860L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@Column(nullable = false)
	String startRouteName; 
	@Column(nullable = false)
	String endRouteName;
	@Column(nullable = false)
	Integer tollLane;
	@Column(nullable = false)
	Boolean status;
	@Column(nullable = false)
	String routeCode;
	@Column(nullable = false)
	Date created_at;
	Date updated_at;
	Date deleted_at;
	
	//Customer Deposit Cash Into Card Via Merchant
		//OnUs:		CR CardDistributorAcct
				//	CR Card
		//NotOnUs:	CR CardDistributorAcct 	DR BankHoldingAccount B2
				//	CR Card
	//Customer Deposit Cash Into Card Via Distributor
		//OnUs:		CR CardDistributorAcct	DR DistributorAccount B1
				//	CR Card
		//NotOnUs:	CR CardDistributorAcct	DR BankHoldingAccount B2
				//	CR Card
	//Distributor Deposit Cash Into Distributor Account
		//OnUs:		CR DistributorAcct
	//Distributor Deposit Cash Into General Pool Account
		//OnUs:		CR BankPoolAcct;
	//Card Debit At Toll 
		//			DR Card
		//IND1		DR CardDistributorAcct	CR BankCollectionAccount B1
		//IND2		DR BankPoolAcct			CR BankCollectionAccount B1
		//COR1		DR CardDistributorAcct	CR BankCollectionAccount B1
		//COR2		DR BankPoolAcct			CR BankCollectionAccount B1
		//MER1		DR CardMerchantAcct		CR BankCollectionAccount B1
		//MER2		DR BankPoolAcctAcct		CR BankCollectionAccount B1
	
	
	
	
	
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

	public String getStartRouteName() {
		return startRouteName;
	}

	public void setStartRouteName(String startRouteName) {
		this.startRouteName = startRouteName;
	}

	public String getEndRouteName() {
		return endRouteName;
	}

	public void setEndRouteName(String endRouteName) {
		this.endRouteName = endRouteName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getTollLane() {
		return tollLane;
	}

	public void setTollLane(Integer tollLane) {
		this.tollLane = tollLane;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}


	
}
