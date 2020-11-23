package  app.component.cus.controller;
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

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCapitalInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCapitalInfoFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfCusCapitalInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 03 16:21:44 CST 2016
 **/
@Controller
@RequestMapping("/mfCusCapitalInfo")
public class MfCusCapitalInfoController extends BaseFormBean{
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired 
	private MfCusCapitalInfoFeign mfCusCapitalInfoFeign;
	@Autowired 
	private MfCusCustomerFeign mfCusCustomerFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		return "/component/cus/MfCusCapitalInfo_List";
	}
	/**
	 * 
	 * 方法描述： 获得列表
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2016-6-3 下午5:12:36
	 */
	@RequestMapping(value = "/getListPageTmp")
	public String getListPageTmp(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			FormData formcuscapi00001 = formService.getFormData("cuscapi00001");
			MfCusCapitalInfo mfCusCapitalInfo = new MfCusCapitalInfo();
			mfCusCapitalInfo.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfCusCapitalInfo", mfCusCapitalInfo));
			List<MfCusCapitalInfo> mfCusCapitalInfoList = (List<MfCusCapitalInfo>)mfCusCapitalInfoFeign.findByPage(ipage).getResult();
			model.addAttribute("mfCusCapitalInfoList", mfCusCapitalInfoList);
			model.addAttribute("formcuscapi00001", formcuscapi00001);
			model.addAttribute("query", "");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return "/component/cus/MfCusCapitalInfo_ListTmp";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusCapitalInfo  mfCusCapitalInfo = new MfCusCapitalInfo();
		try {
			mfCusCapitalInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCapitalInfo.setCriteriaList(mfCusCapitalInfo, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusCapitalInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusCapitalInfo", mfCusCapitalInfo));
			ipage = mfCusCapitalInfoFeign.findByPage(ipage);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String,Object>  insertAjax(String ajaxData,String cusNo,String tableId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formcuscapi00001 = formService.getFormData("cuscapi00001");
			getFormValue(formcuscapi00001, getMapByJson(ajaxData));
			if(this.validateFormData(formcuscapi00001)){	
				MfCusCapitalInfo mfCusCapitalInfo = new MfCusCapitalInfo();
				setObjValue(formcuscapi00001, mfCusCapitalInfo);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusCapitalInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusCapitalInfo.setCusName(cusName);
				mfCusCapitalInfoFeign.insert(mfCusCapitalInfo);
				dataMap=getTableData(cusNo,tableId);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
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
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		FormData formcuscapi00002 = formService.getFormData("cuscapi00002");
		getFormValue(formcuscapi00002, getMapByJson(ajaxData));
		MfCusCapitalInfo mfCusCapitalInfoJsp = new MfCusCapitalInfo();
		setObjValue(formcuscapi00002, mfCusCapitalInfoJsp);
		MfCusCapitalInfo mfCusCapitalInfo = mfCusCapitalInfoFeign.getById(mfCusCapitalInfoJsp);
		if(mfCusCapitalInfo!=null){
			try{
				mfCusCapitalInfo = (MfCusCapitalInfo)EntityUtil.reflectionSetVal(mfCusCapitalInfo, mfCusCapitalInfoJsp, getMapByJson(ajaxData));
				mfCusCapitalInfoFeign.update(mfCusCapitalInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
	public Map<String,Object> updateAjax(String ajaxData,String cusNo,String tableId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formcuscapi00002 = formService.getFormData("cuscapi00002");
			getFormValue(formcuscapi00002, getMapByJson(ajaxData));
			if(this.validateFormData(formcuscapi00002)){
				MfCusCapitalInfo mfCusCapitalInfo = new MfCusCapitalInfo();
				setObjValue(formcuscapi00002, mfCusCapitalInfo);
				mfCusCapitalInfoFeign.update(mfCusCapitalInfo);
				dataMap=getTableData(cusNo,tableId);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
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
	public Map<String,Object> getByIdAjax(String capitalInfoId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formcuscapi00002 = formService.getFormData("cuscapi00002");
		MfCusCapitalInfo mfCusCapitalInfo = new MfCusCapitalInfo();
		mfCusCapitalInfo.setCapitalInfoId(capitalInfoId);
		mfCusCapitalInfo = mfCusCapitalInfoFeign.getById(mfCusCapitalInfo);
		getObjValue(formcuscapi00002, mfCusCapitalInfo,formData);
		if(mfCusCapitalInfo!=null){
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
	public Map<String,Object> deleteAjax(String capitalInfoId,String ajaxData,String cusNo,String tableId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusCapitalInfo mfCusCapitalInfo = new MfCusCapitalInfo();
		mfCusCapitalInfo.setCapitalInfoId(capitalInfoId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfCusCapitalInfo = (MfCusCapitalInfo)JSONObject.toBean(jb, MfCusCapitalInfo.class);
			mfCusCapitalInfoFeign.delete(mfCusCapitalInfo);
			dataMap=getTableData(cusNo,tableId);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
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
		FormService formService = new FormService();
		FormData formcuscapi00002 = formService.getFormData("cuscapi00002");
		return "/component/cus/MfCusCapitalInfo_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formcuscapi00002 = formService.getFormData("cuscapi00002");
		 getFormValue(formcuscapi00002);
		 MfCusCapitalInfo mfCusCapitalInfo = new MfCusCapitalInfo();
		 setObjValue(formcuscapi00002, mfCusCapitalInfo);
		 mfCusCapitalInfoFeign.insert(mfCusCapitalInfo);
		 getObjValue(formcuscapi00002, mfCusCapitalInfo);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfCusCapitalInfo", mfCusCapitalInfo));
		 List<MfCusCapitalInfo> mfCusCapitalInfoList = (List<MfCusCapitalInfo>)mfCusCapitalInfoFeign.findByPage(ipage).getResult();
		 model.addAttribute("formcuscapi00002", formcuscapi00002);
		 model.addAttribute("mfCusCapitalInfoList", mfCusCapitalInfoList);
		 model.addAttribute("query", "");
		return "/component/cus/MfCusCapitalInfo_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String capitalInfoId) throws Exception{
		 ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		 FormData formcuscapi00001 = formService.getFormData("cuscapi00001");
		 getFormValue(formcuscapi00001);
		 MfCusCapitalInfo mfCusCapitalInfo = new MfCusCapitalInfo();
		 mfCusCapitalInfo.setCapitalInfoId(capitalInfoId);
		 mfCusCapitalInfo = mfCusCapitalInfoFeign.getById(mfCusCapitalInfo);
		 getObjValue(formcuscapi00001, mfCusCapitalInfo);
		 model.addAttribute("formcuscapi00001", "");
		 model.addAttribute("query", "");
		return "/component/cus/MfCusCapitalInfo_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model,String capitalInfoId) throws Exception {
		ActionContext.initialize(request,response);
		MfCusCapitalInfo mfCusCapitalInfo = new MfCusCapitalInfo();
		mfCusCapitalInfo.setCapitalInfoId(capitalInfoId);
		mfCusCapitalInfoFeign.delete(mfCusCapitalInfo);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcuscapi00002 = formService.getFormData("cuscapi00002");
		 getFormValue(formcuscapi00002);
		 boolean validateFlag = this.validateFormData(formcuscapi00002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcuscapi00002 = formService.getFormData("cuscapi00002");
		 getFormValue(formcuscapi00002);
		 boolean validateFlag = this.validateFormData(formcuscapi00002);
	}
	
	private Map<String, Object> getTableData(String cusNo,String tableId) throws Exception {
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		JsonTableUtil jtu = new JsonTableUtil();
		MfCusCapitalInfo mfCusCapitalInfo = new MfCusCapitalInfo();
		mfCusCapitalInfo.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfCusCapitalInfo", mfCusCapitalInfo));
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", (List<MfCusCapitalInfo>)mfCusCapitalInfoFeign.findByPage(ipage).getResult(), null,true);
		dataMap.put("tableData",tableHtml);
		return dataMap;
	}
}
