package com.unionpay.wxc.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.unionpay.wxc.dao.ConnectionPool;

@Controller
@RequestMapping("/authorize")
public class AuthorizeService {

	static String[] sexs = { "未知", "男", "女" };
	static final String ethereumURL = "";
	static String uid = "";
	final static String dbURL = "jdbc:mysql://localhost:3306/wxc";
	final static String dbDriver = "com.mysql.jdbc.Driver";
	final static String dbUser = "root";
	final static String dbPassWord = "perhaps123";

	public AuthorizeService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "/author", method = RequestMethod.POST)
	@ResponseBody
	public String authorize(@RequestBody Map<String, String> params) {
		System.out.println("用户id: " + uid);
		String uid = params.get("uid").toString();
		JSONObject request1 = new JSONObject();
		request1.put("authorize", TagsService.map.get(uid));
		System.out.println(TagsService.map.get(uid).toString());
		request1.put("notice", 3);
		return request1.toString();
	}


	@RequestMapping(value = "/acceptAuthorize", method = RequestMethod.POST)
	@ResponseBody
	public String acceptAuthorize(@RequestBody Map<String, String> params)  {
		
		JSONArray array = TagsService.map.get(params.get("uid"));
		String nickName = params.get("nickName");
		int j = -1;
		for (int i = 0; i < array.size(); i++) {
			if (nickName.equals(array.getJSONObject(i).getString("name"))) {
				array.getJSONObject(i).put("isAuthorize", true);
				Client client = Client.create();
				WebResource resource = client.resource("http://118.178.136.41:8080/labelContract/auth");  
				MultivaluedMapImpl paramsA = new MultivaluedMapImpl();
				JSONObject map = new JSONObject();
				map.put("userId", params.get("uid").toString());
				map.put("fromId", nickName);
				map.put("amount", array.getJSONObject(i).getString("preFee"));
				paramsA.add("jsonString", map.toString());
				System.out.println("jsonString:"+map.toString());
				String response = resource.post(String.class,paramsA);
				System.out.println("response:"+response);
				j=i;
			}
		}
		if(j>=0){
			array.remove(j);
		}
		// TODO 通知区块连
		TagsService.map.put(params.get("uid"),array);
		
		return array.toString();
	}

	@RequestMapping(value = "/deleteAuthorize", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAuthorize(@RequestBody Map<String, String> params)  {
		JSONArray array = TagsService.map.get(params.get("uid"));
		String nickName = params.get("nickName");
		int j = -1;
		for (int i = 0; i < array.size(); i++) {
			if (nickName.equals(array.getJSONObject(i).getString("name"))) {
				j=i;
			}
		}
		if(j>=0){
			array.remove(j);
		}
		// TODO 通知区块连
		TagsService.map.put(params.get("uid"),array);
		return array.toString();
	}
	
}