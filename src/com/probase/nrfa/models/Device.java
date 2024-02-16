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
import com.probase.nrfa.enumerations.DeviceType;
import com.probase.nrfa.enumerations.MerchantStatus;
import com.probase.nrfa.enumerations.NetBankingPaymentType;
  
@Entity
@Table(name="devices")  
public class Device implements Serializable{  
	private static final long serialVersionUID = 5457558421999610410L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@OneToOne  
    @JoinColumn
	Merchant merchant;
	@Column(nullable = false)
	DeviceType deviceType;
	@Column(nullable = false)
	DeviceStatus status;
	@OneToOne  
    @JoinColumn
	Distributor distributor;
	@OneToOne  
    @JoinColumn
	User setupByUser;
	Long merchantId;
	String merchantName;
	String merchantCode;
	String distributorName;
	String distributorCode;
	Long distributorId;
	String lastTransactions;
	@Column(nullable = false)
	String setupByUserName; 
	@Column(nullable = false)
	String deviceCode; 
	String deviceSerialNo;
	@Column(nullable = false)
	Date created_at;
	Date updated_at;
	Date deleted_at;
	@Column(nullable = true)
	String terminalApiKey;
	
	@PrePersist
	protected void onCreate() {
		this.created_at = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updated_at = new Date();
	}
	
	@Enumerated(EnumType.STRING)
	public DeviceStatus getStatus() {
	    return status;
	}
	
	@Enumerated(EnumType.STRING)
	public DeviceType getDeviceType() {
	    return deviceType;
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

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceSerialNo() {
		return deviceSerialNo;
	}

	public void setDeviceSerialNo(String deviceSerialNo) {
		this.deviceSerialNo = deviceSerialNo;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	public User getSetupByUser() {
		return setupByUser;
	}

	public void setSetupByUser(User setupByUser) {
		this.setupByUser = setupByUser;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public void setStatus(DeviceStatus status) {
		this.status = status;
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
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

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getDistributorCode() {
		return distributorCode;
	}

	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}

	public Long getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}

	public String getLastTransactions() {
		return lastTransactions;
	}

	public void setLastTransactions(String lastTransactions) {
		this.lastTransactions = lastTransactions;
	}

	public String getSetupByUserName() {
		return setupByUserName;
	}

	public void setSetupByUserName(String setupByUserName) {
		this.setupByUserName = setupByUserName;
	}

	public String getTerminalApiKey() {
		return terminalApiKey;
	}

	public void setTerminalApiKey(String terminalApiKey) {
		this.terminalApiKey = terminalApiKey;
	}

	
	
    
}

