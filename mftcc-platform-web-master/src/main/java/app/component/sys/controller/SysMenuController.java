package app.component.sys.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.core.struts.BaseFormBean;

import app.component.sys.entity.SysMenu;
import app.component.sys.feign.SysMenuFeign;
@Controller
@RequestMapping("/sysMenu")
public class SysMenuController extends BaseFormBean {
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	//spring注入BO
	@Autowired
	private SysMenuFeign sysMenuFeign;


	/***************** Action method start  *************************/
	/**
	 * 返回所有菜单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllMenu")
	public String getAllMenu() throws Exception{
	//	menuStr =  sysMenuFeign.getAllJsonMenu();
		return "/component/sys/SysMenu_List";
	}
	@RequestMapping(value = "/getAllMenu1")
	public String getAllMenu1() throws Exception{
//		menuStr =  sysMenuFeign.getAllJsonMenu1();
//		String role_no=this.getHttpRequest().getParameter("roleNo");
//		String menuno_str=tblorgjobusersbo.checkJsp(role_no);
//		this.getHttpRequest().setAttribute("menuno_str", menuno_str);
		
		return "/component/sys/SysMenuRole_List";
	}
	/**
	 * 获取菜单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllMenus")
	public String getAllMenus() throws Exception{
//		ActionContext.initialize(ServletActionContext.getRequest(), ServletActionContext.getResponse());
//		menuStr =  sysMenuFeign.getAllJsonMenu();
//		String menuno_str=tblorgjobusersbo.checkuserJsp(userid);
//		this.getHttpRequest().setAttribute("menuno_str", menuno_str);
		return "/component/sys/SysUserRole_List";
	}
	/**
	 * 查询一个操作员号下面对应的所有菜单的内容
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAllMenuByRoleNo")
	public String getAllMenuByRoleNo()throws Exception{
//		String roleNo=this.getHttpRequest().getParameter("roleNo");
	//	menuStr=sysMenuFeign.getAllMenuByRoleNo(roleNo);
		return "/component/sys/SysMenuButton_List";	
	}
	
	@RequestMapping(value = "/getRootMenu")
	public String getRootMenu(Model model) throws Exception{
		String menuStr = sysMenuFeign.getAllJsonMenu2();
		model.addAttribute("menuStr", menuStr);
		return "rootMenu";
	}
	@RequestMapping(value = "/getChildMenu")
	public String getChildMenu(String menuNo) throws Exception{
		SysMenu sm = sysMenuFeign.getById(menuNo);
		if(sm!=null){
			List<SysMenu> children = sysMenuFeign.getAllMenuByParent(menuNo);
			this.getHttpResponse().setContentType("text/html;charset=utf-8"); 
			PrintWriter out = this.getHttpResponse().getWriter();
			StringBuffer result = new StringBuffer();
			if("1".equals(sm.getMenuLvl())){
				for(SysMenu menu : children){
					result.append("<li id='");
					result.append(menu.getMenuNo());
					result.append("'><span class='text'>");
					result.append(menu.getMenuName());
					result.append("</span><ul class='ajax'><li id='");
					result.append(menu.getMenuNo());
					result.append("-1'>{url:Sys_MenuAction_getChildMenu.action?menuNo=");
					result.append(menu.getMenuNo());
					result.append("}</li></ul></li>");
				}
				out.print(result.toString());
			} else if("2".equals(sm.getMenuLvl())){
				for(SysMenu menu : children){
					result.append("<li id='");
					result.append(menu.getMenuNo());
					result.append("'><span class='text'>");
					result.append(menu.getMenuName());
					result.append("</span></li>");
				}
				out.print(result.toString());
			} else {
				out.print("");
			}
		}
		return null;
	}
	@RequestMapping(value = "/insertMenu")
	public String insertMenu(String tname,String tcode,String pcode,String lvl,String url,String menuStats,String shape,String style) throws Exception{
		tname = java.net.URLDecoder.decode(new String(tname.getBytes("GBK"),"ISO-8859-1"), "UTF-8");
		
		this.getHttpResponse().setContentType("text/html;charset=utf-8"); 
		PrintWriter out = this.getHttpResponse().getWriter(); 
		String result = "success";
		try{
			SysMenu sm = new SysMenu();
			sm.setMenuNo(tcode);
			sm.setMenuName(tname);
			sm.setMenuParent(pcode);
			sm.setMenuLvl(lvl);
			sm.setMenuUrl(url);
			sm.setMenuStats(menuStats);
			sm.setMenuShape(shape);
			sm.setMenuStyle(style);
			if(sysMenuFeign.getById(tcode)==null){
				sysMenuFeign.insert(sm);
			}else {
				result = "菜单号[ "+tcode+" ]已存在!";
			}
		}catch(Exception e){
			e.printStackTrace();
			result = "新增菜单失败!";
		}
		out.println(result);
		return null;
	}
	@RequestMapping(value = "/updateMenu")
	public String updateMenu(String tname,String tcode,String pcode,String lvl,String url,String menuStats,String shape,String style,String tain) throws Exception{
		tname = java.net.URLDecoder.decode(new String(tname.getBytes("GBK"),"ISO-8859-1"), "UTF-8");
		
		this.getHttpResponse().setContentType("text/html;charset=utf-8"); 
		PrintWriter out = this.getHttpResponse().getWriter(); 
		String result = "success";
		try{
			SysMenu sm = new SysMenu();
			sm.setMenuNo(tcode);
			sm.setMenuName(tname);
			sm.setMenuParent(pcode);
			sm.setMenuLvl(lvl);
			sm.setMenuUrl(url);
			sm.setMenuStats(menuStats);
			sm.setMenuShape(shape);
			sm.setMenuStyle(style);
			sm.setMenuTain(tain);
			if(sysMenuFeign.getById(tcode)!=null){
				sysMenuFeign.update(sm);
			}else {
				result = "菜单号[ "+tcode+" ]不存在,修改失败!";
			}
		}catch(Exception e){
			result = "修改菜单失败!";
		}
		out.println(result);
		return null;
	}
	@RequestMapping(value = "/editWindow")
	public String editWindow() throws Exception{
		return "/component/sys/SysMenu_EditWindow";
	}
	@RequestMapping(value = "/moveMenu")
	public String moveMenu(String menuNo,String parentMenuNo) throws Exception{
		SysMenu sm = sysMenuFeign.getById(menuNo);
		if(sm!=null){
			this.getHttpResponse().setContentType("text/html;charset=utf-8"); 
			PrintWriter out = this.getHttpResponse().getWriter();
			SysMenu parent = sysMenuFeign.getById(parentMenuNo);
			if(parent!=null){
				sm.setMenuParent(parentMenuNo);
				sm.setMenuLvl((Integer.parseInt(parent.getMenuLvl())+1)+"");
				try {
					sysMenuFeign.update(sm);
					out.print("success");
					return null;
				} catch (Exception e) {
					out.print("false");
					return null;
				}
			}else {
				out.print("false");
			}
		}
		return null;
	}
	@RequestMapping(value = "/deleteMenu")
	public String deleteMenu(String tcode,String lvl) throws Exception{
		PrintWriter out = response.getWriter();
		if(tcode!=null && !"".equals(tcode)){
			try {
				int lev = (lvl==null|| "".equals(lvl))?3:Integer.parseInt(lvl);
				if(lev==3){
					sysMenuFeign.delete(tcode);
				} else if(lev==2){
					sysMenuFeign.delete(tcode);
					sysMenuFeign.deleteByParent(tcode);
				} else if (lev==1){
					for(SysMenu sysMenu : sysMenuFeign.getAllMenuByParent(tcode)){
						sysMenuFeign.deleteByParent(sysMenu.getMenuNo());
					}
					sysMenuFeign.deleteByParent(tcode);
					sysMenuFeign.delete(tcode);
					out.print("success");
					return null;
				}else {
				}
			} catch (Exception e) {
				out.print("fail");
				return null;
			}
			
		}else {
			out.print("fail");
		}
		return null;
	}

}