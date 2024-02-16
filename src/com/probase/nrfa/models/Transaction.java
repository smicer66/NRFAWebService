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
import com.probase.nrfa.enumerations.NFRACurrency;
import com.probase.nrfa.enumerations.PaymentMeans;
import com.probase.nrfa.enumerations.ServiceType;
import com.probase.nrfa.enumerations.TransactionStatus;
  
@Entity
@Table(name="transactions")  
public class Transaction{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	@Column(nullable = false)
	String transactionRef; 
	@Column(nullable = false)
	String orderRef;
	@Column(nullable = false)
	Channel channel;
	@Column(nullable = false)
	NFRACurrency nfraCurrency;
	@Column(nullable = false)
	PaymentMeans paymentMeans;
	@Column(nullable = false)
	Date transactionDate;
	@Column(nullable = false)
	ServiceType serviceType;
	@OneToOne 
    @JoinColumn 
	User transactingUser;
	@Column(nullable = false)
	String transactingUserName;
	@Column(nullable = false)
	Long userId;
	@Column(nullable = false)
	TransactionStatus status;
	@OneToOne  
    @JoinColumn
	ECard card;
	@OneToOne  
    @JoinColumn
	Device device;
	@OneToOne  
    @JoinColumn
	Distributor distributor;
	@OneToOne  
    @JoinColumn
	Merchant merchant;
	Long deviceId;
	String deviceCode;
	String startRoute;
	String endRoute;
	Integer tollLane;
	String messageRequest;
	String messageResponse;
	Double fixedCharge;
	Double transactionFee;
	Double amount;
	Integer responseCode;
	@Column(nullable = false)
	Boolean onUsTransaction;
	Long transactingBankId;	//Src Bank Id
	Long receipientTransactingBankId;	//Rec Bank Id
	String transactionDetail;
	@OneToOne  
    @JoinColumn
	TrafficScheme trafficScheme;
	Long trafficSchemeId;
	String trafficSchemeName;
	
	Long crCardId;
	Long crDistributorAccountId;
	Long crDistributorId;
	Long crMerchantId;
	Long crBankId;
	Long drCardId;
	Long drDistributorAccountId;
	Long drDistributorId;
	Long drMerchantId;
	Long drBankId;
	String crCardPan;
	String crDistributorName;
	String crMerchantName;
	String crBankName;
	String drCardPan;
	String drDistributorName;
	String drMerchantName;
	String drBankName;
	@Column(nullable = false)
	String narration;
	String carPlateNumber;
	String expectedCarPlateNumber;
	String receiptNo;
	Long promotionId;
	Double promotionAmountApplied;
	Long trafficExceptionId;
	Double trafficExceptionAmountApplied;
	Double exceptionAmountApplied;
	Long exceptionId;
	String extraDetails;
	
	Boolean cardOverDraft;
	Integer serverOption;		//0:NFRA		1:Online cloud
	
	
	/*
	 PROMOTION
	 25%	(01/01/17 to 30/01/17)		Base 10		ROUTE001
	 15%	(01/02/17 to 28/02/17)		Base 15		ROUTE002
	 
	 EXCEPTION
	 AU910MP	NULL			10%		ROUTE001
	 AUIPD10	NULL			10%		ROUTE002
	 NULL		0012010012023	5%		ROUTE003
	 
	  
	  
	  
	 */


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
	
	
	
	
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	@PrePersist
	protected void onCreate() {
		this.created_at = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_at = new Date();
	}


	@Enumerated(EnumType.STRING)
	public ServiceType getServiceType() {
	    return serviceType;
	}
	
	@Enumerated(EnumType.STRING)
	public TransactionStatus getStatus() {
	    return status;
	}
	
	@Enumerated(EnumType.STRING)
	public Channel getChannel() {
	    return channel;
	}
	
	@Enumerated(EnumType.STRING)
	public PaymentMeans getPaymentMeans() {
	    return paymentMeans;
	}
	
	@Enumerated(EnumType.STRING)
	public NFRACurrency getNFRACurrency() {
	    return nfraCurrency;
	}

	public Long getId() {
		return id;
	}

	public String getOrderRef() {
		return orderRef;
	}

	public void setOrderRef(String orderRef) {
		this.orderRef = orderRef;
	}

	public String getTransactionRef() {
		return transactionRef;
	}

	public void setTransactionRef(String transactionRef) {
		this.transactionRef = transactionRef;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public User getTransactingUser() {
		return transactingUser;
	}

	public void setTransactingUser(User transactingUser) {
		this.transactingUser = transactingUser;
	}

	public String getTransactingUserName() {
		return transactingUserName;
	}

	public void setTransactingUserName(String transactingUserName) {
		this.transactingUserName = transactingUserName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ECard getCard() {
		return card;
	}

	public void setCard(ECard card) {
		this.card = card;
	}

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
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

	public Integer getTollLane() {
		return tollLane;
	}

	public void setTollLane(Integer tollLane) {
		this.tollLane = tollLane;
	}

	public String getMessageRequest() {
		return messageRequest;
	}

	public void setMessageRequest(String messageRequest) {
		this.messageRequest = messageRequest;
	}

	public String getMessageResponse() {
		return messageResponse;
	}

	public void setMessageResponse(String messageResponse) {
		this.messageResponse = messageResponse;
	}

	public Double getFixedCharge() {
		return fixedCharge;
	}

	public void setFixedCharge(Double fixedCharge) {
		this.fixedCharge = fixedCharge;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}


	public Boolean getOnUsTransaction() {
		return onUsTransaction;
	}

	public void setOnUsTransaction(Boolean onUsTransaction) {
		this.onUsTransaction = onUsTransaction;
	}

	public Long getTransactingBankId() {
		return transactingBankId;
	}

	public void setTransactingBankId(Long transactingBankId) {
		this.transactingBankId = transactingBankId;
	}

	public Long getReceipientTransactingBankId() {
		return receipientTransactingBankId;
	}

	public void setReceipientTransactingBankId(Long receipientTransactingBankId) {
		this.receipientTransactingBankId = receipientTransactingBankId;
	}

	public String getTransactionDetail() {
		return transactionDetail;
	}

	public void setTransactionDetail(String transactionDetail) {
		this.transactionDetail = transactionDetail;
	}


	public Long getCrCardId() {
		return crCardId;
	}

	public void setCrCardId(Long crCardId) {
		this.crCardId = crCardId;
	}

	public Long getCrDistributorAccountId() {
		return crDistributorAccountId;
	}

	public void setCrDistributorAccountId(Long crDistributorAccountId) {
		this.crDistributorAccountId = crDistributorAccountId;
	}

	public Long getCrDistributorId() {
		return crDistributorId;
	}

	public void setCrDistributorId(Long crDistributorId) {
		this.crDistributorId = crDistributorId;
	}

	public Long getCrMerchantId() {
		return crMerchantId;
	}

	public void setCrMerchantId(Long crMerchantId) {
		this.crMerchantId = crMerchantId;
	}

	public Long getCrBankId() {
		return crBankId;
	}

	public void setCrBankId(Long crBankId) {
		this.crBankId = crBankId;
	}

	public Long getDrCardId() {
		return drCardId;
	}

	public void setDrCardId(Long drCardId) {
		this.drCardId = drCardId;
	}

	public Long getDrDistributorAccountId() {
		return drDistributorAccountId;
	}

	public void setDrDistributorAccountId(Long drDistributorAccountId) {
		this.drDistributorAccountId = drDistributorAccountId;
	}

	public Long getDrDistributorId() {
		return drDistributorId;
	}

	public void setDrDistributorId(Long drDistributorId) {
		this.drDistributorId = drDistributorId;
	}

	public Long getDrMerchantId() {
		return drMerchantId;
	}

	public void setDrMerchantId(Long drMerchantId) {
		this.drMerchantId = drMerchantId;
	}

	public Long getDrBankId() {
		return drBankId;
	}

	public void setDrBankId(Long drBankId) {
		this.drBankId = drBankId;
	}

	public String getCrCardPan() {
		return crCardPan;
	}

	public void setCrCardPan(String crCardPan) {
		this.crCardPan = crCardPan;
	}

	public String getCrDistributorName() {
		return crDistributorName;
	}

	public void setCrDistributorName(String crDistributorName) {
		this.crDistributorName = crDistributorName;
	}

	public String getCrMerchantName() {
		return crMerchantName;
	}

	public void setCrMerchantName(String crMerchantName) {
		this.crMerchantName = crMerchantName;
	}

	public String getCrBankName() {
		return crBankName;
	}

	public void setCrBankName(String crBankName) {
		this.crBankName = crBankName;
	}

	public String getDrCardPan() {
		return drCardPan;
	}

	public void setDrCardPan(String drCardPan) {
		this.drCardPan = drCardPan;
	}

	public String getDrDistributorName() {
		return drDistributorName;
	}

	public void setDrDistributorName(String drDistributorName) {
		this.drDistributorName = drDistributorName;
	}

	public String getDrMerchantName() {
		return drMerchantName;
	}

	public void setDrMerchantName(String drMerchantName) {
		this.drMerchantName = drMerchantName;
	}

	public String getDrBankName() {
		return drBankName;
	}

	public void setDrBankName(String drBankName) {
		this.drBankName = drBankName;
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

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public void setNFRACurrency(NFRACurrency nfraCurrency) {
		this.nfraCurrency = nfraCurrency;
	}
	

	public void setPaymentMeans(PaymentMeans paymentMeans) {
		this.paymentMeans = paymentMeans;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public Double getTransactionFee() {
		return transactionFee;
	}

	public TrafficScheme getTrafficScheme() {
		return trafficScheme;
	}

	public void setTrafficScheme(TrafficScheme trafficScheme) {
		this.trafficScheme = trafficScheme;
	}

	public Long getTrafficSchemeId() {
		return trafficSchemeId;
	}

	public void setTrafficSchemeId(Long trafficSchemeId) {
		this.trafficSchemeId = trafficSchemeId;
	}

	public String getTrafficSchemeName() {
		return trafficSchemeName;
	}

	public void setTrafficSchemeName(String trafficSchemeName) {
		this.trafficSchemeName = trafficSchemeName;
	}

	public void setTransactionFee(Double transactionFee) {
		this.transactionFee = transactionFee;
	}

	public String getCarPlateNumber() {
		return carPlateNumber;
	}

	public void setCarPlateNumber(String carPlateNumber) {
		this.carPlateNumber = carPlateNumber;
	}

	public String getExpectedCarPlateNumber() {
		return expectedCarPlateNumber;
	}

	public void setExpectedCarPlateNumber(String expectedCarPlateNumber) {
		this.expectedCarPlateNumber = expectedCarPlateNumber;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}

	public Double getPromotionAmountApplied() {
		return promotionAmountApplied;
	}

	public void setPromotionAmountApplied(Double promotionAmountApplied) {
		this.promotionAmountApplied = promotionAmountApplied;
	}

	public Long getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(Long exceptionId) {
		this.exceptionId = exceptionId;
	}

	public Double getExceptionAmountApplied() {
		return exceptionAmountApplied;
	}

	public void setExceptionAmountApplied(Double exceptionAmountApplied) {
		this.exceptionAmountApplied = exceptionAmountApplied;
	}

	public String getExtraDetails() {
		return extraDetails;
	}

	public void setExtraDetails(String extraDetails) {
		this.extraDetails = extraDetails;
	}

	public Integer getServerOption() {
		return serverOption;
	}

	public void setServerOption(Integer serverOption) {
		this.serverOption = serverOption;
	}

	public Boolean getCardOverDraft() {
		return cardOverDraft;
	}

	public void setCardOverDraft(Boolean cardOverDraft) {
		this.cardOverDraft = cardOverDraft;
	}

	
	
}
