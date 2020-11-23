package app.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import app.base.User;

public class AuthenticationInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse,
			Object obj, Exception exception) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj,
			ModelAndView modelandview) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj)
			throws Exception {
		try {
			getMenuNo(httpservletrequest);// 加载访问的菜单编号到session，用于读取自定义按钮的key判定权限
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	private void getMenuNo(HttpServletRequest httpservletrequest) {
		try {
			String menuno = httpservletrequest.getParameter("menuno").trim();
			if (!"".equals(menuno)) {
				httpservletrequest.getSession().setAttribute("menuno", menuno);
			}
		} catch (Exception e) {
		} // 页面按钮操作时候，是取不到menuno的
	}

	private void getUserApptime(String startTime, String endTime, double ce, HttpServletRequest httpservletrequest) {
		try {
			String urlStr = (String) httpservletrequest.getServletPath();
			String tlrno = User.getOrgNo(httpservletrequest);
			String opname = User.getRegNo(httpservletrequest);
			String param = "";
			java.util.Map map = httpservletrequest.getParameterMap();
			java.util.Set set = map.keySet();
			java.util.Iterator it = set.iterator();
			while (it.hasNext()) {
				String name = it.next().toString();
				String value = httpservletrequest.getParameter(name);
				param = param + name + "=" + value + "&";
			}
			if (param.length() > 30) {
				param = param.substring(0, 30);
			}
			// securityInterface.insertUserApptime(urlStr,tlrno,opname,param,startTime,endTime,ce);
		} catch (Exception e) {
		} // 页面按钮操作时候，是取不到menuno的
	}

}
