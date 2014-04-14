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
	//서블릿을 시작할 때 불러오는 메소드.
	public void init() {
		//네이밍 다시 합시다.
		start = Start.getInstance();
		//컨트롤러와 매핑정보를 담겨있는 클래스.
		
		//클래스를 전부 찾아와 저장한다. 
		List<Class<?>> classes = ClassFinder.find("");
		
		for (Class c : classes) {
			//Controller 어노테이션이 있는지 확인.
			if (c.isAnnotationPresent(Controller.class))
				start.addMapping(c);
		}
		
	}

	

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		requestMapping(request, response, RequestMapping.Method.GET);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		requestMapping(request, response, RequestMapping.Method.POST);
	}

	public void requestJSP(String url, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	public void requestMapping(HttpServletRequest request,
			HttpServletResponse response, RequestMapping.Method doMethod) throws ServletException, IOException {
		//서블릿 url을 보여준다. 가장 상위 주소이면 null이다.
		String url = request.getPathInfo();
		url = url == null ? "/" : url;
		

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
			if (pc == (HttpServletRequest.class)) {
				parameterArray.add(request);
			} else if (pc == HttpServletResponse.class) {
				parameterArray.add(response);
			} else if (pc == HttpSession.class) {
				parameterArray.add(session);
			}
			System.out.println(pc.getName());
		}

		try {
			System.out.println(url+"   " + method.getName() );

			String str = (String) method.invoke(classObject,
					parameterArray.toArray());
			if(str.startsWith("redirect:")) {
				response.sendRedirect(str.substring(9));
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(str);
				dispatcher.forward(request, response);
			}
		} catch (IllegalArgumentException e) { // TODO Auto-generated catch
			e.printStackTrace();
		} catch (IllegalAccessException e) { // TODO
			e.printStackTrace();
		} catch (InvocationTargetException e) { // TODO Auto-generated catch
			e.printStackTrace();
		}

	}

}
