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

import com.probase.nrfa.enumerations.AccountStatus;
import com.probase.nrfa.enumerations.AccountType;
  
@Entity
@Table(name="accounts")  
public class Account implements Serializable {  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id; 
	//@Column(nullable = false)
	@OneToOne  
    @JoinColumn
	Distributor distributor;
	Long distributorId;
	@Column(nullable = false)
	AccountStatus status;
	String otp;
	@Column(nullable = false)
	Date created_at;
	Date updated_at;
	Date deleted_at;
	@Column(nullable = false)
	String branchCode;
	@OneToOne  
    @JoinColumn
	Bank bank;
	@Column(nullable = false)
	String currencyCode;
	@Column(nullable = false)
	AccountType accountType;
	@GeneratedValue(strategy=GenerationType.AUTO)
	String accountIdentifier;
	@Column(nullable = false)
	Integer accountCount;
	@Column(nullable = false)
	Double currentBalance;
	String realBankAccountNo;
	Double thresholdValue;
	
	
	

	@PrePersist
	protected void onCreate() {
		this.created_at = new Date();
		this.updated_at = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_at = new Date();
	}

	@Enumerated(EnumType.STRING)
	public AccountType getAccountType() {
	    return accountType;
	}
	
	@Enumerated(EnumType.STRING)
	public AccountStatus getStatus() {
	    return status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public Long getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
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

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}

	public int getAccountCount() {
		return accountCount;
	}

	public void setAccountCount(int accountCount) {
		this.accountCount = accountCount;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getRealBankAccountNo() {
		return realBankAccountNo;
	}

	public void setRealBankAccountNo(String realBankAccountNo) {
		this.realBankAccountNo = realBankAccountNo;
	}

	public void setAccountCount(Integer accountCount) {
		this.accountCount = accountCount;
	}

	public Double getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(Double thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	
}
