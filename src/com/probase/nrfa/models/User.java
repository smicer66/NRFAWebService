package com.probase.nrfa.models;

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

import com.probase.nrfa.enumerations.RoleType;
import com.probase.nrfa.enumerations.UserStatus;
  
@Entity
@Table(name="user_accounts")  
public class User {  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id; 

	@Column(nullable = false)
	String username;
	@Column(nullable = false)
	String email;
	@Column(nullable = false)
	UserStatus userStatus;
	@Column(nullable = false)
	String mobileNumber;
	String details;
	String branchCode;
	String bankCode;
	String firstName;
	String uniqueId;
	String lastName;
	String otherName;
	@Column(nullable = false)
	String password;
	RoleType roleCode;
	@Column(columnDefinition = "BIT", length = 1)
	Boolean lockOut;
	Integer failedLoginCount;
	String salt;
	String privileges;
	@Column(nullable = false)
	Date created_at;
	@Column(nullable = false)
	Date updated_at;
	Date deleted_at;
	Date lastLoginDate;
	String otp;
	String webActivationCode;
	@OneToOne  
    @JoinColumn
	Distributor distributor;
	Long distributorId;
	String distributorCompanyName;
	String assignedTrafficTollRouteStart;
	String assignedTrafficTollRouteEnd;
	String assignedTrafficTollRouteCode;
	Integer assignedTrafficTollRouteLane;

	@PrePersist
	protected void onCreate() {
		this.created_at = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_at = new Date();
	}

	
	
	@Enumerated(EnumType.STRING)
	public UserStatus getUserStatus() {
	    return userStatus;
	}
	

	@Enumerated(EnumType.STRING)
	public RoleType getRoleCode() {
	    return roleCode;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public void setUniqueId(String uniqueId)
	{
		this.uniqueId = uniqueId;
	}
	
	
	public String getUniqueId()
	{
		return this.uniqueId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getLockOut() {
		return lockOut;
	}

	public void setLockOut(Boolean lockOut) {
		this.lockOut = lockOut;
	}

	public Integer getFailedLoginCount() {
		return failedLoginCount;
	}

	public void setFailedLoginCount(Integer failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		this.privileges = privileges;
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

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getWebActivationCode() {
		return webActivationCode;
	}

	public void setWebActivationCode(String webActivationCode) {
		this.webActivationCode = webActivationCode;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public void setRoleCode(RoleType roleCode) {
		this.roleCode = roleCode;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
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

	public String getDistributorCompanyName() {
		return distributorCompanyName;
	}

	public void setDistributorCompanyName(String distributorCompanyName) {
		this.distributorCompanyName = distributorCompanyName;
	}

	public String getAssignedTrafficTollRouteStart() {
		return assignedTrafficTollRouteStart;
	}

	public void setAssignedTrafficTollRouteStart(
			String assignedTrafficTollRouteStart) {
		this.assignedTrafficTollRouteStart = assignedTrafficTollRouteStart;
	}

	public String getAssignedTrafficTollRouteEnd() {
		return assignedTrafficTollRouteEnd;
	}

	public void setAssignedTrafficTollRouteEnd(String assignedTrafficTollRouteEnd) {
		this.assignedTrafficTollRouteEnd = assignedTrafficTollRouteEnd;
	}

	public String getAssignedTrafficTollRouteCode() {
		return assignedTrafficTollRouteCode;
	}

	public void setAssignedTrafficTollRouteCode(String assignedTrafficTollRouteCode) {
		this.assignedTrafficTollRouteCode = assignedTrafficTollRouteCode;
	}

	public void setAssignedTrafficTollRouteLane(Integer assignedTrafficTollRouteLane) {
		this.assignedTrafficTollRouteLane = assignedTrafficTollRouteLane;
	}
	
	public Integer getAssignedTrafficTollRouteLane() {
		return assignedTrafficTollRouteLane;
	}

	
}