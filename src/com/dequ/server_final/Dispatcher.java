package com.dequ.server_final;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
/**
 * �ַ���
 * @author ������
 *
 */
public class Dispatcher implements Runnable {

	private Socket client;
	Request request;
	Response response;


	public Dispatcher(Socket client) {
		this.client = client;
		try {
			System.out.println("���ˣ���");
			request = new Request(client);
			response = new Response(client);
		} catch (IOException e) {
			this.release();
		}
	}



	@Override
	public void run() {

		try {
			if(null==request.getUrl() || request.getUrl().equals("")) {
				byte[] datas = new byte[1024*1024];
				InputStream is =Thread.currentThread().getContextClassLoader().getResourceAsStream("shouye.html");
				response.print(new String(datas,0,is.read(datas)));
				response.pushToBrowser(200);
				is.close();
				this.release();
				return ;
			}
			
			Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
			if (null != servlet) {
				servlet.service(request, response);
				response.pushToBrowser(200);
			} else {
				byte[] datas = new byte[1024*1024];
				//����
				InputStream is =Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
				response.print(new String(datas,0,is.read(datas)));
				response.pushToBrowser(404);
				is.close();
				return;
			}
		} catch (IOException e) {
			try {
				response.println("505505505505505505505505");
				System.out.println("���е�505");
				response.pushToBrowser(505);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		this.release();
       System.out.println("������һ��");
	}
	private void release() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
