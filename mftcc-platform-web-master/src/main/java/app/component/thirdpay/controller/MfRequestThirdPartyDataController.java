package  app.component.thirdpay.controller;
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
import app.component.thirdpay.entity.MfRequestThirdPartyData;
import app.component.thirdpay.feign.MfRequestThirdPartyDataFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfRequestThirdPartyDataAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Nov 09 14:41:04 CST 2017
 **/
@Controller
@RequestMapping(value = "/mfRequestThirdPartyData")
public class MfRequestThirdPartyDataController extends BaseFormBean{

	//注入MfRequestThirdPartyDataBo
	@Autowired
	private MfRequestThirdPartyDataFeign mfRequestThirdPartyDataFeign;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	/*//全局变量
	private MfRequestThirdPartyData mfRequestThirdPartyData;
	private List<MfRequestThirdPartyData> mfRequestThirdPartyDataList;
	private String requestId;		
	private String tableType;
	private String tableId;
	private int pageNo;
	private String query;
	//异步参数
	private String ajaxData;
	private Map<String,Object> dataMap;
	//表单变量
	private FormData formrequestdata0001;
	private FormData formrequestdata0002;
	private FormService formService = new FormService();
	
	public MfRequestThirdPartyDataController(){
		query = "";
	}*/
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		return "/component/thirdpay/MfRequestThirdPartyData_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfRequestThirdPartyData mfRequestThirdPartyData = new MfRequestThirdPartyData();
		try {
			mfRequestThirdPartyData.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfRequestThirdPartyData.setCriteriaList(mfRequestThirdPartyData, ajaxData);//我的筛选
			//mfRequestThirdPartyData.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfRequestThirdPartyData,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfRequestThirdPartyDataFeign.findByPage(ipage, mfRequestThirdPartyData);
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
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formrequestdata0002 = formService.getFormData("requestdata0002");
			getFormValue(formrequestdata0002, getMapByJson(ajaxData));
			if(this.validateFormData(formrequestdata0002)){
				MfRequestThirdPartyData mfRequestThirdPartyData = new MfRequestThirdPartyData();
				setObjValue(formrequestdata0002, mfRequestThirdPartyData);
				mfRequestThirdPartyDataFeign.insert(mfRequestThirdPartyData);
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
	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrequestdata0002 = formService.getFormData("requestdata0002");
		getFormValue(formrequestdata0002, getMapByJson(ajaxData));
		MfRequestThirdPartyData mfRequestThirdPartyDataJsp = new MfRequestThirdPartyData();
		setObjValue(formrequestdata0002, mfRequestThirdPartyDataJsp);
		MfRequestThirdPartyData mfRequestThirdPartyData = mfRequestThirdPartyDataFeign.getById(mfRequestThirdPartyDataJsp);
		if(mfRequestThirdPartyData!=null){
			try{
				mfRequestThirdPartyData = (MfRequestThirdPartyData)EntityUtil.reflectionSetVal(mfRequestThirdPartyData, mfRequestThirdPartyDataJsp, getMapByJson(ajaxData));
				mfRequestThirdPartyDataFeign.update(mfRequestThirdPartyData);
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
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfRequestThirdPartyData mfRequestThirdPartyData = new MfRequestThirdPartyData();
		try{
			FormData formrequestdata0002 = formService.getFormData("requestdata0002");
			getFormValue(formrequestdata0002, getMapByJson(ajaxData));
			if(this.validateFormData(formrequestdata0002)){
				mfRequestThirdPartyData = new MfRequestThirdPartyData();
				setObjValue(formrequestdata0002, mfRequestThirdPartyData);
				mfRequestThirdPartyDataFeign.update(mfRequestThirdPartyData);
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
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String requestId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formrequestdata0002 = formService.getFormData("requestdata0002");
		MfRequestThirdPartyData mfRequestThirdPartyData = new MfRequestThirdPartyData();
		mfRequestThirdPartyData.setRequestId(requestId);
		mfRequestThirdPartyData = mfRequestThirdPartyDataFeign.getById(mfRequestThirdPartyData);
		getObjValue(formrequestdata0002, mfRequestThirdPartyData,formData);
		if(mfRequestThirdPartyData!=null){
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
	public Map<String, Object> deleteAjax(String requestId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfRequestThirdPartyData mfRequestThirdPartyData = new MfRequestThirdPartyData();
		mfRequestThirdPartyData.setRequestId(requestId);
		try {
			mfRequestThirdPartyDataFeign.delete(mfRequestThirdPartyData);
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
		FormService formService = new FormService();
		FormData formrequestdata0002 = formService.getFormData("requestdata0002");
		model.addAttribute("formrequestdata0002", formrequestdata0002);
		model.addAttribute("query", "");
		return "/MfRequestThirdPartyData_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String requestId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formrequestdata0001 = formService.getFormData("requestdata0001");
		getFormValue(formrequestdata0001);
		MfRequestThirdPartyData mfRequestThirdPartyData = new MfRequestThirdPartyData();
		mfRequestThirdPartyData.setRequestId(requestId);
		mfRequestThirdPartyData = mfRequestThirdPartyDataFeign.getById(mfRequestThirdPartyData);
		getObjValue(formrequestdata0001, mfRequestThirdPartyData);
		model.addAttribute("formrequestdata0001", formrequestdata0001);
		model.addAttribute("query", ""); 
		return "/component/thirdpay/MfRequestThirdPartyData_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formrequestdata0002 = formService.getFormData("requestdata0002");
		 getFormValue(formrequestdata0002);
		 boolean validateFlag = this.validateFormData(formrequestdata0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formrequestdata0002 = formService.getFormData("requestdata0002");
		 getFormValue(formrequestdata0002);
		 boolean validateFlag = this.validateFormData(formrequestdata0002);
	}
	
}
