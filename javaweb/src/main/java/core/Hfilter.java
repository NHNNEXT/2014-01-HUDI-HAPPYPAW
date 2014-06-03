package core;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class Hfilter implements Filter {
	private String encoding = "UTF-8";
	private boolean forceEncoding = false;

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}
	public void setForceEncoding(boolean forceEncoding) {
		this.forceEncoding = forceEncoding;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI().substring(
				req.getContextPath().length());
		String uri = req.getRequestURI();
		
		System.out.println(this.encoding + "   " +  request.getCharacterEncoding());

		if (this.encoding != null
				&& (this.forceEncoding || request.getCharacterEncoding() == null)) {
			request.setCharacterEncoding(this.encoding);
			if (this.forceEncoding) {
				response.setCharacterEncoding(this.encoding);
			}
		}
		if (path.startsWith("/resources")) {
			// System.out.println("path");
			chain.doFilter(request, response); // Goes to container's own
												// default servlet.
		} else {
			// System.out.println("request" + path);
			request.getRequestDispatcher("/app" + path).forward(request,
					response); // Goes to controller servlet.
		}

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
