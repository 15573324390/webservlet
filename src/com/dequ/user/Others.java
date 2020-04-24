package com.dequ.user;

import com.dequ.server_final.Request;
import com.dequ.server_final.Response;
import com.dequ.server_final.Servlet;

public class Others implements Servlet {

	@Override
	public void service(Request request, Response response) {
		response.print("Test!!!");	
	}

}
