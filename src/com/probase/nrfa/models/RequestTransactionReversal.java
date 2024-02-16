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
@Table(name="requesttransactionreversal")  
public class RequestTransactionReversal implements Serializable{  
	/**
	 * 
	 */
	private static final long serialVersionUID = -6069324983789271160L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@Column(nullable = false)
	String reverseTransactionRef; 
		//@Column(nullable = false)
	@Column(nullable = false)
	String orderId; 
	@Column(nullable = false)
	String requestId;
	@Column(nullable = false)
	Double amount;
	@Column(nullable = false)
	Double transactionAmount;
	@Column(nullable = false)
	String description;
	@Column(nullable = false)
	Long merchantId;
	@Column(nullable = false)
	String merchantName;
	@Column(nullable = false)
	String merchantCode;
	@Column(nullable = false)
	String deviceCode;
	@Column(nullable = false)
	Date created_at;
	@Column(nullable = false)
	Date updated_at;
	Date deleted_at;
	@Column(nullable = false)
	TransactionStatus status;
	@OneToOne 
    @JoinColumn 
	Transaction transaction;
	String payerMobileNo;
	@OneToOne 
    @JoinColumn 
	Transaction newTransaction;
	@OneToOne 
    @JoinColumn 
	User requestedByUser;
	@Column(nullable = false)
	String requestedByUserName;
	
	
	
	@PrePersist
	protected void onCreate() {
		this.created_at = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_at = new Date();
	}

	
	
	@Enumerated(EnumType.STRING)
	public TransactionStatus getStatus() {
	    return status;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}


	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}


	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getReverseTransactionRef() {
		return reverseTransactionRef;
	}

	public void setReverseTransactionRef(String reverseTransactionRef) {
		this.reverseTransactionRef = reverseTransactionRef;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public String getPayerMobileNo() {
		return payerMobileNo;
	}

	public void setPayerMobileNo(String payerMobileNo) {
		this.payerMobileNo = payerMobileNo;
	}

	public Transaction getNewTransaction() {
		return newTransaction;
	}

	public void setNewTransaction(Transaction newTransaction) {
		this.newTransaction = newTransaction;
	}

}
