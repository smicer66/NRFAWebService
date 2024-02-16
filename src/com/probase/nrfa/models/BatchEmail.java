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
@Table(name="batch_emails")  
public class BatchEmail implements Serializable {  
	
	private static final long serialVersionUID = -4978223012667661805L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@Column(nullable = false)
	String subject;
	@Column(nullable = false)
	String emailBody;
	@Column(nullable = false)
	String receipients;
	@Column(nullable = false)
	Integer sentYes;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public String getReceipients() {
		return receipients;
	}

	public void setReceipients(String receipients) {
		this.receipients = receipients;
	}

	public Integer getSentYes() {
		return sentYes;
	}

	public void setSentYes(Integer sentYes) {
		this.sentYes = sentYes;
	}
	
    
}

