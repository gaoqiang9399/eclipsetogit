package app.component.app.makepolicymeeting.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.component.app.entity.MfBusApply;
import app.component.app.makepolicymeeting.entity.MfMakingMeetingSummaryBus;
import app.component.app.makepolicymeeting.feign.MfMakingMeetingSummaryBusFeign;
import app.component.carmodel.entity.MfCarBrand;
import app.component.common.EntityUtil;
import app.component.app.makepolicymeeting.feign.MfMakingMeetingSummaryFeign;
import app.component.app.makepolicymeeting.entity.MfMakingMeetingSummary;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: MfMakingMeetingSummaryController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 29 11:14:20 CST 2020
 **/
@Controller
@RequestMapping(value = "/mfMakingMeetingSummary")
public class MfMakingMeetingSummaryController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfMakingMeetingSummaryFeign mfMakingMeetingSummaryFeign;
	@Autowired
	private MfMakingMeetingSummaryBusFeign mfMakingMeetingSummaryBusFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/app/makepolicymeeting/MfMakingMeetingSummary_List";
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
		MfMakingMeetingSummary mfMakingMeetingSummary = new MfMakingMeetingSummary();
		try {
			mfMakingMeetingSummary.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMakingMeetingSummary.setCriteriaList(mfMakingMeetingSummary, ajaxData);//我的筛选
			mfMakingMeetingSummary.setCustomSorts(ajaxData);//自定义排序
//			this.getRoleConditions(mfMakingMeetingSummary,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfMakingMeetingSummary", mfMakingMeetingSummary));
			//自定义查询Feign方法
			ipage = mfMakingMeetingSummaryFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData,String ajaxDataList) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formMakingMeetingSummaryBase = formService.getFormData("MakingMeetingSummaryBase");
			getFormValue(formMakingMeetingSummaryBase, getMapByJson(ajaxData));
			if(this.validateFormData(formMakingMeetingSummaryBase)){
				MfMakingMeetingSummary mfMakingMeetingSummary = new MfMakingMeetingSummary();
				mfMakingMeetingSummary.setAjaxDataList(ajaxDataList);
				setObjValue(formMakingMeetingSummaryBase, mfMakingMeetingSummary);
				mfMakingMeetingSummaryFeign.insert(mfMakingMeetingSummary);
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
		FormData formMakingMeetingSummaryBase = formService.getFormData("MakingMeetingSummaryBase");
		getFormValue(formMakingMeetingSummaryBase, getMapByJson(ajaxData));
		MfMakingMeetingSummary mfMakingMeetingSummaryJsp = new MfMakingMeetingSummary();
		setObjValue(formMakingMeetingSummaryBase, mfMakingMeetingSummaryJsp);
		MfMakingMeetingSummary mfMakingMeetingSummary = mfMakingMeetingSummaryFeign.getById(mfMakingMeetingSummaryJsp);
		if(mfMakingMeetingSummary!=null){
			try{
				mfMakingMeetingSummary = (MfMakingMeetingSummary)EntityUtil.reflectionSetVal(mfMakingMeetingSummary, mfMakingMeetingSummaryJsp, getMapByJson(ajaxData));
				mfMakingMeetingSummaryFeign.update(mfMakingMeetingSummary);
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
		MfMakingMeetingSummary mfMakingMeetingSummary = new MfMakingMeetingSummary();
		try{
			FormData formMakingMeetingSummaryBase = formService.getFormData("MakingMeetingSummaryBase");
			getFormValue(formMakingMeetingSummaryBase, getMapByJson(ajaxData));
			if(this.validateFormData(formMakingMeetingSummaryBase)){
				mfMakingMeetingSummary = new MfMakingMeetingSummary();
				setObjValue(formMakingMeetingSummaryBase, mfMakingMeetingSummary);
				mfMakingMeetingSummaryFeign.update(mfMakingMeetingSummary);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formMakingMeetingSummaryBase = formService.getFormData("MakingMeetingSummaryBase");
		MfMakingMeetingSummary mfMakingMeetingSummary = new MfMakingMeetingSummary();
		mfMakingMeetingSummary.setId(id);
		mfMakingMeetingSummary = mfMakingMeetingSummaryFeign.getById(mfMakingMeetingSummary);
		getObjValue(formMakingMeetingSummaryBase, mfMakingMeetingSummary,formData);
		if(mfMakingMeetingSummary!=null){
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMakingMeetingSummary mfMakingMeetingSummary = new MfMakingMeetingSummary();
		mfMakingMeetingSummary.setId(id);
		try {
			mfMakingMeetingSummaryFeign.delete(mfMakingMeetingSummary);
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
		// 获取上会时间
		FormData formMakingMeetingSummaryBase = formService.getFormData("MakingMeetingSummaryBase");
		List<String> meetingTimeList = mfMakingMeetingSummaryFeign.getMeetingTimeList();
		JSONArray meetingTimeMap = new JSONArray();
		for (String meetingTime : meetingTimeList) {
			JSONObject obj = new JSONObject();
			obj.put("id", meetingTime);
			obj.put("name", DateUtil.getShowDateTime(meetingTime));
			meetingTimeMap.add(obj);
		}
		model.addAttribute("meetingTimeMap", meetingTimeMap.toString());

		model.addAttribute("formMakingMeetingSummaryBase", formMakingMeetingSummaryBase);
		model.addAttribute("query", "");
		return "/component/app/makepolicymeeting/MfMakingMeetingSummary_Insert";
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
		FormData formMakingMeetingSummaryDetail = formService.getFormData("MakingMeetingSummaryDetail");
		getFormValue(formMakingMeetingSummaryDetail);
		MfMakingMeetingSummary mfMakingMeetingSummary = new MfMakingMeetingSummary();
		mfMakingMeetingSummary.setId(id);
		mfMakingMeetingSummary = mfMakingMeetingSummaryFeign.getById(mfMakingMeetingSummary);
		getObjValue(formMakingMeetingSummaryDetail, mfMakingMeetingSummary);

		MfMakingMeetingSummaryBus mfMakingMeetingSummaryBus = new MfMakingMeetingSummaryBus();
		mfMakingMeetingSummaryBus.setMeetingId(id);
		List<MfMakingMeetingSummaryBus> list = mfMakingMeetingSummaryBusFeign.getListByMeetingId(mfMakingMeetingSummaryBus);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tableMakingMeetingSummaryBusList", "tableTag", list, null, true);

		model.addAttribute("formMakingMeetingSummaryDetail", formMakingMeetingSummaryDetail);
		model.addAttribute("tableHtml", tableHtml);
		model.addAttribute("query", "");
		return "/component/app/makepolicymeeting/MfMakingMeetingSummary_Detail";
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
		FormData formMakingMeetingSummaryBase = formService.getFormData("MakingMeetingSummaryBase");
		getFormValue(formMakingMeetingSummaryBase);
		boolean validateFlag = this.validateFormData(formMakingMeetingSummaryBase);
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
		FormData formMakingMeetingSummaryBase = formService.getFormData("MakingMeetingSummaryBase");
		getFormValue(formMakingMeetingSummaryBase);
		boolean validateFlag = this.validateFormData(formMakingMeetingSummaryBase);
	}
}
