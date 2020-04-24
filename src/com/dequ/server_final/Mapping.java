package com.dequ.server_final;

import java.util.HashSet;
import java.util.Set;
/**
 * 
 * @author 赖金荣
 *
 *	<servlet-mapping>
		<servlet-name>login</servlet-name>
		<url-pattern>/login</url-pattern>
		<url-pattern>/g</url-pattern>
	</servlet-mapping>
 *
 */
public class Mapping {

	private String name;
	private Set<String> patterns;   //一对多，去重
	
	public Mapping() {
		patterns = new HashSet<String>(); 
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<String> getPatterns() {
		return patterns;
	}
	public void setPatterns(Set<String> patterns) {
		this.patterns = patterns;
	}
	public void addPattern(String pattern) {
		this.patterns.add(pattern);
	}
}
