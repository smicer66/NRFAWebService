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
import com.probase.nrfa.authenticator.UtilityFunction;
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

@Path( "services/UtilityServices" )
@Stateless( name = "UtilityService", mappedName = "services/UtilityServices" )
public class UtilityServices {

	private static Logger log = Logger.getLogger(UtilityServices.class);
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
    @Path( "createNewBank" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response createNewBank(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "bankName" ) String bankName,
            @FormParam( "primaryFqdn" ) String primaryFqdn,
            @FormParam( "bankCode" ) String bankCode ) throws JSONException {

        

        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	UtilityFunction utilityFunction = UtilityFunction.getInstance();
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
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
            Response authResponse = utilityFunction.createNewBank( bankName, primaryFqdn, bankCode, token, requestId, ipAddress );
            

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
    @Path( "getBanks" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getBanks(
    		@Context HttpHeaders httpHeaders) throws JSONException {

    	UtilityFunction utilityFunction = UtilityFunction.getInstance();

        try {
        	log.info("Proceed 1");
        	
            Response authResponse = utilityFunction.getBanks();
            

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
    @Path( "createPromotion" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response createPromotion(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "description" ) String description,
            @FormParam( "cardScheme" ) String cardScheme,
            @FormParam( "startDate" ) String startDate,
            @FormParam( "endDate" ) String endDate,
            @FormParam( "baseRationaleCount" ) Integer baseRationaleCount,
            @FormParam( "discountPercentageApplicable" ) Double discountPercentageApplicable,
            @FormParam( "trafficScheme" ) String trafficScheme,
            @FormParam( "promotionName" ) String promotionName,
            @FormParam( "routeCode" ) String routeCode ) throws JSONException {

        

        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	UtilityFunction utilityFunction = UtilityFunction.getInstance();
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
        	
        	if(startDate==null || endDate==null || routeCode==null || baseRationaleCount==null || discountPercentageApplicable==null || (trafficScheme!=null && cardScheme!=null) 
        			 || (trafficScheme==null && cardScheme==null))
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.INCOMPLETE_PARAMETERS );
            	jsonObject.add( "message", "Parameters provided are invalid. Consult the API docs for guidance" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
            Response authResponse = utilityFunction.createPromotion( promotionName, description, cardScheme, startDate, endDate, baseRationaleCount, discountPercentageApplicable, 
            		trafficScheme, routeCode, token, requestId, ipAddress );
            

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
    @Path( "deletePromotion" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response deletePromotion(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
    		@FormParam( "promotionCode" ) String promotionCode) throws JSONException {

        

        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	UtilityFunction utilityFunction = UtilityFunction.getInstance();
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
        	
        	if(promotionCode==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.INCOMPLETE_PARAMETERS );
            	jsonObject.add( "message", "Parameters provided are invalid. Consult the API docs for guidance" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
            Response authResponse = utilityFunction.deletePromotion(promotionCode, token, requestId, ipAddress );
            

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
    @Path( "getPromotions" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getPromotions(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext) throws JSONException {

    	try {
        	log.info("Proceed 1");
        	String ipAddress = requestContext.getRemoteAddr();
        	UtilityFunction utilityFunction = UtilityFunction.getInstance();
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
            Response authResponse = utilityFunction.getPromotions(token);
            

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
    @Path( "createTraficException" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response createTraficException(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "vehicleRegNo" ) String vehicleRegNo,
            @FormParam( "carOwnerDetails" ) String carOwnerDetails,
            @FormParam( "discountPercentageApplicable" ) Double discountPercentageApplicable,
            @FormParam( "routeCode" ) String routeCode,
            @FormParam( "cardPan" ) String cardPan,
            @FormParam( "userFullName" ) String userFullName,
            @FormParam( "userMobileNo" ) String userMobileNo ) throws JSONException {

        

        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	UtilityFunction utilityFunction = UtilityFunction.getInstance();
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
        	
        	if(carOwnerDetails==null || discountPercentageApplicable==null || routeCode==null || userFullName==null || userMobileNo==null)
        	{
            	JsonObjectBuilder jsonObject = Json.createObjectBuilder();
            	jsonObject.add( "status", ERROR.INCOMPLETE_PARAMETERS );
            	jsonObject.add( "message", "Parameters provided are invalid. Consult the API docs for guidance" );
                JsonObject jsonObj = jsonObject.build();
        		return UtilityHelper.getNoCacheResponseBuilder( Response.Status.UNAUTHORIZED ).entity( jsonObj.toString() ).build();
        	}
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
            Response authResponse = utilityFunction.createException(userMobileNo, userFullName, carOwnerDetails, vehicleRegNo, discountPercentageApplicable, routeCode, cardPan, token, requestId, ipAddress );
            

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
    @Path( "getTrafficExceptions" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getTrafficExceptions(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
            @FormParam( "routeCode" ) String routeCode) throws JSONException {

    	try {
        	log.info("Proceed 1");
        	String ipAddress = requestContext.getRemoteAddr();
        	UtilityFunction utilityFunction = UtilityFunction.getInstance();
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
            Response authResponse = utilityFunction.getTrafficExceptions(token, routeCode);
            

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
    @Path( "deleteTrafficException" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response deleteTrafficException(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext,
    		@FormParam( "userMobileNo" ) String userMobileNo,
            @FormParam( "routeCode" ) String routeCode) throws JSONException {

    	try {
        	log.info("Proceed 1");
        	String ipAddress = requestContext.getRemoteAddr();
        	UtilityFunction utilityFunction = UtilityFunction.getInstance();
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
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
            Response authResponse = utilityFunction.deleteTrafficException( userMobileNo, routeCode, token, requestId, ipAddress);
            

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
    @Path( "syncOutData" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response syncOutData(
    		@Context HttpHeaders httpHeaders,
    		@Context HttpServletRequest requestContext ) throws JSONException {

        

        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	UtilityFunction utilityFunction = UtilityFunction.getInstance();
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
        	String requestId = token.substring(token.length()-10) + "-" + (new SimpleDateFormat("YYYYMMddHHmmss").format(new Date())) + ": ";
        	log.info(requestId + "Proceed 1");
            Response authResponse = utilityFunction.syncOutData( token, requestId, ipAddress );
            

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
