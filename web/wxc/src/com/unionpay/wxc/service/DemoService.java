package com.unionpay.wxc.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unionpay.wxc.server.HttpRequestModel;

import net.sf.json.JSONObject;

/**
 * 
 * @author xiaoyingdong
 *
 */

@Controller
@RequestMapping("/Demo")
public class DemoService {
	@RequestMapping(value = "/query")
	@ResponseBody
	public String query() {
		return "success";
	}
}
