package com.unionpay.wxc.server;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;



public class HttpRequestModel {

	/**
	 * jersey框架发送get请求服务
	 * @param url
	 * @return
	 */
	public static String HttpJerseyGet(String url){
		Client client = Client.create();
		WebResource webResource = client.resource(url);  
		String result = webResource.get(String.class); 
		return result;
	}
	
	/**
	 * jersey框架发送get请求服务
	 * @param url
	 * @return
	 */
	public static String HttpJerseyGetParam(String url,String param){
		Client client = Client.create();
		WebResource webResource = client.resource(url);  
		MultivaluedMapImpl params = new MultivaluedMapImpl();
		params.add("jsonString", param);
		String result = webResource.queryParams(params).get(String.class); 
		return result;
	}
	
	/**
	 * Jersey框架发送post请求服务
	 * @param url
	 * @param data
	 * @return
	 */
	public static String HttpJerseyPost(String url, String data){
		Client client = Client.create(); 
		WebResource resource = client.resource(url); 
//		MultivaluedMapImpl params = new MultivaluedMapImpl();  
		
		String result = resource.entity(data).post(String.class);  
		return result;
	}
	
}
