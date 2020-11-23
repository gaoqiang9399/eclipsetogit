package app.component.report.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import app.component.common.EntityUtil;
import app.component.report.entity.MfReportQueryConditionConfigSet;
import app.component.report.feign.MfReportQueryConditionConfigSetFeign;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: MfReportQueryConditionConfigController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Oct 24 18:14:04 CST 2019
 **/
@Controller
@RequestMapping(value = "/mfReportQueryConditionConfigSet")
public class MfReportQueryConditionConfigSetController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReportQueryConditionConfigSetFeign mfReportQueryConditionConfigSetFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/report/MfReportQueryConditionConfigList";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfReportQueryConditionConfigSet mfReportQueryConditionConfigSet = new MfReportQueryConditionConfigSet();
		try {
			mfReportQueryConditionConfigSet.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfReportQueryConditionConfigSet.setCriteriaList(mfReportQueryConditionConfigSet, ajaxData);//我的筛选
			//mfReportQueryConditionConfigSet.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfReportQueryConditionConfigSet,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfReportQueryConditionConfigSet", mfReportQueryConditionConfigSet));
			//自定义查询Feign方法
			ipage = mfReportQueryConditionConfigSetFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
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
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formReportConditionInsert = formService.getFormData("ReportConditionInsert");
			getFormValue(formReportConditionInsert, getMapByJson(ajaxData));
			if(this.validateFormData(formReportConditionInsert)){
				MfReportQueryConditionConfigSet mfReportQueryConditionConfigSet = new MfReportQueryConditionConfigSet();
				setObjValue(formReportConditionInsert, mfReportQueryConditionConfigSet);
				mfReportQueryConditionConfigSetFeign.insert(mfReportQueryConditionConfigSet);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formReportConditionDetail = formService.getFormData("ReportConditionDetail");
		getFormValue(formReportConditionDetail, getMapByJson(ajaxData));
		MfReportQueryConditionConfigSet mfReportQueryConditionConfigJsp = new MfReportQueryConditionConfigSet();
		setObjValue(formReportConditionDetail, mfReportQueryConditionConfigJsp);
		MfReportQueryConditionConfigSet mfReportQueryConditionConfigSet = mfReportQueryConditionConfigSetFeign.getById(mfReportQueryConditionConfigJsp);
		if(mfReportQueryConditionConfigSet!=null){
			try{
				mfReportQueryConditionConfigSet = (MfReportQueryConditionConfigSet)EntityUtil.reflectionSetVal(mfReportQueryConditionConfigSet, mfReportQueryConditionConfigJsp, getMapByJson(ajaxData));
				mfReportQueryConditionConfigSetFeign.update(mfReportQueryConditionConfigSet);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfReportQueryConditionConfigSet mfReportQueryConditionConfigSet = new MfReportQueryConditionConfigSet();
		try{
			FormData formReportConditionDetail = formService.getFormData("ReportConditionDetail");
			getFormValue(formReportConditionDetail, getMapByJson(ajaxData));
			if(this.validateFormData(formReportConditionDetail)){
				mfReportQueryConditionConfigSet = new MfReportQueryConditionConfigSet();
				setObjValue(formReportConditionDetail, mfReportQueryConditionConfigSet);
				mfReportQueryConditionConfigSetFeign.update(mfReportQueryConditionConfigSet);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
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
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id,String reportId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formReportConditionDetail = formService.getFormData("ReportConditionDetail");
		MfReportQueryConditionConfigSet mfReportQueryConditionConfigSet = new MfReportQueryConditionConfigSet();
		mfReportQueryConditionConfigSet.setId(id);
		mfReportQueryConditionConfigSet.setReportId(reportId);
		mfReportQueryConditionConfigSet = mfReportQueryConditionConfigSetFeign.getById(mfReportQueryConditionConfigSet);
		getObjValue(formReportConditionDetail, mfReportQueryConditionConfigSet,formData);
		if(mfReportQueryConditionConfigSet!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "查找失败");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfReportQueryConditionConfigSet mfReportQueryConditionConfigSet = new MfReportQueryConditionConfigSet();
		mfReportQueryConditionConfigSet.setId(id);
		try {
			mfReportQueryConditionConfigSetFeign.delete(mfReportQueryConditionConfigSet);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
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
	@RequestMapping("/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formReportConditionInsert = formService.getFormData("ReportConditionInsert");
		model.addAttribute("formReportConditionInsert", formReportConditionInsert);
		model.addAttribute("query", "");
		return "/component/report/MfReportQueryConditionConfigInsert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formReportConditionDetail = formService.getFormData("ReportConditionDetail");
		getFormValue(formReportConditionDetail);
		MfReportQueryConditionConfigSet mfReportQueryConditionConfigSet = new MfReportQueryConditionConfigSet();
		mfReportQueryConditionConfigSet.setId(id);
		mfReportQueryConditionConfigSet = mfReportQueryConditionConfigSetFeign.getById(mfReportQueryConditionConfigSet);
		getObjValue(formReportConditionDetail, mfReportQueryConditionConfigSet);
		model.addAttribute("formReportConditionDetail", formReportConditionDetail);
		model.addAttribute("query", "");
		return "/component/report/MfReportQueryConditionConfigDetail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formReportConditionInsert = formService.getFormData("ReportConditionInsert");
		getFormValue(formReportConditionInsert);
		boolean validateFlag = this.validateFormData(formReportConditionInsert);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formReportConditionDetail = formService.getFormData("ReportConditionDetail");
		getFormValue(formReportConditionDetail);
		boolean validateFlag = this.validateFormData(formReportConditionDetail);
	}
}
