package core;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import annotation.Controller;
import annotation.RequestMapping;

public class Core extends HttpServlet {
	Start start;

	public void init() {
		start = Start.getInstance();

		List<Class<?>> classes = ClassFinder.find("");
		for (Class c : classes) {
			if (c.isAnnotationPresent(Controller.class))
				start.addMapping(c);
		}
		
	}

	

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		requestMapping(request, response);
	}

	public void requestJSP(String url, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	public void requestMapping(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = request.getPathInfo();
		url = url == null ? "/" : url;
		System.out.println(url);
		HttpSession session = request.getSession();
		Method method = start.getMapping(url);
		if (method == null) {
			requestJSP(url, request, response);
			return;
		}

		Object classObject = start.getClassObject(method.getDeclaringClass()
				.getName());
		if (classObject == null) {
			requestJSP(url, request, response);
			return;
		}

		Annotation methodAnnotation = method
				.getAnnotation(RequestMapping.class);
		RequestMapping mapping = (RequestMapping) methodAnnotation;

		ArrayList<Object> parameterArray = new ArrayList<Object>();

		Class<?>[] parameterType = method.getParameterTypes();
		// Parameter Type
		for (Class<?> pc : parameterType) {
			if (pc.isInstance(HttpServletRequest.class)) {
				parameterArray.add(request);
			} else if (pc.isInstance(HttpServletResponse.class)) {
				parameterArray.add(response);
			} else if (pc.isInstance(HttpSession.class)) {
				parameterArray.add(session);
			}
		}

		try {
			String str = (String) method.invoke(classObject,
					parameterArray.toArray());
			RequestDispatcher dispatcher = request.getRequestDispatcher(str);
			dispatcher.forward(request, response);
		} catch (IllegalArgumentException e) { // TODO Auto-generated catch
			e.printStackTrace();
		} catch (IllegalAccessException e) { // TODO
			e.printStackTrace();
		} catch (InvocationTargetException e) { // TODO Auto-generated catch
			e.printStackTrace();
		}

	}

}
