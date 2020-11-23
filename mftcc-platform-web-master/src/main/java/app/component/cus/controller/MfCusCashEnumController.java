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
import app.component.cus.entity.MfCusCashEnum;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCashEnumFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfCusCashEnumAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 04 09:13:34 CST 2016
 **/
@Controller
@RequestMapping("/mfCusCashEnum")
public class MfCusCashEnumController extends BaseFormBean{
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired 
	private MfCusCashEnumFeign mfCusCashEnumFeign;
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
		return "/component/cus/MfCusCashEnum_List";
	}
	/**
	 * 
	 * 方法描述： 获得企业客户现金流量信息列表
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2016-6-4 上午9:54:50
	 */
	@RequestMapping(value = "/getListPageTmp")
	public String getListPageTmp(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			FormData formcuscash00001 = formService.getFormData("cuscash00001");
			MfCusCashEnum mfCusCashEnum = new MfCusCashEnum();
			mfCusCashEnum.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfCusCashEnum", mfCusCashEnum));
			List<MfCusCashEnum> mfCusCashEnumList = (List<MfCusCashEnum>)mfCusCashEnumFeign.findByPage(ipage).getResult();
			model.addAttribute("mfCusCashEnumList", mfCusCashEnumList);
			model.addAttribute("formcuscash00001", formcuscash00001);
			model.addAttribute("query", "");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return "/component/cus/MfCusCashEnum_ListTmp";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusCashEnum  mfCusCashEnum = new MfCusCashEnum();
		try {
			mfCusCashEnum.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCashEnum.setCriteriaList(mfCusCashEnum, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusCashEnum,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusCashEnum", mfCusCashEnum));
			ipage = mfCusCashEnumFeign.findByPage(ipage);
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
	public Map<String,Object> insertAjax(String ajaxData,String cusNo,String tableId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formcuscash00001 = formService.getFormData("cuscash00001");
			getFormValue(formcuscash00001, getMapByJson(ajaxData));
			if(this.validateFormData(formcuscash00001)){
				MfCusCashEnum mfCusCashEnum = new MfCusCashEnum();
				setObjValue(formcuscash00001, mfCusCashEnum);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusCashEnum.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusCashEnum.setCusName(cusName);
				mfCusCashEnumFeign.insert(mfCusCashEnum);
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
		FormData formcuscash00002 = formService.getFormData("cuscash00002");
		getFormValue(formcuscash00002, getMapByJson(ajaxData));
		MfCusCashEnum mfCusCashEnumJsp = new MfCusCashEnum();
		setObjValue(formcuscash00002, mfCusCashEnumJsp);
		MfCusCashEnum mfCusCashEnum = mfCusCashEnumFeign.getById(mfCusCashEnumJsp);
		if(mfCusCashEnum!=null){
			try{
				mfCusCashEnum = (MfCusCashEnum)EntityUtil.reflectionSetVal(mfCusCashEnum, mfCusCashEnumJsp, getMapByJson(ajaxData));
				mfCusCashEnumFeign.update(mfCusCashEnum);
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
			FormData formcuscash00001 = formService.getFormData("cuscash00001");
			getFormValue(formcuscash00001, getMapByJson(ajaxData));
			if(this.validateFormData(formcuscash00001)){
				MfCusCashEnum mfCusCashEnum = new MfCusCashEnum();
				setObjValue(formcuscash00001, mfCusCashEnum);
				mfCusCashEnumFeign.update(mfCusCashEnum);
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
	public Map<String,Object> getByIdAjax(String cashEnumId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formcuscash00002 = formService.getFormData("cuscash00002");
		MfCusCashEnum mfCusCashEnum = new MfCusCashEnum();
		mfCusCashEnum.setCashEnumId(cashEnumId);
		mfCusCashEnum = mfCusCashEnumFeign.getById(mfCusCashEnum);
		getObjValue(formcuscash00002, mfCusCashEnum,formData);
		if(mfCusCashEnum!=null){
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
	public Map<String,Object> deleteAjax(String cashEnumId,String ajaxData,String cusNo,String tableId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusCashEnum mfCusCashEnum = new MfCusCashEnum();
		mfCusCashEnum.setCashEnumId(cashEnumId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfCusCashEnum = (MfCusCashEnum)JSONObject.toBean(jb, MfCusCashEnum.class);
			mfCusCashEnumFeign.delete(mfCusCashEnum);
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
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formcuscash00002 = formService.getFormData("cuscash00002");
		model.addAttribute("formcuscash00002", formcuscash00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusCashEnum_Insert";
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
		FormData formcuscash00002 = formService.getFormData("cuscash00002");
		 getFormValue(formcuscash00002);
		 MfCusCashEnum mfCusCashEnum = new MfCusCashEnum();
		 setObjValue(formcuscash00002, mfCusCashEnum);
		 mfCusCashEnumFeign.insert(mfCusCashEnum);
		 getObjValue(formcuscash00002, mfCusCashEnum);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfCusCashEnum", mfCusCashEnum));
		 List<MfCusCashEnum> mfCusCashEnumList = (List<MfCusCashEnum>)mfCusCashEnumFeign.findByPage(ipage).getResult();
		 model.addAttribute("mfCusCashEnumList",mfCusCashEnumList); 
		 model.addAttribute("formcuscash00002",formcuscash00002); 
		 model.addAttribute("query", "");
		return "/component/cus/MfCusCashEnum_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String cashEnumId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formcuscash00001 = formService.getFormData("cuscash00001");
		 getFormValue(formcuscash00001);
		 MfCusCashEnum mfCusCashEnum = new MfCusCashEnum();
		 mfCusCashEnum.setCashEnumId(cashEnumId);
		 mfCusCashEnum = mfCusCashEnumFeign.getById(mfCusCashEnum);
		 getObjValue(formcuscash00001, mfCusCashEnum);
		 model.addAttribute("formcuscash00001", formcuscash00001);
		 model.addAttribute("query","");
		return "/component/cus/MfCusCashEnum_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model,String cashEnumId) throws Exception {
		ActionContext.initialize(request,response);
		MfCusCashEnum mfCusCashEnum = new MfCusCashEnum();
		mfCusCashEnum.setCashEnumId(cashEnumId);
		mfCusCashEnumFeign.delete(mfCusCashEnum);
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
		 FormData formcuscash00002 = formService.getFormData("cuscash00002");
		 getFormValue(formcuscash00002);
		 boolean validateFlag = this.validateFormData(formcuscash00002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcuscash00002 = formService.getFormData("cuscash00002");
		 getFormValue(formcuscash00002);
		 boolean validateFlag = this.validateFormData(formcuscash00002);
	}
	private Map<String,Object> getTableData(String cusNo,String tableId) throws Exception {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		JsonTableUtil jtu = new JsonTableUtil();
		MfCusCashEnum mfCusCashEnum = new MfCusCashEnum();
		mfCusCashEnum.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusCashEnum", mfCusCashEnum));
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", (List<MfCusCashEnum>)mfCusCashEnumFeign.findByPage(ipage).getResult(), null,true);
		dataMap.put("tableData",tableHtml);
		return dataMap;
	}
}
