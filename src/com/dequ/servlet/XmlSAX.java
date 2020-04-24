package com.dequ.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * ������
 * @author ������
 *
 */
public class XmlSAX {

	public static void main(String[] args) throws Throwable, SAXException {
		// 1����ȡ��������
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 2���ӽ����������ȡ������
		SAXParser parse = factory.newSAXParser();
		// 3�������ĵ�Documentע�ᴦ����
		// 4����д������ Ҫ�Լ���дһ��������
		PHandler handler = new PHandler();
		parse.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/dequ/servlet/web.xml"),
				handler);
		
		//��xml��ȡ����Ȼ����
        WebContext wc = new WebContext(handler.getEntitys(),handler.getMappings());
 
        Class clz = Class.forName(wc.getclz("/reg"));
        LoginServlet loginServlet = (LoginServlet)clz.getConstructor().newInstance();
        
  
	}
}

class PHandler extends DefaultHandler {

	List<Entity> entitys;
	Entity entity;
	List<Mapping> mappings;
	Mapping mapping;
	private String tag;
	private String tag2;

	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if(qName.equals("web-app")) {
			entitys = new ArrayList<Entity>();
			mappings = new ArrayList<Mapping>();
		}
	    
		if (qName != null) { 
			if (qName.equals("servlet")) {
                entity = new Entity();
				tag = qName;
			} else if (qName.equals("servlet-mapping")) {
                mapping = new Mapping();
				tag = qName;
			}

			if (qName.equals("servlet-name") || qName.equals("servlet-class") || qName.equals("url-pattern")) {
				tag2 = qName;
			}

		}

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		String contents = new String(ch, start, length).trim();
		if (contents.length() > 0 && tag != null && tag2!=null) {
			if ("servlet".equals(tag)) {
				if ("servlet-name".equals(tag2)) {
					entity.setName(contents);
				} else if ("servlet-class".equals(tag2)) {
					entity.setClz(contents);
				}

			}
			if ("servlet-mapping".equals(tag)) {
				if ("servlet-name".equals(tag2)) {
					mapping.setName(contents);
				} else if ("url-pattern".equals(tag2)) {
					mapping.addPattern(contents);
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName != null) {
			if (qName.equals("servlet")) {
				entitys.add(entity);
			} else if (qName.equals("servlet-mapping")) {
				mappings.add(mapping);
			}
		}
	}

	@Override
	public void endDocument() throws SAXException {
	}

	public List<Entity> getEntitys() {
		return entitys;
	}

	public List<Mapping> getMappings() {
		return mappings;
	}

}