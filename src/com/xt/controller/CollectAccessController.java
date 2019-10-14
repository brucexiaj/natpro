package com.xt.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



@WebServlet("/collect")
public class CollectAccessController extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		doPost(req,resp);
	} 
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//System.out.println("==================0=================");
		//连接数据库	
		List<Map> datas = new ArrayList<Map>();
	    try {
	    	Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动            
	    	String jdbc="jdbc:mysql://127.0.0.1:3306/natpro?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
	        Connection conn=DriverManager.getConnection(jdbc, DBAccess.UNAME,DBAccess.PWD);//链接到数据库	            
	        Statement state=conn.createStatement();   //容器
	        
	       
	        //统计30天左右
	        for (int i = 0; i >= -30; i--) {
	        	//调表用户
	        	 String sql1="SELECT count(*) AS rest FROM userinfo WHERE phonenumber<>'-1' and TO_DAYS( NOW( ) ) - TO_DAYS( createtime) = "+(-i);   //SQL语句
	        	 ResultSet rs1 = state.executeQuery(sql1);//查询多个表
	        	 Integer rest1 = 0;
	        	 while(rs1.next()){
	        		 rest1 = rs1.getInt("rest");
	        	 }
	        	 //未填表
	        	 String sql2="SELECT count(*) AS rest FROM userinfo WHERE phonenumber='-1' and TO_DAYS( NOW( ) ) - TO_DAYS( createtime) = "+(-i);   //SQL语句
	        	 //System.out.println(sql1);
	        	 //System.out.println(sql2);
	        	 ResultSet rs2 = state.executeQuery(sql2);//查询多个表
	        	 Integer rest2 = 0;
	        	 while(rs2.next()){
	        		 rest2 = rs2.getInt("rest");
	        	 }
	        	 //获取时间
	        	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        	 Date today = new Date();
	        	 String endDate = sdf.format(today);//当前日期

	        	 //获取三十天前日期
	        	 Calendar theCa = Calendar.getInstance();
	        	 theCa.setTime(today);
	        	 theCa.add(theCa.DATE, i);//最后一个数字30可改，30天的意思
	        	 Date start = theCa.getTime();
	        	 String startDate = sdf.format(start);//三十天之前日期
	        	 
	        	 //存下来
	        	 Map<String,String> ds = new HashMap<String,String>();
		 			ds.put("time", startDate);
		 			ds.put("fangwen", rest2.toString());
		 			ds.put("tianbiao", rest1.toString());
		 			
		 			datas.add(ds); 
			}
	       
	     
	        conn.close();//关闭通道
	        state.close();
	        //System.out.println("==================1=================");
	        //写入excel
		    //WriteExcelController.writeExcel(datas,5,"user.xlsx",resp);
	        
	   
	        String[] title={"日期","访问量","填表用户数量"};
	        //创建HSSFWorkbook对象(excel的文档对象)  
	        HSSFWorkbook wb = new HSSFWorkbook();
	        // 建立新的sheet对象（excel的表单）
	        HSSFSheet sheet = wb.createSheet("sheet1");
		    //创建第一行
		    HSSFRow row=sheet.createRow(0);
		    HSSFCell cell=null;
		    //插入第一行数据的表头
		    for(int i=0;i<title.length;i++){
		        cell=row.createCell(i);
		        cell.setCellValue(title[i]);
		    }
		    //写入数据
		    for (int i=0;i<datas.size();i++){
		    	 Map dataMap = datas.get(i);
               String time = dataMap.get("time").toString();
               String fangwen = dataMap.get("fangwen").toString();
               String tianbiao = dataMap.get("tianbiao").toString();
              
		    	 
		    	 
		        HSSFRow nrow=sheet.createRow(i+1);
		        
		        HSSFCell ncell=nrow.createCell(0);
		        ncell.setCellValue(time);
		        ncell=nrow.createCell(1);
		        ncell.setCellValue(fangwen);
		        ncell=nrow.createCell(2);
		        ncell.setCellValue(tianbiao);
		        
		       
		    }
	
		    //System.out.println("==================2=================");
		     OutputStream output;
		     output = resp.getOutputStream();
		     //清空缓存
		     resp.reset();
		     //定义浏览器响应表头，顺带定义下载名，比如students(中文名需要转义)
		     resp.setHeader("Content-disposition", "attachment;filename="+new String("用户访问统计".getBytes(),"iso-8859-1")+".xls");
		     //定义下载的类型，标明是excel文件
		     resp.setContentType("application/vnd.ms-excel");
		     //这时候把创建好的excel写入到输出流
		     wb.write(output);
		     //养成好习惯，出门记得随手关门
		     output.close();

		     //System.out.println("==================3=================");
		     
//		     
//		  //要下载的这个文件的类型-----客户端通过文件的MIME类型去区分类型
//		    resp.setContentType("application/vnd.ms-excel");
//			//告诉客户端该文件不是直接解析 而是以附件形式打开(下载)----filename="+filename 客户端默认对名字进行解码
//		     resp.setHeader("Content-disposition", "attachment;filename="+new String("答题用户信息".getBytes(),"iso-8859-1")+".xls");
//
//			//获取文件的绝对路径   记得更改自己项目下的路径
//			String path = this.getServletContext().getRealPath("new.xls");
//			//获得该文件的输入流
//			InputStream in = new FileInputStream(path);
//			//获得输出流---通过response获得的输出流 用于向客户端写内容
//			ServletOutputStream out = resp.getOutputStream();
//			//文件拷贝的模板代码
//			int len = 0;
//			byte[] buffer = new byte[1024];
//			while((len=in.read(buffer))>0){
//				out.write(buffer, 0, len);
//			}
//
//			in.close();
		     
	

	        	
	        	
	        	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }	
	    
	    
	}		
	
	
	
	




}
