package  app.component.cus.controller;
import java.util.ArrayList;
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
import app.component.cus.entity.MfCusProfitInfo;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusProfitInfoFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfCusProfitInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 04 09:03:12 CST 2016
 **/
@Controller
@RequestMapping("/mfCusProfitInfo")
public class MfCusProfitInfoController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusProfitInfoBo
	@Autowired
	private MfCusProfitInfoFeign mfCusProfitInfoFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
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
		return "/component/cus/MfCusProfitInfo_List";
	}
	/**
	 * 
	 * 方法描述： 获得企业客户利润分配信息列表
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2016-6-4 上午9:45:49
	 */
	@RequestMapping(value = "/getListPageTmp")
	public String getListPageTmp(Model model, String ajaxData,String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData 	formcusprof00001 = null;
		List<MfCusProfitInfo> mfCusProfitInfoList = new ArrayList<MfCusProfitInfo>();
		try {
		 	formcusprof00001 = formService.getFormData("cusprof00001");
		MfCusProfitInfo 	mfCusProfitInfo = new MfCusProfitInfo();
			mfCusProfitInfo.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfCusProfitInfo", mfCusProfitInfo));
			mfCusProfitInfoList = (List<MfCusProfitInfo>)mfCusProfitInfoFeign.findByPage(ipage).getResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("formcusprof00001", formcusprof00001);
		model.addAttribute("mfCusProfitInfoList", mfCusProfitInfoList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusProfitInfo_ListTmp";
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
		MfCusProfitInfo mfCusProfitInfo = new MfCusProfitInfo();
		try {
			mfCusProfitInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusProfitInfo.setCriteriaList(mfCusProfitInfo, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusProfitInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusProfitInfo", mfCusProfitInfo));
			ipage = mfCusProfitInfoFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData,String cusNo,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formcusprof00001 = formService.getFormData("cusprof00001");
			getFormValue(formcusprof00001, getMapByJson(ajaxData));
			if(this.validateFormData(formcusprof00001)){
		MfCusProfitInfo mfCusProfitInfo = new MfCusProfitInfo();
				setObjValue(formcusprof00001, mfCusProfitInfo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusProfitInfo.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusProfitInfo.setCusName(cusName);
				mfCusProfitInfoFeign.insert(mfCusProfitInfo);
				getTableData(cusName, cusName, dataMap);
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
		FormData formcusprof00002 = formService.getFormData("cusprof00002");
		getFormValue(formcusprof00002, getMapByJson(ajaxData));
		MfCusProfitInfo mfCusProfitInfoJsp = new MfCusProfitInfo();
		setObjValue(formcusprof00002, mfCusProfitInfoJsp);
		MfCusProfitInfo mfCusProfitInfo = mfCusProfitInfoFeign.getById(mfCusProfitInfoJsp);
		if(mfCusProfitInfo!=null){
			try{
				mfCusProfitInfo = (MfCusProfitInfo)EntityUtil.reflectionSetVal(mfCusProfitInfo, mfCusProfitInfoJsp, getMapByJson(ajaxData));
				mfCusProfitInfoFeign.update(mfCusProfitInfo);
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
	public Map<String, Object> updateAjax(String ajaxData,String cusNo,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusProfitInfo mfCusProfitInfo = new MfCusProfitInfo();
		try{
		FormData 	formcusprof00001 = formService.getFormData("cusprof00001");
			getFormValue(formcusprof00001, getMapByJson(ajaxData));
			if(this.validateFormData(formcusprof00001)){
				setObjValue(formcusprof00001, mfCusProfitInfo);
				mfCusProfitInfoFeign.update(mfCusProfitInfo);
				getTableData(tableId, tableId, dataMap);
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
	public Map<String, Object> getByIdAjax(String profitEnumId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusprof00002 = formService.getFormData("cusprof00002");
		MfCusProfitInfo mfCusProfitInfo = new MfCusProfitInfo();
		mfCusProfitInfo.setProfitEnumId(profitEnumId);
		mfCusProfitInfo = mfCusProfitInfoFeign.getById(mfCusProfitInfo);
		getObjValue(formcusprof00002, mfCusProfitInfo,formData);
		if(mfCusProfitInfo!=null){
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
	public Map<String, Object> deleteAjax(String ajaxData,String cusNo,String tableId,String profitEnumId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusProfitInfo mfCusProfitInfo = new MfCusProfitInfo();
		mfCusProfitInfo.setProfitEnumId(profitEnumId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfCusProfitInfo = (MfCusProfitInfo)JSONObject.toBean(jb, MfCusProfitInfo.class);
			mfCusProfitInfoFeign.delete(mfCusProfitInfo);
			getTableData(cusNo, tableId,dataMap);
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
		FormData formcusprof00002 = formService.getFormData("cusprof00002");
		model.addAttribute("formcusprof00002", formcusprof00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusProfitInfo_Insert";
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
		FormData  formcusprof00002 = formService.getFormData("cusprof00002");
		 getFormValue(formcusprof00002);
		MfCusProfitInfo  mfCusProfitInfo = new MfCusProfitInfo();
		 setObjValue(formcusprof00002, mfCusProfitInfo);
		 mfCusProfitInfoFeign.insert(mfCusProfitInfo);
		 getObjValue(formcusprof00002, mfCusProfitInfo);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfCusProfitInfo", mfCusProfitInfo));
		 ipage.setParams(this.setIpageParams("mfCusProfitInfo", mfCusProfitInfo));
		 List<MfCusProfitInfo> mfCusProfitInfoList = (List<MfCusProfitInfo>)mfCusProfitInfoFeign.findByPage(ipage).getResult();
		model.addAttribute("formcusprof00002", formcusprof00002);
		model.addAttribute("mfCusProfitInfoList", mfCusProfitInfoList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusProfitInfo_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String profitEnumId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcusprof00001 = formService.getFormData("cusprof00001");
		 getFormValue(formcusprof00001);
		MfCusProfitInfo  mfCusProfitInfo = new MfCusProfitInfo();
		mfCusProfitInfo.setProfitEnumId(profitEnumId);
		 mfCusProfitInfo = mfCusProfitInfoFeign.getById(mfCusProfitInfo);
		 getObjValue(formcusprof00001, mfCusProfitInfo);
		model.addAttribute("formcusprof00001", formcusprof00001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusProfitInfo_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String profitEnumId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfCusProfitInfo mfCusProfitInfo = new MfCusProfitInfo();
		mfCusProfitInfo.setProfitEnumId(profitEnumId);
		mfCusProfitInfoFeign.delete(mfCusProfitInfo);
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
		FormData  formcusprof00002 = formService.getFormData("cusprof00002");
		 getFormValue(formcusprof00002);
		 boolean validateFlag = this.validateFormData(formcusprof00002);
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
		FormData  formcusprof00002 = formService.getFormData("cusprof00002");
		 getFormValue(formcusprof00002);
		 boolean validateFlag = this.validateFormData(formcusprof00002);
	}
	private void getTableData(String cusNo,String tableId,Map<String,Object>dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		MfCusProfitInfo mfCusProfitInfo = new MfCusProfitInfo();
		mfCusProfitInfo.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusProfitInfo", mfCusProfitInfo));
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", (List<MfCusProfitInfo>)mfCusProfitInfoFeign.findByPage(ipage).getResult(), null,true);
		dataMap.put("tableData",tableHtml);
	}
	
}
