package com.probase.nrfa.util;

import java.util.Date;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONArray;

import com.google.gson.Gson;
import com.probase.nrfa.enumerations.AccountStatus;
import com.probase.nrfa.enumerations.DeviceType;
import com.probase.nrfa.enumerations.MerchantStatus;
import com.probase.nrfa.models.Bank;
import com.probase.nrfa.models.CardScheme;
import com.probase.nrfa.models.Merchant;
import com.probase.nrfa.models.MerchantScheme;
import com.probase.nrfa.models.Promotion;
import com.probase.nrfa.models.Province;
import com.probase.nrfa.models.Setting;
import com.probase.nrfa.models.TrafficException;
import com.probase.nrfa.services.MerchantServices;

public class Application {

	
	

	private static Application instance;
	//private static Key key;
	private static JSONObject accessKeys;
	public String businessUnit = "GOTV";
	public String dataSource = "Ghana_UAT";
	public String interfaceType = null;
	public String ipAddress = "127.1.1";
	public String language = "English";
	public String vendorCode = "eTranzactD1Stv";
	public String currencyCode = "GSH";
	public String methodOfPayment = "CASH";
	public String paymentVendorCode = "RTPP_Ghana_eTranzact";
	private static Logger log = Logger.getLogger(Application.class);
	private ServiceLocator serviceLocator = ServiceLocator.getInstance();
	public SwpService swpService = null;
	private Collection<Bank> allBanks = null;
	private Collection<MerchantScheme> allMerchantSchemes = null;
	private Collection<CardScheme> allCardSchemes = null;
	private Collection<Province> allProvinces = null;
	private JSONObject allDeviceTypes = null;
	private JSONObject allSettings = null;
	private LinkedList<String> tokenList = null;
	private LinkedList<String> guestTokenList = null;
	private JSONObject trafficExceptionList = null;
	private JSONObject promotionList = null;
	private final String probaseBranchCode = "999";
	private final String countryCode = "026";
	private final String acquirerId = "PROBASE";
	private Double minimumBalance = 10.00;
	private Double minimumTransactionAmountWeb = 1.00;
	private Double maximumTransactionAmountWeb = 100001.00;
	public String cyberSourceAccessKey = "ea291f2aa1e93806835e1308e9262e35";
	public String cyberSourceProfileId = "D9022B8F-3DCF-424C-8176-174B5245F598";
	public String cyberSourceSecretKey = 
			"a820cdde511b41d1b794b38286e51a41e5e538f6f049458c9f7c4b7e3af32b7ba1b17e193" +
			"1a74a12921af1a26467daf79784b8822b2b4842bc26e49fc9536e1ec6b14da77b1a4cd884" +
			"6a15d7e1e7d1d026b0d86bee9c49e1bf223ba4fdda35c80ec48ec253ec42f5875fb9481a8" +
			"634336ce09b587953462092cabaa3e9265903";
	public String cyberSourceLocale = "en-us";
	public static final Integer BASE_LIST_COUNT = 100;
	public static final String PROBASEPAY_SERVICE_TYPE_ID_REVERSAL 				= "1981598619900";
	public static final String PROBASEPAY_SERVICE_TYPE_ID_GENERIC_ECOMMERCE 	= "1981511018900";
	public static final String PROBASEPAY_SERVICE_TYPE_SCHOOL_FEE_PAYMENT		= "1981598182741";
	
	public Integer serverOption = null;
	
    private Application(SwpService swpService)
    {
        // shouldn't be instantiated
    	//setKey(MacProvider.generateKey());
    	//key.

    	/*try
    	{
    		System.out.println(1);
	    	String userName = "nrfa_root";
	    	System.out.println(2);
	    	String password = "k0l0zaq1ZAQ!";
	    	System.out.println(3);
	
	    	String url = "jdbc:sqlserver://192.168.1.101:1433;databaseName=nrfa_live";
	    	System.out.println(4);
	    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    	System.out.println(5);
	    	Connection conn = DriverManager.getConnection(url, userName, password);
	    	System.out.println(6);
	    	Statement statement = conn.createStatement();
	    	System.out.println(7);
	         String queryString = "select * from sms_messages";
	         System.out.println(8);
	         ResultSet rs = statement.executeQuery(queryString);
	         System.out.println(9);
	         while (rs.next()) {
	            System.out.println(rs.getString(1));
	         }
	         System.out.println(10);
    	}
    	catch(Exception e)
    	{
    		log.warn(e);
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    	}*/
    	
    	
    	JSONObject jsonObject = new JSONObject();
    	try {
    		this.swpService = this.serviceLocator.getSwpService();
			Collection<Bank> bankList = (Collection<Bank>)swpService.getAllRecords(Bank.class);
			
			//jsonObject.put("PROBASE", "WMXGGHowzFdq0fpTg93pYmA5Wjuiq97l");
			jsonObject.put("PROBASE", "sewu5T4AqnVCb3TfajRY4ixF0MZUhNnL");
			jsonObject.put("PROBASEWALLET", "WMXGGHowzFdq0fpTg93pYmA5Wjuiq97l");
			//jsonObject.put("031", "N2d5uUDFLypAvzrdPIdvig0zIHrKrHLZ");
			//jsonObject.put("033", "VoYTttuMSMzCGZkfp86aDq4RMjr9JcXJ");
			jsonObject.put("NRFA", "y4GKrKbeMLrfTuy2jY3o8idIXATV97oz");
			//jsonObject.put("PROBASEWALLET", "y4GKrKbeMLrfTuy2jY3o8idIXATV97oz");
			System.out.println("AccessBank Keys = " + jsonObject.toString());
			
			String hql = "Select tp from Merchant tp where tp.status = " + MerchantStatus.ACTIVE.ordinal();
			Collection<Merchant> merchants = (Collection<Merchant>)swpService.getAllRecordsByHQL(hql);
			Iterator<Merchant> itr = merchants.iterator();
			while(itr.hasNext())
			{
				Merchant merchant = itr.next();
				jsonObject.put(merchant.getMerchantCode(), merchant.getApiKey());
			}
			
			//this.setAllBanks((Collection<Bank>)swpService.getAllRecords(Bank.class));
			//this.setAllMerchantSchemes((Collection<MerchantScheme>)swpService.getAllRecords(MerchantScheme.class));
			//hql = "Select tp from PoolAccount tp Where tp.status = " + AccountStatus.ACTIVE.ordinal();
			//Collection<PoolAccount> allPoolAccounts = (Collection<PoolAccount>)this.swpService.getAllRecordsByHQL(hql);
			//this.setAllPoolAccounts(allPoolAccounts);
			
			
			JSONObject dType = new JSONObject();
			for(DeviceType d : DeviceType.values())
			{
				dType.put(Integer.toString(d.ordinal()), d.name());
			}
			this.setAllDeviceTypes(dType);
			//this.setAllCardSchemes((Collection<CardScheme>)swpService.getAllRecords(CardScheme.class));
			//this.setAllProvinces((Collection<Province>)swpService.getAllRecords(Province.class));
			//this.setAllCountries((Collection<Country>)swpService.getAllRecords(Country.class));
			this.setAccessKeys(jsonObject);
			tokenList = new LinkedList<String>();
			
			
			hql = "Select tp from Promotion tp where tp.deleted_at IS NULL AND CURRENT_TIMESTAMP BETWEEN tp.startDate AND tp.endDate";
			Collection<Promotion> promoList = (Collection<Promotion>)this.swpService.getAllRecordsByHQL(hql);
			Iterator<Promotion> itrPromo = promoList.iterator();
			promotionList = new JSONObject();
			
			while(itrPromo.hasNext())
			{
				Promotion promo = itrPromo.next();
				String key = promo.getRouteCode() + "~" + (promo.getCardScheme()!=null ? (promo.getCardScheme().getSchemeCode() + "~") : "") + 
						(promo.getTrafficScheme()!=null ? (promo.getTrafficScheme().getSchemeCode()) : "");
				JSONObject js = new JSONObject();
				js.put("0", promo.getBaseRationaleCount());
				js.put("1", promo.getDiscountPercentageApplicable());
				js.put("2", promo.getId());
				promotionList.put(key, promo.getDiscountPercentageApplicable());
			}
			
			
			
			hql = "Select tp from TrafficException tp where tp.deleted_at IS NULL";
			Collection<TrafficException> trafficExceptions = (Collection<TrafficException>)this.swpService.getAllRecordsByHQL(hql);
			Iterator<TrafficException> itrTrafficException = trafficExceptions.iterator();
			trafficExceptionList = new JSONObject();
			
			while(itrTrafficException.hasNext())
			{
				TrafficException trafficException = itrTrafficException.next();
				String key = trafficException.getRouteCode() + "~" + (trafficException.getVehicleRegNo()!=null ? (trafficException.getVehicleRegNo() + "~") : "") 
						 + (trafficException.getTrafficScheme()!=null ? (trafficException.getTrafficScheme().getSchemeCode() + "~") : "");
				JSONObject js = new JSONObject();
				js.put("0", trafficException.getDiscountPercentageApplicable());
				js.put("1", trafficException.getId());
				trafficExceptionList.put(key, trafficException.getDiscountPercentageApplicable());
			}
			
			
			
			hql = "Select tp from Setting tp where tp.status = 1 AND  tp.deleted_at IS NULL";
			Collection<Setting> settings = (Collection<Setting>)this.swpService.getAllRecordsByHQL(hql);
			Iterator<Setting> itrSetting = settings.iterator();
			allSettings = new JSONObject();
			
			while(itrSetting.hasNext())
			{
				Setting setting = itrSetting.next();
				String key = setting.getName();
				String value = setting.getValue();
				trafficExceptionList.put(key, value);
			}
			
			
			this.serverOption = 1;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getMessage());
		}
    }

    /**
     * Gets the shared instance of this Class
     *
     * @return the shared service locator instance.
     */
    public static final Application getInstance(SwpService swpService)
    {
    	System.out.println("Get Instance 1");
        if (instance == null)
        {
            instance = new Application(swpService);
        }
        return instance;
    }
    
    public static final Application getInstance(SwpService swpService, Boolean force)
    {
    	System.out.println("Get Instance 2");
        if (instance == null || force.equals(Boolean.TRUE))
        {
        	System.out.println("Force Reload of Application");
            instance = new Application(swpService);
        }
        return instance;
    }

	/*public static Key getKey() {
		try {
			System.out.println("JSWT Key = " + new String(key.getEncoded(), "UTf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		return key;
	}

	public static void setKey(Key key) {
		Application.key = key;
	}*/

    public String getCyberSourceAccessKey() {
		return cyberSourceAccessKey;
	}

	public String getCyberSourceProfileId() {
		return cyberSourceProfileId;
	}

	public String getCybersourcesecretkey() {
		return cyberSourceSecretKey;
	}

	public String getCybersourcelocale() {
		return cyberSourceLocale;
	}


    public void setCyberSourceAccessKey(String cyberSourceAccessKey) {
		this.cyberSourceAccessKey = cyberSourceAccessKey;
	}

	public void setCyberSourceProfileId(String cyberSourceProfileId) {
		this.cyberSourceProfileId = cyberSourceProfileId;
	}

	public void setCybersourcesecretkey(String cyberSourceSecretKey) {
		this.cyberSourceSecretKey = cyberSourceSecretKey;
	}

	public void setCybersourcelocale(String cyberSourceLocale) {
		this.cyberSourceLocale  = cyberSourceLocale;
	}
	
	
	public JSONObject getAccessKeys() {
		return accessKeys;
	}

	public void setAccessKeys(JSONObject accessKeys) {
		Application.accessKeys = accessKeys;
	}
	
	public static String getTokenKey()
	{
		return "Yn7sWDar7yPZZh7xvHFpnRWRNcj1l8Rf";
	}
	
	public String createJWT(String id, String issuer, String branchCode, String subject, long ttlMillis)
	{
		SignatureAlgorithm signAlg = SignatureAlgorithm.HS256;
		
		long nowMillis = System.currentTimeMillis();
		Date date = new Date(nowMillis);
		byte[] keyBytes = DatatypeConverter.parseBase64Binary(getTokenKey());
		Key signingKey = new SecretKeySpec(keyBytes, signAlg.getJcaName());
		
		JwtBuilder jwtBuilder = Jwts.builder().setId(id)
				.setIssuedAt(date)
				.setSubject(subject)
				.setIssuer(issuer)
				.setAudience(branchCode)
				.signWith(signAlg, signingKey);
		
		
		
		if (ttlMillis >= 0) {
		    long expMillis = nowMillis + ttlMillis;
		    Date exp = new Date(expMillis);
		    jwtBuilder.setExpiration(exp);
		}
		
		return jwtBuilder.compact();
		
	}
	
	public String createGuestJWT(String id, String subject, long ttlMillis)
	{
		SignatureAlgorithm signAlg = SignatureAlgorithm.HS256;
		
		long nowMillis = System.currentTimeMillis();
		Date date = new Date(nowMillis);
		byte[] keyBytes = DatatypeConverter.parseBase64Binary(getTokenKey());
		Key signingKey = new SecretKeySpec(keyBytes, signAlg.getJcaName());
		
		JwtBuilder jwtBuilder = Jwts.builder().setId(id)
				.setIssuedAt(date)
				.setSubject(subject)
				.signWith(signAlg, signingKey);
		
		
		
		if (ttlMillis >= 0) {
		    long expMillis = nowMillis + ttlMillis;
		    Date exp = new Date(expMillis);
		    jwtBuilder.setExpiration(exp);
		}
		
		return jwtBuilder.compact();
		
	}

	public Collection<Bank> getAllBanks() {
		return allBanks;
	}

	public void setAllBanks(Collection<Bank> allBanks) {
		this.allBanks = allBanks;
	}

	public Collection<MerchantScheme> getAllMerchantSchemes() {
		return allMerchantSchemes;
	}

	public void setAllMerchantSchemes(Collection<MerchantScheme> allMerchantSchemes) {
		this.allMerchantSchemes = allMerchantSchemes;
	}

	public JSONObject getAllDeviceTypes() {
		return allDeviceTypes;
	}

	public void setAllDeviceTypes(JSONObject deviceTypes) {
		this.allDeviceTypes = deviceTypes;
	}

	public Collection<CardScheme> getAllCardSchemes() {
		return allCardSchemes;
	}

	public void setAllCardSchemes(Collection<CardScheme> allCardSchemes) {
		this.allCardSchemes = allCardSchemes;
	}

	public Collection<Province> getAllProvinces() {
		return allProvinces;
	}

	public void setAllProvinces(Collection<Province> allProvinces) {
		this.allProvinces = allProvinces;
	}

	public LinkedList<String> getTokenList() {
		return tokenList;
	}

	public void setTokenList(LinkedList<String> tokenList) {
		this.tokenList = tokenList;
	}

	public LinkedList<String> getGuestTokenList() {
		return guestTokenList;
	}

	public void setGuestTokenList(LinkedList<String> tokenList) {
		this.guestTokenList = tokenList;
	}

	public void addTokenToTokenList(String token) {
		
		if(this.tokenList==null)
		{
			System.out.println("tokenList is null");
			this.tokenList = new LinkedList<String>();
		}
		this.tokenList.add(token);
		System.out.println("tokenList size = " + this.tokenList.size());
	}


	public void removeTokenFromTokenList(String token) {
		System.out.println("remove from token list = " + token);
		if(this.tokenList!=null)
			this.tokenList.remove(token);
		else
			this.tokenList = new LinkedList<String>(); 
	}

	public void addTokenToGuestTokenList(String guestToken) {
		//this.guestTokenList.add(guestToken);
		if(this.guestTokenList==null)
			this.guestTokenList = new LinkedList<String>(); 
		this.guestTokenList.add(guestToken);
	}


	public void removeTokenFromGuestTokenList(String guestToken) {
		//this.guestTokenList.remove(guestToken);
		if(this.guestTokenList!=null)
			this.guestTokenList.remove(guestToken);
		else
			this.guestTokenList = new LinkedList<String>(); 
	}

	public String getProbaseBranchCode() {
		return probaseBranchCode;
	}


	public String getCountryCode() {
		return countryCode;
	}

	public String getAcquirerId() {
		return acquirerId;
	}

	public Double getMinimumBalance() {
		return minimumBalance;
	}

	public Double getMinimumTransactionAmountWeb() {
		return minimumTransactionAmountWeb;
	}

	public Double getMaximumTransactionAmountWeb() {
		return maximumTransactionAmountWeb;
	}

	public JSONObject getAllSettings() {
		return allSettings;
	}

	public void setAllSettings(JSONObject allSettings) {
		this.allSettings = allSettings;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getMethodOfPayment() {
		return methodOfPayment;
	}

	public void setMethodOfPayment(String methodOfPayment) {
		this.methodOfPayment = methodOfPayment;
	}

	public String getPaymentVendorCode() {
		return paymentVendorCode;
	}

	public void setPaymentVendorCode(String paymentVendorCode) {
		this.paymentVendorCode = paymentVendorCode;
	}

	public void setMinimumBalance(Double minimumBalance) {
		this.minimumBalance = minimumBalance;
	}

	public void setMinimumTransactionAmountWeb(Double minimumTransactionAmountWeb) {
		this.minimumTransactionAmountWeb = minimumTransactionAmountWeb;
	}

	public void setMaximumTransactionAmountWeb(Double maximumTransactionAmountWeb) {
		this.maximumTransactionAmountWeb = maximumTransactionAmountWeb;
	}
	
	
	public JSONObject getTrafficExceptionList()
	{
		return this.trafficExceptionList;
	}
	
	
	public JSONObject getPromotionList()
	{
		return this.promotionList;
	}
}
