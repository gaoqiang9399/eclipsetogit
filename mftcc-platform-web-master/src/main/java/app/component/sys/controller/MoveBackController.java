package app.component.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
@Controller
@RequestMapping("/moveBack")
public class MoveBackController extends BaseFormBean{
	private static final long serialVersionUID = 6808525534132651942L;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	/**
	 * 返回标志
	 */
	private static final String FLAG_BACK = "back";
	/**
	 * 前进标志
	 */
	private static final String FLAG_FORWARD = "forward";
	/**
	 * 入口后退机制Action
	 * @Title: backMechanism
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/backMechanism")
	@ResponseBody
	public Map<String,Object> backMechanism() throws Exception{
		ActionContext.initialize(request,response);
		HttpSession session = request.getSession();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<HashMap<String,String>> markPointList = null;
		markPointList = (List<HashMap<String,String>>)session.getAttribute("markPointList");
		String beforURL = "";
		String markURL = "";
		String currentURL = (String)session.getAttribute("currentURL");
		String markPoint = (String)session.getAttribute("markPoint");//当前标记点
		String entranceNo = (String)session.getAttribute("oldEntranceNo");//当前入口
		Integer markIndex = (Integer)session.getAttribute("markIndex");//当前标记点位置
		boolean markFlag = false;//验证标记点和标记点位置是否符合
		HashMap<String,String> forwardBackMap = new HashMap<String,String>();
		if(markPointList!=null){
			int markListSize = markPointList.size();
			if(markListSize>0){//匹配标记点对应的URL
				for(int i=0;i<markListSize;i++){
					 HashMap<String,String> map = markPointList.get(i);
					 if(markPoint!=null&&markPoint.equals(map.get("markPoint"))){
						 int markIndexTmp = Integer.parseInt(map.get("markIndex"));
						 if(markIndex==markIndexTmp){
							 markFlag = true;
							 markURL = map.get("markURL");
							 break;
						 }
					 }
				}
				if(currentURL!=null&&currentURL.equals(markURL)){//当前URL是否与标记点URL一致
					if(markFlag){
						if(markIndex>=0){
							beforURL = markPointList.get(markIndex-1).get("markURL");
							if(beforURL!=null&&!"".equals(beforURL)){
								beforURL = getMoveBackURL(beforURL);
								dataMap.put("flag", "success");
							}
						}else{
							dataMap.put("flag", "error");
							dataMap.put("msg", "指针定向为"+markIndex);
						}
					}else{
						dataMap.put("flag", "error");
					}
				}else{
					beforURL = markPointList.get(markIndex).get("markURL");
					if(beforURL!=null&&!"".equals(beforURL)){
						if(beforURL.contains("?")){	
							if(beforURL.length()-1==beforURL.indexOf("?")){
								beforURL+="moveBack=true";
							}else{
								beforURL+="&moveBack=true";
							}
						}else{
							beforURL+="?moveBack=true";
						}
						dataMap.put("flag", "success");
					}
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", "不后退");
			}
			dataMap.put("backURL", beforURL);
			if(markFlag){
				forwardBackMap.put("back", "false");
			}else{
				forwardBackMap.put("back", "true");
			}
			if(markListSize>1){
				forwardBackMap.put("forward", "true");
			}else{
				forwardBackMap.put("forward", "false");
			}
 			dataMap.put("forwardBack", forwardBackMap);
 			dataMap.put("entranceNo", entranceNo);
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "没有相应操作不可后退");
			forwardBackMap.put("back", "false");
			forwardBackMap.put("forward", "false");
			dataMap.put("forwardBack", forwardBackMap);
		}
		changeSession(session,FLAG_BACK);//调整session
		return dataMap;
	}
	/**
	 * 入口前进机制Action
	 * @Title: backMechanism
	 */
	@RequestMapping(value = "/forwardMechanism")
	@ResponseBody
	public Map<String,Object> forwardMechanism() throws Exception{
		ActionContext.initialize(request,response);
		HttpSession session = request.getSession();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<HashMap<String,String>> markPointList = null;
		markPointList = (List<HashMap<String,String>>)session.getAttribute("markPointList");
		String afterURL = "";
		String markPoint = (String)session.getAttribute("markPoint");
		Integer markIndex = (Integer)session.getAttribute("markIndex");
		String entranceNo = (String)session.getAttribute("oldEntranceNo");
		boolean markFlag = false;
		HashMap<String,String> forwardBackMap = new HashMap<String,String>();
		if(markPointList!=null&&markIndex>=0&&markPoint!=null){
			int listSize = markPointList.size();
			for(int i=0;i<listSize;i++){
				 HashMap<String,String> map = markPointList.get(i);
				 if(markPoint!=null&&markPoint.equals(map.get("markPoint"))){
					 int markIndexTmp = Integer.parseInt(map.get("markIndex"));
					 if(markIndex==markIndexTmp){
						 markFlag = true;
						 break;
					 }
				 }
			}
			if(markFlag){
				if(markIndex<listSize){
					afterURL = markPointList.get(markIndex+1).get("markURL");
				}
				if(afterURL!=null&&!"".equals(afterURL)){
					afterURL = getMoveBackURL(afterURL);
					dataMap.put("flag", "success");
					dataMap.put("forwardURL", afterURL);
					dataMap.put("entranceNo", entranceNo);//根据入口来判断后退的ifream
					if(markIndex+2<listSize){
						forwardBackMap.put("forward", "true");
					}else{
						forwardBackMap.put("forward", "false");
					}
				}else{
					forwardBackMap.put("forward", "false");
				}
			}else{
				forwardBackMap.put("forward", "false");
			}
			forwardBackMap.put("back", "true");
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "没有相应操作不可后退");
			forwardBackMap.put("forward", "false");
			forwardBackMap.put("back", "false");
		}
		dataMap.put("forwardBack", forwardBackMap);
		changeSession(session,FLAG_FORWARD);//调整session
		return dataMap;
	}
	/**
	 * 初始化前进后退按钮是否可点击
	 * @Title: initMoveBackBotton
	 */
	@RequestMapping(value = "/initMoveBackBotton")
	@ResponseBody
	public Map<String,Object> initMoveBackBotton() throws Exception{
		ActionContext.initialize(request,response);
		HttpSession session = request.getSession();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<HashMap<String,String>> markPointList = null;
		markPointList = (List<HashMap<String,String>>)session.getAttribute("markPointList");
		String currentURL = (String)session.getAttribute("currentURL");
		String markPoint = (String)session.getAttribute("markPoint");
		String markURL = "";
		Integer markIndex = (Integer)session.getAttribute("markIndex");
		boolean markFlag = false;
		HashMap<String,String> forwardBackMap = new HashMap<String,String>();
		if(markPointList!=null){
		/*	System.out.println("markPointList-长度："+markPointList.size());
			System.out.println("markPoint："+markPoint);
			System.out.println("markIndex："+markIndex);*/
		}
		if(markPointList!=null&&markIndex>=0&&markPoint!=null){
			int markListSize = markPointList.size();
			if(markListSize>0){
				for(int i=0;i<markListSize;i++){
					HashMap<String,String> map = markPointList.get(i);
					if(markPoint.equals(map.get("markPoint"))&&markIndex==i){
						markFlag = true;
						markURL = map.get("markURL");
						break;
					}
				}
				/*System.out.println("----:"+markPointList);
				System.out.println("currentURL:"+currentURL+",\n   markURL:"+markURL);*/
				if(currentURL!=null&&!currentURL.equals(markURL)){
					forwardBackMap.put("forward", "false");
					forwardBackMap.put("back", "true");
				}else{
					if(markFlag&&markListSize>1){
						if(markIndex<markListSize-1&&markIndex>0){
							forwardBackMap.put("forward","true");
							forwardBackMap.put("back", "true");
						}else if(markIndex==0){
							forwardBackMap.put("forward", "true");
							forwardBackMap.put("back", "false");
						}else if(markIndex==markListSize-1){
							forwardBackMap.put("forward", "false");
							forwardBackMap.put("back", "true");
						}else{
							forwardBackMap.put("forward", "false");
							forwardBackMap.put("back", "false");
						}
					}else{
						forwardBackMap.put("forward", "false");
						forwardBackMap.put("back", "false");
					}
				}
			}else{
				forwardBackMap.put("forward", "false");
				forwardBackMap.put("back", "false");
			}
		}else{
			forwardBackMap.put("forward", "false");
			forwardBackMap.put("back", "false");
		}
		dataMap.put("flag", "success");
		dataMap.put("forwardBack", forwardBackMap);
		return dataMap;	
	}
	
	/**
	 * 后退返回成功将对session数据稍微调整
	 * @Title: changeSession
	 * @param @param session
	 */
	@SuppressWarnings("unchecked")
	private void changeSession(HttpSession session,String flag)throws Exception{
		List<HashMap<String,String>> markPointList = null;
		markPointList = (List<HashMap<String,String>>)session.getAttribute("markPointList");
		String markPoint = (String)session.getAttribute("markPoint");
		Integer markIndex = (Integer)session.getAttribute("markIndex");
		String markURL = "";
		String currentURL = (String)session.getAttribute("currentURL");
		int markListSize = 0;
		boolean markFlag = false;
		if(markPointList!=null){
			markListSize = markPointList.size();
			for(int i=0;i<markListSize;i++){
				HashMap<String,String> map = markPointList.get(i);
				if(markPoint!=null&&markPoint.equals(map.get("markPoint"))){
					 int markIndexTmp = Integer.parseInt(map.get("markIndex"));
					 if(markIndex==markIndexTmp){
						 markFlag = true;
						 markURL = map.get("markURL");
						 break;
					 }
				}
			}
		}
		if(markFlag){
			if(FLAG_BACK.equals(flag)){//返回
				if(markURL!=null&&currentURL!=null&&!markURL.equals(currentURL)){//跳转到没有标记点
					markPoint = markPointList.get(markIndex).get("markPoint");
					session.setAttribute("markPoint", markPoint);
					session.setAttribute("markIndex", markIndex);
					session.setAttribute("currentURL", markPointList.get(markIndex).get("markURL"));
				}else if(markIndex>0){
					markPoint = markPointList.get(markIndex-1).get("markPoint");
					session.setAttribute("markPoint", markPoint);
					session.setAttribute("markIndex", markIndex-1);
					session.setAttribute("currentURL", markPointList.get(markIndex-1).get("markURL"));
				}else if(markIndex==0){
					session.setAttribute("currentURL", markPointList.get(0).get("markURL"));
				}else {
				}
			}else if(FLAG_FORWARD.equals(flag)){//前进
				if(markIndex<markListSize-1){
					markPoint = markPointList.get(markIndex+1).get("markPoint");
					session.setAttribute("markPoint", markPoint);
					session.setAttribute("markIndex", markIndex+1);
					session.setAttribute("currentURL", markPointList.get(markIndex+1).get("markURL"));
				}
			}else {
			}
		}
	}
	/**
	 * 获取前进后退URL
	 */
	public static String getMoveBackURL(String url){
		if(url.contains("?")){
			if(url.length()-1==url.indexOf("?")){
				url+="moveBack=true";
			}else{
				url+="&moveBack=true";
			}
		}else{
			url+="?moveBack=true";
		}
		return url;
	}
}
