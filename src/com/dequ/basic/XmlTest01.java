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
		// SAX����
		// 1����ȡ��������
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 2���ӽ���������ȡ������
		SAXParser parse = factory.newSAXParser();
		// 3�������ĵ�Documentע�ᴦ����
		// 4����д������ Ҫ�Լ���дһ��������
		PersonHandler handler = new PersonHandler();
		parse.parse(Thread.currentThread() // ��ǰ�߳�
				.getContextClassLoader() // �������
				.getResourceAsStream("com/dequ/basic/p.xml")// ��Դ�ļ�
				, handler);

		// ���ĵ���ȡ��������
		List<Person> person = handler.getPersons();
		for (Person p : person) {

			System.out.println(p.getName()+"-->"+p.getSayings());
		}
	}

}

class PersonHandler extends DefaultHandler {
	// �����ݷŵ���������
	private List<Person> persons;
	private Person person;
	private String tag;

	@Override
	public void startDocument() throws SAXException {
		persons = new ArrayList<Person>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		System.out.println(qName + "����Ԫ�ؿ�ʼ");

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

		System.out.println(qName + "����Ԫ�ؽ���");
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
