package com.dequ.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebContext {

	private List<Entity> entitys;
	private List<Mapping> mappings;
	
	//��map���洢web.xml���ݣ�����ȡ����
	//entity:key:servlet-name    value:clz
	private Map<String,String> entityMap = new HashMap<String,String>();
	//mapping:key:url-pattern   value:servlet-name
	private Map<String,String> mappingMap = new HashMap<String,String>();
	
	public WebContext(List<Entity> entitys, List<Mapping> mappings) {
		this.entitys = entitys;
		this.mappings = mappings;
		
		for(Entity e:entitys) {
			entityMap.put(e.getName(), e.getClz());
		}
		for(Mapping m : mappings) {
			for(String s : m.getPatterns()) {
				mappingMap.put(s, m.getName());
			}
		}
	}
	
	public String getclz(String url) {
		String name = mappingMap.get(url);
		return entityMap.get(name);
	}

    

}
