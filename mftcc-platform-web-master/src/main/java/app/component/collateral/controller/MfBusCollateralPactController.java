package app.component.collateral.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.component.collateral.entity.MfBusCollateralPact;
import app.component.collateral.feign.MfBusCollateralPactFeign;
import app.component.common.EntityUtil;
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
 * Title: MfBusCollateralDetailRelController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Nov 16 10:24:44 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfBusCollateralPact")
public class MfBusCollateralPactController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusCollateralPactFeign mfBusCollateralPactFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/MfBusCollateralPact_List";
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
		MfBusCollateralPact mfBusCollateralPact = new MfBusCollateralPact();
		try {
			mfBusCollateralPact.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusCollateralPact.setCriteriaList(mfBusCollateralPact, ajaxData);//我的筛选
			//mfBusCollateralDetailRel.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfBusCollateralDetailRel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfBusCollateralDetailRel", mfBusCollateralPact));
			//自定义查询Feign方法
			ipage = mfBusCollateralPactFeign.findByPage(ipage);
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
			FormData formcuscollateralpactdetail = formService.getFormData("cuscollateralpactdetail");
			getFormValue(formcuscollateralpactdetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcuscollateralpactdetail)){
				MfBusCollateralPact mfBusCollateralPact = new MfBusCollateralPact();
				setObjValue(formcuscollateralpactdetail, mfBusCollateralPact);
				mfBusCollateralPactFeign.insert(mfBusCollateralPact);
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
		FormData formcuscollateralpactdetail = formService.getFormData("cuscollateralpactdetail");
		getFormValue(formcuscollateralpactdetail, getMapByJson(ajaxData));
		MfBusCollateralPact mfBusCollateralPactJsp = new MfBusCollateralPact();
		setObjValue(formcuscollateralpactdetail, mfBusCollateralPactJsp);
		MfBusCollateralPact mfBusCollateralPact = mfBusCollateralPactFeign.getById(mfBusCollateralPactJsp);
		if(mfBusCollateralPact!=null){
			try{
				mfBusCollateralPact = (MfBusCollateralPact)EntityUtil.reflectionSetVal(mfBusCollateralPact, mfBusCollateralPactJsp, getMapByJson(ajaxData));
				mfBusCollateralPactFeign.update(mfBusCollateralPact);
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
		MfBusCollateralPact mfBusCollateralPact = new MfBusCollateralPact();
		try{
			FormData formcuscollateralpactdetail = formService.getFormData("cuscollateralpactdetail");
			getFormValue(formcuscollateralpactdetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcuscollateralpactdetail)){
				mfBusCollateralPact = new MfBusCollateralPact();
				setObjValue(formcuscollateralpactdetail, mfBusCollateralPact);
				mfBusCollateralPactFeign.update(mfBusCollateralPact);
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
		FormData formcuscollateralpactdetail = formService.getFormData("cuscollateralpactdetail");
		MfBusCollateralPact mfBusCollateralPact = new MfBusCollateralPact();
		mfBusCollateralPact.setId(id);
		mfBusCollateralPact = mfBusCollateralPactFeign.getById(mfBusCollateralPact);
		getObjValue(formcuscollateralpactdetail, mfBusCollateralPact,formData);
		if(mfBusCollateralPact!=null){
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
		MfBusCollateralPact mfBusCollateralPact = new MfBusCollateralPact();
		mfBusCollateralPact.setId(id);
		try {
			mfBusCollateralPactFeign.delete(mfBusCollateralPact);
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
		FormData formcuscollateralpactdetail = formService.getFormData("cuscollateralpactdetail");
		model.addAttribute("formcuscollateralpactdetail", formcuscollateralpactdetail);
		model.addAttribute("query", "");
		return "/component/collateral/MfBusCollateralPact_Insert";
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
		FormData formcuscollateralpactbase = formService.getFormData("cuscollateralpactbase");
		getFormValue(formcuscollateralpactbase);
		MfBusCollateralPact mfBusCollateralPact = new MfBusCollateralPact();
		mfBusCollateralPact.setId(id);
		mfBusCollateralPact = mfBusCollateralPactFeign.getById(mfBusCollateralPact);
		getObjValue(formcuscollateralpactbase, mfBusCollateralPact);
		model.addAttribute("formcuscollateralpactbase", formcuscollateralpactbase);
		model.addAttribute("query", "");
		return "/component/collateral/MfBusCollateralPact_Detail";
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
		FormData formcuscollateralpactdetail = formService.getFormData("cuscollateralpactdetail");
		getFormValue(formcuscollateralpactdetail);
		boolean validateFlag = this.validateFormData(formcuscollateralpactdetail);
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
		FormData formcuscollateralpactdetail = formService.getFormData("cuscollateralpactdetail");
		getFormValue(formcuscollateralpactdetail);
		boolean validateFlag = this.validateFormData(formcuscollateralpactdetail);
	}
}
