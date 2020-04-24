package com.dequ.server_final;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WebApp {

	private static WebContext webContext;
	static {
		try {
			// 1����ȡ��������
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 2���ӽ����������ȡ������
			SAXParser parse = factory.newSAXParser();
			// 3�������ĵ�Documentע�ᴦ����
			// 4����д������ Ҫ�Լ���дһ��������
			WebHandler webhandler = new WebHandler();
			parse.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"), webhandler);
			webContext = new WebContext(webhandler.getEntitys(), webhandler.getMappings());
		} catch (Exception e) {
			System.out.println("�������󣡣���");
		}

	}

	/**
	 * ͨ��url��ȡ�ļ�����
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
