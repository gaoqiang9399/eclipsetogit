package app.component.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.common.EntityUtil;
import app.component.sys.entity.SysRole;
import app.component.sys.feign.SysRoleFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 系统角色Action
 * @author leopard mailto:haoxiaofeng@dhcc.com.cn
 * @date 2010-11-22
 * @see 
 * 修改记录:
 */
@Controller
@RequestMapping("/sysRole")
public class SysRoleController extends BaseFormBean {
	//页面传值
	private List<SysRole> sysRoleList;//查询结果集
    private FormData formsys2001;	//查询表单
	private String query;
    private FormService formService = new FormService();
    @Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private SysRoleFeign sysRoleFeign;
	
/***************** Action method start  *************************/
	/*
	 * 首页
	 */
	@RequestMapping(value = "/skipToC")
	public String toIndex() throws Exception{
		return "index";
	}
	/*
	 * 分页显示所有
	 */
	@RequestMapping(value = "/listByPage")
	public String listByPage() throws Exception{
		Ipage ipage = this.getIpage();
		ipage.setPageSize(3);
		
		if(StringUtils.isBlank(getEadisPage())){
			ipage.setPageNo(1);									//设置当前页码为1
		}else if("1".equals(getFlag())){
			ipage.setPageNo(1);									//设置当前页码为1
		}else{
			ipage.setPageNo(Integer.valueOf(getEadisPage()));	//设置当前页码为页面选定值
        }
		SysRole sysRole = new SysRole();
		ipage.setParams(this.setIpageParams("role", sysRole));
		this.setIpage(sysRoleFeign.findPage(ipage));
		return "/component/sys/SysRole_findByPage";
	}
	
/***************** Action method end    ***********************/
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model) throws Exception{
		ActionContext.initialize(request, response);
		SysRole role=new SysRole();
		formsys2001 = formService.getFormData("sys2001");
		getFormValue(formsys2001);
		setObjValue(formsys2001, role);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("role", role));
		sysRoleList=(List)sysRoleFeign.findByPage(ipage).getResult();
		model.addAttribute("formsys2001", formsys2001);
		model.addAttribute("sysRoleList", sysRoleList);
		return "/component/sys/SysRole_findByPage";
	}
	@RequestMapping(value = "/getAll")
	@ResponseBody
	public JSONObject getAll() throws Exception{
		SysRole role = new SysRole();
		sysRoleList =sysRoleFeign.getAllList(role);
		JSONArray array = JSONArray.fromObject(sysRoleList);
		JSONObject json = new JSONObject();
		json.put("sysRoleList", array);
		return json;
	}
	@RequestMapping(value = "/getAllAjax")
	@ResponseBody
	public Map<String, Object> getAllAjax()throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		try{
			SysRole role = new SysRole();
			List<SysRole> suList = sysRoleFeign.getAllList(role);
			JSONArray userArray = JSONArray.fromObject(suList);
			for (int i = 0; i < userArray.size(); i++) {
				userArray.getJSONObject(i).put("id",userArray.getJSONObject(i).getString("roleNo"));
				userArray.getJSONObject(i).put("text",userArray.getJSONObject(i).getString("roleName"));
			}
			dataMap.put("json", userArray);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataMap;
	}
	@RequestMapping(value = "/search")
	@ResponseBody
	public JSONObject search(String ajaxData) throws Exception{
		SysRole role = new SysRole();
		role.setRoleNo(ajaxData);
		role.setRoleName(ajaxData);
		sysRoleList =sysRoleFeign.getAllList(role);
		JSONArray array = JSONArray.fromObject(sysRoleList);
		JSONObject json = new JSONObject();
		json.put("sysRoleList", array);
		return json;
	}
	@RequestMapping(value = "/getByRoleNo")
	@ResponseBody
	public JSONObject getByRoleNo(String ajaxData,String roleNo) throws Exception{
		SysRole role = new SysRole();
		JSONObject json = JSONObject.fromObject(ajaxData);
		role = sysRoleFeign.getByRoleNo(roleNo);
		json = JSONObject.fromObject(role);
		return json;
	}
	@RequestMapping(value = "/del")
	@ResponseBody
	public JSONObject del(String roleNo) throws Exception{
		sysRoleFeign.deleteByRoleNo(roleNo);
		JSONObject json =new JSONObject();
		json.put("sts", "1");
		return json;
	}
	@RequestMapping(value = "/update")
	@ResponseBody
	public JSONObject update(String ajaxData) throws Exception{
		JSONObject json = JSONObject.fromObject(ajaxData);
		SysRole role = new SysRole();
		role = sysRoleFeign.getByRoleNo(json.getString("roleNo"));
		role.setRoleNo(json.getString("roleNo"));
		role.setRoleName(json.getString("roleName"));
		role.setRoleType(json.getString("roleType"));
		sysRoleFeign.update(role);
		return json;
	}
	@RequestMapping(value = "/input")
	public String input() throws Exception{
		
		return "/component/sys/SysRole_Insert";
	}
	/**
	 * 
	 * 方法描述： 新增或编辑角色
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-2-23 下午10:16:39
	 */
	@RequestMapping(value = "/insertOrUpdate")
	@ResponseBody
	public JSONObject insertOrUpdate(String ajaxData,String flag) throws Exception{
		ActionContext.initialize(request,response);
		//json = JSONObject.fromObject(ajaxData);
		Map<String,Object> dataMap = getMapByJson(ajaxData);
		SysRole role = new SysRole();
		JSONObject json =new JSONObject();
		String  roleNo = dataMap.get("roleNo").toString();
		String  roleName = dataMap.get("roleName").toString();
		String roleType = dataMap.get("roleType").toString();
		role.setRoleNo(roleNo);
		role.setRoleName(roleName);
		role.setRoleType(roleType);
		role.setRoleLev("1");
		if("add".equals(flag)){
			SysRole sysRole = new SysRole();
			sysRole = sysRoleFeign.getByRoleNo(roleNo);
			if(sysRole==null){
				sysRoleFeign.insert(role);
				json.put("flag", "1");
				json.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				json.put("flag", "0");
				json.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage("角色"));
			}
		}else if("edit".equals(flag)){
			sysRoleFeign.update(role);
			json.put("flag", "update");
			json.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		}else {
		}
		return json;
	}
	/**
	 * 
	 * 方法描述： 保存角色权限配置
	 * 包括角色、入口权限、功能权限
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-7-19 下午5:44:30
	 */
	@RequestMapping(value = "/saveRolePmsConfig")
	@ResponseBody
	public JSONObject saveRolePmsConfig(String ajaxData,String flag,String entrNoStr,String pmsBizArr,String opNoType) throws Exception{
		ActionContext.initialize(request,response);
		//json = JSONObject.fromObject(ajaxData);
		Map<String,Object> dataMap = getMapByJson(ajaxData);
		dataMap.put("flag", flag);
		String opNo=User.getRegNo(request);
		String opName=User.getRegName(request);
		JSONObject json =new JSONObject();
		dataMap.put("opNo",opNo);
		dataMap.put("opName",opName);
		dataMap.put("entrNoStr", entrNoStr);
		dataMap.put("pmsBizArr", pmsBizArr);
		if(StringUtils.isBlank(opNoType)){
			dataMap.put("opNoType", "1");
		}else{
			dataMap.put("opNoType", opNoType);
		}
		json=sysRoleFeign.saveRolePmsConfig(dataMap);
		return json;
	}
	@RequestMapping(value = "/getRoleListAjax")
	@ResponseBody
	public Map<String,Object> getRoleListAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap=new HashMap<String,Object>();
		SysRole role = new SysRole();
		try {
			EntityUtil entityUtil=new EntityUtil();
			dataMap.put("data", entityUtil.prodAutoMenu(role, ajaxData, query, "", null));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getPageForMutiSel")
	//选择多个角色
	public String getPageForMutiSel(Model model,String roleNo,String opNoType) throws Exception{
		ActionContext.initialize(request, response);
		SysRole role = new SysRole();
        role.setOpNoType(opNoType);
		sysRoleList = sysRoleFeign.getAllList(role);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		dataMap.put("sysRoleList", sysRoleList);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("roleNo", roleNo);
		return "/component/sys/SysRole_MutiSel";
	}
	

public FormData getFormsys2001() {
	return formsys2001;
}

public void setFormsys2001(FormData formsys2001) {
	this.formsys2001 = formsys2001;
}


public List<SysRole> getSysRoleList() {
	return sysRoleList;
}

public void setSysRoleList(List<SysRole> sysRoleList) {
	this.sysRoleList = sysRoleList;
}

public String getQuery() {
	return query;
}

public void setQuery(String query) {
	this.query = query;
}

	
}
