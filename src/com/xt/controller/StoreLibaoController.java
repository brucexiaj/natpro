package com.xt.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/lingqu")
public class StoreLibaoController extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		doPost(req,resp);
	} 
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String phoneNumber = req.getParameter("phonenumber");
		String libao = req.getParameter("libao");
		if (phoneNumber==null || phoneNumber == "" ) {
			return;
		}
		if (libao==null || libao == "" ) {
			return;
		}
		
		//连接数据库
		 
	    try {
	    	//String name = "root";
	    	//String pwd = "yinbin99";
	    	Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动            
	        String jdbc="jdbc:mysql://127.0.0.1:3306/natpro?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
	        Connection conn=DriverManager.getConnection(jdbc, DBAccess.UNAME,DBAccess.PWD);//链接到数据库	            
	        Statement state=conn.createStatement();   //容器
	        String sql="update userinfo set libao='" + libao +"' where phonenumber='" + phoneNumber + "'";   //SQL语句
	        state.executeUpdate(sql);         //将sql语句上传至数据库执行            
	        conn.close();//关闭通道
	        state.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }		
	}		
}
