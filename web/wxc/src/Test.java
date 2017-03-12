import java.util.Map;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.unionpay.wxc.server.HttpRequestModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {

		/*String ethereumURL = "http://118.178.136.41:8080/labelContract";
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("userId", "lichenhuan");
		array.add(json);
		String result = HttpRequestModel.HttpJerseyGetParam(ethereumURL + "/persons/info" , array.toString().replace("\"", "'"));
		System.out.println(result);
		
		ethereumURL = "http://118.178.136.41:8080/labelContract";
		array = new JSONArray();
		json = new JSONObject();
		json.put("userId", "lichenhuan");
		array.add(json);
		result = HttpRequestModel.HttpJerseyGetParam(ethereumURL + "/labels/info" , array.toString().replace("\"", "'"));
		System.out.println(result);
		
		ethereumURL = "http://118.178.136.41:8080/labelContract";
		array = new JSONArray();
		json = new JSONObject();
		json.put("userId", "lichenhuan");
		array.add(json);
		result = HttpRequestModel.HttpJerseyGetParam(ethereumURL + "/labels/info" , array.toString().replace("\"", "'"));
		System.out.println(result);
		

	    ethereumURL = "http://118.178.136.41:8080/labelContract";
		array = new JSONArray();
		json = new JSONObject();
		json.put("userId", "lichenhuan");
		array.add(json);
		result = HttpRequestModel.HttpJerseyGetParam(ethereumURL + "/labels/info" , array.toString().replace("\"", "'"));
		System.out.println(result);
		*/
		
//		Client client = Client.create();
//		WebResource resource = client.resource("http://118.178.136.41:8080/labelContract/newAuth");  
//		MultivaluedMapImpl paramsA = new MultivaluedMapImpl();
//		JSONObject map = new JSONObject();
//		map.put("userId", "Porco Rosso");
//		map.put("toId", "xiaoyingdong");
//		map.put("amount", "100");
//		paramsA.add("jsonString", map.toString());
//		String response = resource.post(String.class,paramsA);
//		System.out.println(response);
//		
//		 Client client = Client.create();
//		 WebResource resource = client.resource("http://118.178.136.41:8080/labelContract/auth");  
//		 MultivaluedMapImpl paramsA = new MultivaluedMapImpl();
//		 JSONObject map = new JSONObject();
//		map.put("userId", "xiaoyingdong");
//		map.put("fromId", "Porco Rosso");
//		map.put("amount", "100");
//		paramsA.add("jsonString", map.toString());
//		String response = resource.post(String.class,paramsA);
//		System.out.println(response);
		String response = HttpRequestModel
				.HttpJerseyGet("http://118.178.136.41:8080/ethereum/account/uuid/" + "xiaoyingdong" + "/balance");
		System.out.print(response);
	}

}
