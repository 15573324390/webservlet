package com.dequ.server_final;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WebApp {

	private static WebContext webContext;
	static {
		try {
			// 1、获取解析工厂
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 2、从解析工厂里获取解析器
			SAXParser parse = factory.newSAXParser();
			// 3、加载文档Document注册处理器
			// 4、编写处理器 要自己编写一个处理器
			WebHandler webhandler = new WebHandler();
			parse.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"), webhandler);
			webContext = new WebContext(webhandler.getEntitys(), webhandler.getMappings());
		} catch (Exception e) {
			System.out.println("解析错误！！！");
		}

	}

	/**
	 * 通过url获取文件配置
	 * 
	 * @param url
	 * @return
	 */
	public static Servlet getServletFromUrl(String url) {

		try {
			if (url != null) {
				String className = webContext.getclz("/" + url);
				if (!className.isEmpty()) {
					Class clz = Class.forName(className);
					Servlet servlet = (Servlet) clz.getConstructor().newInstance();
					return servlet;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
