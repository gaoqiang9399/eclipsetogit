package  app.component.cus.controller;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusPersonLiabilities;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.feign.MfCusPersonLiabilitiesFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONObject;
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
 * Title: MfCusPersonLiabilitiesAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Aug 05 16:03:23 CST 2017
 **/
@Controller
@RequestMapping("/mfCusPersonLiabilities")
public class MfCusPersonLiabilitiesController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusPersonLiabilitiesBo
	@Autowired
	private MfCusPersonLiabilitiesFeign mfCusPersonLiabilitiesFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	//全局变量
	//异步参数
	//表单变量
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusProduct_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
		try {
			mfCusPersonLiabilities.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusPersonLiabilities.setCriteriaList(mfCusPersonLiabilities, ajaxData);//我的筛选
			//mfCusPersonLiabilities.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusPersonLiabilities,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusPersonLiabilities", mfCusPersonLiabilities));
			ipage = mfCusPersonLiabilitiesFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
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
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			Map<String , Object> map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusPersonLiabilitiesAction").getAddModel();
			}
		FormData 	formcusliabilitiesBase = formService.getFormData(formId);
			getFormValue(formcusliabilitiesBase, getMapByJson(ajaxData));
			if(this.validateFormData(formcusliabilitiesBase)){
		MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
				setObjValue(formcusliabilitiesBase, mfCusPersonLiabilities);
				
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusPersonLiabilities.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				mfCusPersonLiabilities.setCusName(mfCusCustomer.getCusName());
				
				mfCusPersonLiabilitiesFeign.insert(mfCusPersonLiabilities);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusPersonLiabilities.getCusNo(),mfCusPersonLiabilities.getCusNo());//更新客户信息完整度
				String relNo = mfCusPersonLiabilities.getRelNo();
				mfCusPersonLiabilities.setCusNo(mfCusCustomer.getCusNo());
				mfCusPersonLiabilities.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusPersonLiabilities", mfCusPersonLiabilities));
				String  tableHtml = jtu.getJsonStr("tablecusliabilitiesBase","tableTag", (List<MfCusPersonLiabilities>)mfCusPersonLiabilitiesFeign.findByPage(ipage).getResult(), null,true);
				dataMap.put("mfCusCustomer", mfCusCustomer);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");
				dataMap.put("infIntegrity",infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/autoSaveZiChan")
	@ResponseBody
	public Map<String, Object> autoSaveZiChan(String appId,String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			MfCusPersonLiabilities mfCusPersonLiabilitiesQuery = new MfCusPersonLiabilities();
			mfCusPersonLiabilitiesQuery.setAppId(appId);
			mfCusPersonLiabilitiesQuery.setCusNo(cusNo);
			mfCusPersonLiabilitiesQuery.setOpNo(User.getRegNo(request));
			mfCusPersonLiabilitiesFeign.delete(mfCusPersonLiabilitiesQuery);
			MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();

			mfCusPersonLiabilities.setCusNo(mfCusCustomer.getCusNo());
			mfCusPersonLiabilities.setOpNo(User.getRegNo(request));
			mfCusPersonLiabilities.setCusName(mfCusCustomer.getCusName());
			mfCusPersonLiabilities.setAppId(appId);
			mfCusPersonLiabilities.setRelNo(mfCusCustomer.getCusNo());

			mfCusPersonLiabilities = mfCusPersonLiabilitiesFeign.autoCalc(mfCusPersonLiabilities);
			mfCusPersonLiabilitiesFeign.insert(mfCusPersonLiabilities);
			String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusPersonLiabilities.getCusNo(),mfCusPersonLiabilities.getCusNo());//更新客户信息完整度
			String relNo = mfCusPersonLiabilities.getRelNo();
			mfCusPersonLiabilities.setCusNo(mfCusCustomer.getCusNo());
			mfCusPersonLiabilities.setRelNo(relNo);
			Ipage ipage = this.getIpage();
			JsonTableUtil jtu = new JsonTableUtil();
			ipage.setParams(this.setIpageParams("mfCusPersonLiabilities", mfCusPersonLiabilities));
			String  tableHtml = jtu.getJsonStr("tablecusliabilitiesBase","tableTag", (List<MfCusPersonLiabilities>)mfCusPersonLiabilitiesFeign.findByPage(ipage).getResult(), null,true);
			dataMap.put("mfCusCustomer", mfCusCustomer);
			dataMap.put("htmlStr", tableHtml);
			dataMap.put("htmlStrFlag","1");
			dataMap.put("infIntegrity",infIntegrity);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
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
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcusliabilitiesBase = formService.getFormData("cusliabilitiesBase");
		getFormValue(formcusliabilitiesBase, getMapByJson(ajaxData));
		MfCusPersonLiabilities mfCusPersonLiabilitiesJsp = new MfCusPersonLiabilities();
		setObjValue(formcusliabilitiesBase, mfCusPersonLiabilitiesJsp);
		MfCusPersonLiabilities mfCusPersonLiabilities = mfCusPersonLiabilitiesFeign.getById(mfCusPersonLiabilitiesJsp);
		if(mfCusPersonLiabilities!=null){
			try{
				mfCusPersonLiabilities = (MfCusPersonLiabilities)EntityUtil.reflectionSetVal(mfCusPersonLiabilities, mfCusPersonLiabilitiesJsp, getMapByJson(ajaxData));
				mfCusPersonLiabilitiesFeign.update(mfCusPersonLiabilities);
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
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Map<String , Object> map = getMapByJson(ajaxData);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo((String)map.get("cusNo"));
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersonLiabilitiesAction");
		String formId="";
		if(mfCusFormConfig == null){
			
		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
			//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonLiabilitiesAction表单信息没有查询到");
		}else{
			try{
		FormData formcusliabilitiesBase = formService.getFormData(formId);
				getFormValue(formcusliabilitiesBase, getMapByJson(ajaxData));
				if(this.validateFormData(formcusliabilitiesBase)){
		MfCusPersonLiabilities 	mfCusPersonLiabilities = new MfCusPersonLiabilities();
					setObjValue(formcusliabilitiesBase, mfCusPersonLiabilities);
					mfCusPersonLiabilitiesFeign.update(mfCusPersonLiabilities);
					String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusPersonLiabilities.getCusNo(),mfCusPersonLiabilities.getCusNo());//更新客户信息完整度
					mfCusPersonLiabilities.setCusNo(mfCusCustomer.getCusNo());
					Ipage ipage = this.getIpage();
					JsonTableUtil jtu = new JsonTableUtil();
					ipage.setParams(this.setIpageParams("mfCusPersonLiabilities", mfCusPersonLiabilities));
					String  tableHtml = jtu.getJsonStr("tablecusliabilitiesBase","tableTag", (List<MfCusPersonLiabilities>)mfCusPersonLiabilitiesFeign.findByPage(ipage).getResult(), null,true);
					dataMap.put("mfCusCustomer", mfCusCustomer);
					dataMap.put("htmlStr", tableHtml);
					dataMap.put("htmlStrFlag","1");
					dataMap.put("infIntegrity",infIntegrity);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg",this.getFormulavaliErrorMsg());
				}
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
				throw new Exception(e.getMessage());
			}
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
	public Map<String, Object> getByIdAjax(String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusliabilitiesBase = formService.getFormData("cusliabilitiesBase");
		MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
		mfCusPersonLiabilities.setCusNo(cusNo);
		mfCusPersonLiabilities = mfCusPersonLiabilitiesFeign.getById(mfCusPersonLiabilities);
		getObjValue(formcusliabilitiesBase, mfCusPersonLiabilities,formData);
		if(mfCusPersonLiabilities!=null){
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
	public Map<String, Object> deleteAjax(String cusNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
		mfCusPersonLiabilities.setCusNo(cusNo);
		try {
			mfCusPersonLiabilitiesFeign.delete(mfCusPersonLiabilities);
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
	public String input(Model model,String cusNo,String relNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData 	formcusliabilitiesBase = formService.getFormData("cusliabilitiesBase");
		MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
		mfCusPersonLiabilities.setCreaterNo(User.getRegNo(request));
		mfCusPersonLiabilities.setCreaterName(User.getRegName(request));
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfCusPersonLiabilities.setCusNo(cusNo);
		mfCusPersonLiabilities.setRelNo(relNo);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersonLiabilitiesAction");
		String formId="";
		if(mfCusFormConfig == null){
			
		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
			//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonLiabilitiesAction表单信息没有查询到");
		}else{
		 	formcusliabilitiesBase = formService.getFormData(formId);
			if(formcusliabilitiesBase.getFormId() == null){
				//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonLiabilitiesAction表单form"+formId+".xml文件不存在");
			}else{
				getFormValue(formcusliabilitiesBase);
				mfCusPersonLiabilities.setCusName(mfCusCustomer.getCusName());
				getObjValue(formcusliabilitiesBase, mfCusPersonLiabilities);
			}
		}
		model.addAttribute("formcusliabilitiesBase", formcusliabilitiesBase);
		model.addAttribute("query", "");
        model.addAttribute("mfCusPersonLiabilities", mfCusPersonLiabilities);
		return "/component/cus/MfCusPersonLiabilities_Insert";
	}
	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-9-26 下午2:13:49
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model, String cusNo,String relNo,String kindNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData 	formcusliabilitiesBase=null;
		MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
		mfCusPersonLiabilities.setCreaterNo(User.getRegNo(request));
		mfCusPersonLiabilities.setCreaterName(User.getRegName(request));
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfCusPersonLiabilities.setCusNo(cusNo);
		mfCusPersonLiabilities.setRelNo(relNo);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusPersonLiabilitiesAction");
		String formId="";
		if(mfCusFormConfig == null){
			
		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
			//			logger.error("产品类型为"+kindNo+"的MfCusPersonLiabilitiesAction表单信息没有查询到");
		}else{
		 	formcusliabilitiesBase = formService.getFormData(formId);
			if(formcusliabilitiesBase.getFormId() == null){
				//			logger.error("产品类型为"+kindNo+"的MfCusPersonLiabilitiesAction表单form"+formId+".xml文件不存在");
			}else{
				getFormValue(formcusliabilitiesBase);
				mfCusPersonLiabilities.setCusName(mfCusCustomer.getCusName());
				mfCusPersonLiabilities.setCreaterName(mfCusCustomer.getCusName());
				getObjValue(formcusliabilitiesBase, mfCusPersonLiabilities);
			}
		}
		model.addAttribute("formcusliabilitiesBase", formcusliabilitiesBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonLiabilities_Insert";
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
		FormData  formcusliabilitiesBase = formService.getFormData("cusliabilitiesBase");
		 getFormValue(formcusliabilitiesBase);
		MfCusPersonLiabilities  mfCusPersonLiabilities = new MfCusPersonLiabilities();
		 setObjValue(formcusliabilitiesBase, mfCusPersonLiabilities);
		 mfCusPersonLiabilitiesFeign.insert(mfCusPersonLiabilities);
		 getObjValue(formcusliabilitiesBase, mfCusPersonLiabilities);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfCusPersonLiabilities", mfCusPersonLiabilities));
		 List<MfCusPersonLiabilities> mfCusPersonLiabilitiesList = (List<MfCusPersonLiabilities>)mfCusPersonLiabilitiesFeign.findByPage(ipage).getResult();
		model.addAttribute("formcusliabilitiesBase", formcusliabilitiesBase);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonLiabilities_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String cusNo,String id) throws Exception{
		
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfCusPersonLiabilities.setCusNo(cusNo);
		mfCusPersonLiabilities.setId(id);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersonLiabilitiesAction");
		String formId="";
		FormData  formcusliabilitiesDetail=null;
		if(mfCusFormConfig == null){
			
		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
			//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonLiabilitiesAction表单信息没有查询到");
		}else{
		FormData 	formcusliabilitiesBase = formService.getFormData(formId);
			if(formcusliabilitiesBase.getFormId() == null){
				//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusPersonLiabilitiesAction表单form"+formId+".xml文件不存在");
			}else{
				formcusliabilitiesDetail = formService.getFormData(formId);
				getFormValue(formcusliabilitiesDetail);
				mfCusPersonLiabilities.setCusNo(cusNo);
				mfCusPersonLiabilities = mfCusPersonLiabilitiesFeign.getById(mfCusPersonLiabilities);
				getObjValue(formcusliabilitiesDetail, mfCusPersonLiabilities);
				/*MfCusOutProject mfCusOutProject = new MfCusOutProject();
				mfCusOutProject.setCusNo(cusNo);
				mfCusOutProject.setDelFlag("0");
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusOutProject", mfCusOutProject));
				JsonTableUtil jtu = new JsonTableUtil();
				@SuppressWarnings("unchecked")
				List<MfCusOutProject> mfCusOutProjectList = (List<MfCusOutProject>) mfCusOutProjectFeign.findByPage(ipage).getResult();
				String tableHtml = jtu.getJsonStr("tablecusoutprojectDetail", "tableTag", mfCusOutProjectList, null, true);
				JSONObject json = new JSONObject();
				json.put("tableHtml",tableHtml);
				model.addAttribute("ajaxData",json.toString());*/
			}
		}

		model.addAttribute("formcusliabilitiesDetail", formcusliabilitiesDetail);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonLiabilities_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
		mfCusPersonLiabilities.setCusNo(cusNo);
		mfCusPersonLiabilitiesFeign.delete(mfCusPersonLiabilities);
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
		FormData  formcusliabilitiesBase = formService.getFormData("cusliabilitiesBase");
		 getFormValue(formcusliabilitiesBase);
		 boolean validateFlag = this.validateFormData(formcusliabilitiesBase);
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
		FormData  formcusliabilitiesBase = formService.getFormData("cusliabilitiesBase");
		 getFormValue(formcusliabilitiesBase);
		 boolean validateFlag = this.validateFormData(formcusliabilitiesBase);
	}
	
	
	
}
