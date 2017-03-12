package com.unionpay.wxc.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.unionpay.wxc.server.HttpRequestModel;
import com.unionpay.wxc.service.utils.InvokerUtil;
import com.unionpay.wxc.service.utils.RestfulInvoke;

import net.sf.json.JSONObject;

public class ReadResultThread extends Thread{
	static final String ethereumURL = "http://118.178.136.41:8080/query/";
	String hashAddr = "A";
	
	public void run() {  
        while (!this.isInterrupted()) {// 线程未中断执行循环  
            try {  
                Thread.sleep(2000);  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
              
            // ------------------ 开始执行 ---------------------------  
            System.out.println(System.currentTimeMillis());  
            while(true) {
    			System.out.println
    			("loop read resultSet for node to dispaly...");
    			JSONObject json = new JSONObject();
    			json.put("query", hashAddr);
    			String jsonString = json.toString();
//    			String result = HttpRequestModel.HttpJerseyPost(ethereumURL + "readResultSet", "jsonString=" + jsonString);
    			String result = invokerResult("B");//B is company Name
    			JSONObject jsonRes = JSONObject.fromObject(result);
    			String fromParam = (String)jsonRes.get("from");
    			if(fromParam.equals(hashAddr)) {
    				// 显示到前端，查询到结果了
    				// jsonRes怎么传到前端？
    				System.out.println("query result is: " + jsonRes);
    			}
    		}
        }  
    }
	
	private String invokerResult(String key){

		 String output="";
		try {
			 
			URL targetUrl = new URL(RestfulInvoke.targetURL);
			
			HttpsURLConnection httpConnection = (HttpsURLConnection) targetUrl.openConnection();
			
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
	 
//			String input = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
			String input = InvokerUtil.readResult(key);;
			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();
	 
			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ httpConnection.getResponseCode());
			}
	 
			BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
					(httpConnection.getInputStream())));
	 
			while ((output = responseBuffer.readLine()) != null) {
//				System.out.println(output);
			}
			httpConnection.disconnect();

	 
		  } catch (MalformedURLException e) {
	 
			e.printStackTrace();
	 
		  } catch (IOException e) {
	 
			e.printStackTrace();
	 
		 }
		return output;
	}
}
