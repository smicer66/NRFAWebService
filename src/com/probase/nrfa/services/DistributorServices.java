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

import org.json.JSONException;
import org.json.JSONObject;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.probase.nrfa.authenticator.CardFunction;
import com.probase.nrfa.authenticator.DistributorFunction;
import com.probase.nrfa.authenticator.HttpHeaderNames;
import com.probase.nrfa.authenticator.MerchantFunction;
import com.probase.nrfa.authenticator.TerminalFunction;
import com.probase.nrfa.enumerations.AccountStatus;
import com.probase.nrfa.enumerations.AccountType;
import com.probase.nrfa.enumerations.CardStatus;
import com.probase.nrfa.enumerations.CardType;
import com.probase.nrfa.enumerations.Channel;
import com.probase.nrfa.enumerations.CustomerStatus;
import com.probase.nrfa.enumerations.DeviceStatus;
import com.probase.nrfa.enumerations.DistributorType;
import com.probase.nrfa.enumerations.FundingAccountType;
import com.probase.nrfa.enumerations.Gender;
import com.probase.nrfa.enumerations.MobileAccountStatus;
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
import com.probase.nrfa.models.Transaction;
import com.probase.nrfa.models.User;
import com.probase.nrfa.util.Application;
import com.probase.nrfa.util.ERROR;
import com.probase.nrfa.util.PrbCustomService;
import com.probase.nrfa.util.ServiceLocator;
import com.probase.nrfa.util.SwpService;
import com.probase.nrfa.util.UtilityHelper;

@Path( "services/DistributorServices" )
@Stateless( name = "DistributorService", mappedName = "services/DistributorServices" )
public class DistributorServices {
	private static Logger log = Logger.getLogger(DistributorServices.class);
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
    @Path( "createDistributor" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response createDistributor(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "contactEmail" ) String contactEmail,
            @FormParam( "contactMobile" ) String contactMobile,
            @FormParam( "address" ) String address,
            @FormParam( "city" ) String city,
            @FormParam( "district" ) String district,
            @FormParam( "province" ) String province,
            @FormParam( "companyName" ) String companyName,
            @FormParam( "companyRegNo" ) String companyRegNo,
            @FormParam( "distributorStatus" ) String distributorStatus,
            @FormParam( "distributorCode" ) String distributorCode,
            @FormParam( "editFlag" ) Boolean editFlag,
            @FormParam( "fundingAccountType" ) String fundingAccountType,
            @FormParam( "distributorType" ) String distributorType,
            @FormParam( "contactFullName" ) String contactFullName,
            @FormParam( "merchantCode" ) String merchantCode,
            @FormParam( "corporateBankAccountNumber" ) String corporateBankAccountNumber,
            @FormParam( "thresholdValue" ) Double thresholdValue,
            @FormParam( "parentDistributorCode" ) String parentDistributorCode) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        DistributorFunction distributorFunction = DistributorFunction.getInstance();
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
        	
            Response authResponse = distributorFunction.createDistributor(contactEmail, contactMobile, address, city, district, province, companyName, 
                    companyRegNo, distributorStatus, distributorCode, editFlag, fundingAccountType, distributorType, contactFullName, merchantCode, corporateBankAccountNumber,
                    thresholdValue, parentDistributorCode, token, requestId, ipAddress );

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
    @Path( "creditDistributorAccount" )
    @Produces( MediaType.APPLICATION_JSON )	
    public Response creditDistributorAccount(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "distributorCode" ) String distributorCode,
            @FormParam( "amount" ) Double amount,
            @FormParam( "hash" ) String hash, 
            @FormParam( "orderId" ) String orderId, 
            @FormParam( "serviceType" ) String serviceType, 
            @FormParam( "terminalId" ) String terminalId) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        DistributorFunction distributorFunction = DistributorFunction.getInstance();
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
        	
            Response authResponse = distributorFunction.creditDistributorAccount(distributorCode, amount, hash, 
            		orderId, serviceType, terminalId, token, requestId, ipAddress );

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
    @Path( "assignCardsToDistributor" )
    @Produces( MediaType.APPLICATION_JSON )	
    public Response assignCardsToDistributor(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "distributorCode" ) String distributorCode,
            @FormParam( "cardList" ) String cardList) throws JSONException {

    	String ipAddress = requestContext.getRemoteAddr();
        DistributorFunction distributorFunction = DistributorFunction.getInstance();
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
        	
            Response authResponse = distributorFunction.assignCardsToDistributor(distributorCode, cardList, token, requestId, ipAddress );

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
    @Path( "listDistributors" )
    @Produces( MediaType.APPLICATION_JSON )	
    public Response listDistributors(
    		@Context HttpHeaders httpHeaders,
            @FormParam( "merchantCode" ) String merchantCode,
            @FormParam( "startIndex" ) Integer startIndex,
            @FormParam( "limit" ) Integer limit) throws JSONException {

        DistributorFunction distributorFunction = DistributorFunction.getInstance();
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
        	
            Response authResponse = distributorFunction.listDistributors(merchantCode, startIndex, limit, token, requestId );

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
    @Path( "getDistributorTypes" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getDistributorTypes(
    		@Context HttpHeaders httpHeaders
            ) throws JSONException {

    	
        try {
        	log.info("Proceed 1");
        	JSONObject jsDistributorTypes = new JSONObject();
        	DistributorType[] distributorTypes = DistributorType.values();
        	for(int i=0; i<distributorTypes.length; i++)
        	{
        		jsDistributorTypes.put(i+"", distributorTypes[i].name());
        	}
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "status", ERROR.GENERAL_OK );
        	jsonObject.add( "distributorTypeList", jsDistributorTypes.toString() );
        	jsonObject.add( "message", "Distributor Types Listed" );
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
    
    
    @GET
    @Path( "getFundingAccountTypes" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getFundingAccountTypes(
    		@Context HttpHeaders httpHeaders
            ) throws JSONException {

    	
        try {
        	log.info("Proceed 1");
        	JSONObject jsFundingAccountTypes = new JSONObject();
        	FundingAccountType[] fundingAccountTypes = FundingAccountType.values();
        	for(int i=0; i<fundingAccountTypes.length; i++)
        	{
        		jsFundingAccountTypes.put(i+"", fundingAccountTypes[i].name());
        	}
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "status", ERROR.GENERAL_OK );
        	jsonObject.add( "fundingAccountTypeList", jsFundingAccountTypes.toString() );
        	jsonObject.add( "message", "Funding Account Types Listed" );
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
    @Path( "updateCardSms" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response updateCardSms(@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
    		@FormParam( "cardPan" ) String cardPan,  @FormParam( "cardUniqueId" ) String cardUniqueId, 
    		@FormParam( "customerMobile" ) String customerMobile,  @FormParam( "distributorCode" ) String distributorCode, 
    		@FormParam( "distributorTxnHash" ) String distributorTxnHash,
    		@FormParam("disableSms") Integer disableSMS
    		)
	{
    	String ipAddress = requestContext.getRemoteAddr();
    	DistributorFunction distributorFunction = DistributorFunction.getInstance();
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
        	
            Response authResponse = distributorFunction.disableCardSms(cardPan, cardUniqueId, customerMobile, distributorCode, 
            		distributorTxnHash, token, requestId, ipAddress, disableSMS);

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
    @Path( "assignCardsToCustomer" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response assignCardsToCustomer(@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
    		@FormParam( "cardPan" ) String cardPan,  @FormParam( "cardUniqueId" ) String cardUniqueId, 
    		@FormParam( "customerMobile" ) String customerMobile,  @FormParam( "distributorCode" ) String distributorCode, 
    		@FormParam( "firstName" ) String firstName, @FormParam( "lastName" ) String lastName, 
    		@FormParam( "otherName" ) String otherName, @FormParam( "contactEmail" ) String contactEmail, 
    		@FormParam( "customerType" ) String customerType, @FormParam( "distributorTxnHash" ) String distributorTxnHash, 
    		@FormParam( "distributorRoleCode" ) String distributorRoleCode, @FormParam( "orderId" ) String orderId, 
    		@FormParam( "distributorTerminalId" ) String distributorTerminalId, @FormParam( "amount" ) Double amount, 
    		@FormParam( "narration" ) String narration, @FormParam( "extraDetails" ) String extraDetails, 
    		@FormParam( "distributorChannel" ) String distributorChannel, 
    		@FormParam( "vehicleRegNumber" ) String vehicleRegNumber,
    		@FormParam( "nrfaCurrency" ) String nrfaCurrency,
    		@FormParam("alternativeCustomerMobile1") String alternativeCustomerMobile1,
    		@FormParam("alternativeCustomerMobile2") String alternativeCustomerMobile2,
    		@FormParam("disableSms") Integer disableSMS
    		)
	{
    	String ipAddress = requestContext.getRemoteAddr();
    	DistributorFunction distributorFunction = DistributorFunction.getInstance();
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
        	
            Response authResponse = distributorFunction.assignCardsToCustomer(cardPan, cardUniqueId, customerMobile, distributorCode, firstName, lastName, otherName, 
        			contactEmail, customerType, distributorTxnHash, distributorRoleCode,  orderId, distributorTerminalId, amount, narration, 
            		extraDetails, distributorChannel, vehicleRegNumber, nrfaCurrency, token, requestId, ipAddress, alternativeCustomerMobile1, alternativeCustomerMobile2, 
            		disableSMS);

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
    @Path( "updateCustomerDetails" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response updateCustomerDetails(@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
    		@FormParam( "verificationNumber" ) String verificationNumber, 
    		@FormParam( "customerMobile" ) String customerMobile,  
    		@FormParam( "firstName" ) String firstName, @FormParam( "lastName" ) String lastName, 
    		@FormParam( "otherName" ) String otherName, @FormParam( "contactEmail" ) String contactEmail, 
    		@FormParam("alternativeCustomerMobile1") String alternativeCustomerMobile1,
    		@FormParam("alternativeCustomerMobile2") String alternativeCustomerMobile2
    		)
	{
    	String ipAddress = requestContext.getRemoteAddr();
    	DistributorFunction distributorFunction = DistributorFunction.getInstance();
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
        	
            Response authResponse = distributorFunction.updateCustomerDetails(verificationNumber, customerMobile, firstName, lastName, otherName, 
        			contactEmail, token, requestId, ipAddress, alternativeCustomerMobile1, alternativeCustomerMobile2);

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
    @Path( "getDistributorCount" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getDistributorCount(
    		@Context HttpHeaders httpHeaders,
            @FormParam( "distributorStatus" ) String distributorStatus,
            @FormParam( "merchantCode" ) String merchantCode
            ) throws JSONException {

    	DistributorFunction distributorFunction = DistributorFunction.getInstance();
        try {
        	log.info("Proceed 1");
        	
        	
            Response authResponse = distributorFunction.getDistributorCount(distributorStatus, merchantCode);
            

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
