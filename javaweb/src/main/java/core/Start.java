package core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import annotation.Controller;
import annotation.RequestMapping;

public class Start {
	//private List<Class<?>> classes;

	private HashMap<String, Object> classObject;
	private HashMap<String, Method> mappingMethod;
	private boolean is_end = false;
	private static Start start;

	private Start() {
		//classes = new ArrayList<Class<?>>();
		mappingMethod = new HashMap<String, Method>();
		classObject = new HashMap<String, Object>();
	}


	public void addClassObject(String className, Object object) {
		classObject.put(className, object);
	}

	public void addMapping(String url, Method method) {
		mappingMethod.put(url, method);
	}

	public void addMapping(Class c) {
		Object obj;
		try {
			obj = c.newInstance();
			start.addClassObject(c.getName(), obj);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		Annotation classAnnotation = c.getAnnotation(Controller.class);
		//Controller Annotation의 정보
		Controller controller = (Controller) classAnnotation;

		// method == function 선언한 메소드를 가져온다.
		for (Method method : c.getDeclaredMethods()) {
			// RequestMapping이 걸려 있는지 확인
			if (method.isAnnotationPresent(RequestMapping.class)) {
				Annotation methodAnnotation = method
						.getAnnotation(RequestMapping.class);
				// RequestMapping에 대한 Annotation 정보 가져오기.
				RequestMapping mapping = (RequestMapping) methodAnnotation;

				// System.out.println("method : " + method.getName());
				this.addMapping(mapping.url(), method);
			}
		}
	}

	public Method getMapping(String url) {
		return mappingMethod.get(url);
	}

	public Object getClassObject(String className) {
		return classObject.get(className);
	}
	
	public static Start getInstance() {
		if (start == null)
			start = new Start();
		return start;
	}
}
