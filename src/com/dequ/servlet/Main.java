package com.dequ.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Socket：建立Socket连接，获取请求协议
 * 
 * 返回响应协议
 * 
 * @author 赖金荣
 *
 */
public class Main {

	private ServerSocket socketServer;

	public static void main(String[] args) {
		    System.out.println("启动服务！");
			Main server = new Main();
			server.start();
		
	}

	public void start() {
		try {
			socketServer = new ServerSocket(8888);
			receive();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("服务器启动失败！！！！");
		}
	}

	public void receive() {
		try {
			Socket client = socketServer.accept();
			System.out.println("一个客户端接受了连接");
//			InputStream is = client.getInputStream(); // 获取输入流
			
			Request request = new Request(client);
//			byte[] datas = new byte[1024 * 1024]; // 创建容器
//			int len = is.read(datas); // 读取
//	        System.out.println(len);
//			String requestInfo = new String(datas, 0, len); // 变成字符串打印
//			System.out.println(requestInfo);
			
			
		/*	//返回响应协议

			//3、正文
			StringBuilder content = new StringBuilder();
			content.append("<html>");
			content.append("<head>");
			content.append("<title>");
			content.append("Success");
			content.append("</title>");
			content.append("</head>");
			content.append("<body>");
			content.append("shsxt server.");
			content.append("</body>");
			content.append("</html>");
			int size =content.toString().getBytes().length;
			
			//1、响应行：HTTP/1.1 200 OK
			//2、响应头（最后一行存在空行）：
			
//			 //* Date:Mon,31 Dec 209904:25:57GMT
//			 * Server:shsxt Server/0.0.1;charset=GBK
//			 * Content-type:text/html
//			 * Content-length:23535
			 *
			StringBuilder responseInfo = new StringBuilder();
			String blank = " " ;
			String CRLF = "\r\n";
			responseInfo.append("HTTP/1.1").append(blank);
			responseInfo.append(200).append(blank);
			responseInfo.append("OK").append(CRLF);
			responseInfo.append("Date:").append(new Date()).append(CRLF);
			responseInfo.append("Server:").append("shsxt Server/0.0.1;charset=GBK").append(CRLF);
			responseInfo.append("Content-type:text/html").append(CRLF);
			responseInfo.append("Content-length:").append(size).append(CRLF);
			responseInfo.append(CRLF);
			responseInfo.append(content.toString());

			//写出到客户端
			BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));//获取输出流对象
			bw.write(responseInfo.toString());
			bw.flush();
		*/
			
			
			Response response = new Response(client);
//			Servlet servlet =null ;
//			//正文
//			if(request.getUrl().equals("login")) {
//				servlet = new LoginServlet();
//			}else if(request.getUrl().equals("reg")) {
//				servlet = new LoginServlet();
//			}else {
//				
//			}
//			servlet.service(request, response);
			
			
//			response.print("<html>");
//			response.print("<head>");
//			response.print("<title>");
//			response.print("Success");
//			response.print("</title>");
//			response.print("</head>");
//			response.print("<body>");
//			response.print("shsxt 我我我我怕server!!!!!!"+request.getUrl());
//			response.print("shsxt server!!!!!!"+request.getParameterValue("uname"));
//			response.print("</body>");
//			response.print("</html>");
			//关注了状态码
			
			Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
			if(null!=servlet) {
				servlet.service(request, response);
			}else {
				response.pushToBrowser(404);
			}
			
			response.pushToBrowser(200); 
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("客户端错误！");
		}
	}

	public void stop() {

	}
}
