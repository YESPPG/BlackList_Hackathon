package com.unionpay.wxc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unionpay.wxc.dao.ConnectionPool;

import javax.servlet.http.HttpServletRequest;


import net.sf.json.JSONObject;


/**
 * 
 * @author xiaoyingdong
 *
 */

@Controller
@RequestMapping("/user")
public class UserService extends AbstractService{

	static String[] sexs = {"未知","男","女"};
	public UserService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String userInfoTransCheck(
	    @RequestParam ("nickName") String nickName,
		@RequestParam ("gender") int gender,
		@RequestParam ("iconUrl") String iconUrl,
		@RequestParam ("language") String language,
		@RequestParam ("province") String province,
		@RequestParam ("city") String city,HttpServletRequest request)
	{
		// 用户数据绑定
		String ipAddress = request.getRemoteAddr();
		System.out.println(
			"用户昵称: "+nickName+
			"\n性别:  "+sexs[gender]+
			"\n"+iconUrl+
			"\nlanguage  "+language+
			"\n省份:  "+province+
			"\n城市/地区:  "+city+
			"\nIP地址:  "+ipAddress
				);
		int count =0;
		try{
			   //创建数据库连接库对象  
		       ConnectionPool connPool  = new ConnectionPool("com.mysql.jdbc.Driver"  
			           ,"jdbc:mysql://localhost:3306/wxc"  
			           ,"root"     
			           ,"perhaps123");  
			   //新建数据库连接库  
			   connPool.createPool(); 
			   String sql="insert into tbl_mc_user (wx_nick, wx_icon, gender, ip_token) values (?,?,?,?)";  
			   Connection conn = connPool.getConnection(); //从连接库中获取一个可用的连接  
		       Statement stmt = conn.prepareStatement(sql);  
		       ((PreparedStatement) stmt).setString(1, nickName);
		       ((PreparedStatement) stmt).setString(2, iconUrl);
		       ((PreparedStatement) stmt).setString(3, sexs[gender]);
		       ((PreparedStatement) stmt).setString(4, ipAddress);
		       count =  ((PreparedStatement) stmt).executeUpdate();
		       stmt.close();
		       conn.close();
		}catch(Exception e){
			
		}
		JSONObject result = new JSONObject();
		if(count>0){
			result.put("msg", "0001");
		}
		else{
			result.put("msg", "0004");
		}
		return result.toString();
	}
	


	
}
