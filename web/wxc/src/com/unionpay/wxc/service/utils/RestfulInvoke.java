package com.unionpay.wxc.service.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RestfulInvoke {
	
	public static final String targetURL = "https://30ba35f9f6c34bb9ad288932c087e15f-vp0.us.blockchain.ibm.com:5002/chaincode";
//	private static final String targetURL = "https://www.baidu.com";

	public static void main(String[] args) {
		 
		try {
			 
			URL targetUrl = new URL(targetURL);
			
			HttpsURLConnection httpConnection = (HttpsURLConnection) targetUrl.openConnection();
			
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
	 
//			String input = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
			String input = "{"
			+"	     \"jsonrpc\": \"2.0\","
			+"	     \"method\": \"query\","
			+"	     \"params\": {"
			+"	         \"type\": 1,"
			+"	         \"chaincodeID\": {"
			+"	             \"name\": \"26267ecf373f393dd3d61ba060a65245894a607b81f6bf1615671274caaf838b081ac10b00c8438be3f2e8992a59d17f65306aae3b551a9d4cc0220d83f4d1f6\""
			+"	         },"
			+"	         \"ctorMsg\": {"
			+"	             \"function\": \"read\","
			+"	             \"args\": ["
			+"	                 \"hello_world\""
			+"	             ]"
			+"	         },"
			+"	         \"secureContext\": \"admin\""
			+"	     },"
			+"	     \"id\": 2131231"
			+"	 }";
	 
//			String invokerInput = "{"
//		+"     \"jsonrpc\": \"2.0\",                    "
//		+"	     \"method\": \"invoke\",                "
//		+"	     \"params\": {                          "
//		+"	         \"type\": 1,                       "
//		+"	         \"chaincodeID\": {                 "
//		+"	             \"name\": \"3aeb9793d67968f966f2b093c361c70cdbf7a2813a02f7a5da344386580d3b519899b73003b335c587e3d016d44b54eb7d8030bddddbc3e9abf05db81c20eaef\""
//		+"	         },                                  "
//		+"	         \"ctorMsg\": {                      "
//		+"	             \"function\": \"write\",        "
//		+"	             \"args\": [                     "
//		+"	                 \"hello_world\",            "
//		+"	                 \"halo lch\"                "
//		+"	             ]                               "
//		+"	         },                                  "
//		+"	         \"secureContext\": \"admin\"        "
//		+"	     },                                      "
//		+"	     \"id\": 3                               "
//		+"	 }";
			String hackathonDeploy = "{"
		+"		     \"jsonrpc\": \"2.0\",                                                                  "
		+"		     \"method\": \"deploy\",                                                                "
		+"		     \"params\": {                                                                          "
		+"		         \"type\": 1,                                                                       "
		+"		         \"chaincodeID\": {                                                                 "
		+"		             \"path\": \"https://github.com/YESPPG/learn-chaincode/blob/master/finished\"    "
		+"		         },                                                                                 "
		+"		         \"ctorMsg\": {                                                                     "
		+"		             \"function\": \"init\",                                                        "
		+"		             \"args\": [                                                                    "
		+"		                 \"init\"                                                                     "
		+"		             ]                                                                              "
		+"		         },                                                                                 "
		+"		         \"secureContext\": \"admin\"                                                       "
		+"		     },                                                                                     "
		+"		     \"id\": 1	      																		"																			
		+"		 }";
			
			String hackathonQuery = "{"
		+"		     \"jsonrpc\": \"2.0\",                                                                  "
		+"		     \"method\": \"deploy\",                                                                "
		+"		     \"params\": {                                                                          "
		+"		         \"type\": 1,                                                                       "
		+"		         \"chaincodeID\": {                                                                 "
		+"		             \"path\": \"https://github.com/YESPPG/learn-chaincode/blob/master/finished\"    "
		+"		         },                                                                                 "
		+"		         \"ctorMsg\": {                                                                     "
		+"		             \"function\": \"init\",                                                        "
		+"		             \"args\": [                                                                    "
		+"		                 \"init\"                                                                     "
		+"		             ]                                                                              "
		+"		         },                                                                                 "
		+"		         \"secureContext\": \"admin\"                                                       "
		+"		     },                                                                                     "
		+"		     \"id\": 1	      																		"																			
		+"		 }";
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(hackathonDeploy.getBytes());
			outputStream.flush();
	 
			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ httpConnection.getResponseCode());
			}
	 
			BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
					(httpConnection.getInputStream())));
	 
			String output;
			System.out.println("Output from Server:\n");
			while ((output = responseBuffer.readLine()) != null) {
				System.out.println(output);
			}
	 
			httpConnection.disconnect();
	 
		  } catch (MalformedURLException e) {
	 
			e.printStackTrace();
	 
		  } catch (IOException e) {
	 
			e.printStackTrace();
	 
		 }
	 
		}
	 
	 
}
