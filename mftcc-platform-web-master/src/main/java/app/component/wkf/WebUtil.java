package app.component.wkf;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class WebUtil {
	
	public static Object getUserSession(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(AppConstant.SESSION_USER_KEY);
		if(obj == null) {
			System.err.println(AppConstant.SESSION_USER_KEY + " is null");
			return null;
		}
		return obj; 
	}
	 
	public static String getUserId(HttpServletRequest request) {
		return (String)request.getSession().getAttribute("tlrno");
	}
	
	public static String getUserName(HttpServletRequest request) {
		return (String)request.getSession().getAttribute("displayname");
	}
	
	public static String getBranchId(HttpServletRequest request) {
		return (String)request.getSession().getAttribute("brno");
	}
	
	public static void forwardTimeOutPage(HttpServletRequest request, HttpServletResponse response) {
		
		if( !AppConstant.CHECKSESSION ) {
			return;
		}
		
		try {
			PrintWriter out = response.getWriter();
			out.print("<script language=\"javascript\">parent.document.location.href='" 
						+ request.getContextPath() 
						+ AppConstant.TIMEOUT_PAGE + "';</script>");
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}
}
