package com.unionpay.wxc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.unionpay.wxc.dao.ConnectionPool;
import com.unionpay.wxc.server.HttpRequestModel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author xiaoyingdong
 *
 */

@Controller
@RequestMapping("/tag")
@SessionAttributes("openid")
public class TagsService {

	static String[] sexs = { "未知", "男", "女" };
	static final String ethereumURL = "http://118.178.136.41:8080/labelContract/";
	public static String uid = "";
	final static String dbURL = "jdbc:mysql://localhost:3306/wxc?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	final static String dbDriver = "com.mysql.jdbc.Driver";
	final static String dbUser = "root";
	final static String dbPassWord = "root";

	public static HashMap<String, JSONArray> map = new HashMap<String, JSONArray>();

	public TagsService() {
		super();
		// TODO Auto-generated constructor stub
	}

	// 用户
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String makeTag(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		// 绑定标签相关信息
		String companyName = (String) params.get("companyName");
		String researchAreas = (String) params.get("researchAreas");
		String job = (String) params.get("job");
		String extraInfo = (String) params.get("extraInfo");
		String birthday = (String) params.get("birthday");
		String tel = (String) params.get("tel");
		String mail = (String) params.get("mail");
		String wxID = (String) params.get("wxID");
		String industry = (String) params.get("industry");
		String companyAddrs = (String) params.get("companyAddrs");
		// 绑定用户相关信息
		String nickName = (String) params.get("nickName");
		String gender = sexs[Integer.parseInt(params.get("gender").toString())];
		String iconUrl = (String) params.get("wxicon");
		String ipAddress = request.getHeader("X-Real-IP");
		// String ipAddress = request.getRemoteAddr();
		System.out.println("ip地址:  " + ipAddress);
		/**
		 * 数据库相关操作，用户信息入库
		 */
		int count = 0;
		try {
			// 创建数据库连接库对象
			ConnectionPool connPool = new ConnectionPool(dbDriver, dbURL, dbUser, dbPassWord);
			// 新建数据库连接库
			connPool.createPool();
			// 录入用户基本信息
			String sql_user = "insert into tbl_mc_user (wx_nick, wx_icon, gender, ip_token, uid) values (?,?,?,?,?)";
			Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接
			Statement stmt = conn.prepareStatement(sql_user);
			((PreparedStatement) stmt).setString(1, nickName);
			((PreparedStatement) stmt).setString(2, iconUrl);
			((PreparedStatement) stmt).setString(3, gender);
			((PreparedStatement) stmt).setString(4, ipAddress);
			((PreparedStatement) stmt).setString(5, nickName);

			count = ((PreparedStatement) stmt).executeUpdate();
			stmt.close();
			conn.close();

			/**
			 * 临时数据库存储测试
			 */
			String sql_tag = "insert into tbl_mc_tags (user_id, birth_date, contact, mail, industry, work_for, work_address, job, fields, overhead, wx_id) values (?,?,?,?,?,?,?,?,?,?,?)";
			// 创建数据库连接库对象
			ConnectionPool connPool2 = new ConnectionPool(dbDriver, dbURL, dbUser, dbPassWord);
			// 新建数据库连接库
			connPool2.createPool();
			Connection conn2 = connPool2.getConnection(); // 从连接库中获取一个可用的连接
			Statement stmt2 = conn2.prepareStatement(sql_tag);
			((PreparedStatement) stmt2).setString(1, nickName);
			((PreparedStatement) stmt2).setString(2, birthday);
			((PreparedStatement) stmt2).setString(3, tel);
			((PreparedStatement) stmt2).setString(4, mail);
			((PreparedStatement) stmt2).setString(5, industry);
			((PreparedStatement) stmt2).setString(6, companyName);
			((PreparedStatement) stmt2).setString(7, companyAddrs);
			((PreparedStatement) stmt2).setString(8, job);
			((PreparedStatement) stmt2).setString(9, researchAreas);
			((PreparedStatement) stmt2).setString(10, extraInfo);
			((PreparedStatement) stmt2).setString(11, wxID);
			count = ((PreparedStatement) stmt2).executeUpdate();

			stmt2.close();
			conn2.close();

			// 区块链存储用户基本信息
			JSONObject dataField = new JSONObject();
			dataField.put("userId", nickName);
			dataField.put("contact", tel);
			dataField.put("industry", industry);
			dataField.put("workFor", companyName);
			dataField.put("fields", researchAreas);
			dataField.put("overhead", extraInfo);
			dataField.put("wxNick", nickName);
			dataField.put("gender", params.get("gender").toString());
			dataField.put("ipToken", ipAddress);
			String jsonString = dataField.toString();
			String result = HttpRequestModel.HttpJerseyPost(ethereumURL + "newPerson", "jsonString=" + jsonString);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject result = new JSONObject();
		if (count > 0) {
			// 调用区块链接口得到推荐好友列表3个
			result.put("msg", "0001");
		} else {
			result.put("msg", "0004");
		}
		return result.toString();
	}

	// 判断用户是否已经打了标签
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public String checkTag(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		// 获取发送请求的ip地址信息
		// 调区块链上用户微信昵称，判断用户是否已经打了标签
		// 打了标签，则关联ip,进入交友推送页面，否则进入打标签页面
		// System.out.println(uid);
		// HttpSession session = request.getSession();
		// Map<String,String> map = new HashMap<String,String>();
		// map.put("uid", uid);
		// session.setAttribute("openid", map);
		// System.out.println("session: "+map.toString());
		// System.out.println("session1: "+session.getAttribute("openid"));
		uid = (String) params.get("uid");
		System.out.println("uid: " + uid);
		JSONObject response = new JSONObject();
		// 查询数据库中该uid用户是否已经存在
		try {
			// 创建数据库连接库对象
			ConnectionPool connPool = new ConnectionPool(dbDriver, dbURL, dbUser, dbPassWord);
			// 新建数据库连接库
			connPool.createPool();
			String querySql = "select * from tbl_mc_user where uid = '" + uid + "' ";
			Connection conn = connPool.getConnection(); // 从连接库中获取一个可用的连接
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(querySql);
			if (rs.next()) {
				// 表示用戶已經存在,更新IP地址
				// 執行更新操作
				ConnectionPool connPool2 = new ConnectionPool(dbDriver, dbURL, dbUser, dbPassWord);
				// 新建数据库连接库
				connPool2.createPool();
				Connection conn2 = connPool2.getConnection();
				String updateSql = "update tbl_mc_user set ip_token='" + request.getHeader("X-Real-IP")
						+ "' where uid = '" + uid + "' ";
				PreparedStatement pstmt2;
				pstmt2 = (PreparedStatement) conn2.prepareStatement(updateSql);
				int i = pstmt2.executeUpdate();
				System.out.println(i);
				pstmt2.close();
				conn2.close();
				System.out.println("user is exist !");
				response.put("msg", "0001");
			} else {
				response.put("msg", "0004");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response.toString();
	}

	@RequestMapping(value = "/matchedUser", method = RequestMethod.POST)
	@ResponseBody
	// public String userInfoTrans(
	// @ModelAttribute("user") String uid,
	// HttpServletRequest request)
	public String userInfoTrans(@RequestBody Map<String, String> dataMap, HttpServletRequest request) {
		String ipAddress = request.getHeader("X-Real-IP");
		System.out.println("ip地址:  " + ipAddress);
		System.out.println("uid: " + uid);

		String userId = (String) dataMap.get("uid");
		JSONObject response = new JSONObject(); // 查询数据库中该uid用户是否已经存在
		try {
			// 创建数据库连接库对象
			ConnectionPool connPool = new ConnectionPool(dbDriver, dbURL, dbUser, dbPassWord); // 新建数据库连接库
			connPool.createPool();
			String querySql = "select uid,wx_nick, wx_icon, gender, ip_token,mail, industry, work_for, work_address, job, fields, overhead, wx_id,contact from tbl_mc_user as a join tbl_mc_tags as b on a.wx_nick = b.user_id  where a.ip_token = '"
					+ ipAddress + "' ";
			Connection conn = connPool.getConnection();
			// 从连接库中获取一个可用的连接
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(querySql);
			JSONArray array = new JSONArray();
			JSONObject json = new JSONObject();
			JSONArray authors = new JSONArray();
			JSONObject author = new JSONObject();
			int i = 0;
			while (rs.next()) {
				// 表示用戶已經存在,更新IP地址
				System.out.println("user is exist !");
				json.put("userId", rs.getString(1));
				array.add(json);
				json = new JSONObject();
				author.put("name", rs.getString(2));
				author.put("avatar", rs.getString(3));
				author.put("likeTotal", "100");
				author.put("email", rs.getString(6));
				author.put("tel", rs.getString("contact"));
				author.put("extraInfo", rs.getString("overhead"));
				author.put("companyName", rs.getString("work_for"));
				author.put("city", rs.getString("work_address"));
				author.put("unionId", i);
				authors.add(author);
				i++;
			}
			rs.close();
			stmt.close();
			conn.close();
			// String result = HttpRequestModel.HttpJerseyGetParam(ethereumURL +
			// "labels/info",
			// array.toString().replace("\"", "'"));

			// System.out.println(result);

			// JSONArray arrayResult = JSONArray.fromObject(result);
			response.put("msg", authors.toString());
			response.put("notice", map.get(userId).size());
			JSONArray temp = new JSONArray();
			for (i = 0; i < 3; i++) {
				// temp.add(arrayResult.get(i));
			}

			response.put("authorize", map.get(userId));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return response.toString();

	}

	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public String getUserInfoTrans(@ModelAttribute("user") String uid, HttpServletRequest request)
	// public String getDetails(@RequestParam("uid") String uid,
	// HttpServletRequest request)
	{
		// 鐢ㄦ埛鏁版嵁缁戝畾
		String ipAddress = request.getRemoteAddr();
		System.out.println("用户id: " + uid);
		JSONObject request1 = new JSONObject();
		String msg = "[{name:'阿祖',city:'香港',tel:'13582753123',email:'wuyanzu@163.com',likeTotal:'1282',"
				+ "extraInfo:'香港吴彦祖，魔兽古尔丹',avatar:'/images/wuyanzu.png',unionId:'1'},"
				+ "{name:'多多妈',city:'北京',tel:'13782758882',email:'duoduoma@126.com',likeTotal:'1032',"
				+ "extraInfo:'既来之则安之',avatar:'/images/duoduoma.png',unionId:'2'},"
				+ "{name:'李晓天',city:'上海',tel:'17829385885',email:'lixiaotian@sina.com',likeTotal:'829',"
				+ "extraInfo:'既来之则安之',avatar:'/images/lixiaotian.png',unionId:'3'}]";
		String authorize = msg;
		request1.put("authorize", authorize);
		request1.put("msg", msg);
		request1.put("notice", 3);
		return request1.toString();
	}

	@RequestMapping(value = "/account", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccount(@RequestBody Map<String, Object> params) {

		String uid = (String) params.get("uid");
		String response = HttpRequestModel
				.HttpJerseyGet("http://118.178.136.41:8080/ethereum/account/uuid/" + uid + "/balance");
		System.out.print(response);

		return response.toString();
	}

}
