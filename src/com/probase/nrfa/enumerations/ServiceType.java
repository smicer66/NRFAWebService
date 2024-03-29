package com.probase.nrfa.enumerations;

public enum ServiceType {

	CUSTOMER_DEPOSIT_CASH_VIA_MERCHANT_OTC_ONUS,
	CUSTOMER_DEPOSIT_CASH_VIA_MERCHANT_OTC_NOTONUS,
	CUSTOMER_LOAD_CARD_VIA_DISTRIBUTOR_ONUS,
	CUSTOMER_LOAD_CARD_VIA_DISTRIBUTOR_NOTONUS,
	DISTRIBUTOR_DEPOSIT_CASH_INTO_DISTRIBUTOR_ACCOUNT,
	DISTRIBUTOR_DEPOSIT_CASH_INTO_GENERAL_POOL_ACCOUNT, 
	CARD_DEBIT_AT_TOLL,
	TRANSACTION_REVERSAL,
	CASH_COLLECT_AT_TOLL,
			
}

//0	CUSTOMER_DEPOSIT_CASH_VIA_MERCHANT_OTC_ONUS,		//Debit Merchant Collection Account; Credit Card
//1	CUSTOMER_DEPOSIT_CASH_VIA_MERCHANT_OTC_NOTONUS,		//Debit Cash Collecting Merchant Account; Credit Card Merchant Collecting Account; Debit Card Merchant Collecting Account; Credit Card
//2	CUSTOMER_LOAD_CARD_VIA_DISTRIBUTOR_ONUS,			//Debit Distributor Account; Credit Customer Card
//3	CUSTOMER_LOAD_CARD_VIA_DISTRIBUTOR_NOTONUS,			//Debit Cash Collecting distributor Account; Credit Card Distributor Acct; Debit Card Distributor Acct; Credit Card
//4	DISTRIBUTOR_DEPOSIT_CASH_INTO_DISTRIBUTOR_ACCOUNT,
//5	DISTRIBUTOR_DEPOSIT_CASH_INTO_GENERAL_POOL_ACCOUNT, 
//6	CARD_DEBIT_AT_TOLL,
//7	TRANSACTION_REVERSAL,
//8	CASH_COLLECT_AT_TOLL,
