package com.probase.nrfa.util;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;


import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.NodeList;
/*import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;*/
/*import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;*/

import com.probase.nrfa.authenticator.Authenticator;
import com.probase.nrfa.authenticator.CardFunction;
import com.probase.nrfa.authenticator.MerchantFunction;
import com.probase.nrfa.authenticator.PaymentFunction;
import com.probase.nrfa.authenticator.ReportFunction;
import com.probase.nrfa.authenticator.UtilityFunction;
import com.probase.nrfa.enumerations.CardStatus;
import com.probase.nrfa.enumerations.CardType;
import com.probase.nrfa.enumerations.Channel;
import com.probase.nrfa.enumerations.CustomerType;
import com.probase.nrfa.enumerations.DeviceType;
import com.probase.nrfa.enumerations.DistributorStatus;
import com.probase.nrfa.enumerations.DistributorType;
import com.probase.nrfa.enumerations.FundingAccountType;
import com.probase.nrfa.enumerations.MerchantStatus;
import com.probase.nrfa.enumerations.NFRACurrency;
import com.probase.nrfa.enumerations.RoleType;
import com.probase.nrfa.enumerations.ServiceType;
import com.probase.nrfa.enumerations.UserStatus;
import com.probase.nrfa.models.CardScheme;
import com.probase.nrfa.models.Distributor;
import com.probase.nrfa.models.ECard;
import com.probase.nrfa.models.Merchant;
import com.probase.nrfa.models.User;
import com.probase.nrfa.services.CardServices;
import com.probase.nrfa.services.UtilityServices;
import com.probase.nrfa.util.Application;
import com.probase.nrfa.util.PrbCustomService;
import com.probase.nrfa.util.ServiceLocator;
import com.probase.nrfa.util.SwpService;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;


public class Test {
	private static final String WS_URL = "http://localhost:9999/ws/hello?wsdl";

	/**
	 * @param args
	 */
	private static Logger log = Logger.getLogger(com.probase.nrfa.util.Test.class);
	/*private static ServiceLocator serviceLocator = ServiceLocator.getInstance();
	public static SwpService swpService = null;
	public static PrbCustomService swpCustomService = PrbCustomService.getInstance();
	*/
	private final static String USER_AGENT = "Mozilla/5.0";
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*swpService = serviceLocator.getSwpService();
		Application application  = Application.getInstance(swpService);
		*/
		/*String encryptedStr = "eyJpdiI6IjZYakNKT2RsRUtodTJyU3IwRldkaFE9PSI" + 
				"sInZhbHVlIjoiXC9cL3dacXl3TmV1M3hoNUhcL1FYSU9WUT09IiwibWFjIj" +
				"oiMjljODRlYmFjY2E3MGM1Yzc4MGI4MmM2MWZjNDhiOGRkYTZhODVmN2Y5M" + 
				"DliNDliZDhkODhkNmFiMDk1MTBlOCJ9";
		encryptedStr = "eyJpdiI6Im4rREhONzhFUTcycXV6cFwvYU5cL0pyZz09IiwidmFsd" +
				"WUiOiJuMHFCU1pGQUdpNlZwMkNDVGJweVJnPT0iLCJtYWMiOiJmNzJlYjAzM" +
				"DcxYzdlNDU1NTJmYjc3MDM2OWJhNWI2NGZiYTczMjkwMzEyYTQ1ZWExY2QzN" +
				"TE5YmViZmRhOTIzIn0=";*/
		/*encryptedStr = "eyJpdiI6InZoVjF1RUJsUE9wVWxCSGdpV1wvdzdRPT0iLCJ2YWx1Z" +
				"SI6InZRcWIyNm4wQVpVUE5VT3JIbHJ1NEE9PSIsIm1hYyI6IjhjOTU2YWVmO" +
				"DYzMTM1NjMwNDE4ZTExZTRhMTAwYWEyMzNhMzM2YTczODQ4Mzc0MzE5ODViZ" +
				"DZiNzZlNjE0YzYifQ==";*/
		/*encryptedStr = "eyJpdiI6IlNzXC8rdDBibG9vTDVFVUhMTCtud0pnPT0iLCJ2YWx1ZSI6InN2ZmJ5VjdnaHZFYTdFdDhtK25LSDRWWHZLUzl2Rlh4eWM3eVVJRVlkakt0Slh2MTNtMG85bnRFZ0dXSXRQRXAiLCJtYWMiOiI1N2VjMGViMGIyMDFjZGZkOWEyZGYxMDYxZTFhYjUyNjQ1MTZlZTcxNWNlZmJmMTE0YTU0MmYwODhlZWY2YzcxIn0=";//strval
		String bankKey = "WMXGGHowzFdq0fpTg93pYmA5Wjuiq97l";
		Object pws = UtilityHelper.decryptData(encryptedStr, bankKey);
		System.out.println(">>>" + (((Long)pws)*2));*/
		/*System.out.println(CardStatus.ACTIVATED_LEVEL_3.ordinal());
		Double amount = 1700.00;
		String terminalId = "81420";
		String serviceType ="CUSTOMER_LOAD_CARD_VIA_DISTRIBUTOR_ONUS";
		String orderId = "nQ3ekLzT";
		String api_key = "iZfv7kd3vdtThuc9LwbPXTOpzNsXIAXK";
		String hash = "d725800ac8130d4250acf04e0f107fa00ac55bee460b8dc66a1af3e0117018a3caf6a86dd09522e6393673d1ee62cdc5b80e3fa375bfd1156e62cd7ed906b044";
		
		*/
		try{
			
			//String resp = "<?xml version='1.0' encoding='UTF-8' ?><responses><response status-code='0'><messageid>197121913464094042</messageid><messagestatus>SUCCESS</messagestatus><mobile>260976360360</mobile></response></responses>";
			/*Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(resp)));
			NodeList errNodes = doc.getElementsByTagName("response");
			if (errNodes.getLength() > 0) {
			    Element err = (Element)errNodes.item(0);
			    System.out.println(err.getElementsByTagName("messagestatus")
			                          .item(0)
			                          .getTextContent());
			} else { 
			        // success
			}*/
			
			
			try
			{
				/*boolean proceed = true;
				String status = null;
				SAXReader reader = new SAXReader();
				
				Document doc = reader.read(new StringReader(resp));
				NodeList nodes =
				Iterator nodeIterator = doc.nodeIterator();
				while(nodeIterator.hasNext() && proceed==true)
				{
					Node node = (Node)nodeIterator.next();
					System.out.println("node.getName()" + node.getName());
					if(node.getName()=="responses")
					{
						node.get
					}
					if(node.getName()=="messageStatus")
					{
						status = node.getText();
						proceed = false;
					}
				}
				System.out.println(status);*/
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			//SmsSender ss = new SmsSender(swpService, "Test ABC", "260976360360");
			//ss.run();
			
			//String BASE_ENDPOINT = "159.8.22.212:8080";
			//BASE_ENDPOINT = "";
			/*//BASE_ENDPOINT = "localhost:8080";
			 * 
			
			DecimalFormat df = new DecimalFormat("0.00");
			String amt = df.format(amount);
			amount = Double.valueOf(amt);
			//
			//
			String toHash = terminalId+serviceType+orderId+amt+api_key;
			System.out.println("ToHAsh = " + toHash);
			System.out.println("To HAsh = " + terminalId+"-"+serviceType+"-"+orderId+"-"+amt+"-"+api_key);
			try {
				//toHash = "81420CUSTOMER_DEPOSIT_CASH_VIA_MERCHANT_OTC_ONUSfBkPZ2cEiZfv7kd3vdtThuc9LwbPXTOpzNsXIAXK";
				String hashed = UtilityHelper.get_SHA_512_SecurePassword(toHash);
				System.out.println("1.hash = " + hash);
				System.out.println("2.hash = " + hashed);
				if(hashed.equals(hash))
				{
					System.out.println("valid hash match");
				}
				else
				{
					System.out.println("invalid hash match");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				System.out.println("valid hash match");
			}
			*/
			//com.probase.nrfa.services.AuthenticationService authService = new com.probase.nrfa.services.AuthenticationService();
			
			//1. Create New User//
			/*Authenticator at = Authenticator.getInstance();
			Application app = Application.getInstance(swpService);
			System.out.println(app.getPromotionList().toString());
			System.out.println(app.getTrafficExceptionList().toString());
			at.addNewUser2("distributor1@gmail.com", "distributor1@gmail.com", "+260972019919", UserStatus.ACTIVE.name(), 
					null, Boolean.FALSE, null, "001", "031", "Jude", "Paul", "Ben", RoleType.DISTRIBUTOR_STAFF.name());
			at.addNewUser2("tolluser2@nrfa.co.zm", "tolluser2@nrfa.co.zm", "+260972019919", UserStatus.ACTIVE.name(), 
							null, Boolean.FALSE, null, "001", "NRFA", "Chola", "Chinedu", "Mwenya", RoleType.NFRA_TOLL_STAFF.name());*/
			/*/*at.addNewUser2("nrfadev@probasegroup.com", "nrfaadmin@probasegroup.com", "+260972018129", UserStatus.ACTIVE.name(), 
					null, Boolean.FALSE, null, "001", "NRFA", "Lucky1", "Somili1", "Muleyu1", RoleType.NFRA_ADMIN_STAFF.name());
			at.addNewUser2("test2@zanazo.co.zm", "test2@zanazo.co.zm", "+260972812129", UserStatus.ACTIVE.name(), 
					null, Boolean.FALSE, null, "001", "031", "Charles", "Mare", "Madini", RoleType.BANK_STAFF.name());*/
					
			//2. Authenticate User
			//Admin Password: iaVzphRo
			/*String jsonObjectStr1 = (String)(at.login("nrfaadmin@probasegroup.com", "password", "NRFA", Boolean.FALSE, "dasdadsadwe2322r2r2r2rr2rasda", "2342424222323243223442dd").getEntity());
			System.out.println(jsonObjectStr1);
			JSONObject userData1 = new JSONObject(jsonObjectStr1);
			String token1 = userData1.getString("token");
			System.out.println(token1);
			
			PaymentFunction pf = PaymentFunction.getInstance();
			Response rs = pf.listTransactions("031", null, null, null, null, null, null, null, null, null, "2017-09-21", "2017-09-23", null, 0, 50, token1, "123443");
			
			System.out.println((rs.getEntity().toString()));
			
			
			ReportFunction rpFn = ReportFunction.getInstance();
			Response rs = rpFn.getCardList(null, null, token1, null, null, null, null, null, "Test 123", "127.0.0.1");
			
			
			rs = rpFn.listTransactions( null, null, null, null, null, null,
					null, null, null, null, null, null, null, null, null, null, null, null, token1, "127.0.0.1");
			
			rs = rpFn.getTollPlazaByTransactionCountAndValue("1001",
					null, null, token1, "121212");
			System.out.println("rs..." + rs.getEntity().toString());
			
			rs = rpFn.getMerchantsByTransactionCountAndValue(null,
					null, null, token1, "121212");
			System.out.println("rs..." + rs.getEntity().toString());*/
			
			/*CardFunction cf = CardFunction.getInstance();
			JSONObject json = new JSONObject();
			json.put("0", "89AJ29A0");
			json.put("1", "89AJ281K");
			json.put("2", "8KD82K03");
			json.put("3", "LOPA3923");
			json.put("4", "923SKD23");
			json.put("5", "202LD23");
			json.put("6", "910293E");
			cf.assignUniqueIdToCard("TNXMJCKSUN", json.toString(), token1);
			//at.logout(token1);
			/*JSONObject userData1 = new JSONObject(jsonObjectStr1);
			String token1 = userData1.getString("token");
			//String token1 = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI5RUdIbHE2OVU0IiwiaWF0IjoxNTA1ODc2MDc3LCJzdWIiOiJ7XCJtYXBcIjp7XCJ1c2VybmFtZVwiOlwibnJmYWRldkBwcm9iYXNlZ3JvdXAuY29tXCIsXCJyb2xlXCI6XCJORlJBX0FETUlOX1NUQUZGXCIsXCJicmFuY2hDb2RlXCI6XCIwMDFcIixcImJhbmtDb2RlXCI6XCJQUk9CQVNFXCJ9fSIsImlzcyI6IlBST0JBU0UiLCJhdWQiOiIwMDEiLCJleHAiOjE1MDU4Nzk2Nzd9.ZEE4Bp0Nfa70dKOCxOUrE8222P5L2HAfp_iW2zzSGqk";
			
			UtilityServices cardServices = new UtilityServices();
			UtilityFunction uFunction = UtilityFunction.getInstance();
			uFunction.createNewBank("Stanbic", "http://stanbic.co.za", "031", token1);
			System.out.println("End");
			
			
			//sendGet("http://localhost:8080/TestWebService/NCE/services/AuthenticationServices/demo-get-method", null);
			JSONObject jsObj = new JSONObject();
			String jsonObjectStr = sendPost("http://"+BASE_ENDPOINT+"/NFRAWebService/NCE/services/AuthenticationServices/authenticateUser", 
					"username=nrfaadmin@probasegroup.com&encPassword=password&bankCode=PROBASE",
					null);
			System.out.println(jsonObjectStr);
			JSONObject userData = new JSONObject(jsonObjectStr);
			String token = userData.getString("token");*/
			
			/*jsObj = new JSONObject();
			jsObj.put("auth_token", token);
			jsonObjectStr = sendPost("http://"+BASE_ENDPOINT+"/TestWebService/NCE/services/PaymentServices/getTrafficTollRouteIdentifers", 
					null,
					jsObj);
			System.out.println(jsonObjectStr);
			//Create New bank
			jsObj = new JSONObject();
			jsObj.put("auth_token", token);
			jsonObjectStr = sendPost("http://localhost:8080/TestWebService/NCE/services/UtilityServices/createNewBank", 
					"bankName=Stanbic&fqdn=http://stanbic.co.za&bankCode=031",
					jsObj);
			System.out.println(jsonObjectStr);
*/

			//List banks
			/*jsObj = new JSONObject();
			jsObj.put("auth_token", token);
			jsonObjectStr = sendPost("http://localhost:8080/TestWebService/NCE/services/UtilityServices/getBanks", 
					null,
					jsObj);*/
			


			/*jsObj = new JSONObject();
			jsObj.put("auth_token", token);
			jsonObjectStr = sendPost("http://159.8.22.212:8080/TestWebService/NCE/services/UtilityServices/getBanks", 
					null,
					jsObj);*/
			
			/*jsObj = new JSONObject();
			jsObj.put("auth_token", token);
			jsonObjectStr = sendPost("http://"+BASE_ENDPOINT+"/TestWebService/NCE/services/MerchantServices/createNewMerchantScheme", 
					"schemename=Scheme002&schemecode=002&transactionPercentage=0.0&fixedCharge=0.0",
					jsObj);
			System.out.println("jsonObjectStr == " + jsonObjectStr);
			
			jsObj = new JSONObject();
			jsObj.put("auth_token", token);
			jsonObjectStr = sendPost("http://"+BASE_ENDPOINT+"/TestWebService/NCE/services/MerchantServices/getMerchantSchemeList", 
					null,
					jsObj);
			
			jsObj = new JSONObject();
			jsObj.put("auth_token", token);
			jsonObjectStr = sendPost("http://"+BASE_ENDPOINT+"/TestWebService/NCE/services/MerchantServices/createNewMerchant", 
					"merchantName=Stanbic&merchantBankCode=031&merchantSchemeCode=001",
					jsObj);
			System.out.println("jsonObjectStr == " + jsonObjectStr);
			
			jsObj = new JSONObject();
			jsObj.put("auth_token", token);
			jsonObjectStr = sendPost("http://"+BASE_ENDPOINT+"/TestWebService/NCE/services/CardServices/addNewCardScheme", 
					"schemeName=CardScheme01&schemeDetails=test&extraCharges=0.00&transactionFee=0.00",
					jsObj);
			System.out.println("jsonObjectStr == " + jsonObjectStr);*/
			
			/*jsObj = new JSONObject();
			jsObj.put("auth_token", token);
			jsonObjectStr = sendPost("http://"+BASE_ENDPOINT+"/TestWebService/NCE/services/CardServices/addNewCard", 
					"cardType=CardScheme01&cardScheme=test&extraCharges=0.00&transactionFee=0.00",
					jsObj);*/

			//bankuser@gmail.com	XOhTF0Do
			//testtollsupervisor@gmail.com	yLCo8eMT
			System.out.println("111");
			String str;
			//String str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/AuthenticationServices/authenticateUser/", "username=superadmin@probasegroup.com&encPassword=dCFF0RHE&bankCode=PROBASE", new JSONObject());
			String qry;
			qry = "username=nrfaadmin@probasegroup.com&encPassword=password&bankCode=NRFA";
			//qry = "username=testtollsupervisor@gmail.com&encPassword=yLCo8eMT&bankCode=NRFA";
			//qry = "username=bankuser@gmail.com&encPassword=XOhTF0Do&bankCode=001";
			//qry = "username=distributor1@gmail.com&encPassword=XOhTF0Do&bankCode=001";
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/AuthenticationServices/authenticateUser/", qry, new JSONObject());
			str = "";
			/*JSONObject js = new JSONObject(str);
			String token = js.getString("token");
			JSONObject authHeader = new JSONObject();
			authHeader.put("auth_token", token);*/
			
			qry = "username=testtollsupervisor@gmail.com&email=testtollsupervisor@gmail.com&mobileNumber=260964926646&userStatus=" + UserStatus.ACTIVE + "&branchCode=001&bankCode=NRFA&firstName=Bhon&lastName=Lars&roleCode=" + RoleType.NFRA_TOLL_STAFF_SUPERVISOR.name();
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/AuthenticationServices/addNewUser/", qry, authHeader);
			
			
			qry = "schemeName=Test Scheme&schemeDetails=Testing schemes&extraCharges=0.10&schemeCode=SCH10912&transactionFee=1.00&updateFlag=false";
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/CardServices/addNewCardScheme/", qry, authHeader);
			
			qry = "";
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/CardServices/getCardBatchIds/", qry, authHeader);
			
			qry = "batchId=DBLIPUVEYK&startIndex=0&limit=50";
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/CardServices/getListCardsByBatchIdWithNoUniqueId/", qry, authHeader);
			//CardFunction cf = new CardFunction();
			//cf.getListCardsByBatchIdWithNoUniqueId("DBLIPUVEYK", 0, 50, token, "34", "127.0.0.1");
			//cf.getCardList("001", 0, 50, "DBLIPUVEYK", token, Boolean.FALSE, null, null, "23", "127.0.0.1");
			//cf.getLastFiveTransactions("000100195401403", "1954", token, "23");
			//cf.getCard("000100195401403", "1954", token, "23", "127.0.0.1");
			
			//MerchantFunction mf = new MerchantFunction();
			//mf.createNewMerchant("Teddy Sales", "001", "M1023", Boolean.FALSE, token, "23", "127.0.0.1");
			//mf.getMerchantList(0, 50, token, "34");
			//mf.getMerchant("M00120", token, "23");
			//mf.createNewMerchantScheme("New Scheme Name", "M00120", 1.5, 0.00, token, "23", "127.0.0.1");
			//mf.getMerchantScheme("M00120", token, "43");
			//mf.getMerchantSchemeList(token, "54");
			//mf.getMerchantCount(MerchantStatus.ACTIVE.name(), "001", "56");
			
			//PaymentFunction pf = new PaymentFunction();
			String startDate = null, endDate = null;
			//pf.listTransactions(null, null, null, null, null, null, null, null, null, null, startDate, endDate, null, 0, 50, token, "76");
			//pf.generateEODTransactionList("001", token, "2017-12-02", "89", "127.0.0.1");
			
			qry = "username=testtolluser@gmail.com&tollRouteCode=M1023&tollRouteLane=1";
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/AuthenticationServices/assignTollStaffToTollRoute/", qry, authHeader);
			
			qry = "";
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/AuthenticationServices/listUsers/", qry, authHeader);
			
			qry = "username=testtolluser@gmail.com&userStatus=" + UserStatus.ACTIVE.name();
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/AuthenticationServices/updateUserStatus/", qry, authHeader);
			
			qry = "terminalCount=1&merchantCode=001&deviceType=" + DeviceType.POS.name();
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/TerminalServices/createNewTerminal/", qry, authHeader);
			
			qry = "startIndex=0&merchantCode=001";
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/TerminalServices/listTerminals/", qry, authHeader);
			
			qry = "terminalSerialId=190192&deviceType=" + DeviceType.POS.name();
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/TerminalServices/syncSetUpData/", qry, authHeader);
			
			qry = "contactEmail=distributor1@gmail.com&contactMobile=260964926646&address=1 Tern Road&city=Lusaka&district=Lusaka&province=Lusaka&companyName=Terb" +
					"&companyRegNo=RC18291&distributorStatus="+DistributorStatus.ACTIVE.name()+"&fundingAccountType="+FundingAccountType.DISTRIBUTOR_BANK_ACCOUNT.name()+
					"&distributorType="+DistributorType.INDIVIDUAL+"&contactFullName=Paul Reamet&merchantCode=001";
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/DistributorServices/createDistributor/", qry, authHeader);
			
			String orderId = RandomStringUtils.randomNumeric(8);
			String hash="12";
			qry = "distributorCode=16957959&amount="+2000.00+"&hash="+hash+"&orderId="+orderId+"&serviceType="+ServiceType.DISTRIBUTOR_DEPOSIT_CASH_INTO_DISTRIBUTOR_ACCOUNT+"&terminalId=81354";
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/DistributorServices/creditDistributorAccount/", qry, authHeader);
			
			qry = "";
			//str = sendGet("http://localhost:8080/NFRAWebService/NCE/services/CardServices/getCardSchemes/", qry, authHeader);
			
			String cardScheme = "8531";
			qry = "cardType=" + CardType.NRFA_TOLL_CARD_DEBIT_CARD.name() + "&cardScheme=" + cardScheme + "&quantity=5&merchantCode=001&branchCode=001";
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/CardServices/addNewCard/", qry, authHeader);
			
			JSONObject cardList = new JSONObject();
			cardList.put("0", "1954");
			cardList.put("1", "1893");
			qry = "batchId=DBLIPUVEYK&cardList="+cardList;
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/CardServices/assignUniqueIdToCard/", qry, authHeader);
					
			
			JSONObject jsCard = new JSONObject();
			JSONArray jsArr = new JSONArray();
			jsCard.put("cardPan", "000100195401403");
			jsCard.put("cardUniqueId", "1954");
			jsArr.put(jsCard);
			jsCard = new JSONObject();
			jsCard.put("cardPan", "000100189352638");
			jsCard.put("cardUniqueId", "1893");
			jsArr.put(jsCard);
			qry = "distributorCode=16957959&cardList=" + jsArr.toString();
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/DistributorServices/assignCardsToDistributor/", qry, authHeader);
			
			
			qry = "merchantCode=001&startIndex=0&limit=50";
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/DistributorServices/listDistributors/", qry, authHeader);
			
			
			qry = "";
			//str = sendGet("http://localhost:8080/NFRAWebService/NCE/services/DistributorServices/getFundingAccountTypes/", qry, authHeader);
			
			qry = "cardPan=000100195401403&cardUniqueId=1954&customerMobile=260964926646&distributorCode=16957959&firstName=Bopa&lastName" +
					"=Lasini&otherName=Brood&contactEmail=bopa.lasini@gmail.com&customerType="+CustomerType.INDIVIDUAL.name()+
					"&distributorTxnHash="+hash+"&orderId="+orderId+"&distributorTerminalId="+81354+"&amount="+500+
					"&narration=Assign Customer Card - 000100195401403&distributorChannel" +
					"="+Channel.POS+"&vehicleRegNumber=YPA91KS&nrfaCurrency="+NFRACurrency.ZAMBIAN_KWACHA.name();
			//str = sendPost("http://localhost:8080/NFRAWebService/NCE/services/DistributorServices/assignCardsToCustomer/", qry, authHeader);
			
			qry = "distributorStatus="+DistributorStatus.ACTIVE.name()+"&merchantCode=001";
			qry = "cardStatus=" + CardStatus.ACTIVATED_LEVEL_1.name();
			//str = sendGet("http://localhost:8080/NFRAWebService/NCE/services/CardServices/getCardTypes", qry, authHeader);
			
			System.out.println("str == " + str);
			
			//terminalId=81354		terminalApiKey=sMwrnf9GDoPnpq83I8JMyHayluD720wK		serialNo=190192
			
			JSONObject qry1 = new JSONObject();
			JSONObject header = new JSONObject();
			header.put("serviceType", "cardDepositsEodNotification");
			qry1.put("header", header);
			JSONArray payload = new JSONArray();
			JSONObject data1 = new JSONObject();
			data1.put("distributorId", "10574589");
			data1.put("merchantCode", "1057458");
			data1.put("bankCode", "033");
			data1.put("bankName", "Stanic bank");
			data1.put("cardPanNumber", "74546557525565");
			data1.put("transactionDate", "2017-09-26");
			data1.put("amount", 3);
			data1.put("transactionReference", "TEST00301");
			payload.put(data1);

			JSONObject data2 = new JSONObject();
			data2.put("distributorId", "10574589");
			data2.put("merchantCode", "1057458");
			data2.put("bankCode", "033");
			data2.put("bankName", "Stanic bank");
			data2.put("cardPanNumber", "4545457878785");
			data2.put("transactionDate", "2017-10-26");
			data2.put("amount", 3);
			data2.put("transactionReference", "TEST00302");
			payload.put(data2);
			qry1.put("payload", payload);
			str = sendPost("http://probase.a2hosted.com/SmartPay/services/nrfa/eod", qry1.toString(), null);
			
			
			/*String token = "eyJpdiI6ImMzbDRXRkZXTkVKNFFrcHFUMHByYlE9PSIsInZhbHVlIjoiaHBmcmhYVVJIc2tsVzMwNlMwTUh5QT09IiwibWFjIjoiMj9kP11LSjd5Py4/P29YPz94JyE3XHUwMDE0P35AVz8oaT9cdTAwMDQ/In0=";
			
			MerchantFunction mf = MerchantFunction.getInstance();
			mf.createNewMerchant("Stanbic", "031", "001", token);*/
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	private static String sendGet(String baseUrl, String parameters, JSONObject jsObj) throws Exception {

		String url = baseUrl + (parameters!=null ? ("?" + parameters) : "");
		System.out.println("url ==" + url);
		url="";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		if(jsObj!=null && jsObj.length()>0)
		{
			Iterator<String> iter = jsObj.keys();
			while(iter.hasNext())
			{
				String key = iter.next();
				con.setRequestProperty(key, jsObj.getString(key));
			}
		}

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
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

	// HTTP POST request
	private static String sendPost(String baseUrl, String parameters, JSONObject jsObj) throws Exception {

		String url = "https://selfsolve.apple.com/wcResults.do";
		url = baseUrl;
		System.out.println("url == " + url);
		url = "";
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
	
			//print result
			System.out.println(response.toString());
			return response.toString();
		}
		else
		{
			InputStream is1 = con.getErrorStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is1));
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

}
