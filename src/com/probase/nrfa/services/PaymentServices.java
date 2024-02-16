package com.probase.nrfa.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.probase.nrfa.authenticator.CardFunction;
import com.probase.nrfa.authenticator.HttpHeaderNames;
import com.probase.nrfa.authenticator.PaymentFunction;
import com.probase.nrfa.authenticator.TerminalFunction;
import com.probase.nrfa.enumerations.AccountStatus;
import com.probase.nrfa.enumerations.AccountType;
import com.probase.nrfa.enumerations.CardStatus;
import com.probase.nrfa.enumerations.CardType;
import com.probase.nrfa.enumerations.Channel;
import com.probase.nrfa.enumerations.CustomerStatus;
import com.probase.nrfa.enumerations.DeviceStatus;
import com.probase.nrfa.enumerations.Gender;
import com.probase.nrfa.enumerations.MobileAccountStatus;
import com.probase.nrfa.enumerations.NFRACurrency;
import com.probase.nrfa.enumerations.RoleType;
import com.probase.nrfa.enumerations.ServiceType;
import com.probase.nrfa.enumerations.TransactionStatus;
import com.probase.nrfa.enumerations.UserStatus;
import com.probase.nrfa.models.Acquirer;
import com.probase.nrfa.models.Bank;
import com.probase.nrfa.models.CardScheme;
import com.probase.nrfa.models.Device;
import com.probase.nrfa.models.District;
import com.probase.nrfa.models.ECard;
import com.probase.nrfa.models.Merchant;
import com.probase.nrfa.models.TrafficScheme;
import com.probase.nrfa.models.TrafficTollPrice;
import com.probase.nrfa.models.TrafficTollRoute;
import com.probase.nrfa.models.TrafficTollRouteLane;
import com.probase.nrfa.models.Transaction;
import com.probase.nrfa.models.User;
import com.probase.nrfa.util.Application;
import com.probase.nrfa.util.ERROR;
import com.probase.nrfa.util.PrbCustomService;
import com.probase.nrfa.util.ServiceLocator;
import com.probase.nrfa.util.SwpService;
import com.probase.nrfa.util.UtilityHelper;

@Path( "services/PaymentServices" )
@Stateless( name = "PaymentServices", mappedName = "services/PaymentServices" )
public class PaymentServices {

	private static Logger log = Logger.getLogger(PaymentServices.class);
	private ServiceLocator serviceLocator = ServiceLocator.getInstance();
	public SwpService swpService = null;
	public PrbCustomService swpCustomService = PrbCustomService.getInstance();
	
	
	/*
	 * @params	status	
	 * 	CardStatus.ACTIVE	0
	 *	CardStatus.DELETED	1
	 *	CardStatus.DISABLED	2
	 *	CardStatus.INACTIVE	3
	 */
	
	private static final long serialVersionUID = -6663599014192066936L;

	   
    @POST
    @Path( "processCardTransaction" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response processCardTransaction(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "cardPan" ) String cardPan,
            @FormParam( "orderId" ) String orderId,
            @FormParam( "terminalId" ) String terminalId,
            @FormParam( "amount" ) Double amount,
            @FormParam( "narration" ) String narration,
            @FormParam( "serviceType" ) String serviceType,
            @FormParam( "extraDetails" ) String extraDetails,
            @FormParam( "hash" ) String hash,
            @FormParam( "routeCode" ) String routeCode,
            @FormParam( "channel" ) String channel,
            @FormParam( "tollLane" ) Integer tollLane,
            @FormParam( "trafficScheme" ) String trafficScheme, 
            @FormParam( "vehicleRegNumber" ) String vehicleRegNumber, 
            @FormParam( "nfraCurrency" ) String nfraCurrency ) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	
        	if(nfraCurrency==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.NO_CURRENCY_SPECIFIED );
            	jsonObject.add( "message", "No Currency Specified" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
        	
            Response authResponse = paymentFunction.
            		processCardTransaction(cardPan, orderId, terminalId, amount, narration, 
            	    		serviceType, extraDetails, hash, routeCode, tollLane, 
            	    		trafficScheme, channel, vehicleRegNumber, nfraCurrency, token, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    @POST
    @Path( "processCardTransactionV2" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response processCardTransactionV2(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "cardPan" ) String cardPan,
            @FormParam( "orderId" ) String orderId,
            @FormParam( "terminalId" ) String terminalId,
            @FormParam( "amount" ) Double amount,
            @FormParam( "narration" ) String narration,
            @FormParam( "serviceType" ) String serviceType,
            @FormParam( "extraDetails" ) String extraDetails,
            @FormParam( "hash" ) String hash,
            @FormParam( "routeCode" ) String routeCode,
            @FormParam( "channel" ) String channel,
            @FormParam( "tollLane" ) Integer tollLane,
            @FormParam( "trafficScheme" ) String trafficScheme, 
            @FormParam( "vehicleRegNumber" ) String vehicleRegNumber, 
            @FormParam( "nfraCurrency" ) String nfraCurrency ) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	
        	if(nfraCurrency==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.NO_CURRENCY_SPECIFIED );
            	jsonObject.add( "message", "No Currency Specified" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
        	
            Response authResponse = paymentFunction.
            		processCardTransactionV2(cardPan, orderId, terminalId, amount, narration, 
            	    		serviceType, extraDetails, hash, routeCode, tollLane, 
            	    		trafficScheme, channel, vehicleRegNumber, nfraCurrency, token, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    @POST
    @Path( "processCardBatchTransactions" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response processCardBatchTransactions(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "transactionData" ) String transactionData) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = paymentFunction.
            		processCardBatchTransactions(transactionData, token, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    public Response processDistributorAccountFunding(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "distributorCode" ) String distributorCode,
            @FormParam( "orderId" ) String orderId,
            @FormParam( "terminalId" ) String terminalId,
            @FormParam( "amount" ) Double amount,
            @FormParam( "narration" ) String narration,
            @FormParam( "serviceType" ) String serviceType,
            @FormParam( "extraDetails" ) String extraDetails,
            @FormParam( "hash" ) String hash,
            @FormParam( "channel" ) String channel ) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
        	
            Response authResponse = paymentFunction.
            		processDistributorAccountFunding(distributorCode, orderId, terminalId, amount, narration, 
            	    		serviceType, extraDetails, hash, channel, token, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    
    
    @POST
    @Path( "getTransaction" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getTransaction(
    		@Context HttpHeaders httpHeaders,
            @FormParam( "paymentRef" ) String paymentRef,
            @FormParam( "orderId" ) String orderId) throws JSONException {

        PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = paymentFunction.getTransaction(paymentRef, orderId, requestId);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    @POST
    @Path( "listTransactions" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response listTransactions(
    		@Context HttpHeaders httpHeaders,
            @FormParam( "merchantCode" ) String merchantCode,
            @FormParam( "cardPan" ) String cardPan,
            @FormParam( "terminalId" ) String terminalId,
            @FormParam( "minumumAmount" ) Double minumumAmount,
            @FormParam( "maximumAmount" ) Double maximumAmount,
            @FormParam( "serviceType" ) String serviceType,
            @FormParam( "startRoute" ) String startRoute,
            @FormParam( "endRoute" ) String endRoute,
            @FormParam( "tollLane" ) Integer tollLane,
            @FormParam( "trafficScheme" ) String trafficScheme,
            @FormParam( "startDate" ) String startDate,
            @FormParam( "endDate" ) String endDate,
            @FormParam( "transactionStatus" ) String transactionStatus,
            @FormParam( "distributorCode" ) String distributorCode,
            @FormParam( "parentDistributorYes" ) Integer parentDistributorYes,
            @FormParam( "startIndex" ) Integer startIndex,
            @FormParam( "limit" ) Integer limit) throws JSONException {

        PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = paymentFunction.listTransactions( merchantCode, cardPan, terminalId, minumumAmount, maximumAmount, serviceType,
                    startRoute, endRoute, tollLane, trafficScheme, startDate, endDate, transactionStatus, 
                    distributorCode, parentDistributorYes, startIndex, limit, token, requestId);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }

    
    @GET
    @Path( "getCurrencyList" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getCurrencyList(
    		@Context HttpHeaders httpHeaders
            ) throws JSONException {

    	
        try {
        	log.info("Proceed 1");
        	JSONObject jscurrencies = new JSONObject();
        	NFRACurrency[] nfraCurrencies = NFRACurrency.values();
        	for(int i=0; i<nfraCurrencies.length; i++)
        	{
        		jscurrencies.put(i+"", nfraCurrencies[i].name());
        	}
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "status", ERROR.GENERAL_OK );
        	jsonObject.add( "nrfaCurrencyList", jscurrencies.toString() );
        	jsonObject.add( "message", "Card Types Listed" );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    @POST
    @Path( "requestTransactionReversal" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response requestTransactionReversal(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "paymentRef" ) String paymentRef,
            @FormParam( "orderId" ) String orderId,
            @FormParam( "transactionAmount" ) Double transactionAmount,
            @FormParam( "reversalAmount" ) Double reversalAmount,
            @FormParam( "transactionReason" ) String transactionReason) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = paymentFunction.requestTransactionReversal( paymentRef, orderId, transactionAmount, reversalAmount, 
            		transactionReason, token, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    @POST
    @Path( "reverseTransaction" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response reverseTransaction(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "paymentRef" ) String paymentRef,
            @FormParam( "transactionAmount" ) Double transactionAmount,
            @FormParam( "reversalAmount" ) Double reversalAmount,
            @FormParam( "reversalId" ) String reversalId) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = paymentFunction.reverseTransaction( paymentRef, transactionAmount, reversalAmount, reversalId, token, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    
    @GET
    @Path( "getServiceTypes" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getServiceTypes(
    		@Context HttpHeaders httpHeaders
            ) throws JSONException {

    	
        try {
        	log.info("Proceed 1");
        	JSONObject jsServiceTypes = new JSONObject();
        	ServiceType[] serviceTypes = ServiceType.values();
        	for(int i=0; i<serviceTypes.length; i++)
        	{
        		jsServiceTypes.put(i+"", serviceTypes[i].name());
        	}
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "status", ERROR.GENERAL_OK );
        	jsonObject.add( "serviceTypeList", jsServiceTypes.toString() );
        	jsonObject.add( "message", "Service Types Listed" );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    @POST
    @Path( "createNewTrafficTollRouteIdentifers" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response createNewTrafficTollRouteIdentifers (
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
    		@FormParam( "district" )  String district,
    		@FormParam( "province" )  String province,
    		@FormParam( "city" )  String city,
    		@FormParam( "startRouteName" )  String startRouteName,
    		@FormParam( "endRouteName" )  String endRouteName,
    		@FormParam( "routeCode" )  String routeCode
    		
            ) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
    	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    	
    	PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = paymentFunction.createNewTrafficTollRouteIdentifers( district, province, city, startRouteName, endRouteName, routeCode, 
            		token, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    
    @POST
    @Path( "getTrafficTollRouteIdentifers" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getTrafficTollRouteIdentifers (
    		@Context HttpHeaders httpHeaders
            ) throws JSONException {

    	log.info("-------------------------------");
    	log.info("Start Get Traffic Toll Route Identifiers");
    	
    	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    	List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;
    	
		try{
			if(token==null)
        	{
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
			
			
			swpService = serviceLocator.getSwpService();
			Application app = Application.getInstance(swpService);
			
			String hql = "Select tp from TrafficTollRoute tp";
			Collection<TrafficTollRoute> trafficTollRoutes = (Collection<TrafficTollRoute>)this.swpService.getAllRecordsByHQL(hql);
			if(trafficTollRoutes==null)
			{
				jsonObject.add("status", ERROR.NO_CARD_SCHEMES);
				jsonObject.add("message", "No Traffic Toll Routes Available");
				JsonObject jsonObj = jsonObject.build();
	            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.NO_CONTENT ).entity( jsonObj.toString() ).build();
			}
			
			Iterator<TrafficTollRoute> iterator = trafficTollRoutes.iterator();
			JSONArray trafficTollRouteArray = new JSONArray();
			while(iterator.hasNext())
			{
				TrafficTollRoute trafficTollRoute = iterator.next();
				JSONObject trafficTollRouteObj = new JSONObject();
				trafficTollRouteObj.put("startRouteName", trafficTollRoute.getStartRouteName());
				trafficTollRouteObj.put("endRouteName", trafficTollRoute.getEndRouteName());
				trafficTollRouteObj.put("city", trafficTollRoute.getCity());
				trafficTollRouteObj.put("district", trafficTollRoute.getDistrict());
				trafficTollRouteObj.put("province", trafficTollRoute.getProvince());
				trafficTollRouteObj.put("routeCode", trafficTollRoute.getRouteCode());
				trafficTollRouteObj.put("status", trafficTollRoute.getStatus().equals(Boolean.TRUE) ? "Active" : "Inactive");
				trafficTollRouteArray.put(trafficTollRouteObj);
			}
			
			jsonObject.add("message", "Traffic Routes Listed");
			jsonObject.add("trafficTollRouteList", trafficTollRouteArray.toString());
			jsonObject.add("status", ERROR.GENERAL_OK);
			JsonObject jsonObj = jsonObject.build();
            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();
			
		}catch(Exception e)
		{
			log.warn(e);
			e.printStackTrace();
			JsonObject jsonObj = jsonObject.build();
            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
		}
    }
    
    
    
    @POST
    @Path( "createNewTollLane" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response createNewTollLane (
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
    		@FormParam( "routeCode" )  String routeCode,
    		@FormParam( "tollLane" )  Integer tollLane
    		
            ) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
    	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    	
    	PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = paymentFunction.createNewTollLane( routeCode, tollLane, token, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    @POST
    @Path( "getTollLanes" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getTollLanes (
    		@Context HttpHeaders httpHeaders,
    		@FormParam( "trafficTollRouteCode" )  String trafficTollRouteCode
            ) throws JSONException {

    	
    	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    	
    	
		try{
			
			swpService = serviceLocator.getSwpService();
			Application app = Application.getInstance(swpService);
			
			String hql = "Select tp from TrafficTollRouteLane tp WHERE tp.routeCode = '" + trafficTollRouteCode.toLowerCase() + "'";
			Collection<TrafficTollRouteLane> trafficTollRouteLanes = (Collection<TrafficTollRouteLane>)this.swpService.getAllRecordsByHQL(hql);
			if(trafficTollRouteLanes==null)
			{
				jsonObject.add("status", ERROR.NO_CARD_SCHEMES);
				jsonObject.add("message", "No Traffic Toll Routes Lanes Available");
				JsonObject jsonObj = jsonObject.build();
	            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.NO_CONTENT ).entity( jsonObj.toString() ).build();
			}
			
			Iterator<TrafficTollRouteLane> iterator = trafficTollRouteLanes.iterator();
			JSONArray trafficTollRouteArray = new JSONArray();
			while(iterator.hasNext())
			{
				TrafficTollRouteLane trafficTollRouteLane = iterator.next();
				JSONObject trafficTollRouteObj = new JSONObject();
				trafficTollRouteObj.put("startRouteName", trafficTollRouteLane.getStartRouteName());
				trafficTollRouteObj.put("endRouteName", trafficTollRouteLane.getEndRouteName());
				trafficTollRouteObj.put("tollLane", trafficTollRouteLane.getTollLane());
				trafficTollRouteObj.put("routeCode", trafficTollRouteLane.getRouteCode());
				trafficTollRouteObj.put("status", trafficTollRouteLane.getStatus().equals(Boolean.TRUE) ? "Active" : "Inactive");
				trafficTollRouteArray.put(trafficTollRouteObj);
			}
			
			jsonObject.add("message", "Traffic Route Lanes for Route Code "+trafficTollRouteCode+" Listed ");
			jsonObject.add("tollLaneList", trafficTollRouteArray.toString());
			jsonObject.add("status", ERROR.GENERAL_OK);
			JsonObject jsonObj = jsonObject.build();
            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();
			
		}catch(Exception e)
		{
			log.warn(e);
			e.printStackTrace();
			JsonObject jsonObj = jsonObject.build();
            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
		}
    }
    
    
    
    
    @POST
    @Path( "createNewTrafficScheme" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response createNewTrafficScheme (
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
    		@FormParam( "schemeAmount" )  Double schemeAmount,
    		@FormParam( "schemeCode" )  String schemeCode,
    		@FormParam( "schemeDetail" )  String schemeDetail,
    		@FormParam( "schemeName" )  String schemeName,
    		@FormParam( "currency" )  String currency
    		
            ) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
    	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    	
    	PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = paymentFunction.createNewTrafficScheme( schemeAmount, schemeCode, schemeDetail, schemeName, token, requestId, ipAddress, currency);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    
    @POST
    @Path( "getTrafficSchemes" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getTrafficSchemes (
    		@Context HttpHeaders httpHeaders
            ) throws JSONException {

    	
    	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    	
    	
		try{
			
			swpService = serviceLocator.getSwpService();
			Application app = Application.getInstance(swpService);
			
			String hql = "Select tp from TrafficScheme tp";
			Collection<TrafficScheme> trafficSchemes = (Collection<TrafficScheme>)this.swpService.getAllRecordsByHQL(hql);
			if(trafficSchemes==null)
			{
				jsonObject.add("status", ERROR.NO_CARD_SCHEMES);
				jsonObject.add("message", "No Traffic Schemes Available");
				JsonObject jsonObj = jsonObject.build();
	            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.NO_CONTENT ).entity( jsonObj.toString() ).build();
			}
			
			Iterator<TrafficScheme> iterator = trafficSchemes.iterator();
			JSONArray trafficSchemeArray = new JSONArray();
			while(iterator.hasNext())
			{
				TrafficScheme trafficScheme = iterator.next();
				JSONObject trafficTollRouteObj = new JSONObject();
				trafficTollRouteObj.put("schemeAmount", trafficScheme.getSchemeAmount());
				trafficTollRouteObj.put("schemeCode", trafficScheme.getSchemeCode());
				trafficTollRouteObj.put("schemeDetail", trafficScheme.getSchemeDetail());
				trafficTollRouteObj.put("schemeName", trafficScheme.getSchemeName());
				trafficTollRouteObj.put("status", trafficScheme.getTrafficSchemeStatus().equals(Boolean.TRUE) ? "Active" : "Inactive");
				trafficSchemeArray.put(trafficTollRouteObj);
			}
			
			jsonObject.add("message", "Traffic Schemes Listed ");
			jsonObject.add("trafficSchemeList", trafficSchemeArray.toString());
			jsonObject.add("status", ERROR.GENERAL_OK);
			JsonObject jsonObj = jsonObject.build();
            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();
			
		}catch(Exception e)
		{
			log.warn(e);
			e.printStackTrace();
			JsonObject jsonObj = jsonObject.build();
            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
		}
    }
    
    
    
    @POST
    @Path( "getTollFeePrice" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getTollFeePrice (
    		@Context HttpHeaders httpHeaders,
    		@FormParam( "trafficTollRouteCode" )  String trafficTollRouteCode,
    		@FormParam( "tollLaneCode" )  String tollLaneCode,
    		@FormParam( "trafficScheme" )  String trafficScheme
            ) throws JSONException {

    	
    	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
    	
    	
		try{
			
			swpService = serviceLocator.getSwpService();
			Application app = Application.getInstance(swpService);
			
			String hql = "Select tp from TrafficTollPrice tp ";
			String sql = "";
			if(trafficTollRouteCode!=null)
				sql = sql + (sql!="" ? "AND " :"WHERE ") + "tp.routeCode = '" + trafficTollRouteCode + "' ";
			if(tollLaneCode!=null)
				sql = sql + (sql!="" ? "AND " :"WHERE ") + "tp.tollLane = '" + tollLaneCode + "' ";
			if(trafficScheme!=null)
				sql = sql + (sql!="" ? "AND " :"WHERE ") + "tp.trafficSchemeCode = '" + trafficScheme + "' ";
			
			sql = sql + (sql!="" ? "AND " :"WHERE ") + "tp.status = 1";
			hql = hql + sql;
			
			
			Collection<TrafficTollPrice> trafficTollPrices = (Collection<TrafficTollPrice>)this.swpService.getAllRecordsByHQL(hql);
			if(trafficTollPrices==null)
			{
				jsonObject.add("status", ERROR.NO_CARD_SCHEMES);
				jsonObject.add("message", "No Traffic Toll Prices Available");
				JsonObject jsonObj = jsonObject.build();
	            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.NO_CONTENT ).entity( jsonObj.toString() ).build();
			}
			
			Iterator<TrafficTollPrice> iterator = trafficTollPrices.iterator();
			JSONArray trafficTollPriceArray = new JSONArray();
			while(iterator.hasNext())
			{
				TrafficTollPrice trafficTollPrice = iterator.next();
				JSONObject trafficTollPriceObj = new JSONObject();
				trafficTollPriceObj.put("startRouteName", trafficTollPrice.getStartRouteName());
				trafficTollPriceObj.put("endRouteName", trafficTollPrice.getEndRouteName());
				trafficTollPriceObj.put("tollLane", trafficTollPrice.getTollLane());
				trafficTollPriceObj.put("feePrice", trafficTollPrice.getFeePrice());
				trafficTollPriceObj.put("routeCode", trafficTollPrice.getRouteCode());
				trafficTollPriceObj.put("status", trafficTollPrice.getStatus().equals(Boolean.TRUE) ? "Active" : "Inactive");
				trafficTollPriceArray.put(trafficTollPriceObj);
			}
			
			jsonObject.add("message", "Traffic Route Lane Prices for Route Code "+trafficTollRouteCode+" Listed ");
			jsonObject.add("tollLaneList", trafficTollPriceArray.toString());
			jsonObject.add("status", ERROR.GENERAL_OK);
			JsonObject jsonObj = jsonObject.build();
            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.OK ).entity( jsonObj.toString() ).build();
			
		}catch(Exception e)
		{
			log.warn(e);
			e.printStackTrace();
			JsonObject jsonObj = jsonObject.build();
            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
		}
    }
    
    
    
    @POST
    @Path( "createTollFeePrice" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response createTollFeePrice(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "routeCode" ) String routeCode,
            @FormParam( "tollLaneCode" ) Integer tollLaneCode,
            @FormParam( "trafficScheme" ) String trafficScheme,
            @FormParam( "feePrice" ) Double feePrice) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = paymentFunction.
            		createTollFeePrice(routeCode, tollLaneCode, trafficScheme, feePrice, token, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    @POST
    @Path( "updateTollFeePrice" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response updateTollFeePrice(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "routeCode" ) String routeCode,
            @FormParam( "tollLane" ) Integer tollLane,
            @FormParam( "trafficScheme" ) String trafficScheme,
            @FormParam( "feePrice" ) Double feePrice) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = paymentFunction.
            		updateTollFeePrice(routeCode, tollLane, trafficScheme, feePrice, token, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    
    @POST
    @Path( "eodTransactionsNotification" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response eodTransactionsNotification(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "routeCode" ) String routeCode,
            @FormParam( "tollLaneCode" ) Integer tollLane,
            @FormParam( "trafficScheme" ) String trafficScheme,
            @FormParam( "feePrice" ) Double feePrice) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = paymentFunction.
            		updateTollFeePrice(routeCode, tollLane, trafficScheme, feePrice, token, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    
    
    
    
    
    @POST
    @Path( "generateEODTransactionList" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response generateEODTransactionList(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "merchantCode" ) String merchantCode,
            @FormParam( "eodDate" ) String eodDate) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = paymentFunction.generateEODTransactionList(merchantCode, token, eodDate, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    

    @GET
    @Path( "getChannelList" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getChannelList(
    		@Context HttpHeaders httpHeaders
            ) throws JSONException {

    	PaymentFunction paymentFunction = PaymentFunction.getInstance();
        try {
        	log.info("Proceed 1");
        	
        	
            Response authResponse = paymentFunction.getChannelList(  );
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    @GET
    @Path( "getTransactionStatusList" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getTransactionStatusList(
    		@Context HttpHeaders httpHeaders
            ) throws JSONException {

    	PaymentFunction paymentFunction = PaymentFunction.getInstance();
        try {
        	log.info("Proceed 1");
        	
        	
            Response authResponse = paymentFunction.getTransactionStatusList(  );
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    @POST
    @Path( "receiveEODTransactionList" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response receiveEODTransactionList(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "payload" ) String payload) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        PaymentFunction paymentFunction = PaymentFunction.getInstance();
        List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
        String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        try {
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 12");
        	
            Response authResponse = paymentFunction.receiveEODTransactionList(payload, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
}
