package com.dequ.server_final;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Test {
	private static Map<String,List<String>> map = null;
	public static void main(String[] args) {
		System.out.println("¿ªÊ¼");
	    int o = 0;
	    for(int i = 0 ; i < 10 ; i++) {
	    	List<String> list1 = new ArrayList();
	    	for(int j = 0 ; j < 3 ; j++) {
	    		list1.add(" "+ o++ +" ");
	    	}
	    	map = new HashMap<>();
	    	map.put("d"+i, list1);
	    }
	    
	}
	
	public static String oppo(String key) {
		List<String> values = map.get(key);
		return values.get(0);		
	}
}
