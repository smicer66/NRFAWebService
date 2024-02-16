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
import com.probase.nrfa.authenticator.DistributorFunction;
import com.probase.nrfa.authenticator.HttpHeaderNames;
import com.probase.nrfa.authenticator.MerchantFunction;
import com.probase.nrfa.authenticator.PaymentFunction;
import com.probase.nrfa.authenticator.ReportFunction;
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
import com.sun.jersey.api.client.ClientResponse.Status;

@Path( "services/ReportService" )
@Stateless( name = "ReportService", mappedName = "services/ReportService" )
public class ReportService {

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
            @FormParam( "orderBy" ) String orderBy,
            @FormParam( "orderByPreference" ) String orderByPreference,
            @FormParam( "paymentMeans" ) String paymentMeans,
            @FormParam( "currency" ) String currency,
            @FormParam( "vehicleRegNo" ) String vehicleRegNo) throws JSONException {

        ReportFunction reportFunction = ReportFunction.getInstance();
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
        	
            Response authResponse = reportFunction.listTransactions( merchantCode, cardPan, terminalId, minumumAmount, maximumAmount, serviceType,
                    startRoute, endRoute, tollLane, trafficScheme, startDate, endDate, transactionStatus, orderBy, orderByPreference, vehicleRegNo, paymentMeans, currency, token, requestId);
            

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
    @Path( "getTransactionFieldsList" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getChannelList(
    		@Context HttpHeaders httpHeaders
            ) throws JSONException {

    	ReportFunction reportFunction = ReportFunction.getInstance();
        try {
        	log.info("Proceed 1");
        	
        	
            Response authResponse = reportFunction.getTransactionFieldsList(  );
            

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
    @Path( "getNFRACurrencyList" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getNFRACurrencyList(
    		@Context HttpHeaders httpHeaders
            ) throws JSONException {

    	ReportFunction reportFunction = ReportFunction.getInstance();
        try {
        	log.info("Proceed 1");
        	
        	
            Response authResponse = reportFunction.getNFRACurrencyList(  );
            

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
            @FormParam( "batchId" ) String batchId, 
            @FormParam( "assignedToDistributorYes" ) Boolean assignedToDistributorYes, 
            @FormParam( "distributorCode" ) String distributorCode, 
            @FormParam( "bankCode" ) String bankCode, 
            @FormParam( "cardStatus" ) String cardStatus, 
            @FormParam( "schemeCode" ) String schemeCode) throws JSONException {

        

        try {
        	String ipAddress = requestContext.getRemoteAddr();
        	ReportFunction reportFunction = ReportFunction.getInstance();
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
        	
            Response authResponse = reportFunction.getCardList( merchantCode, batchId, token, assignedToDistributorYes, cardStatus, distributorCode, bankCode, schemeCode,
            		requestId, ipAddress );
            

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
    @Path( "getMerchantList" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response getMerchantList(
    		@Context HttpHeaders httpHeaders) throws JSONException {

    	ReportFunction reportFunction = ReportFunction.getInstance();
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
        	
            Response authResponse = reportFunction.getMerchantList( token, requestId );

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
            @FormParam( "merchantCode" ) String merchantCode) throws JSONException {

        ReportFunction reportFunction = ReportFunction.getInstance();
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
        	
            Response authResponse = reportFunction.listDistributors(merchantCode, token, requestId );

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
    @Path( "getTollPlazaByTransactionCountAndValue" )
    @Produces( MediaType.APPLICATION_JSON )	
    public Response getTollPlazaByTransactionCountAndValue(
    		@Context HttpHeaders httpHeaders,
            @FormParam( "routeCode" ) String routeCode,
            @FormParam( "startDate" ) String startDate,
            @FormParam( "endDate" ) String endDate) throws JSONException {

        ReportFunction reportFunction = ReportFunction.getInstance();
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
        	
            Response authResponse = reportFunction.getTollPlazaByTransactionCountAndValue( routeCode, startDate, endDate, token, requestId);;

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
    @Path( "getMerchantsByTransactionCountAndValue" )
    @Produces( MediaType.APPLICATION_JSON )	
    public Response getMerchantsByTransactionCountAndValue(
    		@Context HttpHeaders httpHeaders,
            @FormParam( "merchantCode" ) String merchantCode,
            @FormParam( "startDate" ) String startDate,
            @FormParam( "endDate" ) String endDate) throws JSONException {

        ReportFunction reportFunction = ReportFunction.getInstance();
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
        	
            Response authResponse = reportFunction.getMerchantsByTransactionCountAndValue( merchantCode, startDate, endDate, token, requestId);;

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
