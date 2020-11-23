package app.base.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

public class AnyOfRolesAuthorizationFilter extends RolesAuthorizationFilter {

	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

		final Subject subject = getSubject(request, response);
		final String[] rolesArray = (String[]) mappedValue;

		if (rolesArray == null || rolesArray.length == 0) {
			// no roles specified, so nothing to check - allow access.
			return true;
		}

		for (String roleName : rolesArray) {
			if (subject.hasRole(roleName)) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		boolean result = this.isSessinoAvailable(request, response);
		if (result == true) {// 有session但是没权限
			return super.onAccessDenied(request, response);
		}
		return result;// result==false时不继续执行过滤链了，已经处理
	}

	/**
	 * session是否存活，如果是失效直接重定向。如果是seesion正常或第一次访问就返回true，继续执行后面的过滤链（如role）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private boolean isSessinoAvailable(ServletRequest request, ServletResponse response) throws IOException {
		boolean result = true;
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Cookie[] cookies = httpServletRequest.getCookies();
		boolean firstCome = true;
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if("shiro_redis_session".equals(cookie.getName())){
					firstCome = false;
				}
			}
		}
		String contextPath = httpServletRequest.getContextPath();
		if (firstCome == true) {
			this.redirectToLogin(request, response);
			return false;
		}
		
		if (httpServletRequest.getSession(false) == null) {
			result = false;
			if (httpServletRequest.getHeader("x-requested-with") != null && "XMLHttpRequest".equalsIgnoreCase(httpServletRequest.getHeader("x-requested-with"))) { // ajax
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				httpServletResponse.setHeader("sessionstatus", "timeout");
			} else {// 同步跳转请求
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				System.out.println(httpServletRequest.getRequestURI());
				httpServletResponse.sendRedirect(contextPath + "/sessionInvalid");
			}
		}else{
			final Subject subject = getSubject(request, response);
			if(CollectionUtils.isEmpty(subject.getPrincipals())){
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				httpServletResponse.sendRedirect(contextPath + "/sessionInvalid");
				result = false;
			}
		}
		return result;
	}
}