package com.unionpay.wxc.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import com.unionpay.wxc.server.HttpRequestModel;
import com.unionpay.wxc.service.utils.InvokerUtil;
import com.unionpay.wxc.service.utils.RestfulInvoke;

import net.sf.json.JSONObject;

public class ReadQueryThread extends Thread{
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
            // 循环读取list2拿到query数据
    		while(true) {
    			System.out.println("loop read querySet...");
    			JSONObject json = new JSONObject();
    			json.put("query", hashAddr);
    			String jsonString = json.toString();
//    			String result = HttpRequestModel.HttpJerseyPost(ethereumURL + "readQuerySet", "jsonString=" + jsonString);
    			String result = invokerReadResult("B");
    			JSONObject jsonRes = JSONObject.fromObject(result);
    			String toParam = (String)jsonRes.get("to");
    			if(toParam.equals(hashAddr)) {
    				System.out.println("target matches current node, write to resultSet.");
    				// 模拟查库
    				// 生成0-100随机数
    				Random random = new Random();
    				int randomScore = random.nextInt(100);
    				
    				// 写入resultSet list1中
    				JSONObject data = new JSONObject();
    				data.put("from", jsonRes.get("from")==null?"unknown":jsonRes.get("from").toString());
    				data.put("to", toParam);
    				data.put("score", randomScore);
    				String jsonStr2Res = data.toString();
//    				String res = HttpRequestModel.HttpJerseyPost(ethereumURL + "write2ResultSet", "jsonString=" + jsonStr2Res);
    				String res = invokerWriteResult(jsonRes.get("from").toString(),jsonStr2Res);
    			}
    		}
        }  
    } 
	
	private String invokerReadResult(String key){

		 String output="";
		try {
			 
			URL targetUrl = new URL(RestfulInvoke.targetURL);
			
			HttpsURLConnection httpConnection = (HttpsURLConnection) targetUrl.openConnection();
			
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
	 
//			String input = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
			String input = InvokerUtil.readQuery(key);
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
	private String invokerWriteResult(String result,String from){

		 String output="";
		try {
			 
			URL targetUrl = new URL(RestfulInvoke.targetURL);
			
			HttpsURLConnection httpConnection = (HttpsURLConnection) targetUrl.openConnection();
			
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
	 
//			String input = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
			String input = InvokerUtil.writeResult(from, "B", result);
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
