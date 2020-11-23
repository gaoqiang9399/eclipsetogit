package app.component.sys.controller;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.sys.entity.MfUserPermission;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.MfUserPermissionFeign;
import app.component.sys.feign.SysUserFeign;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfUserPermissionAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Aug 21 15:13:44 CST 2017
 **/
@Controller
@RequestMapping("/mfUserPermission")
public class MfUserPermissionController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入mfUserPermissionFeign
	@Autowired
	private MfUserPermissionFeign mfUserPermissionFeign;
	@Autowired
	private SysUserFeign sysUserFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	//全局变量
	private String query;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	//表单变量
	private FormData formsys0001;
	private FormData formsys0002;
	private FormService formService = new FormService();
	@Autowired
	public MfBusApplyFeign mfBusApplyFeign;

	public MfUserPermissionController(){
		query = "";
	}
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,response);
		return "/component/sys/MfUserPermission_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			mfUserPermission.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfUserPermission.setCriteriaList(mfUserPermission, ajaxData);//我的筛选
			//this.getRoleConditions(mfUserPermission,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfUserPermission", mfUserPermission));
			ipage = mfUserPermissionFeign.findByPage(ipage);
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
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			formsys0002 = formService.getFormData("sys0002");
			getFormValue(formsys0002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys0002)){
				MfUserPermission mfUserPermission = new MfUserPermission();
				setObjValue(formsys0002, mfUserPermission);
				mfUserPermissionFeign.insert(mfUserPermission);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formsys0002 = formService.getFormData("sys0002");
		getFormValue(formsys0002, getMapByJson(ajaxData));
		MfUserPermission mfUserPermissionJsp = new MfUserPermission();
		setObjValue(formsys0002, mfUserPermissionJsp);
		MfUserPermission mfUserPermission = mfUserPermissionFeign.getById(mfUserPermissionJsp);
		if(mfUserPermission!=null){
			try{
				mfUserPermission = (MfUserPermission)EntityUtil.reflectionSetVal(mfUserPermission, mfUserPermissionJsp, getMapByJson(ajaxData));
				mfUserPermissionFeign.update(mfUserPermission);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfUserPermission mfUserPermission = new MfUserPermission();
		try{
			formsys0002 = formService.getFormData("sys0002");
			getFormValue(formsys0002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys0002)){
				mfUserPermission = new MfUserPermission();
				setObjValue(formsys0002, mfUserPermission);
				mfUserPermissionFeign.update(mfUserPermission);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String,Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		formsys0002 = formService.getFormData("sys0002");
		MfUserPermission mfUserPermission = new MfUserPermission();
		mfUserPermission.setId(id);
		mfUserPermission = mfUserPermissionFeign.getById(mfUserPermission);
		getObjValue(formsys0002, mfUserPermission,formData);
		if(mfUserPermission!=null){
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfUserPermission mfUserPermission = new MfUserPermission();
		mfUserPermission.setId(id);
		try {
			mfUserPermissionFeign.delete(mfUserPermission);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		formsys0002 = formService.getFormData("sys0002");
		model.addAttribute("formsys0002", formsys0002);
		return "/component/sys/MfUserPermission_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		 formsys0002 = formService.getFormData("sys0002");
		 getFormValue(formsys0002);
		 MfUserPermission mfUserPermission = new MfUserPermission();
		 setObjValue(formsys0002, mfUserPermission);
		 mfUserPermissionFeign.insert(mfUserPermission);
		 getObjValue(formsys0002, mfUserPermission);
		 this.addActionMessage(model,"保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfUserPermission", mfUserPermission));
		 List<MfUserPermission> mfUserPermissionList = (List<MfUserPermission>)mfUserPermissionFeign.findByPage(ipage).getResult();
		model.addAttribute("mfUserPermissionList", mfUserPermissionList);
		model.addAttribute("formsys0002", formsys0002);
		 return "/component/sys/MfUserPermission_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request,response);
		 formsys0001 = formService.getFormData("sys0001");
		 getFormValue(formsys0001);
		 MfUserPermission mfUserPermission = new MfUserPermission();
		mfUserPermission.setId(id);
		 mfUserPermission = mfUserPermissionFeign.getById(mfUserPermission);
		 getObjValue(formsys0001, mfUserPermission);
		 model.addAttribute("formsys0001", formsys0001);
		return "/component/sys/MfUserPermission_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String id) throws Exception {
		ActionContext.initialize(request,
				response);
		MfUserPermission mfUserPermission = new MfUserPermission();
		mfUserPermission.setId(id);
		mfUserPermissionFeign.delete(mfUserPermission);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys0002 = formService.getFormData("sys0002");
		 getFormValue(formsys0002);
		 boolean validateFlag = this.validateFormData(formsys0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys0002 = formService.getFormData("sys0002");
		 getFormValue(formsys0002);
		 boolean validateFlag = this.validateFormData(formsys0002);
	}
	
	
	/**
	 * 方法描述： 选择组件操作员的方法
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2017-8-21 上午10:55:08
	 */
	@RequestMapping(value = "/getOpDataSourceAjax")
	@ResponseBody
	public Map<String, Object> getOpDataSourceAjax(int pageNo,String formId,String element,String ajaxData,String ifFilterFlag,String opNoType,String relNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			mfUserPermission.setFormId(formId);
			mfUserPermission.setElement(element);
			mfUserPermission.setOpNoType(opNoType);
			mfUserPermission.setRelNo(relNo);
			Map<String,Object> paramMap = new HashMap<String,Object>();
			mfUserPermission.setCurrentSessionRegNo(User.getRegNo(request));
			paramMap.put("mfUserPermission", mfUserPermission);
			paramMap.put("ajaxData", ajaxData);
			paramMap.put("ifFilterFlag", ifFilterFlag);
			ipage.setParams(this.setIpageParams("paramMap", paramMap));
			ipage = mfUserPermissionFeign.getOpDataSource(ipage);
			dataMap.put("ipage", ipage);
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
	 * 方法描述： 展示所有的追偿数据
	 * @param pageNo
	 * @param formId
	 * @param element
	 * @param ajaxData
	 * @param ifFilterFlag
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 曲植
	 * @date 2018年8月17日 下午5:21:22
	 */
	@RequestMapping(value = "/getAmountSourceAjax")
	@ResponseBody
	public Map<String, Object> getAmountSourceAjax(int pageNo,String formId,String element,String ajaxData,String ifFilterFlag,String fincId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			mfUserPermission.setFormId(formId);
			mfUserPermission.setElement(element);
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("mfUserPermission", mfUserPermission);
			paramMap.put("ajaxData", ajaxData);
			paramMap.put("ifFilterFlag", ifFilterFlag);
			paramMap.put("fincId", fincId);
			ipage.setParams(this.setIpageParams("paramMap", paramMap));
			ipage = mfUserPermissionFeign.getAmountSource(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *  方法描述： 展示本客户的在贷合同
	 * @param pageNo
	 * @param formId
	 * @param element
	 * @param ajaxData
	 * @param ifFilterFlag
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getContractLoanAjax")
	@ResponseBody
	public Map<String, Object> getContractLoanAjax(int pageNo,String formId,String element,String ajaxData,String ifFilterFlag,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			mfUserPermission.setFormId(formId);
			mfUserPermission.setElement(element);
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("mfUserPermission", mfUserPermission);
			paramMap.put("ajaxData", ajaxData);
			paramMap.put("ifFilterFlag", ifFilterFlag);
			paramMap.put("cusNo", cusNo);
			ipage.setParams(this.setIpageParams("paramMap", paramMap));
			ipage = mfUserPermissionFeign.getContractLoan(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	
	/**
	 * 方法描述： 选择组件操作员的方法
	 * @return
	 * @throws Exception
	 * String
	 */
	@RequestMapping(value = "/getSiblingOpDataSourceAjax")
	@ResponseBody
	public Map<String, Object> getSiblingOpDataSourceAjax(int pageNo,String formId,String element,String ajaxData,String ifFilterFlag,String manageOpNo1) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			mfUserPermission.setFormId(formId);
			mfUserPermission.setElement(element);
			SysUser sysUser = new SysUser();
			sysUser.setOpNo(manageOpNo1);
			sysUser = sysUserFeign.getById(sysUser);
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("mfUserPermission", mfUserPermission);
			paramMap.put("ajaxData", ajaxData);
			paramMap.put("ifFilterFlag", ifFilterFlag);
			ipage.setParams(this.setIpageParams("paramMap", paramMap));
			ipage = mfUserPermissionFeign.getSiblingOpDataSource(ipage,sysUser.getBrNo());
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 方法描述： 选择渠道商的方法
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2017-8-21 上午10:55:08
	 */
	@RequestMapping(value = "/getChannelSourceAjax")
	@ResponseBody
	public Map<String, Object> getChannelSourceAjax(int pageNo,String formId,String element,String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			mfUserPermission.setFormId(formId);
			mfUserPermission.setElement(element);
			ipage.setParams(this.setIpageParams("mfUserPermission", mfUserPermission));
			ipage.setParamsStr(ajaxData);
			ipage = mfUserPermissionFeign.getChannelSource(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 方法描述： 选择核心企业
	 * @param pageNo
	 * @param formId
	 * @param element
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年7月23日 下午9:40:55
	 */
	@RequestMapping(value = "/getCoreCompanySourceAjax")
	@ResponseBody
	public Map<String, Object> getCoreCompanySourceAjax(int pageNo,String formId,String element,String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			mfUserPermission.setFormId(formId);
			mfUserPermission.setElement(element);
			ipage.setParams(this.setIpageParams("mfUserPermission", mfUserPermission));
			ipage.setParamsStr(ajaxData);
			ipage = mfUserPermissionFeign.getCoreCompanySource(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	
	/**
	 * 方法描述： 选择组件获取共同借款人的方法
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2017-8-21 上午10:55:08
	 */
	@RequestMapping(value = "/getPerDataSourceAjax")
	@ResponseBody
	public Map<String, Object> getPerDataSourceAjax(int pageNo,String formId,String element,String ajaxData,String cusNo,String cusBaseType,String cusType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if(ajaxData != null){
				mfUserPermission.setCustomQuery(ajaxData);// 自定义查询参数赋值
			}
			mfUserPermission.setCusNo(cusNo);
			mfUserPermission.setFormId(formId);
			mfUserPermission.setElement(element);
			mfUserPermission.setCusBaseType(cusBaseType);
			mfUserPermission.setCusType(cusType);
			ipage.setParams(this.setIpageParams("mfUserPermission", mfUserPermission));
			ipage.setParamsStr(ajaxData);
			ipage = mfUserPermissionFeign.getPerDataSource(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 普通ajax获取共借人
	 * @param pageNo
	 * @param cusNo
	 * @param cusType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCoborrDataAjax")
	@ResponseBody
	public Map<String, Object> getCoborrDataAjax(int pageNo,String cusNo,String cusType,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			mfUserPermission.setCusNo(cusNo);
			mfUserPermission.setCusType(cusType);
			ipage.setParams(this.setIpageParams("mfUserPermission", mfUserPermission));
			ipage = mfUserPermissionFeign.getCoborrDataAjax(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * @Description:选择组件获取保证人的方法 
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-11-8 下午5:05:50
	 */
	@RequestMapping(value = "/getAssureDataSourceAjax")
	@ResponseBody
	public Map<String, Object> getAssureDataSourceAjax(int pageNo,String formId,String element,String ajaxData,String cusNo,String cusBaseType,String cusType, String appId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if(ajaxData != null){
				mfUserPermission.setCustomQuery(ajaxData);// 自定义查询参数赋值
			}
			mfUserPermission.setCusNo(cusNo);
			mfUserPermission.setFormId(formId);
			mfUserPermission.setElement(element);
			mfUserPermission.setCusBaseType(cusBaseType);
			mfUserPermission.setCusType(cusType);

			Map<String, Object> parmMap = new HashMap<String, Object>();
			parmMap.put("mfUserPermission", mfUserPermission);
			parmMap.put("appId", appId);// 用于排除已填加的担保人
			ipage.setParams(parmMap);

			ipage.setParamsStr(ajaxData);
			ipage = mfUserPermissionFeign.getAssureDataSource(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * @Description:选择组件获取企业客户和个人客户的方法 
	 * @return
	 * @throws Exception
	 * @author: 刘东迎
	 * @date: 2018-9-14 下午5:05:50
	 */
	@RequestMapping(value = "/getCustomerDataSourceAjax")
	@ResponseBody
	public Map<String, Object> getCustomerDataSourceAjax(int pageNo,String formId,String element,String ajaxData,String cusNo,String cusBaseType,String cusType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if(ajaxData != null){
				mfUserPermission.setCustomQuery(ajaxData);// 自定义查询参数赋值
			}
			mfUserPermission.setCusNo(cusNo);
			mfUserPermission.setFormId(formId);
			mfUserPermission.setElement(element);
			mfUserPermission.setCusBaseType(cusBaseType);
			mfUserPermission.setCusType(cusType);
			
			ipage.setParams(this.setIpageParams("mfUserPermission", mfUserPermission));
			ipage.setParamsStr(ajaxData);
			ipage = mfUserPermissionFeign.getCustomerDataSource(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * @Description:选择组件获取代偿确认
	 * @return
	 * @throws Exception
	 * @author: 刘东迎
	 * @date: 2018-9-14 下午5:05:50
	 */
	@RequestMapping(value = "/getCustomerDataSourceAjaxByOver")
	@ResponseBody
	public Map<String, Object> getCustomerDataSourceAjaxByOver(int pageNo,String formId,String element,String ajaxData,String cusNo,String cusBaseType,String cusType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if(ajaxData != null){
				mfUserPermission.setCustomQuery(ajaxData);// 自定义查询参数赋值
			}
			mfUserPermission.setCusNo(cusNo);
			mfUserPermission.setElement(element);
			mfUserPermission.setFormId(formId);
			mfUserPermission.setCusBaseType(cusBaseType);
			mfUserPermission.setCusType(cusType);

			ipage.setParams(this.setIpageParams("mfUserPermission", mfUserPermission));
			ipage.setParamsStr(ajaxData);
			ipage = mfUserPermissionFeign.getCustomerDataSourceByOver(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * @Description:选择组件获取企业客户和个人客户的方法
	 * @return
	 * @throws Exception
	 * @author: 刘东迎
	 * @date: 2018-9-14 下午5:05:50
	 */
	@RequestMapping(value = "/getCustomerDataSourceAjaxByCompensatory")
	@ResponseBody
	public Map<String, Object> getCustomerDataSourceAjaxByCompensatory(int pageNo,String formId,String element,String ajaxData,String cusNo,String cusBaseType,String cusType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if(ajaxData != null){
				mfUserPermission.setCustomQuery(ajaxData);// 自定义查询参数赋值
			}
			mfUserPermission.setCusNo(cusNo);
			mfUserPermission.setElement(element);
			mfUserPermission.setFormId(formId);
			mfUserPermission.setCusBaseType(cusBaseType);
			mfUserPermission.setCusType(cusType);
			ipage.setParams(this.setIpageParams("mfUserPermission", mfUserPermission));
			ipage.setParamsStr(ajaxData);
			ipage = mfUserPermissionFeign.getCustomerDataSourceAjaxByCompensatory(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}



	@RequestMapping(value = "/getCustomerInfoByCusTypeAjax")
	@ResponseBody
	public Map<String, Object> getCustomerInfoByCusTypeAjax(String cusType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCusType(cusType);
			List<MfCusCustomer> mfCusCustomerList = cusInterfaceFeign.getMfCusCustomerListByCusType(mfCusCustomer);
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject = new JSONObject();
			if(mfCusCustomerList != null && mfCusCustomerList.size() > 0){
				for (int i = 0; i < mfCusCustomerList.size(); i++) {
					jsonObject = new JSONObject();
					jsonObject.put("id", mfCusCustomerList.get(i).getCusNo());
					jsonObject.put("name", mfCusCustomerList.get(i).getCusName());
					jsonArray.add(jsonObject);
				}
			}
			dataMap.put("items", jsonArray.toArray());
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/getCustomerInfoAjax")
	@ResponseBody
	public Map<String, Object> getCustomerInfoAjax(int pageNo,String formId,String element,String ajaxData,String cusType,String ifFilterFlag,String cusNo,String cusBaseType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if(ajaxData != null){
				mfUserPermission.setCustomQuery(ajaxData);// 自定义查询参数赋值
			}
			mfUserPermission.setFormId(formId);
			mfUserPermission.setElement(element);
			mfUserPermission.setCusType(cusType);
			if(StringUtils.isNotEmpty(ifFilterFlag) && ifFilterFlag.equals("1") && StringUtils.isNotEmpty(cusNo)){
				mfUserPermission.setCusNo(cusNo);
			}
			mfUserPermission.setCusBaseType(cusBaseType);
			ipage.setParams(this.setIpageParams("mfUserPermission", mfUserPermission));
			ipage.setParamsStr(ajaxData);
			ipage = mfUserPermissionFeign.getCustomerInfo(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/selectCocoboList")
	public String selectCocoboList(Model model, String cusNo, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfBusApply busApply = new MfBusApply();
		busApply.setAppId(appId);
		MfBusApply mfBusApply = mfBusApplyFeign.getById(busApply);
		String coborrNo = "";
		if (mfBusApply!=null){
			coborrNo = mfBusApply.getCoborrNo();
		}
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("coborrNo", coborrNo);
		model.addAttribute("query", "");
		return "/component/app/MfCocoborr_CusList";
	}

	@ResponseBody
	@RequestMapping("/selectCocoboListAjax")
	public Map<String, Object> selectCocoboListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
													String ajaxData,String formId,String element,
													String cusNo,String cusBaseType,String cusType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if(ajaxData != null){
				mfUserPermission.setCustomQuery(ajaxData);// 自定义查询参数赋值
			}
			mfUserPermission.setCusNo(cusNo);
			mfUserPermission.setFormId(formId);
			mfUserPermission.setElement(element);
			mfUserPermission.setCusBaseType(cusBaseType);
			mfUserPermission.setCusType(cusType);
			ipage.setParams(this.setIpageParams("mfUserPermission", mfUserPermission));
			ipage.setParamsStr(ajaxData);
			ipage = mfUserPermissionFeign.getPerDataSource(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	@RequestMapping(value = "/getRelCorpInfo")
	@ResponseBody
	public Map<String, Object> getRelCorpInfo(int pageNo,String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfUserPermission mfUserPermission = new MfUserPermission();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("ajaxData", ajaxData);
			ipage.setParams(this.setIpageParams("paramMap", paramMap));
			ipage = mfUserPermissionFeign.getRelCorpInfo(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	public FormData getFormsys0002() {
		return formsys0002;
	}
	public void setFormsys0002(FormData formsys0002) {
		this.formsys0002 = formsys0002;
	}
	public FormData getFormsys0001() {
		return formsys0001;
	}
	public void setFormsys0001(FormData formsys0001) {
		this.formsys0001 = formsys0001;
	}
}
