package com.dequ.basic;

import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author ������
 *
 *���䣺��java���еĸ��ֽṹ�����ԡ���������������������ӳ���һ������java����
 *1����ȡclass����
 *���ַ�ʽ
 *2����̬�Ĵ�������
 *Iphone iphone=(Iphone)clz1.getConstructor().newInstance()
 *
 *
 */
public class Reflection {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception, SecurityException {
	   //���ַ�ʽ
	   //1������.getClass();   �Ѿ��õ�����
	   Class clz1 = new Iphone().getClass();
	
	
	   //2����.class()  �Ѿ��õ�ģ��
	   Class clz2 = Iphone.class;

	   //3����λ��͵     Class.forName(����.����)
	   Class clz3 = Class.forName("com.dequ.basic.Iphone");
	   
	   //��������
	 /* JDK8��ǰ��д��
	  *  Iphone iphone=(Iphone)clz1.newInstance();
	   System.out.print(iphone);
	  */
	   //�ù�����
	   Iphone iphone=(Iphone)clz1.getConstructor().newInstance();
	   System.out.print(iphone);
	}
	
}

class Iphone{
	public Iphone() {
		
	}
}