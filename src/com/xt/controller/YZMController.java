package com.xt.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/send")
public class YZMController extends HttpServlet {
	private static String codelist[] = {"765213","854672",} ;
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String str="0123456789";
		StringBuilder sb=new StringBuilder(6);
		for(int i=0;i<6;i++)
		{
		char ch=str.charAt(new Random().nextInt(str.length()));
		sb.append(ch);
		}
		
		
		String phoneNumber = req.getParameter("phonenumber");
		System.out.println(sb.toString());
		String appkey="7d72f636e0a3d1f81bca1b64360621a5";
		String text = "新通教育验证码是123456";
		String s = "https://sms.yunpian.com/v2/sms/single_send.json";
		String para = "apikey=7d72f636e0a3d1f81bca1b64360621a5&mobile="+phoneNumber+"&text=【云片网】您的验证码是"+sb.toString();
		//send message
		String reString =  YZMController.doPost("https://sms.yunpian.com/v2/sms/single_send.json",para);
		resp.getWriter().println(sb.toString());
		 
	} 
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doGet(req, resp);
	}	

	  public static String doPost(String httpUrl, String param) {

	        HttpURLConnection connection = null;
	        InputStream is = null;
	        OutputStream os = null;
	        BufferedReader br = null;
	        String result = null;
	        try {
	            URL url = new URL(httpUrl);
	            // 通过远程url连接对象打开连接
	            connection = (HttpURLConnection) url.openConnection();
	            // 设置连接请求方式
	            connection.setRequestMethod("POST");
	            // 设置连接主机服务器超时时间：15000毫秒
	            connection.setConnectTimeout(15000);
	            // 设置读取主机服务器返回数据超时时间：60000毫秒
	            connection.setReadTimeout(60000);

	            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
	            connection.setDoOutput(true);
	            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
	            connection.setDoInput(true);
	            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
	            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
	            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
	            // 通过连接对象获取一个输出流
	            os = connection.getOutputStream();
	            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
	            os.write(param.getBytes());
	            // 通过连接对象获取一个输入流，向远程读取
	            if (connection.getResponseCode() == 200) {

	                is = connection.getInputStream();
	                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
	                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

	                StringBuffer sbf = new StringBuffer();
	                String temp = null;
	                // 循环遍历一行一行读取数据
	                while ((temp = br.readLine()) != null) {
	                    sbf.append(temp);
	                    sbf.append("\r\n");
	                }
	                result = sbf.toString();
	            }
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            // 关闭资源
	            if (null != br) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (null != os) {
	                try {
	                    os.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (null != is) {
	                try {
	                    is.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            // 断开与远程地址url的连接
	            connection.disconnect();
	        }
	        return result;
	    }
}
