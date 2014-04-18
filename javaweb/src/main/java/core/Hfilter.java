package core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class Hfilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI().substring(
				req.getContextPath().length());
		String uri = req.getRequestURI();
		if (path.startsWith("/resources")) {
			//System.out.println("path");
			chain.doFilter(request, response); // Goes to container's own
												// default servlet.
		} else {
			//System.out.println("request" + path);
			request.getRequestDispatcher("/app" +  path).forward(request, response); // Goes to controller servlet.
		}

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
