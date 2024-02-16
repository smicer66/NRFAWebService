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

import com.probase.nrfa.enumerations.DistributorStatus;
import com.probase.nrfa.enumerations.DistributorType;
import com.probase.nrfa.enumerations.FundingAccountType;
import com.probase.nrfa.enumerations.MerchantStatus;
  
@Entity
@Table(name="distributors")  
public class Distributor implements Serializable{ 
	private static final long serialVersionUID = -883733125943668937L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@OneToOne  
    @JoinColumn
	Merchant distributorMerchant;
	@Column(nullable = false)
	DistributorStatus distributorStatus;
	@Column(nullable = false)
	FundingAccountType fundingAccountType;
	@Column(nullable = false)
	DistributorType distributorType;
	@OneToOne  
    @JoinColumn
	User createdByUserId;
	@Column(nullable = false)
	String createdByUserName; 
	@Column(nullable = false)
	String distributorCode; 
	@Column(nullable = false)
	String distributorName;
	@OneToOne  
    @JoinColumn
	Account distributorBankAccount;
	@Column(nullable = false)
	String contactAddress;
	@Column(nullable = false)
	String city;
	@Column(nullable = false)
	String district;
	@Column(nullable = false)
	String province;
	@Column(nullable = false)
	String companyName;
	String companyRegNo;
	@Column(nullable = false)
	String contactFullName;
	@Column(nullable = false)
	String contactEmail;
	String altContactEmail;
	@Column(nullable = false)
	String contactMobile;
	String altContactMobile;
	@Column(nullable = false)
	String merchantCode;
	@Column(nullable = false)
	Date created_at;
	Date updated_at;
	Date deleted_at;
	Long parentDistributorId;
	String parentDistributorCode;
	@PrePersist
	protected void onCreate() {
		this.created_at = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_at = new Date();
	}

	
	
	@Enumerated(EnumType.STRING)
	public DistributorStatus getDistributorStatus() {
	    return distributorStatus;
	}
	
	@Enumerated(EnumType.STRING)
	public DistributorType getDistributorType() {
	    return distributorType;
	}
	
	@Enumerated(EnumType.STRING)
	public FundingAccountType getFundingAccountType() {
	    return fundingAccountType;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Merchant getDistributorMerchant() {
		return this.distributorMerchant;
	}

	public void setDistributorMerchant(Merchant distributorMerchant) {
		this.distributorMerchant = distributorMerchant;
	}

	public User getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(User createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public String getCreatedByUserName() {
		return createdByUserName;
	}

	public void setCreatedByUserName(String createdByUserName) {
		this.createdByUserName = createdByUserName;
	}

	public String getDistributorCode() {
		return distributorCode;
	}

	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public Account getDistributorBankAccount() {
		return distributorBankAccount;
	}

	public void setDistributorBankAccount(Account distributorBankAccount) {
		this.distributorBankAccount = distributorBankAccount;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyRegNo() {
		return companyRegNo;
	}

	public void setCompanyRegNo(String companyRegNo) {
		this.companyRegNo = companyRegNo;
	}

	public String getContactFullName() {
		return contactFullName;
	}

	public void setContactFullName(String contactFullName) {
		this.contactFullName = contactFullName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getAltContactEmail() {
		return altContactEmail;
	}

	public void setAltContactEmail(String altContactEmail) {
		this.altContactEmail = altContactEmail;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getAltContactMobile() {
		return altContactMobile;
	}

	public void setAltContactMobile(String altContactMobile) {
		this.altContactMobile = altContactMobile;
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

	public void setDistributorStatus(DistributorStatus distributorStatus) {
		this.distributorStatus = distributorStatus;
	}

	public void setFundingAccountType(FundingAccountType fundingAccountType) {
		this.fundingAccountType = fundingAccountType;
	}
	
	public void setDistributorType(DistributorType distributorType) {
		this.distributorType = distributorType;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public Long getParentDistributorId() {
		return parentDistributorId;
	}

	public void setParentDistributorId(Long parentDistributorId) {
		this.parentDistributorId = parentDistributorId;
	}

	public String getParentDistributorCode() {
		return parentDistributorCode;
	}

	public void setParentDistributorCode(String parentDistributorCode) {
		this.parentDistributorCode = parentDistributorCode;
	}

	
    
}

