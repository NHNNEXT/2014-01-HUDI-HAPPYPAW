package core;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class WrappedRequest extends HttpServletRequestWrapper {
	Map<String, String[]> parameterMap;

	public WrappedRequest(final HttpServletRequest request) {
		// build your param Map here with required values
		
		super(request);
		parameterMap = request.getParameterMap();
		parameterMap.put("id", new String[]{});
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return parameterMap;
		// return local param map
	}

	// override other methods if needed.

}