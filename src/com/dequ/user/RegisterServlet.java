package com.dequ.user;

import com.dequ.server_final.Request;
import com.dequ.server_final.Response;
import com.dequ.server_final.Servlet;

public class RegisterServlet implements Servlet {

	@Override
	public void service(Request request, Response response) {
        String name = request.getParameter("uname");
        String pwd = request.getParameter("pwd");

        response.println("name:"+name);
        response.println("pwd:"+pwd);
		response.println("<html>");
		response.println("<head>");
		response.println("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\">");
		response.print("<title>");
		response.print("login!!!");
		response.println("</title>");
		response.println("</head>");
		response.println("<body>");
		response.println("<h1>shsxt server!!!!!!"+request.getUrl()+"</h1>");
		response.println("<span>welcome "+request.getParameter("uname")+"</span>");
		response.println("</body>");
		response.println("</html>");
        
	}


}
