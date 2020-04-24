package com.dequ.servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {

	private BufferedWriter bw ;
	//正文
	private StringBuilder content;
	//协议头信息
	private StringBuilder headInfo;
	//正文的字节数
	private int len = 0;
	
	private final String BLANK = " " ;
	private final String CRLF = "\r\n";
	
	
	private Response() {
	     content = new StringBuilder();	
	     headInfo = new StringBuilder();	
	     len = 0 ;
	}
	public Response(Socket client) {
		this();
		try {
			bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			headInfo=null;
		}
		
	}
	public Response(OutputStream os) {
		this();
		bw = new BufferedWriter(new OutputStreamWriter(os));
	}
	
	//动态添加内容
	public Response print(String info) {
		content.append(info);
		len +=info.getBytes().length;
		return this;
	}
	//动态添加内容
	public Response println(String info) {
		content.append(info).append(CRLF);
		len +=(info+CRLF).getBytes().length;
		return this;
	}
	
	//推送响应信息
	public void pushToBrowser(int code) throws IOException {
		if(null==headInfo) {
			code=500;
		}
		createHeadInfo(code);
		bw.append(headInfo);
		//处理中文并加入正文
		//bw.append(java.net.URLDecoder.decode(content.toString(),"gbk"));
		bw.append(content);
		System.out.println(content);
		bw.flush();
	}
	
	//构建头信息
	private void createHeadInfo(int code) {
		//1、协议头信息
		headInfo.append("HTTP/1.1").append(BLANK);
		headInfo.append(200).append(BLANK);
		headInfo.append("OK").append(CRLF);
		switch(code) {
		case 200:
			headInfo.append("OK").append(CRLF);
			break;
		case 404:
			headInfo.append("NOT FOUND").append(CRLF);
			break;
		case 500:
			headInfo.append("SERVER ERROR").append(CRLF);
			break;
		}
		//2、响应头信息
		headInfo.append("HTTP/1.1").append(BLANK);
		headInfo.append(200).append(BLANK);
		headInfo.append("OK").append(CRLF);
		headInfo.append("Date:").append(new Date()).append(CRLF);
		headInfo.append("Server:").append("shsxt Server/0.0.1").append(CRLF);
		headInfo.append("Content-type:text/html;charset=utf-8").append(CRLF);
		headInfo.append("Content-length:").append(len).append(CRLF);
		headInfo.append(CRLF);
		
	}
}
