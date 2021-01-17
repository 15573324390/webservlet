package com.dequ.basic;

public class Person {



	//测试
	private String name;
	private String Sayings;

	public Person() {
		super();
	}
	public Person(String name, String sayings) {
		super();
		this.name = name;
		Sayings = sayings;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSayings() {
		return Sayings;
	}
	public void setSayings(String sayings) {
		Sayings = sayings;
	}
}
