package com.dequ.server_final;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {

	private BufferedWriter bw ;
	//����
	private StringBuilder content;
	//Э��ͷ��Ϣ
	private StringBuilder headInfo;
	//���ĵ��ֽ���
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
	
	//��̬��������
	public Response print(String info) {
		content.append(info);
		len +=info.getBytes().length;
		return this;
	}
	//��̬��������
	public Response println(String info) {
		content.append(info).append(CRLF);
		len +=(info+CRLF).getBytes().length;
		return this;
	}
	
	//������Ӧ��Ϣ
	public void pushToBrowser(int code) throws IOException {
		if(null==headInfo) {
			code=500;
		}
		createHeadInfo(code);
		bw.append(headInfo);
		//�������Ĳ���������
		bw.append(java.net.URLDecoder.decode(content.toString(),"GBK"));
		//bw.append(content);
		bw.flush();
	}
	
	//����ͷ��Ϣ
	private void createHeadInfo(int code) {
		//1��Э��ͷ��Ϣ
		headInfo.append("HTTP/1.1").append(BLANK);
		headInfo.append(code).append(BLANK);
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
		headInfo.append("Date:").append(new Date()).append(CRLF);
		headInfo.append("Server:").append("shsxt Server/0.0.1").append(CRLF);
		headInfo.append("Content-type:text/html;charset=GBK").append(CRLF);
		headInfo.append("Content-length:").append(len).append(CRLF);
		headInfo.append(CRLF);
		
	}
}