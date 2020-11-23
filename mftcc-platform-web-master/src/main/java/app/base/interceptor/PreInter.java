package app.base.interceptor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import app.base.SysDescTempUtil;
import app.component.sys.entity.SysDescTemp;
import app.util.CodeUtils;

public class PreInter implements HandlerInterceptor  {
	
	@Override
	public void afterCompletion(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse,
			Object handler, Exception exception) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object handler,
			ModelAndView modelandview) throws Exception {
		// TODO Auto-generated method stub
		 if(handler instanceof HandlerMethod){  
			 HandlerMethod handlerMethod = (HandlerMethod)handler;
			 if(handlerMethod.getMethod().getAnnotation(ResponseBody.class)==null&&modelandview!=null){//非ajax请求
				 String actionName  = handlerMethod.getMethod().getName();
				 String jsp = modelandview.getViewName();
				 if(httpservletrequest.getSession(false)==null){
					 return;
				 }
				 addHelpInfo(httpservletrequest, jsp,actionName);
				 setMoveBackSession(httpservletrequest);
			 };
		 }
	}

	@Override
	public boolean preHandle(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object handler)
			throws Exception {
			return true;
	}
	
	/**
	 * 设置后退机制的session
	 * @Title: setMoveBackSession
	 * @param @param request
	 */
	private void setMoveBackSession(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		//获取链接参数
		Map parmMap = request.getParameterMap();
		String entranceNo = null;
		String oldEntranceNo = null;
		String moveBack = null;
		String param = "";
		String vpNo = null;
		if(parmMap!=null){
			//视角编号
			if((String[])parmMap.get("vpNo")!=null){
				String parms[] = (String[])parmMap.get("vpNo");
				if(parms.length>0){
					vpNo = parms[0].toString();
					Map<String, Object> pageInfoMap = (Map<String, Object>) request.getAttribute("pageInfoMap");
					if(pageInfoMap!=null){
						CodeUtils cu =new CodeUtils();
						try {
							SysDescTemp sdt = (SysDescTemp) cu.getCacheByKeyName(vpNo);
							String descTempContent = SysDescTempUtil.replace(sdt, pageInfoMap,request);
							session.setAttribute("descTempContent",descTempContent);
						} catch (Exception e) {
						}
					}
				}
			}
			//入口编号
			if((String[])parmMap.get("entranceNo")!=null){
				String parms[] = (String[])parmMap.get("entranceNo");
				if(parms.length>0){
					entranceNo = parms[0].toString();
				}
				session.setAttribute("descTempContent","");
			}
			//前进后退使用标志
			if((String[])parmMap.get("moveBack")!=null){
				String parms2[] = (String[])parmMap.get("moveBack");
				if(parms2.length>0){
					moveBack = parms2[0].toString();
				}
			}
			Set set = parmMap.keySet() ;
			Iterator it = set.iterator() ;
			while(it.hasNext() ){
			    String name = it.next().toString()  ;
			    String value = request.getParameter(name);
			    if(!"moveBack".equals(name)&&!"entranceNo".equals(name)){//过滤掉所用的参数
			    	//前进后退将get传的参数转换正常
			    	value = new String(value.trim().getBytes("ISO-8859-1"), "utf-8");
			    	/*if("ajaxData".equals(name)){
			    		value = new String(value.trim().getBytes("ISO-8859-1"), "utf-8");
			    	}*/
			    	param = param + name +"="+ value;
			    	if(it.hasNext()) {
			    		 param += "&";
			        }
			    }
			}
			if(param.length()>300){
			    param = param.substring(0,300);
		    }
		}
		String currentURL = request.getRequestURL().toString();
		if(currentURL.indexOf("ActionAjax_")==-1){
			oldEntranceNo = (String)session.getAttribute("oldEntranceNo");
			if(entranceNo!=null&&oldEntranceNo!=null&&!oldEntranceNo.equals(entranceNo)){
				session.removeAttribute("markPointList");
				session.removeAttribute("markPoint");
			}else if(oldEntranceNo==null){
				session.removeAttribute("markPointList");
				session.removeAttribute("markPoint");
			}else {
			}
			if(moveBack!=null&&"true".equals(moveBack)){
				session.setAttribute("moveBack",moveBack);
			}else{
				session.removeAttribute("moveBack");
				session.setAttribute("currentURL", currentURL+"?"+param);//当前URL		
			}
			if(entranceNo!=null){
				session.setAttribute("entranceNo",entranceNo);
			}else{
				session.removeAttribute("entranceNo");
			}
		}
	}
	
	private void addHelpInfo(HttpServletRequest request,String jsp,String actionName) {
		if(request.getParameter("menuno")!=null&&!"".equals(request.getParameter("menuno"))){
			request.getSession().setAttribute("menuNo", request.getParameter("menuno"));
		}
		if(request.getParameter("entranceNo")!=null&&!"".equals(request.getParameter("entranceNo"))){
			request.getSession().setAttribute("menuNo", request.getParameter("entranceNo"));
		}
//		String helpJsp = entry.getValue().getParams().get("location");
		if(jsp!=null&&!"".equals(jsp)){
			request.getSession().setAttribute("helpJsp", jsp);
			request.getSession().setAttribute("helpAction",actionName);
		}
		/*System.out.println("**************************************************************");
		System.out.println(request.getSession().getAttribute("helpAction"));
		System.out.println(request.getSession().getAttribute("menuNo"));
		System.out.println(request.getSession().getAttribute("helpJsp"));
		System.out.println("**************************************************************");*/
	}

}
