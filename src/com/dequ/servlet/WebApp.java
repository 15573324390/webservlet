package com.dequ.servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
/**
 * 处理器
 * @author 赖金荣
 *
 */
public class WebApp {

	private static WebContext WebContext ; 
	static { 
		try {
			// 1、获取解析工厂
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 2、从解析工厂里获取解析器
			SAXParser parse = factory.newSAXParser();
			// 3、加载文档Document注册处理器
			// 4、编写处理器 要自己编写一个处理器
			WebHandler webhandler = new WebHandler();
			parse.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("comdequ/servlet/web.xml"),
					webhandler);
			WebContext = new WebContext(webhandler.getEntitys(),webhandler.getMappings());
		}catch(Exception e) {
			System.out.println("解析异常！！！");
		}
	
	}
	/**
	 * 通过URL获取配置文件对应servlet
	 * @param url
	 * @return
	 */
	public static Servlet getServletFromUrl(String url) {
		       
        Class clz;
		try {
			clz = Class.forName(WebContext.getclz("/"+url));
			Servlet Servlet = (Servlet)clz.getConstructor().newInstance();
			return Servlet;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
