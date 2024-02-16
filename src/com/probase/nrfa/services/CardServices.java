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
import com.probase.nrfa.authenticator.HttpHeaderNames;
import com.probase.nrfa.enumerations.AccountStatus;
import com.probase.nrfa.enumerations.AccountType;
import com.probase.nrfa.enumerations.CardStatus;
import com.probase.nrfa.enumerations.CardType;
import com.probase.nrfa.enumerations.Channel;
import com.probase.nrfa.enumerations.CustomerStatus;
import com.probase.nrfa.enumerations.DeviceStatus;
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

@Path( "services/CardServices" )
@Stateless( name = "CardService", mappedName = "services/CardServices" )
public class CardServices {

	private static Logger log = Logger.getLogger(CardServices.class);
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
    @Path( "addNewCard" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response addNewCard(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "cardType" ) String cardType,
            @FormParam( "cardScheme" ) String cardScheme,
            @FormParam( "quantity" ) Integer quantity,
            @FormParam( "merchantCode" ) String merchantCode,
            @FormParam( "branchCode" ) String branchCode) throws JSONException {

        

        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	CardFunction cardFunction = CardFunction.getInstance();
            List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
            String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;
            String requestId = token.substring(token.length()-10) + "-" +  (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
            
        	log.info(requestId + "Proceed 1");
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	
            Response authResponse = cardFunction.addNewCard( cardType, cardScheme, quantity, merchantCode, branchCode, token, requestId, ipAddress );
            

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
    @Path( "updateCard" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response updateCard(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "cardDetails" ) String cardDetails ) throws JSONException {

        

        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	CardFunction cardFunction = CardFunction.getInstance();
            List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
            String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	log.info("Proceed 1");
        	String requestId = token + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	
            Response authResponse = cardFunction.updateCard( cardDetails, token,requestId, ipAddress );
            

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
    @Path( "getCard" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getCard(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "cardPan" ) String cardPan,
            @FormParam( "cardUniqueId" ) String cardUniqueId ) throws JSONException {

        

        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	CardFunction cardFunction = CardFunction.getInstance();
            List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
            String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" +  (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = cardFunction.getCard( cardPan, cardUniqueId, token, requestId, ipAddress );
            

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
    @Path( "lastFiveTransactions" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response lastFiveTransactions(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "cardPan" ) String cardPan,
            @FormParam( "cardUniqueId" ) String cardUniqueId) throws JSONException {

        

        try {
        	CardFunction cardFunction = CardFunction.getInstance();
            List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
            String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;
            
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	log.info("Proceed 1");
        	log.info("Proceed 1");
        	String requestId = token + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	
            Response authResponse = cardFunction.getLastFiveTransactions( cardPan, cardUniqueId, token, requestId );
            

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
    @Path( "listCards" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response listCards(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "merchantCode" ) String merchantCode,
            @FormParam( "startIndex" ) Integer startIndex, 
            @FormParam( "limit" ) Integer limit, 
            @FormParam( "batchId" ) String batchId, 
            @FormParam( "assignedToDistributorYes" ) Boolean assignedToDistributorYes, 
            @FormParam( "distributorCode" ) String distributorCode, 
            @FormParam( "bankCode" ) String bankCode) throws JSONException {

        

        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	CardFunction cardFunction = CardFunction.getInstance();
            List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
            String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	log.info("Proceed 1");
        	String requestId = token + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	
            Response authResponse = cardFunction.getCardList( merchantCode, startIndex, limit, batchId, token, assignedToDistributorYes, distributorCode, bankCode, requestId, ipAddress );
            

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
    @Path( "assignUniqueIdToCard" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response assignUniqueIdToCard(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
    		@FormParam( "batchId" ) String batchId,
    		@FormParam( "cardList" ) String cardList) throws JSONException {

        

        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	CardFunction cardFunction = CardFunction.getInstance();
            List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
            String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	log.info("Proceed 1");
        	String requestId = token.substring(token.length()-10) + "-" +  (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	
            Response authResponse = cardFunction.assignUniqueIdToCard( batchId, cardList, token, requestId, ipAddress );
            

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
    @Path( "getListCardsByBatchIdWithNoUniqueId" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getListCardsByBatchIdWithNoUniqueId(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "batchId" ) String batchId,
            @FormParam( "startIndex" ) Integer startIndex, 
            @FormParam( "limit" ) Integer limit) throws JSONException {

        

        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	CardFunction cardFunction = CardFunction.getInstance();
            List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
            String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;
        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = cardFunction.getListCardsByBatchIdWithNoUniqueId( batchId, startIndex, limit, token, requestId, ipAddress );
            

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
    @Path( "getCardBatchIds" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getCardBatchIds(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext) throws JSONException {

        
        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	CardFunction cardFunction = CardFunction.getInstance();
            List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
            String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" +  (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 121");
        	
            Response authResponse = cardFunction.getCardBatchIds( token, requestId, ipAddress );
            

            return authResponse;

        } catch ( final Exception ex ) {
        	log.info("Proceed 122");
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    @POST
    @Path( "addNewCardScheme" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response addNewCardScheme(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "schemeName" ) String schemeName,
            @FormParam( "schemeDetails" ) String schemeDetails, 
            @FormParam( "extraCharges" ) Double extraCharges,  
            @FormParam( "updateFlag" ) Boolean updateFlag,  
            @FormParam( "schemeCode" ) String schemeCode,  
            @FormParam( "transactionFee" ) Double transactionFee
            ) throws JSONException {

        
        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	log.info("Proceed 1");
        	CardFunction cardFunction = CardFunction.getInstance();
            List<String> tokenList = httpHeaders.getRequestHeader(HttpHeaderNames.AUTH_TOKEN);
            String token = tokenList!=null && tokenList.size()>0 ? tokenList.get(0) : null;

        	if(token==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.UNAUTHORIZED_ACTION );
            	jsonObject.add( "message", "Invalid transaction" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
            String requestId = token.substring(token.length()-10) + "-" +  (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	
            Response authResponse = cardFunction.addNewCardScheme( schemeName, schemeDetails, extraCharges, updateFlag, schemeCode, transactionFee, token, requestId, ipAddress );
            

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
    @Path( "getCardTypes" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getCardTypes(
    		@Context HttpHeaders httpHeaders
            ) throws JSONException {

    	
        try {
        	log.info("Proceed 1");
        	JSONObject jsCardTypes = new JSONObject();
        	CardType[] cardTypes = CardType.values();
        	for(int i=0; i<cardTypes.length; i++)
        	{
        		jsCardTypes.put(i+"", cardTypes[i].name());
        	}
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "status", ERROR.GENERAL_OK );
        	jsonObject.add( "cardTypeList", jsCardTypes.toString() );
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
    
    
    @GET
    @Path( "getCardStatusList" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getCardStatusList(
    		@Context HttpHeaders httpHeaders
            ) throws JSONException {

    	
        try {
        	log.info("Proceed 1");
        	JSONObject jsCardStatuses = new JSONObject();
        	CardStatus[] cardStatuses = CardStatus.values();
        	for(int i=0; i<cardStatuses.length; i++)
        	{
        		jsCardStatuses.put(i+"", cardStatuses[i].name());
        	}
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "status", ERROR.GENERAL_OK );
        	jsonObject.add( "cardStatusList", jsCardStatuses.toString() );
        	jsonObject.add( "message", "Card Status Listed" );
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
    @Path( "getCardSchemes" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getCardSchemes(
    		@Context HttpHeaders httpHeaders
            ) throws JSONException {
    	String requestId = (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
    	CardFunction cardFunction = CardFunction.getInstance();
        try {
        	log.info(requestId + "Proceed 1");
        	
        	
            Response authResponse = cardFunction.getCardSchemes(  );
            

            return authResponse;

        } catch ( final Exception ex ) {
        	ex.printStackTrace();
        	log.warn(ex);
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
    
    
    
    
    @GET
    @Path( "getCardCountByStatus" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getCardCountByStatus(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "cardStatus" ) String cardStatus
            ) throws JSONException {
    	String requestId = (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
    	CardFunction cardFunction = CardFunction.getInstance();
        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	log.info(requestId + "Proceed 1");
        	
            Response authResponse = cardFunction.getCardCountByStatus(cardStatus, requestId, ipAddress);
            

            return authResponse;

        } catch ( final Exception ex ) {
        	log.warn(ex);
        	ex.printStackTrace();
        	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
        	jsonObject.add( "message", "Experienced a system server error " );
            JsonObject jsonObj = jsonObject.build();

            return UtilityHelper.getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).entity( jsonObj.toString() ).build();
        }
    }
}
