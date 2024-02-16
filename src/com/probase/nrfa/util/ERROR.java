package com.probase.nrfa.util;

import java.util.Collection;

import javax.json.JsonValue;


public class ERROR {

	/*User Account Authenticate*/

	public static final String GENERAL_SUCCESS = "0";
	public static final String AUTHENTICATE_FAIL = "1";
	public static final String INVALID_USER_CREATION_PRIVILEDGES = "2";
	public static final String USER_ACCOUNT_CREATION_FAILED = "3";
	public static final String INCOMPLETE_PARAMETERS = "4";
	public static final String GENERAL_SYSTEM_ERROR = "5";
	public static final String UNAUTHORIZED_ACTION = "6";
	public static final String PASSWORD_CHANGE_FAIL = "7";
	public static final String INVALID_CARD_CREATION_PRIVILEDGES = "8";
	public static final String CARD_SCHEME_ALREADY_EXISTS = "9";
	public static final String CARD_SCHEME_NOT_EXISTING = "10";
	public static final String INVALID_SERVICE_TYPE = "11";
	public static final String INVALID_CARD_CHARGE_PRIVILEDGES = "12";
	public static final String INVALID_TRAFFIC_SCHEME = "13";
	public static final String INVALID_DEVICE_USED = "14";
	public static final String MERCHANT_STATUS_INVALID = "15";
	public static final String DISTRIBUTOR_STATUS_INVALID = "16";
	public static final String TRANSACTION_BY_INVALID_USER = "17";
	public static final String NO_TRANSACTIONS_LISTED = "18";
	public static final String NO_CARD_SCHEMES = "19";
	public static final String TRANSACTIONS_NOT_FOUND = "20";
	public static final String TRANSACTION_REVERSAL_REQUEST_EXISTS = "21";
	public static final String TRANSACTION_REVERSAL_REQUEST_PRIVILEDGES_FAIL = "22";
	public static final String USER_PRIVILEDGES_INSUFFICIENT = "23";
	public static final String TRAFFIC_SCHEME_NOT_PROVIDED = "24";
	public static final String TRAFFIC_TOLL_PRICE_ALREADY_EXISTS = "25";
	public static final String TOLL_ROUTE_LANE_NOT_EXISTS = "26";
	public static final String TRAFFIC_TOLL_PRICE_NOT_EXISTS = "27";
	public static final String EOD_TRANSACTION_UPDATE_FAILED = "28";
	public static final String INVALID_SERIAL_ID_COUNT_MATCH = "29";
	public static final String INVALID_TERMINAL_LIST_PRIVILEDGES = "30";
	public static final String CARD_ASSIGN_DISTRIBUTOR_FAIL = "31";
	public static final String NO_DISTRIBUTOR_LIST = "32";
	public static final String BANK_CREATE_FAIL = "33";
	public static final String CARD_NOT_EXIST = "34";
	public static final String INVALID_MERCHANT_CREATION_PRIVILEDGES = "35";
	public static final String INVALID_BANK_PROVIDED = "36";
	public static final String INVALID_MERCHANT_SCHEME = "37";
	public static final String INVALID_MERCHANT_LISTING_PRIVILEDGES = "38";
	public static final String MERCHANT_SCHEME_EXISTS = "39";
	public static final String MERCHANT_SCHEME_NOT_EXIST = "40";
	public static final String NO_MERCHANT_SCHEMES = "41";
	public static final String USER_NOT_FOUND = "42";
	public static final String INSUFFICIENT_PRIVILEDGES = "43";
	public static final String DEVICE_ALREADY_SYNCED = "44";
	public static final String TERMINAL_IDS_NOT_AVAILABLE = "45";
	public static final String NO_ROUTE_MATCHING_ROUTE_CODE = "46";
	public static final String WEB_TERMINAL_NEEDS_MERCHANT_CODE = "47";
	public static final String NO_CARD_MATCHING_DETAILS_PROVIDED = "48";
	public static final String CARD_ASSIGN_CUSTOMER_FAIL = "49";
	public static final String INVALID_CARD_ASSIGN_TO_CUSTOMER_PRIVILEDGES = "50";
	public static final String DISTRIBUTOR_ACCOUNT_NOT_EXIST = "51";
	public static final String INVALID_SERVICE_TYPE_PROVIDED = "52";
	public static final String ISSUING_CARD_DISTRIBUTOR_ACCOUNT_NOT_EXIST = "53";
	public static final String CASH_COLLECTING_DISTRIBUTOR_ACCOUNT_NOT_EXIST = "54";
	public static final String SERVICE_TYPE_MISMATCH = "55";
	public static final String TRAFFIC_SCHEME_EXISTS = "56";
	public static final String TRAFFIC_TOLL_ROUTE_LANE_EXISTS = "57";
	public static final String TRAFFIC_TOLL_ROUTE_NOT_EXISTS = "58";
	public static final String TRAFFIC_TOLL_ROUTE_EXISTS = "59";
	public static final String INVALID_CARD_UNIQUE_ID_ASSIGN_PRIVILEDGES = "60";
	public static final String ORDER_ID_DUPLICATE = "61";
	public static final String NO_CARDS_AVAILABLE = "62";
	public static final String NO_MERCHANT_LIST = "63";
	public static final String CURRENCY_SERVICETYPE_NOT_SUPPORTED = "64";
	public static final String EMPTY_USER_LIST = "65";
	public static final String INVALID_USER_STATUS = "66";
	public static final String USER_EMAIL_USERNAME_EXISTS = "67";
	public static final String INVALID_CARD_DISTRIBUTOR_ASSIGN_PRIVILEDGES = "68";
	public static final String INVALID_DISTRIBUTOR_FUNDING_ACCOUNT_TYPE_COMBINATION = "69";
	public static final String INVALID_CARD_SMS_ACTIVATE_PRIVILEDGES = "70";
	public static final String DISTRIBUTOR_NOT_EXIST = "71";
	
	public static int GENERAL_OK = 0;
	public static int GENERAL_FAIL = 5001;
	
	public static final int NEW_PASSWORD_SET = 602;
	public static final int NEW_PASSWORD_SET_FAILED = 603;
	
	
	/*JWT*/
	public static final int FORCE_LOGOUT_USER = -1;
	
	/*Customers*/
	public static final int CUSTOMER_CREATE_SUCCESS = 100;
	public static final int CUSTOMER_CREATION_DOB_FAILED = 102;
	public static final int CUSTOMER_CREATION_FAILED = 101;
	public static final int CUSTOMER_LIST_FETCH_SUCCESS = 110;
	public static final int CUSTOMER_LIST_FETCH_FAIL = 111;
	public static final int CUSTOMER_CREATE_SUCCESS_NO_USER_ACCOUNT = 112;
	public static final int CUSTOMER_ACCOUNT_ADDITION_SUCCESSFUL = 113;
	public static final int CUSTOMER_ACCOUNT_UPDATE_SUCCESSFUL = 114;
	public static final int BATCH_CUSTOMER_CREATE_SUCCESS = 115;
	public static final int BATCH_CUSTOMER_CREATE_FAIL = 116;
	public static final int CUSTOMER_FETCH_FAILED = 117;
	/*Merchants*/
	public static final int MERCHANT_SETUP_SUCCESS = 200;
	public static final int MERCHANT_SETUP_FAIL = 201;
	public static final int MERCHANT_LIST_FETCH_SUCCESS = 210;
	public static final int MERCHANT_LIST_FETCH_FAIL = 211;
	public static final int MERCHANT_UPDATE_STATUS_SUCCESS = 220;
	public static final int MERCHANT_UPDATE_STATUS_FAIL_NO_MERCHANT = 221;
	public static final int MERCHANT_UPDATE_STATUS_FAIL = 222;
	public static final int MERCHANT_SETUP_SUCCESS_NO_USER_ACCOUNT = 223;
	public static final int MERCHANT_SETUP_COMPANY_NAME_EXIST = 224;
	/*EWaller*/
	public static final int EWALLET_ACTIVATION_SUCCESS = 300;
	public static final int EWALLET_ACTIVATION_FAIL = 301;
	public static final int EWALLET_ACTIVATION_INVALID_CODE = 302;
	/*MMoney*/
	public static final int MMONEY_PROFILE_SUCCESS = 400;
	public static final int MMONEY_PROFILE_FAIL_INVALID_MOBILE = 401;
	public static final int MMONEY_PROFILE_FAIL = 402;
	public static final int MMONEY_ADD_SUCCESS = 410;
	public static final int MMONEY_ADD_FAIL = 411;
	public static final int MMONEY_ADD_FAIL_PAN_MISMATCH = 412;
	public static final int MMONEY_ADD_FAIL_MMONEY_EXIST = 413;
	public static final int MMONEY_STATUS_UPDATED = 414;
	public static final int MMONEY_STATUS_FAIL = 415;
	public static final int MMONEY_ACCOUNT_ACTIVATED = 416;
	public static final int MMONEY_ACCOUNT_ACTIVATE_FAIL = 417;
	/*User Accounts*/
	public static final int USER_ACCOUNT_ADD_SUCCESSFUL = 500;
	public static final int USER_ACCOUNT_ADD_FAIL = 501;
	public static final int USER_STATUS_UPDATE_SUCCESS = 502;
	public static final int USER_STATUS_UPDATE_FAIL = 503;
	
	/*Devices*/
	public static final int DEVICE_ADD_SUCCESS = 700;
	public static final int DEVICE_ADD_FAIL = 701;
	public static final int DEVICE_LIST_FETCH_SUCCESS = 710;
	public static final int DEVICE_LIST_FETCH_FAIL = 711;
	/*Bank Staff*/
	public static final int BANK_STAFF_CREATE_SUCCESS = 800;
	public static final int BANK_STAFF_CREATE_FAIL = 801;
	/*Transaction*/
	public static final int OTP_GENERATE_SUCCESS = 900;
	public static final int BALANCE_INADEQUATE = 901;
	public static final int INVALID_TXN_AMOUNT = 902;
	public static final int HASH_FAIL = 903;
	public static final int EXPIRED_CARD = 904;
	public static final int INVALID_EXPIRY_DATE = 905;
	public static final int MERCHANT_EXIST_FAIL = 906;
	public static final int MERCHANT_PLUS_DEVICE_STATUS_FAIL = 907;
	public static final int CVV_FAIL = 908;
	public static final int DATA_INCONSISTENCY = 909;
	public static final String PAYMENT_TRANSACTION_SUCCESS = "00";
	public static final int TRANSACTION_NOT_FOUND = 911;
	public static final int MERCHANT_PLUS_VENDOR_SERVICE_STATUS_FAIL = 912;
	public static final int MERCHANT_VENDOR_SERVICE_INVALID = 913;
	public static final int TRANSACTION_STATUS_UPDATED = 914;
	public static final int CARD_NOT_VALID = 915;
	/*Payment Interface Data*/
	public static final int PAYMENT_INTERFACE_PULL_SUCCESS = 1000;
	public static final int MERCHANT_NOT_EXIST = 1001;
	public static final int PAYMENT_INITIATION_SUCCESS = 1002;
	/*General Transaction*/
	public static final int TRANSACTION_SUCCESS = 0;
	public static final int INSUFFICIENT_FUNDS = 1;
	public static final int OTP_CHECK_FAIL = 2;
	public static final int TRANSACTION_FAIL = 3;
	/*EWallet Transactions*/
	public static final int EWALLET_TRANSFER_INVALID_REC_ACCOUNT = 2001;
	public static final int EWALLET_TRANSFER_DESTINATION_STATUS_INACTIVE = 2002;
	public static final int EWALLET_BALANCE_PULL_SUCCESS = 2003;
	public static final int EWALLET_ACCOUNTS_NO_EXIST = 2004;
	/*Card Scheme*/
	public static final int CARD_SCHEME_FETCH_SUCCESS = 2100;
	public static final int CARD_SCHEME_FETCH_FAIL = 2101;
	public static final int CARD_SCHEME_CREATED_SUCCESS = 2102;
	public static final int CARD_SCHEME_UPDATED_SUCCESS = 2103;
	/*OTP*/
	public static final int OTP_TIMEOUT = 3100;

	public static final int GENERAL_SYSTEM_FAIL = 99;

	

	public static String API_KEY_FAIL = "4000";
	public static String RESPONSE_URL_NOT_PROVIDED = "4001";
	public static String HASH_NOT_PROVIDED= "4002";
	public static String ORDER_ID_NOT_PROVIDED= "4003";
	public static String TRANSACTION_AMOUNT_INVALID= "4004";
	public static String SERVICE_TYPE_NOT_PROVIDED= "4005";
	public static String MERCHANT_DEVICE_FAIL= "4006";
	public static String HASH_FAIL_VALIDATION= "4007";
	public static String INVALID_TRANSACTION = "4008";
	public static String INVALID_DEVICE_DETAIL = "4009";
	public static String NO_CURRENCY_SPECIFIED = "4010";
	public static String CARD_QUANTITY_MAX_EXCEEDED = "4011";
	public static String TERMINAL_COUNT_EXCEEDED = "4012";
	
	
	
	
	
	
	

	

	


	

	

	

	

	
	

}
