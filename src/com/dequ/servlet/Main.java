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
 * Socket������Socket���ӣ���ȡ����Э��
 * 
 * ������ӦЭ��
 * 
 * @author ������
 *
 */
public class Main {

	private ServerSocket socketServer;

	public static void main(String[] args) {
		    System.out.println("��������");
			Main server = new Main();
			server.start();
		
	}

	public void start() {
		try {
			socketServer = new ServerSocket(8888);
			receive();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("����������ʧ�ܣ�������");
		}
	}

	public void receive() {
		try {
			Socket client = socketServer.accept();
			System.out.println("һ���ͻ��˽���������");
//			InputStream is = client.getInputStream(); // ��ȡ������
			
			Request request = new Request(client);
//			byte[] datas = new byte[1024 * 1024]; // ��������
//			int len = is.read(datas); // ��ȡ
//	        System.out.println(len);
//			String requestInfo = new String(datas, 0, len); // ����ַ�����ӡ
//			System.out.println(requestInfo);
			
			
		/*	//������ӦЭ��

			//3������
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
			
			//1����Ӧ�У�HTTP/1.1 200 OK
			//2����Ӧͷ�����һ�д��ڿ��У���
			
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

			//д�����ͻ���
			BufferedWriter bw =new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));//��ȡ���������
			bw.write(responseInfo.toString());
			bw.flush();
		*/
			
			
			Response response = new Response(client);
//			Servlet servlet =null ;
//			//����
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
//			response.print("shsxt ����������server!!!!!!"+request.getUrl());
//			response.print("shsxt server!!!!!!"+request.getParameterValue("uname"));
//			response.print("</body>");
//			response.print("</html>");
			//��ע��״̬��
			
			Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
			if(null!=servlet) {
				servlet.service(request, response);
			}else {
				response.pushToBrowser(404);
			}
			
			response.pushToBrowser(200); 
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�ͻ��˴���");
		}
	}

	public void stop() {

	}
}
