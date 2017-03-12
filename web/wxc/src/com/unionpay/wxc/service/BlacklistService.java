package com.unionpay.wxc.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unionpay.wxc.server.HttpRequestModel;
import com.unionpay.wxc.service.utils.InvokerUtil;
import com.unionpay.wxc.service.utils.RestfulInvoke;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/blacklist")
public class BlacklistService {
	static final String ethereumURL = "http://118.178.136.41:8080/query/";
	String hashAddr = "A";
	
	@RequestMapping(value = "/singleQuery")
	@ResponseBody
	public String singleQuery() {
		String To = "154684";
		String paramValue = "18217515170";
		
		
		invokerWriteQuery(hashAddr,paramValue);
		// 写入结构体列表list2中
		//JSONObject data = new JSONObject();
		//data.put("from", hashAddr);
		//data.put("to", paramTarget);
		//data.put("condition", paramValue);
		//data.put("price", paramPrice);
		//String jsonString = data.toString();

		//String result = HttpRequestModel.HttpJerseyPost(ethereumURL + "write2QuerySet", "jsonString=" + jsonString);
		//System.out.println(result);
			
		return null;
	}
	
	private String invokerWriteQuery(String to ,String queryValue){

		 String output="";
		try {
			 
			URL targetUrl = new URL(RestfulInvoke.targetURL);
			
			HttpsURLConnection httpConnection = (HttpsURLConnection) targetUrl.openConnection();
			
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
	 
//			String input = "{\"id\":1,\"firstName\":\"Liam\",\"age\":22,\"lastName\":\"Marco\"}";
			String input = InvokerUtil.writeQuery("B", to, queryValue);
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
//	public void readQuery() {
//		// 循环读取list2拿到query数据
//		while(true) {
//			System.out.println("loop read querySet...");
//			JSONObject json = new JSONObject();
//			json.put("query", hashAddr);
//			String jsonString = json.toString();
//			String result = HttpRequestModel.HttpJerseyPost(ethereumURL + "readQuerySet", "jsonString=" + jsonString);
//			JSONObject jsonRes = JSONObject.fromObject(result);
//			String toParam = (String)jsonRes.get("to");
//			if(toParam.equals(hashAddr)) {
//				System.out.println("target matches current node, write to resultSet.");
//				// 模拟查库
//				// 生成0-100随机数
//				Random random = new Random();
//				int randomScore = random.nextInt(100);
//				
//				// 写入resultSet list1中
//				JSONObject data = new JSONObject();
//				data.put("from", jsonRes.get("from"));
//				data.put("to", toParam);
//				data.put("score", randomScore);
//				String jsonStr2Res = data.toString();
//				String res = HttpRequestModel.HttpJerseyPost(ethereumURL + "write2ResultSet", "jsonString=" + jsonStr2Res);
//			}
//		}
//	}
//	
//	public void readResult() {
//		while(true) {
//			System.out.println("loop read resultSet for node to dispaly...");
//			JSONObject json = new JSONObject();
//			json.put("query", hashAddr);
//			String jsonString = json.toString();
//			String result = HttpRequestModel.HttpJerseyPost(ethereumURL + "readResultSet", "jsonString=" + jsonString);
//			JSONObject jsonRes = JSONObject.fromObject(result);
//			String fromParam = (String)jsonRes.get("from");
//			if(fromParam.equals(hashAddr)) {
//				// 显示到前端，查询到结果了
//				// jsonRes怎么传到前端？
//				
//			}
//		}
//	}
	
	/**
	 * 全网查询
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public String Query(@RequestBody Map<String,Object> params, HttpServletRequest request) {
		System.out.println(params);
		
		return null;
	}
}
