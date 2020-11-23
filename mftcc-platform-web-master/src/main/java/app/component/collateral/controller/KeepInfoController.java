
package app.component.collateral.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.collateral.entity.KeepInfo;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.component.collateral.entity.CollateralConstant;
import app.component.collateral.entity.PledgeBaseInfoBill;
import app.component.collateral.feign.PledgeBaseInfoBillFeign;
import app.tech.upload.FeignSpringFormEncoder;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.collateral.feign.KeepInfoFeign;
import app.component.collateral.feign.MfCollateralFormConfigFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.BizPubParm;
import app.component.pact.entity.MfBusPact;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdct.entity.MfKindFlow;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

/**
 * Title: KeepInfoController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Mar 15 13:23:37 CST 2017
 **/
@Controller
@RequestMapping("/keepInfo")
public class KeepInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private KeepInfoFeign keepInfoFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private MfCollateralFormConfigFeign mfCollateralFormConfigFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private PledgeBaseInfoBillFeign pledgeBaseInfoBillFeign;
	private void getTableData(Map<String, Object> dataMap, String tableId, KeepInfo keepInfo) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", keepInfoFeign.getAll(keepInfo), null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralkeep0002 = formService.getFormData("dlcollateralkeep0002");
		KeepInfo keepInfo = new KeepInfo();
		Ipage ipage = this.getIpage();
		List<KeepInfo> keepInfoList = (List<KeepInfo>) keepInfoFeign.findByPage(ipage, keepInfo).getResult();
		model.addAttribute("formdlcollateralkeep0002", formdlcollateralkeep0002);
		model.addAttribute("keepInfoList", keepInfoList);
		model.addAttribute("query", "");
		return "/component/collateral/KeepInfo_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralkeep0002 = formService.getFormData("dlcollateralkeep0002");
		KeepInfo keepInfo = new KeepInfo();
		List<KeepInfo> keepInfoList = keepInfoFeign.getAll(keepInfo);
		model.addAttribute("formdlcollateralkeep0002", formdlcollateralkeep0002);
		model.addAttribute("keepInfoList", keepInfoList);
		model.addAttribute("query", "");
		return "/component/collateral/KeepInfo_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String classId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlkeepinfo0002 = formService.getFormData("dlkeepinfo0002");
			getFormValue(formdlkeepinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlkeepinfo0002)) {
				KeepInfo keepInfo = new KeepInfo();
				setObjValue(formdlkeepinfo0002, keepInfo);
				keepInfoFeign.insert(keepInfo);

				// getTableData();//获取列表

				// PledgeBase pledgeBase = new PledgeBase();
				// pledgeBase.setPledgeId(keepInfo.getCollateralId());
				// pledgeBase = pledgeBaseFeign.getById(pledgeBase);

				// 获得基本信息的展示表单ID，并将表单解析
				MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign
						.getByPledgeImPawnType(classId, "KeepInfoAction", "");
				String formId = "";
				if (mfCollateralFormConfig == null) {

				} else {
					formId = mfCollateralFormConfig.getShowModelDef();
				}
				FormData formdlkeepinfo0004 = formService.getFormData(formId);
				if (formdlkeepinfo0004.getFormId() == null) {
					// logger.error("押品类型为" +
					// mfCollateralFormConfig.getFormType() +
					// "的KeepInfoController表单form" + formId
					// + ".xml文件不存在");
				}
				getFormValue(formdlkeepinfo0004);
				getObjValue(formdlkeepinfo0004, keepInfo);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formdlkeepinfo0004, "propertySeeTag", "");

				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag", "1");

				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
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
	@RequestMapping("/instockAjax1")
	@ResponseBody
	public Map<String, Object> instockAjax1(String ajaxData, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlkeepinfo0007 = formService.getFormData("dlkeepinfo0007");
			getFormValue(formdlkeepinfo0007, getMapByJson(ajaxData));
			if (this.validateFormData(formdlkeepinfo0007)) {
				KeepInfo keepInfo = new KeepInfo();
				setObjValue(formdlkeepinfo0007, keepInfo);
				keepInfo.setAppId(appId);
				keepInfo = keepInfoFeign.insertForInOutStock(keepInfo, CollateralConstant.INSTOCK);
				dataMap.put("keepInfo", keepInfo);
				dataMap.put("flag", "success");
				dataMap.put("isExamineInOutStock", keepInfo.getIsExamineInOutStock());
				if (BizPubParm.YES_NO_Y.equals(keepInfo.getIsExamineInOutStock())) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("userRole", keepInfo.getApproveNodeName());
					paramMap.put("opNo", keepInfo.getApprovePartName());
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				} else {
					dataMap.put("msg", "入库成功");
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		/*
		 * if (dataMap != null || !dataMap.get("flag").equals("error")) {
		 * pledgeBaseInfo = new PledgeBaseInfo(); String collateralId =
		 * keepInfo.getCollateralId(); pledgeBaseInfo.setPledgeNo(collateralId);
		 * pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		 * pledgeBaseInfo.setKeepStatus(CollateralConstant.INSTOCK);
		 * pledgeBaseInfo.setImportDate(keepInfo.getOperateDate());
		 * pledgeBaseInfoFeign.update(pledgeBaseInfo); }
		 */
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/outstockAjax1")
	@ResponseBody
	public Map<String, Object> outstockAjax1(String ajaxData, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlkeepout0008 = formService.getFormData("dlkeepout0008");
			getFormValue(formdlkeepout0008, getMapByJson(ajaxData));
			if (this.validateFormData(formdlkeepout0008)) {
				KeepInfo keepInfo = new KeepInfo();
				setObjValue(formdlkeepout0008, keepInfo);
				keepInfo.setAppId(appId);
				keepInfo = keepInfoFeign.insertForInOutStock(keepInfo, CollateralConstant.OUTSTOCK);
				dataMap.put("keepInfo", keepInfo);
				dataMap.put("flag", "success");
				dataMap.put("isExamineInOutStock", keepInfo.getIsExamineInOutStock());
				if (BizPubParm.YES_NO_Y.equals(keepInfo.getIsExamineInOutStock())) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("userRole", keepInfo.getApproveNodeName());
					paramMap.put("opNo", keepInfo.getApprovePartName());
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				} else {
					dataMap.put("msg", "出库成功");
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		/*
		 * if (dataMap != null && dataMap.size() > 0 &&
		 * !dataMap.get("flag").equals("error")) { pledgeBaseInfo = new
		 * PledgeBaseInfo(); String collateralId = keepInfo.getCollateralId();
		 * pledgeBaseInfo.setPledgeNo(collateralId); pledgeBaseInfo =
		 * pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		 * pledgeBaseInfo.setKeepStatus(CollateralConstant.OUTSTOCK);
		 * pledgeBaseInfo.setExportDate(keepInfo.getOperateDate());
		 * pledgeBaseInfoFeign.update(pledgeBaseInfo);
		 * 
		 * }
		 */
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/certiInfo")
	@ResponseBody
	public Map<String, Object> handleAjax1(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlkeephandle0009 = formService.getFormData("dlkeephandle0009");
			getFormValue(formdlkeephandle0009, getMapByJson(ajaxData));
			if (this.validateFormData(formdlkeephandle0009)) {
				KeepInfo keepInfo = new KeepInfo();
				setObjValue(formdlkeephandle0009, keepInfo);
				keepInfo = keepInfoFeign.insertForInOutStock(keepInfo, CollateralConstant.HANDLECOLLATERAL);
				dataMap.put("keepInfo", keepInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "处置成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		/*
		 * if (dataMap != null && dataMap.size() > 0 &&
		 * !dataMap.get("flag").equals("error")) { pledgeBaseInfo = new
		 * PledgeBaseInfo(); String collateralId = keepInfo.getCollateralId();
		 * pledgeBaseInfo.setPledgeNo(collateralId); pledgeBaseInfo =
		 * pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		 * pledgeBaseInfo.setKeepStatus(CollateralConstant.HANDLECOLLATERAL);
		 * pledgeBaseInfo.setExportDate(keepInfo.getOperateDate());
		 * pledgeBaseInfoFeign.update(pledgeBaseInfo); }
		 */
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcollateralkeep0002 = formService.getFormData("dlcollateralkeep0002");
			getFormValue(formdlcollateralkeep0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralkeep0002)) {
				KeepInfo keepInfo = new KeepInfo();
				setObjValue(formdlcollateralkeep0002, keepInfo);
				keepInfoFeign.update(keepInfo);
				getTableData(dataMap, tableId, keepInfo);// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String keepId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdlcollateralkeep0002 = formService.getFormData("dlcollateralkeep0002");
		KeepInfo keepInfo = new KeepInfo();
		keepInfo.setKeepId(keepId);
		keepInfo = keepInfoFeign.getById(keepInfo);
		getObjValue(formdlcollateralkeep0002, keepInfo, formData);
		if (keepInfo != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * AJAX获取查看 判断信息是否存在
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/isDataExistsAjax")
	@ResponseBody
	public Map<String, Object> isDataExistsAjax(String collateralId, String keepType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		KeepInfo keepInfo = new KeepInfo();
		keepInfo.setCollateralId(collateralId);
		keepInfo.setKeepType(keepType);
		String dataExists = keepInfoFeign.isDataExists(keepInfo);
		if (StringUtil.isEmpty(dataExists)) {
			// dataExists为空，信息不存在
			dataMap.put("result", "1");
		} else {
			// dataExists不为空，信息存在
			dataMap.put("result", "0");
		}
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String keepId, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		KeepInfo keepInfo = new KeepInfo();
		keepInfo.setKeepId(keepId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			keepInfo = (KeepInfo) JSONObject.toBean(jb, KeepInfo.class);
			keepInfoFeign.delete(keepInfo);
			getTableData(dataMap, tableId, keepInfo);// 获取列表
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/getInsertPage")
	public String getInsertPage(Model model, String collateralId, String collateralName, String collateralMethod,String appId)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String keepId = WaterIdUtil.getWaterId();
		KeepInfo keepInfo = new KeepInfo();
		keepInfo.setKeepId(keepId);
		keepInfo.setCollateralId(collateralId);
		keepInfo.setCollateralName(collateralName);
		keepInfo.setCollateralMethod(collateralMethod);
		keepInfo.setKeepType(CollateralConstant.KEEP_IN);
		keepInfo.setRegDate(DateUtil.getDate());
		keepInfo.setOperatorCusName(User.getRegName(request));
		FormData formdlkeepinfo0007 = formService.getFormData("dlkeepinfo0007");
		getObjValue(formdlkeepinfo0007, keepInfo);
		String processId=BizPubParm.INSTOCK_APPROVE_WKF_NO;
		MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setModelType(BizPubParm.MODEL_TYPE_BASE);
		mfKindFlow.setCategory(BizPubParm.FLOW_CATEGORY_4);
		mfKindFlow.setFlowApprovalNo("inOutStock");
		mfKindFlow = prdctInterfaceFeign.getKindFlow(mfKindFlow);
		if(mfKindFlow != null && BizPubParm.YES_NO_Y.equals(mfKindFlow.getUseFlag())){
			if(StringUtil.isNotEmpty(mfKindFlow.getFlowId())){
				processId = mfKindFlow.getFlowId();
			}
		}
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralId);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		if(pledgeBaseInfo != null){//入库页面展示押品展示号码
			model.addAttribute("pledgeBaseInfo", pledgeBaseInfo);
			this.changeFormProperty(formdlkeepinfo0007, "pledgeShowNo", "initValue", pledgeBaseInfo.getPledgeShowNo());
		}
		model.addAttribute("formdlkeepinfo0007", formdlkeepinfo0007);
		model.addAttribute("processId", processId);
		model.addAttribute("query", "");
		model.addAttribute("appId",appId);
		return "/component/collateral/KeepInfo_instock_Insert";
	}

	@RequestMapping("/getViewPage")
	public String getViewPage(Model model, String collateralId, String keepType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String str = null;
		KeepInfo ki = new KeepInfo();
		ki.setCollateralId(collateralId);
		ki.setKeepType(keepType);
		KeepInfo keepInfo = keepInfoFeign.getByColIdKPT(ki);
		if (keepType.equals(CollateralConstant.KEEP_IN)) {
			FormData formdlkeepinfoview0007 = formService.getFormData("dlkeepinfoview0007");
			getObjValue(formdlkeepinfoview0007, keepInfo);
			str = "/component/collateral/KeepInfo_Instock_View";
			model.addAttribute("query", "");
			model.addAttribute("keepInfo", keepInfo);
			model.addAttribute("formdlkeepinfoview0007",formdlkeepinfoview0007);
		} else if (keepType.equals(CollateralConstant.KEEP_OUT)) {
			FormData formdlkeepoutview0008 = formService.getFormData("dlkeepoutview0008");
			getObjValue(formdlkeepoutview0008, keepInfo);
			str = "/component/collateral/KeepInfo_Outstock_View";
			model.addAttribute("query", "");
			model.addAttribute("query", "");
			model.addAttribute("formdlkeepoutview0008",formdlkeepoutview0008);
		} else if (keepType.equals(CollateralConstant.KEEP_HANDLE)) {
			FormData formdlkeephandleview0009 = formService.getFormData("dlkeephandleview0009");
			getObjValue(formdlkeephandleview0009, keepInfo);
			str = "/component/collateral/KeepInfo_Handle_View";
			model.addAttribute("query", "");
			model.addAttribute("formdlkeephandleview0009",formdlkeephandleview0009);
		}else {
		}
		return str;

	}

	@RequestMapping("/getOutStockPage")
	public String getOutStockPage(Model model, String collateralId,String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		KeepInfo keepQuery = new KeepInfo();
		keepQuery.setCollateralId(collateralId);
		keepQuery.setKeepType(CollateralConstant.KEEP_IN);
		keepQuery.setKeepAppSts("2");
		KeepInfo keepInfo = keepInfoFeign.getByColIdKPT(keepQuery);
		String keepId = WaterIdUtil.getWaterId();
		keepInfo.setKeepId(keepId);
		keepInfo.setUpdateDate(DateUtil.getDate());
		keepInfo.setKeepType(CollateralConstant.KEEP_OUT);
		keepInfo.setOperateDate(DateUtil.getDate());
		keepInfo.setKeepDesc("");
		keepInfo.setApprovePartName("");
		keepInfo.setApprovePartNo("");
		FormData formdlkeepout0008 = formService.getFormData("dlkeepout0008");
		getObjValue(formdlkeepout0008, keepInfo);
		String processId=BizPubParm.INSTOCK_APPROVE_WKF_NO;
		MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setModelType(BizPubParm.MODEL_TYPE_BASE);
		mfKindFlow.setCategory(BizPubParm.FLOW_CATEGORY_4);
		mfKindFlow.setFlowApprovalNo("inOutStock");
		mfKindFlow = prdctInterfaceFeign.getKindFlow(mfKindFlow);
		if(mfKindFlow != null && BizPubParm.YES_NO_Y.equals(mfKindFlow.getUseFlag())){
			if(StringUtil.isNotEmpty(mfKindFlow.getFlowId())){
				processId = mfKindFlow.getFlowId();
			}
		}
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralId);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		if(pledgeBaseInfo != null){//出库页面展示押品展示号码
			model.addAttribute("pledgeBaseInfo", pledgeBaseInfo);
			this.changeFormProperty(formdlkeepout0008, "pledgeShowNo", "initValue", pledgeBaseInfo.getPledgeShowNo());
		}
		model.addAttribute("formdlkeepout0008", formdlkeepout0008);
		model.addAttribute("processId", processId);
		model.addAttribute("query", "");
		model.addAttribute("appId",appId);
		return "/component/collateral/KeepInfo_outstock_Insert";
	}

	@RequestMapping("/getHandlePage")
	public String getHandlePage(Model model, String collateralId, String collateralName, String collateralMethod)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		KeepInfo keepQuery = new KeepInfo();
		keepQuery.setCollateralId(collateralId);
		keepQuery.setKeepType(CollateralConstant.KEEP_IN);
		KeepInfo keepInfo = keepInfoFeign.getByColIdKPT(keepQuery);
		if (keepInfo == null) {
			keepInfo = new KeepInfo();
		}
		String keepId = WaterIdUtil.getWaterId();
		keepInfo.setKeepId(keepId);
		keepInfo.setCollateralId(collateralId);
		keepInfo.setCollateralName(collateralName);
		keepInfo.setCollateralMethod(collateralMethod);
		keepInfo.setKeepType(CollateralConstant.KEEP_HANDLE);
		keepInfo.setOperateDate(DateUtil.getDate());
		keepInfo.setKeepDesc("");
		FormData formdlkeephandle0009 = formService.getFormData("dlkeephandle0009");
		getObjValue(formdlkeephandle0009, keepInfo);
		model.addAttribute("formdlkeephandle0009", formdlkeephandle0009);
		model.addAttribute("query", "");
		return "/component/collateral/Handle_Page";
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model, String collateralNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralNo);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		String classId = pledgeBaseInfo.getClassId();

		MfCollateralFormConfig mfCollateralFormConfig = mfCollateralFormConfigFeign.getByPledgeImPawnType(classId,
				"KeepInfoAction", "");
		String formId = "";
		if (mfCollateralFormConfig == null) {

		} else {
			formId = mfCollateralFormConfig.getAddModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			// logger.error("押品类型为" + mfCollateralFormConfig.getFormType() +
			// "的KeepInfoController表单信息没有查询到");
		} else {
			KeepInfo keepInfo = new KeepInfo();
			keepInfo.setCollateralId(collateralNo);

			FormData formdlkeepinfo0002 = formService.getFormData(formId);
			if (formdlkeepinfo0002.getFormId() == null) {
				// logger.error("押品类型为" + mfCollateralFormConfig.getFormType() +
				// "的KeepInfoController表单form" + formId
				// + ".xml文件不存在");
			} else {
				getFormValue(formdlkeepinfo0002);
				getObjValue(formdlkeepinfo0002, keepInfo);
				model.addAttribute("formdlkeepinfo0002", formdlkeepinfo0002);
			}
		}

		model.addAttribute("query", "");
		return "/component/collateral/KeepInfo_Insert";
	}

	/**
	 * 查询保管页面 报关信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getKeepByCollateralId")
	public String getKeepByCollateralId(Model model, String collateralId) throws Exception {
		KeepInfo keepQuery = new KeepInfo();
		keepQuery.setCollateralId(collateralId);
		KeepInfo keepInfo = keepInfoFeign.getLatestOne(keepQuery);
		CodeUtils cu = new CodeUtils();
		Map<String, String> keepType = cu.getMapByKeyName("KEEP_TYPE");
		Map<String, String> keepStatus = cu.getMapByKeyName("KEEP_STATUS");
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralId);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		// 如果没有保管信息，则查询押品名称返回给页面。
		if (keepInfo == null) {
			keepInfo = new KeepInfo();
			keepInfo.setCollateralName(pledgeBaseInfo.getPledgeName());
			keepInfo.setCollateralId(collateralId);
			keepInfo.setKeepType(pledgeBaseInfo.getKeepStatus());
			keepInfo.setKeepTypeName(keepStatus.get(pledgeBaseInfo.getKeepStatus()));
			model.addAttribute("keepInfo", keepInfo);

		} else {
			keepInfo.setKeepType(pledgeBaseInfo.getKeepStatus());
			keepInfo.setKeepTypeName(keepStatus.get(pledgeBaseInfo.getKeepStatus()));
			// 如果存在保管信息，全部取出。
			KeepInfo keepListQuery = new KeepInfo();
			keepListQuery.setCollateralId(collateralId);
			List<KeepInfo> keepInfoList = keepInfoFeign.getByCollateralId(keepListQuery);
			for (KeepInfo ki : keepInfoList) {
				String str = keepType.get(ki.getKeepType());
				ki.setKeepTypeName(str);
			}
			model.addAttribute("keepInfoList", keepInfoList);
		}
		model.addAttribute("query", "");
		return "/component/collateral/KeepList_Page";
	}

	/**
	 * 
	 * 方法描述： 打开入库出库审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-26 下午3:46:27
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String keepId, String appId, String hideOpinionType,String activityType,String fincId,String taskId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formKeepInfoApprove = formService.getFormData("KeepInfoApprove");
		KeepInfo keepInfo = new KeepInfo();
		keepInfo.setKeepId(keepId);
		keepInfo = keepInfoFeign.getById(keepInfo);
		String aloneFlag="0";
		if(!StringUtil.isEmpty(keepInfo.getCollateralId())){//collateralId有值时为单个入库，没值是批量入库
			formKeepInfoApprove = formService.getFormData("aloneKeepInfoApprove");
			aloneFlag = "1";
		}
		getObjValue(formKeepInfoApprove, keepInfo);
		String cusNo = "";
		String collateralId = keepInfo.getCollateralId();
		if(StringUtil.isNotEmpty(collateralId)){
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(collateralId);
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			if(pledgeBaseInfo!=null){
				cusNo = pledgeBaseInfo.getCusNo();
			}
		}
		/*MfBusPact mfBusPact = pactInterfaceFeign.getByAppId(appId);
		if(mfBusPact!=null){
			cusNo = mfBusPact.getCusNo();
		}*/
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(keepId, null);// 当前审批节点task
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", taskAppro.getActivityName());// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formKeepInfoApprove, "opinionType", "optionArray", opinionTypeList);
        // 获得当前审批岗位前面审批过得岗位信息
        JSONArray befNodesjsonArray = new JSONArray();
        befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
        request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("formKeepInfoApprove", formKeepInfoApprove);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("query", "");
		model.addAttribute("aloneFlag", aloneFlag);
		model.addAttribute("keepInfo", keepInfo);
		model.addAttribute("appId", appId);
		model.addAttribute("fincId", fincId);
		model.addAttribute("nodeNo", taskAppro.getActivityName());
		return "/component/collateral/WkfKeepInfoViewPoint";
	}

	/**
	 * 
	 * 方法描述： 审批意见保存提交
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param operaType 
	 * @date 2017-9-26 下午6:21:47
	 */
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String appId, String taskId, String transition,
			String nextUser, String operaType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		FormData formKeepInfoApprove = formService.getFormData("KeepInfoApprove");
		getFormValue(formKeepInfoApprove, formDataMap);
		KeepInfo keepInfo = new KeepInfo();
		setObjValue(formKeepInfoApprove, keepInfo);
		Result res;
		try {
			formDataMap.put("appId", appId);
			formDataMap.put("operaType", operaType);
			formDataMap.put("opNo", User.getRegNo(request));
			formDataMap.put("orgNo", User.getOrgNo(request));
            new FeignSpringFormEncoder().addParamsToBaseDomain(keepInfo);
			formDataMap.put("keepInfo",keepInfo);
			res = keepInfoFeign.doCommit(taskId, transition, nextUser, formDataMap);
			dataMap = new HashMap<String, Object>();
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping("/getInsertPageAgain")
	public String getInsertPageAgain(Model model, String collateralId, String collateralName, String collateralMethod,String appId)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String keepId = WaterIdUtil.getWaterId();
		KeepInfo keepInfo = new KeepInfo();
		keepInfo.setKeepId(keepId);
		keepInfo.setCollateralId(collateralId);
		keepInfo.setCollateralName(collateralName);
		keepInfo.setCollateralMethod(collateralMethod);
		keepInfo.setKeepType(CollateralConstant.KEEP_IN_AGAIN);
		keepInfo.setRegDate(DateUtil.getDate());
		keepInfo.setOperatorCusName(User.getRegName(request));
		FormData formdlkeepinfo0007 = formService.getFormData("dlkeepinfo0007");
		getObjValue(formdlkeepinfo0007, keepInfo);
		String processId=BizPubParm.INSTOCK_APPROVE_WKF_NO;
		MfKindFlow mfKindFlow = new MfKindFlow();
		mfKindFlow.setModelType(BizPubParm.MODEL_TYPE_BASE);
		mfKindFlow.setCategory(BizPubParm.FLOW_CATEGORY_4);
		mfKindFlow.setFlowApprovalNo("inOutStock");
		mfKindFlow = prdctInterfaceFeign.getKindFlow(mfKindFlow);
		if(mfKindFlow != null && BizPubParm.YES_NO_Y.equals(mfKindFlow.getUseFlag())){
			if(StringUtil.isNotEmpty(mfKindFlow.getFlowId())){
				processId = mfKindFlow.getFlowId();
			}
		}
		PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
		pledgeBaseInfo.setPledgeNo(collateralId);
		pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
		if(pledgeBaseInfo != null){//入库页面展示押品展示号码
			model.addAttribute("pledgeBaseInfo", pledgeBaseInfo);
			this.changeFormProperty(formdlkeepinfo0007, "pledgeShowNo", "initValue", pledgeBaseInfo.getPledgeShowNo());
		}
		model.addAttribute("formdlkeepinfo0007", formdlkeepinfo0007);
		model.addAttribute("processId", processId);
		model.addAttribute("query", "");
		model.addAttribute("appId",appId);
		return "/component/collateral/KeepInfo_instock_InsertAgain";
	}

	/**
	 * AJAX新增
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/instockAgainAjax")
	@ResponseBody
	public Map<String, Object> instockAgainAjax(String ajaxData, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlkeepinfo0007 = formService.getFormData("dlkeepinfo0007");
			getFormValue(formdlkeepinfo0007, getMapByJson(ajaxData));
			if (this.validateFormData(formdlkeepinfo0007)) {
				KeepInfo keepInfo = new KeepInfo();
				setObjValue(formdlkeepinfo0007, keepInfo);
				keepInfo.setAppId(appId);
				keepInfo.setKeepType(CollateralConstant.KEEP_IN_AGAIN);
				keepInfo = keepInfoFeign.insertForInOutStock(keepInfo, CollateralConstant.INSTOCK);
				dataMap.put("keepInfo", keepInfo);
				dataMap.put("flag", "success");
				dataMap.put("isExamineInOutStock", keepInfo.getIsExamineInOutStock());
				if (BizPubParm.YES_NO_Y.equals(keepInfo.getIsExamineInOutStock())) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("userRole", keepInfo.getApproveNodeName());
					paramMap.put("opNo", keepInfo.getApprovePartName());
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				} else {
					dataMap.put("msg", "入库成功");
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 根据入库信息获得关联的货物明细
	 *r
	 * @return
	 * @throws Exception
	 *             String
	 * @author ywh
	 * @date 2019-5-16 下午8:31:50
	 */
	@RequestMapping("/getGoodsDetailList")
	@ResponseBody
	public Map<String, Object> getGoodsDetailList(String ajaxData, String keepId, String tableId)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<PledgeBaseInfoBill> pledgeBaseInfoBillList = new ArrayList<PledgeBaseInfoBill>();
		try {
			KeepInfo keepInfo = new KeepInfo();
			keepInfo.setKeepId(keepId);
			keepInfo = keepInfoFeign.getById(keepInfo);
			if(keepInfo!=null){
				String goodsDetail = keepInfo.getGoodsDetail();
				if(StringUtil.isNotEmpty(goodsDetail)){
					String[] goods = goodsDetail.split("\\|");

					for (int i = 0; i < goods.length; i++) {
						String good = goods[i];
						if(StringUtil.isNotEmpty(good)){
							PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
							pledgeBaseInfoBill.setPledgeBillNo(good);
							pledgeBaseInfoBill = pledgeBaseInfoBillFeign.getById(pledgeBaseInfoBill);
							if(pledgeBaseInfoBill!=null){
								pledgeBaseInfoBillList.add(pledgeBaseInfoBill);
							}
						}
					}
				}
			}

			JsonTableUtil jtu = new JsonTableUtil();
			if (pledgeBaseInfoBillList!=null&&pledgeBaseInfoBillList.size()>0){
				double goodsValus = pledgeBaseInfoBillFeign.getGoodsValue(pledgeBaseInfoBillList);
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", pledgeBaseInfoBillList, null, true);
				dataMap.put("tableData", tableHtml);
				dataMap.put("goodsValus", goodsValus);
				dataMap.put("flag", "success");
			}else {
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 在保业务应收账款点击入库操作
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/wareHouse")
	@ResponseBody
	public Map<String,Object> wareHouse(String pledgeNo,String appId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		KeepInfo keepInfo =  new KeepInfo();
		try {
			//
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(pledgeNo);
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			//应收账款到期时间是否在系统时间之前
			if(DateUtil.isBefore(pledgeBaseInfo.getPleExpiryDate())){
				keepInfo.setCollateralId(pledgeNo);
				keepInfo.setKeepType("2");
				keepInfoFeign.updateByCollateralId(keepInfo);
				String classFirstNo = "C";//押品类别为应收账款
				List<PledgeBaseInfo> listPledge = pledgeBaseInfoFeign.getAccountByAppId(appId, classFirstNo);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr("tableaccountInfoBase", "tableTag", listPledge, null, true);
				dataMap.put("tableHtml", tableHtml);
				dataMap.put("flag", "success");
				dataMap.put("msg", "出库成功");
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", "应收账款未到期，不可出库");
			}
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;

	}

	/**
	 * 应收账款批量出库
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/outWareHouse")
	@ResponseBody
	public Map<String,Object> outWareHouse(String appId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String classFirstNo="C";//押品类别为应收账款
			String outWareHouse="";//是否有应收账款不到期
			List<PledgeBaseInfo> listPledge = pledgeBaseInfoFeign.getAccountByAppId(appId,classFirstNo);
			for(int i=0;i<listPledge.size();i++){
                //应收账款到期日是否在系统时间之前
				if(DateUtil.isBefore(listPledge.get(i).getPleExpiryDate())){
				}else{
					outWareHouse="yes";
				}
			}
			if(outWareHouse.equals("yes")){//有不到期应收账款
				dataMap.put("flag", "error");
				dataMap.put("msg", "有未到期应收账款，不可批量出库");
			}else{
				for(int i=0;i<listPledge.size();i++){
					KeepInfo keep = new KeepInfo();
					keep.setCollateralId(listPledge.get(i).getPledgeNo());
					keep.setKeepType("2");
					keepInfoFeign.updateByCollateralId(keep);
				}
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr("tableaccountInfoBase", "tableTag", listPledge, null, true);
				dataMap.put("tableHtml", tableHtml);
				dataMap.put("flag", "success");
				dataMap.put("msg", "出库成功");
			}
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
}
