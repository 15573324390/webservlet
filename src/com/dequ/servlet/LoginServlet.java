package com.dequ.servlet;

public class LoginServlet implements Servlet{

	@Override
	public void service(Request request, Response response) {
		response.print("<html>");
		response.print("<head>");
		response.print("<title>");
		response.print("��һ��С�ű�");
		response.print("</title>");
		response.print("</head>");
		response.print("<body>");
		response.print("shsxt ����������server!!!!!!"+request.getUrl());
		response.print("��ӭ������"+request.getParameterValue("uname"));
		response.print("</body>");
		response.print("</html>");		
	}



}
