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

import com.probase.nrfa.enumerations.CardStatus;
import com.probase.nrfa.enumerations.CardType;
import com.probase.nrfa.enumerations.DistributorType;
  
@Entity
@Table(name="ecards")  
public class ECard implements Serializable {  
	
	private static final long serialVersionUID = -4978223012667661805L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@OneToOne  
    @JoinColumn
	Merchant merchant;
	@OneToOne  
    @JoinColumn
	Distributor distributor;
	DistributorType distributorType;
	@OneToOne  
    @JoinColumn
	Acquirer acquirer;
	@OneToOne  
    @JoinColumn
	CardScheme cardScheme;
	@OneToOne  
    @JoinColumn
	Customer customer;
	@Column(nullable = false)
	CardStatus cardStatus;
	@Column(nullable = false)
	CardType cardType;
	@Column(nullable = false)
	String pan;
	@Column(nullable = false)
	String pin;
	@Column(nullable = false)
	Date expiryDate;
	@Column(nullable = false)
	String cvv;
	@Column(nullable = false)
	String batchId;
	String otp;
	String customerMobileNumber;
	String merchantCode;
	Long merchantId;
	String distributorCode;
	Long distributorId;
	Date lastCreditDate;
	Date lastDebitDate;
	String lastFiveTransactions;
	@Column(columnDefinition = "BIT", length = 1)
	Boolean changedCardPin;
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long serialNo;
	String uniqueId;
	@Column(nullable = false)
	Double currentBalance;
	@Column(nullable = false)
	Double currentDebt;
	@Column(nullable = false)
	Double totalCredited;
	@Column(nullable = false)
	Double totalDebited;
	String defaultCarPlateNumber;
	@OneToOne  
    @JoinColumn
	TrafficScheme defaultTrafficScheme;

	@Column(nullable = false)
	Date created_at;
	Date updated_at;
	Date deleted_at;
	Integer disableSMS;
	
	Integer currentMonthTollDebitTransactionCount;
	String currentMonthIndex;
	
	
	

	@PrePersist
	protected void onCreate() {
		this.created_at = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_at = new Date();
	}

	
	
	@Enumerated(EnumType.STRING)
	public CardStatus getCardStatus() {
	    return cardStatus;
	}
	
	@Enumerated(EnumType.STRING)
	public CardType getCardType() {
	    return cardType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setChangedCardPin(Boolean changedCardPin) {
		this.changedCardPin = changedCardPin;
	}

	public Boolean getChangedCardPin() {
		return changedCardPin;
	}


	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public Acquirer getAcquirer() {
		return acquirer;
	}

	public void setAcquirer(Acquirer acquirer) {
		this.acquirer = acquirer;
	}

	public CardScheme getCardScheme() {
		return cardScheme;
	}

	public void setCardScheme(CardScheme cardScheme) {
		this.cardScheme = cardScheme;
	}

	public void setCardStatus(CardStatus cardStatus) {
		this.cardStatus = cardStatus;
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

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public DistributorType getDistributorType() {
		return distributorType;
	}

	public void setDistributorType(DistributorType distributorType) {
		this.distributorType = distributorType;
	}

	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}

	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
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

	public Date getLastCreditDate() {
		return lastCreditDate;
	}

	public void setLastCreditDate(Date lastCreditDate) {
		this.lastCreditDate = lastCreditDate;
	}

	public Date getLastDebitDate() {
		return lastDebitDate;
	}

	public void setLastDebitDate(Date lastDebitDate) {
		this.lastDebitDate = lastDebitDate;
	}

	public String getLastFiveTransactions() {
		return lastFiveTransactions;
	}

	public void setLastFiveTransactions(String lastFiveTransactions) {
		this.lastFiveTransactions = lastFiveTransactions;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Double getTotalCredited() {
		return totalCredited;
	}

	public void setTotalCredited(Double totalCredited) {
		this.totalCredited = totalCredited;
	}

	public Double getTotalDebited() {
		return totalDebited;
	}

	public void setTotalDebited(Double totalDebited) {
		this.totalDebited = totalDebited;
	}

	public Double getCurrentDebt() {
		return currentDebt;
	}

	public void setCurrentDebt(Double currentDebt) {
		this.currentDebt = currentDebt;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getDefaultCarPlateNumber() {
		return defaultCarPlateNumber;
	}

	public void setDefaultCarPlateNumber(String defaultCarPlateNumber) {
		this.defaultCarPlateNumber = defaultCarPlateNumber;
	}

	public TrafficScheme getDefaultTrafficScheme() {
		return defaultTrafficScheme;
	}

	public void setDefaultTrafficScheme(TrafficScheme defaultTrafficScheme) {
		this.defaultTrafficScheme = defaultTrafficScheme;
	}

	public Integer getCurrentMonthTollDebitTransactionCount() {
		return currentMonthTollDebitTransactionCount;
	}

	public void setCurrentMonthTollDebitTransactionCount(Integer currentMonthTollDebitTransactionCount) {
		this.currentMonthTollDebitTransactionCount = currentMonthTollDebitTransactionCount;
	}

	public String getCurrentMonthIndex() {
		return currentMonthIndex;
	}

	public void setCurrentMonthIndex(String currentMonthIndex) {
		this.currentMonthIndex = currentMonthIndex;
	}

	public Integer getDisableSMS() {
		return disableSMS;
	}

	public void setDisableSMS(Integer disableSMS) {
		this.disableSMS = disableSMS;
	}
	
    
}

