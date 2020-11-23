package  app.component.cus.relation.controller;
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
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.relation.entity.MfCusRelationType;
import app.component.cus.relation.feign.MfCusRelationTypeFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfCusRelationTypeAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Dec 03 17:34:38 CST 2016
 **/
@Controller
@RequestMapping("/mfCusRelationType")
public class MfCusRelationTypeController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusRelationTypeBo
	@Autowired
	private MfCusRelationTypeFeign mfCusRelationTypeFeign;
	//全局变量
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	//异步参数
	//表单变量
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData formrelationtype0001 = formService.getFormData("cusrelation0001");
		getFormValue(formrelationtype0001);
		getObjValue(formrelationtype0001,this);
		List<MfCusRelationType> mfCusRelationTypeList = mfCusRelationTypeFeign.getRelationType();
		JSONArray array = JSONArray.fromObject(mfCusRelationTypeList);
		JSONObject json = new JSONObject();
		json.put("relationType", array);
		model.addAttribute("formrelationtype0001", formrelationtype0001);
		model.addAttribute("query", "");
		return "/component/cus/relation/MfCusRelationType_Insert";
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
		MfCusRelationType mfCusRelationType = new MfCusRelationType();
		try {
			mfCusRelationType.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusRelationType.setCriteriaList(mfCusRelationType, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusRelationType,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfCusRelationTypeFeign.findByPage(ipage, mfCusRelationType);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formrelationtype0002 = formService.getFormData("MfCusRelationType0001");
			getFormValue(formrelationtype0002, getMapByJson(ajaxData));
			if(this.validateFormData(formrelationtype0002)){
		MfCusRelationType mfCusRelationType = new MfCusRelationType();
				setObjValue(formrelationtype0002, mfCusRelationType);
				mfCusRelationTypeFeign.insert(mfCusRelationType);
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
		FormData formrelationtype0002 = formService.getFormData("relationtype0002");
		getFormValue(formrelationtype0002, getMapByJson(ajaxData));
		MfCusRelationType mfCusRelationTypeJsp = new MfCusRelationType();
		setObjValue(formrelationtype0002, mfCusRelationTypeJsp);
		MfCusRelationType mfCusRelationType = mfCusRelationTypeFeign.getById(mfCusRelationTypeJsp);
		if(mfCusRelationType!=null){
			try{
				mfCusRelationType = (MfCusRelationType)EntityUtil.reflectionSetVal(mfCusRelationType, mfCusRelationTypeJsp, getMapByJson(ajaxData));
				mfCusRelationTypeFeign.update(mfCusRelationType);
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formrelationtype0002 = formService.getFormData("relationtype0002");
			getFormValue(formrelationtype0002, getMapByJson(ajaxData));
			if(this.validateFormData(formrelationtype0002)){
				MfCusRelationType mfCusRelationType = new MfCusRelationType();
				setObjValue(formrelationtype0002, mfCusRelationType);
				mfCusRelationTypeFeign.update(mfCusRelationType);
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
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formrelationtype0002 = formService.getFormData("relationtype0002");
		MfCusRelationType mfCusRelationType = new MfCusRelationType();
		mfCusRelationType.setId(id);
		mfCusRelationType = mfCusRelationTypeFeign.getById(mfCusRelationType);
		getObjValue(formrelationtype0002, mfCusRelationType,formData);
		if(mfCusRelationType!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusRelationType mfCusRelationType = new MfCusRelationType();
		mfCusRelationType.setId(id);
		try {
			mfCusRelationTypeFeign.delete(mfCusRelationType);
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
	 * @param cusNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		 MfCusCustomer mfCusCustomer = new MfCusCustomer();
		 mfCusCustomer.setCusNo(cusNo);
		 mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		 String cusName = mfCusCustomer.getCusName();
		FormData formrelationtype0001 = formService.getFormData("cusrelation0001");
		getFormValue(formrelationtype0001);
		MfCusRelationType mfCusRelationType = new MfCusRelationType();
		getObjValue(formrelationtype0001,mfCusRelationType);
		List<MfCusRelationType> mfCusRelationTypeList = mfCusRelationTypeFeign.getRelationType();
		JSONArray array = JSONArray.fromObject(mfCusRelationTypeList);
		JSONObject json = new JSONObject();
		json.put("relationType", array);
		json.put("cusName", mfCusCustomer.getCusName());
		model.addAttribute("formrelationtype0001", formrelationtype0001);
		model.addAttribute("json", json);
		model.addAttribute("cusName", cusName);
		model.addAttribute("mfCusRelationTypeList", mfCusRelationTypeList);
		model.addAttribute("query", "");
		return "/component/cus/relation/MfCusRelationType_Insert";
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
		FormData  formrelationtype0002 = formService.getFormData("relationtype0002");
		 getFormValue(formrelationtype0002);
		MfCusRelationType  mfCusRelationType = new MfCusRelationType();
		 setObjValue(formrelationtype0002, mfCusRelationType);
		 mfCusRelationTypeFeign.insert(mfCusRelationType);
		 getObjValue(formrelationtype0002, mfCusRelationType);
		 this.addActionMessage(model, "保存成功");
		 List<MfCusRelationType> mfCusRelationTypeList = (List<MfCusRelationType>)mfCusRelationTypeFeign.findByPage(this.getIpage(), mfCusRelationType).getResult();
		model.addAttribute("formrelationtype0002", formrelationtype0002);
		model.addAttribute("mfCusRelationTypeList", mfCusRelationTypeList);
		model.addAttribute("query", "");
		return "/component/cus/relation/MfCusRelationType_Insert";
	}
	/**
	 * 查询
	 * @param mfCusRelationType 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, MfCusRelationType mfCusRelationType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formrelationtype0001 = formService.getFormData("MfCusRelationType0001");
		 getFormValue(formrelationtype0001);
		List<MfCusRelationType> mfCusRelationTypeList = mfCusRelationTypeFeign.getRelationType();
		 getObjValue(formrelationtype0001, mfCusRelationType);
		model.addAttribute("formrelationtype0001", formrelationtype0001);
		model.addAttribute("query", "");
		return "/component/cus/relation/MfCusRelationType_Detail";
	}
	/**
	 * 删除
	 * @param id 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String id) throws Exception {
		ActionContext.initialize(request,
				response);
		MfCusRelationType mfCusRelationType = new MfCusRelationType();
		mfCusRelationType.setId(id);
		mfCusRelationTypeFeign.delete(mfCusRelationType);
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
		FormData  formrelationtype0002 = formService.getFormData("relationtype0002");
		 getFormValue(formrelationtype0002);
		 boolean validateFlag = this.validateFormData(formrelationtype0002);
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
		FormData  formrelationtype0002 = formService.getFormData("relationtype0002");
		 getFormValue(formrelationtype0002);
		 boolean validateFlag = this.validateFormData(formrelationtype0002);
	}
	
}
