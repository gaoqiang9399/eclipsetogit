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

import app.base.User;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCulture;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCultureFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

/**
 * Title: MfCusCultureAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 20 09:45:36 CST 2018
 **/
@Controller
@RequestMapping("/mfCusCulture")
public class MfCusCultureController extends BaseFormBean{
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired 
	private MfCusCultureFeign mfCusCultureFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired 
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	//全局变量
	private String query = "";//初始化query为空
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("query", "");
		return "/component/cus/MfCusCulture_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCulture mfCusCulture = new MfCusCulture();
		try {
			mfCusCulture.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCulture.setCriteriaList(mfCusCulture, ajaxData);//我的筛选
			//mfCusCulture.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusCulture,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusCulture", mfCusCulture));
			
			ipage = mfCusCultureFeign.findByPage(ipage);
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
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusCultureAction").getAddModel();
			}
			FormData formcusCultureDetail = formService.getFormData(formId);
			getFormValue(formcusCultureDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusCultureDetail)){
				MfCusCulture mfCusCulture = new MfCusCulture();
				setObjValue(formcusCultureDetail, mfCusCulture);
				
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusCulture.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				mfCusCulture.setCusName(mfCusCustomer.getCusName());
				
				mfCusCultureFeign.insert(mfCusCulture);
				
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusCulture.getCusNo(),
						mfCusCulture.getRelNo());
				String detailFormId = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCultureAction")
						.getShowModel();
				if (StringUtil.isEmpty(detailFormId)) {
					// logger.error("MfCusPersonIncExpeAction的detailFormId找不到");
				}
				formcusCultureDetail = formService.getFormData(detailFormId);
				getObjValue(formcusCultureDetail, mfCusCulture);
				
				request.setAttribute("ifBizManger", "3");   // 
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusCultureDetail, "propertySeeTag", "");

				dataMap.put("mfCusPersonIncExpe", mfCusCulture);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("DataFullFlag", "1");

				dataMap.put("htmlStrFlag", "1");
				dataMap.put("flag", "success");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusCultureDetail = formService.getFormData("cusCultureDetail");
		getFormValue(formcusCultureDetail, getMapByJson(ajaxData));
		MfCusCulture mfCusCultureJsp = new MfCusCulture();
		setObjValue(formcusCultureDetail, mfCusCultureJsp);
		MfCusCulture mfCusCulture = mfCusCultureFeign.getById(mfCusCultureJsp);
		if(mfCusCulture!=null){
			try{
				mfCusCulture = (MfCusCulture)EntityUtil.reflectionSetVal(mfCusCulture, mfCusCultureJsp, getMapByJson(ajaxData));
				mfCusCultureFeign.update(mfCusCulture);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCulture mfCusCulture = new MfCusCulture();
		try{
			FormData formcusCultureBase = formService.getFormData("cusCultureBase");
			getFormValue(formcusCultureBase, getMapByJson(ajaxData));
			if(this.validateFormData(formcusCultureBase)){
				mfCusCulture = new MfCusCulture();
				setObjValue(formcusCultureBase, mfCusCulture);
				mfCusCultureFeign.update(mfCusCulture);


				String formId = "";
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusCulture.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCultureAction");
				if(mfCusFormConfig == null){

				}else{
					formId = mfCusFormConfig.getShowModelDef();
				}
				FormData formcusCultureDetail = formService.getFormData(formId);
				if(formcusCultureDetail.getFormId() == null){
					//log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersBaseInfoAction表单form"+formId+".xml文件不存在");
				}
				getFormValue(formcusCultureDetail);
				getObjValue(formcusCultureDetail, mfCusCustomer);
				getObjValue(formcusCultureDetail, mfCusCulture);
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusCultureDetail,"propertySeeTag",query);

				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag","1");

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
	public Map<String, Object> getByIdAjax(String cusNo,String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusCultureDetail = formService.getFormData("cusCultureDetail");
		MfCusCulture mfCusCulture = new MfCusCulture();
		mfCusCulture.setCusNo(cusNo);
		mfCusCulture.setRelNo(relNo);
		mfCusCulture = mfCusCultureFeign.getById(mfCusCulture);
		getObjValue(formcusCultureDetail, mfCusCulture,formData);
		if(mfCusCulture!=null){
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
	public Map<String, Object> deleteAjax(String cusNo,String relNo) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCulture mfCusCulture = new MfCusCulture();
		mfCusCulture.setCusNo(cusNo);
		mfCusCulture.setRelNo(relNo);
		try {
			mfCusCultureFeign.delete(mfCusCulture);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
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
	public String input(Model model, String cusNo, String relNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusCultureBase = null;
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusCultureAction");
		String formId = mfCusFormConfig.getAddModelDef();
		if(formId == "" || formId == null) {
			formId = "cusCultureDetail";
		}
		formcusCultureBase = formService.getFormData(formId);
		
		MfCusCulture mfCusCulture = new MfCusCulture();
		mfCusCulture.setCusNo(cusNo);
		mfCusCulture.setRelNo(relNo);
		mfCusCulture.setCusName(mfCusCustomer.getCusName());
		mfCusCulture.setOpName(User.getRegName(request));
		mfCusCulture.setOpNo(User.getRegNo(request));
		getObjValue(formcusCultureBase, mfCusCulture);
		
		model.addAttribute("formcusCultureBase", formcusCultureBase);
		model.addAttribute("relNo", relNo);
		model.addAttribute("query", "");
		return "/component/cus/MfCusCulture_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String cusNo,String relNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcusCultureBase = formService.getFormData("cusCultureBase");
		getFormValue(formcusCultureBase);
		MfCusCulture  mfCusCulture = new MfCusCulture();
		mfCusCulture.setCusNo(cusNo);
		mfCusCulture.setRelNo(relNo);
		mfCusCulture = mfCusCultureFeign.getById(mfCusCulture);
		getObjValue(formcusCultureBase, mfCusCulture);
		model.addAttribute("formcusCultureBase", formcusCultureBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusCulture_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception{
		 FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		 FormData formcusCultureDetail = formService.getFormData("cusCultureDetail");
		 getFormValue(formcusCultureDetail);
		 boolean validateFlag = this.validateFormData(formcusCultureDetail);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception{
		 FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		 FormData formcusCultureDetail = formService.getFormData("cusCultureDetail");
		 getFormValue(formcusCultureDetail);
		 boolean validateFlag = this.validateFormData(formcusCultureDetail);
	}
	
	
	
}
