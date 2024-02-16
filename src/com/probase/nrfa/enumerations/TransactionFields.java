package com.probase.nrfa.enumerations; 

import java.util.Date; 

import javax.persistence.Column; 
import javax.persistence.JoinColumn; 
import javax.persistence.OneToOne; 

import com.probase.nrfa.models.Device; 
import com.probase.nrfa.models.Distributor; 
import com.probase.nrfa.models.ECard; 
import com.probase.nrfa.models.Merchant; 
import com.probase.nrfa.models.TrafficScheme; 
import com.probase.nrfa.models.User; 

public enum TransactionFields {
	channel, nfraCurrency, paymentMeans, transactionDate, serviceType, status,  startRoute,  endRoute, 
	amount, onUsTransaction, trafficScheme, crDistributorName, crMerchantName, crBankName, drDistributorName, 
	drMerchantName, drBankName, created_at
}
