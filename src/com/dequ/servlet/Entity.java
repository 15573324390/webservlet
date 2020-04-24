package com.dequ.servlet;
/**
 * 
 * @author Àµ½ðÈÙ
 *<servlet-name>login -name
 *<servlet-class>com.dequ.servlet.LoginServlet -clz
 *
 */
public class Entity {
	private String name;
	private String clz;
	
	public Entity() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClz() {
		return clz;
	}
	public void setClz(String clz) {
		this.clz = clz;
	}
	

}
