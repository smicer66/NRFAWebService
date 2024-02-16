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

import com.probase.nrfa.enumerations.MerchantStatus;
  
@Entity
@Table(name="merchants")  
public class Merchant implements Serializable{  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@Column(nullable = false)
	String merchantCode; 
	@Column(nullable = false)
	String merchantName;
	@OneToOne  
    @JoinColumn
	Bank merchantBank;
	@Column(nullable = false)
	MerchantStatus status;
	/*@Column(nullable = false)
	String addressLine1;
	String addressLine2;
	@Column(nullable = false)
	String contactEmail;
	String altContactEmail;
	@Column(nullable = false)
	String contactMobile;
	String altContactMobile;
	@Column(nullable = false)
	String companyName;
	@Column(nullable = false)
	String companyRegNo;
	@Column(nullable = false)
	String certificateOfIncorporation;
	@Column(nullable = false)
	String companyLogo;
	@Column(nullable = false)
	String companyData;
	@Column(nullable = false)
	String webActivationCode;
	@Column(nullable = false)
	String mobileActivationCode;
	@Column(nullable = false)
	String bankAccount;*/
	
	//@Column(nullable = false)
	@OneToOne  
    @JoinColumn
	MerchantScheme merchantScheme;
	//@Column(nullable = false)
	@OneToOne  
    @JoinColumn
	User user;
	@Column(nullable = false)
	Long createdByUserId;
	@Column(nullable = false)
	String createdByUserName;
	@Column(nullable = false)
	Date created_at;
	@Column(nullable = false)
	Boolean isBank;
	Date updated_at;
	Date deleted_at;
	String apiKey;
	/*Boolean autoReturnToMerchantSite;
	String manualReturnUrlLink;
	String merchantDecryptKey;*/
	
	@PrePersist
	protected void onCreate() {
		this.created_at = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_at = new Date();
	}

	
	
	@Enumerated(EnumType.STRING)
	public MerchantStatus getStatus() {
	    return status;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Bank getMerchantBank() {
		return merchantBank;
	}

	public void setMerchantBank(Bank merchantBank) {
		this.merchantBank = merchantBank;
	}

	/*public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}*/

	public void setStatus(MerchantStatus status) {
		this.status = status;
	}

	/*public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
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

	public String getCertificateOfIncorporation() {
		return certificateOfIncorporation;
	}

	public void setCertificateOfIncorporation(String certificateOfIncorporation) {
		this.certificateOfIncorporation = certificateOfIncorporation;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getCompanyData() {
		return companyData;
	}

	public void setCompanyData(String companyData) {
		this.companyData = companyData;
	}*/

	public MerchantScheme getMerchantScheme() {
		return merchantScheme;
	}

	public void setMerchantScheme(MerchantScheme merchantScheme) {
		this.merchantScheme = merchantScheme;
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

	/*public String getWebActivationCode() {
		return webActivationCode;
	}

	public void setWebActivationCode(String webActivationCode) {
		this.webActivationCode = webActivationCode;
	}

	public String getMobileActivationCode() {
		return mobileActivationCode;
	}

	public void setMobileActivationCode(String mobileActivationCode) {
		this.mobileActivationCode = mobileActivationCode;
	}*/

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getCreatedByUserName() {
		return createdByUserName;
	}

	public void setCreatedByUserName(String createdByUserName) {
		this.createdByUserName = createdByUserName;
	}

	public Long getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(Long createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public Boolean getIsBank() {
		return isBank;
	}

	public void setIsBank(Boolean isBank) {
		this.isBank = isBank;
	}

	/*public Boolean isAutoReturnToMerchantSite() {
		return autoReturnToMerchantSite;
	}

	public void setAutoReturnToMerchantSite(Boolean autoReturnToMerchantSite) {
		this.autoReturnToMerchantSite = autoReturnToMerchantSite;
	}

	public String getManualReturnUrlLink() {
		return manualReturnUrlLink;
	}

	public void setManualReturnUrlLink(String manualReturnUrlLink) {
		this.manualReturnUrlLink = manualReturnUrlLink;
	}

	public String getMerchantDecryptKey() {
		return merchantDecryptKey;
	}

	public void setMerchantDecryptKey(String merchantDecryptKey) {
		this.merchantDecryptKey = merchantDecryptKey;
	}

	public Boolean getAutoReturnToMerchantSite() {
		return autoReturnToMerchantSite;
	}*/
	
    
}

