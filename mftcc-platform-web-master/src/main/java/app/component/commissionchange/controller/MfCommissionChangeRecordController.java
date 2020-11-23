package app.component.commissionchange.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import app.component.commissionchange.entity.MfCommissionChangeRecord;
import app.component.commissionchange.feign.MfCommissionChangeRecordFeign;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfBusTrench;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfBusTrenchFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;

/**
 * 类名： MfCommissionChangeRecordController
 * 描述：分润记录
 * @author 仇招
 * @date 2018年9月4日 下午9:49:07
 */
@Controller
@RequestMapping("/mfCommissionChangeRecord")
public class MfCommissionChangeRecordController extends BaseFormBean {
	@Autowired
	private MfCommissionChangeRecordFeign mfCommissionChangeRecordFeign;
	@Autowired
	private MfBusTrenchFeign mfBusTrenchFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("cusNo", cusNo);
		return "/component/commissionchange/MfCommissionChangeRecord_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @param pageNo
	 * @param tableType
	 * @param tableId
	 * @param pageSize
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, String tableType, String tableId, Integer pageSize,
			String ajaxData,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCommissionChangeRecord mfCommissionChangeRecord = new MfCommissionChangeRecord();
		try {
			mfCommissionChangeRecord.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCommissionChangeRecord.setCriteriaList(mfCommissionChangeRecord, ajaxData);// 我的筛选
			mfCommissionChangeRecord.setCustomSorts(ajaxData);// 自定义排序
			mfCommissionChangeRecord.setTrenchUid(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCommissionChangeRecord", mfCommissionChangeRecord));
			ipage = mfCommissionChangeRecordFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 方法描述： 根据客户号获取分润记录列表
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年9月6日 下午2:41:36
	 */
	@RequestMapping("/getListByCusNoAjax")
	@ResponseBody
	public Map<String, Object> getListByCusNoAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCommissionChangeRecord mfCommissionChangeRecord = new MfCommissionChangeRecord();
			mfCommissionChangeRecord.setTrenchUid(cusNo);
			List<MfCommissionChangeRecord> mfCommissionChangeRecordList = mfCommissionChangeRecordFeign
					.getByCusNo(mfCommissionChangeRecord);
			for (MfCommissionChangeRecord mfCommissionChangeRecord2 : mfCommissionChangeRecordList) {
				mfCommissionChangeRecord2.setRegTime(DateUtil.getShowDateTime(mfCommissionChangeRecord2.getRegTime()));
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String commissionChangeRecordListHtml = jtu.getJsonStr("tablecommissionchangelist", "thirdTableTag",
					mfCommissionChangeRecordList, null, true);
			dataMap.put("commissionChangeRecordListHtml", commissionChangeRecordListHtml);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", MessageEnum.ERROR_SELECT.getMessage());
			dataMap.put("msg", "");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述：  新增资金机构分润记录
	 * @param ajaxData
	 * @param calcBase
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年9月7日 下午4:03:46
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String calcBase,String cusNo) throws Exception {
		Logger logger = LoggerFactory.getLogger(MfCommissionChangeRecordController.class);
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCommissionChangeRecord mfCommissionChangeRecord = new MfCommissionChangeRecord();
			mfCommissionChangeRecord.setAjaxData(ajaxData);
			mfCommissionChangeRecord.setCalcBase(calcBase);
			mfCommissionChangeRecord.setTrenchUid(cusNo);
			mfCommissionChangeRecordFeign.insert(mfCommissionChangeRecord);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			logger.error("新增分润记录失败",e);
		}
		return dataMap;
	}
	/**
	 * 方法描述： 新增资金机构分润记录
	 * @param ajaxData
	 * @param calcBase
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年9月7日 下午4:03:26
	 */
	@RequestMapping("/insertAgenciesAjax")
	@ResponseBody
	public Map<String, Object> insertAgenciesAjax(String ajaxData,String calcBase,String cusNo) throws Exception {
		Logger logger = LoggerFactory.getLogger(MfCommissionChangeRecordController.class);
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCommissionChangeRecord mfCommissionChangeRecord = new MfCommissionChangeRecord();
			mfCommissionChangeRecord.setAjaxData(ajaxData);
			mfCommissionChangeRecord.setCalcBase(calcBase);
			mfCommissionChangeRecord.setTrenchUid(cusNo);
			mfCommissionChangeRecordFeign.insertAgencies(mfCommissionChangeRecord);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			logger.error("新增分润记录失败",e);
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcommissionchangebase = formService.getFormData("commissionchangebase");
		getFormValue(formcommissionchangebase, getMapByJson(ajaxData));
		MfCommissionChangeRecord mfCommissionChangeRecordJsp = new MfCommissionChangeRecord();
		setObjValue(formcommissionchangebase, mfCommissionChangeRecordJsp);
		MfCommissionChangeRecord mfCommissionChangeRecord = mfCommissionChangeRecordFeign
				.getById(mfCommissionChangeRecordJsp);
		if (mfCommissionChangeRecord != null) {
			try {
				mfCommissionChangeRecord = (MfCommissionChangeRecord) EntityUtil.reflectionSetVal(
						mfCommissionChangeRecord, mfCommissionChangeRecordJsp, getMapByJson(ajaxData));
				mfCommissionChangeRecordFeign.update(mfCommissionChangeRecord);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCommissionChangeRecord mfCommissionChangeRecord = new MfCommissionChangeRecord();
		try {
			FormData formcommissionchangebase = formService.getFormData("commissionchangebaseconfirm");
			getFormValue(formcommissionchangebase, getMapByJson(ajaxData));
			if (this.validateFormData(formcommissionchangebase)) {
				mfCommissionChangeRecord = new MfCommissionChangeRecord();
				setObjValue(formcommissionchangebase, mfCommissionChangeRecord);
				mfCommissionChangeRecordFeign.update(mfCommissionChangeRecord);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCommissionChangeRecord.getTrenchUid());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				MfBusTrench mfBusTrench = new MfBusTrench();
				mfBusTrench.setTrenchUid(mfCommissionChangeRecord.getTrenchUid());
				mfBusTrench = mfBusTrenchFeign.getById(mfBusTrench);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String formId = "trenchBaseDetail";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfBusTrenchAction");
				if (mfCusFormConfig != null) {
					formId = mfCusFormConfig.getShowModelDef();
				}
				FormData formcommon = formService.getFormData(formId);
				getFormValue(formcommon);
				getObjValue(formcommon, mfBusTrench);
				request.setAttribute("ifBizManger", "3");
				String basehtmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", "");
				dataMap.put("basehtmlStr", basehtmlStr);
				
//				// 查询佣金变更记录
				mfCommissionChangeRecord = new MfCommissionChangeRecord();
				mfCommissionChangeRecord.setTrenchUid(mfBusTrench.getTrenchUid());
				List<MfCommissionChangeRecord> mfCommissionChangeRecordList = mfCommissionChangeRecordFeign.getByCusNo(mfCommissionChangeRecord);
				for (MfCommissionChangeRecord mfCommissionChangeRecord2 : mfCommissionChangeRecordList) {
					mfCommissionChangeRecord2.setRegTime(DateUtil.getShowDateTime(mfCommissionChangeRecord2.getRegTime()));
 				}
				JsonTableUtil jtu = new JsonTableUtil();
				String  commissionChangeRecordListHtml = jtu.getJsonStr("tablecommissionchangelist","thirdTableTag",mfCommissionChangeRecordList, null,true);
				dataMap.put("commissionChangeRecordListHtml", commissionChangeRecordListHtml);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param commissionChangeId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String commissionChangeId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcommissionchangebase = formService.getFormData("commissionchangebase");
		MfCommissionChangeRecord mfCommissionChangeRecord = new MfCommissionChangeRecord();
		mfCommissionChangeRecord.setCommissionChangeId(commissionChangeId);
		mfCommissionChangeRecord = mfCommissionChangeRecordFeign.getById(mfCommissionChangeRecord);
		getObjValue(formcommissionchangebase, mfCommissionChangeRecord, formData);
		if (mfCommissionChangeRecord != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @param commissionChangeId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String commissionChangeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCommissionChangeRecord mfCommissionChangeRecord = new MfCommissionChangeRecord();
		mfCommissionChangeRecord.setCommissionChangeId(commissionChangeId);
		try {
			mfCommissionChangeRecordFeign.delete(mfCommissionChangeRecord);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcommissionchangebase = formService.getFormData("commissionchangebase");
		model.addAttribute("formcommissionchangebase", formcommissionchangebase);
		model.addAttribute("query", "");
		return "/component/commissionchange/MfCommissionChangeRecord_Insert";
	}
	@RequestMapping("/inputConfirm")
	public String inputConfirm(Model model, String commissionChangeId,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcommissionchangebase = formService.getFormData("commissionchangebaseconfirm");
		MfCommissionChangeRecord mfCommissionChangeRecord = new MfCommissionChangeRecord();
		mfCommissionChangeRecord.setCommissionChangeId(commissionChangeId);
		mfCommissionChangeRecord = mfCommissionChangeRecordFeign.getById(mfCommissionChangeRecord);
		MfBusTrench mfBusTrench = new MfBusTrench();
		mfBusTrench.setTrenchUid(cusNo);
		mfBusTrench = mfBusTrenchFeign.getById(mfBusTrench);
		mfCommissionChangeRecord.setRegTime(DateUtil.getShowDateTime(mfCommissionChangeRecord.getRegTime()));
		getObjValue(formcommissionchangebase, mfCommissionChangeRecord);
		model.addAttribute("formcommissionchangebase", formcommissionchangebase);
		model.addAttribute("query", "");
		return "/component/commissionchange/MfCommissionChangeRecord_InsertConfirm";
	}

	/**
	 * 查询
	 * 
	 * @param commissionChangeId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String commissionChangeId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcommissionchangedetail = formService.getFormData("commissionchangedetail");
		getFormValue(formcommissionchangedetail);
		MfCommissionChangeRecord mfCommissionChangeRecord = new MfCommissionChangeRecord();
		mfCommissionChangeRecord.setCommissionChangeId(commissionChangeId);
		mfCommissionChangeRecord = mfCommissionChangeRecordFeign.getById(mfCommissionChangeRecord);
		getObjValue(formcommissionchangedetail, mfCommissionChangeRecord);
		model.addAttribute("formcommissionchangedetail", formcommissionchangedetail);
		model.addAttribute("query", "");
		return "/component/commissionchange/MfCommissionChangeRecord_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcommissionchangebase = formService.getFormData("commissionchangebase");
		getFormValue(formcommissionchangebase);
		boolean validateFlag = this.validateFormData(formcommissionchangebase);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcommissionchangebase = formService.getFormData("commissionchangebase");
		getFormValue(formcommissionchangebase);
		boolean validateFlag = this.validateFormData(formcommissionchangebase);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String commissionChangeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		FormData formcommissionchangedetail = new FormService().getFormData("commissionchangedetail");
		MfCommissionChangeRecord mfCommissionChangeRecord = new MfCommissionChangeRecord();
		mfCommissionChangeRecord.setCommissionChangeId(commissionChangeId);
		mfCommissionChangeRecord = mfCommissionChangeRecordFeign.getById(mfCommissionChangeRecord);
		mfCommissionChangeRecord.setRegTime(DateUtil.getShowDateTime(mfCommissionChangeRecord.getRegTime()));
		getObjValue(formcommissionchangedetail, mfCommissionChangeRecord, formData);
		String htmlStrCorp = jsonFormUtil.getJsonStr(formcommissionchangedetail, "propertySeeTag", "query");
		dataMap.put("formHtml", htmlStrCorp);
		dataMap.put("flag", "success");
		dataMap.put("formData", mfCommissionChangeRecord);

		return dataMap;
	}
}
