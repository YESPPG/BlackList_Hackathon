package com.unionpay.wxc.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.unionpay.wxc.dao.ConnectionPool;
import com.unionpay.wxc.server.HttpRequestModel;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author xiaoyingdong
 *
 */

@Controller
@RequestMapping("/merchant")
public class QueryService {
	static String[] sexs = {"未知","男","女"};
	public QueryService() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public static String uid = "";
	final static String dbURL = "jdbc:mysql://localhost:3306/wxc?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	final static String dbDriver = "com.mysql.jdbc.Driver";
	final static String dbUser = "root";
	final static String dbPassWord = "root";
	
	@RequestMapping(value = "/mchtInfo", method = RequestMethod.POST)
	@ResponseBody
	public String userInfoTrans(@RequestBody Map<String,Object> params,HttpServletRequest request ) {
		/*JSONObject request1 = new JSONObject();
		String msg = "[{name:'阿祖',city:'香港',tel:'13582753123',email:'wuyanzu@163.com',likeTotal:'1282',"
				+ "extraInfo:'香港吴彦祖，魔兽古尔丹',avatar:'/images/wuyanzu.png',unionId:'1'},"
				+"{name:'多多妈',city:'北京',tel:'13782758882',email:'duoduoma@126.com',likeTotal:'1032',"
				+ "extraInfo:'既来之则安之',avatar:'/images/duoduoma.png',unionId:'2'},"
				+"{name:'李晓天',city:'上海',tel:'17829385885',email:'lixiaotian@sina.com',likeTotal:'829',"
	    				+ "extraInfo:'既来之则安之',avatar:'/images/lixiaotian.png',unionId:'3'}]";
		String authorize = msg;
		request1.put("authorize",authorize);
		request1.put("msg", msg);
		request1.put("notice",3);
		return request1.toString();*/
		//获取远程IP
		String ipAddress = request.getRemoteAddr();
		HttpRequestModel ethModel = new HttpRequestModel();
		String url = "";
		JSONObject resToEth = new JSONObject();
		JSONObject resToWX = new JSONObject();
		resToEth.put("custOpenID", params.get("custOpenID"));
		resToEth.put("preFee", params.get("preFee"));		
		String res = ethModel.HttpJerseyPost(url, resToEth.toString());
		//resToEth.fromObject(res);
		if(res != "") {
			resToWX.put("transStatus","success");
			resToWX.put("msg",res);
			resToWX.put("notice",3);
			return resToWX.toString();
			
		} else {
			resToWX.put("transStatus","failed");
			resToWX.put("msg",res);
			resToWX.put("notice",0);
			return resToWX.toString();
		}
		//System.out.println("用户ID: " + params.get("custOpenID") + "\n出价:  " + params.get("preFee"));
	}
	
	@RequestMapping(value = "/mchtQuery", method = RequestMethod.POST)
	@ResponseBody
	public String userInfoQuery(@RequestBody Map<String,Object> params,HttpServletRequest request ) {
		System.out.println(params.get("custOpenID").toString());
		JSONArray array = new JSONArray();
		JSONObject json = new JSONObject();
		json = new JSONObject();
		json.put("isAuthorize", false);
		json.put("name", params.get("nickName").toString());
		json.put("preFee", params.get("preFee").toString());
		
		
		
		try {
			// 创建数据库连接库对象
			ConnectionPool connPool = new ConnectionPool(dbDriver, dbURL, dbUser, dbPassWord);
			// 新建数据库连接库
			connPool.createPool();
			String querySql = "select wx_icon from tbl_mc_user where uid = '" + params.get("custOpenID").toString() + "' ";
			Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(querySql);
			if (rs.next()) {
				// 表示用戶已經存在,更新IP地址
				json.put("avatar", rs.getString("wx_icon"));
			} else {
				json.put("avatar", "/images/wuyanzu.png");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		array.add(json);
		TagsService.map.put(params.get("custOpenID").toString(), array);
		System.out.println(TagsService.map.get(params.get("custOpenID").toString()).toString());
		
		Client client = Client.create();
		WebResource resource = client.resource("http://118.178.136.41:8080/labelContract/newAuth");  
		MultivaluedMapImpl paramsA = new MultivaluedMapImpl();
		JSONObject map = new JSONObject();
		map.put("userId", params.get("nickName").toString());
		map.put("toId", params.get("custOpenID").toString());
		map.put("amount", params.get("preFee").toString());
		paramsA.add("jsonString", map.toString());
		System.out.println("jsonString:"+map.toString());
		String response = resource.post(String.class,paramsA);
		System.out.println("response:"+response);
		JSONObject resToWX = new JSONObject();
		resToWX.put("transStatus","success");
		return resToWX.toString();
		
		//获取远程IP                  
		/*String ipAddress = request.getRemoteAddr();
		HttpRequestModel ethModel = new HttpRequestModel();
		String url = "";
		JSONObject resToEth = new JSONObject();
		JSONObject resToWX = new JSONObject();
		
		resToEth.put("nickName", params.get("nickName"));
		resToEth.put("custOpenID", params.get("custOpenID"));
		resToEth.put("preFee", params.get("preFee"));		
		String res = ethModel.HttpJerseyPost(url, resToEth.toString());
		if(res == "success") {
			resToWX.put("transStatus","success");
			return resToWX.toString();
			
		} else {
			resToWX.put("transStatus","failed");
			return resToWX.toString();
		}*/
	}
	
	/*@RequestMapping(value = "/mchtRegister", method = RequestMethod.POST)
	@ResponseBody
	public String userInfoRegister(@RequestBody Map<String,Object> params,HttpServletRequest request ) {
		//  行业
		String industry = (String)params.get("industry");
		//公司名称
		String companyName = (String)params.get("companyName");
		//公司地址
		String companyAddrs = (String)params.get("companyAddrs");
		
		// 绑定经办人相关信息
		String nickName = (String)params.get("nickName");
		String gender = sexs[Integer.parseInt(params.get("gender").toString())];
		String iconUrl = (String)params.get("wxicon");
		String ipAddress = request.getRemoteAddr();
		String uid = "test";
		*//**
		 *  数据库相关操作，用户信息入库
		 *//*
		int count =0;
		try{
			   //创建数据库连接库对象  
		       ConnectionPool connPool  = new ConnectionPool("com.mysql.jdbc.Driver"  
			           ,"jdbc:mysql://localhost:3306/wxc"  
			           ,"root"     
			           ,"perhaps123");  
			   //新建数据库连接库  
			   connPool.createPool();
			   //录入用户基本信息
			   String sql_user="insert into tbl_mc_user (wx_nick, wx_icon, gender, ip_token) values (?,?,?,?)";  
			   Connection conn = (Connection) connPool.getConnection(); //从连接库中获取一个可用的连接  
		       Statement stmt = (Statement) conn.prepareStatement(sql_user);  
		       ((PreparedStatement) stmt).setString(1, nickName);
		       ((PreparedStatement) stmt).setString(2, iconUrl);
		       ((PreparedStatement) stmt).setString(3, ipAddress);
		       
		       count =  ((PreparedStatement) stmt).executeUpdate();
		       stmt.close();
		       conn.close();
		       //区块链存储用户基本信息
		       //区块链存储标签数据
		       *//**
		        *   临时数据库存储测试
		        *//*
		       String sql_tag = "insert into tbl_mc_tags (birth_date, contact, mail, industry, work_for, work_address, job, fields, overhead, wx_id) values (?,?,?,?,?,?,?,?,?,?)";
		     //创建数据库连接库对象  
		       ConnectionPool connPool2  = new ConnectionPool("com.mysql.jdbc.Driver"  
			           ,"jdbc:mysql://localhost:3306/wxc"  
			           ,"root"     
			           ,"perhaps123");  
			   //新建数据库连接库  
			   connPool2.createPool();
			   Connection conn2 = (Connection) connPool2.getConnection(); //从连接库中获取一个可用的连接
		       Statement stmt2 = (Statement) conn2.prepareStatement(sql_tag);   
		       ((PreparedStatement) stmt2).setString(1, uid);
		       ((PreparedStatement) stmt2).setString(4, industry);
		       ((PreparedStatement) stmt2).setString(5, companyName);
		       ((PreparedStatement) stmt2).setString(6, companyAddrs);
		       count =  ((PreparedStatement) stmt2).executeUpdate();
		       
		       stmt2.close();
		       conn2.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		JSONObject result = new JSONObject();
		if(count>0){
			// 调用区块链接口得到推荐好友列表3个
			result.put("msg", "0001");
		}
		else{
			result.put("msg", "0004");
		}
		return result.toString();
	}
*/
}
