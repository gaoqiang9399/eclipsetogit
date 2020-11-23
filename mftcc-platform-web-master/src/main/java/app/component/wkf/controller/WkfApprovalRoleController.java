package app.component.wkf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.wkf.entity.WkfApprovalRole;
import app.component.wkf.feign.WkfApprovalRoleFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: WkfApprovalRoleAction.java Description:
 * 
 * @author:zhanglei@dhcc.com.cn
 * @Thu Feb 21 14:01:33 CST 2013
 **/
@Controller
@RequestMapping(value = "/wkfApprovalRole")
public class WkfApprovalRoleController extends BaseFormBean {

	// ҳ�洫
	private WkfApprovalRole wkfApprovalRole;
	private List<WkfApprovalRole> wkfApprovalRoleList;

	// ע��WkfApprovalRoleBo
	@Autowired
	private WkfApprovalRoleFeign wkfApprovalRoleFeign;

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	/**
	 * ��ҳ��ѯ
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByPage")
	public String findByPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0001 = formService.getFormData("wkf0001");
		wkfApprovalRole = new WkfApprovalRole();
		getFormValue(formwkf0001);
		setObjValue(formwkf0001, wkfApprovalRole);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfApprovalRole",wkfApprovalRole));
		wkfApprovalRoleList = (List<WkfApprovalRole>) wkfApprovalRoleFeign.findByPage(ipage).getResult();
		model.addAttribute("formwkf0001", formwkf0001);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalRole_List"; 
	}
	/**
	 * ��ҳ��ѯ
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByPageForApprovalUser")
	public String findByPageForApprovalUser(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0006 = formService.getFormData("wkf0006");
		wkfApprovalRole = new WkfApprovalRole();
		getFormValue(formwkf0006);
		setObjValue(formwkf0006, wkfApprovalRole);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfApprovalRole",wkfApprovalRole));
		wkfApprovalRoleList = (List<WkfApprovalRole>) wkfApprovalRoleFeign.findByPage(ipage).getResult();
		model.addAttribute("formwkf0006", formwkf0006);
		model.addAttribute("query", "");
		return "approvalUserList";
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByPageForGroup")
	public String findByPageForGroup(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0009 = formService.getFormData("wkf0009");
		wkfApprovalRole = new WkfApprovalRole();
		getFormValue(formwkf0009);
		setObjValue(formwkf0009, wkfApprovalRole);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfApprovalRole",wkfApprovalRole));
		wkfApprovalRoleList = (List<WkfApprovalRole>) wkfApprovalRoleFeign.findByPage(ipage).getResult();
		model.addAttribute("formwkf0009", formwkf0009);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalGroup_List";
	}
	/**
	 * pop��ҳ��ѯ
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByPagePop")
	public String findByPagePop(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0005 = formService.getFormData("wkf0005");
		wkfApprovalRole = new WkfApprovalRole();
		getFormValue(formwkf0005);
		setObjValue(formwkf0005, wkfApprovalRole);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfApprovalRole",wkfApprovalRole));
		wkfApprovalRoleList = (List<WkfApprovalRole>) wkfApprovalRoleFeign.findByPage(ipage).getResult();
		model.addAttribute("formwkf0005", formwkf0005);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalRole_PopList";
	}

	/**
	 * ��ȡ����ҳ��
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0002 = formService.getFormData("wkf0002");
		model.addAttribute("formwkf0002", formwkf0002);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalRole_Insert";
	}
	@RequestMapping(value = "/groupInput")
	public String groupInput(Model model,String wkfRoleNo) throws Exception 
	{
		ActionContext.initialize(request,response);
		if(null!=wkfRoleNo&&!"".equals(wkfRoleNo))
		{
			wkfApprovalRole = new WkfApprovalRole();
			wkfApprovalRole.setWkfRoleNo(wkfRoleNo);
			wkfApprovalRole=wkfApprovalRoleFeign.getById(wkfApprovalRole);
		}
		return "/component/wkf/WkfApprovalGroup_input";
	}
	@RequestMapping(value = "/insertOrUpdate")
	public String insertOrUpdate(Model model,String wkfRoleName,String wkfRoleNo,String members,String saveFlag) throws Exception 
	{
		ActionContext.initialize(request,response);
		wkfApprovalRole = new WkfApprovalRole();
		wkfApprovalRole.setWkfRoleName(wkfRoleName);
		wkfApprovalRole.setWkfRoleNo(wkfRoleNo);
		wkfApprovalRoleFeign.insertOrUpdate(wkfApprovalRole,members,saveFlag);
		if(null!=wkfRoleNo&&!"".equals(wkfRoleNo))
		{
			wkfApprovalRole = new WkfApprovalRole();
			wkfApprovalRole.setWkfRoleNo(wkfRoleNo);
			wkfApprovalRole=wkfApprovalRoleFeign.getById(wkfApprovalRole);
		}
		this.addActionMessage(model, "����ɹ�");
		return "/component/wkf/WkfApprovalGroup_input";
	}
	/**
	 * ��������
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0002 = formService.getFormData("wkf0002");
		getFormValue(formwkf0002);
		wkfApprovalRole = new WkfApprovalRole();
		setObjValue(formwkf0002, wkfApprovalRole);
		wkfApprovalRoleFeign.insert(wkfApprovalRole);
		getObjValue(formwkf0002, wkfApprovalRole);
		this.addActionMessage(model, "����ɹ�");
		model.addAttribute("formwkf0002", formwkf0002);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalRole_Detail";
	}

	/**
	 * �޸ı������
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	public String update(Model model) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0002 = formService.getFormData("wkf0002");
		getFormValue(formwkf0002);
		wkfApprovalRole = new WkfApprovalRole();
		setObjValue(formwkf0002, wkfApprovalRole);
		wkfApprovalRoleFeign.update(wkfApprovalRole);
		getObjValue(formwkf0002, wkfApprovalRole);
		this.changeFormProperty(formwkf0002,"wkfRoleNo","readonly","1");
		this.addActionMessage(model, "�޸ĳɹ�");
		model.addAttribute("formwkf0002", formwkf0002);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalRole_Detail";
	}

	/**
	 * ɾ�����
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/del")
	public String del(Model model,String wkfRoleNo) throws Exception {
		ActionContext.initialize(request,response);
		wkfApprovalRole = new WkfApprovalRole();
		wkfApprovalRole.setWkfRoleNo(wkfRoleNo);
		wkfApprovalRoleFeign.del(wkfApprovalRole);
		this.addActionMessage(model, "ɾ��ɹ�");
		wkfApprovalRole = new WkfApprovalRole();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfApprovalRole",wkfApprovalRole));
		wkfApprovalRoleList = (List<WkfApprovalRole>) wkfApprovalRoleFeign.findByPage(ipage).getResult();
		return "/component/wkf/WkfApprovalRole_List";
	}
	@RequestMapping(value = "/delGroup")
	public String delGroup(Model model,String wkfRoleNo) throws Exception {
		ActionContext.initialize(request,response);
		wkfApprovalRole = new WkfApprovalRole();
		wkfApprovalRole.setWkfRoleNo(wkfRoleNo);
		wkfApprovalRoleFeign.delGroup(wkfApprovalRole);
		this.addActionMessage(model, "ɾ��ɹ�");
		wkfApprovalRole = new WkfApprovalRole();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfApprovalRole",wkfApprovalRole));
		wkfApprovalRoleList = (List<WkfApprovalRole>) wkfApprovalRoleFeign.findByPage(ipage).getResult();
		return "/component/wkf/WkfApprovalRole_List";
	}
	

	/**
	 * ��ѯ����
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String wkfRoleNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0002 = formService.getFormData("wkf0002");
		wkfApprovalRole = new WkfApprovalRole();
		wkfApprovalRole.setWkfRoleNo(wkfRoleNo);
		wkfApprovalRole = wkfApprovalRoleFeign.getById(wkfApprovalRole);
		getObjValue(formwkf0002, wkfApprovalRole);
		this.changeFormProperty(formwkf0002,"wkfRoleNo","readonly","1");
		model.addAttribute("formwkf0002", formwkf0002);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalRole_Detail";
	}

	/**
	 * ��������У��
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model,String wkfRoleNo) {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0002 = formService.getFormData("wkf0002");
		getFormValue(formwkf0002);
		validateFormData(formwkf0002);
		wkfApprovalRole = new WkfApprovalRole();
		wkfApprovalRole.setWkfRoleNo(wkfRoleNo);
		wkfApprovalRole = wkfApprovalRoleFeign.getById(wkfApprovalRole);
		if(null!=wkfApprovalRole) {
            this.addActionError(model, "�ý�ɫ���["+wkfRoleNo+"]�Ѵ���");
        }
		model.addAttribute("formwkf0002", formwkf0002);
		model.addAttribute("query", "");
	}

	/**
	 * �޸ı������У��
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0002 = formService.getFormData("wkf0002");
		getFormValue(formwkf0002);
		validateFormData(formwkf0002);
//		wkfApprovalRole = new WkfApprovalRole();
//		wkfApprovalRole.setWkfRoleNo(wkfRoleNo);
//		wkfApprovalRole = wkfApprovalRoleBo.getById(wkfApprovalRole);
//		if(null!=wkfApprovalRole)
//			this.addActionError("�ý�ɫ���["+wkfRoleNo+"]�Ѵ���");
	}
/********新系统 begin********/
	/**
	 * C面 获取配置页面（含角色和用户）
	 * @return
	 */
	@RequestMapping(value = "/getConfPage")
	public String getConfPage(Model model){
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formwkf0001 = formService.getFormData("wkf0001");
		FormData formwkf0043 = formService.getFormData("wkf0043");
		model.addAttribute("formwkf0001", formwkf0001);
		model.addAttribute("formwkf0043", formwkf0043);
		model.addAttribute("query", "");
		return "/component/wkf/WkfApprovalRole_ConfPage";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,Integer pageNo,String tableId, String tableType) throws Exception {
		ActionContext.initialize(request,response);
		pageNo = pageNo == null ? 1:pageNo;
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		wkfApprovalRole = new WkfApprovalRole();
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			wkfApprovalRole=(WkfApprovalRole)JSONObject.toBean(jb,WkfApprovalRole.class);
			//this.getRoleConditions(claModelBase,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("wkfApprovalRole",wkfApprovalRole));
			ipage = wkfApprovalRoleFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData ,String tableId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		try{
			FormData formwkf0001 = formService.getFormData("wkf0001");
			getFormValue(formwkf0001, getMapByJson(ajaxData));
			if(this.validateFormData(formwkf0001)){
				wkfApprovalRole = new WkfApprovalRole();
				setObjValue(formwkf0001, wkfApprovalRole);
				if(wkfApprovalRoleFeign.getById(wkfApprovalRole)!=null){
					dataMap.put("flag", "error");
					dataMap.put("msg", "新增失败:角色编号重复");
				}else{
					wkfApprovalRoleFeign.insert(wkfApprovalRole);
					dataMap.put("flag", "success");
					dataMap.put("msg", "新增成功");
				}
				getTableDataIpage(dataMap, wkfApprovalRole.getWkfRoleNo(), tableId);
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
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		wkfApprovalRole = new WkfApprovalRole();
		FormService formService = new FormService();
		try{
			FormData formwkf0001 = formService.getFormData("wkf0001");
			getFormValue(formwkf0001, getMapByJson(ajaxData));
			if(this.validateFormData(formwkf0001)){
				wkfApprovalRole = new WkfApprovalRole();
				setObjValue(formwkf0001, wkfApprovalRole);
				wkfApprovalRoleFeign.update(wkfApprovalRole);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
				getTableDataIpage(dataMap, wkfApprovalRole.getWkfRoleNo(), tableId);
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
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formwkf0001 = formService.getFormData("wkf0001");
		wkfApprovalRole = new WkfApprovalRole();
		wkfApprovalRole.setWkfRoleNo(wkfRoleNo);
		wkfApprovalRole = wkfApprovalRoleFeign.getById(wkfApprovalRole);
		getObjValue(formwkf0001, wkfApprovalRole,formData);
		if(wkfApprovalRole!=null){
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
	public Map<String, Object> deleteAjax(String wkfRoleNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		wkfApprovalRole = new WkfApprovalRole();
		wkfApprovalRole.setWkfRoleNo(wkfRoleNo);
		try {
			wkfApprovalRoleFeign.delGroup(wkfApprovalRole);
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
		JsonTableUtil jtu = new JsonTableUtil();
		wkfApprovalRole = new WkfApprovalRole();
		wkfApprovalRole.setWkfRoleNo(wkfRoleNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("wkfApprovalRole",wkfApprovalRole));
		 List list = (List)wkfApprovalRoleFeign.findByPage(ipage).getResult();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag",list, ipage,true);
		ipage.setResult(tableHtml);
		dataMap.put("ipage",ipage);
	}
/********新系统 end**********/
}