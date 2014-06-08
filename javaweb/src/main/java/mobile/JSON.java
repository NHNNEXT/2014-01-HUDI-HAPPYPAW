package mobile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class JSON {
	public static String makeJSON(ArrayList arr) {
		String json = "[";
		for (int i = 0; i < arr.size(); ++i) {
			if (i != 0)
				json += ",";

			json += JSON.makeJSON(arr.get(i));
		}
		json += "]";
		return json;
	}

	public static String makeJSON(HashMap hash) {
		String json = "{";
		Iterator iterator = hash.keySet().iterator();
		boolean is_first = true;
		while (iterator.hasNext()) {
			if (!is_first)
				json += ",";
			Object key = iterator.next();
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

	public static String makeJSON(Object obj) {
		Method[] methods = obj.getClass().getMethods();
		if (obj instanceof HashMap)
			return makeJSON((HashMap) obj);
		else if(obj instanceof ArrayList)
			return makeJSON((ArrayList) obj);
		
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

					Object value = method.invoke(obj, new Object[] {});
					sValue = value.toString();
					sValue = sValue.replace("\"", "\\\"");
					sValue = sValue.replace("\r", "");
					sValue = sValue.replace("\t", "");
					sValue = sValue.replace("\n", "");
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
