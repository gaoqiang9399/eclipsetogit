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
import app.component.cus.entity.MfCusCorpMajorChange;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCorpMajorChangeFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;

/**
 * Title: MfCusCorpMajorChangeAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 03 19:42:31 CST 2017
 **/
@Controller
@RequestMapping("/mfCusCorpMajorChange")
public class MfCusCorpMajorChangeController extends BaseFormBean{
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired 
	private MfCusCorpMajorChangeFeign mfCusCorpMajorChangeFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		return "/component/cus/MfCusCorpMajorChange_List";
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
		MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
		try {
			mfCusCorpMajorChange.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCorpMajorChange.setCriteriaList(mfCusCorpMajorChange, ajaxData);//我的筛选
			//mfCusCorpMajorChange.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusCorpMajorChange,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusCorpMajorChange", mfCusCorpMajorChange));
			ipage = mfCusCorpMajorChangeFeign.findByPage(ipage);
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
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "MfCusEquityInfoAction").getAddModel();
			}
			FormData formcuschange0001 = formService.getFormData(formId);
			getFormValue(formcuschange0001, map);
			if(this.validateFormData(formcuschange0001)){
				MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
				setObjValue(formcuschange0001, mfCusCorpMajorChange);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusCorpMajorChange.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				mfCusCorpMajorChange.setCusName( mfCusCustomer.getCusName());
				mfCusCorpMajorChangeFeign.insert(mfCusCorpMajorChange);
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfCusCorpMajorChange.getCusNo(),mfCusCorpMajorChange.getRelNo());//更新客户信息完整度
				String relNo = mfCusCorpMajorChange.getRelNo();
			    mfCusCorpMajorChange = new MfCusCorpMajorChange();
				mfCusCorpMajorChange.setCusNo(mfCusCustomer.getCusNo());
				mfCusCorpMajorChange.setRelNo(relNo);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfCusCorpMajorChange", mfCusCorpMajorChange));
				String  tableHtml = jtu.getJsonStr("tablecuschange0001","tableTag", (List<MfCusCorpMajorChange>)mfCusCorpMajorChangeFeign.findByPage(ipage).getResult(), null,true);
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
		FormData formcuschange0002 = formService.getFormData("cuschange0002");
		getFormValue(formcuschange0002, getMapByJson(ajaxData));
		MfCusCorpMajorChange mfCusCorpMajorChangeJsp = new MfCusCorpMajorChange();
		setObjValue(formcuschange0002, mfCusCorpMajorChangeJsp);
		MfCusCorpMajorChange mfCusCorpMajorChange = mfCusCorpMajorChangeFeign.getById(mfCusCorpMajorChangeJsp);
		if(mfCusCorpMajorChange!=null){
			try{
				mfCusCorpMajorChange = (MfCusCorpMajorChange)EntityUtil.reflectionSetVal(mfCusCorpMajorChange, mfCusCorpMajorChangeJsp, getMapByJson(ajaxData));
				mfCusCorpMajorChangeFeign.update(mfCusCorpMajorChange);
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
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
		try{
			Map map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			if(StringUtil.isEmpty(formId)){
				formId = mfCusFormConfigFeign.getByCusType("base", "mfCusCorpMajorChangeAction").getAddModel();
			}
			FormData formcuschange0001 = formService.getFormData(formId);
			
			getFormValue(formcuschange0001, map);
			if(this.validateFormData(formcuschange0001)){
				setObjValue(formcuschange0001, mfCusCorpMajorChange);
				mfCusCorpMajorChangeFeign.update(mfCusCorpMajorChange);
				
				String cusNo = mfCusCorpMajorChange.getCusNo();
				mfCusCorpMajorChange = new MfCusCorpMajorChange();
				mfCusCorpMajorChange.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusCorpMajorChange", mfCusCorpMajorChange));
				JsonTableUtil jtu = new JsonTableUtil();
				String  tableHtml = jtu.getJsonStr("tablecusMajorChangeListBase","tableTag", (List<MfCusCorpMajorChange>)mfCusCorpMajorChangeFeign.findByPage(ipage).getResult(), null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");
				
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
	public Map<String,Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formcuschange0002 = formService.getFormData("cuschange0002");
		MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
		mfCusCorpMajorChange.setId(id);
		mfCusCorpMajorChange = mfCusCorpMajorChangeFeign.getById(mfCusCorpMajorChange);
		getObjValue(formcuschange0002, mfCusCorpMajorChange,formData);
		if(mfCusCorpMajorChange!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdForOneAjax")
	@ResponseBody
	public Map<String,Object> getByIdForOneAjax(String id) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		FormData formcuschange0002 = formService.getFormData("cuschange0002");
		MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
		mfCusCorpMajorChange.setId(id);
		mfCusCorpMajorChange = mfCusCorpMajorChangeFeign.getById(mfCusCorpMajorChange);
		getObjValue(formcuschange0002, mfCusCorpMajorChange,formData);
		if(mfCusCorpMajorChange!=null){
			dataMap.put("flag", "success");
			//变更类型
			String changeType = mfCusCorpMajorChange.getChangeType();
			 Map<String, String> map =new CodeUtils().getMapByKeyName("CHANGE_TYPE");
			 changeType = map.get(changeType);
			dataMap.put("changeType", changeType);
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
	public Map<String,Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
		mfCusCorpMajorChange.setId(id);
		try {
			mfCusCorpMajorChangeFeign.delete(mfCusCorpMajorChange);
			String cusNo = mfCusCorpMajorChange.getCusNo();
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
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
	public String input(Model model,String cusNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcuschange0001 = formService.getFormData("cuschange0001");
		MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
		mfCusCorpMajorChange.setCusNo(cusNo);
		mfCusCorpMajorChange.setRelNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String formId="";
		mfCusCorpMajorChange.setCusName(mfCusCustomer.getCusName());
		mfCusCorpMajorChange.setCusNo(mfCusCustomer.getCusNo());
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCorpMajorChangeAction");
		if(mfCusFormConfig == null){
			
		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpMajorChangeAction表单信息没有查询到");
		}else{
			formcuschange0001 = formService.getFormData(formId);
			if(formcuschange0001.getFormId() == null){
//				logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpMajorChangeAction表单form"+formId+".xml文件不存在");
			}else{
				getFormValue(formcuschange0001);
				getObjValue(formcuschange0001, mfCusCorpMajorChange);
			}
			model.addAttribute("formcuschange0001", formcuschange0001);
			model.addAttribute("query", "");
		}
		return "/component/cus/MfCusCorpMajorChange_Insert";
	}
	/**
	 * 
	 * 方法描述： 业务新增客户信息表单页面
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-9-26 上午11:22:21
	 */
	@RequestMapping(value = "/inputBiz")
	public String inputBiz(Model model,String cusNo,String relNo,String kindNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
		mfCusCorpMajorChange.setCusNo(cusNo);
		mfCusCorpMajorChange.setRelNo(relNo);
		
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String formId="";
		mfCusCorpMajorChange.setCusName(mfCusCustomer.getCusName());
		mfCusCorpMajorChange.setCusNo(mfCusCustomer.getCusNo());
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(kindNo, "MfCusCorpMajorChangeAction");
		if(mfCusFormConfig == null){
			
		}else{
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
//			logger.error("产品类型为"+kindNo+"的MfCusCorpMajorChangeAction表单信息没有查询到");
		}else{
			FormData formcuschange0001 = formService.getFormData(formId);
			if(formcuschange0001.getFormId() == null){
//				logger.error("产品类型为"+kindNo+"的MfCusCorpMajorChangeAction表单form"+formId+".xml文件不存在");
			}else{
				getFormValue(formcuschange0001);
				getObjValue(formcuschange0001, mfCusCorpMajorChange);
			}
			model.addAttribute("formcuschange0001", formcuschange0001);
			model.addAttribute("query", "");
		}
		return "/component/cus/MfCusCorpMajorChange_Insert";
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
		 FormData formcuschange0002 = formService.getFormData("cuschange0002");
		 getFormValue(formcuschange0002);
		 MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
		 setObjValue(formcuschange0002, mfCusCorpMajorChange);
		 mfCusCorpMajorChangeFeign.insert(mfCusCorpMajorChange);
		 getObjValue(formcuschange0002, mfCusCorpMajorChange);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfCusCorpMajorChange", mfCusCorpMajorChange));
		 List<MfCusCorpMajorChange> mfCusCorpMajorChangeList = (List<MfCusCorpMajorChange>)mfCusCorpMajorChangeFeign.findByPage(ipage).getResult();
		 model.addAttribute("mfCusCorpMajorChangeList", mfCusCorpMajorChangeList);
		 model.addAttribute("formcuschange0002", formcuschange0002);
		 model.addAttribute("query", "");
		return "/component/cus/MfCusCorpMajorChange_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception

	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcuschange0001 = formService.getFormData("cuschange0001");

		 MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
		 mfCusCorpMajorChange.setId(id);
		 mfCusCorpMajorChange = mfCusCorpMajorChangeFeign.getById(mfCusCorpMajorChange);
		String formId="";
		 MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(mfCusCorpMajorChange.getCusNo());
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCorpMajorChangeAction");
			if(mfCusFormConfig == null){
				
			}else{
				if("1".equals(mfCusFormConfig.getShowType())){
					formId = mfCusFormConfig.getShowModelDef();
				}else{
					formId = mfCusFormConfig.getAddModelDef();
				}
			}
			if(StringUtil.isEmpty(formId)){
//				logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpMajorChangeAction表单信息没有查询到");
			}else{
				formcuschange0001 = formService.getFormData(formId);
				if(formcuschange0001.getFormId() == null){
//					logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpMajorChangeAction表单form"+formId+".xml文件不存在");
				}else{
					getFormValue(formcuschange0001);
					getObjValue(formcuschange0001, mfCusCorpMajorChange);
				}
				model.addAttribute("formcuschange0001", formcuschange0001);
				model.addAttribute("query", "");
			}
		return "/component/cus/MfCusCorpMajorChange_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model,String id) throws Exception {
		ActionContext.initialize(request,response);
		MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
		mfCusCorpMajorChange.setId(id);
		mfCusCorpMajorChangeFeign.delete(mfCusCorpMajorChange);
		return getListPage(model);
	}
	//列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String,Object> listShowDetailAjax(String cusNo,String id) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		String formId="";
		String query="";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig  mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCorpMajorChangeAction");
		if(mfCusFormConfig == null){
			
		}else{
			formId = mfCusFormConfig.getShowModelDef();
		}
		if(StringUtil.isEmpty(formId)){
//			logger.error("客户类型为"+mfCusCustomer.getCusType()+"的MfCusCorpMajorChangeAction表单信息没有查询到");
			dataMap.put("msg", "获取详情失败");
			dataMap.put("flag", "error");
		}else{
			Map<String,Object> formData = new HashMap<String,Object>();
			request.setAttribute("ifBizManger","3");
			
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formcuschange0003 = formService.getFormData(formId);
			MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
			mfCusCorpMajorChange.setId(id);
			mfCusCorpMajorChange = mfCusCorpMajorChangeFeign.getById(mfCusCorpMajorChange);
			getObjValue(formcuschange0003, mfCusCorpMajorChange,formData);
			String htmlStrCorp = jsonFormUtil.getJsonStr(formcuschange0003,"propertySeeTag","");
			if(mfCusCorpMajorChange!=null){
				dataMap.put("formHtml", htmlStrCorp);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("msg", "获取详情失败");
				dataMap.put("flag", "error");
			}
			//返回出资方式json
			JSONArray map =new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
			String items=map.toString().replaceAll("optName", "name").replace("optCode", "id");
			dataMap.put("items", items);
			dataMap.put("formData", mfCusCorpMajorChange);			
		}		
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateByOneAjax")
	@ResponseBody
	public Map<String,Object> updateByOneAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		MfCusCorpMajorChange mfCusCorpMajorChange =  new MfCusCorpMajorChange();
		//这里得到的formId是带form字符串的，比如formcuscorp0001
		Map map = getMapByJson(ajaxData);
		String formId = (String) map.get("formId");		
		FormData formcuschange0003 = formService.getFormData(formId);
		getFormValue(formcuschange0003, getMapByJson(ajaxData));
		MfCusCorpMajorChange mfCusCorpMajorChangeNew = new MfCusCorpMajorChange();
		setObjValue(formcuschange0003, mfCusCorpMajorChangeNew);
		mfCusCorpMajorChange.setId(mfCusCorpMajorChangeNew.getId());
		mfCusCorpMajorChange = mfCusCorpMajorChangeFeign.getById(mfCusCorpMajorChange);
		if(mfCusCorpMajorChange!=null){
			try{
				mfCusCorpMajorChange = (MfCusCorpMajorChange)EntityUtil.reflectionSetVal(mfCusCorpMajorChange, mfCusCorpMajorChangeNew, getMapByJson(ajaxData));
				mfCusCorpMajorChangeFeign.update(mfCusCorpMajorChange);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcuschange0002 = formService.getFormData("cuschange0002");
		 getFormValue(formcuschange0002);
		boolean validateFlag = this.validateFormData(formcuschange0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcuschange0002 = formService.getFormData("cuschange0002");
		 getFormValue(formcuschange0002);
		 boolean validateFlag = this.validateFormData(formcuschange0002);
	}	
}
