package com.dequ.user;

import com.dequ.server_final.Request;
import com.dequ.server_final.Response;
import com.dequ.server_final.Servlet;

public class LoginServlet implements Servlet{

	@Override
	public void service(Request request, Response response) {
		response.println("<html>");
		response.println("<head>");
		response.print("<title>");
		response.print("login!!!");
		response.println("</title>");
		response.println("</head>");
		response.println("<body>");
		response.println("shsxt server!!!!!!"+request.getUrl());
		response.println("welcome "+request.getParameter("uname"));
		response.println("</body>");
		response.println("</html>");		
	}



}
