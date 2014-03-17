package test;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		String name = req.getParameter("name");
		if(name ==null){
			name= "JSP";
		}
		String text = req.getParameter("text");
		
		String hello = "hello "+ name;
		
		req.setAttribute("helloo", hello);
		req.setAttribute("texttest", text);//http://localhost:8080/java-mvc/hello?name=dak&text=test
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/hello.jsp");
		dispatcher.forward(req, resp);
		
		
	}
}
