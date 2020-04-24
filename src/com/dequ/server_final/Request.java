package com.dequ.server_final;

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

	// 协议信息
	private String requestInfo;
	// 请求方式
	private String method;
	// 请求url
	private String url;
	// 请求参数
	private String queryStr;
	// 存储参数
	private Map<String, List<String>> parameterMap;

	private final String BLANK = " ";
	private final String CRLF = "\r\n";

	public Request(Socket client) throws IOException {
		this(client.getInputStream());
	}

	public Request(InputStream is) {

		parameterMap = new HashMap<String, List<String>>();

		int len;
		byte[] datas = new byte[1024 * 1024]; // 创建容器
		try {
			len = is.read(datas);
			this.requestInfo = new String(datas, 0, len); // 变成字符串打印
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		parseRequestInfo();
	}

	private void parseRequestInfo() {
		System.out.println("分解开始");

		// 1、获取请求方式
		this.method = this.requestInfo.substring(0, this.requestInfo.indexOf("/")).toLowerCase().trim();
		System.out.println("method:" + this.method);
		// 2、获取i请求url:第一个/到HTTP/----"
		// "可能包含的请求参数？前面的为url
		int startidx = this.requestInfo.indexOf("/") + 1;
		int endidx = this.requestInfo.indexOf("HTTP/");
		this.url = this.requestInfo.substring(startidx, endidx).trim();

		// 获取问号的位置
		int queryidx = this.url.indexOf("?");
		if (queryidx >= 0) {
			String[] urlArray = this.url.split("\\?");
			this.url = urlArray[0].trim();
			queryStr = urlArray[1].trim();
		}
		System.out.println("url:" + this.url);

		// 3、获取i请求参数：如果Get已经获取，如果是post可能在请求体中"
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

		// 转成Map a1=1&a2=2&a3=3
		convertMap();
		print();
		System.out.println("请求信息处理没有错误");
	}

	// 处理请求参数为Map
	private void convertMap() {
		// 分割字符串
		String[] keyValues = this.queryStr.split("&");
		for (String query : keyValues) {
			// 再次分割字符串
			String[] kv = query.split("=");
			kv = Arrays.copyOf(kv, 2);
			String key = kv[0];
			String value = kv[1] == null ? null : decode(kv[1], "GBK");
			if (!parameterMap.containsKey(kv[0])) {
				parameterMap.put(key, new ArrayList<String>());
			}
			parameterMap.get(key).add(value);
		}
	}

	// 处理中文
	private String decode(String value, String enc) {
		try {
			return java.net.URLDecoder.decode(value, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过name获取对应的多个值
	 * 
	 * @param key
	 * @return
	 */
	public String[] getParameterValues(String key) {
		List<String> values = this.parameterMap.get(key);
		if (null == values || values.size() < 1) {
			return null;
		}
		return values.toArray(new String[0]);
	}

	/**
	 * 通过name获取对应的多个值
	 * 
	 * @param key
	 * @return
	 */
	public void print() {
		if (!this.parameterMap.isEmpty()) {
			for (String key : this.parameterMap.keySet()) {
				System.out.println(key + "-->" + getParameter(key));
			}
		}
	}

	/**
	 * 通过name获取对应的一个值
	 * 
	 * @param key
	 * @return
	 */
	public String getParameter(String key) {
		List<String> values = this.parameterMap.get(key);
		if (null == values || values.size() < 1) {
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
