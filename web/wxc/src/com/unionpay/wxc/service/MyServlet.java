package com.unionpay.wxc.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet{  
	  
    private static final long serialVersionUID = 1L;  
    private ReadResultThread readResult;  
    private ReadQueryThread readQuery;
      
    public MyServlet(){  
    }  
  
    public void init(){  
        
        if (readResult == null && readQuery == null) {  
        	readQuery = new ReadQueryThread(); 
        	readResult = new ReadResultThread();
        	readQuery.start(); // servlet 上下文初始化时启动 socket
        	readResult.start();
        }  
    }  
  
    public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)  
        throws ServletException, IOException{  
    }  
  
    public void destory(){  
        if (readResult != null && readResult.isInterrupted() && readQuery != null && readQuery.isInterrupted()) {  
        	readResult.interrupt();  
        	readQuery.interrupt();
        }  
    }  
}  
