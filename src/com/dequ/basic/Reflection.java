package com.dequ.basic;

import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author 赖金荣
 *
 *反射：把java类中的各种结构（属性、方法、构造器、类名）映射成一个个的java对象。
 *1、获取class对象
 *三种方式
 *2、动态的创建对象
 *Iphone iphone=(Iphone)clz1.getConstructor().newInstance()
 *
 *
 */
public class Reflection {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception, SecurityException {
	   //三种方式
	   //1、对象.getClass();   已经拿到对象
	   Class clz1 = new Iphone().getClass();
	
	
	   //2、类.class()  已经拿到模子
	   Class clz2 = Iphone.class;

	   //3、定位，偷     Class.forName(包名.类名)
	   Class clz3 = Class.forName("com.dequ.basic.Iphone");
	   
	   //创建对象
	 /* JDK8以前的写法
	  *  Iphone iphone=(Iphone)clz1.newInstance();
	   System.out.print(iphone);
	  */
	   //用构造器
	   Iphone iphone=(Iphone)clz1.getConstructor().newInstance();
	   System.out.print(iphone);
	}
	
}

class Iphone{
	public Iphone() {
		
	}
}