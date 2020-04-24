package com.dequ.servlet;

public class LoginServlet implements Servlet{

	@Override
	public void service(Request request, Response response) {
		response.print("<html>");
		response.print("<head>");
		response.print("<title>");
		response.print("第一个小脚本");
		response.print("</title>");
		response.print("</head>");
		response.print("<body>");
		response.print("shsxt 我我我我怕server!!!!!!"+request.getUrl());
		response.print("欢迎回来："+request.getParameterValue("uname"));
		response.print("</body>");
		response.print("</html>");		
	}



}
