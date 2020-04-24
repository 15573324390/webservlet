package com.dequ.servlet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
/**
 * ������
 * @author ������
 *
 */
public class WebApp {

	private static WebContext WebContext ; 
	static { 
		try {
			// 1����ȡ��������
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 2���ӽ����������ȡ������
			SAXParser parse = factory.newSAXParser();
			// 3�������ĵ�Documentע�ᴦ����
			// 4����д������ Ҫ�Լ���дһ��������
			WebHandler webhandler = new WebHandler();
			parse.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("comdequ/servlet/web.xml"),
					webhandler);
			WebContext = new WebContext(webhandler.getEntitys(),webhandler.getMappings());
		}catch(Exception e) {
			System.out.println("�����쳣������");
		}
	
	}
	/**
	 * ͨ��URL��ȡ�����ļ���Ӧservlet
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
