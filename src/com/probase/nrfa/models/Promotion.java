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
@Table(name="promotions")  
public class Promotion implements Serializable {  
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id; 
	@Column(nullable = false)
	String description;
	CardScheme cardScheme;
	@Column(nullable = false)
	Date startDate;
	@Column(nullable = false)
	Date endDate;
	@Column(nullable = false)
	Date created_at;
	Date updated_at;
	Date deleted_at;
	@Column(nullable = false)
	Integer baseRationaleCount;
	@Column(nullable = false)
	Double discountPercentageApplicable;
	@OneToOne  
    @JoinColumn
	TrafficScheme trafficScheme;
	@Column(nullable = false)
	String routeCode;
	@Column(nullable = false)
	String startRoute;
	@Column(nullable = false)
	String endRoute;
	@Column(nullable = false)
	String promotionCode;
	@Column(nullable = false)
	String promotionName;
	@OneToOne  
    @JoinColumn
	User setupByUser;
	
	
	

	@PrePersist
	protected void onCreate() {
		this.created_at = new Date();
		this.updated_at = new Date();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CardScheme getCardScheme() {
		return cardScheme;
	}

	public void setCardScheme(CardScheme cardScheme) {
		this.cardScheme = cardScheme;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getBaseRationaleCount() {
		return baseRationaleCount;
	}

	public void setBaseRationaleCount(Integer baseRationaleCount) {
		this.baseRationaleCount = baseRationaleCount;
	}

	public TrafficScheme getTrafficScheme() {
		return trafficScheme;
	}

	public void setTrafficScheme(TrafficScheme trafficScheme) {
		this.trafficScheme = trafficScheme;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getStartRoute() {
		return startRoute;
	}

	public void setStartRoute(String startRoute) {
		this.startRoute = startRoute;
	}

	public String getEndRoute() {
		return endRoute;
	}

	public void setEndRoute(String endRoute) {
		this.endRoute = endRoute;
	}

	public String getPromotionCode() {
		return promotionCode;
	}

	public void setPromotionCode(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	public Double getDiscountPercentageApplicable() {
		return discountPercentageApplicable;
	}

	public void setDiscountPercentageApplicable(Double discountPercentageApplicable) {
		this.discountPercentageApplicable = discountPercentageApplicable;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public User getSetupByUser() {
		return setupByUser;
	}

	public void setSetupByUser(User setupByUser) {
		this.setupByUser = setupByUser;
	}

	
}
