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
			//해당 클래스를 통해 새로운 인스턴스를 만든다.
			//메소드를 부르기 위해 인스턴스가 필요하다.
			
			start.addClassObject(c.getName(), obj);
			//해당 클래스의 인스턴스를 저장한다.
			
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

				//해당 주소에 대한 메소드를 넣는다.
				this.addMapping(mapping.value(), method);
			}
		}
	}
	//해당 url에 대한 매핑 메소드를 가져온다.
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
