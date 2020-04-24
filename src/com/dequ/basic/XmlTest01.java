package com.dequ.basic;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlTest01 {

	public static void main(String[] args) throws Exception, SAXException {
		// SAX解析
		// 1、获取解析工厂
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 2、从解析工厂获取解析器
		SAXParser parse = factory.newSAXParser();
		// 3、加载文档Document注册处理器
		// 4、编写处理器 要自己编写一个处理器
		PersonHandler handler = new PersonHandler();
		parse.parse(Thread.currentThread() // 当前线程
				.getContextClassLoader() // 类加载器
				.getResourceAsStream("com/dequ/basic/p.xml")// 资源文件
				, handler);

		// 从文档获取到的数据
		List<Person> person = handler.getPersons();
		for (Person p : person) {

			System.out.println(p.getName()+"-->"+p.getSayings());
		}
	}

}

class PersonHandler extends DefaultHandler {
	// 把内容放到容器里面
	private List<Person> persons;
	private Person person;
	private String tag;

	@Override
	public void startDocument() throws SAXException {
		persons = new ArrayList<Person>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		System.out.println(qName + "解析元素开始");

		tag = qName;

		if (tag.equals("person")) {
			person = new Person();
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

		String contents = new String(ch, start, length).trim();

		if (null != tag && contents.length() > 0) {
			if (tag.equals("name")) {
				person.setName(contents);
			} else if (tag.equals("Sayings")) {
				person.setSayings(contents);
			}
		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		System.out.println(qName + "解析元素结束");
		if (qName.equals("person")) {
			persons.add(person);
		}
	}

	@Override
	public void endDocument() throws SAXException {

	}

	public List<Person> getPersons() {
		return persons;
	}

}
