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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusReputationInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusReputationInfoFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfCusReputationInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 18 10:27:55 CST 2017
 **/
@Controller
@RequestMapping("/mfCusReputationInfo")
public class MfCusReputationInfoController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusReputationInfoBo
	@Autowired
	private MfCusReputationInfoFeign mfCusReputationInfoFeign;
	//全局变量
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	//全局变量
	private String query = "";//初始化query为空
	//异步参数
	//表单变量
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusReputationInfo_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusReputationInfo mfCusReputationInfo = new MfCusReputationInfo();
		try {
			mfCusReputationInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusReputationInfo.setCriteriaList(mfCusReputationInfo, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusReputationInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusReputationInfo", mfCusReputationInfo));
			ipage = mfCusReputationInfoFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData,String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusReputationInfoAction").getAddModel();
			}
		FormData 	formcusReputationBase = formService.getFormData(formId);
			getFormValue(formcusReputationBase, getMapByJson(ajaxData));
			if(this.validateFormData(formcusReputationBase)){
		MfCusReputationInfo mfCusReputationInfo = new MfCusReputationInfo();
				setObjValue(formcusReputationBase, mfCusReputationInfo);
				
				mfCusReputationInfoFeign.insert(mfCusReputationInfo);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusReputationInfo.getCusNo(), mfCusReputationInfo.getRelNo());//更新资料完整度
		FormData formcusReputationDetail = formService.getFormData("cusReputationDetail");
				getObjValue(formcusReputationDetail, mfCusReputationInfo);
				
				this.getHttpRequest().setAttribute("ifBizManger","3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusReputationDetail,"propertySeeTag",query);
				
				dataMap.put("mfCusReputationInfo",mfCusReputationInfo);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("infIntegrity",infIntegrity);
				dataMap.put("DataFullFlag", "1");
				
				dataMap.put("htmlStrFlag","1");	
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
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusReputationDetail = formService.getFormData("cusReputationDetail");
		getFormValue(formcusReputationDetail, getMapByJson(ajaxData));
		MfCusReputationInfo mfCusReputationInfoJsp = new MfCusReputationInfo();
		setObjValue(formcusReputationDetail, mfCusReputationInfoJsp);
		MfCusReputationInfo mfCusReputationInfo = mfCusReputationInfoFeign.getById(mfCusReputationInfoJsp);
		if(mfCusReputationInfo!=null){
			try{
				mfCusReputationInfo = (MfCusReputationInfo)EntityUtil.reflectionSetVal(mfCusReputationInfo, mfCusReputationInfoJsp, getMapByJson(ajaxData));
				mfCusReputationInfoFeign.update(mfCusReputationInfo);
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
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusReputationInfo mfCusReputationInfo = new MfCusReputationInfo();
		try{
		FormData 	formcusReputationDetail = formService.getFormData("cusReputationDetail");
			getFormValue(formcusReputationDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcusReputationDetail)){
				setObjValue(formcusReputationDetail, mfCusReputationInfo);
				mfCusReputationInfoFeign.update(mfCusReputationInfo);

				String formId = "";
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusReputationInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusReputationInfoAction");
				if(mfCusFormConfig == null){

				}else{
					formId = mfCusFormConfig.getShowModelDef();
				}

				if(formcusReputationDetail.getFormId() == null){
					//log.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersBaseInfoAction表单form"+formId+".xml文件不存在");
				}
				getFormValue(formcusReputationDetail);
				getObjValue(formcusReputationDetail, mfCusCustomer);
				getObjValue(formcusReputationDetail, mfCusReputationInfo);
				this.getHttpRequest().setAttribute("ifBizManger", "3");
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcusReputationDetail,"propertySeeTag",query);

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
	public Map<String, Object> getByIdAjax(String reputationId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusReputationDetail = formService.getFormData("cusReputationDetail");
		MfCusReputationInfo mfCusReputationInfo = new MfCusReputationInfo();
		mfCusReputationInfo.setReputationId(reputationId);
		mfCusReputationInfo = mfCusReputationInfoFeign.getById(mfCusReputationInfo);
		getObjValue(formcusReputationDetail, mfCusReputationInfo,formData);
		if(mfCusReputationInfo!=null){
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
	public Map<String, Object> deleteAjax(String reputationId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusReputationInfo mfCusReputationInfo = new MfCusReputationInfo();
		mfCusReputationInfo.setReputationId(reputationId);
		try {
			mfCusReputationInfoFeign.delete(mfCusReputationInfo);
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
	 * 
	 * 方法描述： 客户视角登记客户表单信息
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-10-14 下午5:07:23
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String cusNo,String relNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		FormData 	formcusReputationBase=null;
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusReputationInfoAction");
		String formId="";
		if(mfCusFormConfig == null){
			
		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
			//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusReputationInfoAction表单信息没有查询到");
		}else{
		 	formcusReputationBase = formService.getFormData(formId);
			if(formcusReputationBase.getFormId() == null){
				//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusReputationInfoAction表单form"+formId+".xml文件不存在");
			}else{
		MfCusReputationInfo mfCusReputationInfo = new MfCusReputationInfo();
				mfCusReputationInfo.setCusNo(cusNo);
				mfCusReputationInfo.setRelNo(relNo);
				mfCusReputationInfo.setCusName(mfCusCustomer.getCusName());
				getFormValue(formcusReputationBase);
				getObjValue(formcusReputationBase, mfCusCustomer);
				getObjValue(formcusReputationBase, mfCusReputationInfo);
			}
		}
		model.addAttribute("formcusReputationBase", formcusReputationBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusReputationInfo_Insert";
	}
	/**
	 * 
	 * 方法描述： 业务视角登记客户表单信息
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-10-14 下午5:07:23
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo,String relNo,String kindNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		FormData 	formcusReputationBase=null;
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusReputationInfoAction");
		String formId="";
		if(mfCusFormConfig == null){
			
		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
			//			logger.error("产品类型为"+kindNo+"的MfCusReputationInfoAction表单信息没有查询到");
		}else{
		 	formcusReputationBase = formService.getFormData(formId);
			if(formcusReputationBase.getFormId() == null){
				//			logger.error("产品类型为"+kindNo+"的MfCusReputationInfoAction表单form"+formId+".xml文件不存在");
			}else{
		MfCusReputationInfo mfCusReputationInfo = new MfCusReputationInfo();
				mfCusReputationInfo.setCusNo(cusNo);
				mfCusReputationInfo.setRelNo(relNo);
				mfCusReputationInfo.setCusName(mfCusCustomer.getCusName());
				getFormValue(formcusReputationBase);
				getObjValue(formcusReputationBase, mfCusCustomer);
				getObjValue(formcusReputationBase, mfCusReputationInfo);
			}
		}
		model.addAttribute("formcusReputationBase", formcusReputationBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusReputationInfo_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcusReputationDetail = formService.getFormData("cusReputationDetail");
		 getFormValue(formcusReputationDetail);
		MfCusReputationInfo  mfCusReputationInfo = new MfCusReputationInfo();
		 setObjValue(formcusReputationDetail, mfCusReputationInfo);
		 mfCusReputationInfoFeign.insert(mfCusReputationInfo);
		 getObjValue(formcusReputationDetail, mfCusReputationInfo);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage =this.getIpage();
		 ipage.setParams(this.setIpageParams("mfCusReputationInfo", mfCusReputationInfo));
		 List<MfCusReputationInfo> mfCusReputationInfoList = (List<MfCusReputationInfo>)mfCusReputationInfoFeign.findByPage(ipage).getResult();
		model.addAttribute("formcusReputationDetail", formcusReputationDetail);
		model.addAttribute("mfCusReputationInfoList", mfCusReputationInfoList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusReputationInfo_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String cusNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcusReputationBase = formService.getFormData("cusReputationBase");
		 getFormValue(formcusReputationBase);
		MfCusReputationInfo  mfCusReputationInfo = new MfCusReputationInfo();
		mfCusReputationInfo.setCusNo(cusNo);
		 mfCusReputationInfo = mfCusReputationInfoFeign.getById(mfCusReputationInfo);
			 getObjValue(formcusReputationBase, mfCusReputationInfo);
		model.addAttribute("formcusReputationBase", formcusReputationBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusReputationInfo_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String reputationId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfCusReputationInfo mfCusReputationInfo = new MfCusReputationInfo();
		mfCusReputationInfo.setReputationId(reputationId);
		mfCusReputationInfoFeign.delete(mfCusReputationInfo);
		return getListPage(model);
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
		FormData  formcusReputationDetail = formService.getFormData("cusReputationDetail");
		 getFormValue(formcusReputationDetail);
		 boolean validateFlag = this.validateFormData(formcusReputationDetail);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formcusReputationDetail = formService.getFormData("cusReputationDetail");
		 getFormValue(formcusReputationDetail);
		 boolean validateFlag = this.validateFormData(formcusReputationDetail);
	}
	
	
}
