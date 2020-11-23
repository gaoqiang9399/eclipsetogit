package app.base.shiro;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		boolean result = this.isSessinoAvailable(request, response);
		if (result == true) {// 有session
			return super.onAccessDenied(request, response);
		}
		return result;// result==false时不继续执行过滤链了，已经处理
	}

	/**
	 * result=fasle，session如果失效处理直接重定向， =true 意味没权限
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
			for (Cookie cookie : cookies) {
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
				httpServletResponse.setCharacterEncoding("UTF-8");
				Map<String,Object> map = new HashMap<>();
				httpServletResponse.setContentType("application/json");
				map.put("code",403);
				map.put("message","登录认证失效，请重新登录!");
				httpServletResponse.setStatus(403);
				httpServletResponse.getWriter().write(JSONObject.toJSON(map).toString());
			} else {// 同步跳转请求
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				httpServletResponse.sendRedirect(contextPath + "/sysLogin/sessionInvalid");
			}
		}
		return result;

	}

}