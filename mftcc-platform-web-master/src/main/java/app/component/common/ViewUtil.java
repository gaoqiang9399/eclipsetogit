package app.component.common;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

public class ViewUtil {
	/**
	 * 视角所需要的参数传递
	 * @param request
	 * @param bean
	 */
	public static void setViewPointParm(HttpServletRequest request ,Object bean){
		JSONObject json  = JSONObject.fromObject(bean);
		request.setAttribute("jsonBean", json.toString());
	}
	
}
