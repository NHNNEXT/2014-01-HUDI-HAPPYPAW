package mobile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.DAO;

public class JSON {
	private static Logger logger = LoggerFactory.getLogger(JSON.class);
	public static boolean has(Object obj, Object...objects) {
		for(int j = 0; j < objects.length; ++j) {
			if(objects[j].equals(obj))
				return true;
		}
		return false;
	}
	public static String makeJSON(ArrayList arr, Object...ignores) {
		String json = "[";
		boolean is_first = true;
		for (int i = 0; i < arr.size(); ++i) {
			boolean is_ignore = has(arr.get(i), ignores);
			if(is_ignore)
				continue;
			
			if (!is_first)
				json += ",";
			
			json += JSON.makeJSON(arr.get(i));
			is_first = false;
		}
		json += "]";
		return json;
	}

	public static String makeJSON(HashMap hash, Object...ignores) {
		String json = "{";
		Iterator iterator = hash.keySet().iterator();
		boolean is_first = true;
		while (iterator.hasNext()) {
			Object key = iterator.next();
			
			boolean is_ignore = has(key, ignores);
			if(is_ignore)
				continue;
			
			if (!is_first)
				json += ",";
			
			String value = hash.get(key) + "";
			value = value.replace("\"", "\\\"");
			value = value.replace("\r", "");
			value = value.replace("\t", "");
			value = value.replace("\n", "");
			try {
				int number = Integer.parseInt(value);
				json += "\"" + key + "\"" + ":" + number;
			} catch (Exception e) {
				json += "\"" + key + "\"" + ":\"" + value + "\"";
			}
			is_first = false;
		}
		json += "}";
		return json;
	}

	public static String makeJSON(Object obj, Object...ignores) {
		Method[] methods = obj.getClass().getMethods();
		if (obj instanceof HashMap)
			return makeJSON((HashMap) obj, ignores);
		else if(obj instanceof ArrayList)
			return makeJSON((ArrayList) obj, ignores);
		
		String sValue = "";
		String json = "{";
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			String name = method.getName();
			if (name.startsWith("get")) {
				try {
					if (name.equals("getClass"))
						continue;
					if (method.getParameterTypes().length > 0)
						continue;
					name = name.substring(3, name.length());
					name = name.toLowerCase();
					
					
					logger.info("invoke : " + name);
					
					boolean is_ignore = has(name, ignores);
					if(is_ignore)
						continue;
					
					Object value = method.invoke(obj, new Object[] {});
					if(value == null)
						value = "";
					
					sValue = value.toString();
					sValue = sValue.replace("\"", "\\\"");
					sValue = sValue.replace("\r", "");
					sValue = sValue.replace("\t", "");
					sValue = sValue.replace("\n", "");
					
					logger.info("invoke value: " + sValue);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (!json.equals("{"))
					json += " , ";
				
				json += "\"" + name + "\"" + " : " + "\"" + sValue + "\"";

				/*
				 * if(!json.equals("{")){ json += " , " + "\"" + name +"\"" +
				 * " : " + "\"" + sValue +"\"" ; }else{ json += "\"" + name
				 * +"\"" + " : " + "\"" + sValue +"\"" ; }
				 */
			}
		}
		json += "}";

		return json;
	}
}
