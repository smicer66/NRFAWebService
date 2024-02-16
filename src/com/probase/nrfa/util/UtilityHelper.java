package com.probase.nrfa.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import javax.xml.parsers.DocumentBuilderFactory;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.probase.nrfa.enumerations.RequestType;
import com.probase.nrfa.enumerations.RoleType;
//import com.probase.nrfa.models.Account;
import com.probase.nrfa.models.AuditTrail;
import com.probase.nrfa.models.CardScheme;
import com.probase.nrfa.models.SMSMesage;
import com.probase.nrfa.models.User;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class UtilityHelper {
	private static Logger log = Logger.getLogger(UtilityHelper.class);
	private static final String baseUrl = "http://smsapi.probasesms.com/apis/text/index.php";
	private final static String USER_AGENT = "Mozilla/5.0";
	public final static String cloudBaseUrl = "http://159.8.22.212:8080/TestWebService/";
	
	
	public static void checkConstraintsForNewMerchant(String merchantCode, String addressLine1, 
			String bankAccount, String certificateOfIncorporation, 
			String companyData, String companyLogo, String companyName, 
			String companyRegNo, String contactEmail, String contactMobile, 
			String merchantBank, String merchantName, String merchantScheme)
	{
		try{
			JSONObject returnErrors = new JSONObject();
			
			if(merchantCode!=null && merchantCode.length()==0){returnErrors.put("merchantCode", "Merchant Code must be provided");}
			if(addressLine1!=null && addressLine1.length()==0){returnErrors.put("addressLine1", "First line of address must be provided");}
			if(bankAccount!=null && bankAccount.length()==0){returnErrors.put("bankAccount", "Valid bank account for the merchant must be provided");}
			if(certificateOfIncorporation!=null && certificateOfIncorporation.length()==0){returnErrors.put("certificateOfIncorporation", "Certificate of Incorporation must be provided");
			}
			if(merchantCode!=null && merchantCode.length()==0){
				returnErrors.put("merchantCode", "Merchant Code must be provided");
			}
			if(addressLine1!=null && addressLine1.length()==0){
				returnErrors.put("addressLine1", "First line of address must be provided");
			}
			if(bankAccount!=null && bankAccount.length()==0){
				returnErrors.put("bankAccount", "Valid bank account for the merchant must be provided");
			}
			if(certificateOfIncorporation!=null && certificateOfIncorporation.length()==0){
				returnErrors.put("certificateOfIncorporation", "Certificate of Incorporation must be provided");
			}
		}catch(JSONException e)
		{
			log.info(e.getMessage());
		}
	}

	/*public static String getLoggedInBankBranchAndBankCode(SwpService swpService, String token, 
			String currencyCode, 
			String productScheme, String uniqueCode, Long customerId) {
		// TODO Auto-generated method stub
		String hql = "Select tp from Account tp where tp.customer.id = " + customerId;
		Collection<Account> customerAccounts;
		try {
			customerAccounts = (Collection<Account>)swpService.getAllRecordsByHQL(hql);
			return "035" + "001" + currencyCode + productScheme + uniqueCode + customerAccounts.size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			return null;
		}
		
	}

	public static String generateAccountNo(CardScheme cardScheme, Account account) {
		// TODO Auto-generated method stub
		log.info("Account No => " + cardScheme.getSchemeCode() + account.getAccountIdentifier() + account.getAccountCount());
		return cardScheme.getSchemeCode() + account.getAccountIdentifier() + account.getAccountCount();
	}*/
	
	public static int addTwoDigitNum(int x)
	{
		String xString = Integer.toString(x);
		char[] xStringArray = xString.toCharArray();
		//int sum = xString.length()>1 ? (Integer.parseInt(""+xStringArray[0]) + Integer.parseInt(""+xStringArray[1])) : x;
		int sum = x>9 ? (x-9) : x;
		return x;
	}

	public static String generatePan(String bankCode, String branchCode) {
		// TODO Auto-generated method stub
		//						01 234567 8
		//pan = 001|001|0123456|1
		//npan = (6*2)5(4*2)3(2*2)1
		String accountNo = RandomStringUtils.random(7, false, true);
		char[] acc = accountNo.toCharArray();
		int x6th = Integer.parseInt("" + acc[6]) * 2;
		int x5th = Integer.parseInt("" + acc[5]);
		int x4th = Integer.parseInt("" + acc[4]) * 2;
		int x3th = Integer.parseInt("" + acc[3]);
		int x2th = Integer.parseInt("" + acc[2]) * 2;
		int x1th = Integer.parseInt("" + acc[1]);
		int x0th = Integer.parseInt("" + acc[0]) * 2;
		int sumXth = (x6th<10 ? x6th : addTwoDigitNum(x6th)) +  x5th +
				(x4th<10 ? x4th : addTwoDigitNum(x4th)) +  x3th +
				(x2th<10 ? x2th : addTwoDigitNum(x2th)) +  x1th +
				(x0th<10 ? x0th : addTwoDigitNum(x0th));
		sumXth = sumXth * 9;
		int mod = sumXth%10;
		return "0" + bankCode + branchCode + "" + accountNo + "" + mod;
	}
	
	public static String decryptDataNew(byte[] keyValue, String ivValue, String encryptedData, String macValue) throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, DecoderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		Key key = new SecretKeySpec(keyValue, "AES");
        byte[] iv = org.apache.commons.codec.binary.Base64.decodeBase64(ivValue.getBytes("UTF-8"));
        byte[] decodedValue = org.apache.commons.codec.binary.Base64.decodeBase64(encryptedData.getBytes("UTF-8"));

        SecretKeySpec macKey = new SecretKeySpec(keyValue, "HmacSHA256");
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(macKey);
        hmacSha256.update(ivValue.getBytes("UTF-8"));
        byte[] calcMac = hmacSha256.doFinal(encryptedData.getBytes("UTF-8"));
        byte[] mac = Hex.decodeHex(macValue.toCharArray());
        if (!Arrays.equals(calcMac, mac))
            return "MAC mismatch";

        Cipher c = Cipher.getInstance("AES/CBC/PKCS7Padding"); // or PKCS5Padding
        c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] decValue = c.doFinal(decodedValue);

        int firstQuoteIndex = 0;
        while (decValue[firstQuoteIndex] != (byte) '"') firstQuoteIndex++;
        return new String(Arrays.copyOfRange(decValue, firstQuoteIndex + 1, decValue.length - 2));
	}
	
	public static Object decryptData(String encryptedStr, String bankKey)
	{
		try{
			log.info("encryptedStr = " + encryptedStr);
			byte[] decode = org.apache.commons.codec.binary.Base64.decodeBase64(encryptedStr.getBytes("UTF-8"));
			String decodeStr = new String(decode, "UTF-8");
			log.info("Decoded = " + decodeStr);
			JSONObject jsonObject = new JSONObject(decodeStr);
			String iv1 = jsonObject.getString("iv");
			log.info("iv1 = " + iv1);
			String value = jsonObject.getString("value");
			log.info("value = " + value);
			String mac = jsonObject.getString("mac");
			log.info("mac = " + mac);
			log.info("bankKey = " + bankKey);
			byte[] keyValue = bankKey.getBytes("UTF-8");
			/*String dec = decryptDataNew(keyValue, iv1, encryptedStr, mac);
			log.info("dec = " + dec);
			return dec;
		}catch(Exception e)
		 {
			 log.info(e.getMessage());
			 return null;
		 }*/
			log.info("value = " + value);

			log.info("bankKey = " + bankKey);
			Key key = new SecretKeySpec(keyValue, "AES");
			byte[] iv = org.apache.commons.codec.binary.Base64.decodeBase64(iv1.getBytes("UTF-8"));
			byte[] decodedValue = org.apache.commons.codec.binary.Base64.decodeBase64(value.getBytes("UTF-8"));
			
			
			log.info(key.getAlgorithm());
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding"); // or PKCS5Padding
			log.info(1);
			c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			log.info(2);
			byte[] decValue = c.doFinal(decodedValue);
			log.info(3);
			log.info(decValue);
			
			int firstQuoteIndex = 0;
			byte b;
			log.info(decValue[0]);
			if(decValue[0] == 105 || decValue[0] == 100 ){
				b = ((byte)';');
			}
			else{
				b = ((byte)'"');
			}
			
			log.info("length>--" + decValue.length);
			log.info("" + decValue[0]);
			log.info("" + new String(decValue, "UTF-8"));
			while(decValue[firstQuoteIndex] != b){
				firstQuoteIndex++;
			}
			String vl = "";
			
			
			if(decValue[0] == 105 || decValue[0] == 100 ){
				vl = new String(Arrays.copyOfRange(decValue, 2, decValue.length - 1), "UTF-8");
				if(decValue[0] == 105)
				{
					return Integer.valueOf(vl);
				}else if(decValue[0] == 100)
				{
					return Long.valueOf(vl);
				}
			}else{
				vl = new String(Arrays.copyOfRange(decValue, firstQuoteIndex + 1, decValue.length-2), "UTF-8");
			}
			return (vl);
		 }catch(Exception e)
		 {
			 e.printStackTrace();
			 log.info(e.getMessage());
			 log.info(e.getLocalizedMessage());
			 log.error(e);
			 return null;
		 }
	}
	
	public AlgorithmParameterSpec getIV(Cipher cipher) {
		AlgorithmParameterSpec ivspec;
		byte[] iv = new byte[cipher.getBlockSize()];
		new SecureRandom().nextBytes(iv);
		ivspec = new IvParameterSpec(iv);
		return ivspec;
	}
	
	
	
	public static String bcryptData(String data)
	{
		return BCrypt.hashpw(data, BCrypt.gensalt());
	}
	
	
	public static String encryptData(Object toencrypt, String bankKey) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, JSONException
	{
		String strtoencrypt = "s:" + toencrypt.toString().length() + ":\"" + toencrypt.toString() + "\";";
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    SecretKeySpec key = new SecretKeySpec(bankKey.getBytes("UTF-8"), "AES");
	    byte[] iv = RandomStringUtils.randomAlphanumeric(16).getBytes("UTF-8");
	    cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(iv));
	    byte[] df = cipher.doFinal(strtoencrypt.getBytes("UTF-8"));
	    byte[] val = org.apache.commons.codec.binary.Base64.encodeBase64(df);
	    byte[] iv1 = org.apache.commons.codec.binary.Base64.encodeBase64(iv);
	    byte[] dest = new byte[iv1.length + val.length];
	    System.arraycopy(iv, 0, dest, 0, iv.length);
	    System.arraycopy(val, 0, dest, iv1.length, val.length);
	    //cipher.
	    key = new SecretKeySpec(bankKey.getBytes("UTF-8"), "HmacSHA256");
	    Mac mac = Mac.getInstance("HmacSHA256");
	    mac.init(key);
	    mac.update(iv1);
	    mac.update(val);
	    
	    
	    //Arrays.
	    byte[] mc = (mac.doFinal());
	    JSONObject  js = new JSONObject();
	    js.put("iv", new String(iv1, "UTF-8"));
	    js.put("value", new String(val, "UTF-8"));
	    js.put("mac", new String(mc, "UTF-8"));
	    String encStr = new String(org.apache.commons.codec.binary.Base64.encodeBase64(js.toString().getBytes()), "UTF-8");
	    return (encStr);
	}

	public static String getBankKey(String bankCode, SwpService swpService) throws JSONException {
		// TODO Auto-generated method stub
		Application application = Application.getInstance(swpService);
		JSONObject bankKeys = application.getAccessKeys();
		log.info("bankKeys ==> " + bankKeys.toString());
		
		
		if(bankCode==null)
			bankCode = "PROBASE";
		
		return bankKeys.getString(bankCode);
	}

	public static JSONObject verifyToken(String jwtoken, Application app) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		try{
			log.info("-------------------------");
			log.info("jwtone =" + jwtoken);
			Claims claims = Jwts.parser()         
					   .setSigningKey(DatatypeConverter.parseBase64Binary(app.getTokenKey()))
					   .parseClaimsJws(jwtoken).getBody();
			Date expDate = claims.getExpiration();
			String subject = claims.getSubject();
			String issuerBankCode = claims.getIssuer();
			String branchCode = claims.getAudience();
			Date now = new Date(System.currentTimeMillis());
			
			log.info("exp Date ==" + expDate);
			log.info("Now Date ==" + now);
			int active = 1;
			long ttl = 0;
			if(now.after(expDate))
				active = 0;
			else
				ttl = claims.getExpiration().getTime() - now.getTime();
			
			//String tkId = RandomStringUtils.randomAlphanumeric(10);
			
			log.info("active ==" + active);
			//log.info("new tkId ==" + tkId);
			log.info("remove tkId ==" + claims.getId());
			//app.removeTokenFromTokenList(claims.getId());
			//if(now.after(expDate))
				//app.addTokenToTokenList(tkId);
			
			log.info("token size ==" + app.getTokenList().size());
			log.info("-------------------------");
			
			Gson gson = new Gson();
			JSONObject subjectJSON = gson.fromJson(subject, JSONObject.class);
			
			jsonObject.put("active", active);
			jsonObject.put("issuerBankCode", issuerBankCode);
			jsonObject.put("branchCode", branchCode);
			jsonObject.put("bankCode", issuerBankCode);
			jsonObject.put("subject", subject);
			if(subjectJSON.has("roleCode"))
				jsonObject.put("roleCode", subjectJSON.getString("roleCode"));
			if(subjectJSON.has("username"))
				jsonObject.put("username", subjectJSON.getString("username"));
			if(subjectJSON.has("distributorCode"))
				jsonObject.put("distributorCode", subjectJSON.getString("distributorCode"));
			
			log.info("---verifyJSON Response = " + jsonObject.toString());
			
			
			return jsonObject;
			
		}catch(ExpiredJwtException e)
		{
			log.info(e.getMessage());
			e.printStackTrace();
			return new JSONObject();
		}catch(Exception e)
		{
			log.info(e.getMessage());
			e.printStackTrace();
			return new JSONObject();
		}
	}
	
	public static JSONObject verifyToken(String jwtoken, Application app, Boolean forceNewToken) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		try{
			log.info("-------------------------");
			log.info("jwtone =" + jwtoken);
			Claims claims = Jwts.parser()         
					   .setSigningKey(DatatypeConverter.parseBase64Binary(app.getTokenKey()))
					   .parseClaimsJws(jwtoken).getBody();
			Date expDate = claims.getExpiration();
			String subject = claims.getSubject();
			String issuerBankCode = claims.getIssuer();
			String branchCode = claims.getAudience();
			Date now = new Date(System.currentTimeMillis());
			
			log.info("exp Date ==" + expDate);
			log.info("Now Date ==" + now);
			int active = 1;
			long ttl = 0;
			if(now.after(expDate))
				active = 0;
			else
				ttl = claims.getExpiration().getTime() - now.getTime();
			
			String tkId = RandomStringUtils.randomAlphanumeric(10);
			
			log.info("active ==" + active);
			log.info("new tkId ==" + tkId);
			log.info("remove tkId ==" + claims.getId());
			app.removeTokenFromTokenList(claims.getId());
			if(now.after(expDate))
				app.addTokenToTokenList(tkId);
			
			log.info("token size ==" + app.getTokenList().size());
			log.info("-------------------------");
			
			Gson gson = new Gson();
			JSONObject subjectJSON = gson.fromJson(subject, JSONObject.class);
			
			jsonObject.put("active", active);
			jsonObject.put("issuerBankCode", issuerBankCode);
			jsonObject.put("branchCode", branchCode);
			jsonObject.put("bankCode", issuerBankCode);
			if(subjectJSON.has("username"))
				jsonObject.put("username", subjectJSON.getString("username"));
			
			if(active==1 && forceNewToken!=null && forceNewToken.equals(Boolean.TRUE))
				jsonObject.put("token", app.createJWT(tkId, issuerBankCode, branchCode, subject, (ttl)));
			
			log.info("verifyJSON Response = " + jsonObject.toString());
			
			
			return jsonObject;
			
		}catch(ExpiredJwtException e)
		{
			log.info(e.getMessage());
			e.printStackTrace();
			return new JSONObject();
		}catch(Exception e)
		{
			log.info(e.getMessage());
			e.printStackTrace();
			return new JSONObject();
		}
	}
	
	
	
	public static JSONObject verifyTokenGetUser(String jwtoken, Application app, SwpService swpService) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		try{
			log.info("-------------------------");
			log.info("jwtone =" + jwtoken);
			Claims claims = Jwts.parser()         
					   .setSigningKey(DatatypeConverter.parseBase64Binary(app.getTokenKey()))
					   .parseClaimsJws(jwtoken).getBody();
			Date expDate = claims.getExpiration();
			String subject = claims.getSubject();
			Gson gsonConverter = new Gson();
			User user = gsonConverter.fromJson(subject, User.class);
			String hql = "Select tp from User tp where tp.id = " + user.getId();
			user = (User)swpService.getUniqueRecordByHQL(hql);
			subject = gsonConverter.toJson(user);
			String issuerBankCode = claims.getIssuer();
			String branchCode = claims.getAudience();
			Date now = new Date(System.currentTimeMillis());
			
			log.info("exp Date ==" + expDate);
			log.info("Now Date ==" + now);
			int active = 1;
			long ttl = 0;
			if(now.after(expDate))
				active = 0;
			else
				ttl = claims.getExpiration().getTime() - now.getTime();
			
			String tkId = RandomStringUtils.randomAlphanumeric(10);
			
			log.info("active ==" + active);
			log.info("new tkId ==" + tkId);
			log.info("remove tkId ==" + claims.getId());
			app.removeTokenFromTokenList(claims.getId());
			app.addTokenToTokenList(tkId);
			log.info("token size ==" + app.getTokenList().size());
			log.info("-------------------------");
			
			jsonObject.put("active", active);
			jsonObject.put("issuerBankCode", issuerBankCode);
			jsonObject.put("branchCode", branchCode);
			jsonObject.put("staff_bank_code", issuerBankCode);
			jsonObject.put("subject", subject);
			log.info("staff_bank_code ==" + issuerBankCode);
			if(active==1)
				jsonObject.put("token", app.createJWT(tkId, issuerBankCode, branchCode, subject, (ttl)));
			
			return jsonObject;
			
		}catch(ExpiredJwtException e)
		{
			log.info(e.getMessage());
			return new JSONObject();
		}catch(Exception e)
		{
			log.info(e.getMessage());
			return new JSONObject();
		}
	}
	
	
	public static JSONObject verifyGuestToken(String jwtoken, Application app) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		try{
			log.info("-------------------------");
			log.info("jwtone =" + jwtoken);
			Claims claims = Jwts.parser()         
					   .setSigningKey(DatatypeConverter.parseBase64Binary(app.getTokenKey()))
					   .parseClaimsJws(jwtoken).getBody();
			Date expDate = claims.getExpiration();
			String subject = claims.getSubject();
			Date now = new Date(System.currentTimeMillis());
			
			log.info("exp Date ==" + expDate);
			log.info("Now Date ==" + now);
			int active = 1;
			long ttl = 0;
			if(now.after(expDate))
				active = 0;
			else
				ttl = claims.getExpiration().getTime() - now.getTime();
			
			String tkId = RandomStringUtils.randomAlphanumeric(10);
			
			log.info("active ==" + active);
			log.info("new tkId ==" + tkId);
			log.info("remove tkId ==" + claims.getId());
			app.removeTokenFromGuestTokenList(claims.getId());
			app.addTokenToGuestTokenList(tkId);
			log.info("token size ==" + app.getGuestTokenList().size());
			log.info("-------------------------");
			
			jsonObject.put("active", active);
			if(active==1)
				jsonObject.put("token", app.createGuestJWT(tkId, subject, (ttl)));
			
			return jsonObject;
			
		}catch(ExpiredJwtException e)
		{
			log.info(e.getMessage());
			return new JSONObject();
		}catch(Exception e)
		{
			log.info(e.getMessage());
			return new JSONObject();
		}
	}


	public static boolean validateTransactionHash(String hash, String terminalId,
			String serviceType, String orderId, Double amount, String api_key) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("0.00");
		String amt = df.format(amount);
		amount = Double.valueOf(amt);
		//
		String toHash = terminalId+serviceType+orderId+amt+api_key;
		log.info("To HAsh = " + terminalId+"-"+serviceType+"-"+orderId+"-"+amt+"-"+api_key);
		try {
			String hashed = UtilityHelper.get_SHA_512_SecurePassword(toHash);
			log.info("1.hash = " + hash);
			log.info("2.hash = " + hashed);
			if(hashed.equals(hash))
			{
				return true;
			}
			else
			{
				return false;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			return false;
		}
	}
	
	
	public static String get_SHA_512_SecurePassword(String passwordToHash) throws UnsupportedEncodingException{
		String generatedPassword = null;
		    try {
		         MessageDigest md = MessageDigest.getInstance("SHA-512");
		         byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
		         StringBuilder sb = new StringBuilder();
		         for(int i=0; i< bytes.length ;i++){
		            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		         }
		         generatedPassword = sb.toString();
		        } 
		       catch (NoSuchAlgorithmException e){
		        log.info(e.getMessage());
		       }
		    return generatedPassword;
		}

	public static JSONObject validateForCyberSource(String billingFirstName,
			String billingLastName, String billingPhone, String billingEmail,
			String billingStreetAddress, String billingCity,
			String billingDistrict, String merchantId, String deviceCode,
			String serviceTypeId, String orderId, String hash,
			String payerName, String payerEmail, String payerPhone,
			Double amount, String paymentItem, String responseurl,
			String payerId, String nationalId, String scope,
			String description, String api_key, SwpService swpService, Application app) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject();
		try
		{
			if(merchantId!=null && deviceCode!=null)
				if(serviceTypeId!=null)
					if(orderId!= null)
						if(hash!=null)
							/*if(validateTransactionHash(hash, merchantId, deviceCode, serviceTypeId, orderId, amount, responseurl, api_key))
								if(amount!=null && amount>0 && amount > app.getMinimumTransactionAmountWeb() && amount<app.getMaximumTransactionAmountWeb())
									if(responseurl!=null)
										if(api_key!=null && api_key.length()>0)
											return null;
										else
											jsonObject.put(ERROR.API_KEY_FAIL, "Merchant API key failed");//apiKey
									else
										jsonObject.put(ERROR.RESPONSE_URL_NOT_PROVIDED, "Response URL not provided");//responseUrl
								else
									jsonObject.put(ERROR.TRANSACTION_AMOUNT_INVALID, "Transaction Amount is Invalid.");//amount < 0  & minimumtxn
							else*/
								jsonObject.put(ERROR.HASH_FAIL_VALIDATION, "HASH Validation Failed");//hash validation failed
						else
							jsonObject.put(ERROR.HASH_NOT_PROVIDED, "HASH Not Provided");//hash not provided
					else
						jsonObject.put(ERROR.ORDER_ID_NOT_PROVIDED, "Order ID Not Provided");//orderId
				else
					jsonObject.put(ERROR.SERVICE_TYPE_NOT_PROVIDED, "Service Type Not Provided");//serviceType Not provided
			else
				jsonObject.put(ERROR.MERCHANT_DEVICE_FAIL, "Merchant and Device Codes provided are invalid");//merchantId, deviceCode not provided
			
		}catch(Exception e)
		{
			log.info(e.getMessage());
		}
		return jsonObject;
	}
	
	public static String hash_hmac(String dataToEncrypt, String secretKey)
	{
		String signature = null;
		try
		{
	        byte[] decodedKey = Hex.decodeHex(secretKey.toCharArray());
	        SecretKeySpec keySpec = new SecretKeySpec(decodedKey, "HmacSHA256");
	        Mac mac = Mac.getInstance("HmacSHA256");
	        mac.init(keySpec);
	        byte[] dataBytes = dataToEncrypt.getBytes("UTF-8");
	        byte[] signatureBytes = mac.doFinal(dataBytes);
	        signature = new String(org.apache.commons.codec.binary.Base64.encodeBase64(signatureBytes), "UTF-8");
	        log.info("key = " + secretKey);
	        log.info("data = " + dataToEncrypt);
	        log.info("signature = " + signature);
		}catch(Exception e)
		{
			log.info(e.getMessage());
		}
		return signature;
	}
	
	public static Response.ResponseBuilder getNoCacheResponseBuilder( Response.Status status ) {
        CacheControl cc = new CacheControl();
        cc.setNoCache( true );
        cc.setMaxAge( -1 );
        cc.setMustRevalidate( true );

        return Response.status( status ).cacheControl( cc );
    }
	
	
	public static AuditTrail createAuditTrailEntry(String ip_address, RequestType requestType, String requestId, SwpService swpService, String username, Long objectId, String objectClass) throws Exception
	{
		AuditTrail ad = new AuditTrail();
		ad.setCreated_at(new Date());
		ad.setUpdated_at(new Date());
		ad.setIp_address(ip_address);
		ad.setRequestId(requestId.substring(0, requestId.length()-1).toUpperCase());
		ad.setRequestType(requestType);
		ad.setUsername(username);
		ad.setPrimaryObjectIdHandled(objectId);
		ad.setPrimaryObjectType(objectClass);
		ad = (AuditTrail)swpService.createNewRecord(ad);
		return ad;
	}
	
	
	public static String sendSMS(SwpService swpService, String message, String receipientMobileNumber) throws Exception {

		//String url = baseUrl + (parameters!=null ? ("?" + parameters) : "");
		receipientMobileNumber = receipientMobileNumber.startsWith("260") ? receipientMobileNumber : (receipientMobileNumber.startsWith("0") ? ("260"+receipientMobileNumber.substring(1)) : null);
		if(receipientMobileNumber!=null)
		{
			String url = baseUrl + "?username=demo&password=password&mobiles="+receipientMobileNumber+"&message="+URLEncoder.encode(message,"UTF-8")+"&sender=NRFA&type=TEXT";
			
	
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
			// optional default is GET
			con.setRequestMethod("GET");
	
			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
	
			int responseCode = con.getResponseCode();
			log.info("\nSending 'GET' request to URL : " + url);
			log.info("Response Code : " + responseCode);
	
			if(responseCode==200)
			{
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
		
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				con.disconnect();
		
				//print result
				log.info(response.toString());
				String status = getSmsResponseStatus(response.toString());
				SMSMesage sms = new SMSMesage();
				sms.setReceipentMobileNumber(receipientMobileNumber);
				sms.setResponseCode(responseCode);
				sms.setMessage(message);
				sms.setCreated_at(new Date());
				sms.setUpdated_at(new Date());
				sms.setDataResponse(response.toString());
				sms.setStatus(status==null ? "FAILED" : "SUCCESS");
				swpService.createNewRecord(sms);
				return response.toString();
			}
		}
		return null;

	}
	
	
	
	public static String getSmsResponseStatus(String resp)
	{
		try
		{
			Document doc = DocumentBuilderFactory.newInstance()
	                .newDocumentBuilder()
	                .parse(new InputSource(new StringReader(resp)));
			NodeList errNodes = doc.getElementsByTagName("response");
			if (errNodes.getLength() > 0) {
			    Element err = (Element)errNodes.item(0);
			    return (err.getElementsByTagName("messagestatus")
			                          .item(0)
			                          .getTextContent());
			}
			return null;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static String sendPost(String baseUrl, String parameters, JSONObject jsObj) throws Exception {

		String url = "https://selfsolve.apple.com/wcResults.do";
		url = baseUrl;
		System.out.println("112");
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		System.out.println("113");
		//add reuqest header
		con.setRequestMethod("POST");
		System.out.println("114");
		con.setRequestProperty("User-Agent", USER_AGENT);
		System.out.println("115");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		System.out.println("116");
		if(jsObj!=null && jsObj.length()>0)
		{
			Iterator<String> iter = jsObj.keys();
			while(iter.hasNext())
			{
				String key = iter.next();
				con.setRequestProperty(key, jsObj.getString(key));
			}
		}
		System.out.println("117");

		//String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
		String urlParameters = parameters;
		System.out.println("118");

		// Send post request
		con.setDoOutput(true);
		System.out.println("119");
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		System.out.println("121");
		if(urlParameters!=null)
			wr.writeBytes(urlParameters);
		
		System.out.println("112");
		
		wr.flush();
		wr.close();
		System.out.println("113");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		return response.toString();

	}
	
	

}
