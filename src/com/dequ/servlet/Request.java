package com.dequ.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {

	// Э����Ϣ
	private String requestInfo;
	// ����ʽ
	private String method;
	// ����url
	private String url;
	// �������
	private String queryStr;
	//�洢����
	private Map<String,List<String>> parameterMap;

	private final String BLANK = " ";
	private final String CRLF = "\r\n";

	public Request(Socket client) throws IOException {
		this(client.getInputStream());
	}

	public Request(InputStream is) {
		
		parameterMap = new HashMap<String,List<String>>();
		
		int len;
		byte[] datas = new byte[1024 * 1024]; // ��������
		try {
			len = is.read(datas);
			this.requestInfo = new String(datas, 0, len); // ����ַ�����ӡ
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		parseRequestInfo();
	}

	private void parseRequestInfo() {
		System.out.println("�ֽ⿪ʼ");

		/*
		 * POST /qwer HTTP/1.1 Host: localhost:8888 Connection: keep-alive
		 * Content-Length: 0 Sec-Fetch-Mode: cors Origin:
		 * chrome-extension://eejfoncpjfgmeleakejdcanedmefagga User-Agent: Mozilla/5.0
		 * (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)
		 * Chrome/77.0.3865.90 Safari/537.36 Content-Type: text/plain;charset=UTF-8
		 * Accept: Sec-Fetch-Site: cross-site Accept-Encoding: gzip, deflate, br
		 * Accept-Language: zh-CN,zh;q=0.9
		 */

		// 1����ȡ����ʽ
		this.method = this.requestInfo.substring(0, this.requestInfo.indexOf("/")).toLowerCase().trim();
		System.out.println("method:" + this.method);
		// 2����ȡi����url:��һ��/��HTTP/----"
		// "���ܰ��������������ǰ���Ϊurl
		int startidx = this.requestInfo.indexOf("/") + 1;
		int endidx = this.requestInfo.indexOf("HTTP/");
		this.url = this.requestInfo.substring(startidx, endidx);

		// ��ȡ�ʺŵ�λ��
		int queryidx = this.url.indexOf("?");
		if (queryidx >= 0) {
			String[] urlArray = this.url.split("\\?");
			this.url = urlArray[0].trim();
			queryStr = urlArray[1].trim();
		}
		System.out.println("url:" + this.url);

		// 3����ȡi������������Get�Ѿ���ȡ�������post��������������"
		if ("post".equals(this.method)) {

			String qStr = this.requestInfo.substring(this.requestInfo.lastIndexOf(CRLF)).trim();
			System.out.println("qStr:" + qStr);
			if (qStr.length() > 0) {
				if (null == queryStr) {
					queryStr = qStr;
				} else {
					queryStr += "&" + qStr;
				}
			}
		}
		queryStr = null == queryStr ? "" : queryStr;
		System.out.println("queryStr:" + queryStr);
		System.out.println(requestInfo);
	
	    //ת��Map a1=1&a2=2&a3=3
		convertMap();
		print();
	}
	
	//�����������ΪMap
	private void convertMap() {
		//�ָ��ַ���
		String[] keyValues = this.queryStr.split("&");
		for(String query:keyValues) {
			//�ٴηָ��ַ���
			String[] kv = query.split("=");
			kv = Arrays.copyOf(kv,2);
			String key = kv[0];
			String value =kv[1] ==null?null:decode(kv[1],"GBK");
			if(!parameterMap.containsKey(kv[0])) {
				parameterMap.put(key, new ArrayList<String>());
			}
			parameterMap.get(key).add(value);
		}
	}
	
	
	//��������
	private String decode(String value,String enc) {
		try {
			return java.net.URLDecoder.decode(value,enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ͨ��name��ȡ��Ӧ�Ķ��ֵ
	 * @param key
	 * @return
	 */
	public String[] getParameterValues(String key) {
		List<String> values = this.parameterMap.get(key);
		if(null == values || values.size()<1) {
			return null;
		}
		return values.toArray(new String[0]);
	}
	
	/**
	 * ͨ��name��ȡ��Ӧ�Ķ��ֵ
	 * @param key
	 * @return
	 */
	public void print() {
		for(String key : this.parameterMap.keySet()) {
			System.out.println(key+"-->"+getParameterValue(key));
		}
	}
	
	/**
	 * ͨ��name��ȡ��Ӧ��һ��ֵ
	 * @param key
	 * @return
	 */
	public String getParameterValue(String key) {
		List<String> values = this.parameterMap.get(key);
		if(null == values || values.size()<1) {
			return null;
		}
		return values.get(0);
	}

	public String getRequestInfo() {
		return requestInfo;
	}

	public void setRequestInfo(String requestInfo) {
		this.requestInfo = requestInfo;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	
	
}
