package app.component.frontview.controller;

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

import app.component.frontview.entity.MfFrontAppform;
import app.component.frontview.feign.MfFrontAppformFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfFrontAppformAction.java Description:前端产品要素设置
 * 
 * @author:mahao@dhcc.com.cn
 * @Thu Jun 22 10:18:20 CST 2017
 **/
@Controller
@RequestMapping("/mfFrontAppform")
public class MfFrontAppformController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfFrontAppformBo
	@Autowired
	private MfFrontAppformFeign mfFrontAppformFeign;

	/**
	 * 新增页面初始化 暂未与数据库中的值同步
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMfFrontAppformActiveSetting")
	public String getMfFrontAppformActiveSetting(Model model, String id, String fieldLabel, String fieldName, String kindNo, String defaultVal, String fieldUnit) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfFrontAppform mfFrontAppform = new MfFrontAppform();
		mfFrontAppform.setId(id);
		mfFrontAppform.setFieldLabel(fieldLabel);
		mfFrontAppform.setFieldName(fieldName);
		mfFrontAppform.setKindNo(kindNo);
		mfFrontAppform.setDefaultVal(defaultVal);
		mfFrontAppform.setFieldUnit(fieldUnit);
		FormData formappformset0002 = formService.getFormData("appformset0002");
		MfFrontAppform condition = new MfFrontAppform();
		condition.setKindNo(kindNo);
		condition.setFieldName(fieldName);
		MfFrontAppform newMfFrontAppform = mfFrontAppformFeign.getByKindNoAndFieldName(condition);
		if (null != newMfFrontAppform) {
			mfFrontAppform = newMfFrontAppform;
		}
		getObjValue(formappformset0002, mfFrontAppform);
		model.addAttribute("mfFrontAppform", mfFrontAppform);
		model.addAttribute("formappformset0002", formappformset0002);
		model.addAttribute("query", "");
		return "/component/frontview/MfFrontAppformActiveSetting";
	}

	/**
	 * 初始化表基础数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/initformActiveSettingAjax")
	@ResponseBody
	public Map<String, Object> initformActiveSettingAjax(String id, String fieldLabel, String fieldName, String kindNo, String defaultVal, String fieldUnit) throws Exception {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfFrontAppform mfFrontAppform = new MfFrontAppform();
			mfFrontAppform.setId(id);
			mfFrontAppform.setFieldLabel(fieldLabel);
			mfFrontAppform.setFieldName(fieldName);
			mfFrontAppform.setKindNo(kindNo);
			mfFrontAppform.setDefaultVal(defaultVal);
			mfFrontAppform.setFieldUnit(fieldUnit);
			// 全部默认启用状态
			mfFrontAppform.setMobileUse("1");
			mfFrontAppform.setMobleShow("1");
			mfFrontAppform.setPcUse("1");
			mfFrontAppform.setPcShow("1");
			mfFrontAppformFeign.insert(mfFrontAppform);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("初始化产品要素配置异常", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formappformset0002 = formService.getFormData("appformset0002");
			getFormValue(formappformset0002, getMapByJson(ajaxData));
			if (this.validateFormData(formappformset0002)) {
				MfFrontAppform mfFrontAppform = new MfFrontAppform();
				setObjValue(formappformset0002, mfFrontAppform);
				mfFrontAppformFeign.insert(mfFrontAppform);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("新增产品要素配置异常", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delformActiveSettingAjax")
	@ResponseBody
	public Map<String, Object> delformActiveSettingAjax(String fieldName ,String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFrontAppform mfFrontAppform = new MfFrontAppform();
		// mfFrontAppform.setId(id);
		mfFrontAppform.setFieldName(fieldName);// 根据这两个联合主键删除
		mfFrontAppform.setKindNo(kindNo);
		try {
			mfFrontAppformFeign.deleteByKindNoAndFieldName(mfFrontAppform);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX更新产品描述信息
	 * 
	 * @return
	 * @throws Exception
	 */
	/*
	 * @RequestMapping(value = "/updateContentAjax")
	 * 
	 * @ResponseBody public Map<String, Object> updateContentAjax(String
	 * ajaxData) throws Exception { FormService formService = new FormService();
	 * ActionContext.initialize(request, response); Map<String, Object> dataMap
	 * = new HashMap<String, Object>(); try{ Map<String, Object> parmMap =
	 * getMapByJson(ajaxData); kindNo = (String) parmMap.get("kindNo"); String
	 * str = (String) parmMap.get("content"); MfSysKind mfSysKind = new
	 * MfSysKind(); mfSysKind.setKindNo(kindNo);
	 * mfSysKind.setApplyRequirement(str);
	 * prdctInterfaceFeign.update(mfSysKind); dataMap.put("flag", "success");
	 * dataMap.put("msg", "更新成功"); }catch(Exception e){ e.printStackTrace();
	 * logger.error("更新产品描述异常",e); dataMap.put("flag", "error");
	 * dataMap.put("msg", "新增失败"); throw e; } return dataMap; }
	 *//**
		 * AJAX获取产品描述信息
		 * 
		 * @return
		 * @throws Exception
		 */
	/*
	 * @RequestMapping(value = "/getContentByKindNoAjax")
	 * 
	 * @ResponseBody public Map<String, Object> getContentByKindNoAjax(String
	 * ajaxData) throws Exception { FormService formService = new FormService();
	 * ActionContext.initialize(request, response); Map<String, Object> dataMap
	 * = new HashMap<String, Object>(); try{ MfSysKind sysKind =
	 * prdctInterfaceFeign.getSysKindById(kindNo); if(null != sysKind){
	 * dataMap.put("flag", "success"); dataMap.put("msg", "获取产品描述信息成功");
	 * dataMap.put("str", sysKind.getApplyRequirement()); }else{
	 * dataMap.put("flag", "error"); dataMap.put("msg", "未找到编号"+kindNo+"产品"); }
	 * }catch(Exception e){ e.printStackTrace();
	 * logger.error("获取产品描述信息异常，产品编号:{}",kindNo,e); dataMap.put("flag",
	 * "error"); dataMap.put("msg", "获取产品描述信息失败"); throw e; } return dataMap; }
	 *//**
		 * 打开产品描述页面
		 * 
		 * @return
		 * @throws Exception
		 */

	/*
	 * @RequestMapping(value = "/getMfFrontKindDescription") public String
	 * getMfFrontKindDescription(Model model, String ajaxData) throws Exception{
	 * Map<String, Object> dataMap = new HashMap<String,Object>();
	 * dataMap.put("kindNo", kindNo); dataMap.put("kindName", kindName);
	 * FormData formfrontkinddescription0001 =
	 * formService.getFormData("frontkinddescription0001");
	 * getObjValue(formfrontkinddescription0001, dataMap);
	 * model.addAttribute("formappformset0002", formappformset0002);
	 * model.addAttribute("query", ""); return "/component/frontview/MfFrontKindDescription"; }
	 */
	/*
	*//**
		 * 列表打开页面请求
		 * 
		 * @return
		 * @throws Exception
		 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/frontview/MfFrontAppform_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFrontAppform mfFrontAppform = new MfFrontAppform();
		try {
			mfFrontAppform.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfFrontAppform.setCriteriaList(mfFrontAppform, ajaxData);// 我的筛选
			// mfFrontAppform.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfFrontAppform,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfFrontAppformFeign.findByPage(ipage, mfFrontAppform);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	/*
	 * @RequestMapping(value = "/updateAjaxByOne")
	 * 
	 * @ResponseBody public Map<String, Object> updateAjaxByOne(String ajaxData)
	 * throws Exception{ FormService formService = new FormService();
	 * ActionContext.initialize(request, response); Map<String, Object> dataMap
	 * = new HashMap<String, Object>(); FormData formappformset0002 =
	 * formService.getFormData("appformset0002");
	 * getFormValue(formappformset0002, getMapByJson(ajaxData)); MfFrontAppform
	 * mfFrontAppformJsp = new MfFrontAppform(); setObjValue(formappformset0002,
	 * mfFrontAppformJsp); mfFrontAppform =
	 * mfFrontAppformFeign.getById(mfFrontAppformJsp); if(mfFrontAppform!=null){
	 * try{ mfFrontAppform =
	 * (MfFrontAppform)EntityUtil.reflectionSetVal(mfFrontAppform,
	 * mfFrontAppformJsp, getMapByJson(ajaxData));
	 * mfFrontAppformFeign.update(mfFrontAppform); dataMap.put("flag",
	 * "success"); dataMap.put("msg", "保存成功"); }catch(Exception e){
	 * e.printStackTrace(); dataMap.put("flag", "error"); dataMap.put("msg",
	 * "新增失败"); throw e; } }else{ dataMap.put("flag", "error");
	 * dataMap.put("msg", "编号不存在,保存失败"); } return dataMap; }
	 *//**
		 * AJAX更新保存
		 * 
		 * @return
		 * @throws Exception
		 */
	/*
	 * @RequestMapping(value = "/updateAjax")
	 * 
	 * @ResponseBody public Map<String, Object> updateAjax(String ajaxData)
	 * throws Exception { FormService formService = new FormService();
	 * ActionContext.initialize(request, response); Map<String, Object> dataMap
	 * = new HashMap<String, Object>(); MfFrontAppform mfFrontAppform = new
	 * MfFrontAppform(); try{ FormData formappformset0002 =
	 * formService.getFormData("appformset0002");
	 * getFormValue(formappformset0002, getMapByJson(ajaxData));
	 * if(this.validateFormData(formappformset0002)){ MfFrontAppform
	 * mfFrontAppform = new MfFrontAppform(); setObjValue(formappformset0002,
	 * mfFrontAppform); mfFrontAppformFeign.update(mfFrontAppform);
	 * dataMap.put("flag", "success"); dataMap.put("msg", "更新成功"); }else{
	 * dataMap.put("flag", "error");
	 * dataMap.put("msg",this.getFormulavaliErrorMsg()); } }catch(Exception e){
	 * e.printStackTrace(); dataMap.put("flag", "error"); dataMap.put("msg",
	 * "更新失败"); throw e; } return dataMap; }
	 * 
	 *//**
		 * AJAX获取查看
		 * 
		 * @return
		 * @throws Exception
		 *//*
		 * @RequestMapping(value = "/getByIdAjax")
		 * 
		 * @ResponseBody public Map<String, Object> getByIdAjax(String ajaxData)
		 * throws Exception { FormService formService = new FormService();
		 * ActionContext.initialize(request, response); Map<String,Object>
		 * formData = new HashMap<String,Object>(); Map<String, Object> dataMap
		 * = new HashMap<String, Object>(); FormData formappformset0002 =
		 * formService.getFormData("appformset0002"); MfFrontAppform
		 * mfFrontAppform = new MfFrontAppform(); mfFrontAppform.setId(id);
		 * mfFrontAppform = mfFrontAppformFeign.getById(mfFrontAppform);
		 * getObjValue(formappformset0002, mfFrontAppform,formData);
		 * if(mfFrontAppform!=null){ dataMap.put("flag", "success"); }else{
		 * dataMap.put("flag", "error"); } dataMap.put("formData", formData);
		 * return dataMap; }
		 */

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	/*
	 * @RequestMapping(value = "/input") public String input(Model model) throws
	 * Exception{ FormService formService = new FormService();
	 * ActionContext.initialize(request,response); FormData formappformset0002 =
	 * formService.getFormData("appformset0002");
	 * model.addAttribute("formappformset0002", formappformset0002);
	 * model.addAttribute("query", ""); return "/component/frontview/MfFrontAppform_Insert"; }
	 *//**
		 * 查询
		 * 
		 * @return
		 * @throws Exception
		 */
	/*
	 * @RequestMapping(value = "/getById") public String getById(Model model,
	 * String ajaxData) throws Exception{ FormService formService = new
	 * FormService(); ActionContext.initialize(request,response); FormData
	 * formappformset0002 = formService.getFormData("appformset0001");
	 * getFormValue(formappformset0002); MfFrontAppform mfFrontAppform = new
	 * MfFrontAppform(); mfFrontAppform.setId(id); mfFrontAppform =
	 * mfFrontAppformFeign.getById(mfFrontAppform);
	 * getObjValue(formappformset0002, mfFrontAppform);
	 * model.addAttribute("formappformset0002", formappformset0002);
	 * model.addAttribute("query", ""); return "/component/frontview/MfFrontAppform_Detail"; }
	 * 
	 *//**
		 * 新增校验
		 * 
		 * @return
		 * @throws Exception
		 */
	/*
	 * @RequestMapping(value = "/validateInsert") public void
	 * validateInsert(Model model) throws Exception{ FormService formService =
	 * new FormService(); ActionContext.initialize(request, response); FormData
	 * formappformset0002 = formService.getFormData("appformset0002");
	 * getFormValue(formappformset0002); boolean validateFlag =
	 * this.validateFormData(formappformset0002); }
	 *//**
		 * 修改校验
		 * 
		 * @return
		 * @throws Exception
		 *//*
		 * @RequestMapping(value = "/validateUpdate") public void
		 * validateUpdate(Model model) throws Exception{ FormService formService
		 * = new FormService(); ActionContext.initialize(request, response);
		 * FormData formappformset0002 =
		 * formService.getFormData("appformset0002");
		 * getFormValue(formappformset0002); boolean validateFlag =
		 * this.validateFormData(formappformset0002); }
		 */

}
