package app.component.app.makepolicymeeting.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import app.component.common.EntityUtil;
import app.component.app.makepolicymeeting.feign.MfMakingMeetingSummaryBusFeign;
import app.component.app.makepolicymeeting.entity.MfMakingMeetingSummaryBus;
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
 * Title: MfMakingMeetingSummaryBusController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 29 11:16:04 CST 2020
 **/
@Controller
@RequestMapping(value = "/mfMakingMeetingSummaryBus")
public class MfMakingMeetingSummaryBusController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
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
		return "/component/app/makepolicymeeting/MfMakingMeetingSummaryBus_List";
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
		MfMakingMeetingSummaryBus mfMakingMeetingSummaryBus = new MfMakingMeetingSummaryBus();
		try {
			mfMakingMeetingSummaryBus.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMakingMeetingSummaryBus.setCriteriaList(mfMakingMeetingSummaryBus, ajaxData);//我的筛选
			//mfMakingMeetingSummaryBus.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMakingMeetingSummaryBus,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfMakingMeetingSummaryBus", mfMakingMeetingSummaryBus));
			//自定义查询Feign方法
			ipage = mfMakingMeetingSummaryBusFeign.findByPage(ipage);
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
			FormData formMakingMeetingSummaryBusBase = formService.getFormData("MakingMeetingSummaryBusBase");
			getFormValue(formMakingMeetingSummaryBusBase, getMapByJson(ajaxData));
			if(this.validateFormData(formMakingMeetingSummaryBusBase)){
				MfMakingMeetingSummaryBus mfMakingMeetingSummaryBus = new MfMakingMeetingSummaryBus();
				setObjValue(formMakingMeetingSummaryBusBase, mfMakingMeetingSummaryBus);
				mfMakingMeetingSummaryBusFeign.insert(mfMakingMeetingSummaryBus);
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
		FormData formMakingMeetingSummaryBusBase = formService.getFormData("MakingMeetingSummaryBusBase");
		getFormValue(formMakingMeetingSummaryBusBase, getMapByJson(ajaxData));
		MfMakingMeetingSummaryBus mfMakingMeetingSummaryBusJsp = new MfMakingMeetingSummaryBus();
		setObjValue(formMakingMeetingSummaryBusBase, mfMakingMeetingSummaryBusJsp);
		MfMakingMeetingSummaryBus mfMakingMeetingSummaryBus = mfMakingMeetingSummaryBusFeign.getById(mfMakingMeetingSummaryBusJsp);
		if(mfMakingMeetingSummaryBus!=null){
			try{
				mfMakingMeetingSummaryBus = (MfMakingMeetingSummaryBus)EntityUtil.reflectionSetVal(mfMakingMeetingSummaryBus, mfMakingMeetingSummaryBusJsp, getMapByJson(ajaxData));
				mfMakingMeetingSummaryBusFeign.update(mfMakingMeetingSummaryBus);
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
		MfMakingMeetingSummaryBus mfMakingMeetingSummaryBus = new MfMakingMeetingSummaryBus();
		try{
			FormData formMakingMeetingSummaryBusBase = formService.getFormData("MakingMeetingSummaryBusBase");
			getFormValue(formMakingMeetingSummaryBusBase, getMapByJson(ajaxData));
			if(this.validateFormData(formMakingMeetingSummaryBusBase)){
				mfMakingMeetingSummaryBus = new MfMakingMeetingSummaryBus();
				setObjValue(formMakingMeetingSummaryBusBase, mfMakingMeetingSummaryBus);
				mfMakingMeetingSummaryBusFeign.update(mfMakingMeetingSummaryBus);
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
		FormData formMakingMeetingSummaryBusBase = formService.getFormData("MakingMeetingSummaryBusBase");
		MfMakingMeetingSummaryBus mfMakingMeetingSummaryBus = new MfMakingMeetingSummaryBus();
		mfMakingMeetingSummaryBus.setId(id);
		mfMakingMeetingSummaryBus = mfMakingMeetingSummaryBusFeign.getById(mfMakingMeetingSummaryBus);
		getObjValue(formMakingMeetingSummaryBusBase, mfMakingMeetingSummaryBus,formData);
		if(mfMakingMeetingSummaryBus!=null){
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
		MfMakingMeetingSummaryBus mfMakingMeetingSummaryBus = new MfMakingMeetingSummaryBus();
		mfMakingMeetingSummaryBus.setId(id);
		try {
			mfMakingMeetingSummaryBusFeign.delete(mfMakingMeetingSummaryBus);
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
		FormData formMakingMeetingSummaryBusBase = formService.getFormData("MakingMeetingSummaryBusBase");
		model.addAttribute("formMakingMeetingSummaryBusBase", formMakingMeetingSummaryBusBase);
		model.addAttribute("query", "");
		return "/component/app/makepolicymeeting/MfMakingMeetingSummaryBus_Insert";
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
		FormData formMakingMeetingSummaryBusDetail = formService.getFormData("MakingMeetingSummaryBusDetail");
		getFormValue(formMakingMeetingSummaryBusDetail);
		MfMakingMeetingSummaryBus mfMakingMeetingSummaryBus = new MfMakingMeetingSummaryBus();
		mfMakingMeetingSummaryBus.setId(id);
		mfMakingMeetingSummaryBus = mfMakingMeetingSummaryBusFeign.getById(mfMakingMeetingSummaryBus);
		getObjValue(formMakingMeetingSummaryBusDetail, mfMakingMeetingSummaryBus);
		model.addAttribute("formMakingMeetingSummaryBusDetail", formMakingMeetingSummaryBusDetail);
		model.addAttribute("query", "");
		return "/component/app/makepolicymeeting/MfMakingMeetingSummaryBus_Detail";
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
		FormData formMakingMeetingSummaryBusBase = formService.getFormData("MakingMeetingSummaryBusBase");
		getFormValue(formMakingMeetingSummaryBusBase);
		boolean validateFlag = this.validateFormData(formMakingMeetingSummaryBusBase);
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
		FormData formMakingMeetingSummaryBusBase = formService.getFormData("MakingMeetingSummaryBusBase");
		getFormValue(formMakingMeetingSummaryBusBase);
		boolean validateFlag = this.validateFormData(formMakingMeetingSummaryBusBase);
	}
}
