package com.dequ.server_final;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
/**
 * 分发器
 * @author 赖金荣
 *
 */
public class Dispatcher implements Runnable {

	private Socket client;
	Request request;
	Response response;


	public Dispatcher(Socket client) {
		this.client = client;
		try {
			System.out.println("来了！！");
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
				//错误
				InputStream is =Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
				response.print(new String(datas,0,is.read(datas)));
				response.pushToBrowser(404);
				is.close();
				return;
			}
		} catch (IOException e) {
			try {
				response.println("505505505505505505505505");
				System.out.println("运行到505");
				response.pushToBrowser(505);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		this.release();
       System.out.println("运行完一个");
	}
	private void release() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
