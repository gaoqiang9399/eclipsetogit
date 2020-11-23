package app.component.wkf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.sys.entity.SysOrg;
import app.component.sys.entity.SysTaskInfo;
import app.component.sys.feign.SysOrgFeign;
import app.component.sysTaskInfoInterface.SysTaskInfoInterfaceFeign;
import app.component.wkf.entity.WkfApprovalUser;
import app.component.wkf.feign.TaskFeign;
import app.component.wkf.feign.WkfApprovalUserFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: WkfApprovalUserAction.java Description:
 * 
 * @author:zhanglei@dhcc.com.cn
 * @Fri Feb 22 10:03:00 CST 2013
 **/
@Controller
@RequestMapping(value = "/wkfApprovalUser")
public class WkfApprovalUserController extends BaseFormBean {

	private static final long serialVersionUID = -5939313515181895639L;
	// 页面传值ֵ
	private WkfApprovalUser wkfApprovalUser;
	private List<WkfApprovalUser> wkfApprovalUserList;
	private List<Map<String, Object>> wkfApprovalUserMapList;
	@Autowired
	private SysOrgFeign sysOrgFeign;
	
	@Autowired
	private SysTaskInfoInterfaceFeign sysTaskInfoInterfaceFeign;
	
	/*private List<TblOrgUser> tblOrgUserList;*/

	//注入WkfApprovalUserBo
	@Autowired
	private WkfApprovalUserFeign wkfApprovalUserFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private TaskFeign taskFeign;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	/*private IqpInterface iqpInterface;*/
	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0003 = formService.getFormData("wkf0003");
		wkfApprovalUser = new WkfApprovalUser();
		getFormValue(formwkf0003);
		setObjValue(formwkf0003, wkfApprovalUser);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfApprovalUser",wkfApprovalUser));
		wkfApprovalUserList = (List<WkfApprovalUser>) wkfApprovalUserFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formwkf0003", formwkf0003);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalUser_List";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByPagePop")
	public String findByPagePop(Model model,String taskId,String parNames,String popNames) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0033 = formService.getFormData("wkf0033");
		wkfApprovalUser = new WkfApprovalUser();
		String roleNo=wkfInterfaceFeign.getApprovalRoleNo(taskId);
		wkfApprovalUser.setWkfRoleNo(roleNo);
//		getFormValue(formwkf0008);
//		setObjValue(formwkf0008, wkfApprovalUser);
		this.changeFormProperty(formwkf0033,"taskId","initValue",taskId);
		this.changeFormProperty(formwkf0033,"parNames","initValue",parNames);
		this.changeFormProperty(formwkf0033,"popNames","initValue",popNames);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfApprovalUser",wkfApprovalUser));
//		wkfApprovalUserList = (List<WkfApprovalUser>) wkfApprovalUserBo.findByPage(ipage, wkfApprovalUser).getResult();
		wkfApprovalUserList = (List<WkfApprovalUser>) wkfApprovalUserFeign.findByPageMapPop(ipage).getResult();
		model.addAttribute("formwkf0033", formwkf0033);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalUser_PopList";
	}
	@ResponseBody
	@RequestMapping(value = "/findByPagePop4Ajax")
	@SuppressWarnings("unchecked")
	public Map<String, Object> findByPagePop4Ajax(String taskId,String parNames,String popNames,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formwkf0033 = formService.getFormData("wkf0033");
		wkfApprovalUser = new WkfApprovalUser();
		String roleNo = wkfInterfaceFeign.getApprovalRoleNo(taskId);
		wkfApprovalUser.setWkfRoleNo(roleNo);
//		getFormValue(formwkf0008);
//		setObjValue(formwkf0008, wkfApprovalUser);
		this.changeFormProperty(formwkf0033, "taskId", "initValue", taskId);
		this.changeFormProperty(formwkf0033, "parNames", "initValue", parNames);
		this.changeFormProperty(formwkf0033, "popNames", "initValue", popNames);
		Ipage ipage = this.getIpage();
//		wkfApprovalUserList = (List<WkfApprovalUser>) wkfApprovalUserBo.findByPage(ipage, wkfApprovalUser).getResult();
		ipage.setParams(this.setIpageParams("wkfApprovalUser", wkfApprovalUser));
		ipage = wkfApprovalUserFeign.findByPageMapPop(ipage);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
		ipage.setResult(tableHtml);
		dataMap.put("ipage", ipage);
		return dataMap;
	}

	@ResponseBody
	@RequestMapping(value = "/findByPageRoleAjax")
	@SuppressWarnings("unchecked")
	public Map<String, Object> findByPageRoleAjax(Integer pageSize,Integer pageNo,String roleNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			WkfApprovalUser wkfApprovalUser = new WkfApprovalUser();
			wkfApprovalUser.setWkfRoleNo(roleNo);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("wkfApprovalUser", wkfApprovalUser));
			ipage = wkfApprovalUserFeign.findByPage(ipage);
			dataMap.put("ipage", ipage);
			dataMap.put("flag", "success");
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "根据角色获取审批人员失败！");
		}
		return dataMap;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByPageMapPop")
	public String findByPageMapPop(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0008 = formService.getFormData("wkf0008");
		wkfApprovalUser = new WkfApprovalUser();
		getFormValue(formwkf0008);
		setObjValue(formwkf0008, wkfApprovalUser);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfApprovalUser",wkfApprovalUser));
		wkfApprovalUserMapList = (List<Map<String, Object>>) wkfApprovalUserFeign
				.findByPageMapPop(ipage).getResult();
		model.addAttribute("formwkf0008", formwkf0008);
		model.addAttribute("query", "");
		return "userMapList";
	}
	@RequestMapping(value = "/findUserForSignTask")
	public String findUserForSignTask(Model model,String wkfBrNo,String wkfRoleNo,String wkfUserName) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0041 = formService.getFormData("wkf0041");
		wkfApprovalUser = new WkfApprovalUser();
		String[] wkfBrNoArr = null;
		String[] wkfRoleNoArr = null;
		String[] wkfUserNameArr = null;

		if(wkfBrNo.indexOf("null")<0 && StringUtil.isNotEmpty(wkfBrNo)){
			wkfBrNoArr = wkfBrNo.split(",");
		}
		if(wkfRoleNo.indexOf("null")<0 && StringUtil.isNotEmpty(wkfRoleNo)){
			wkfRoleNoArr = wkfRoleNo.split(",");
		}
		if(wkfUserName.indexOf("null")<0 && StringUtil.isNotEmpty(wkfUserName)){
			wkfUserNameArr = wkfUserName.split(",");
		}

		wkfApprovalUser.setWkfBrNoArr(wkfBrNoArr);
		wkfApprovalUser.setWkfRoleNoArr(wkfRoleNoArr);
		//wkfApprovalUser.setWkfUserName(wkfUserName);
		wkfApprovalUser.setWkfUserNameArr(wkfUserNameArr);
		wkfApprovalUserList =  wkfApprovalUserFeign.findAllForWkf( wkfApprovalUser);
		if(wkfApprovalUserList.size()>0){
			ArrayList al = new ArrayList();
			for (WkfApprovalUser wkfApprovalUser : wkfApprovalUserList) {
				String val = wkfApprovalUser.getWkfUserName();
				//String label = wkfApprovalUser.getWkfUserName();
				String label = wkfApprovalUser.getDisplayname();
				OptionsList ol = new OptionsList();
				ol.setOptionValue(val);
				ol.setOptionLabel(label);
				al.add(ol);
			}
			setOptions(formwkf0041, "nextUser", al);
		}
		model.addAttribute("formwkf0041", formwkf0041);
		model.addAttribute("wkfBrNoArr", wkfBrNoArr);
		model.addAttribute("wkfRoleNoArr", formwkf0041);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalUser_ListSignTaskUser";
	}
	@ResponseBody
	@RequestMapping(value = "/findUserForSignTaskAjax")
	public Map<String,String> findUserForSignTaskAjax(Model model,String wkfBrNo,String wkfRoleNo,String wkfUserName) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,String> dataMap = new HashMap<String,String>();
		wkfApprovalUser = new WkfApprovalUser();
		String[] wkfBrNoArr = null;
		String[] wkfRoleNoArr = null;
		if(wkfBrNo.indexOf("null")<0 && StringUtil.isNotEmpty(wkfBrNo)){
			wkfBrNoArr = wkfBrNo.split(",");
		}
		if(wkfRoleNo.indexOf("null")<0 && StringUtil.isNotEmpty(wkfRoleNo)){
			wkfRoleNoArr = wkfRoleNo.split(",");
		}
		wkfApprovalUser.setWkfBrNoArr(wkfBrNoArr);
		wkfApprovalUser.setWkfRoleNoArr(wkfRoleNoArr);
		wkfApprovalUser.setWkfUserName(wkfUserName);
		wkfApprovalUserList =  wkfApprovalUserFeign.findAllForWkf( wkfApprovalUser);
		String userName = "";
		if(wkfApprovalUserList.size()>0){
			ArrayList al = new ArrayList();
			for (WkfApprovalUser wkfApprovalUser : wkfApprovalUserList) {
				String val = wkfApprovalUser.getWkfUserName();
				userName += val+",";
			}
		}
		dataMap.put("selectedValue", userName);
		return dataMap;
	}
	@RequestMapping(value = "/findApprovalUserByPage")
	public String findApprovalUserByPage(Model model,String taskId,String wkfRoleNo,String wkfUserName) throws Exception {
		ActionContext.initialize(request,response);	
		String wkfBrNo = null;//审批人员部门编号
		String brNo = User.getOrgNo(request);//当前操作员的部门编号
		SysOrg sysOrg = sysOrgFeign.getByBrNo(brNo);
		
		WkfApprovalUser wkfApprovalUser = new WkfApprovalUser();
		wkfApprovalUser.setWkfBrNo("");
		if(wkfRoleNo.contains(",")){
			
			String[] wkfRoleNoArr = wkfRoleNo.split(",");
			wkfRoleNo = "";
			for(int i=0;i<wkfRoleNoArr.length;i++){
				wkfRoleNo += "'"+wkfRoleNoArr[i]+"'";
				if(i<wkfRoleNoArr.length-1){
					wkfRoleNo += ",";
				}
			}
		}else{
			wkfRoleNo = "'"+wkfRoleNo+"'";
		}
		
		//获取同级数据
		if(taskId != null){
			String[] next = taskFeign.getNextTaskExecutor(taskId);//users, groups, level 
			if(null!=next[2]&&!"".equals(next[2].trim())){
				if("self".equals(next[2])){
					wkfBrNo = brNo;
				}else if("up_1".equals(next[2])){
					wkfBrNo = sysOrg.getUpOne();
				}else if("up_2".equals(next[2])){
					wkfBrNo = sysOrg.getUpTwo();
				}else {
				}
			}
		}
		
		wkfApprovalUser.setWkfRoleNo(wkfRoleNo);
		wkfApprovalUser.setWkfUserName(wkfUserName);
		wkfApprovalUser.setWkfBrNo(wkfBrNo);
		List<WkfApprovalUser> wkfApprovalUserList = wkfApprovalUserFeign.findApprovalUser(wkfApprovalUser);
		
		String opNo =User.getRegNo(request);//当前操作员
		String ifFilterFlag  =  new CodeUtils().getSingleValByKey("IF_FILTER_FLAG");//是否过滤当前操作员1-是 0-否
		List<WkfApprovalUser> resList = new ArrayList<WkfApprovalUser>();
		for(int i=0;i<wkfApprovalUserList.size();i++){
			String userName = wkfApprovalUserList.get(i).getWkfUserName();
			if(BizPubParm.YES_NO_Y.equals(ifFilterFlag) && opNo.equals(userName)){//过滤当前操作员
				continue;
			}
			SysTaskInfo sysTaskInfo = new SysTaskInfo();
			sysTaskInfo.setUserNo(userName);
			int cut = sysTaskInfoInterfaceFeign.findByPageSysTaskCut(sysTaskInfo);
			int count=sysTaskInfoInterfaceFeign.findByPageSysTaskCount(sysTaskInfo);
			((WkfApprovalUser)wkfApprovalUserList.get(i)).setWkfBrNoS(cut+"");
			((WkfApprovalUser)wkfApprovalUserList.get(i)).setCount(count+"");
			resList.add(wkfApprovalUserList.get(i));
		}
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tablewkf0014", "thirdTableTag", wkfApprovalUserList, this.getIpage(), true);
		model.addAttribute("wkfBrNo", wkfBrNo);
		model.addAttribute("wkfApprovalUserList", resList);
		model.addAttribute("tableHtml", tableHtml);
		model.addAttribute("brNo", brNo);
		model.addAttribute("sysOrg", sysOrg);
		model.addAttribute("query", "");
		model.addAttribute("nextWkfRoleNo", wkfRoleNo.replaceAll("'",""));
		return "/component/wkf/WkfApprovalUser_ListApprovalUser";
	}

	/**
	 * 异步返回审批用户列表
	* @Title findApprovalUserByPageAjax  
	* @throws Exception
	* @return String
	 */
	@ResponseBody
	@RequestMapping(value = "/findApprovalUserByPageAjax")
	public Map<String, Object> findApprovalUserByPageAjax(String wkfRoleNo,String wkfUserName) throws Exception {
		ActionContext.initialize(request,
				response);
		Gson gson = new Gson();
		wkfApprovalUser = new WkfApprovalUser();
		wkfApprovalUser.setWkfBrNo("");
		if(wkfRoleNo.contains(",")){
			String[] wkfRoleNoArr = wkfRoleNo.split(",");
			wkfRoleNo = "";
			for(int i=0;i<wkfRoleNoArr.length;i++){
				wkfRoleNo += "'"+wkfRoleNoArr[i]+"'";
				if(i<wkfRoleNoArr.length-1){
					wkfRoleNo += ",";
				}
			}
		}else{
			wkfRoleNo = "'"+wkfRoleNo+"'";
		}
		wkfApprovalUser.setWkfRoleNo(wkfRoleNo);
		wkfApprovalUser.setWkfUserName(wkfUserName);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfApprovalUser",wkfApprovalUser));
		List<Map<String, Object>> wkfApprovalUserMapList = (List<Map<String, Object>>) wkfApprovalUserFeign
				.findApprovalUserByPage(ipage).getResult();
		WkfApprovalUser wkfAppUser ;
		for(int i=0;i<wkfApprovalUserMapList.size();i++){
			//String userName = ((WkfApprovalUser)wkfApprovalUserMapList.get(i)).getWkfUserName();
			wkfAppUser = (WkfApprovalUser) gson.fromJson(gson.toJson(wkfApprovalUserMapList.get(i)),new TypeToken<WkfApprovalUser>(){}.getType());
			
			SysTaskInfo sysTaskInfo = new SysTaskInfo();
			sysTaskInfo.setUserNo(wkfAppUser.getWkfUserName());
			int cut = sysTaskInfoInterfaceFeign.findByPageSysTaskCut(sysTaskInfo);
			wkfAppUser.setWkfBrNoS(cut+"");
		}
		Map<String, Object> dataMap = new HashMap<String,Object>();
		JsonTableUtil jtu = new JsonTableUtil();
		//dataMap.put("ipage",ipage);
		dataMap.put("list", wkfApprovalUserMapList);
		
		return dataMap;
	}
	/**
	 * 获取新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		FormData formwkf0004 = formService.getFormData("wkf0004");
		model.addAttribute("formwkf0004", formwkf0004);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalUser_Insert";
	}

	/**
	 * 获取批量新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchInput")
	public String batchInput(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0007 = formService.getFormData("wkf0007");
		model.addAttribute("formwkf0007", formwkf0007);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalUser_BatchInsert";
	}

	/**
	 * 新增保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		FormData formwkf0004 = formService.getFormData("wkf0004");
		getFormValue(formwkf0004);
		wkfApprovalUser = new WkfApprovalUser();
		setObjValue(formwkf0004, wkfApprovalUser);
		wkfApprovalUserFeign.insert(wkfApprovalUser);
		getObjValue(formwkf0004, wkfApprovalUser);
		this.addActionMessage(model, "操作成功！");
		model.addAttribute("formwkf0004", formwkf0004);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalUser_Detail";
	}

	/**
	 * 批量新增保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchInsert")
	public String batchInsert(Model model,String wkfRoleNoStr,String roleNoStr,String brNoStr) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		wkfApprovalUserFeign.batchInsert(wkfRoleNoStr, roleNoStr, brNoStr);
		FormData formwkf0007 = formService.getFormData("wkf0007");
		this.addActionMessage(model, "操作成功");
		model.addAttribute("formwkf0007", formwkf0007);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalUser_BatchInsert";
	}

	/**
	 * 修改保存操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public String update(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		FormData formwkf0004 = formService.getFormData("wkf0004");
		getFormValue(formwkf0004);
		wkfApprovalUser = new WkfApprovalUser();
		setObjValue(formwkf0004, wkfApprovalUser);
		wkfApprovalUserFeign.update(wkfApprovalUser);
		getObjValue(formwkf0004, wkfApprovalUser);
		this.addActionMessage(model, "操作成功！");
		model.addAttribute("formwkf0004", formwkf0004);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalUser_Detail";
	}

	/**
	 * 删除操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/del")
	public String del(Model model,String seq) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		FormData formwkf0003 = formService.getFormData("wkf0003");
		wkfApprovalUser = new WkfApprovalUser();
		wkfApprovalUser.setSeq(seq);
		wkfApprovalUserFeign.del(wkfApprovalUser);
		this.addActionMessage(model, "删除成功");
		wkfApprovalUser = new WkfApprovalUser();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfApprovalUser",wkfApprovalUser));
		wkfApprovalUserList = (List<WkfApprovalUser>) wkfApprovalUserFeign
				.findByPage(ipage).getResult();
		model.addAttribute("formwkf0003", formwkf0003);
		model.addAttribute("query", "");
		
		return "/component/wkf/WkfApprovalUser_List";
	}

	/**
	 * 查询操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String seq) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		FormData formwkf0004 = formService.getFormData("wkf0004");
		wkfApprovalUser = new WkfApprovalUser();
		wkfApprovalUser.setSeq(seq);
		wkfApprovalUser = wkfApprovalUserFeign.getById(wkfApprovalUser);
		getObjValue(formwkf0004, wkfApprovalUser);
		model.addAttribute("formwkf0004", formwkf0004);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalUser_Detail";
	}

	/**
	 * 新增保存操作校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		FormData formwkf0004 = formService.getFormData("wkf0004");
		getFormValue(formwkf0004);
		validateFormData(formwkf0004);
		wkfApprovalUser = new WkfApprovalUser();
		setObjValue(formwkf0004, wkfApprovalUser);
		wkfApprovalUser = wkfApprovalUserFeign.getByUser(wkfApprovalUser);
		if (null != wkfApprovalUser) {
            this.addActionError(model, "该用户已存在审批角色编号为["
                    + wkfApprovalUser.getWkfRoleNo() + "]的审批角色");
        }
		model.addAttribute("formwkf0004", formwkf0004);
		model.addAttribute("query", "");
	}

	/**
	 * 修改保存操作校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		FormData formwkf0004 = formService.getFormData("wkf0004");
		getFormValue(formwkf0004);
		validateFormData(formwkf0004);
		wkfApprovalUser = new WkfApprovalUser();
		setObjValue(formwkf0004, wkfApprovalUser);
		wkfApprovalUser = wkfApprovalUserFeign.getByUser(wkfApprovalUser);
		if (null != wkfApprovalUser) {
            this.addActionError(model, "该用户已存在审批角色编号为["
                    + wkfApprovalUser.getWkfRoleNo() + "]的审批角色");
        }
		model.addAttribute("formwkf0004", formwkf0004);
		model.addAttribute("query", "");
	}

	/********新系统 begin********/
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model)  throws Exception {
		ActionContext.initialize(request,response);
		return "/component/wkf/WkfApprovalUser_ListPage";
	}

	
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageForWkfAjax")
	public Map<String, Object> findByPageForWkfAjax(String ajaxData,int pageNo,String wkfBrNo,String wkfRoleNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		wkfApprovalUser = new WkfApprovalUser();
		try {
			JSONArray arr = JSONArray.fromObject(ajaxData);
			JSONObject jb = JSONObject.fromObject(arr.get(0));
			wkfApprovalUser=(WkfApprovalUser)JSONObject.toBean(jb,WkfApprovalUser.class);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			wkfApprovalUser.setWkfUserName(jb.getString("customQuery"));
			String[] wkfBrNoArr = null;
			String[] wkfRoleNoArr = null;
			if(wkfBrNo.indexOf("null")<0){
				wkfBrNoArr = wkfBrNo.split(",");
			}
			if(wkfRoleNo.indexOf("null")<0){
				wkfRoleNoArr = wkfRoleNo.split(",");
			}
			wkfApprovalUser.setWkfBrNoArr(wkfBrNoArr);
			wkfApprovalUser.setWkfRoleNoArr(wkfRoleNoArr);
			ipage = wkfApprovalUserFeign.findByPageForWkf(ipage, wkfApprovalUser);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,Integer pageNo,String wkfBrNo,String wkfRoleNo,String tableId,String tableType) throws Exception {
		pageNo = pageNo == null ? 1 : pageNo;
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		wkfApprovalUser = new WkfApprovalUser();
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			wkfApprovalUser=(WkfApprovalUser)JSONObject.toBean(jb,WkfApprovalUser.class);
			//this.getRoleConditions(claModelBase,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("wkfApprovalUser",wkfApprovalUser));
			ipage = wkfApprovalUserFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData,String tableId ,String wkfRoleNo) throws Exception {
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formwkf0043 = formService.getFormData("wkf0043");
			getFormValue(formwkf0043, getMapByJson(ajaxData));

			if(this.validateFormData(formwkf0043)){
				wkfApprovalUser = new WkfApprovalUser();
				setObjValue(formwkf0043, wkfApprovalUser);
				WkfApprovalUser wau = new WkfApprovalUser();
				wau = wkfApprovalUserFeign.getByUser(wkfApprovalUser);
				if(wau!=null){
					dataMap.put("flag", "error");
					dataMap.put("msg", "该用户已存在！");
				}else{
					wkfApprovalUserFeign.insert(wkfApprovalUser);
					dataMap.put("flag", "success");
					dataMap.put("msg", "新增成功");
					getTableDataIpage(dataMap, wkfRoleNo, tableId);
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData,String tableId ,String wkfRoleNo ) throws Exception {
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		wkfApprovalUser = new WkfApprovalUser();
		try{
			FormData formwkf0043 = formService.getFormData("wkf0043");
			getFormValue(formwkf0043, getMapByJson(ajaxData));
			if(this.validateFormData(formwkf0043)){
				wkfApprovalUser = new WkfApprovalUser();
				setObjValue(formwkf0043, wkfApprovalUser);
				wkfApprovalUserFeign.update(wkfApprovalUser);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
				getTableDataIpage(dataMap, wkfRoleNo, tableId);
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String wkfRoleNo) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formwkf0043 = formService.getFormData("wkf0043");
		wkfApprovalUser = new WkfApprovalUser();
		wkfApprovalUser.setWkfRoleNo(wkfRoleNo);
		wkfApprovalUser = wkfApprovalUserFeign.getById(wkfApprovalUser);
		getObjValue(formwkf0043, wkfApprovalUser,formData);
		if(wkfApprovalUser!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String seq) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		wkfApprovalUser = new WkfApprovalUser();
		wkfApprovalUser.setSeq(seq);
		try {
			wkfApprovalUserFeign.del(wkfApprovalUser);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	private void getTableDataIpage(Map<String, Object> dataMap,String wkfRoleNo,String tableId) throws Exception {
//		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		JsonTableUtil jtu = new JsonTableUtil();
		wkfApprovalUser = new WkfApprovalUser();
		wkfApprovalUser.setWkfRoleNo(wkfRoleNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfApprovalUser",wkfApprovalUser));
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", (List)wkfApprovalUserFeign.findByPage(ipage).getResult(), ipage,true);
		ipage.setResult(tableHtml);
		dataMap.put("ipage",ipage);
	}
	
	/********新系统 end**********/	
	
	/**
	 * 
	 * 方法描述： 跳转至审批人员选择页面
	 * @param  busId 业务表数据id
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-1-24 下午5:53:37
	 */
	@RequestMapping(value = "/getUserForTaskList")
	public String getUserForTaskList(Model model,String taskId,String processId,String nodeName,String multipleFlag,String ifFilterFlag,String committeeMember,String committeeFlag,String creditAppId,String pasSubType)throws Exception{
		ActionContext.initialize(request, response);
		model.addAttribute("taskId", taskId);
		model.addAttribute("processId", processId);
		model.addAttribute("multipleFlag", multipleFlag);
		model.addAttribute("nodeName", nodeName);
		model.addAttribute("ifFilterFlag", ifFilterFlag);
		model.addAttribute("committeeFlag", committeeFlag);
		model.addAttribute("committeeMember", committeeMember);
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("pasSubType", pasSubType);
		return "/component/wkf/WkfApprovalUser_ApprovalUserList";
	}
	
	/**
	 * 
	 * 方法描述： 根据流程定义、节点名称获取审批节点的审批用户 
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-1-24 下午5:52:58
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserForTaskByPageAjax")
	public Map<String, Object> getUserForTaskByPageAjax(int pageNo,String ajaxData,String processId,String ifFilterFlag,String nodeName,String committeeFlag,String committeeMember,String taskId,String tableId,String tableType,String creditAppId,String pasSubType) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WkfApprovalUser wkfApprovalUser = new WkfApprovalUser();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if(ajaxData != null){
				wkfApprovalUser.setCustomQuery(ajaxData);// 自定义查询参数赋值
			}
			dataMap.put("processId", processId);
			dataMap.put("nodeName", nodeName);
			dataMap.put("opNo", User.getRegNo(request));
			dataMap.put("ifFilterFlag", ifFilterFlag);
			dataMap.put("committeeFlag", committeeFlag);
			dataMap.put("committeeMember", committeeMember);
			dataMap.put("taskId", taskId);
			dataMap.put("pasSubType", pasSubType);
			dataMap.put("wkfApprovalUser", wkfApprovalUser);
			dataMap.put("creditAppId", creditAppId);
			Map<String, Object> parmMap = this.setIpageParams("dataMap", dataMap);
			parmMap.put("wkfApprovalUser", wkfApprovalUser);
			ipage.setParams(parmMap);
			ipage = wkfApprovalUserFeign.getUserForTaskPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 
	 * 方法描述： 根据流程任务Id获取当前节点上配置的审批人员
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-1-29 上午10:41:27
	 */
	@RequestMapping(value = "/getApprovalUserListByTaskId")
	public String getApprovalUserListByTaskId(Model model)throws Exception{
		ActionContext.initialize(request, response);
		
		return "/component/wkf/WkfApprovalUser_ApprovalUserList";
	}
	
}