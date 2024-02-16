package com.probase.nrfa.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.log4j.Logger;

import com.probase.nrfa.models.SMSMesage;

public class SmsSender implements Runnable {
	private static Logger log = Logger.getLogger(SmsSender.class);
	SwpService swpService;
	String message;
	String receipientMobileNumber;
	final static String baseUrl = "http://smsapi.probasesms.com/apis/text/index.php";
	
	public SmsSender(SwpService swpService, String message, String receipientMobileNumber)
	{
		this.swpService = swpService;
		this.message = message;
		this.receipientMobileNumber = receipientMobileNumber;
	}
	
	public void run() {
		receipientMobileNumber = receipientMobileNumber.startsWith("260") ? receipientMobileNumber : (receipientMobileNumber.startsWith("0") ? ("260"+receipientMobileNumber.substring(1)) : null);
		if(receipientMobileNumber!=null)
		{
			try {
				String url = baseUrl + "?username=demo&password=password&mobiles="+receipientMobileNumber+"&message="+URLEncoder.encode(message,"UTF-8")+"&sender=NRFA&type=TEXT";
				URL obj;
				obj = new URL(url);
			
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
					String status = UtilityHelper.getSmsResponseStatus(response.toString());
					SMSMesage sms = new SMSMesage();
					sms.setReceipentMobileNumber(receipientMobileNumber);
					sms.setResponseCode(responseCode);
					sms.setMessage(message);
					sms.setDataResponse(response.toString());
					sms.setStatus(status==null ? "FAILED" : "SUCCESS");
					sms.setCreated_at(new Date());
					sms.setUpdated_at(new Date());
					swpService.createNewRecord(sms);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
